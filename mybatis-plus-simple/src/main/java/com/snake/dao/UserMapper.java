package com.snake.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake.pojo.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> { //继承时要传入对应的实体
    List<User> findAll();
}
