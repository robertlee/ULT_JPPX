package com.uletian.ultcrm.business.service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.uletian.ultcrm.business.value.OrderMessage;


/**
 * 测试订单的监听器
 * @author robertliu1
 *
 */
@Component
public class TestOrderStatusListener implements MessageListener{


	private static Logger logger = Logger.getLogger(TestOrderStatusListener.class);

		
	@Override
	public void onMessage(Message arg0) {
	
 		logger.info("into TestOrderStatusListener.....");
        try {
        	
        	String sender = arg0.getStringProperty("SENDER");
        	logger.info("消息头sender: "+sender);
        	String orderMessage = ((TextMessage)arg0).getText();
        	logger.info("消息体: "+orderMessage);
    		logger.info("over TestOrderStatusListener!");
            
        }catch(Exception e)
        {
        	e.printStackTrace();
        	logger.info(e.getMessage());
        }
        
        
        
	}
	
}
