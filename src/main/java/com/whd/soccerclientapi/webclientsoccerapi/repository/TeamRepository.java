package com.whd.soccerclientapi.webclientsoccerapi.repository;

import com.whd.soccerclientapi.webclientsoccerapi.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findFirstByIdTeam(int idTeam);
}
