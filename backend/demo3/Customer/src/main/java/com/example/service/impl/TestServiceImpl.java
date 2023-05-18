package com.example.service.impl;

import com.example.mapper.CustomerMapper;
import com.example.pojo.Customer;
import com.example.service.TestService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

public class TestServiceImpl implements TestService {

    @Resource
    CustomerMapper customermapper;


    public List<Customer> getallCustomers(){
        return customermapper.selectList(null);
    }
}
