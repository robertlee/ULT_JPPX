package com.uletian.ultcrm.business.service;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.TechModel;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.repo.TechModelRepository;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;


@Component
public class CustomerInfoMessageService implements MessageListener {
	
	private static Logger logger = Logger.getLogger(CustomerInfoMessageService.class);
	
	@Autowired
	private JmsTemplate topicJmsTemplate;
	
	@Autowired
	private ActiveMQTopic customerTopic;
	
	@Autowired
	private CustomerInfoSyncService customerInfoSyncService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TechRepository techRepository;
	
	@Autowired
	private TechModelRepository techModelRepository;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public void onMessage(Message message) {


		TextMessage textMessage = (TextMessage) message;
		try {
			handlerCustomerInfo(textMessage.getText());
		} catch (JMSException | SAXException | DocumentException | IOException e) {

			logger.error("处理客户同步数据出错", e);
		}
	}

	public void sendMessage(String message){
		logger.info("发送客户同步数据\n" + message);
		topicJmsTemplate.convertAndSend(customerTopic, message,
				new MessagePostProcessor() {
					public Message postProcessMessage(Message message)
							throws JMSException {
						message.setStringProperty("SENDER", "ULTCRM");
						message.setStringProperty("ACTION", "BINDING_TEL");
						return message;
					}
				});
	}
	
	public void handlerCustomerInfo(String message) throws SAXException, DocumentException, IOException {
		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		Document doc;
		doc = DocumentHelper.parseText(message);
		XMLWriter xmlwriter = new XMLWriter(writer, format);
		xmlwriter.write(doc);
		logger.info("接收客户同步数据\n" + writer.toString());

		Element element = doc.getRootElement();
		String action = element.elementText("action");
		String sourceSys = element.elementText("sourceSys");
		String ultcrmid = element.elementText("ultcrmid");
		String crmid = element.elementText("crmid");
		String name = element.elementText("name");
		String sexy = element.elementText("sexy");
		String telephone = element.elementText("telephone");
		String country = element.elementText("country");
		String province = element.elementText("province");
		String city = element.elementText("city");
		String address = element.elementText("address");
		String postcode = element.elementText("postcode");
		
		
		
		if (!"UPDATE".equals(action)) {
			logger.debug("客户丢弃数据包\ncrmid:" + crmid + "\nultcrmid:" + ultcrmid);
			return;
		}
		Customer customer = customerRepository.findOne(Long.decode(ultcrmid));
		if (customer == null) {
			logger.warn("系统没有找到同步客户" + ultcrmid);
			return;
		}
		customer.setPhone(telephone);
		customer.setSyncid(crmid);
		customer.setName(name);
		customer.setSex(sexy);
		customer.setCountry(country);
		customer.setProvince(province);
		customer.setCity(city);
		customer.setAddress(address);
		customer.setPostcode(postcode);
		customer.setCrmCustomerId(crmid);
		customerRepository.save(customer);

		Element elements = element.element("techs");
		Iterator<Element> iterator = elements.elementIterator("tech");
		ArrayList<Tech> techs = new ArrayList<Tech>(0);

		while (iterator.hasNext()) {
			Element techElement = iterator.next();
			String code = techElement.elementText("code");
			String techlevelno = techElement.elementText("techlevelno");
			String techerno = techElement.elementText("techerno");
			String techname = techElement.elementText("techname");
			String coursetime = techElement.elementText("coursetime");
			String trainExpireDate = techElement.elementText("trainExpireDate");
			String trainCompany = techElement.elementText("trainCompany");
			String crmTechId  = techElement.elementText("crmtechid");
			String courseCode = techElement.elementText("courseCode");
			String techColor = techElement.elementText("techColor");
			String registerDate = techElement.elementText("registerDate");
			String courseLicense = techElement.elementText("courseLicense");
			String checkExpireDate = techElement.elementText("checkExpireDate");
			String memberLevel = techElement.elementText("memberLevel");
			
			String nextMaintCoursetime = techElement.elementText("nextMaintCoursetime");
			String nextMaintDate = techElement.elementText("nextMaintDate");
			String lastConsumeDate = techElement.elementText("lastConsumeDate");
			

			Tech tech = techRepository.findTechByTechlevelno(techlevelno);
			if (tech == null) {
				tech = new Tech();
			}
			TechModel techModel = null;
			try {
				techModel = techModelRepository.findModelByCode(code);
			} catch (Exception e) {
			}
			if (techModel == null) {
				logger.warn("车型数据无法匹配：\n" + code);
				techModel = techModelRepository.findModelByCode("OTHERS");
			}
			tech.setCrmTechId(crmTechId);
			tech.setTechlevelno(techlevelno);
			tech.setTechModel(techModel);
			tech.setTechSery(techModel.getTechSery());
			tech.setTechCourse(techModel.getTechSery().getTechCourse());
			tech.setCustomer(customer);
			tech.setCode(code);
			tech.setTechlevelno(techlevelno);
			tech.setTecherno(techerno);
			tech.setTechname(techname);
			tech.setCoursetime(coursetime);
			
			try {
				tech.setTrainExpireDate(sdf.parse(trainExpireDate));
			} catch (ParseException e) {
				logger.warn("解析保险时间格式出错\n" + trainExpireDate, e);
			}
			tech.setTrainCompany(trainCompany);
			tech.setCourseCode(courseCode);
			tech.setMemberLevel(memberLevel);
			tech.setColor(techColor);
			try {
				tech.setRegisterDate(sdf.parse(registerDate));
			} catch (ParseException e) {
				logger.warn("解析注册时间格式出错\n" + registerDate, e);
			}
			tech.setCourseLicense(courseLicense);
			try {
				tech.setCheckExpireDate(sdf.parse(checkExpireDate));
			} catch (ParseException e) {
				logger.warn("解析年检时间格式出错\n" + checkExpireDate, e);
			}
			
			// 新添加三个同步字段，by xiecheng 2015-11-19
			tech.setNextMaintCoursetime(nextMaintCoursetime);
			try {
				tech.setNextMaintDate(sdf.parse(nextMaintDate));
			} catch (ParseException e) {
				logger.warn("解析注册时间格式出错\n" + nextMaintDate, e);
			}
			
			try {
				tech.setLastConsumeDate(sdf.parse(lastConsumeDate));
			} catch (ParseException e) {
				logger.warn("解析注册时间格式出错\n" + lastConsumeDate, e);
			}
			
			techs.add(tech);
		}
		techRepository.save(techs);
	}
}