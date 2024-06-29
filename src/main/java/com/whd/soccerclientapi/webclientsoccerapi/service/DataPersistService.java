package com.whd.soccerclientapi.webclientsoccerapi.service;

import com.whd.soccerclientapi.webclientsoccerapi.model.*;
import com.whd.soccerclientapi.webclientsoccerapi.pojo.EventPojo;
import com.whd.soccerclientapi.webclientsoccerapi.pojo.SeasonPojo;
import com.whd.soccerclientapi.webclientsoccerapi.pojo.TeamPojo;
import com.whd.soccerclientapi.webclientsoccerapi.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class DataPersistService {

    @Autowired
    CountryLoadService countryService;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    LeagueRepository leagueRepository;
    @Autowired
    SeasonRepository seasonRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    FootballEventRepository footballRepository;

    public void saveAllData(List<Country> countryList, ArrayList<League> allLeagues, ArrayList<TeamPojo> allTeams, ArrayList<SeasonPojo> allSeasons, ArrayList<EventPojo> allEvents) {
        saveCountries(countryList);
        saveLeagues(allLeagues);
        saveSeason(allSeasons);
        saveTeams(allTeams);
        saveEvents(allEvents);
    }

    private void saveEvents(ArrayList<EventPojo> allEvents) {

        int[] contador = {0};
        allEvents.forEach((event) -> {
            contador[0]++;
            log.info("working on event "+ contador[0] + "of "+ allEvents.size() + " - "+ event.getStrLeague() + " - " + event.getStrFilename());
            Optional<League> league = leagueRepository.findFirstByIdLeague(Long.valueOf(event.getIdLeague()));
            if (league.isPresent()) {
                Season season = seasonRepository.findFirstByLeagueAndSeasonName(league.get(), event.getStrSeason());
                Optional<Team> homeTeam = teamRepository.findFirstByIdTeam(event.getIdHomeTeam());
                Optional<Team> awayTeam = teamRepository.findFirstByIdTeam(event.getIdAwayTeam());
                LocalTime time = null;
                try {
                    time = LocalTime.parse(event.getStrTime());
                } catch (Exception e) {
                    time = null;
                }
                LocalDate date = null;
                try {
                    date = LocalDate.parse(event.getDateEvent());
                } catch (Exception e) {
                    date = null;
                }

                Optional<FootballEvent> eventOld = footballRepository.findFirstByFilename(event.getStrFilename());
                if (eventOld.isEmpty()) {
                    FootballEvent newEvent = FootballEvent.builder()
                            .idEvent(Long.valueOf(event.getIdEvent()))
                            .eventName(event.getStrEvent())
                            .filename(event.getStrFilename())
                            .awayTeam(awayTeam.orElse(null))
                            .homeTeam(homeTeam.orElse(null))
                            .dateEvent(date)
                            .timeEvent(time)
                            .season(season)
                            .league(league.get())
                            .homeScore(event.getIntHomeScore())
                            .awayScore(event.getIntAwayScore())
                            .spectators(event.getIntSpectators())
                            .round(event.getIntRound())
                            .build();
                    footballRepository.save(newEvent);
                }

            }


        });

    }

    private void saveTeams(ArrayList<TeamPojo> allTeams) {
        allTeams.forEach(team -> {
            Optional<Team> teamOptional = teamRepository.findFirstByIdTeam(Integer.parseInt(team.getIdTeam()));
            if (teamOptional.isEmpty()) {

                Team newTeam = Team.builder()
                        .teamName(team.getStrTeam())
                        .idTeam(Long.valueOf(team.getIdTeam()))
                        .idAPIfootball(team.getIdAPIfootball() != null ? Long.parseLong(team.getIdAPIfootball()) : null)
                        .sport(team.getStrSport())
                        .alternateName(team.getStrAlternate())
                        .formedYear(team.getIntFormedYear()!= null ? Long.parseLong(team.getIntFormedYear()) : null)
                        .teamShortName(team.getStrTeamShort())
                        .build();

                teamRepository.save(newTeam);
            }
        });
    }

    private void saveSeason(ArrayList<SeasonPojo> allSeasons) {
        allSeasons.forEach(seasonPojo -> {
            Optional<League> leagueOptional = leagueRepository.findFirstByIdLeague(seasonPojo.getIdLeague());
            if (leagueOptional.isPresent()){
                League league = leagueOptional.get();
                if (seasonRepository.findFirstByLeagueAndSeasonName(league,seasonPojo.getStrSeason()) == null ) {
                    Season season = Season.builder()
                            .seasonName(seasonPojo.getStrSeason())
                            .league(league)
                            .build();
                    seasonRepository.save(season);
                }
            }
        });
    }

    private void saveLeagues(ArrayList<League> allLeagues) {
        allLeagues.forEach(league -> {
            if (leagueRepository.findFirstByIdLeague(league.getIdLeague()).isEmpty()) {
                Country country = null;
                try {
                    country = countryService.getCountry(league.getStrCountry());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                league.setCountry(country);
                leagueRepository.save(league);

            }
        });
    }

    private void saveCountries(List<Country> countryList) {
        countryList.forEach(country -> {
            if (countryRepository.findFirstByNameEn(country.getNameEn()).isEmpty() ) {
                countryRepository.save(country);
            }
        });
    }
}
