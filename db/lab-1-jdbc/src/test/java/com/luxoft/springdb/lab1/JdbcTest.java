package com.luxoft.springdb.lab1;

import com.luxoft.springdb.lab1.dao.CountryDao;
import com.luxoft.springdb.lab1.dao.CountryNotFoundException;
import com.luxoft.springdb.lab1.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JdbcTest {

    public static final String[][] COUNTRY_INIT_DATA = {{"Australia", "AU"},
            {"Canada", "CA"}, {"France", "FR"}, {"Hong Kong", "HK"},
            {"Iceland", "IC"}, {"Japan", "JP"}, {"Nepal", "NP"},
            {"Russian Federation", "RU"}, {"Sweden", "SE"},
            {"Switzerland", "CH"}, {"United Kingdom", "GB"},
            {"United States", "US"}};

    @Autowired
    private CountryDao countryDao;

    private List<Country> expectedCountryList = new ArrayList<Country>();
    private List<com.luxoft.springdb.lab1.model.Country> expectedCountryListStartsWithA = new ArrayList<Country>();
    private Country countryWithChangedName = new Country(1, "Russia", "RU");

    @BeforeEach
    public void setUp() throws Exception {
        initExpectedCountryLists();
    }


    @Test
    @DirtiesContext
    public void testCountryList() {
        List<Country> countryList = countryDao.getCountryList();
        assertNotNull(countryList);
        assertEquals(expectedCountryList.size(), countryList.size());
        for (int i = 0; i < expectedCountryList.size(); i++) {
            assertEquals(expectedCountryList.get(i), countryList.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testCountryListStartsWithA() {
        List<Country> countryList = countryDao.getCountryListStartWith("A");
        assertNotNull(countryList);
        assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
        for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
            assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testCountryChange() {
        countryDao.updateCountryName("RU", "Russia");
        assertEquals(countryWithChangedName, countryDao.getCountryByCodeName("RU"));
    }

    @Test
    @DirtiesContext
    public void testCountryGetName() throws CountryNotFoundException {
        assertEquals(new Country(1, "Canada", "CA"), countryDao.getCountryByName("Canada"));
    }

    @Test
    @DirtiesContext
    public void testCountryGetNameExc() throws CountryNotFoundException {
        assertThrows(CountryNotFoundException.class, () -> {
            assertEquals(countryWithChangedName, countryDao.getCountryByName("Canada Not Found"));
        });
    }

    private void initExpectedCountryLists() {
        for (int i = 0; i < COUNTRY_INIT_DATA.length; i++) {
            String[] countryInitData = COUNTRY_INIT_DATA[i];
            Country country = new Country(i, countryInitData[0], countryInitData[1]);
            expectedCountryList.add(country);
            if (country.getName().startsWith("A")) {
                expectedCountryListStartsWithA.add(country);
            }
        }
    }
}