package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao= new UserDaoImpl();


    @Override
    public boolean register(User user) {
        //首先根据用户名进行数据的查询
        User byUsername = dao.findByUsername(user.getUsername());
        //判断数据库中是否有这个用户
        if (byUsername!=null){
            //代表数据库存在了数据，那么添加失败
            return false;
        }
        //设置唯一的激活码
        user.setCode(UuidUtil.getUuid());   //全球唯一的激活码
        //在没有确定邮箱之前，设置用户的初试状态是N
        user.setStatus("N");
        //代表数据库中没有数据，那么可以往数据库添加
        dao.insertUser(user);
        //添加用户之后，判断邮件是否正确。  由于是本地地址，在localhost后面直接添加项目的名字
        String content="<a href='http://localhost/travel/user/activeUserServlet?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件。");
        return true;
    }
    @Override
    public boolean active(String code) {
        //根据激活码查询用户是否存在,先查询是否存在，然后进行修改
        User user = dao.findByCode(code);
        if (user != null){
            //根据激活码查询到了用户，那么修改状态值
            dao.updateStatus(user);
            return true;
        }else{
            return false;
        }
    }



    //判断用户是否存在
    @Override
    public User login(User loginUser) {
        //调用方法
        User user=dao.login(loginUser.getUsername(),loginUser.getPassword());
        return user;
    }

}
