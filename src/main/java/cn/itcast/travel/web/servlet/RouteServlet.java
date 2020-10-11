package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService rs = new RouteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取页面的参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");  //分类的ID
        //对数据进行处理
        int cid=0;
        if (cidStr!=null && cidStr.length()>0){
            //进行数据的强转
            cid=Integer.parseInt(cidStr);
        }
        int currentPage=0;
        if (currentPageStr!=null && currentPageStr.length()>0){
            //进行数据的强转
            currentPage=Integer.parseInt(currentPageStr);
        }else{
            //当第一次，进入的时候为了避免出现异常，所以设定默认值
            currentPage=1;
        }

        int pageSize=0;
        if (pageSizeStr!=null && pageSizeStr.length()>0){
            //进行数据的强转
            pageSize=Integer.parseInt(pageSizeStr);
        }else{
            //当第一次，进入的时候为了避免出现异常，所以设定默认值
            pageSize=5;
        }

        //调用service查询PageBean
        PageBean<Route> pb=rs.pageQuery(cid,currentPage,pageSize);
        //将pageBean对象序列化为json返回
        writeValue(pb,response);
    }

}
