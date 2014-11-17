package com.tcl.aota.common.utils;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kelong on 9/2/14.
 */
public class DateUtil {
	
	private final static Logger LOG = Logger.getLogger(DateUtil.class);
	
    /**
     * 时间转换为字符串
     *
     * @param format：格式方式
     * @param date：时间
     * @return
     */
    public static String fomartDateToStr(String format, Date date) {
        String dateString = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            dateString = formatter.format(date);
        } catch (Exception e) {
        	LOG.error(e.getMessage(), e);
        }
        return dateString;
    }
    
    /**
     * 
     * @param days
     * @return
     */
 	public static String getBeforeDays(int days) {
 		Calendar c = Calendar.getInstance();
 		c.add(Calendar.DATE, -days);
 		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
 	}
 	
 	/**
     * 
     * @param hours
     * @return
     */
 	public static String getBeforeHours(int hours) {
 		Calendar c = Calendar.getInstance();
 		c.add(Calendar.HOUR, -hours);
 		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
 	}
}