package com.ithub.ru.microservicefortesting.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

@Getter
@Setter
@Entity
@Table(name = "testing_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Customer name can't be null")
    private String name;

    @NotNull(message = "Customer surname can't be null")
    private String surname;

    @Positive(message = "Age must be positive")
    private int age;

    public Customer(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Customer() {}

    public boolean equalsFields(Customer o) {
        return this.name.equals(o.getName()) &&
                this.surname.equals(o.getSurname()) &&
                this.age == o.getAge() &&
                this.id == o.getId();
    }

    @Override
    public String toString() {
        return "{id = "+id+", name = "+name+", surname = "+surname+", age = "+age+"}";
    }
}
