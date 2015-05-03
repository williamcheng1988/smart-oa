
package com.chz.smartoa.common.page;

import java.util.ArrayList;
import java.util.List;

public class PageResultVO 
{

    /**
     * 每页记录数
     */
    protected int pageSize = PageConstants.DEF_PAGE_SIZE;

    /**
     * 满足条件的记录结果集
     */
    protected List pageInfo = new ArrayList();

    /**
     * 总页数
     */
    protected int totalPages = 0;

    /**
     * 总记录数
     */
    protected int totalRows = 0;

    /**
     * 当前页号
     */
    protected int currentPageNo = 1;

    /**
     * 当前页的记录的开始序号
     */
    public int startnum = 1;

    /**
     * 下一页码数
     */
    protected int nextPageNo = 0;

    /**
     * 上一页码数
     */
    protected int prevPageNo = 0;

    /**
     * 第一页数
     */
    protected int firstPage = 1;

    /**
     * 最后一页数
     */
    protected int lastPage = 1;

    public int getPageSize()
    {

        return pageSize;
    }

    public void setPageSize(int pageSize)
    {

        this.pageSize = pageSize;
    }

    public List getPageInfo()
    {

        return pageInfo;
    }

    public void setPageInfo(List pageInfo)
    {

        this.pageInfo = pageInfo;
    }

    public int getCurrentPageNo()
    {

        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo)
    {

        this.currentPageNo = currentPageNo;
    }

    public int getTotalRows()
    {

        return totalRows;
    }

    public void setTotalRows(int totalRows)
    {

        this.totalRows = totalRows;
        this.totalPages = this.totalRows / this.pageSize + ((this.totalRows % this.pageSize == 0) ? 0 : 1);
    }

    public int getFirstPage()
    {

        return firstPage;
    }

    public int getLastPage()
    {

        return lastPage;
    }

    public int getNextPageNo()
    {

        return nextPageNo;
    }

    public int getPrevPageNo()
    {

        return prevPageNo;
    }

    public int getStartnum()
    {
    	
    	return startnum;
    }
    
    public void setStartnum(int startnum)
    {

        this.startnum = startnum;
    }

    public void setTotalPages(int totalPages)
    {

        this.totalPages = totalPages;
    }
    
    public int getTotalPages()
    {
    
        return totalPages;
    };
}
