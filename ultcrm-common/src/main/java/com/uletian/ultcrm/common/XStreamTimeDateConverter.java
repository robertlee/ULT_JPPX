/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/**
 * 
 * @author robertxie
 * 2015年10月26日
 */
public class XStreamTimeDateConverter extends AbstractSingleValueConverter {
	
	 private static final DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat(  
	            "yyyyMMddHHmmss");  
	  
	    @Override  
	    public boolean canConvert(Class type) {  
	        return type.equals(Date.class) || type.equals(java.sql.Timestamp.class);  
	    }  
	  
	    @Override  
	    public Object fromString(String str) {  
	    	if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str.trim())) {
	    		return null;
	    	}
	    	
	    	// 这里将字符串转换成日期  
	        try {  
	            return DEFAULT_DATEFORMAT.parseObject(str);  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        }  
	        throw new ConversionException("Cannot parse date " + str);  
	    }  
	  
	    @Override  
	    public String toString(Object obj) {  
	        // 这里将日期转换成字符串  
	        return DEFAULT_DATEFORMAT.format((Date) obj);  
	    }  
	    

}
