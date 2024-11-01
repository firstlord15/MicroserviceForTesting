package com.ithub.ru.microservicefortesting.utils;

import com.ithub.ru.microservicefortesting.models.Customer;
import com.ithub.ru.microservicefortesting.models.Order;
import com.ithub.ru.microservicefortesting.repositories.CustomerRepository;
import com.ithub.ru.microservicefortesting.repositories.OrderRepository;
import com.ithub.ru.microservicefortesting.models.OrderStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(OrderRepository orderRepository, CustomerRepository customerRepository) {
        return args -> {
            if (orderRepository.count() == 0 && customerRepository.count() == 0) {

                // Сохраняем клиентов, связывая их с заказами
                Customer customer1 = customerRepository.save(new Customer("Alice", "Smith", 30));
                Customer customer2 = customerRepository.save(new Customer("Bob", "Johnson", 42));
                Customer customer3 = customerRepository.save(new Customer("Charlie", "Brown", 35));
                Customer customer4 = customerRepository.save(new Customer("Diana", "White", 27));

                orderRepository.save(new Order("Laptop", 7, new BigDecimal("145000.00"), OrderStatus.CREATED, LocalDate.now(), customer1));
                orderRepository.save(new Order("Phone", 25, new BigDecimal("60399.00"), OrderStatus.SHIPPED, LocalDate.of(2024, 10, 20), customer2));
                orderRepository.save(new Order("Computer", 10, new BigDecimal("198000.00"), OrderStatus.DELIVERED, LocalDate.now(), customer3));
                orderRepository.save(new Order("Mouse", 15, new BigDecimal("4999.00"), OrderStatus.CREATED, LocalDate.now(), customer4));
            }
        };
    }
}
