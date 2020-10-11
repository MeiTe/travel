package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    //查询出这个cid所有的数量
    public int findAllByCid(int cid);
    //根据数据分页查询, 查询出一个tab_route列表
    public List<Route> pageQuery(int cid, int start, int pageSize);
}
