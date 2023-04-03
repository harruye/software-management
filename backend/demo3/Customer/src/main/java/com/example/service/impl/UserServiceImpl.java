package com.example.service.impl;


import com.example.mapper.CustomerMapper;
import com.example.pojo.Customer;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  UserServiceImpl implements UserService {

    @Autowired
    CustomerMapper customermapper;
    public List<Customer> getallCustomers(){

        return customermapper.selectList(null);

    }
    public Customer getCustomer(int id){
        return customermapper.selectById(id);
    }
}
