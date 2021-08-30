package com.luxoft.springdb.lab2.rest;

import com.luxoft.springdb.lab2.dao.CountryDao;
import com.luxoft.springdb.lab2.model.Country;
import com.luxoft.springdb.lab2.rest.dto.CountryDTO;
import com.luxoft.springdb.lab2.rest.exception.NotFoundRestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/country")
public class CountryRestController {

    @Autowired
    private CountryDao dao;

    @GetMapping(path = "/")
    public CountryDTO getByName(@RequestParam String name) {
        return Optional.ofNullable(dao.getCountryByName(name))
                .map(CountryDTO::mapFromCountry)
                .orElseThrow(NotFoundRestException::new);
    }

    @GetMapping(path = "/all")
    public List<CountryDTO> findAll() {
        return dao.getAllCountries()
                .stream()
                .map(CountryDTO::mapFromCountry)
                .collect(Collectors.toList());
    }

    @PutMapping(path = "/")
    public void save(@RequestBody CountryDTO country) {
        Country toSave = country.mapToCountry();
        dao.save(toSave);
    }

}
