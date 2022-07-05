package com.snake.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.snake.mapper.UserMapper;
import com.snake.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestUserMapper {
    //注入mapper
    @Autowired
    private UserMapper userMapper;
    /**
     * 2022年7月4日15:56:14
     *  使用Spring + mybatisPlus
     */
    @Test
    public void test1(){
        //因为使用注解指定了password不能被查询,所以查询出的password为null
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试 通用方法中的 insert()方法
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setUserName("snake");
        user.setAge(22);
        user.setPassword("1234");
        user.setMail("snake@snake.com"); //该属性与数据库字段名不相同,但是我们使用了注解去映射了,所以可以插入到数据中
        user.setName("斯内克");
        user.setAddress("北京");  //数据库中不存在的字段, 使用通用方法时,因为我们使用注解,表明了该字段不存在,所以sql语句没有插入该字段
        //调用插入方法,返回影响行数
        int effectLine = userMapper.insert(user);
        System.out.println("影响行数=>"+effectLine);
        //进行插入方法后,会自动帮我们,返回插入后的主键,并赋值给user
        //但是mybatis默认的主键策略,如果传递的id为null,
        // 它会自动帮我们使用一个策略生成一个主键,所以我们应该到User类里面使用注解改变下策略,
        // 注意如果之前使用默认策略了,则表的自增初始值被改变了,我们应该到数据库里面去设置回去
        System.out.println("生成的主键id=>"+user.getId());
    }

    /**
     * 测试通过使用通用方法updateById
     * 修改用户信息
     */
    @Test
    public void testUpdateById(){
        User user = new User();
        user.setId(1L); //设置查询条件的id
        user.setPassword("66666"); //设置用户密码为66666
        user.setAge(20);    //设置年龄为20
        //根据上面条件生成的sql语句:  UPDATE tb_user SET password=?, age=? WHERE id=?
        int effectLine = userMapper.updateById(user);
        System.out.println("影响行数:"+effectLine);
    }

    /**
     * 测试使用Update方法,更新数据,并且传入更新的条件
     * 有两种方式设置更新的数据
     * 方式1: 传入设置好属性的实体类,之后再传入条件
     * 方式2: 只使用UpdateWrapper,可以用它设置修改后的数据,以及查询条件
     */
    @Test
    public void testUpdate1(){
        User user = new User();
        user.setPassword("88888"); //设置用户密码为88888
        user.setAge(18);    //设置年龄为18
        //创建 QueryWrapper对象或者UpdateWrapper对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("password","66666"); //条件是 password = 66666
        //执行的sql语句, UPDATE tb_user SET password=?, age=? WHERE password = ?
        int update = userMapper.update(user, queryWrapper);
        System.out.println("影响行数:"+update);
    }

    @Test
    public void testUpdate2(){
        //创建UpdateWrapper对象或者UpdateWrapper对象
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password","66666").set("age","22") //修改的属性
                .eq("password","88888"); //条件是 password = 88888
        //执行的sql语句, UPDATE tb_user SET password=?,age=? WHERE password = ?
        int update = userMapper.update(null, updateWrapper); //只需要传入updateWrapper,实体参数为null
        System.out.println("影响行数:"+update);
    }

    /**
     * 测试通用方法 DeleteById()
     */
    @Test
    public void testDeleteById(){
        //执行的sql语句 DELETE FROM tb_user WHERE id=?
        int effectLine = userMapper.deleteById(7L);//删除id为7的数据,Serializable为可序列化的接口,Long的包装类默认实现了
        System.out.println("影响行数:"+effectLine);
    }

    /**
     * 测试通用方法 DeleteByMap(map);
     * 通过传入的Map集合,设置删除的条件,map设置的多个键值对的条件 是以AND形式连接的
     */
    @Test
    public void testDeleteByMap(){
        //创建map
        Map<String,Object> map = new HashMap<>();
        map.put("password","1234");
        map.put("user_name","snake"); //设置多个键值对后, 条件使用AND连接
        //调用删除方法, 使用的SQL语句: DELETE FROM tb_user WHERE password = ? AND user_name = ?
        int effectLine = userMapper.deleteByMap(map);
        System.out.println(effectLine);
    }

    /**
     * 测试使用Delete(wrapper)方法,删除对象
     */
    @Test
    public void testDelete(){
        //用法1: 创建queryWrapper
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_name","zhangsan").eq("password","66666"); //设置条件
        //用法2: 创建User对象,设置好对应的值,传入wrapper的构造函数,使其生成映射好的wrapper,
        // 更推荐用法2! 因为 省去了写字段名的过程,减少了写错的概率
        User user = new User();
        user.setUserName("lisi");
        user.setPassword("123456");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        //执行的sql :DELETE FROM tb_user WHERE user_name = ? AND password = ?
        int line = userMapper.delete(queryWrapper);
        System.out.println("影响条数:"+line);
    }
}
