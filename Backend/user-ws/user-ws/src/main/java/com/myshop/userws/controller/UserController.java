package com.myshop.userws.controller;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import com.myshop.userws.dbaccess.*;

@RestController

public class UserController {

	@RequestMapping(method = RequestMethod.GET, path = "/getUser/{uid}")
	public User getUser(@PathVariable("uid") String uid) {
		User user = new User();

		try {
			UserDAO db = new UserDAO();
			user = db.getUserDetails(uid);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return user;
	}

	/**
	 * Get all users from the database.
	 *
	 * @return A list of User objects containing details of all users.
	 */
	@GetMapping("/getAllUsers")
	public ArrayList<User> getAllUsers() {
		ArrayList<User> userList = new ArrayList<>();
		try {
			UserDAO db = new UserDAO();
			userList = db.listAllUsers();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return userList;
	}

	@RequestMapping(path = "/createUser", consumes = "application/json", method = RequestMethod.POST)
	public int createUser(@RequestBody User user) {
		int rec = 0;
		try {
			UserDAO db = new UserDAO();
			String uId = user.getUserid();
			System.out.print("…..inside UserController…..uId:" + uId);
			int uAge = user.getAge();
			String uGender = user.getGender();
			rec = db.insertUser(uId, uAge, uGender);
			System.out.print("… ..done create user." + rec);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		return rec; // <-- using the default ResponseBody with custom status from Spring
	}

	@RequestMapping(path = "/updateUser/{uid}", consumes = "application/json", method = RequestMethod.PUT)
	public int updateUser(@PathVariable String uid, @RequestBody User user) {
		// return "updateUser () was being called!".
		int rec = 0;
		try {
			UserDAO db = new UserDAO();
			rec = db.updateUser(uid, user);
			System.out.print(". ..in UserController-done update user. ." + rec);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		return rec;
	}

	@RequestMapping(path = "/deleteUser/{uid}", method = RequestMethod.DELETE)
	public int deleteUser(@PathVariable String uid) {
		// return "deleteUser () was being called!";
		int rec = 0;
		try {
			UserDAO db = new UserDAO();
			rec = db.deleteUser(uid);
			System.out.print("...in UserController-done deleting user.." + rec);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		return rec;
	}
}
