package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao dao = new RouteDaoImpl();


    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        PageBean<Route> pb = new PageBean<Route>();
        //查询出总的条数
        int totalCount = dao.findAllByCid(cid);
        pb.setTotalCount(totalCount);
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        //计算出总共有多少页面
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);
        //计算出查询符合条件的列表的开始数据
        int statr = (currentPage-1)*pageSize;
        //查询出符合条件的列表
        List<Route> routeList = dao.pageQuery(cid, statr, pageSize);
        pb.setList(routeList);
        return pb;
    }
}
