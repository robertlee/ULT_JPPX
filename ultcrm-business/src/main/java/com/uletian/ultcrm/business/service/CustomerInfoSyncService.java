package com.uletian.ultcrm.business.service;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.repo.TechModelRepository;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;

@Service
public class CustomerInfoSyncService {
	
	private static Logger logger = Logger.getLogger(CustomerInfoSyncService.class);
	
	@Autowired
	private CustomerInfoMessageService customerInfoMessageService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private TechRepository techRepository;
	
	@Autowired
	private TechModelRepository techModelRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	public enum action{
		BINDING_TEL, UPDATE
	}

	
	
	public void notifycationDataChange(Customer customer) {
		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		Document doc = DocumentHelper.createDocument();
		
		Namespace namespace = new Namespace("ns0", "http://crm/91jpfw.cn");
		Element root = doc.addElement(new QName("customer", namespace));
		root.addElement(new QName("action")).addText(action.BINDING_TEL.toString());
		root.addElement(new QName("sourceSys")).addText("ULTCRM");
		root.addElement(new QName("ultcrmid")).addText(customer.getId().toString());
		root.addElement(new QName("crmid")).addText(customer.getSyncid() == null ? "" : customer.getSyncid());
		String name = null;
		if (customer.getName() == null || "".equals(customer.getName())) {
			name = customer.getNickname();
		}else{
			name = customer.getName();
		}
		root.addElement(new QName("name")).addText(name);
		root.addElement(new QName("sexy")).addText(customer.getSex() == null ? "" : customer.getSex());
		root.addElement(new QName("telephone")).addText(customer.getPhone() == null ? "" : customer.getPhone());
		root.addElement(new QName("country")).addText(customer.getCountry() == null ? "" : customer.getCountry());
		root.addElement(new QName("province")).addText(customer.getProvince() == null ? "" : customer.getProvince());
		root.addElement(new QName("city")).addText(customer.getCity() == null ? "" : customer.getCity());
		root.addElement(new QName("address")).addText(customer.getAddress() == null ? "" : customer.getAddress());
		root.addElement(new QName("postcode")).addText(customer.getPostcode() == null ? "" : customer.getPostcode());
		Element techsElement = root.addElement(new QName("techs"));
		List<Tech> techs = customer.getTechs();
		if (techs != null && techs.size() > 0) {
			for (int i = 0; i < techs.size(); i++) {
				Tech tech = techs.get(i);
				Element techElement = techsElement.addElement("tech");
				 
				techElement.addElement(new QName("crmtechid")).addText(tech.getCrmTechId() == null ? "" : tech.getCrmTechId());
				techElement.addElement(new QName("code")).addText(tech.getTechModel().getCode());
				techElement.addElement(new QName("techlevelno")).addText(tech.getTechlevelno() == null ? "" : tech.getTechlevelno());
				techElement.addElement(new QName("techerno")).addText(tech.getTecherno() == null ? "" : tech.getTecherno());
				techElement.addElement(new QName("techname")).addText(tech.getTechname() == null ? "" : tech.getTechname());
				techElement.addElement(new QName("coursetime")).addText(tech.getCoursetime() == null ? "" : tech.getCoursetime());
				String trainExpireDate = "";
				if (tech.getTrainExpireDate() != null) {
					trainExpireDate = sdf.format(tech.getTrainExpireDate());
				}
				techElement.addElement(new QName("trainExpireDate")).addText(trainExpireDate);
				techElement.addElement(new QName("trainCompany")).addText(tech.getTrainCompany() == null ? "" : tech.getTrainCompany());
				techElement.addElement(new QName("courseCode")).addText(tech.getCourseCode() == null ? "" : tech.getCourseCode());
				techElement.addElement(new QName("techColor")).addText(tech.getColor() == null ? "" : tech.getColor());
				String registerDate = "";
				if (tech.getRegisterDate() != null) {
					registerDate = sdf.format(tech.getRegisterDate());
				}
				techElement.addElement(new QName("registerDate")).addText(registerDate);
				techElement.addElement(new QName("courseLicense")).addText(tech.getCourseLicense() == null ? "" : tech.getCourseLicense());
				String checkExpireDate = "";
				if (tech.getCheckExpireDate() != null) {
					checkExpireDate = sdf.format(tech.getCheckExpireDate());
				}
				techElement.addElement(new QName("checkExpireDate")).addText(checkExpireDate);
				techElement.addElement(new QName("memberLevel")).addText(tech.getMemberLevel() == null ? "" : tech.getMemberLevel());
				
				// 添加三个同步字段， by xiecheng 2015-11-19
				techElement.addElement(new QName("nextMaintCoursetime")).addText(StringUtils.isNoneBlank(tech.getNextMaintCoursetime())  ? tech.getNextMaintCoursetime() :"" );
				
				String nextMaintDate = "";
				if (tech.getNextMaintDate() != null) {
					nextMaintDate = sdf.format(tech.getNextMaintDate());
				}
				techElement.addElement(new QName("nextMaintDate")).addText(nextMaintDate);
				
				String lastConsumeDate = "";
				if (tech.getLastConsumeDate() != null) {
					lastConsumeDate = sdf.format(tech.getLastConsumeDate());
				}
				techElement.addElement(new QName("lastConsumeDate")).addText(lastConsumeDate);
				
			} 
		}
		XMLWriter xmlwriter = new XMLWriter(writer, format);
		try {
			xmlwriter.write(doc);
		} catch (IOException e) {
		}
		customerInfoMessageService.sendMessage(writer.toString());
		
	}
}