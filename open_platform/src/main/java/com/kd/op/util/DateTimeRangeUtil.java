package com.kd.op.util;

import com.kd.op.api.controller.MonitorController;
import com.kd.op.common.CustomConstant;
import org.apache.log4j.Logger;
import sun.applet.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther:张健云
 * @Description：日期时间范围工具
 * @DATE：2019/1/15 9:10
 */
public class DateTimeRangeUtil {

//    public static final SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
//    public static final SimpleDateFormat sdf_yyyy_MM = new SimpleDateFormat("yyyy-MM");
//    public static final SimpleDateFormat sdf_yyyy= new SimpleDateFormat("yyyy");
    //保证时间差在一年
    private static final int yearScale = 366;
    //保证时间差在一个月
    private static final int monthScale = 31;
    private final static Logger logger = Logger.getLogger(MonitorController.class);

    /**
     * 获取两个时间中的每一天
     * @param sStartTime
     * @param sEndTime
     * @return
     */
    public static synchronized List<Date> getPerDay(String sStartTime, String sEndTime){
        //时间格式化
        Date startTime = new Date();
        Date endTime = new Date();
        SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startTime = sdf_yyyy_MM_dd.parse(sStartTime);
            endTime = sdf_yyyy_MM_dd.parse(sEndTime);
        } catch (ParseException e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        //定义一个接受时间的集合
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(startTime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(startTime);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endTime);
        // 测试此日期是否在指定日期之后
        while (endTime!=null && endTime.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 获取一天内的整点时刻
     * @return
     */
    public static List<String> getDayHour(){
        List<String> hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if(i<10){
                hours.add("0"+i);
            }else {
                hours.add(i+"");
            }
        }
        return hours;
    }

    /**
     * 获取两个时间中的每一个月
     * @param sStartTime
     * @param sEndTime
     * @return
     */
    public static synchronized List<Date> getPerMonth(String sStartTime, String sEndTime){
        //时间格式化
        Date startTime = null;
        Date endTime = null;
        SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startTime = sdf_yyyy_MM_dd.parse(sStartTime);
            endTime = sdf_yyyy_MM_dd.parse(sEndTime);
        } catch (ParseException e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        //定义一个接受时间的集合
        List<Date> lMonth = new ArrayList<Date>();
        lMonth.add(startTime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(startTime);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endTime);
        // 测试此日期是否在指定日期之后
        while (endTime!=null && endTime.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.MONTH, 1);
            lMonth.add(calBegin.getTime());
        }
        //如果开始时间的日期比结束时间的小，那么会多计算一个月
        if(calEnd.get(Calendar.DATE)>calBegin.get(Calendar.DATE)){
            lMonth.remove(lMonth.size()-1);
        }
        return lMonth;
    }

    /**
     * 获取两个时间中的每一年
     * @param sStartTime
     * @param sEndTime
     * @return
     */
    public static synchronized List<Date> getPerYear(String sStartTime, String sEndTime ){
        //时间格式化
        Date startTime = null;
        Date endTime = null;
        SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startTime = sdf_yyyy_MM_dd.parse(sStartTime);
            endTime = sdf_yyyy_MM_dd.parse(sEndTime);
        } catch (ParseException e) {
            logger.error("error:",e);
//            e.printStackTrace();
        }
        //定义一个接受时间的集合
        List<Date> lYear = new ArrayList<Date>();
        lYear.add(startTime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间

        calBegin.setTime(startTime);

        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间

        calEnd.setTime(endTime);

        // 测试此日期是否在指定日期之后
        while (endTime!=null && endTime.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.YEAR, 1);
            lYear.add(calBegin.getTime());
        }
        //如果开始时间的月比结束时间的小，那么会多计算一年
        if(calEnd.get(Calendar.MONTH)>calBegin.get(Calendar.MONTH)){
            lYear.remove(lYear.size()-1);
        }
        //如果开始时间和相同结束时间的月相同，日期比结束时间的小，那么会多计算一年
        if(calEnd.get(Calendar.MONTH) == calBegin.get(Calendar.MONTH) &&
                calEnd.get(Calendar.DATE)>calBegin.get(Calendar.DATE)){
            lYear.remove(lYear.size()-1);
        }
        return lYear;
    }

    /**
     * 获取给定日期前后任意天的日期
     * @param date
     * @param dayNum 提前或者推后的天数  提前中正数 推后用负数
     * @return
     */
    public static Date getDateBoforeOrAfterAnyDays(Date date,Integer dayNum){
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(date);
        calBegin.add(Calendar.DATE,dayNum);
        return calBegin.getTime();
    }

    /**
     * 根据起始时间计算时间刻度 按天 按月 按年
     * @param sStartTime
     * @param sEndTime
     * @return
     */
    public static List<String> getTimeScale(String sStartTime,String sEndTime){
        List<String> timeScale = new ArrayList<>();
        List<Date> result = null;
        if(sStartTime.length()>10 || sStartTime.equals(sEndTime)){
            return getDayHour();
        }else if(getPerDay(sStartTime,sEndTime).size()>yearScale){
            SimpleDateFormat sdf_yyyy= new SimpleDateFormat("yyyy");
            //按年计算
            result = getPerYear(sStartTime,sEndTime);
            result.forEach(p->{
                timeScale.add(sdf_yyyy.format(p));
            });
        }else if(getPerDay(sStartTime,sEndTime).size()>monthScale){
            SimpleDateFormat sdf_yyyy_MM = new SimpleDateFormat("yyyy-MM");
            //按月计算
            result = getPerMonth(sStartTime,sEndTime);
            result.forEach(p->{
                timeScale.add(sdf_yyyy_MM.format(p));
            });
        }else{
            SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
            result = getPerDay(sStartTime,sEndTime);
            result.forEach(p->{
                timeScale.add(sdf_yyyy_MM_dd.format(p));
            });
        }
        return timeScale;
    }

    /**
     * 获取格式化时间格式
     * @param sStartTime
     * @param sEndTime
     * @return
     */
    public static String getDateFormat(String sStartTime,String sEndTime){
        String dateFormat = CustomConstant.DATE_FORMAT_DAY;
        List<Date> result = getPerDay(sStartTime,sEndTime);
        if(sEndTime.length()>10 || sEndTime.equals(sStartTime)){
            dateFormat = CustomConstant.DATE_FORMAT_HOUR;
        }else if(result.size()>yearScale){
            dateFormat = CustomConstant.DATE_FORMAT_YEAR;
        }else if(result.size()>monthScale){
            dateFormat = CustomConstant.DATE_FORMAT_MONTH;
        }
        return dateFormat;
    }

}
