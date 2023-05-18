package com.example.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Customer{
    @TableId(value = "uid",type= IdType.AUTO)
    private Integer uid;

    private String utele;

    private String upwd;

    private String uname;

}