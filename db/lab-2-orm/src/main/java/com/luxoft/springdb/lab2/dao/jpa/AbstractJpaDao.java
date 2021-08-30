package com.luxoft.springdb.lab2.dao.jpa;

import org.springframework.beans.factory.annotation.Autowired;

public class AbstractJpaDao<R> {

	R repository;

	@Autowired
	public void setRepository(R repository) {
		this.repository = repository;
	}

}