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

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //这里是用户输入的验证码
        String check = request.getParameter("check");
        //这里是自动生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //为了保证验证码的唯一性，每次获取验证码后立马移除
        session.removeAttribute("CHECKCODE_SERVER");
        //首先判断验证码是否正确
        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
            //如果验证码输入的不一致
            //返回提示信息
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("您的验证码输入错误！");
            //把info序列化成为json返回到页面
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //设置格式
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
        //验证码输入的一致
        //获取表单中用户输入的数据
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        //进行数据的封装
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用UserService中的方法
        UserService us = new UserServiceImpl();
        boolean flag = us.register(user);
        ResultInfo info = new ResultInfo();
        //判断什么时候时候登录成功
        if (flag){
            info.setFlag(true);
        }else{
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        //把info数据序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //设置格式
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
