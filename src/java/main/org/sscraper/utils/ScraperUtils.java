package org.sscraper.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.TextUtils;

public class ScraperUtils {

	/**
	 * @brief Find date in a string with below format: 
	 * xxxx-xx-xx xxxx-xx-x xxxx-x-xx xxxx-x-x      
	 * xxxx年xx月xx    
	 * xxxx/xx/xx    
	 * @param str The string 
	 * @return The date string if found, "0" if not found;
	 */
	public static String findDate(String str) {
		if (str == null || TextUtils.isEmpty(str)) {
			return "0";
		}
		Pattern p = Pattern
				.compile("[0-9]{4}[年|\\-|/][0-9]{1,2}[月|\\-|/][0-9]{1,2}");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return m.group();
		}
		
		return "0";
	}
}
