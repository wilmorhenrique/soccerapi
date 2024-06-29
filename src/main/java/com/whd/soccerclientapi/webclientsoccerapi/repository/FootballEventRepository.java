package com.whd.soccerclientapi.webclientsoccerapi.repository;

import com.whd.soccerclientapi.webclientsoccerapi.model.FootballEvent;
import com.whd.soccerclientapi.webclientsoccerapi.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FootballEventRepository extends JpaRepository<FootballEvent, Long> {

    Optional<FootballEvent> findFirstByFilename(String filename);

}
