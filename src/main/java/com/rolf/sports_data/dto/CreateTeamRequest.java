package com.rolf.sports_data.dto;

public class CreateTeamRequest {
    private String name;
    private Boolean isNationalTeam;
    private Long sportId;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsNationalTeam() {
        if (this.isNationalTeam == null){
            return false;
        }
        return this.isNationalTeam;
    }

    public void setIsNationalTeam(boolean isNationalTeam) {
        this.isNationalTeam = isNationalTeam;
    }

    public Long getSportId() {
        return sportId;
    }

    public void setSportId(Long sportId) {
        this.sportId = sportId;
    }
}
