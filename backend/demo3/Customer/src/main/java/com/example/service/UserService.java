package com.example.service;

import com.example.pojo.Customer;

import java.util.List;

public interface UserService {
    public List<Customer> getallCustomers();
    public Customer getCustomer(int id);
}
