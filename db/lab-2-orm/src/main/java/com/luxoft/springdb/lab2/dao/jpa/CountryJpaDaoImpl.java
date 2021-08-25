package com.luxoft.springdb.lab2.dao.jpa;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.luxoft.springdb.lab2.dao.CountryDao;
import com.luxoft.springdb.lab2.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

	@Override
	public void save(Country country) {

		EntityManager em = entityManagerFactory.createEntityManager();
		if (em != null) {
			EntityTransaction tx = em.getTransaction();
			tx.begin();

			if (!em.contains(country)) {
				em.persist(country);
			} else {
				em.merge(country);
			}

			tx.commit();
			em.close();
		}
	}

	@Override
	public List<Country> getAllCountries() {
		EntityManager em = entityManagerFactory.createEntityManager();
		if (em != null) {
			List<Country> countryList =
					em.createQuery("select e from Country e", Country.class)
							.getResultList();
			em.close();
			return countryList;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Country getCountryByName(String name) {
		EntityManager em = entityManagerFactory.createEntityManager();
		if (em != null) {
			try {
				Country country = em.createQuery("select e from Country e where e.name = :name", Country.class)
						.setParameter("name", name)
						.getSingleResult();
				em.close();
				return country;
			} catch (NoResultException e) {
				return null;
			}
		} else {
			return null;
		}
	}

}
