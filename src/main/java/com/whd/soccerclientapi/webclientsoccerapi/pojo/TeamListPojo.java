package com.whd.soccerclientapi.webclientsoccerapi.pojo;

import java.util.ArrayList;
import java.util.List;

public class TeamListPojo {
// keep field as countries because the raw data brings a list with name countries
    private List<TeamPojo> teams;

    public List<TeamPojo> getTeams() {
        return teams == null ? new ArrayList<>() : teams;
    }

    public void setTeams(List<TeamPojo> teams) {
        this.teams = teams;
    }
}
