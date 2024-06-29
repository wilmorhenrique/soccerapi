package com.whd.soccerclientapi.webclientsoccerapi.service;

import com.whd.soccerclientapi.webclientsoccerapi.model.Country;
import com.whd.soccerclientapi.webclientsoccerapi.pojo.CountryListPojo;
import com.whd.soccerclientapi.webclientsoccerapi.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryLoadService {

  private final CountryRepository countryRepository;
  @Autowired
  private TheSportsDBAPIClientService api;

  @Autowired
  public CountryLoadService(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  public void countryLoad() {

    CountryListPojo listPojo = api.getCountries();
    listPojo.getCountries().
            forEach(countryRepository::save);

  }

  public Country getCountry(String countryName) throws Exception {
    List<Country> pais = countryRepository.findByNameEn(countryName);
    if (pais == null || pais.isEmpty()) {
      throw new Exception("Nao tem pais com este nome");
    }
    return pais.get(0);
  }

}
