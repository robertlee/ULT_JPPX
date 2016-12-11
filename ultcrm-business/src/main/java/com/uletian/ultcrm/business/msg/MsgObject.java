/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.msg;

import com.uletian.ultcrm.common.util.XmlConvertable;

/**
 * 
 * @author robertxie
 * 2015年10月23日
 */
public interface MsgObject<T> extends XmlConvertable{
	
	public String getAction();
	
	public String getSourceSys();
	
	
}
