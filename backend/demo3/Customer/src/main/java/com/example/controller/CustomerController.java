package com.example.controller;

import com.example.api.CommonResult;
import com.example.pojo.Customer;
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

    @PostMapping("/finduserbyId")
    @ResponseBody
    public CommonResult<Customer> findCustomerbyId(@RequestBody Customer customer){
        Customer res=userService.getCustomer(customer.getUid());
        return CommonResult.success(res);
    }

    @PostMapping("/adduser")
    @ResponseBody
    public CommonResult<Customer> addCustomer(@RequestBody Customer customer){
        customer.setUid(null);
        Customer res=userService.addCustomer(customer);
        return CommonResult.success(res);
    }

    @PostMapping("/updateuser")
    @ResponseBody
    public CommonResult<Customer> updateCustomer(@RequestBody Customer customer){
        return CommonResult.success(userService.updateCustomer(customer));
    }

    @PostMapping("/login")
    @ResponseBody
    public CommonResult<Customer> login(@RequestBody Customer request){
        Customer customer=userService.getCustomer(request.getUid());
        if(customer.getUid().equals(request.getUid())){
            return CommonResult.success(customer);
        }
        else {
            return null;
        }
    }

}
