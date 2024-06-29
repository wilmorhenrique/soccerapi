package com.whd.soccerclientapi.webclientsoccerapi.service;

import com.google.gson.Gson;
import com.whd.soccerclientapi.webclientsoccerapi.model.League;
import com.whd.soccerclientapi.webclientsoccerapi.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TheSportsDBAPIClientService {
    private final String URI = "https://www.thesportsdb.com/api/v1/json/3"; // FREE API

    public League getLeagueById(String leagueId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = URI + "/lookupleague.php?id="+leagueId;
        String rawData = restTemplate.getForObject(url, String.class);
        rawData = rawData.replace("leagues", "countries");
        LeagueListPojo list = new Gson().fromJson(rawData, LeagueListPojo.class);
        if (list == null || list.getLeagues() == null ) return new League();
        return list.getLeagues().get(0);

    }
    public CountryListPojo getCountries(){
        RestTemplate restTemplate = new RestTemplate();
        String rawData = restTemplate.getForObject(URI + "/all_countries.php", String.class);
        CountryListPojo countries = new Gson().fromJson(rawData, CountryListPojo.class);
        return countries;

    }

    public LeagueListPojo getLeagues(String countryName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = URI + "/search_all_leagues.php?c="+countryName;
        String rawData = restTemplate.getForObject(url, String.class);
        LeagueListPojo list = new Gson().fromJson(rawData, LeagueListPojo.class);
        return list;
    }

    public TeamListPojo getTeamsByLeague(String leagueName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = URI + "/search_all_teams.php?l="+leagueName;
        String rawData = restTemplate.getForObject(url, String.class);
        TeamListPojo list = new Gson().fromJson(rawData, TeamListPojo.class);
        return list;
    }

    public SeasonListPojo getSeasonsByLeague(String idLeague) {
        RestTemplate restTemplate = new RestTemplate();
        String url = URI + "/search_all_seasons.php?id="+idLeague;
        String rawData = restTemplate.getForObject(url, String.class);
        SeasonListPojo list = new Gson().fromJson(rawData, SeasonListPojo.class);
        list.setIdLeague(idLeague);
        return list;
    }


    public EventListPojo getEventsByLeagueAndSeason(String idLeague, String strSeason) {
        RestTemplate restTemplate = new RestTemplate();
        String url = URI + "/eventsseason.php?id="+idLeague+"&s="+strSeason;
        String rawData = restTemplate.getForObject(url, String.class);
        return  new Gson().fromJson(rawData, EventListPojo.class);

    }
}
