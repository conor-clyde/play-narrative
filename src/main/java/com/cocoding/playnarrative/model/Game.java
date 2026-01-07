package com.cocoding.playnarrative.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String rawgId;
    private boolean owned;
    private String intent;
    @ElementCollection
    private List<String> platforms; // PC, PS5, etc.


    // Default constructor (required by JPA)
    public Game() {}

    // Constructor with parameters
    public Game(String title, String rawgId, boolean owned, String intent, List<String> platforms) {
        this.title = title;
        this.rawgId = rawgId;
        this.owned = owned;
        this.intent = intent;
        this.platforms = platforms;


    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getRawgId() {
        return rawgId;
    }

    public void setRawgId(String rawgId) {
        this.rawgId = rawgId;
    }
    
    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rawgId='" + rawgId + '\'' +
                ", owned='" + owned + '\'' +
                ", intent='" + intent + '\'' +
                ", platforms='" + platforms + '\'' +
                '}';
    }
}
