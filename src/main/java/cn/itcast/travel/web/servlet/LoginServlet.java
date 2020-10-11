package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //然后获取页面的用户输入的用户名和密码,保存在map中
        Map<String, String[]> map = request.getParameterMap();
        User loginUser = new User();
        //然后进行封装
        try {
            BeanUtils.populate(loginUser,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service中的登录方法
        UserService us = new UserServiceImpl();
        User u=us.login(loginUser);
        //进行信息的提示
        ResultInfo info = new ResultInfo();
        //先判断u是否为空
        if (u==null){
            info.setFlag(false);
            info.setErrorMsg("该用户不存在！！！");
        }
        //如果u存在的话，那么判断邮箱的状态是否为Y
        if (u!=null && !"Y".equals(u.getStatus())){
            //表示的是邮箱的状态为”N“的情况
            info.setFlag(false);
            info.setErrorMsg("不好意思，你的邮箱尚未激活！！！");
        }
        if (u!=null && "Y".equals(u.getStatus())){

            info.setFlag(true);
            //把登录的信息保存在session中
            HttpSession session = request.getSession();
            session.setAttribute("user",u);

        }

        //设置编码的样式,为json的形式
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        //将传入对象序列化为json，并且返回给客户端
        mapper.writeValue(response.getOutputStream(),info);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
