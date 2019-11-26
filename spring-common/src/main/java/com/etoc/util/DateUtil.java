/**
 * Project Name:dsp
 * File Name:DateUtil.java
 * Package Name:com.etoc.dsp.util
 * Date:Apr 8, 2013 1:29:22 PM
 * Copyright (c) 2013, yumingtao@etoc.cn All Rights Reserved.
 *
 */

package com.etoc.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ClassName:DateUtil <br/>
 * Function: 提供字符和java日期类型的转换. <br/>
 * 
 * Date: Apr 8, 2013 1:29:22 PM <br/>
 * 
 * @author 于明涛
 * @version
 * @since JDK 1.7
 * @see
 */
public class DateUtil
{
    private final static Log logger = LogFactory.getLog(DateUtil.class);
    
    public final static String YEAR_SPLIT_FORMAT = "yyyy";
    
    public final static String MONTH_SPLIT_FORMAT = "yyyy-MM";
    
    public final static String DAY_SPLIT_FORMAT = "yyyy-MM-dd";
    
    public final static String MONTH_FORMAT = "yyyyMM";
    
    public final static String DAY_FORMAT = "yyyyMMdd";
    
    public final static String DATE_FORMAT = "yyyyMMddHHmmss";
    
    public final static String MONTH_DATE_FORMAT = "MMddHHmmss";
    
    public final static String DATE_MILLISECOND = "yyyyMMddHHmmssSSS";
    
    public final static String HOUR_MINUTE_SECOND = "HH:mm:ss";
    
    public final static String DATE_SPLIT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static Date convertString2Date(String dateStr, String formatStr)
    {
        DateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try
        {
            date = format.parse(dateStr);
            logger.debug(date.toString());
        }
        catch (ParseException e)
        {
            logger.debug(e.getMessage());
        }
        
        return date;
    }
    
    /**
     * 字符串转日期，将特定时区时间转成CST时间 <功能详细描述>
     * 
     * @param dateStr
     *            字符串日期
     * @param formatStr
     *            格式化字符串
     * @param zoneId
     *            时区ID，如GMT+8:00
     * @return 返回特定时区的日期时间，异常返回null
     * @see [类、类#方法、类#成员]
     */
    public static Date convertString2Date(String dateStr, String formatStr, String zoneId)
    {
        DateFormat format = new SimpleDateFormat(formatStr);
        format.setTimeZone(TimeZone.getTimeZone(zoneId));
        Date date = null;
        try
        {
            date = format.parse(dateStr);
        }
        catch (ParseException e)
        {
            logger.error(dateStr + " 转日期出错", e);
        }
        return date;
    }
    
    /**
     * 比较当前时间是否在某个时间段内
     * 
     * @param start
     * @param end
     * @return
     */
    public static boolean hasConvertDate(String dateStr, String formatStr)
    {
        DateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try
        {
            date = format.parse(dateStr);
            logger.debug(date.toString());
            return true;
        }
        catch (ParseException e)
        {
            logger.debug(e.getMessage());
        }
        return false;
    }
    
    /**
     * 查询当前时间，返回时间类型 <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date currentDate()
    {
        long dateLong = System.currentTimeMillis();
        Date date = new Date(dateLong);
        return date;
    }
    
    /**
     * 查询当前时间之后的某个时间，返回时间类型 <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date currentDate(int day, int hour, int minute)
    {
        long addTime = day * 24 * 60 * 60 * 1000;
        addTime += hour * 60 * 60 * 1000;
        addTime += minute * 60 * 1000;
        
        long dateLong = System.currentTimeMillis() + addTime;
        Date date = new Date(dateLong);
        return date;
    }
    
    /**
     * 查询当前时间，返回时间类型 <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Timestamp currentTimeStamp()
    {
        long dateLong = System.currentTimeMillis();
        Timestamp date = new Timestamp(dateLong);
        return date;
    }
    
    public static Timestamp dateToTimeStamp(Date date)
    {
        Timestamp ts = new Timestamp(date.getTime());
        return ts;
    }
    
    /**
     * 根据转换格式获取当前时间字符串类型 <功能详细描述>
     * 
     * @param formatStr
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String currentDate2String(String formatStr)
    {
        if (StringUtils.isEmpty(formatStr))
            formatStr = "yyyy-MM-dd HH:mm:ss";
        Date date = currentDate();
        return convertDate2String(date, formatStr);
    }
    
    /**
     * 根据转换格式获取当前时间字符串类型 <功能详细描述>
     * 
     * @param formatStr
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String currentDate2Timestamp(String formatStr)
    {
        if (StringUtils.isEmpty(formatStr))
            formatStr = "yyyyMMddHHmmss";
        Date date = currentDate();
        return convertDate2String(date, formatStr);
    }
    
    public static String convertDate2String(Date date)
    {
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "";
        try
        {
            dateStr = format.format(date);
            logger.debug(date.toString());
        }
        catch (Exception e)
        {
            logger.debug(e.getMessage());
        }
        
        return dateStr;
    }
    
    public static String convertDate2String(Date date, String formatStr)
    {
        
        DateFormat format = new SimpleDateFormat(formatStr);
        String dateStr = "";
        try
        {
            dateStr = format.format(date);
            logger.debug(date.toString());
        }
        catch (Exception e)
        {
            logger.debug(e.getMessage());
        }
        
        return dateStr;
    }
    
    public static Integer convertDate2Integer(Date date, String formatStr)
    {
        
        DateFormat format = new SimpleDateFormat(formatStr);
        String dateStr = "";
        try
        {
            dateStr = format.format(date);
            logger.debug(date.toString());
        }
        catch (Exception e)
        {
            logger.debug(e.getMessage());
        }
        
        return Integer.valueOf(dateStr);
    }
    
    public static String convertTimeStamp2String(Timestamp date)
    {
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "";
        try
        {
            dateStr = format.format(date);
            logger.debug(date.toString());
        }
        catch (Exception e)
        {
            logger.debug(e.getMessage());
        }
        
        return dateStr;
    }
    
    public static String convertTimeStamp2String(Timestamp date, String formatStr)
    {
        DateFormat format = new SimpleDateFormat(formatStr);
        String dateStr = "";
        try
        {
            dateStr = format.format(date);
            logger.debug(date.toString());
        }
        catch (Exception e)
        {
            logger.debug(e.getMessage());
        }
        
        return dateStr;
    }
    
    /**
     * 获取时差的绝对值
     * 
     * @param date
     * @return
     */
    public static long dateDiffer(Date date)
    {
        long nowTime = System.currentTimeMillis();
        long compareTime = date.getTime();
        return Math.abs(nowTime - compareTime);
    }
    
    /**
     * 比较当前时间是否在某个时间段内
     * 
     * @param start
     * @param end
     * @return
     */
    public static boolean dateDiffer(Date start, Date end)
    {
        long now = System.currentTimeMillis();
        long startLong = start.getTime();
        long endLong = end.getTime();
        if (startLong <= now && now <= endLong)
            return true;
        else
            return false;
    }
    
    /**
     * 获得该日期指定天数之前的日期
     * 
     * @param dtDate
     * @param lDays
     * @return 返回日期
     */
    public static Date before(Date dtDate, long lDays)
    {
        long lCurrentDate = 0;
        lCurrentDate = dtDate.getTime() - lDays * 24 * 60 * 60 * 1000;
        Date dtBefor = new Date(lCurrentDate);
        return dtBefor;
    }
    
    /**
     * 获得该日期指定天数之前的日期
     * 
     * @param dtDate
     * @param lDays
     * @return 返回日期
     */
    public static Date dateLaterMins(String executeTime, int quartzBlockTime)
    {
        Date dateAfter10Mins = null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            if (executeTime != null && StringUtils.isNotBlank(executeTime))
            {
                date = sdf.parse(executeTime);
            }
            else
            {
                date = new Date();
            }
            long threeminlater = date.getTime() + quartzBlockTime * 60000;
            dateAfter10Mins = new Date(threeminlater);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
        return dateAfter10Mins;
    }
    
    /**
     * 根据日期字符串（yyyymmdd）获取当天开始时间 20150511 转换为 2015-05-11 00:00:00
     * 
     * @param day
     * @param format
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date formatMinTimeInDate(String day, String format)
    {
        if (StringUtils.isEmpty(format))
            return null;
        Date date = convertString2Date(day, format);
        if (date == null)
            return date;
        return formatMinTimeInDate(date);
    }
    
    /**
     * 根据日期类型（yyyymmdd）获取当天开始时间 2015-05-11 21:12:12 转换为 2015-05-11 00:00:00
     * 
     * @param day
     * @param format
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date formatMinTimeInDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return formatMinTimeInDate(calendar);
    }
    
    /**
     * 根据日历控件获取当天开始时间 2015-05-11 21:12:12 转换为 2015-05-11 00:00:00
     * 
     * @param calendar
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date formatMinTimeInDate(Calendar calendar)
    {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        return calendar.getTime();
    }
    
    /**
     * 根据日期类型（yyyymmdd）获取当天开始时间 2015-05-11 21:12:12 转换为 2015-05-11 00:00:00
     * 
     * @param day
     * @param format
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date formatMinDayTimeInDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return formatMinDayTimeInDate(calendar);
    }
    
    /**
     * 根据日历控件获取当月开始时间 2015-05-11 21:12:12 转换为 2015-05-11 00:00:00
     * 
     * @param calendar
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date formatMinDayTimeInDate(Calendar calendar)
    {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        return calendar.getTime();
    }
    
    /**
     * 根据日期字符串（yyyymmdd）获取当天截止时间 20150511 转换为 2015-05-11 23:59:59
     * 
     * @param day
     * @param format
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date formatMaxTimeInDate(String day, String format)
    {
        if (StringUtils.isEmpty(format))
            return null;
        Date date = convertString2Date(day, format);
        if (date == null)
            return null;
        return formatMaxTimeInDate(date);
    }
    
    /**
     * 根据日期类型（yyyymmdd）获取当天截止时间 2015-05-11 12:51:31 转换为 2015-05-11 23:59:59
     * 
     * @param day
     * @param format
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date formatMaxTimeInDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return formatMaxTimeInDate(calendar);
    }
    
    /**
     * 根据日历控件获取当天截止时间 2015-05-11 12:51:31 转换为 2015-05-11 23:59:59
     * 
     * @param calendar
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date formatMaxTimeInDate(Calendar calendar)
    {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        
        return calendar.getTime();
    }
    
    /**
     * 获得该日期指定天数之前的日期
     * 
     * @param dtDate
     * @param lDays
     * @return 返回日期
     */
    public static Date beforeDay(Date date, int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days * -1);
        return calendar.getTime();
    }
    
    /**
     * 获得该日期指定数月之前的日期
     * 
     * @param dtDate
     * @param lDays
     * @return 返回日期
     */
    public static Date beforeMonth(Date date, int months)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months * -1);
        return calendar.getTime();
    }
    
    
    /**
     * <获取当月最后一天最后一秒> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public static Date getLastDayOfmonth()
    {
        
        // 获取当前年份、月份、日期
        Calendar cale = null;
        cale = Calendar.getInstance();
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String lastday;
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime()) + " 23:59:59";
        // System.out.println("本月第一天和最后一天分别是 ： and " + lastday);
        Date lastdate = convertString2Date(lastday, DATE_SPLIT_FORMAT);
        return lastdate;
    }
    
    /**
     * <获取当月第一天第一秒> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public static Date getFirstDayOfmonth()
    {
        
        // 获取当前年份、月份、日期
        Calendar cale = null;
        cale = Calendar.getInstance();
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday;
        
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime()) + " 00:00:00";
        
        // System.out.println("本月第一天和最后一天分别是 ： and " + lastday);
        Date firstdate = convertString2Date(firstday, DATE_SPLIT_FORMAT);
        return firstdate;
    }
    
    /**
     * <获取当月最后一天最后一秒> <功能详细描述>
     * 
     * @throws ParseException
     * @see [类、类#方法、类#成员]
     */
    public static Date getLastDayOfmonth(String str)
        throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = sdf.parse(str);
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String lastday;
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime()) + " 23:59:59";
        // System.out.println("本月第一天和最后一天分别是 ： and " + lastday);
        Date lastdate = convertString2Date(lastday, DATE_SPLIT_FORMAT);
        return lastdate;
    }
    
    /**
     * <获取当月第一天第一秒> <功能详细描述>
     * @throws ParseException 
     * 
     * @see [类、类#方法、类#成员]
     */
    public static Date getFirstDayOfmonth(String str)
        throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = sdf.parse(str);
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday;
        // 获取前月的第一天
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime()) + " 00:00:00";
        // System.out.println("本月第一天和最后一天分别是 ： and " + lastday);
        Date firstdate = convertString2Date(firstday, DATE_SPLIT_FORMAT);
        return firstdate;
    }
    
    /**
     * 
     * 描述:获取下一个月.
     * 
     * @return
     */
    public static String getNextMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        String nextMonth = dft.format(cal.getTime());
        return nextMonth;
        
    }
    
    public static void main(String[] args)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        String preMonth = dft.format(cal.getTime());
        System.out.println(preMonth);
    }
    
}
