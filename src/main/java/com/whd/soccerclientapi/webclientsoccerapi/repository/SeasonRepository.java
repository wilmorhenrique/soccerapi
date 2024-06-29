package com.whd.soccerclientapi.webclientsoccerapi.repository;

import com.whd.soccerclientapi.webclientsoccerapi.model.League;
import com.whd.soccerclientapi.webclientsoccerapi.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

    @Query("SELECT s FROM Season s WHERE s.league = :league AND s.seasonName = :seasonName")
    Season findFirstByLeagueAndSeasonName(League league, String seasonName);
}
