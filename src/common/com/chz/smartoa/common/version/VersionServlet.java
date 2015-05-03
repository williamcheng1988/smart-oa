
package com.chz.smartoa.common.version ;

import javax.servlet.ServletConfig ;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpServlet ;
import java.util.ArrayList ;

/**
 *     the servlet to show version & patch info at startup
 *     @author  huangdhui
 */

@SuppressWarnings("serial")
public class VersionServlet extends HttpServlet
{
    private final String LS = System.getProperty("line.separator") ;

    private final int charLength = 37 ;

    public VersionServlet ()
    {
    }

    public void init (ServletConfig config)
        throws ServletException
    {
        try
        {
            //状态版本信息
            String versionFileName = config.getInitParameter("versionFile") ;
            String patchPath = config.getInitParameter("patchPath") ;
            String isInternal = config.getInitParameter("isInternal") ;
            String patchFilter = config.getInitParameter("patchFilter") ;
            ProductInfo.setVersionFile(versionFileName) ;
            ProductInfo.setPatchPath(patchPath) ;
            ProductInfo.setIsInternal(isInternal) ;
            ProductInfo.setPatchFilter(patchFilter) ;
            ProductInfo.setServContext(config.getServletContext()) ;
            ProductInfo.load() ;
            //展示版本信息
            String info = getVersionString() ;
            System.out.write(info.getBytes("gb2312")) ;
        }
        catch (Exception ex)
        {
            System.out.println("获得版本/Patch信息出错！" + ex.toString()) ;
        }
    }

    private String getVersionString ()
    {
        StringBuffer str = new StringBuffer() ;
        //print logo
        str.append(makeLogo()) ;
        str.append(LS) ;
        //print owner info
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(ProductInfo.getOwnerInfo()) ;
        str.append(LS) ;
        //print warn info
        String warnInfo = ProductInfo.getWarnInfo() ;
        String[] warns = parseLine(warnInfo) ;
        for (int i = 0 ; i < warns.length ; i++)
        {
            str.append(repeatLetter('#', 2)) ;
            str.append(repeatLetter(' ', 3)) ;
            str.append(warns[i]) ;
            str.append(LS) ;
        }
        //print seperator
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('=', 70)) ;
        str.append(repeatLetter('#', 2)) ;
        str.append(LS) ;
        //print version ID
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append("版本:") ;
        str.append(ProductInfo.getVersionID()) ;
        str.append(LS) ;
        //print patch IDs
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append("更新版本:") ;
        PatchVO[] patch = ProductInfo.queryPatchsArray(true) ;
        if (patch != null)
        {
            for (int i = 0 ; i < patch.length ; i++)
            {
                if (i > 0)
                {
                    str.append(LS) ;
                    str.append(repeatLetter('#', 2)) ;
                    str.append(repeatLetter(' ', 12)) ;
                }
                str.append(patch[i].getPatchID()) ;
            }
        }
        str.append(LS) ;
        return str.toString() ;
    }

    private String makeLogo ()
    {
        StringBuffer str = new StringBuffer() ;
        								//line 1
        str.append(repeatLetter('#', 2)) ;
        str.append(repeatLetter(' ', 3)) ;
        str.append(repeatLetter('=', 70)) ;
        str.append(repeatLetter('#', 2)) ;
        str.append(LS) ;
      
        str.append(repeatLetter('#', 2)) ;
        return str.toString() ;

    }

    private String repeatLetter (char letter, int len)
    {
        StringBuffer str = new StringBuffer() ;
        for (int i = 0 ; i < len ; i++)
        {
            str.append(letter) ;
        }
        return str.toString() ;
    }

    private String[] parseLine (String info)
    {
        ArrayList<String> arr = new ArrayList<String>() ;
        String tmp = info ;
        while (tmp.length() > charLength)
        {
            String line = tmp.substring(0, charLength) ;
            arr.add(line) ;
            tmp = tmp.substring(charLength) ;
        }
        arr.add(tmp) ;
        String[] lines = new String[arr.size()] ;
        for (int i = 0 ; i < arr.size() ; i++)
        {
            lines[i] = (String) arr.get(i) ;
        }
        return lines ;

    }

    public void destroy ()
    {
    }

}
