package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService us = new UserServiceImpl();

    /**
     *  激活用户的邮箱
     * **/
    public void activeUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从页面中获取唯一的code
        String code = request.getParameter("code");
        //判断code是否存在
        if (code != null){
            //如果存在的话，那么调用active方法
            boolean flag=us.active(code);
            //判断标记
            String msg = null;
            if (flag){
                //说明激活成功
                msg="您的邮箱激活成功，请进行登录<a href='login.html'></a>";
            }else {
                //说明激活失败，相应数据
                msg="您的邮箱激活失败";
            }
            //设置页面的编码样式
            response.setContentType("text/html;charset=utf-8");
            //进行数据的相应
            response.getWriter().write(msg);
        }
    }

    /**
     * 用户退出
     * **/
    public void exitServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //重定向到login页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 从session中获取用户的用户名
     * **/
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        Object name = request.getSession().getAttribute("user");
        /*ObjectMapper mapper = new ObjectMapper();
        //相应成json格式的
        response.setContentType("application/json;charset=utf-8");
        //相应到客户端
        mapper.writeValue(response.getOutputStream(),name);*/
        writeValue(name,response);
    }

    /**
     * 用户登录
     * **/
    public void loginServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        /*//设置编码的样式,为json的形式
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        //将传入对象序列化为json，并且返回给客户端
        mapper.writeValue(response.getOutputStream(),info);*/
        writeValue(info,response);
    }

    /**
     * 用户注册
     * **/
    public void registerUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);*/
            String json = writeValueAsString(info);
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
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);*/
        String json = writeValueAsString(info);
        //设置格式
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

}
