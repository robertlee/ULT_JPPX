/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

/**
 * 
 * @author robertxie
 * 2015年9月22日
 */
@Configuration
public class ServletContainerConfig {
	
	@Bean
	public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer servletContainer) {
	            ((TomcatEmbeddedServletContainerFactory) servletContainer).addConnectorCustomizers(
	                    new TomcatConnectorCustomizer() {
	                        @Override
	                        public void customize(Connector connector) {
/*	                            AbstractHttp11Protocol httpProtocol = (AbstractHttp11Protocol) connector.getProtocolHandler();
	                            httpProtocol.setCompression("on");
	                            httpProtocol.setCompressionMinSize(256);
	                            String mimeTypes = httpProtocol.getCompressableMimeTypes();
	                            String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE+",application/javascript";
	                            httpProtocol.setCompressableMimeTypes(mimeTypesWithJson);*/
	                        }
	                    }
	            );
	        }
	    };
	}
	
	
}
