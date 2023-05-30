package com.example.backend;

import com.example.backend.api.CommonResult;
import com.example.backend.api.ResultCode;
import com.example.backend.controller.CustomerController;
import com.example.backend.pojo.Customer;
import com.example.backend.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserfunctionTests {
    @Autowired
    CustomerController customerController;
    @Autowired
    UserService userService;

    @Test
    @Transactional
    @Rollback
    void check_addUsers_1(){
        Customer customer=new Customer(null,"13801967038","123456","史周胤");
        System.out.println(customerController.addCustomer(customer).toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),customerController.addCustomer(customer).getCode());
    }

    @Test
    @Transactional
    @Rollback
    void check_addUsers_2(){
        Customer customer=new Customer(null,"13801964348","123756","刘浩洋");
        System.out.println(customerController.addCustomer(customer).toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),customerController.addCustomer(customer).getCode());
    }
    @Test
    @Transactional
    @Rollback
    void check_addUsers_3(){
        Customer customer=new Customer(null,"13801967838","123456","蔡仕鹏");
        System.out.println(customerController.addCustomer(customer).toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),customerController.addCustomer(customer).getCode());
    }

    @Test
    @Transactional
    @Rollback
    void check_login_1(){
        Customer customer=new Customer(22,"13801987038","szy19991006","史周胤");
        System.out.println(customerController.login(customer).toString());
        Assert.assertEquals(ResultCode.SUCCESS.getCode(),customerController.login(customer).getCode());
    }
    @Test
    @Transactional
    @Rollback
    void check_login_2(){
        Customer customer=new Customer(2,"1380197038","1234565",null);
        System.out.println(customerController.login(customer).toString());
        Assert.assertEquals(ResultCode.FAILED.getCode(),customerController.login(customer).getCode());
    }

    @Test
    @Transactional
    @Rollback
    void check_login_3(){
        Customer customer=new Customer(3,"138019656038","123434345",null);
        System.out.println(customerController.login(customer).toString());
        Assert.assertEquals(ResultCode.FAILED.getCode(),customerController.login(customer).getCode());
    }


}
