package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.book.*;
import com.bookstore.storews.cart.*;

import java.util.ArrayList;

@RestController
@RequestMapping("carts")
public class CartController {

    @RequestMapping(method = RequestMethod.GET, path = "/getAllCartItems/{userid}")
    public ArrayList<Book> getAllBooksInCart(@PathVariable("userid") int userid) {
        ArrayList<Book> cartList = new ArrayList<>();
        try {
            CartDao cartDao = new CartDao();
            cartList = cartDao.getAllBooksInCart(userid);
            System.out.print(cartList);
        } catch (Exception e) {
        	System.out.print("Hello");
            System.out.println("Error: " + e);
        }
        return cartList;
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/getQuantity/{userid}/{bookid}")
    public int getQuantity(@PathVariable("userid") int userid, @PathVariable("bookid") int bookid) {
        int quantity = 0;
        try {
            CartDao cartDao = new CartDao();
            quantity = cartDao.getQuantity(userid, bookid);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return quantity;
    }
    
    @RequestMapping(path = "/addToCart/{userid}/{bookid}/{quantity}", consumes = "application/json", method = RequestMethod.POST)
    public boolean addToCart(@PathVariable("userid") int userid, @PathVariable("bookid") int bookid, @PathVariable("userid") int quantity) {
        boolean created = false;
        try {
            CartDao cartDao = new CartDao();
            created = cartDao.addToCart(userid, bookid, quantity);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return created;
    }
    
    @RequestMapping(path = "/deleteFromCart/{userid}/{bookid}", method = RequestMethod.DELETE)
	public boolean deleteUser(@PathVariable("userid") int userid, @PathVariable("bookid") int bookid) {
    	boolean deleted = false;
		try {
			CartDao cartDao = new CartDao();
			deleted = cartDao.deleteFromCart(userid, bookid);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		return deleted;
	}
    
    

}
