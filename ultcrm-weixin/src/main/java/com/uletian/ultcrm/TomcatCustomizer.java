/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.stereotype.Component;

/**
 * 
 * @author robertxie
 * 2015年9月22日
 */
//@Component
public class TomcatCustomizer implements TomcatConnectorCustomizer {

	@Override
	public void customize(Connector connector) {
		 //connector.setProperty("compression", "on");
		 // Add json and xml mime types, as they're not in the mimetype list by default
		 //connector.setProperty("compressableMimeType", "text/html,text/xml,text/css,application/json,application/javascript,text/plain,application/json,application/xml");
		
	}

}
