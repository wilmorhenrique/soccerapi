package com.whd.soccerclientapi.webclientsoccerapi.service;

import com.whd.soccerclientapi.webclientsoccerapi.model.Country;
import com.whd.soccerclientapi.webclientsoccerapi.model.League;
import com.whd.soccerclientapi.webclientsoccerapi.pojo.*;
import com.whd.soccerclientapi.webclientsoccerapi.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DataLoadService {

    @Autowired
    private TheSportsDBAPIClientService api;
    @Autowired
    private DataPersistService persistService;

    @Autowired
    private CountryRepository countryRepository;

    public String loadAllData(String countryNames) {

        CountryListPojo countryList = api.getCountries();
        ArrayList<League>  allLeagues = new ArrayList<>();
        ArrayList<TeamPojo>  allTeams = new ArrayList<>();
        ArrayList<SeasonPojo> allSeasons = new ArrayList<>();
        ArrayList<EventPojo> allEvents = new ArrayList<>();

        // TODO remove this filter when I have the no limits to call API
        List<Country> selectedCountries = countryList.getCountries().stream().filter(country -> Arrays.asList(countryNames.split(",")).contains(country.getNameEn())).collect(Collectors.toList());

        selectedCountries.forEach(country -> {

            log.info("Processando pais:" + country.getNameEn());
            List<League> leagues = loadLeagues(country);
            allLeagues.addAll(leagues.stream()
                    .filter(item -> "soccer".equalsIgnoreCase(item.getStrSport()) && !item.getStrLeague().contains("Club Friendlies"))
                    .collect(Collectors.toCollection(ArrayList::new)));

        });

        if (!allLeagues.isEmpty()) {
            allLeagues.forEach(league -> {
                log.info("Loading team from " + league.getStrLeague());
                allTeams.addAll(loadTeamsByLeague(league));

                log.info("Loading Seasons from " + league.getStrLeague());
                List<SeasonPojo> seasonsOfLeague = loadSeasonsByLeagueId(league);
                allSeasons.addAll(seasonsOfLeague);

                allEvents.addAll(loadAllEventsByLeagueAndSeason(seasonsOfLeague, league));

            });
        }

        // tenho lista de paises
        // lista de leagues (tem Id pais)
        // lista de times que possivelmente está repetida)
        // removing duplicated teams
        log.info("total de times  =" + allTeams.size());
        Set<TeamPojo> team  = new HashSet<>(allTeams);
        log.info("total de times distinct  =" + team.size());

        log.info("total de Seasons loaded  =" + allSeasons.size());
        log.info("total de Events loaded  =" + allEvents.size());

        persistService.saveAllData(selectedCountries, allLeagues, allTeams, allSeasons, allEvents);
        return "Ok, done!";
    }

    public static void waitSomeTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private Collection<? extends EventPojo> loadAllEventsByLeagueAndSeason(List<SeasonPojo> allSeasons, League league) {
        ArrayList<EventPojo> events = new ArrayList<>();
        if (allSeasons.isEmpty()) return events;

        allSeasons.forEach(item -> {
            waitSomeTime();
            log.info("Loading All events from " + league.getStrLeague() + " season "+ item.getStrSeason());
            Optional<List<EventPojo>> eventsOptional = Optional.ofNullable(api.getEventsByLeagueAndSeason(league.getIdLeague().toString(), item.getStrSeason()).getEvents());
            log.info("Found "+ eventsOptional.orElse(new ArrayList<>()).size() + "events ");
            events.addAll(eventsOptional.orElse(new ArrayList<>()));

        });
        return events;
    }

    private List<SeasonPojo> loadSeasonsByLeagueId(League league) {
        Optional<List<SeasonPojo>> seasonsOptional = Optional.ofNullable(api.getSeasonsByLeague(league.getIdLeague().toString()).getSeasons());

        List<SeasonPojo> seasons = seasonsOptional.orElse(new ArrayList<>());
        seasons.forEach(item -> item.setIdLeague(league.getIdLeague()));
        return  seasons;
    }

    private List<TeamPojo> loadTeamsByLeague(League league) {
        return (api.getTeamsByLeague(league.getStrLeague())).getTeams();
    }

    private List<League> loadLeagues(Country country) {
        LeagueListPojo leagues = api.getLeagues(country.getNameEn());
        return leagues.getLeagues();

    }
}
