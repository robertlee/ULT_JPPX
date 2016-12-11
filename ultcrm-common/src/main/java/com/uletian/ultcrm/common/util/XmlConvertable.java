/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.common.util;

/**
 * 定义可以进行xml-object mapping的对象
 * @author robertxie
 * 2015年10月23日
 */
public interface XmlConvertable<T> {
	String toXML();
	T toObject(String xmlString);
}
