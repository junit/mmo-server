package com.game.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class TimeUtil {
	private static Logger logger = Logger.getLogger(TimeUtil.class);
	
	/**
	 * 字符串转日期("yyyy-MM-dd HH:mm:ss");
	 * @param date
	 * @return
	 */
	public static Date getDateByString(String date) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("{}日期格式有误{}" + date + "yyyy-MM-dd HH:mm:ss");
			return null;
		}
	}
}
