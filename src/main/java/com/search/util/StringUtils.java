package com.search.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils {

	/**
	 * String to Unicode
	 * 
	 * @param str
	 * @return
	 */
	public static String string2Unicode(String str) {
		String result = "";
		if (isNotBlank(str)) {
			int length = str.length();
			for (int i = 0; i < length; i++) {
				int chr1 = (char) str.charAt(i);
				// 汉字范围 \u4e00-\u9fa5 (中文)
				if (chr1 >= 19968 && chr1 <= 171941) {
					result += "\\u" + Integer.toHexString(chr1);
				} else {
					result += str.charAt(i);
				}
			}
		}
		return result;
	}

	/**
	 * Unicode to String
	 * 
	 * @param str
	 * @return
	 */
	public static String unicode2String(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	/**
	 * 判断是否为中文字符
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * HTML Tag to Unicode
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeHTMLTags(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		try {
			char ch = ' ';
			for (int i = 0; i < str.length(); i++) {
				ch = str.charAt(i);
				if (ch == '<' || ch == '>' || ch == '\'' || ch == '='
						|| ch == '&') {
					buf.append("\\u00" + Integer.toHexString(ch));
				} else if (ch == '	') {
					buf.append("\\t");
				} else {
					buf.append(ch);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	public static String format = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 根据给定格式，格式化日期
	 * 
	 * @param newDate
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
}