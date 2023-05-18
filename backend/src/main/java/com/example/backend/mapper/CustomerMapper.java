package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.backend.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
}
