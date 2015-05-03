
package com.chz.smartoa.common.base;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.chz.smartoa.common.util.LoginUtils;
import com.chz.smartoa.system.action.OperateResult;
import com.chz.smartoa.system.pojo.Staff;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Action 基础类
 * @author huangdhui
 *
 */
public class BaseAction extends ActionSupport
{
	private static final long serialVersionUID = -1313107235549834118L;
	
	protected static final String LOGIN_URL = "/login.html";

	/** * 项目. */
    protected static final String SCOPE_PROJECT = "PROJECT";
    /** * 个人. */
    protected static final String SCOPE_STAFF = "STAFF";
    /** * 范围 */
    protected String scope;
    
    
    /** * 网站根目录. */
    protected final String ctx="smartoa";
    
    
    /** * 返回操作结果 */
    protected static final String OPER_RESULT = "operresult";
    protected OperateResult operateResult;
    
    /** * JSON格式：返回实体  */
    protected static final String ENTRY = "entry";
    protected Object entry;
    
    /** * JSON格式：返回集合 */
    protected static final String DATA_LIST = "datalist";
    protected List<?> dataList;
    
    /** * JSON格式：返回datagrid */
    protected static final String DATA_GRID = "datagrid";
    protected DataGrid dataGrid;
    
    /** * JSON格式：返回树 */
    protected static final String TREE_DATA = "treedata";
    protected List<TreeData> treeData;

    /** 是否是数据库操作：0,不是;1,是;*/
    protected int isdo = 0;
    
    /** 操作提示页面_跳转URL */
    private String goUrl;
    
    private String successMsg;
    private String errorMsg;


    /** * 每页最大条数. */
    private static final int MAX_PER_PAGE = 999;

    /** * 缺省每页条数. */
    private static final int DEFAULT_PER_PAGE = 10;

    /** *首页. */
    int firstPage = 1;

    /** *前一页. */
    int prevPage = -1;

    /** *下一页. */
    int nextPage = -1;

    /** *尾页. */
    int lastPage = 1;

    /** *当前页. */
    int page = 1;

    /** *总页数. */
    int pageCount = 1;

    /** *每页大小. */
    int perPage = DEFAULT_PER_PAGE;

    /** * 返回总行数. */
    int totalCount = 0;

    /** * 排序字段（例sp.spCode）. */
    String sort;

    /** * 正序|反序（例ASC）. */
    String order;

    /** * 当前请求地址 */
    String actionUrl;
    /** * 当前请求参数*/
    String reqParams;

    /**
     * 得到http session.
     * 
     * @return http session
     */
    public final HttpSession getSession()
    {
        return getHttpServletRequest().getSession(true);
    }

    /**
     * 得到http request.
     * @return http request
     */
    public final HttpServletRequest getHttpServletRequest()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        return request;
    }

    /**
     * 得到http session.
     * 
     * @return http request
     */
    public final HttpSession getHttpSession()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        return request.getSession(true);
    }

    /**
     * 得到http response.
     * 
     * @return http response
     */
    public final HttpServletResponse getHttpServletResponse()
    {

        HttpServletResponse response = ServletActionContext.getResponse();
        return response;
    }

    /**
     * 设置分页属性.
     * 
     * @param domain 输入DO对象
     */
    public void setPagination(BaseDomain domain)
    {

        // 调整每页分页数
        if (perPage > MAX_PER_PAGE)
        {
            perPage = DEFAULT_PER_PAGE;
        }
        if (perPage <= 0)
        {
            perPage = DEFAULT_PER_PAGE;
        }

        if (page <= 0)
        {
            page = 1;
        }

        // 设置分页属性: start,limit,sort,order
        domain.setStart(perPage * (page - 1));
        domain.setLimit(perPage);
        domain.setSort(sort);
        domain.setOrder(order);

        // 得到当前请求的URL
        HttpServletRequest httpServletRequest = getHttpServletRequest();

        // 循环所有参数
        Enumeration paramNames = httpServletRequest.getParameterNames();
        StringBuffer paramBuffer = new StringBuffer();
        while (paramNames.hasMoreElements())
        {
            // 得到当前参数
            String paramName = ( String ) paramNames.nextElement();

            // 把页号参数排除，在循环外增加
            if (paramName.equals("pageNo"))
            {
                continue;
            }

            // 取得名称所对应的全部值
            String[] paramValues = httpServletRequest.getParameterValues(paramName);

            // 如果有多个参数，取第一个参数的值
            String paramValue = paramValues[0];
            if (StringUtils.isEmpty(paramValue))
            {
                continue;
            }

            if (paramBuffer.length() == 0)
            {
                paramBuffer.append(paramName + "=" + paramValue);
            }
            else
            {
                paramBuffer.append("&" + paramName + "=" + paramValue);
            }
        }

        // 将pageNo参数放在URL最后边，待模板页增加实际页号
        if (paramBuffer.length() == 0)
        {
            actionUrl = httpServletRequest.getRequestURI() + "?pageNo=";
        }
        else
        {
            actionUrl = httpServletRequest.getRequestURI() + "?" + paramBuffer.toString() + "&pageNo=";
        }
        actionUrl = StringEscapeUtils.escapeHtml(actionUrl);
        
        reqParams = actionUrl.substring(actionUrl.indexOf("?")); 
    }

    public int getFirstPage()
    {

        return firstPage;
    }

    public void setFirstPage(int firstPage)
    {

        this.firstPage = firstPage;
    }

    public int getPrevPage()
    {

        return prevPage;
    }

    public void setPrevPage(int prevPage)
    {

        this.prevPage = prevPage;
    }

    public int getNextPage()
    {

        return nextPage;
    }

    public void setNextPage(int nextPage)
    {

        this.nextPage = nextPage;
    }

    public int getLastPage()
    {
        return lastPage;
    }

    public void setLastPage(int lastPage)
    {
        this.lastPage = lastPage;
    }
    public void setPage(int page) {
		this.page = page;
	}
	public int getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }

    public int getPerPage()
    {
        return perPage;
    }

    public void setPerPage(int perPage)
    {

        this.perPage = perPage;
    }

    public int getTotalCount()
    {

        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;

        // 计算总页数
        if (totalCount % perPage > 0)
        {
            pageCount = totalCount / perPage + 1;
        }
        else
        {
            pageCount = totalCount / perPage;
        }

        // 首页尾页号
        firstPage = 1;
        lastPage = pageCount;

        // 前一页，后一页页号
        if (page == pageCount)
        {
            nextPage = -1;
        }
        else
        {
            nextPage = page + 1;
        }

        if (page == firstPage)
        {
            prevPage = -1;
        }
        else
        {
            prevPage = page - 1;
        }
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public String getActionUrl()
    {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl)
    {
        this.actionUrl = actionUrl;
    }
    public String getReqParams() {
		return reqParams;
	}
	public void setReqParams(String reqParams) {
		this.reqParams = reqParams;
	}

    /**
     * 得到登录用户.
     * 
     * @return staff
     */
    public Staff getLoginStaff()
    {
        return LoginUtils.getLoginStaff(( HttpSession ) getSession());
    }

    /**
     * 写入登录用户.
     * 
     * @return staff
     */
    public void setLoginStaff(Staff staff)
    {
        //修改会话
        getSession().invalidate();// 清空session
        if (getHttpServletRequest().getCookies() != null)
        {
            for (Cookie cookie : getHttpServletRequest().getCookies())
            {
                if ("JSESSIONID".equals(cookie.getName().toUpperCase()))
                {
                    cookie.setMaxAge(0);// 让cookie过期
                    break;
                }
            }
          
        }
        LoginUtils.setLoginStaff(staff, getSession());
    }

    public String getCtx() {
		return ctx;
	}
	public String getScope()
    {
        return scope;
    }
    public void setScope(String scope)
    {
        this.scope = scope;
    }
    public String getGoUrl()
    {
        return goUrl;
    }
    public void setGoUrl(String goUrl)
    {
        this.goUrl = goUrl;
    }
	public void setIsdo(int isdo) {
		this.isdo = isdo;
	}
	public DataGrid getDataGrid() {
		return dataGrid;
	}
	//每页条数
	public void setRows(int rows){
		this.perPage = rows;
	}
	public Object getEntry() {
		return entry;
	}
	public List<?> getDataList() {
		return dataList;
	}
	public OperateResult getOperateResult() {
		return operateResult;
	}
	public List<TreeData> getTreeData() {
		return treeData;
	}
	public String getSuccessMsg() {
		return successMsg;
	}
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
