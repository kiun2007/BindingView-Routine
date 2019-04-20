package kiun.com.bvroutine.data;

import kiun.com.bvroutine.interfaces.IgnoreParam;

public class PagerBean<T> extends QueryBean<T>{
    private int pageNum = 1;
    private int pageSize = 20;

    @IgnoreParam
    private int pages = -1;

    public PagerBean(){
        super();
    }

    public PagerBean(T extra){
        super(extra);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void addPage(){
        pageNum ++;
    }
}
