package com.rolf.sports_data.enums;

public enum ClassificationMetricTypeEnum {

    POINTS("points", "Points"),
    WINS("wins", "Wins"),
    LOSSES("losses", "Losses"),
    DRAWS("draws", "Draws"),
    GOALS_FOR("goals_for", "Goals For"),
    GOALS_AGAINST("goals_against", "Goals Against"),
    GOAL_DIFFERENCE("goal_difference", "Goal Difference"),
    PODIUMS("podiums", "Podiums"),
    FASTEST_LAPS("fastest_laps", "Fastest Laps"),
    LAPS_COMPLETED("laps_completed", "Laps Completed"),
    TIME_TOTAL("time_total", "Total Time");

    private final String code;
    private final String description;

    ClassificationMetricTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}