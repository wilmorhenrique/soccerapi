package com.whd.soccerclientapi.webclientsoccerapi.pojo;

import com.whd.soccerclientapi.webclientsoccerapi.model.League;

import java.util.ArrayList;
import java.util.List;

public class LeagueListPojo {
// keep field as countries because the raw data brings a list with name countries
    private List<League> countries;

    public List<League> getLeagues() {
        return countries == null ? new ArrayList<>() : countries;
    }

    public void setLeagues(List<League> leagues) {
        this.countries = leagues;
    }
}
