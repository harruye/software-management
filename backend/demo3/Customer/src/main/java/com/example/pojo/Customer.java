package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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