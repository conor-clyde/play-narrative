package com.cocoding.playnarrative.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String externalId;

    // Display
    private String title;
    private String releaseYear;
    private String imageUrl;

    // Ownership
    private boolean owned; 

    @ElementCollection
    private List<String> platforms;

    @Enumerated(EnumType.STRING)
    private GameFormat format;

    // Play organization
    @Enumerated(EnumType.STRING)
    private PlayState playState;

    // Engagement types - multi-select to describe how you engage with the game
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<EngagementType> engagementTypes;
    
    @Column(length = 1000)
    private String sessionNotes; // For reflection prompts

    // Default constructor (required by JPA)
    public Game() {
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

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean getOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public GameFormat getFormat() {
        return format;
    }

    public void setFormat(GameFormat format) {
        this.format = format;
    }

    public PlayState getPlayState() {
        return playState;
    }

    public void setPlayState(PlayState playState) {
        this.playState = playState;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public List<EngagementType> getEngagementTypes() {
        return engagementTypes;
    }

    public void setEngagementTypes(List<EngagementType> engagementTypes) {
        this.engagementTypes = engagementTypes;
    }

    public String getSessionNotes() {
        return sessionNotes;
    }

    public void setSessionNotes(String sessionNotes) {
        this.sessionNotes = sessionNotes;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", owned=" + owned +
                ", playState=" + playState +
                ", engagementTypes=" + engagementTypes +
                ", externalId=" + externalId +
                '}';
    }
}
