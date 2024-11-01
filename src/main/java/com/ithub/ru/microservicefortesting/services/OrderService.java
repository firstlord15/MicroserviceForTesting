package com.ithub.ru.microservicefortesting.services;

import com.ithub.ru.microservicefortesting.exceptionHandler.ResourceNotFoundException;
import com.ithub.ru.microservicefortesting.models.Customer;
import com.ithub.ru.microservicefortesting.models.Order;
import com.ithub.ru.microservicefortesting.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;

    public OrderService(OrderRepository orderRepository, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
    }

    public boolean existsById(long id) {
        return orderRepository.existsById(id);
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> updateById(long id, Order newOrder) {
        isCustomerInDB(newOrder);
        return orderRepository.findById(id).map(order -> {
            order.setOrderDate(newOrder.getOrderDate());
            order.setStatus(newOrder.getStatus());
            order.setProduct(newOrder.getProduct());
            order.setQuantity(newOrder.getQuantity());
            order.setPrice(newOrder.getPrice());
            order.setCustomer(newOrder.getCustomer());
            return orderRepository.save(order);
        });
    }

    public Order saveOrder(Order order) {
        isCustomerInDB(order);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public void isCustomerInDB(Order order) {
        long customer_id = order.getCustomer().getId();
        if (!customerService.existsById(customer_id)){
            throw new ResourceNotFoundException("Customer not found with ID: " + customer_id);
        }

        Optional<Customer> customerFromDB = customerService.getCustomerById(customer_id);
        if (customerFromDB.isEmpty() || !customerFromDB.get().equalsFields(order.getCustomer())) {
            throw new ResourceNotFoundException("Incorrect Customer data for ID: " + customer_id);
        }
    }
}
