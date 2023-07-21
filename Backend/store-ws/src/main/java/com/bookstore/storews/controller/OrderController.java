package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.order.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @RequestMapping(method = RequestMethod.GET, path = "/getOrderById/{orderId}")
    public Order getOrderById(@PathVariable("orderId") int orderId) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.getOrderById(orderId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @RequestMapping(path = "/createOrder", consumes = "application/json", method = RequestMethod.POST)
    public boolean createOrder(@RequestBody Order order) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.createOrder(order);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getAllOrders")
    public List<Order> getAllOrders() {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.getAllOrders();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/updateOrder")
    public boolean updateOrder(@RequestBody Order order) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.updateOrder(order);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteOrder/{orderId}")
    public boolean deleteOrder(@PathVariable("orderId") int orderId) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.deleteOrder(orderId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getOrderItemsByOrderId/{orderId}")
    public List<OrderItem> getOrderItemsByOrderId(@PathVariable("orderId") int orderId) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.getOrderItemsByOrderId(orderId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/updateOrderStatus/{orderId}/{orderStatus}")
    public boolean updateOrderStatus(@PathVariable("orderId") int orderId, @PathVariable("orderStatus") String orderStatus) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.updateOrderStatus(orderId, orderStatus);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/cancelOrder/{orderId}")
    public boolean cancelOrder(@PathVariable("orderId") int orderId) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.cancelOrder(orderId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/calculateTotalPrice/{orderId}")
    public double calculateTotalPrice(@PathVariable("orderId") int orderId) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.calculateTotalPrice(orderId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return 0;
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/searchOrdersByStatus/{status}")
    public List<Order> searchOrdersByStatus(@PathVariable("status") String status) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.searchOrdersByStatus(status);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getOrdersByDateRange")
    public List<Order> getOrdersByDateRange(@RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.getOrdersByDateRange(startDate, endDate);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getTopOrders/{limit}")
    public List<Order> getTopOrders(@PathVariable("limit") int limit) {
        try {
            OrderDao orderDao = new OrderDao();
            return orderDao.getTopOrders(limit);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }
    }
}
