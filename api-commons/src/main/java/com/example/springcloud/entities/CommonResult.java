package com.example.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//返回前端的通用的json封装体实体串
@Data
@NoArgsConstructor  //无参
@AllArgsConstructor //全参
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    //只有两个参数的构造方法
    public CommonResult(Integer code,String message){
        //调用自身类的全参构造方法
        this(code,message,null);
    }
}
