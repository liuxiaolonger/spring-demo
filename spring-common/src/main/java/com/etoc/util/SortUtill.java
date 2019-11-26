package com.etoc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *.排序工具
 * <功能详细描述>
 * 
 * @author  Auser
 * @version  [版本号, 2019年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SortUtill
{
    
    /**
     * .将前台输入的排序格式(例如{"+code","-name"}),封装成数组
     * <功能详细描述>
     * @param sort
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<String> getSorts(String[] sort)
    {
        List<String> list=new ArrayList<>();
       // String[] sort= {"+code","-name"};
       // Map<String, String> map=new HashMap<>();
        for (int i = 0; i < sort.length; i++)
        {
            StringBuffer buffer=new StringBuffer();
            String orderBy=sort[i].substring(1, sort[i].length());
            String order=sort[i].substring(0, 1);
            if ("-".equals(order))
            {
                order="desc";
            }else {
                order="asc";
            }
            //这里会将排序的字段*1  数据库会默认转换为数字进行排序
            buffer.append(orderBy).append("*1").append(" ").append(order);
            list.add(buffer.toString());
            //map.put(orderBy, flag);
        }
        //System.out.println(list);
        return list;
        
    }
    
    public static void main(String[] args)
    {
        List<String> list=new ArrayList<>();
        String[] sort= {"+code","-name"};
       // Map<String, String> map=new HashMap<>();
        for (int i = 0; i < sort.length; i++)
        {
            StringBuffer buffer=new StringBuffer();
            String orderBy=sort[i].substring(1, sort[i].length());
            String order=sort[i].substring(0, 1);
            if ("-".equals(order))
            {
                order="desc";
            }else {
                order="asc";
            }
            buffer.append(orderBy).append("*1").append(" ").append(order);
            list.add(buffer.toString());
            //map.put(orderBy, flag);
        }
        System.out.println(list);
        
        
    }
}
