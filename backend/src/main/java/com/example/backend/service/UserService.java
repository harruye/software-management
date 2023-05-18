package com.example.backend.service;

import com.example.backend.pojo.Customer;


import java.util.List;

public interface UserService {
    public List<Customer> getallCustomers();
    public Customer getCustomer(int id);
    public Customer addCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
}
