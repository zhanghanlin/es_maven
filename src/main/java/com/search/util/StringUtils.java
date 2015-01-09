package com.search.util;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

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
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(format);
	}

	/**
	 * 生成随机汉字
	 * 
	 * 原理是从汉字区位码找到汉字。在汉字区位码中分高位与底位， 且其中简体又有繁体。位数越前生成的汉字繁体的机率越大。
	 * 所以在本例中高位从171取，底位从161取， 去掉大部分的繁体和生僻字。但仍然会有
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String randomChinese() throws Exception {
		String str = null;
		int hightPos, lowPos; // 定义高低位
		Random random = new Random();
		hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
		lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
		byte[] b = new byte[2];
		b[0] = (new Integer(hightPos).byteValue());
		b[1] = (new Integer(lowPos).byteValue());
		str = new String(b, "GBk");// 转成中文
		return str;
	}

	/**
	 * 生成count个随即汉字
	 * 
	 * @param count
	 * @return
	 */
	public static String randomChinese(int count) {
		String str = "";
		for (int i = 0; i < count; i++) {
			try {
				str += randomChinese();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 获得随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInt(int min, int max) {
		int result = min + new Double(Math.random() * (max - min)).intValue();
		return result;
	}
}