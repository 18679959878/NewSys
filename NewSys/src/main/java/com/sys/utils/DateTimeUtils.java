package com.sys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	// ʱ������ת����ʽ
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * �ַ���ת����
	 * 
	 * @author ��С��
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String date) throws ParseException {
		return simpleDateFormat.parse(date);
	}

	/**
	 * ����ת�ַ���
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		return simpleDateFormat.format(date);
	}

}
