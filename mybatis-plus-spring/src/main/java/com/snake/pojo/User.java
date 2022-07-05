package com.snake.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //自动生成get,set等方法
@AllArgsConstructor //生成构造函数
@NoArgsConstructor //生成无参构造函数
@TableName("tb_user") //声明这个实体类与哪个表对应
public class User {
    @TableId(type = IdType.AUTO) //指定id策略是数据库自增,自动生成
    private Long id;
    private String userName; //因为采用了驼峰命名,所以能匹配上数据库的user_name
    @TableField(select = false) //表明,使用通用方法时,不会返回该数据,sql语句没有查询这个数据
    private String password;
    private String name;
    private Integer age;
    @TableField("email") //解决数据库字段名,与java对象属性名不一致,并且不是驼峰时,指定这个属性与那个字段映射
    private String mail;
    @TableField(exist = false) //代表该字段在数据库中不存在,查询时不要查询这个字段,插入时也不会插入
    private String address; //数据库中不存在这个属性,如果直接使用通用方法查询,就会报错,未知的字段在数据库中
}
