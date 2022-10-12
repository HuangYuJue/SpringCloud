package com.example.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor //全参
@NoArgsConstructor  //空参
//注意接上序列化，序列化在分布式部署会用得到
public class Payment implements Serializable {
    private Long id;    //因为在表中id的类型是bigint，所以要用Long
    private String serial;
}
