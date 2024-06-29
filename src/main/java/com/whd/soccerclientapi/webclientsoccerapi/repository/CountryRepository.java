package com.whd.soccerclientapi.webclientsoccerapi.repository;

import com.whd.soccerclientapi.webclientsoccerapi.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    public List<Country> findByNameEn(String name_en);
    public Optional<Country> findFirstByNameEn(String name_en);
}
