package com.chz.smartoa.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

/**BigDecimal ��Ӧ�ļӼ��˳��ʽ���ȷ���<br />
 * 
 * @author wesson
 *2014-12-05
 */
public class NumberUtils {

	/**
	 * Ĭ�Ϸ���С��2λ
	 */
	public static final int DEFAULT_SCALE = 2;
	
	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ��������ĺ�
	 */
	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		return b1.add(b2);
	}

	/**
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ��������Ĳ�
	 */
	public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
		return b1.subtract(b2);
	}

	/**
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ��������Ļ�
	 */
	public static BigDecimal mul(BigDecimal b1, BigDecimal b2) {
		return b1.multiply(b2);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ����㣬�����������ʱ����ȷ�� С����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return �����������
	 */
	public static BigDecimal div(BigDecimal b1, BigDecimal b2) {
		return div(b1, b2, DEFAULT_SCALE);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ����㡣�����������ʱ����scale����ָ �����ȣ��Ժ�������������롣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @param scale ��ʾ��ʾ��Ҫ��ȷ��С����Ժ�λ��
	 * @return �����������
	 */
	public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("Ĭ��С��λ������ڻ���0��");
		}
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦�?
	 * 
	 * @param v ��Ҫ�������������
	 * @param scale С��������λ
	 * @return ���������Ľ��
	 */
	public static BigDecimal round(BigDecimal b, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("Ĭ��С��λ������ڻ���0��");
		}
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * ��ʽ����Ϣ����
	 * 
	 * @param v ��Ҫ�������������
	 * @param scale С��������λ
	 * @return ��ʽ����Ľ��
	 */
	public static String format(BigDecimal b, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("Ĭ��С��λ������ڻ���0��");
		}
		StringBuffer formatString = new StringBuffer("###,##0");
		if (scale > 0)
		{
			formatString.append(".");
			for (int i = 0; i < scale; i++)
			{
				formatString.append("0");
			}
		}
    	NumberFormat numberFormat = new DecimalFormat(formatString.toString()); 
		
    	return numberFormat.format(b);
	}

	/**�ַ�ת��Ϊ�������
	 * 
	 * @param str
	 * @return
	 */
	public static BigDecimal stringToBigDecimal(String str)
	{
		if (!StringUtils.isEmpty(str))
		{
			return new BigDecimal(str);
		}
		return null;
	}
	/**
	 * ����
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BigDecimal v1 = new BigDecimal("10.58");
		BigDecimal v2 = new BigDecimal("5");
		BigDecimal v3 = new BigDecimal("5555555555.2722222226");
		System.out.println(NumberUtils.add(v1, v2));
		System.out.println(NumberUtils.sub(v1, v2));
		System.out.println(NumberUtils.mul(v1, v2));
		System.out.println(NumberUtils.div(v1, v2));
		System.out.println(NumberUtils.div(v1, v2,3));
		System.out.println(NumberUtils.round(v3, 1));
		System.out.println(NumberUtils.format(v3, 5));
		
	}
}
