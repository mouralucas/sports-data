package com.rolf.sports_data.api;

public final class ApiRoutes {

    private ApiRoutes() {
    }

    public static final String API_V1 = "/api/v1";

    public static final String SPORTS = API_V1 + "/sports";
    public static final String COMPETITIONS = API_V1 + "/competitions";
    public static final String COMPETITION_EDITIONS = API_V1 + "/competition-editions";
    public static final String COMPETITION_STAGES = API_V1 + "/competition-stages";
    public static final String TEAMS = API_V1 + "/teams";
    public static final String VENUES = API_V1 + "/venues";
}