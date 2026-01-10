package com.cocoding.playnarrative.model;

public enum EngagementType {
    STORY("Story"),
    COMPLETION("Completion"),
    ONLINE_MP("Online MP"),
    SOCIAL("Social"),
    COZY_LIFE_SIM("Cozy / Life Sim");

    private final String displayName;

    EngagementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
