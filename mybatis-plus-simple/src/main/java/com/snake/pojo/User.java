package com.snake.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //自动生成get,set等方法
@AllArgsConstructor //生成构造函数
@NoArgsConstructor //生成无参构造函数
@TableName("tb_user") //声明这个实体类与哪个表对应
public class User {
    private Integer id;
    private String userName;
    private String password;
    private String name;
    private Integer age;
    private String email;
}
