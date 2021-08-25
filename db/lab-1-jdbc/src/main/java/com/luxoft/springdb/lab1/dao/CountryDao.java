package com.luxoft.springdb.lab1.dao;

import com.luxoft.springdb.lab1.model.Country;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("countryDao")
public class CountryDao extends JdbcDaoSupport {

    private static final String GET_ALL_COUNTRIES_SQL = "select * from country";
    private static final String GET_COUNTRIES_BY_NAME_SQL = "select * from country where name like :name";
    private static final String GET_COUNTRY_BY_NAME_SQL = "select * from country where name = :name";
    private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "select * from country where code_name = :code_name";

    private static final String UPDATE_COUNTRY_NAME_SQL_1 = "update country SET name = :name";
    private static final String UPDATE_COUNTRY_NAME_SQL_2 = " where code_name = :code_name";

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CountryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Country> getCountryList() {
        return jdbcTemplate.query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);
    }

    public List<Country> getCountryListStartWith(String name) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
                "name", name + "%");
        return jdbcTemplate.query(GET_COUNTRIES_BY_NAME_SQL,
                sqlParameterSource, COUNTRY_ROW_MAPPER);
    }

    public void updateCountryName(String codeName, String newCountryName) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("code_name", codeName)
                .addValue("name", newCountryName);
        jdbcTemplate.update(UPDATE_COUNTRY_NAME_SQL_1 + UPDATE_COUNTRY_NAME_SQL_2, source);
    }

    public Country getCountryByCodeName(String codeName) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("code_name", codeName);
        return jdbcTemplate.query(GET_COUNTRY_BY_CODE_NAME_SQL, source, COUNTRY_ROW_MAPPER).stream()
                .findFirst().orElse(null);
    }

    public Country getCountryByName(String name)
            throws CountryNotFoundException {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("name", name);
        return jdbcTemplate.query(GET_COUNTRY_BY_NAME_SQL, source, COUNTRY_ROW_MAPPER).stream()
                .findFirst().orElseThrow(CountryNotFoundException::new);
    }

}
