package com.cocoding.playnarrative.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class IgdbService {

    private static final Logger logger = LoggerFactory.getLogger(IgdbService.class);

    @Value("${igdb.client.id}")
    private String clientId;

    @Value("${igdb.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private String accessToken;
    private long tokenExpiryTime = 0;

    /**
     * Get OAuth2 access token from Twitch using client credentials flow.
     * Tokens are cached until expiry.
     */
    private String getAccessToken() {
        // Check if we have a valid cached token
        if (accessToken != null && System.currentTimeMillis() < tokenExpiryTime) {
            return accessToken;
        }

        // Request new token
        String tokenUrl = "https://id.twitch.tv/oauth2/token";
        String requestBody = String.format(
            "client_id=%s&client_secret=%s&grant_type=client_credentials",
            clientId, clientSecret
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(tokenUrl, entity, Map.class);
            if (response != null && response.containsKey("access_token")) {
                accessToken = (String) response.get("access_token");
                // Cache token for slightly less than the expiry time (default is 60 days, but we'll refresh after 30 days to be safe)
                long expiresIn = response.containsKey("expires_in") 
                    ? ((Number) response.get("expires_in")).longValue() * 1000 
                    : 30L * 24 * 60 * 60 * 1000; // 30 days default
                tokenExpiryTime = System.currentTimeMillis() + expiresIn - (60 * 60 * 1000); // Refresh 1 hour before expiry
                return accessToken;
            }
        } catch (Exception e) {
            logger.error("Failed to obtain IGDB access token", e);
            throw new RuntimeException("Failed to obtain IGDB access token", e);
        }

        logger.error("Failed to obtain IGDB access token: no access_token in response");
        throw new RuntimeException("Failed to obtain IGDB access token: no access_token in response");
    }

    /**
     * Create headers for IGDB API requests.
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", clientId);
        headers.set("Authorization", "Bearer " + getAccessToken());
        headers.setContentType(MediaType.TEXT_PLAIN);
        return headers;
    }

    /**
     * Format IGDB cover image ID to full URL.
     * IGDB returns cover as either:
     * - A number (cover ID) when requesting just "cover"
     * - An object with image_id when requesting "cover.image_id"
     */
    @SuppressWarnings("unchecked")
    private String formatCoverImageUrl(Object coverObj) {
        if (coverObj == null) return null;
        
        // If it's already a URL, return it
        if (coverObj instanceof String) {
            String url = (String) coverObj;
            if (url.startsWith("http")) {
                return url;
            }
        }
        
        // Extract image_id from cover object
        String imageId = null;
        if (coverObj instanceof Map) {
            Map<String, Object> cover = (Map<String, Object>) coverObj;
            // IGDB cover object has image_id field when requesting cover.image_id
            if (cover.containsKey("image_id")) {
                imageId = cover.get("image_id").toString();
            } else if (cover.containsKey("id")) {
                // Fallback to id if image_id not present (for backward compatibility)
                imageId = cover.get("id").toString();
            }
        } else if (coverObj instanceof Number) {
            // IGDB may return cover as just the ID number (legacy format)
            // The cover ID can be used as the image_id
            imageId = coverObj.toString();
        } else {
            imageId = coverObj.toString();
        }
        
        if (imageId == null || imageId.isEmpty()) {
            return null;
        }
        
        // Format as IGDB cover URL (t_cover_big for large covers, t_cover_small for thumbnails)
        // IGDB image URLs: https://images.igdb.com/igdb/image/upload/t_{size}/{image_id}.jpg
        return "https://images.igdb.com/igdb/image/upload/t_cover_big/" + imageId + ".jpg";
    }

    /**
     * Search games by name.
     * Returns all games regardless of type (main games, DLCs, expansions, etc.).
     */
    public List<Map<String, Object>> searchGames(String query) {
        String url = "https://api.igdb.com/v4/games";
        
        // IGDB uses POST with a query string in the body
        // Fields: id, name, cover.image_id (for image URL), first_release_date, platforms, summary
        String queryBody = String.format(
            "search \"%s\"; fields id,name,cover.image_id,first_release_date,platforms.name,summary; limit 50;",
            query.replace("\"", "\\\"")
        );

        HttpEntity<String> entity = new HttpEntity<>(queryBody, createHeaders());

        try {
            logger.debug("IGDB search query: {}", queryBody);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> games = restTemplate.postForObject(url, entity, List.class);
            
            if (games == null) {
                logger.warn("IGDB search returned null for query: {}", query);
                return List.of();
            }
            
            logger.debug("IGDB search returned {} results for query: {}", games.size(), query);
            
            // Transform IGDB response to match expected format
            return games.stream().map(game -> {
                Map<String, Object> transformed = new HashMap<>();
                transformed.put("id", game.get("id"));
                transformed.put("name", game.get("name"));
                
                // Format cover image URL
                Object cover = game.get("cover");
                if (cover != null) {
                    String imageUrl = formatCoverImageUrl(cover);
                    transformed.put("background_image", imageUrl);
                    transformed.put("cover", cover);
                }
                
                // Format release date (IGDB uses Unix timestamp)
                Object releaseDate = game.get("first_release_date");
                if (releaseDate != null) {
                    try {
                        long timestamp = ((Number) releaseDate).longValue() * 1000; // Convert to milliseconds
                        java.util.Date date = new java.util.Date(timestamp);
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        transformed.put("released", sdf.format(date));
                    } catch (Exception e) {
                        // Ignore date parsing errors
                    }
                }
                
                // Format platforms
                Object platformsObj = game.get("platforms");
                if (platformsObj instanceof List) {
                    List<Map<String, Object>> platformsList = (List<Map<String, Object>>) platformsObj;
                    List<Map<String, Object>> formattedPlatforms = new java.util.ArrayList<>();
                    for (Map<String, Object> platform : platformsList) {
                        Map<String, Object> platformMap = new HashMap<>();
                        Map<String, Object> platformData = new HashMap<>();
                        platformData.put("name", platform.get("name"));
                        platformMap.put("platform", platformData);
                        formattedPlatforms.add(platformMap);
                    }
                    transformed.put("platforms", formattedPlatforms);
                }
                
                transformed.put("summary", game.get("summary"));
                
                return transformed;
            }).collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to search IGDB games for query: " + query, e);
            throw new RuntimeException("Failed to search IGDB games", e);
        }
    }

    /**
     * Get game details by IGDB ID.
     */
    public Map<String, Object> getGameDetails(String igdbId) {
        String url = "https://api.igdb.com/v4/games";
        
        // IGDB uses POST with a query string in the body
        String queryBody = String.format(
            "fields id,name,cover.image_id,first_release_date,platforms.name,summary,genres.name; where id = %s;",
            igdbId
        );

        HttpEntity<String> entity = new HttpEntity<>(queryBody, createHeaders());

        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> games = restTemplate.postForObject(url, entity, List.class);
            
            if (games == null || games.isEmpty()) {
                return null;
            }
            
            Map<String, Object> game = games.get(0);
            Map<String, Object> transformed = new HashMap<>();
            transformed.put("id", game.get("id"));
            transformed.put("name", game.get("name"));
            
            // Format cover image URL
            Object cover = game.get("cover");
            if (cover != null) {
                String imageUrl = formatCoverImageUrl(cover);
                transformed.put("background_image", imageUrl);
                transformed.put("cover", cover);
            }
            
            // Format release date
            Object releaseDate = game.get("first_release_date");
            if (releaseDate != null) {
                try {
                    long timestamp = ((Number) releaseDate).longValue() * 1000;
                    java.util.Date date = new java.util.Date(timestamp);
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    transformed.put("released", sdf.format(date));
                } catch (Exception e) {
                    // Ignore date parsing errors
                }
            }
            
            // Format platforms
            Object platformsObj = game.get("platforms");
            if (platformsObj instanceof List) {
                List<Map<String, Object>> platformsList = (List<Map<String, Object>>) platformsObj;
                List<Map<String, Object>> formattedPlatforms = new java.util.ArrayList<>();
                for (Map<String, Object> platform : platformsList) {
                    Map<String, Object> platformMap = new HashMap<>();
                    Map<String, Object> platformData = new HashMap<>();
                    platformData.put("name", platform.get("name"));
                    platformMap.put("platform", platformData);
                    formattedPlatforms.add(platformMap);
                }
                transformed.put("platforms", formattedPlatforms);
            }
            
            transformed.put("summary", game.get("summary"));
            transformed.put("genres", game.get("genres"));
            
            return transformed;
        } catch (Exception e) {
            logger.error("Failed to get IGDB game details for ID: " + igdbId, e);
            throw new RuntimeException("Failed to get IGDB game details", e);
        }
    }
}
