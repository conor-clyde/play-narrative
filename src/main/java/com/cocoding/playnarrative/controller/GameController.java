package com.cocoding.playnarrative.controller;

import com.cocoding.playnarrative.model.Game;
import com.cocoding.playnarrative.service.RawgService;
import com.cocoding.playnarrative.repository.GameRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import com.cocoding.playnarrative.service.RawgService;

@Controller
public class GameController {

    private final GameRepository gameRepository;
    private final RawgService rawgService;

    public GameController(GameRepository gameRepository, RawgService rawgService) {
        this.gameRepository = gameRepository;
        this.rawgService = rawgService;
    }

    // === WEB PAGES (HTML) ===
    @GetMapping("/games")
    public String listGames(Model model) {
        List<Game> games = gameRepository.findAll();
        model.addAttribute("games", games);
        return "games/list"; // Returns HTML template
    }

    @GetMapping("/games/add")
    public String showAddForm(Model model) {
        model.addAttribute("game", new Game());
        return "games/add"; // Returns HTML form
    }

    @PostMapping("/games/add")
    public String addGame(Game game) {
        gameRepository.save(game);
        return "redirect:/games"; // Redirects to HTML page
    }

    // === REST API (JSON) ===
    @GetMapping("/api/games")
    @ResponseBody
    public List<Game> getGamesJson() {
        return gameRepository.findAll(); // Returns JSON data
    }

    @PostMapping("/api/games")
    @ResponseBody
    public Game createGameJson(@RequestBody Game game) {
        return gameRepository.save(game); // Returns JSON response
    }

    @GetMapping("/api/games/{id}")
    @ResponseBody
    public ResponseEntity<Game> getGameJson(@PathVariable Long id) {
        return gameRepository.findById(id)
                .map(game -> ResponseEntity.ok(game))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/library/game/{id}")
    public String viewGame(@PathVariable Long id, Model model) {
        Game game = gameRepository.findById(id).orElseThrow();
        Map<String, Object> details = rawgService.getGameDetails(game.getRawgId());

        model.addAttribute("game", game); // Local data (owned, intent, platforms)
        model.addAttribute("details", details); // RAWG data (artwork, genre, description)

        return "library/game";
    }

    @GetMapping("/api/games/search")
    @ResponseBody
    public List<Map<String, Object>> searchGames(@RequestParam String query) {
        return rawgService.searchGames(query);
    }
}