package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mapper.CustomerMapper;
import com.example.pojo.Customer;

import com.example.service.UserService;
import org.apache.ibatis.annotations.Update;
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
    public Customer addCustomer(Customer customer){
        QueryWrapper<Customer> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uname",customer.getUname());
        Customer customerinfo=customermapper.selectOne(queryWrapper);
        if(customerinfo==null){
            customermapper.insert(customer);
            Customer customerinfo1=customermapper.selectOne(queryWrapper);
            return customerinfo1;
        }
        else{
            return null;
        }

    }
    public Customer updateCustomer(Customer customer){
        UpdateWrapper<Customer> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("uid",customer.getUid()).set("upwd",customer.getUpwd());
        customermapper.update(null,updateWrapper);
        return customermapper.selectById(customer.getUid());
    }
}
