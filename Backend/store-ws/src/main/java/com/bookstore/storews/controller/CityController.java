package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.city.*;
import java.util.ArrayList;

@RestController
@RequestMapping("city")
public class CityController {

	@RequestMapping(method = RequestMethod.GET, path = "/getCity/{countryid}")
    public ArrayList<City> getCity(@PathVariable("countryid") int countryid) {
        ArrayList<City> cityList = new ArrayList<>();
        try {
            CityDao cityDao = new CityDao();
            cityList = cityDao.getCity(countryid);
            System.out.print(cityList);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return cityList;
    }
}
