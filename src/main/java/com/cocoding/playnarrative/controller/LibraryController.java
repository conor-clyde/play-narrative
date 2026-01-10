package com.cocoding.playnarrative.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocoding.playnarrative.model.EngagementType;
import com.cocoding.playnarrative.model.Game;
import com.cocoding.playnarrative.model.GameFormat;
import com.cocoding.playnarrative.model.PlayState;
import com.cocoding.playnarrative.repository.GameRepository;
import com.cocoding.playnarrative.service.IgdbService;

@Controller
@RequestMapping("/library")
public class LibraryController {

    private final GameRepository gameRepository;

    public LibraryController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // =========================
    // Library page
    // =========================
    @GetMapping
    public String library(@RequestParam(required = false) String view, Model model) {
        // Default to "playing" if view is null or empty
        if (view == null || view.isEmpty()) {
            view = "playing";
        }

        List<Game> currentlyPlaying =
                gameRepository.findByPlayState(PlayState.PLAYING);
        if (currentlyPlaying == null) {
            currentlyPlaying = new java.util.ArrayList<>();
        }

        List<Game> exploreNext =
                gameRepository.findByOwnedAndPlayState(true, PlayState.NOT_STARTED);
        if (exploreNext == null) {
            exploreNext = new java.util.ArrayList<>();
        }

        List<Game> finished =
                gameRepository.findByPlayState(PlayState.FINISHED);
        if (finished == null) {
            finished = new java.util.ArrayList<>();
        }

        List<Game> wishlist =
                gameRepository.findByOwned(false);
        if (wishlist == null) {
            wishlist = new java.util.ArrayList<>();
        }

        // Calculate total games in library (owned games)
        List<Game> allOwnedGames = gameRepository.findByOwned(true);
        int totalGames = allOwnedGames != null ? allOwnedGames.size() : 0;

        model.addAttribute("currentlyPlaying", currentlyPlaying);
        model.addAttribute("engagementTypes", EngagementType.values());
        model.addAttribute("exploreNext", exploreNext);
        model.addAttribute("finished", finished);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("view", view);
        model.addAttribute("totalGames", totalGames);

        return "library/list";
    }

    // =========================
    // Add game form
    // =========================
    @GetMapping("/add")
    public String showAddGameForm(Model model) {
        model.addAttribute("game", new Game());
        return "library/add-game";
    }

    // =========================
    // Add game submit
    // =========================
    @PostMapping("/add")
    public String addGame(
            @RequestParam String title,
            @RequestParam(required = false) String igdbId,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) String releaseYear,
            @RequestParam(required = false, defaultValue = "") List<String> platforms,
            @RequestParam(required = false) String format,
            // New flow parameters
            @RequestParam(defaultValue = "owned") String ownership, // owned, wishlist, played_before
            @RequestParam(defaultValue = "NOT_STARTED") String initialStatus, // NOT_STARTED, PLAYING, FINISHED, ON_HOLD
            @RequestParam(required = false) List<String> engagementTypes, // Multi-select engagement types
            @RequestParam(required = false) String contextualNotes, // Optional notes
            @RequestParam(defaultValue = "false") boolean makeActiveNow, // Whether to mark as Currently Playing
            Model model) {

        // Create new game
        Game game = new Game();
        game.setTitle(title);
        game.setExternalId(igdbId);
        game.setImageUrl(imageUrl);
        game.setReleaseYear(releaseYear);
        // Handle platforms - filter out empty strings
        if (platforms != null) {
            List<String> filteredPlatforms = platforms.stream()
                    .filter(p -> p != null && !p.trim().isEmpty())
                    .collect(Collectors.toList());
            game.setPlatforms(filteredPlatforms.isEmpty() ? null : filteredPlatforms);
        }

        // Set format if provided
        if (format != null && !format.isEmpty()) {
            try {
                // Map form values to enum values
                String formatUpper = format.toUpperCase();
                if (formatUpper.equals("BOTH")) {
                    game.setFormat(GameFormat.BOTH);
                } else if (formatUpper.equals("PHYSICAL")) {
                    game.setFormat(GameFormat.PHYSICAL);
                } else if (formatUpper.equals("DIGITAL")) {
                    game.setFormat(GameFormat.DIGITAL);
                }
            } catch (IllegalArgumentException e) {
                // Invalid format, ignore
            }
        }

        // Handle ownership/intent
        // "owned" and "played_before" both mean the user owns the game
        boolean owned = ownership.equalsIgnoreCase("owned") || ownership.equalsIgnoreCase("played_before");
        game.setOwned(owned);

        // Handle initial status
        PlayState playState;
        try {
            playState = PlayState.valueOf(initialStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            playState = PlayState.NOT_STARTED; // Default fallback
        }

        // If makeActiveNow is true, override to PLAYING
        if (makeActiveNow) {
            playState = PlayState.PLAYING;
        }

        game.setPlayState(playState);

        // Set engagement types if provided (multi-select)
        if (engagementTypes != null && !engagementTypes.isEmpty()) {
            List<EngagementType> validTypes = engagementTypes.stream()
                    .filter(et -> et != null && !et.trim().isEmpty())
                    .map(et -> {
                        try {
                            // Handle both old format (STORY_CAMPAIGN) and new format (STORY)
                            String normalized = et.toUpperCase().replace(" ", "_");
                            return EngagementType.valueOf(normalized);
                        } catch (IllegalArgumentException e) {
                            return null;
                        }
                    })
                    .filter(et -> et != null)
                    .collect(Collectors.toList());
            if (!validTypes.isEmpty()) {
                game.setEngagementTypes(validTypes);
            }
        }

        // Store contextual notes in sessionNotes field
        if (contextualNotes != null && !contextualNotes.trim().isEmpty()) {
            game.setSessionNotes(contextualNotes.trim());
        }

        gameRepository.save(game);

        // Add success message to model for post-add confirmation
        model.addAttribute("gameAdded", true);
        model.addAttribute("gameTitle", title);
        model.addAttribute("isCurrentlyPlaying", playState == PlayState.PLAYING);

        return "redirect:/library?added=true&title=" +
                java.net.URLEncoder.encode(title, java.nio.charset.StandardCharsets.UTF_8) +
                "&playing=" + (playState == PlayState.PLAYING);
    }

    // =========================
    // Start playing a game
    // =========================
    @PostMapping("/{id}/start")
    public String startPlaying(@PathVariable Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game id: " + id));

        game.setPlayState(PlayState.PLAYING);
        gameRepository.save(game);

        return "redirect:/library";
    }

    // =========================
    // Change play state
    // =========================
    @PostMapping("/{id}/state")
    public String changePlayState(@PathVariable Long id, @RequestParam String state) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game id: " + id));

        try {
            PlayState playState = PlayState.valueOf(state.toUpperCase());
            game.setPlayState(playState);
            gameRepository.save(game);
        } catch (IllegalArgumentException e) {
            // Invalid state, ignore
        }

        return "redirect:/library";
    }

    // =========================
    // Move game from wishlist to library (mark as owned)
    // =========================
    @PostMapping("/{id}/own")
    public String moveToLibrary(@PathVariable Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game id: " + id));

        game.setOwned(true);
        game.setPlayState(PlayState.NOT_STARTED);
        gameRepository.save(game);

        return "redirect:/library";
    }

    // =========================
    // View game details
    // =========================
    @GetMapping("/{id}")
    public String viewGame(@PathVariable Long id, Model model) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game id: " + id));

        model.addAttribute("game", game);
        model.addAttribute("engagementTypes", EngagementType.values());
        return "library/detail";
    }

    // =========================
    // Delete game
    // =========================
    @PostMapping("/{id}/delete")
    public String deleteGame(@PathVariable Long id) {
        gameRepository.deleteById(id);
        return "redirect:/library";
    }

    // =========================
    // Update game
    // =========================
    @PostMapping("/{id}/update")
    public ResponseEntity<Map<String, Object>> updateGame(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) List<String> engagementTypes,
            @RequestParam(required = false) List<String> platforms,
            @RequestParam(required = false) String format) {

        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game id: " + id));

        // Update status (owned/wishlist)
        if (status != null) {
            game.setOwned(status.equals("owned"));
        }

        // Update engagement types
        if (engagementTypes != null) {
            List<EngagementType> validTypes = engagementTypes.stream()
                    .filter(et -> et != null && !et.trim().isEmpty())
                    .map(et -> {
                        try {
                            return EngagementType.valueOf(et.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            return null;
                        }
                    })
                    .filter(et -> et != null)
                    .collect(Collectors.toList());
            game.setEngagementTypes(validTypes.isEmpty() ? null : validTypes);
        }

        // Update platforms
        if (platforms != null) {
            List<String> filteredPlatforms = platforms.stream()
                    .filter(p -> p != null && !p.trim().isEmpty())
                    .collect(Collectors.toList());
            game.setPlatforms(filteredPlatforms.isEmpty() ? null : filteredPlatforms);
        }

        // Update format
        if (format != null && !format.isEmpty()) {
            try {
                String formatUpper = format.toUpperCase();
                if (formatUpper.equals("BOTH")) {
                    game.setFormat(GameFormat.BOTH);
                } else if (formatUpper.equals("PHYSICAL")) {
                    game.setFormat(GameFormat.PHYSICAL);
                } else if (formatUpper.equals("DIGITAL")) {
                    game.setFormat(GameFormat.DIGITAL);
                }
            } catch (IllegalArgumentException e) {
                // Invalid format, ignore
            }
        }

        gameRepository.save(game);

        Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    // =========================
    // Add reflection note
    // =========================
    @PostMapping("/{id}/reflection")
    public String addReflection(@PathVariable Long id, @RequestParam String reflection) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid game id: " + id));

        // Append to existing session notes or create new
        String existingNotes = game.getSessionNotes();
        if (existingNotes != null && !existingNotes.isEmpty()) {
            game.setSessionNotes(existingNotes + "\n\n" + reflection);
        } else {
            game.setSessionNotes(reflection);
        }

        gameRepository.save(game);
        return "redirect:/library";
    }
}

/**
 * REST API Controller for game search functionality
 */
@RestController
@RequestMapping("/api/games")
class GameApiController {

    private final IgdbService igdbService;
    private final GameRepository gameRepository;

    public GameApiController(IgdbService igdbService, GameRepository gameRepository) {
        this.igdbService = igdbService;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchGames(@RequestParam String query) {
        try {
            List<Map<String, Object>> results = igdbService.searchGames(query);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> checkGameStatus(@RequestParam String igdbId) {
        try {
            // Check if game exists by externalId - use efficient query instead of loading all games
            Optional<Game> gameOpt = gameRepository.findByExternalId(igdbId);

            Map<String, Object> response = new java.util.HashMap<>();
            if (gameOpt.isPresent()) {
                Game game = gameOpt.get();
                response.put("exists", true);
                response.put("status", game.getOwned() ? "owned" : "wishlist");
            } else {
                response.put("exists", false);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}