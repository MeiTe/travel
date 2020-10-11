package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    //数据库连接
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //分页查询车这个列表
    @Override
    public List<Route> pageQuery(int cid, int start, int pageSize) {
        //编写sql语句
        //SELECT * FROM tab_route WHERE cid=5 LIMIT 20,5
        String sql = "SELECT * FROM tab_route WHERE cid=? LIMIT ?,?";
        //执行sql
        List<Route> routeList = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid, start, pageSize);
        return routeList;
    }


    //根据cid查询总的记录数
    @Override
    public int findAllByCid(int cid){
        //编写SQL语句，SELECT COUNT(*) FROM tab_route WHERE cid=4
        String sql ="SELECT COUNT(*) FROM tab_route WHERE cid=?";
        //执行SQL,因为查询的是一个值，所以使用queryForObject
        int count=template.queryForObject(sql,Integer.class,cid);
        return count;

    }
}
