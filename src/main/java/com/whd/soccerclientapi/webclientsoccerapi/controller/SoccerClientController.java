package com.whd.soccerclientapi.webclientsoccerapi.controller;

import com.whd.soccerclientapi.webclientsoccerapi.pojo.CountryListPojo;
import com.whd.soccerclientapi.webclientsoccerapi.pojo.LeagueListPojo;
import com.whd.soccerclientapi.webclientsoccerapi.pojo.SeasonListPojo;
import com.whd.soccerclientapi.webclientsoccerapi.service.CountryLoadService;
import com.whd.soccerclientapi.webclientsoccerapi.service.DataLoadService;
import com.whd.soccerclientapi.webclientsoccerapi.service.LeagueLoadService;
import com.whd.soccerclientapi.webclientsoccerapi.service.TheSportsDBAPIClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController("/")
public class SoccerClientController {
    @Autowired
    CountryLoadService countryLoadService;
    @Autowired
    DataLoadService dataLoadService;
    @Autowired
    LeagueLoadService leagueLoadService;

    @Autowired
    TheSportsDBAPIClientService apiClient;
    @GetMapping
    public String imAlive(){
        log.info("entrei e vai esperar 1 Segundo");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        return "I'm alive!!!";
    }


    @GetMapping("/countries")
    public CountryListPojo getCountries(){
        return apiClient.getCountries();
    }

    @GetMapping("/updateLeagues")
    public String  updateLeagues() throws Exception {
        return leagueLoadService.updateLeagues();
    }

    @GetMapping("/load-all-data/{countryName}")
    public String loadAllData(@PathVariable String countryName){
        if (countryName.isEmpty()) {
            return "Coloque o pais!!(Ex; Brazil, Portugal, Germany";
        }
        return dataLoadService.loadAllData(countryName);
    }


    @GetMapping("/loadCountries")
    public String loadCountries(){
        countryLoadService.countryLoad();
        return "all country are loaded!!";
    }

    @GetMapping("/leagues/{countryName}")
    public LeagueListPojo getLeaguesByCountry(@PathVariable String countryName){
        return apiClient.getLeagues(countryName);
    }

    @GetMapping("/loadLeagues/{countryName}")
    public LeagueListPojo loadLesByCountry(@PathVariable String countryName) throws Exception {
        return leagueLoadService.leagueLoadByCountry(countryName);
    }

    @GetMapping("/loadSeasons/{idLeague}")
    public SeasonListPojo loadSeasonByLeagueId(@PathVariable String idLeague) throws Exception {
        return apiClient.getSeasonsByLeague(idLeague);
    }

}
