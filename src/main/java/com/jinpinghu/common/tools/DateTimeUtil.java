package com.jinpinghu.common.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;


public class DateTimeUtil {
	/**
	 * 将日期格式化成 YYYY-MM-dd
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(date!=null){
			return df.format(date);
		}else{
			return "";
		}
	}
	
	public static Integer getAge(Date birthday) {
		Calendar c = Calendar.getInstance();
		
		if(birthday == null || c.getTime().before(birthday)) {
			return 0;
		}else {
			Integer yearNow = c.get(Calendar.YEAR);
			Integer monthNow = c.get(Calendar.MONTH);
			Integer dayNow = c.get(Calendar.DAY_OF_MONTH);
			
			c.setTime(birthday);
			
			Integer yearBir = c.get(Calendar.YEAR);
			Integer monthBir = c.get(Calendar.MONTH);
			Integer dayBir = c.get(Calendar.DAY_OF_MONTH);
			
			Integer age = yearNow - yearBir;
			
			if(monthNow < monthBir) {
				age --;
			}else {
				if(monthNow == monthBir) {
					if(dayNow<dayBir) {
						age --;
					}
				}
			}
			
			return age;
		}
	}
	
	/**
	 * 将字符串格式化成 YYYY-MM-dd 日期
	 * @param date
	 * @return
	 */
	public static Date formatTime(String date){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if(date!=null){
				return df.parse(date);
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将日期格式化成 YYYY-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatTime2(Date date){
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(date!=null){
			return dfs.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 将字符串格式化成 YYYY-MM-dd HH:mm:ss 日期
	 * @param date
	 * @return
	 */
	public static Date formatTime5(String date){
		try {
			DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			if(date!=null){
				return dfs.parse(date);
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将日期格式化成 YYYY-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatTime5(Date date){
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		if(date!=null){
			return dfs.format(date);
		}else{
			return "";
		}
	}
	/**
	 * 将字符串格式化成 YYYY-MM-dd HH 日期
	 * @param date
	 * @return
	 */
	public static Date formatTime9(String date){
		try {
			DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH");
			if(date!=null){
				return dfs.parse(date);
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将日期格式化成 YYYY-MM-dd HH
	 * @param date
	 * @return
	 */
	public static String formatTime9(Date date){
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH");
		if(date!=null){
			return dfs.format(date);
		}else{
			return "";
		}
	}
	/**
	 * 将字符串格式化成 YYYY-MM-dd HH:mm:ss 日期
	 * @param date
	 * @return
	 */
	public static Date formatTime2(String date){
		try {
			DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(date!=null){
				return dfs.parse(date);
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将日期格式化成 YYYY-MM-dd HH:mm
	 * @param date
	 * @return
	 */
	public static String formatTime3(Date date){
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(date!=null){
			return dfs.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 将字符串格式化成 YYYY-MM-dd HH:mm 日期
	 * @param date
	 * @return
	 */
	public static Date formatTime3(String date){
		try {
			DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(date!=null){
				return dfs.parse(date);
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String formatSelf(Date date,String f){
		if(date!=null){
			DateFormat d = new SimpleDateFormat(f);
			return d.format(date);
		}else{
			return "";
		}
	}
	
	public static String formatTime6(Date date){
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		if(date!=null){
			return df.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 将字符串格式化成 YYYY-MM 日期
	 * @param date
	 * @return
	 */
	public static Date formatTime6(String date){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			if(date!=null){
				return df.parse(date);
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date formatSelf(String date,String f){
		try {
			if(date!=null){
				DateFormat d = new SimpleDateFormat(f);
				return d.parse(date);
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 邦帮团开始时间format
	 */
	public static String openTimeSmartFormat(Date date) {
		Calendar c = Calendar.getInstance();
		DateFormat dfs7 = new SimpleDateFormat("HH:mm");
		DateFormat dfs6 = new SimpleDateFormat("MM月dd日 HH:mm");
		long oneDay = 60*60*24*1000;
		if(date!=null){
			String result = "";
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			long today = c.getTimeInMillis();//yyyyMd
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			long formatDay = c.getTimeInMillis();
			if(today == formatDay){
				result = dfs7.format(date);
			}else if((formatDay-today)==oneDay){
				result = "明日 "+dfs7.format(date);
			}else{
				result = dfs6.format(date);
			}
			return result;
		}else{
			return "";
		}
	}
	
	/**
	 * 获取今日日期字符串 YYYY-MM-dd
	 * @return
	 */
	public static String getTodaySTR(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}
	
	/**
	 * 获取今日日期 YYYY-MM-dd
	 * @return
	 */
	public static Date getTodayDATE(){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.parse(getTodaySTR());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取本月第一天字符串 YYYY-MM-dd
	 * @return
	 */
	public static String getMonthFirstDaySTR(){
		Calendar c = Calendar.getInstance();
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		c.setTime(new Date()); 
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return dfs.format(c.getTime());
	}
	
	/**
	 * 获取本月最后一天字符串 YYYY-MM-dd
	 * @return
	 */
	public static String getMonthLastDaySTR(){
		Calendar c = Calendar.getInstance();
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		c.setTime(new Date()); 
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return dfs.format(c.getTime());
	}
	
	/**
	 * 获取某月第一天字符串 YYYY-MM-dd
	 * @return
	 */
	public static String getMonthFirstDaySTR(Date date){
		Calendar c = Calendar.getInstance();
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		c.setTime(date); 
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return dfs.format(c.getTime());
	}
	
	/**
	 * 获取某月最后一天字符串 YYYY-MM-dd
	 * @return
	 */
	public static String getMonthLastDaySTR(Date date){
		Calendar c = Calendar.getInstance();
		DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		c.setTime(date); 
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return dfs.format(c.getTime());
	}
	
	/**
	 * 获取本周第一天字符串 YYYY-MM-dd
	 * @return
	 */
	public static String getWeekFirstDaySTR(){
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		int d = 0;
		if(c.get(Calendar.DAY_OF_WEEK)==1){
			d = -6;
		}else{
			d = 2-c.get(Calendar.DAY_OF_WEEK);
		}
		c.add(Calendar.DAY_OF_WEEK, d);
        return df.format(c.getTime());
	}
	
	/**
	 * 获取本周最后一天字符串 YYYY-MM-dd
	 * @return
	 */
	public static String getWeekLastDaySTR(){
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		int d = 0;
		if(c.get(Calendar.DAY_OF_WEEK)==1){
			d = -6;
		}else{
			d = 2-c.get(Calendar.DAY_OF_WEEK);
		}
		c.add(Calendar.DAY_OF_WEEK, d);
		c.add(Calendar.DAY_OF_WEEK, 6);
        return df.format(c.getTime());
	}
	
	/**
	 * 获取星期
	 * @return
	 */
	public static int getDayOfWeek(String date){
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int i = 0;
		try {
			c.setTime(df.parse(date));
			i = c.get(Calendar.DAY_OF_WEEK)-1;
		} catch (ParseException e) {
			e.printStackTrace();
		} 
        return i;
	}
	
	public static String getNowHour(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int hour=c.get(Calendar.HOUR_OF_DAY);
        if(hour<10){
        	return "0"+hour;
        }else{
        	return hour+"";
        }
	}
	
	public static String addHour(Date date,Integer n){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, n);
		int hour=c.get(Calendar.HOUR_OF_DAY);
        if(hour<10){
        	return "0"+hour;
        }else{
        	return hour+"";
        }
	}
	
	public static String addDay(String date,int addDay){
		try {
			Calendar c = Calendar.getInstance();
			DateFormat 	df = new SimpleDateFormat("yyyy-MM-dd");
			c.setTime(df.parse(date));
			c.add(Calendar.DATE, addDay);
			return df.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
		
	}
	
	public static Date addDay(Date date,int addDay){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, addDay);
		return c.getTime();
		
	}
	public static Date addHour(Date date,int adHour){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, adHour);
		return c.getTime();
		
	}
	public static String addMonth(String date,int addMonth){
		try {
			Calendar c = Calendar.getInstance();
			DateFormat 	df = new SimpleDateFormat("yyyy-MM");
			c.setTime(df.parse(date));
			c.add(Calendar.MONTH, addMonth);
			return df.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return date;
		}
		
	}
	public static Date addMonth(Date date,int m){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, m);
			return c.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static int getYear(Date date){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.YEAR);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int getMonth(Date date){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.MONTH);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int getDay(Date date){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static String getHour(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour=c.get(Calendar.HOUR_OF_DAY);
        if(hour<10){
        	return "0"+hour;
        }else{
        	return hour+"";
        }
	}
	
	public static String getMin(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
        int min = c.get(Calendar.MINUTE);
        if(min<10){
        	return "0"+min;
        }else{
        	return min+"";
        }
	}
	
	public static String smartFormat(Date date){
		Calendar c = Calendar.getInstance();
		DateFormat dfs7 = new SimpleDateFormat("HH:mm");
		DateFormat dfs6 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		DateFormat dfs5 = new SimpleDateFormat("MM-dd HH:mm");
		long oneDay = 60*60*24*1000;
		if(date!=null){
			String result = "";
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			long today = c.getTimeInMillis();//yyyyMd
			long nYear=c.get(Calendar.YEAR);
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			long formatDay = c.getTimeInMillis();
			long year=c.get(Calendar.YEAR);
			if(today == formatDay){
				result = dfs7.format(date);
			}else if((today-formatDay)==oneDay){
				result = "昨天 "+dfs7.format(date);
			}else if((today-formatDay)==oneDay*2){
				result = "前天 "+dfs7.format(date);
			}else if(nYear==year){
				result = dfs5.format(date);
			}else{
				result = dfs6.format(date);
			}
			return result;
		}else{
			return "";
		}
	}
	/**
	 * yyyy-MM-dd HH:mm:s  转换成时间戳
	 */
	public static String formatTime4(String date){
		try {
			DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return String.valueOf(dfs.parse(date).getTime()/1000);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * yyyy-MM-dd HH:mm:s  时间戳转换
	 */
	public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**获取上周的开始时间
     * 
     * @return
     */
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }
    /**获取上周的结束时间
     * 
     * @return
     */
    public static Date getEndDayOfLastWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }
    /**获取上月的开始时间
     * 
     * @return
     */
    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }
    /**获取上月的结束时间
     * 
     * @return
     */
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }

  //获取某个日期的开始时间
    public static Date getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }
    //获取某个日期的结束时间
    public static Date getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Date(calendar.getTimeInMillis());
    }
  //获取今年是哪一年
    public static Integer getNowYear() {
            Date date = new Date();
           GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
           gc.setTime(date);
           return Integer.valueOf(gc.get(1));
       }
    //获取本月是哪一月
    public static int getNowMonth() {
            Date date = new Date();
           GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
           gc.setTime(date);
           return gc.get(2) + 1;
       }


	/**
	 * 获取HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatTime7(Date date){
		DateFormat dfs = new SimpleDateFormat("HH:mm:ss");
		if(date!=null){
			return dfs.format(date);
		}else{
			return "";
		}
	}
	/**
	 * 获取HH:mm:ss
	 * @param date
	 * @return
	 */
	public static Date formatTime7(String date){
		try {
			DateFormat dfs = new SimpleDateFormat("HH:mm:ss");
			if(date!=null){
				return dfs.parse(date);
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取HH:mm
	 * @param date
	 * @return
	 */
	public static String formatTime8(Date date){
		DateFormat dfs = new SimpleDateFormat("HH:mm");
		if(date!=null){
			return dfs.format(date);
		}else{
			return "";
		}
	}
	/**
	 * 获取HH:mm
	 * @param date
	 * @return
	 */
	public static Date formatTime8(String date){
		try {
			DateFormat dfs = new SimpleDateFormat("HH:mm");
			if(date!=null){
				return dfs.parse(date);
			}else{
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 判断某一时间是否在某一时间段内
	 */
	public static boolean isInTime(Date time,String startDayStr,String endDayStr,String startHourStr,String endHourStr) {
		if(!StringUtils.isEmpty(startDayStr) && !StringUtils.isEmpty(endDayStr)) {
			Date startDay = formatSelf(startDayStr,"yyyy-MM-dd");
			Date endDay = formatSelf(endDayStr,"yyyy-MM-dd");
			if(time.after(addDay(endDay,1)) ||time.before(startDay)) {
				return false;
			}
		}
		
		if(!StringUtils.isEmpty(startHourStr) && !StringUtils.isEmpty(endHourStr)) {
			Date startHour = formatSelf(startHourStr,"HH:mm:ss");
			Date endHour = formatSelf(endHourStr,"HH:mm:ss");
			Date nowHour = formatSelf(formatSelf(time,"HH:mm:ss"),"HH:mm:ss");
			if(nowHour.after(endHour) || nowHour.before(startHour)) {
				return false;
			}
		}
		
		
		return true;
	}
	
	public static double getDateIffDay(Date startTime,Date endTime) {
		if(startTime == null || endTime == null || startTime.after(endTime))
			return 0;
		
		long start = startTime.getTime();
		long end = endTime.getTime();
		
		double hour = (end-start)/(double)1000/(double)60/(double)60/(double)24;
		return (double)Math.round(hour*10)/10;
	}
	
	/**
	 * 获取两个日期直接相隔天数
	 * @author Ejectam719
	 * @createTime 2016年8月27日 14:48:41
	 * @updateTime 2016年8月27日 15:17:41
	 * @param minuendDate
	 * @param subtrahendDate
	 * @return
	 */
	public static int dateSubtraction(Date minuendDate, Date subtrahendDate){
		long minuend = minuendDate.getTime();
		long subtrahend = subtrahendDate.getTime();
		long days = (minuend - subtrahend) / (1000 * 60 * 60 * 24);
		return (int)days;
	}
	
	public static String firstDayOfMonth(String month) {
		if (StringUtils.isEmpty(month) || formatSelf(month, "yyyy-MM") == null) {
			return "";
		}
		return month+"-01";
	}
	
	public static String lastDayOfMonth(String month) {
		if (StringUtils.isEmpty(month) || formatSelf(month, "yyyy-MM") == null) {
			return "";
		}
		return formatSelf(addDay(addMonth(DateTimeUtil.formatSelf(month+"-01", "yyyy-MM-dd"), 1),-1),"yyyy-MM-dd");
	}
	
	public static void main(String[] args) {
		System.out.println(firstDayOfMonth("2019-"));
	}


}
