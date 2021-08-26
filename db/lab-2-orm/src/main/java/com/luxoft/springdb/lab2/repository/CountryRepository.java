package com.luxoft.springdb.lab2.repository;

import com.luxoft.springdb.lab2.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

    Country findByName(String name);

}
