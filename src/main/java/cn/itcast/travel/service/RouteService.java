package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    //查询符合条件的分页列表
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize);
}
