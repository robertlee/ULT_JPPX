/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.msg;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

/**
 * 
 * @author robertxie
 * 2015年10月26日
 */
@Component
public class TestMsgService {	
	@Autowired
	private JmsTemplate topicJmsTemplate;
	
	public void test() {
		String msg = ""
				+ "<ns0:score xmlns:ns0=\"http://crm/91jpfw.cn\">"
				+"<action>FULL</action>"
				+"<sourceSys>CRM</sourceSys>"
				+"<ultcrmcustid>1</ultcrmcustid>"
				+"<phone>18675515034</phone>"
				+"<techerno>LFV2B28U6E3031564</techerno>"
				+"<techlevelno>作文培训</techlevelno>"
				+"<crmtechid>1000000397</crmtechid>"
				+"<totalscore>68 </totalscore>"
				+"<items>"
					+"<item><desc>test add 3</desc><time>20150917200134</time><orderid></orderid><value>3 </value><storecode></storecode></item>"
					+"<item><desc>test add 3</desc><time>20150917200651</time><orderid></orderid><value>3 </value><storecode></storecode></item>"
					+"<item><desc>test add 3</desc><time>20150917200957</time><orderid></orderid><value>3 </value><storecode></storecode></item>"
					+"</items>"
				+"</ns0:score>";
		topicJmsTemplate.convertAndSend("SCORE", msg, new MessagePostProcessor() {
			public Message postProcessMessage(Message message)
					throws JMSException {
				message.setStringProperty("SENDER", "CRM");
				message.setStringProperty("ACTION", "CREATE_ORDER");
				return message;
			}
		});
		
	}
}
