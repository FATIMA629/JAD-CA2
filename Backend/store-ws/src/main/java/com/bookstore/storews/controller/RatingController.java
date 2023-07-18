package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.rating.*;
import java.util.ArrayList;

@RestController
@RequestMapping("ratings")
public class RatingController {

	@RequestMapping(method = RequestMethod.GET, path = "/getAllRatingsForBook/{bookid}")
    public ArrayList<Rating> getAllRatingsForBook(@PathVariable("bookid") int bookid) {
        ArrayList<Rating> ratingList = new ArrayList<>();
        try {
            RatingDao ratingDao = new RatingDao();
            ratingList = ratingDao.getRatingsForBook(bookid);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return ratingList;
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/getAverageRatingForBook/{bookid}")
    public double getAverageRatingForBook(@PathVariable("bookid") int bookid) {
        double avgRating = 0.0;
        try {
        	RatingDao ratingDao = new RatingDao();
            avgRating = ratingDao.getAverageRatingForBook(bookid);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return avgRating;
    }
	
	@RequestMapping(method = RequestMethod.PUT, path = "/incrementHelpfulCount/{ratingid}")
	public boolean incrementHelpfulCount(@PathVariable int ratingid) {
		boolean updated = false;
		try {
			RatingDao ratingDao = new RatingDao();
			updated = ratingDao.incrementHelpfulCount(ratingid);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		return updated;
	}
	
	@RequestMapping(path = "/addRating", consumes = "application/json", method = RequestMethod.POST)
    public boolean addRating(@RequestBody Rating rating) {
        boolean created = false;
        try {
        	RatingDao ratingDao = new RatingDao();
            created = ratingDao.addRating(rating);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return created;
    }
}
