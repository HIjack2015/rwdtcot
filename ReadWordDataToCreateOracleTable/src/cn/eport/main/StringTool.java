package cn.eport.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {
	static String containTitleRule = "[[A-Z]+_]+[A-Z]";
	static String atLeastOneChineseRule = "[\u4e00-\u9fa5]+";

	public static boolean isTitle(String content) {
		if (content.isEmpty()) {
			return false;
		}
		Matcher matcher = Pattern.compile(containTitleRule).matcher(content);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}

	}

	public static String getTitle(String content) {

		Matcher matcher = Pattern.compile(containTitleRule).matcher(content);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return "";
		}
	}

	public static String getChinese(String parContent) {
		Matcher matcher = Pattern.compile(atLeastOneChineseRule).matcher(parContent);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return "";
		}
	}
}
