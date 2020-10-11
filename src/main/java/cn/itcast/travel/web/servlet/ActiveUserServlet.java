package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从页面中获取唯一的code
        String code = request.getParameter("code");
        //判断code是否存在
        if (code != null){
            //如果存在的话，那么调用active方法
            UserService us = new UserServiceImpl();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
