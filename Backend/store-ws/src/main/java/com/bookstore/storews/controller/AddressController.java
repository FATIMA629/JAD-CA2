package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.address.*;
import java.util.ArrayList;

@RestController
@RequestMapping("address")
public class AddressController {

	@RequestMapping(method = RequestMethod.GET, path = "/getAddressByUserId/{userid}")
    public ArrayList<Address> getAddressByUserId(@PathVariable("userid") int userid) {
        ArrayList<Address> addressList = new ArrayList<>();
        try {
            AddressDao addressDao = new AddressDao();
            addressList = addressDao.getAddressByUserId(userid);
            System.out.print(addressList);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return addressList;
    }
	
	@RequestMapping(path = "/createAddress", consumes = "application/json", method = RequestMethod.POST)
    public boolean createAddress(@RequestBody Address address) {
        boolean created = false;
        try {
        	AddressDao addressDao = new AddressDao();
            created = addressDao.createAddress(address);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return created;
    }
    

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteAddress/{addressid}")
    public boolean deleteBook(@PathVariable("addressid") String addressid) {
        boolean deleted = false;
        try {
        	AddressDao addressDao = new AddressDao();
            deleted = addressDao.deleteAddress(addressid);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return deleted;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/updateAddress")
    public boolean updateAddress(@RequestBody Address address) {
        boolean updated = false;
        try {
        	AddressDao addressDao = new AddressDao();
            updated = addressDao.updateAddress(address);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return updated;
    }
}
