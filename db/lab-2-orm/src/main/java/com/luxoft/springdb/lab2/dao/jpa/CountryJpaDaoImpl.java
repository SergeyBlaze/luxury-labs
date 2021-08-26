package com.luxoft.springdb.lab2.dao.jpa;

import com.luxoft.springdb.lab2.dao.CountryDao;
import com.luxoft.springdb.lab2.model.Country;
import com.luxoft.springdb.lab2.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CountryJpaDaoImpl extends AbstractJpaDao<CountryRepository> implements CountryDao {

	@Override
	public void save(Country country) {
		repository.save(country);
	}

	@Override
	public List<Country> getAllCountries() {
		Iterable<Country> iterable = repository.findAll();
		return StreamSupport.stream(iterable.spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public Country getCountryByName(String name) {
		return repository.findByName(name);
	}

}
