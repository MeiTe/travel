package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private int totalCount;  //总共有多少条数据
    private int totalPage;   //总共有多少页
    private int currentPage; //当前页面
    private int pageSize;    //每页显示多少条数据
    private List<T> list;       //当前页面显示的数据


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
