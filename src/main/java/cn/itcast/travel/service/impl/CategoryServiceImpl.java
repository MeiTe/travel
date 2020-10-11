package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //因为导航栏的里面的内容是很少发生变化的，为了保持高效，不需要每次查询数据库，故可以保留在缓存中
        Jedis jedis = JedisUtil.getJedis();
        //对缓存中的数据进行排序
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        //首先创建一个列表保存从数据库中查询出来的数据
        List<Category> cs = null;
        //首先判断缓存中是否存在数据
        if (categorys==null || categorys.size()==0){
            System.out.println("从数据库中查询的数据·····");
            //表明在缓存中没有数据，那么我们要查询数据库
            cs=dao.findAll();
            //然后遍历数组，保存在缓存中
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{
            System.out.println("在缓存中查询的数据。。。。");
            //表明categorys缓存中存在数据，那么不需要查询数据库，直接从缓存中进行获取
            //给cs定义成一个列表保存数据
            cs= new ArrayList<Category>();
            for (Tuple name : categorys) {
                Category c = new Category();
                //保存导航栏的名称
                c.setCname(name.getElement());
                //保存导航栏的数字
                c.setCid((int)name.getScore());
                cs.add(c);
            }
        }
        return cs;
    }
}
