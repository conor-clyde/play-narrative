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
            String url = BASE_URL + "/games?key=" + apiKey + "&search=" + query + "&page_size=10";
            Map response = restTemplate.getForObject(url, Map.class);

            if (response.containsKey("results")) {
                List games = (List) response.get("results");

                for (Object gameObj : games) {
                    Map game = (Map) gameObj;
                    Map<String, Object> gameData = new HashMap<>();

                    gameData.put("id", game.get("id").toString());
                    gameData.put("name", game.get("name").toString());

                    if (game.containsKey("background_image") && game.get("background_image") != null) {
                        gameData.put("background_image", game.get("background_image").toString());
                    }

                    results.add(gameData);
                }
            }
        } catch (Exception e) {
            System.err.println("Search failed: " + e.getMessage());
        }

        return results;
    }
}