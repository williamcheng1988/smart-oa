package com.chz.smartoa.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * ���ڴ��?����
 * @author wesson
 * @description
 */
public class DateUtil {
	/**
	 * Ĭ�����ڸ�ʽ���ַ�:yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_DATE_FORMAT2 = "yyyyMMdd";
	public static final String DEFAULT_DATE_FORMAT3 = "EEE MMM dd HH:mm:ss zzz yyyy";
	public static final String DEFAULT_DATE_FORMAT4 = "yyyy-mm-dd HH:mm:ss";

	private static String[] cnMonth = {"-һ��","-����","-����","-����","-����","-����","-����","-����","-����","-ʮ��","-ʮһ��","-ʮ����"};
	private static String[] enMonth = {"-01","-02","-03","-04","-05","-06","-07","-08","-09","-10","-11","-12"};
	//���ڸ�ʽ������
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	/**
	 * �����ڶ�����ָ���ĸ�ʽת�����ַ�
	 *@description  
	 *@date 2012-7-26 
	 *@author BIANDONGYU850
	 *@param date
	 *@param formatStr
	 *@return}
	 */
	public static String formatDateToString(Date date,String formatStr){
		synchronized (sdf) {
			sdf = new SimpleDateFormat(formatStr);
			return sdf.format(date);
		}
	}
	
	/**
	 * ���ַ������ָ���ĸ�ʽת��������
	 *@description  
	 *@date 2012-7-26 
	 *@author BIANDONGYU850
	 *@param date
	 *@param formatStr
	 *@return}
	 * @throws ParseException 
	 */
	public static Date formatDateToString(String dateString,String formatStr) throws ParseException{
		synchronized (sdf) {
			sdf = new SimpleDateFormat(formatStr);
			return sdf.parse(dateString);
		}
	}
	/**
	 * �����ڶ�����Ĭ�ϵĸ�ʽת�����ַ�
	 *@description  
	 *@date 2012-7-26 
	 *@author BIANDONGYU850
	 *@param date
	 *@return}
	 */
	public static String formatDateToString(Date date){
		return formatDateToString(date,DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * ���ַ������Ĭ�ϵĸ�ʽת������������
	 *@description  
	 *@date 2012-8-9 
	 *@author EX-ZHANGWEI001
	 *@param date
	 *@return}
	 * @throws ParseException 
	 */
	public static Date formatStringToDate(String date) throws ParseException
	{
		if(date != null)
		{
			synchronized (sdf) {
				sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
				return sdf.parse(date);
			}
		}
		else{
			return null;
		}
		
	}
	/**
	 * ���ַ������Ĭ�ϵĸ�ʽת������������
	 *@description  
	 *@date 2012-8-9 
	 *@author EX-ZHANGWEI001
	 *@param date
	 *@return}
	 * @throws ParseException 
	 */
	public static Date formatStringToDate2(String date) throws ParseException
	{
		if(date != null)
		{
			synchronized (sdf) {
				sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT2);
				return sdf.parse(date);
			}
		}
		else{
			return null;
		}
		
	}
	/**
	 * ���ַ������Ĭ�ϵĸ�ʽת������������(�����ַ�Tue Oct 08 00:00:00 CST 2013���Date����)
	 *@description  
	 *@date 2013-10-31 
	 *@author EX-OUTIANJING001
	 *@param date
	 *@return}
	 * @throws ParseException 
	 */
	public static Date formatStringToDate3(String date) throws ParseException
	{
		if(date != null)
		{
			synchronized (sdf) {
				sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT3,Locale.US);
				return sdf.parse(date);
			}
		}
		else{
			return null;
		}
		
	}
	
	/**
	 * �������ַ�ת�������ڸ�ʽ �磺 31-ʮ����-2099 ת���� 2099-12-31
	 * @param cnDateStr
	 * @return
	 * ����:2013-12-25
	 */
	public static String formatCnStringToDate(String cnDateStr) throws ParseException
	{
		if(StringUtils.isBlank(cnDateStr))
		{
			throw new ParseException("Ŀ���ַ�Ϊ��",0);
		}
		if(cnDateStr.indexOf("-") == -1)
		{
			throw new ParseException("Ŀ���ַ��ʽ����",0);
		}
		String destStr = "";
		boolean parsed = false;
		for (int i = 0; i < cnMonth.length; i++) 
		{
			if(cnDateStr.contains(cnMonth[i]))
			{
				cnDateStr = cnDateStr.replace(cnMonth[i], enMonth[i]);
				parsed = true;
				break;
			}
		}
		if(parsed)
		{
			String[] dateArr = cnDateStr.split("-");
			if(dateArr != null)
			{
				for (int i = dateArr.length-1; i >= 1; i--) {
					destStr = destStr + dateArr[i]+"-";
				}
				destStr = destStr + dateArr[0];
			}
		}
		else
		{
			destStr = cnDateStr;
		}
		return destStr;
	}
	/**
	 * ��ȡ�����꣬�£���
	 *@description  
	 *@date 2012-8-2 
	 *@author EX-ZHANGWEI001
	 *@param dateFlag
	 *@return}
	 */
	public static String getYearOrMonthOrDay(String dateFlag)
	{
		// �����ꡢ�¡���
		dateFlag = dateFlag.toLowerCase();
	    String date = formatDateToString(new Date());
	    String year = date.substring(0, 4);
	    String month = date.substring(5, 7);
	    String day = date.substring(8, 10);
	    if("yyyy".equals(dateFlag))
	    {
	    	return year;
	    }
	    else if("mm".equals(dateFlag))
	    {
	    	return month;
	    }
	    else
	    {
	    	return day;
	    }
	}
	
	/**��ȡ����
	 * 
	 * @param date ��ʱ��������� 2010-10 10:10:10
	 * @return ����ʱ��������� 2010-10-10
	 * @throws ParseException 
	 */
	public static Date getDateToDate(Date date) throws ParseException
	{
		return getDateToDate(date, DEFAULT_DATE_FORMAT);
	}
	
	/**��ȡ��ǰ����
	 * 
	 * @author : EX-CHENWEIXIAN001 ��Ω��
	 * @create_date ��2013-2-27 ����02:46:17
	 * @return
	 * @throws ParseException
	 */
	public static Date getToDay()
	{
		return new Date();
	}
	
	/**��ȡ����
	 * 
	 * @param date ��ʱ��������� 2010-10 10:10:10
	 * @return ����ʱ��������� 2010-10-10
	 * @throws ParseException 
	 */
	public static Date getDateToDate(Date date, String dateFormatString) throws ParseException
	{
		sdf = new SimpleDateFormat(dateFormatString);
		synchronized(sdf)
		{
		String dateString = sdf.format(date);
		
		return sdf.parse(dateString);
		}
	}
	
	/**�Ӽ�����ʱ��
	 * 
	 * @param date ���������ʱ��
	 * @param daynum ����(�����ʾ����ǰ�������ʾ�����)
	 * @return ����������
	 */
	public static Date apDatetime(Date date, int secondnum) throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, secondnum);  
		return calendar.getTime();
	}
	
	/**�Ӽ�����
	 * 
	 * @param date ���������
	 * @param daynum ����(�����ʾ����ǰ�������ʾ�����)
	 * @return ����������
	 */
	public static Date apDate(Date date, int daynum)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, daynum);  
		return calendar.getTime();
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(DateUtil.formatCnStringToDate("31-ʮ����-2099"));
	}
}
