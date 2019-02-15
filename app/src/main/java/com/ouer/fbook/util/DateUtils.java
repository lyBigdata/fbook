package com.ouer.fbook.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateUtils {

	private static final String log_mask = "[date convert error]string=%d";
//	private static final SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
//	private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
//	private static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
//	private static final SimpleDateFormat yyyyMMdd2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
	
	private static final ThreadLocal<SimpleDateFormat> yyyyMMddHHmm = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		}
	};
	private static final ThreadLocal<SimpleDateFormat> yyyyMMddHHmmss = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		}
	};
	private static final ThreadLocal<SimpleDateFormat> yyyyMMddHHmmss2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
		}
	};
	private static final ThreadLocal<SimpleDateFormat> yyyyMMdd = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
		}
	};
	private static final ThreadLocal<SimpleDateFormat> yyyyMMdd2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		}
	};

	private static final ThreadLocal<SimpleDateFormat> yyyyMMddDot = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
		}
	};

	public static Date getDateFromyyyTimestamp(String dateString) {
		Date date = null;
		if (StringUtils.isNotEmpty(dateString)) {
			long tm = ConvertUtils.str2long(dateString);
			if (tm > 0) {
				date = new Date();
			}
		}
		return date;
	}

	public static Date getDateFromyyyyMMdd(String dateString) {
		Date date = null;
		try {
			if (StringUtils.isNotEmpty(dateString)) {
				date = yyyyMMdd.get().parse(dateString);
			}
		} catch (ParseException e) {
			LogUtils.e(String.format(log_mask, dateString), e);
		}
		return date;
	}

	public static Date getDateFromyyyyMMdd2(String dateString) {
		Date date = null;
		try {
			if (StringUtils.isNotEmpty(dateString)) {
				date = yyyyMMdd2.get().parse(dateString);
			}
		} catch (ParseException e) {
			LogUtils.e(String.format(log_mask, dateString), e);
		}
		return date;
	}

	public static Date getDateFromYyyyMMddHHmm(String dateString) {
		Date date = null;
		try {
			if (StringUtils.isNotEmpty(dateString)) {
				date = yyyyMMddHHmm.get().parse(dateString);
			}
		} catch (ParseException e) {
			LogUtils.e(String.format(log_mask, dateString), e);
		}
		return date;
	}

	public static Date getDateFromYyyyMMddHHmmss(String dateString) {
		Date date = null;
		try {
			if (StringUtils.isNotEmpty(dateString)) {
				date = yyyyMMddHHmmss.get().parse(dateString);
			}
		} catch (ParseException e) {
			LogUtils.e(String.format(log_mask, dateString), e);
		}
		return date;
	}

	public static Date getDateFromYyyyMMddHHmmss2(String dateString) {
		Date date = null;
		try {
			if (StringUtils.isNotEmpty(dateString)) {
				date = yyyyMMddHHmmss2.get().parse(dateString);
			}
		} catch (ParseException e) {
			LogUtils.e(String.format(log_mask, dateString), e);
		}
		return date;
	}
	/*
        * 将时间戳转换为时间
        */
	public static String stampToDate(Long s){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(s);
		res = simpleDateFormat.format(date);
		return res;
	}
	public static String getFullStringFromDate(Date date) {
		return yyyyMMddHHmmss.get().format(date);
	}

	public static String getSimpleStringFromDate(Date date) {
		return yyyyMMdd2.get().format(date);
	}


	public static String getSimpleFullDate(Date date) {
		return yyyyMMdd2.get().format(date) + " 00:00:00";
	}

	public static Date getStringFromDateDot(String dateString) {
		Date date = null;
		try {
			if (StringUtils.isNotEmpty(dateString)) {
				date = yyyyMMddDot.get().parse(dateString);
			}
		} catch (ParseException e) {
			LogUtils.e(String.format(log_mask, dateString), e);
		}
		return date;
	}

}
