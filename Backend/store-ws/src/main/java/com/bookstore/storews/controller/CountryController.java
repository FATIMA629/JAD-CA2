package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.country.*;
import java.util.ArrayList;

@RestController
@RequestMapping("country")
public class CountryController {

	@RequestMapping(method = RequestMethod.GET, path = "/getCountry")
    public ArrayList<Country> getCountry() {
        ArrayList<Country> countryList = new ArrayList<>();
        try {
            CountryDao countryDao = new CountryDao();
            countryList = countryDao.getCountry();
            System.out.print(countryList);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return countryList;
    }
}
