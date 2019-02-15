package com.ouer.fbook.util;

import java.math.BigDecimal;

public class ConvertUtils {
	

	public static long str2long(String str) {
		long result = -1;
		if (StringUtils.isNotEmpty(str)) {
			try {
				result = Long.parseLong(str);
			} catch (NumberFormatException ex) {
				LogUtils.e("str2long error:" + str, ex);
			}
		}
		return result;
	}
	
	public static int str2int(String str) {
		int result = -1;
		if (StringUtils.isNotEmpty(str)) {
			try {
				result = Integer.parseInt(str);
			} catch (NumberFormatException ex) {
				LogUtils.e("str2int error:" + str, ex);
			}
		}
		return result;
	}
	
	public static BigDecimal str2bigdecimal(String str) {
		BigDecimal result = BigDecimal.ZERO;
		if (StringUtils.isNotEmpty(str)) {
			result = new BigDecimal(str);
		}
		return result;
	}
}
