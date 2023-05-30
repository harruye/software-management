package com.example.backend.controller;


import com.example.backend.api.CommonResult;
import com.example.backend.pojo.Customer;
import com.example.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    @Autowired
    UserService userService;

    @PostMapping("/findall")
    @ApiOperation(value="所有用户信息")
    public void findallController(){

        System.out.println(userService.getallCustomers());
    }
    @PostMapping("/findbyid{id}")
    @ApiOperation(value="找到要求的用户信息")
    public CommonResult<Customer> findCustomerbyId(@PathVariable int id){

        Customer customer=userService.getCustomer(id);
        return CommonResult.success(customer);
    }

    @PostMapping("/finduserbyId")

    public CommonResult<Customer> findCustomerbyId(@RequestBody Customer customer){
        Customer res=userService.getCustomer(customer.getUid());
        return CommonResult.success(res);
    }

    @PostMapping("/adduser")

    public CommonResult<Customer> addCustomer(@RequestBody Customer customer){

        Customer res=userService.addCustomer(customer);

        return CommonResult.success(res);
    }

    @PostMapping("/updateuser")

    public CommonResult<Customer> updateCustomer(@RequestBody Customer customer){
        return CommonResult.success(userService.updateCustomer(customer));
    }

    @PostMapping("/login")

    public CommonResult<Customer> login(@RequestBody Customer request){
        //System.out.println("accept!");
        Customer customer=userService.getCustomer(request.getUid());
        if(customer!=null) {
            if (customer.getUpwd().equals(request.getUpwd())) {

                return CommonResult.success(customer);
            } else {
                return CommonResult.failed();
            }
        }
        return CommonResult.failed();
    }

}
