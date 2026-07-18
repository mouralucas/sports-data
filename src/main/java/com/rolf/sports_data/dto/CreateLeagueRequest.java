package com.rolf.sports_data.dto;

public class CreateLeagueRequest {
    private String name;
    private String description;
    private boolean playerScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(boolean playerScore) {
        this.playerScore = playerScore;
    }
}