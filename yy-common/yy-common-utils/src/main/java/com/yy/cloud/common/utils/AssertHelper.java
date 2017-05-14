package com.yy.cloud.common.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
/**
 * Assert 工具类
 * 
 * @see Assert
 * @author jiawei
 */
public class AssertHelper {

	/**
	 * Collection 内对象皆为非空
	 * 
	 * @param collection
	 * @param message
	 */
	public static void allNotNull(Collection collection, String message) {
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			Object object = (Object) iterator.next();
			if (object == null) {
				throw new IllegalArgumentException(message);
			}
		}
	}

	/**
	 * Collection 内对象皆为非空
	 * 
	 * @param collection
	 * @see AssertHelper#allNotNull(Collection, String)
	 */
	public static void allNotNull(Collection collection) {
		allNotNull(collection, "[Assertion failed] - these argument are required; they all cannot be null");
	}

	/**
	 * Arrays 内对象皆为非空
	 * 
	 * @param objects
	 * @param message
	 */
	public static void allNotNull(Object[] objects, String message) {
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] == null) {
				throw new IllegalArgumentException(message);
			}
		}
	}

	/**
	 * Arrays 内对象皆为非空
	 * 
	 * @param objects
	 * @see AssertHelper#allNotNull(Object[], String)
	 */
	public static void allNotNull(Object[] objects) {
		allNotNull(objects, "[Assertion failed] - these argument are required; they all cannot be null");
	}

	/**
	 * Collection 内对象皆为非空且有一非空格的字母
	 * 
	 * @param collection
	 *            String对象集合
	 * @param message
	 *            异常message
	 */
	public static void allHasText(Collection collection, String message) {
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			String str = (String) iterator.next();
			if (!StringUtils.hasText(str)) {
				throw new IllegalArgumentException(message);
			}
		}
	}

	/**
	 * Collection 内对象皆为非空且有一非空格的字母
	 * 
	 * @param collection
	 *            String对象集合
	 */
	public static void allHasText(Collection collection) {
		allHasText(collection, "[Assertion failed] - these argument are required and hasText;");
	}

	/**
	 * Arrays 内对象皆为非空且有一非空格的字母
	 * 
	 * @param strs
	 *            String数组
	 * @param message
	 */
	public static void allHasText(String[] strs, String message) {
		for (int i = 0; i < strs.length; i++) {
			if (!StringUtils.hasText(strs[i])) {
				throw new IllegalArgumentException(message);
			}
		}
	}

	/**
	 * Arrays 内对象皆为非空且有一非空格的字母
	 * 
	 * @param strs
	 *            String数组
	 */
	public static void allHasText(String[] strs) {
		allHasText(strs, "[Assertion failed] - these argument are required and hasText;");
	}
	/**
	 * "",null等返回 false,否则返回true
	 * @param value
	 * @return
	 */
	public static Boolean notEmpty(Object value)
	{
		return !empty(value);
	}
	/**
	 * 为空返回true
	 * @param value
	 * @return
	 */
	public static Boolean empty(Object value)
	{
		if(value==null||"".equals(value)||"null".equals(value)||"undefined".equals(value))
		{
			return true;
		}
		return false;
	}
	/**
	 * 非空判断,如果为Null,""返回false
	 * @author sqing
	 */
	public static Boolean isOrNotEmpty_assert(Object value)
	{
		if(empty(value)){
			return false;
		}else{
			return true;
		}
		
	}
	
    /**
     * 功能：判断字符串是否为数字
     * 
     * @param str
     * @return
     */
	public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
	 * 判断url是否可用
	 * @param url
	 * @return
	 */
	public static boolean isValidUrl(String url){
		boolean flag = false;
		int counts = 0;

		if(null==url || url.length()<=0){
			return flag;
		}
		while(counts <= 5){
			try{
				HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
				int state = connection.getResponseCode();
				if(state == 200){
					flag = true;
				}
			break;
			}catch(Exception e){
				counts++;
				continue;
			}
		}
		return flag;
	}
	
	/**
	 * 判断url是否可用
	 * @param url
	 * @return
	 */
	public static List<String> concatToList(String str){
    	String[] strArray = str.split(",");
    	List<String> tmp = new ArrayList<String>();
    	if(AssertHelper.notEmpty(strArray)){
    		tmp = Arrays.asList(strArray);
    	}
    	return tmp;
	}
}
