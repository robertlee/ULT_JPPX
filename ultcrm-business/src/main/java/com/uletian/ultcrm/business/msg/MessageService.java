/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.msg;

import com.uletian.ultcrm.common.util.XmlConvertable;
/**
 * 消息相关的一些公共的处理方法
 * @author robertxie
 * 2015年10月23日
 */
public class MessageService {
	/**
	 * 将一个对象转换成符合jms消息格式的字符串
	 * @return
	 */
	public String convertObjectToMsgString(XmlConvertable object, String name) {
		StringBuffer xmlstrbuf = new StringBuffer();

        xmlstrbuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlstrbuf.append("\r\n");
        xmlstrbuf.append("<ns0:"+name+" xmlns:ns0=\"http://crm/91jpfw.cn\">");
        xmlstrbuf.append("\r\n");
        
        String toxml = object.toXML();
        
        int startIndex = toxml.indexOf("<action>");
        int endIndex = toxml.indexOf("</"+name+">");
        
        xmlstrbuf.append(toxml.substring(startIndex,endIndex));
        
        xmlstrbuf.append("</ns0:"+name+">");
		System.out.println(xmlstrbuf.toString());
		
		return xmlstrbuf.toString();		
	}
	
	/**
	 * 从一个jms字符串消息中抽取对象
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public MsgObject convertMsgStringToObject(String msgContent, Class clazz,String name) throws InstantiationException, IllegalAccessException {
		MsgObject result  = (MsgObject)clazz.newInstance();
		
		int startIndex = msgContent.indexOf("<action>");
        int endIndex = msgContent.indexOf("</ns0");    
        String innerMsg = msgContent.substring(startIndex, endIndex);   
        innerMsg = "<"+name+">" + innerMsg +"</"+name+">";
        result = (MsgObject)result.toObject(innerMsg);
		return result;		
	}	
}
