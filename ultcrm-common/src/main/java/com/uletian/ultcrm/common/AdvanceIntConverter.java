/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.common;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.converters.basic.IntConverter;

/**
 * 
 * @author robertxie
 * 2015年10月29日
 */
public class AdvanceIntConverter extends IntConverter{
	
	 public Object fromString(String str) {
		 // 对字符串进行trim处理
		 if (StringUtils.isNotBlank(str)) {
			 str = str.trim();
			 return super.fromString(str);
		 }
		 else {
			 return null;
		 }

	 }
	 
}
