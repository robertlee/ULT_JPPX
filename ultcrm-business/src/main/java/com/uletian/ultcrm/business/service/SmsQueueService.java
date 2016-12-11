package com.uletian.ultcrm.business.service;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.SmsMessage;
import com.uletian.ultcrm.business.entity.SmsSend;
import com.uletian.ultcrm.business.repo.SmsSendRepository;
import com.uletian.ultcrm.business.value.Result;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 时间段选择模块
 * 
 * @author Administrator
 *
 */
@Component
public class SmsQueueService implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(SmsQueueService.class);

	@Autowired
	private JmsTemplate jmsTemplate; 
	 
	@Autowired
	private SmsSendRepository smsSendRepository;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Qualifier("smsQueue")
	@Autowired
	private Destination smsQueue;

	@Autowired
	private SMSSender sender;

	@Value("${smsservice}")
	private String smsService;
	@Value("${apikey}")
	private String apikey;//短信apikey    修改人：RobertLee   修改时间：2016-04-24
	
	@Value("${smsUrl}")
	private String smsUrl;//短信接口地址  修改人：RobertLee   修改时间：2016-04-24
	
	
	public Result sendMessage(String phone, String content, String ipaddress, boolean isCheck){
		/*
			修改人：RobertLee
			修改时间：2016-04-24
			修改原因：更换短信接口
		*/
		Result result = new Result();
		//String httpUrl = "http://apis.baidu.com/kingtto_media/106sms/106sms";
		try {
			if (isCheck) {
				if (!checkphone(phone)) {
					logger.warn("MobilePhone Number is overload!");
					result.setMsg("sms send ok");
				} else if (!checkipaddress(ipaddress)) {
					logger.warn("recevice report msg is failed");
					result.setMsg("ip send ok");
				} else {
					jmsTemplate.send(smsQueue, new SMSMessageCreator(phone, content, ipaddress));
				}	
			}else{
				String httpArg = "mobile=" + phone + "&content=【快乐语文】" + content;
				String jsonResult = smsRequest(smsUrl, httpArg);			
				logger.info("============================================================");
				logger.info(smsUrl+httpArg);
				logger.info(jsonResult);
				logger.info("============================================================");
				result.setCode(0);
				result.setMsg("发送成功");
				result.setResult(true);				
				return result;
			}
			
			if (result.getMsg().length() > 0) {
				result.setCode(0);
				result.setMsg("sendMessage is pass");
				result.setResult(true);
			}
		} catch (JmsException e) {
			result.setMsg("发送短信出错");
			logger.error("发送短信出错", e);
		} catch (ParseException e) {
			result.setMsg("发送短信出错");
			logger.error("发送短信出错", e);
		}
		return result;
	}
	
	/**
		修改人：吴云
     * @param urlAll
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public String smsRequest(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  apikey);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

	@Override
	public void onMessage(Message message) {
		TextMessage textMsg = (TextMessage) message;
		
		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter xmlwriter = new XMLWriter(writer, format);
		Document doc;
		try {
			doc = DocumentHelper.parseText(textMsg.getText());
			xmlwriter.write(doc);
			logger.info("收到报文 ：\n" + writer.toString() + "");
			Element element = doc.getRootElement();
			xmlwriter.write(doc);
			
			Element sms = element.element("sms");
			String phone = sms.elementText("phone");
			String content = sms.elementText("content");
			String ipaddress = sms.elementText("ipaddress");
			String from = sms.elementText("from");
			
			//String httpArg = "mobile=" + phone + "&content=【快乐语文】" + content;
			//String jsonResult = smsRequest(smsUrl, httpArg);			

			SmsSend smsSend = new SmsSend();
			smsSend.setContent("【快乐语文】"+content);
			smsSend.setPhone(phone);
			smsSend.setIpaddress(ipaddress);
			if ("CRM".equals(from)) {
				smsSend.setTypeid(2L);
			} else {
				smsSend.setTypeid(1L);
			}
			// 测试环境发送的短信内容前增加标注
			if (!Boolean.parseBoolean(smsService)) {
				smsSend.setContent("(测试短信)" + smsSend.getContent());
			}
			String responseStr = sender.send(new SmsMessage(smsSend.getPhone(), smsSend.getContent()));
			smsSend.setResultPackage(responseStr);
			logger.debug("短信发送结果：\n"+responseStr);
			smsSendRepository.save(smsSend);
		} catch (JMSException | IOException | DocumentException  e) {
			logger.warn("发送短信出错", e);
		}
	}

	private boolean checkipaddress(String ipaddress) throws ParseException {
		boolean result = false;
		if (ipaddress == null) {
			return true;
		}
		Calendar c = Calendar.getInstance();
		String datetime = sdf.format(c.getTime());
		List<SmsSend> count = smsSendRepository.findIpCount(ipaddress,
				new Timestamp(sdf.parse(datetime).getTime()));
		if (count == null || count.size() < 9) {
			result = true;
		}
		return result;
	}

	private boolean checkphone(String phone) throws ParseException {
		boolean result = false;
		Calendar c = Calendar.getInstance();
		String datetime = sdf.format(c.getTime());
		List<SmsSend> count = smsSendRepository.findPhoneCount(phone,
				new Timestamp(sdf.parse(datetime).getTime()));
		if (count == null || count.size() < 9) {
			result = true;
		}
		return result;
	}

	/**
	 * 获取当前网络ip
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")
					|| ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	private class SMSMessageCreator implements MessageCreator{
		private String phone;
		private String content;
		private String ipaddress;

		public SMSMessageCreator(String phone,String content,String ipaddress){
			this.phone = phone;
			this.content = content;
			this.ipaddress = ipaddress;
		}

		@Override
		public Message createMessage(Session session) throws JMSException {
			Document doc = DocumentHelper.createDocument();
			Namespace namespace = new Namespace("ns0", "http://crm/91jpfw.cn");
			Element root = doc.addElement(new QName("message", namespace));

			Element sms = root.addElement(new QName("sms"));
			sms.addElement(new QName("phone")).addText(phone);
			sms.addElement(new QName("content")).addCDATA(content);
			sms.addElement(new QName("from")).addText("ULTCRM");
			sms.addElement(new QName("ipaddress")).addText(ipaddress == null ? "" : ipaddress);
			return session.createTextMessage(doc.asXML());
		}
	}
}
