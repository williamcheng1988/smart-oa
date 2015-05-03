package com.chz.smartoa.common.page;
public class PageCalculation
{
    public static int getMaxPageNo(int pageSize,int totalRows){
        if(pageSize==0){
            return 1;
        }else  if(totalRows<=pageSize){
            return 1;
        }else{
            return  new Double(Math.ceil(totalRows*1d/pageSize)).intValue();
        }
    }
    public static int[] startEndNum(int pageNo,int pageSize){
        if(pageNo<1){
            pageNo=1;
        }
        if(pageSize>150){
            pageSize=150;
        }
        int[] calculation = new int[2];
        calculation[0]   = pageSize*(pageNo-1);
        calculation[1]   = calculation[0]+pageSize;
        return  calculation;
    }
    public static int[] startEndNum(int pageNo,int pageSize,int totalRows){
        if(pageNo<1){
            pageNo=1;
        }
        if(pageSize>50){
            pageSize=50;
        }
        int maxPageNo = getMaxPageNo(pageSize,totalRows);
        if(pageNo>maxPageNo){
            pageNo=maxPageNo;
        }
        int start = (pageNo-1)*pageSize;
        int end   = start+pageSize;
        if(end>totalRows){
            end = totalRows;
        }
        return new int[]{start,end};
    }
    
}
