package com.whd.soccerclientapi.webclientsoccerapi.repository;

import com.whd.soccerclientapi.webclientsoccerapi.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

    Optional<League> findFirstByIdLeague(Long idLeague);
}
