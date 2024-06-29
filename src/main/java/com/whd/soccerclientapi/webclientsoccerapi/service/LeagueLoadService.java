package com.whd.soccerclientapi.webclientsoccerapi.service;

import com.whd.soccerclientapi.webclientsoccerapi.model.Country;
import com.whd.soccerclientapi.webclientsoccerapi.model.League;
import com.whd.soccerclientapi.webclientsoccerapi.pojo.LeagueListPojo;
import com.whd.soccerclientapi.webclientsoccerapi.repository.CountryRepository;
import com.whd.soccerclientapi.webclientsoccerapi.repository.LeagueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LeagueLoadService {

  @Autowired
  CountryLoadService countryService;
  private final LeagueRepository repository;
  @Autowired
  private TheSportsDBAPIClientService api;
  private final CountryRepository countryRepository;


  @Autowired
  public LeagueLoadService(LeagueRepository repository, CountryRepository countryRepository) {
    this.repository = repository;
    this.countryRepository = countryRepository;

  }


  public String  updateLeagues() throws Exception {
    List<League> leagues = repository.findAll();
    leagues = leagues.stream().filter(league -> league.getCountry() == null).collect(Collectors.toCollection(ArrayList::new));
    for (League league : leagues) {
      log.info("Loading " + league.getStrLeague());
      League apiLeague = api.getLeagueById(league.getIdLeague().toString());
      Country country = countryService.getCountry(apiLeague.getStrCountry());
      league.setCountry(country);
      repository.save(league);
      DataLoadService.waitSomeTime();
    }
    return "Legues updated!";
  }

  public LeagueListPojo leagueLoadByCountry(String countryName) throws Exception {

    Country country = countryService.getCountry(countryName);

    List<League> leagues = new ArrayList<>();
    LeagueListPojo listPojo = api.getLeagues(countryName);


    listPojo.getLeagues().
            forEach(item -> {
              item.setCountry(country);
              repository.save(item);
            });

    return listPojo;
  }


}
