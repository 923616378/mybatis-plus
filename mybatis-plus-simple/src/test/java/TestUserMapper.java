import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.snake.dao.UserMapper;
import com.snake.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestUserMapper {
    /**
     * 使用原来的mybatis的操作
     */
    @Test
    public void test1() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        UserMapper mapper = sqlSessionFactory.openSession().getMapper(UserMapper.class);
        //调用查询方法
        List<User> allUsers = mapper.findAll();
        for (User allUser : allUsers) {
            System.out.println(allUser);
        }
    }

    /**
     * 使用mybatisPlus + mybatis
     *  步骤:
     *      1. UserMapper继承BaseMapper类, 就拥有了BaseMapper的一些增删改查的方法
     *      2. 使用MybatisSqlSessionFactoryBuilder,来构建SqlSession
     *      3. 在实体pojo类中使用 @TableName("表名") 注解, 来指明实体类与哪张表示映射关系
     *   已知MybatisPlus的好处:
     *      1. 提供了一些常用的curd方法
     *      2. 帮我们解决了数据库字段与实体类字段映射的问题,
     *         比如说数据库是以下滑线分开的方式命名的: user_name,
     *         那么在Java中可以使用userName来命名,依然可以映射上,不会有null
     */
    @Test
    public void test2() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(inputStream);
        UserMapper mapper = sqlSessionFactory.openSession().getMapper(UserMapper.class);
        //调用继承的查询方法
        List<User> users = mapper.selectList(null);//传入的参数是查询的条件,暂时设置为null, 自动做自动的映射 user_name => userName
        for (User user : users) {
            System.out.println(user);
        }

    }
}
