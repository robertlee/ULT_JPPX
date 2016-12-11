/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.CustomerCode;
import com.uletian.ultcrm.business.repo.BusinessTypeRepository;
import com.uletian.ultcrm.business.repo.CustomerCodeRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.service.IdentifyingCode;

/**
 * 
 * @author robertxie
 * 2015年9月1日
 */
@Controller
public class ImageController {
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerCodeRepository customerCodeRepository;

	@RequestMapping(value = "/image/customer/{customerid}/token/{token}", method=RequestMethod.GET)
	public void getCodeImage(@PathVariable("customerid") Long customerid, String token, HttpServletRequest request, HttpServletResponse response) throws IOException{
		//设置不缓存图片  
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        //指定生成的相应图片  
        response.setContentType("image/jpeg");
        IdentifyingCode idCode = new IdentifyingCode();  
        BufferedImage image =new BufferedImage(idCode.getWidth() , idCode.getHeight() , BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        //定义字体样式  
        Font myFont = new Font("黑体" , Font.BOLD , 16);
        //设置字体  
        g.setFont(myFont);
          
        g.setColor(idCode.getRandomColor(200 , 250));
        //绘制背景  
        g.fillRect(0, 0, idCode.getWidth() , idCode.getHeight());
          
        g.setColor(idCode.getRandomColor(180, 200));
        idCode.drawRandomLines(g, 160);
        String codeStr = idCode.drawRandomString(4, g);
        ImageIO.write(image, "JPEG", response.getOutputStream());
        g.dispose() ;
        
        Customer customer = customerRepository.findOne(customerid);
        CustomerCode code = new CustomerCode();
        
        code.setTypeid(new Long(1));
        code.setCustomer(customer);
        code.setImageCode(codeStr);
        customerCodeRepository.save(code);       
	}
}
