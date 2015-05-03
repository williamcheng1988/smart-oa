
package com.chz.smartoa.common.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * <p>
 * 查询结果结构
 * </p>
 * <p>
 * 根据不同的使用场合，提供了多种构造对象的方式<br/> 1.默认的构造方式,采用默认的页面大小、取首页数据 <br/> 2.指定每页大小，页序的构造方式<br/>
 * 3.从httpRequest对象构造，实现从httpRequest取的记录总数，页序参数 <br/>
 * 4.指定PageSize构造对象，实现按照PageSize取首页的方法,如果PageSize小于等于0则取全量数据<br/>
 * <p>
 * 根据不同的使用场合，提供了多种执行的方式<br/> 1.excute---提供SQL语句的执行方式<br/>
 * 2.excuteBySQLCode---提供SQLCode的执行方式 <br/>
 * </p>
 * @author huangdhui

 */
public class PageResult extends PageResultVO
{

    /**
     * 记录日志的实例对象
     */
    private Logger logger = Logger.getLogger(PageResult.class);

    /**
     * 当前页的记录的结尾序号
     */
    protected int endnum = PageConstants.DEF_PAGE_SIZE;

    /**
     * 偏移位数
     */
    protected int scrollNum = 0;
    
    private int startNum = 1;
    
    

    public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	/**
     * 构造函数
     */
    public PageResult()
    {

        logger.debug("构造了一个默认的首页PageResult对象");
    }

    /**
     * 跟据HttpServletRequest构造一个默认每页数量PageResult分页对象
     * 
     * @param httpServletRequest HttpServletRequest
     */
    public PageResult(HttpServletRequest httpServletRequest)
    {

        init(httpServletRequest);
    }

    /**
     * 构造函数
     */
    public PageResult(int pageSize)
    {

        this.pageSize = pageSize;
        if (0 >= pageSize)
        {
            logger.debug("构造了取全量数据的PageResult对象");
        }
        else
        {
            logger.debug("构造了一个每页取" + pageSize + "条记录取首页的PageResult对象");
        }
    }

    /**
     * 构造函数
     * 
     * @param PageSize int 每页记录数
     * @param PageNo int 页面序号
     */
    public PageResult(int pageSize, int pageNo)
    {

        this.pageSize = pageSize;
        this.currentPageNo = pageNo;
        if (logger.isDebugEnabled())
        {
            logger.debug("构造了一个每页取" + pageSize + "条记录，取第" + currentPageNo + "页数据的PageResult对象");
        }
    }

    /**
     * 跟据HttpServletRequest构造一个设定分页数量的PageResult分页对象
     * 
     * @param httpServletRequest HttpServletRequest
     * @param pageSize int
     */
    public PageResult(HttpServletRequest httpServletRequest, int pageSize)
    {

        init(httpServletRequest, pageSize);
    }

    /**
     * 获取参数
     */
    private void init(HttpServletRequest httpServletRequest)
    {

        logger.debug("init");

        String param = "";

        // 获取总记录数，如果获取失败数，则赋初值-1认为是初次查询
        param = httpServletRequest.getParameter(PageConstants.PAGE_RECCOUNT_NAME);
        if (null != param && (param.length() != 0))
        {
            this.totalRows = Integer.parseInt(param);
        }
        else
        {
            this.totalRows = -1;
        }
        logger.debug("totalRows:" + totalRows);
        // 从请求参数中得到当前页序号，如果获取失败，则认为取第一页
        param = httpServletRequest.getParameter(PageConstants.PAGE_INDEX_NAME);

        if (null != param && (param.length() != 0))
        {
            if (param.length() > 7)
                param = "1";
            this.currentPageNo = Integer.parseInt(param);
            if (currentPageNo <= 0)
                currentPageNo = 1;
        }
        else
        {
            this.currentPageNo = 1;
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("来自HttpRequest构造了一个：每页取" + pageSize + "条记录，取第" + currentPageNo + "页数据的PageResult对象");
        }
    }

    /**
     * 获取参数
     */
    private void init(HttpServletRequest httpServletRequest, int pageSize)
    {

        logger.debug("init");
        // 设置每页记录数
        this.pageSize = pageSize;

        String param = "";

        // 获取总记录数，如果获取失败数，则赋初值-1认为是初次查询
        param = httpServletRequest.getParameter(PageConstants.PAGE_RECCOUNT_NAME);
        if (null != param && (param.length() != 0))
        {
            this.totalRows = Integer.parseInt(param);
        }
        else
        {
            this.totalRows = -1;
        }
        logger.debug("totalRows:" + totalRows);
        // 从请求参数中得到当前页序号，如果获取失败，则认为取第一页
        param = httpServletRequest.getParameter(PageConstants.PAGE_INDEX_NAME);

        if (null != param && (param.length() != 0))
        {
            if (param.length() > 7)
                param = "1";
            this.currentPageNo = Integer.parseInt(param);
            if (currentPageNo <= 0)
                currentPageNo = 1;
            if (this.totalRows != -1)
            {
                this.totalPages = this.totalRows / this.pageSize + ((this.totalRows % this.pageSize == 0) ? 0 : 1);
                if (this.currentPageNo > this.totalPages)
                {
                    // 页号输入超过最大页号，则按最大页号计算 biran modify
                    this.currentPageNo = this.totalPages;
                }
            }
        }
        else
        {
            this.currentPageNo = 1;
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("来自HttpRequest构造了一个：每页取" + pageSize + "条记录，取第" + currentPageNo + "页数据的PageResult对象");
        }
    }
    
    public void excute (PageDAO pageDAO, Object para)
    {
        pageInfo.clear();
        //由于存在翻页后记录集的数量变化可能到导致页面跳转后错误的情况存在，所以每次都计算一下总数吧。
        if (true)
        {
            try
            {
                this.totalRows = pageDAO.getCount(para);
            }
            catch (Exception e)
            {
                logger.error(e);
            }
        }
        if ( 0 >= pageSize)
        {
            this.totalPages = 1;
            this.startnum = 1;
            this.endnum = this.totalRows;
            this.currentPageNo = 1;
        }
        else
        {
            //pageSize大于0时，根据页面序号计算本次请求的其起始记录和结尾记录
            this.totalPages = this.totalRows / this.pageSize +
                ((this.totalRows % this.pageSize == 0) ? 0 : 1) ;
            // 请求页数〉总页数则取最后一页的数据
            if (this.totalPages < this.currentPageNo)
            {
                this.currentPageNo = this.totalPages;
            }
            this.startnum = this.pageSize * (this.currentPageNo - 1) + 1 ;
            this.endnum = startnum + this.pageSize - 1 ;
        }
        try
        {
            List list = pageDAO.getList(para,this.startnum,this.pageSize);
            if(list!=null&&list.size()!=0)
            {
                this.pageInfo.addAll(list);
            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }
    }
    
    /**
     * 把一个list加入到分页中。
     * 
     * @param allList List
     */
    public void excute(List allList)
    {

        if (allList == null || allList.size() == 0)
        {
            return;
        }
        pageInfo.clear();

        // 总记录数小于0，说明请求来自新的查询,否则是来自翻页请求
        if (0 >= this.totalRows)
        {
            this.totalRows = allList.size();
        }
        if (0 >= pageSize)
        {
            this.totalPages = 1;
            this.startnum = 1;
            this.endnum = this.totalRows;
            this.currentPageNo = 1;
        }
        else
        {
            // pageSize大于0时，根据页面序号计算本次请求的其起始记录和结尾记录
            this.totalPages = this.totalRows / this.pageSize + ((this.totalRows % this.pageSize == 0) ? 0 : 1);
            if (this.currentPageNo > this.totalPages)
            {
                // 页号输入超过最大页号，则按最大页号计算 biran modify
                this.currentPageNo = this.totalPages;
            }
            this.startnum = this.pageSize * (this.currentPageNo - 1) + 1;
            this.endnum = startnum + this.pageSize - 1;
            this.endnum = (this.endnum >= totalRows ? totalRows : this.endnum);
        }
        pageInfo = allList.subList(startnum - 1, endnum);

    }

    /**
     * 构造分页参数
     * 
     * @param pageList 当前分号页的数据
     * @param totalRows 总记录数
     */
    public void excute(List pageList, int totalRows)
    {

        pageInfo.clear();
        // 总记录数小于0，说明请求来自新的查询,否则是来自翻页请求
        if (0 >= this.totalRows)
        {
            this.totalRows = totalRows;
        }
        if (0 >= pageSize)
        {
            this.totalPages = 1;
            this.startnum = 1;
            this.endnum = this.totalRows;
            this.currentPageNo = 1;
        }
        else
        {
            // pageSize大于0时，根据页面序号计算本次请求的其起始记录和结尾记录
            this.totalPages = this.totalRows / this.pageSize + ((this.totalRows % this.pageSize == 0) ? 0 : 1);
            if (this.currentPageNo > this.totalPages)
            {
                // 页号输入超过最大页号，则按最大页号计算 biran modify
                this.currentPageNo = this.totalPages;
            }
            this.startnum = this.pageSize * (this.currentPageNo - 1) + 1;
            this.endnum = startnum + this.pageSize - 1;
            this.endnum = (this.endnum >= totalRows ? totalRows : this.endnum);
        }
        pageInfo = pageList;

    }

    /**
     * getNextPageNo
     * 
     * @return int
     */
    public int getNextPageNo()
    {

        return (currentPageNo + 1 > totalPages) ? totalPages : currentPageNo + 1;
    }

    /**
     * 
     * @return
     */
    public int getPrevPageNo()
    {

        return (currentPageNo - 1 < 1) ? 1 : currentPageNo - 1;
    }

    /**
     * isFirstPage
     * 
     * @return boolean
     */
    public boolean isFirstPage()
    {

        return currentPageNo <= 1;
    }

    /**
     * isLastPage
     * 
     * @return boolean
     */
    public boolean isLastPage()
    {

        return currentPageNo >= totalPages;
    }

    /**
     * @param scrollNum The scrollNum to set.
     */
    public void setScrollNum(int scrollNum)
    {

        this.scrollNum = scrollNum;
    }

}
