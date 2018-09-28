package com.sys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	// 时间日期转换格式
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 字符串转日期
	 * 
	 * @author 金小瑶
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String date) throws ParseException {
		return simpleDateFormat.parse(date);
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		return simpleDateFormat.format(date);
	}

}
