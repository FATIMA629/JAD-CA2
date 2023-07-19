package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.genre.*;
import java.util.ArrayList;

@RestController
@RequestMapping("genres")
public class GenreController {
	
	@RequestMapping(method = RequestMethod.GET, path = "/getGenre/{genreid}")
    public String getGenre(@PathVariable("genreid") int genreid) {
        String genre = null;
        try {
            GenreDao genreDao = new GenreDao();
            genre = genreDao.getGenre(genreid);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return genre;
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/getAllGenres")
    public ArrayList<Genre> getAllGenres() {
        ArrayList<Genre> genreList = new ArrayList<>();
        try {
        	GenreDao genreDao = new GenreDao();
            genreList = genreDao.getAllGenres();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return genreList;
    }
}
