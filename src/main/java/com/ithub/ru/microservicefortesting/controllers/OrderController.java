package com.ithub.ru.microservicefortesting.controllers;

import com.ithub.ru.microservicefortesting.exceptionHandler.ResourceNotFoundException;
import com.ithub.ru.microservicefortesting.models.Order;
import com.ithub.ru.microservicefortesting.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@Controller

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // API requests
    @PostMapping("/api/orders")
    public ResponseEntity<Order> newOrder(@Valid @RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/api/orders")
    @ResponseBody
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/api/order/{id}")
    @ResponseBody
    public ResponseEntity<Order> getOrder(@PathVariable("id") long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found by id: " + id));
    }

    @PutMapping("/api/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @Valid @RequestBody Order newOrder) {
        return orderService.updateById(id, newOrder)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Order not found by id {0} or cannot update this order", id)));
    }

    @DeleteMapping("/api/order/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") long id) {
        if (!orderService.existsById(id)) {
            throw new ResourceNotFoundException("Order not found by id: " + id);
        }

        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}