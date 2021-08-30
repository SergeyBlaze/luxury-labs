package com.luxoft.springdb.lab2.rest.dto;

import com.luxoft.springdb.lab2.model.Country;

public class CountryDTO {

    private Integer id;

    private String name;

    private String codeName;

    public CountryDTO() {

    }

    public CountryDTO(Integer id, String name, String codeName) {
        this.id = id;
        this.name = name;
        this.codeName = codeName;
    }

    public static CountryDTO mapFromCountry(Country country) {
        return new CountryDTO(
                country.getId(),
                country.getName(),
                country.getCodeName()
        );
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Country mapToCountry() {
        return new Country(id, name, codeName);
    }

}
