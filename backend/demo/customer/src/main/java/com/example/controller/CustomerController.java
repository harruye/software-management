package com.example.controller;

import com.example.pojo.Customer;
import com.example.api.CommonResult;

import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "/customer/findbyid{id}")
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    UserService userService;

    @PostMapping("/findall")
    @ApiOperation(value="所有用户信息")
    public void findallController(){

        System.out.println(userService.getallCustomers());
    }
    @PostMapping("/findbyid{id}")
    @ResponseBody
    @ApiOperation(value="找到要求的用户信息")
    public CommonResult<Customer> findCustomerbyId(@PathVariable int id){

        Customer customer=userService.getCustomer(id);
        return CommonResult.success(customer);
    }

}
