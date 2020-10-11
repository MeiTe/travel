package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    //进行数据库的连接
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            //编写SQL语句
            String sql = "SELECT * FROM tab_user WHERE username=?";
            //执行SQL
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){

        }
        return user;
    }


    //注册用户信息
    @Override
    public void insertUser(User user) {
        //编写SQL语句
        String sql="INSERT INTO tab_user(username,PASSWORD,NAME,birthday,sex,telephone,email,status,code) VALUES(?,?,?,?,?,?,?,?,?)";
        //执行SQL语句
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());
    }


    //根据激活码查询这个用户是否存在
    @Override
    public User findByCode(String code) {
        User user =null;
        //避免查不到有异常
        try {
            //编写SQL
            String sql = "SELECT * FROM tab_user WHERE CODE =? ";
            //执行SQL
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


    //当用户存在激活码时，修改用户的状态
    @Override
    public void updateStatus(User user) {
        //编写SQL语句
        String sql="UPDATE tab_user SET STATUS ='Y' WHERE uid =?";
        //执行SQL语句
        template.update(sql,user.getUid());
    }

    @Override
    public User login(String username, String password) {
        User user=null;
        //为了防止出现查不到数据出现异常，进行异常处理
        try{//编写SQL语句
            String sql="SELECT * FROM tab_user WHERE username=? AND PASSWORD=?";
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
