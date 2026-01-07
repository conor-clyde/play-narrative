package com.cocoding.playnarrative.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RawgService {

    @Value("${rawg.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://api.rawg.io/api";

    public List<Map<String, Object>> searchGames(String query) {
        List<Map<String, Object>> results = new ArrayList<>();

        try {
            String url = BASE_URL + "/games?key=" + apiKey + "&search=" + query + "&page_size=5";
            Map response = restTemplate.getForObject(url, Map.class);

            if (response.containsKey("results")) {
                List games = (List) response.get("results");

                for (Object gameObj : games) {
                    Map game = (Map) gameObj;
                    Map<String, Object> gameData = new HashMap<>();

                    gameData.put("id", game.get("id").toString());
                    gameData.put("name", game.get("name").toString());

                    results.add(gameData);
                }
            }
        } catch (Exception e) {
            System.err.println("Search failed: " + e.getMessage());
        }

        return results;
    }

    public Map<String, Object> getGameDetails(String rawgId) {
        Map<String, Object> details = new HashMap<>();

        try {
            String url = BASE_URL + "/games/" + rawgId + "?key=" + apiKey;
            Map response = restTemplate.getForObject(url, Map.class);

            details.put("name", response.get("name").toString());

            if (response.containsKey("description_raw") && response.get("description_raw") != null) {
                details.put("description", response.get("description_raw").toString());
            }

            if (response.containsKey("background_image") && response.get("background_image") != null) {
                details.put("background_image", response.get("background_image").toString());
            }

        } catch (Exception e) {
            System.err.println("Failed to get game details: " + e.getMessage());
        }

        return details;
    }
}