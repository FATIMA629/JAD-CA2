package com.bookstore.storews.controller;

import com.bookstore.storews.user.User;
import com.bookstore.storews.order.Order;
import com.bookstore.storews.book.Book;
import com.bookstore.storews.sales.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesDao salesDao = new SalesDao();

    @GetMapping("/topOrders")
    public List<Order> getTopOrders(@RequestParam int limit) {
        List<Order> orders = new ArrayList<>();
        try {
            orders = salesDao.fetchTopOrders(limit);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return orders;
    }

    @GetMapping("/topCustomers")
    public List<User> getTopCustomers(@RequestParam int limit) {
        List<User> users = new ArrayList<>();
        try {
            users = salesDao.fetchTopCustomers(limit);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return users;
    }

    @GetMapping("/bookSaleByDate")
    public List<Book> getBookSaleByDate(@RequestParam String date) {
        List<Book> books = new ArrayList<>();
        try {
            books = salesDao.fetchBookSaleByDate(date);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return books;
    }

    @GetMapping("/bookSaleByPeriod")
    public List<Book> getBookSaleByPeriod(@RequestParam String start_date, @RequestParam String end_date) {
        List<Book> books = new ArrayList<>();
        try {
            books = salesDao.fetchBookSaleByPeriod(start_date, end_date);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return books;
    }

    @GetMapping("/topBooks")
    public List<Book> getTopBooks(@RequestParam int limit) {
        List<Book> books = new ArrayList<>();
        try {
            books = salesDao.fetchTopBooks(limit);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return books;
    }

    @GetMapping("/salesByGenre")
    public List<Book> getSalesByGenre(@RequestParam int genreId) {
        List<Book> books = new ArrayList<>();
        try {
            books = salesDao.fetchSalesByGenre(genreId);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return books;
    }
}
