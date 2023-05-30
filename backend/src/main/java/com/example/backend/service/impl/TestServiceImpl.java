package com.example.backend.service.impl;

import com.example.backend.mapper.CustomerMapper;
import com.example.backend.pojo.Customer;
import com.example.backend.service.TestService;
import jakarta.annotation.Resource;


import java.util.List;

public class TestServiceImpl implements TestService {

    @Resource
    CustomerMapper customermapper;


    public List<Customer> getallCustomers(){
        return customermapper.selectList(null);
    }
}
