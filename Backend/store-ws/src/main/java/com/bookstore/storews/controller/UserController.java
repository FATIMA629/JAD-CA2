package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.user.*;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

	@RequestMapping(method = RequestMethod.GET, path = "/getAllUsers")
	public List<User> getAllUsers() {
		List<User> userList = null;
		try {
			UserDao userDao = new UserDao();
			userList = userDao.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getUserByUsername/{username}")
	public User getUserByUsername(@PathVariable("username") String username) {
		User user = null;
		try {
			UserDao userDao = new UserDao();
			user = userDao.getUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getUserById/{userId}")
	public User getUserById(@PathVariable("userId") String userId) {
		User user = null;
		try {
			UserDao userDao = new UserDao();
			user = userDao.getUserById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getUserByEmail/{email}")
	public User getUserByEmail(@PathVariable("email") String email) {
		User user = null;
		try {
			UserDao userDao = new UserDao();
			user = userDao.getUserByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@RequestMapping(path = "/createUser", consumes = "application/json", method = RequestMethod.POST)
	public void createUser(@RequestBody User user) {
		try {
			UserDao userDao = new UserDao();
			userDao.createUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/updateUser", consumes = "application/json", method = RequestMethod.PUT)
	public void updateUser(@RequestBody User user) {
		try {
			UserDao userDao = new UserDao();
			userDao.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteUser/{userId}")
	public boolean deleteUser(@PathVariable("userId") String userId) {
		boolean deleted = false;
		try {
			UserDao userDao = new UserDao();
			deleted = userDao.deleteUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deleted;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getTotalUsers")
	public int getTotalUsers() {
		int totalUsers = 0;
		try {
			UserDao userDao = new UserDao();
			totalUsers = userDao.getTotalUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalUsers;
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public User loginUser(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user = null;
		try {
			UserDao userDao = new UserDao();
			user = userDao.loginUser(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
