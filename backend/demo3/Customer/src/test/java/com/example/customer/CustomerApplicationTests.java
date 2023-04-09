package com.example.customer;

import com.example.controller.CustomerController;
import com.example.pojo.Customer;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CustomerApplicationTests {
    @Autowired
    CustomerController customerController;
    @Autowired
    UserService userService;
    @Test
    void contextLoads() {
        System.out.println(customerController.findCustomerbyId(2).getData());

    }

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
