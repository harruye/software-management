package com.example.backend;

import com.example.backend.controller.CustomerController;
import com.example.backend.mapper.CustomerMapper;
import com.example.backend.pojo.Customer;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    CustomerController customerController;
    @Autowired
    UserService userService;
    @Autowired
    CustomerMapper customerMapper;

    @Test
    @Transactional
    @Rollback
    void check_addUsers(){
        Customer customer=new Customer(null,"13801967038","123456","程心");
        System.out.println(customerController.addCustomer(customer).getData());
    }

    @Test
    void get_all(){
        System.out.println(userService.getallCustomers());
    }

    @Test
    @Transactional
    @Rollback
    void check_updateUsers(){
        Customer customer=new Customer(2,"13801967038","123456","程心");
        System.out.println(customerController.updateCustomer(customer).getData());
    }



}
