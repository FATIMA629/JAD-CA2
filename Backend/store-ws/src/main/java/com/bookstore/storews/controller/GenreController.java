package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.genre.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("genres")
public class GenreController {

	@RequestMapping(method = RequestMethod.GET, path = "/getGenre/{genreid}")
	public Genre getGenre(@PathVariable("genreid") int genreid) {
		Genre genre = null;
		try {
			GenreDao genreDao = new GenreDao();
			genre = genreDao.getGenreById(genreid);
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

	@GetMapping("/{genreId}")
	public Genre getGenreById(@PathVariable int genreId) {
		try {
			GenreDao genreDao = new GenreDao();
			return genreDao.getGenreById(genreId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/popular/{limit}")
	public Map<Genre, Integer> getPopularGenres(@PathVariable int limit) {
		try {
			GenreDao genreDao = new GenreDao();
			return genreDao.getPopularGenres(limit);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/toprated/{limit}")
	public List<Genre> getTopRatedGenres(@PathVariable int limit) {
		try {
			GenreDao genreDao = new GenreDao();
			return genreDao.getTopRatedGenres(limit);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/name/{genreId}")
	public String getGenreNameById(@PathVariable int genreId) {
		try {
			GenreDao genreDao = new GenreDao();
			return genreDao.getGenreNameById(genreId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
