/**
 * Copyright &copy; 2012-2014 uletian All rights reserved.
 */
package com.uletian.ultcrm.weixin.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uletian.ultcrm.business.service.WeixinConfig;
import com.uletian.ultcrm.weixin.event.LocationEventhandler;
import com.uletian.ultcrm.weixin.event.MenuEventHandler;
import com.uletian.ultcrm.weixin.event.SubscribeEventHandler;
import com.uletian.ultcrm.weixin.event.TemplateMessageEventHandler;
import com.uletian.ultcrm.weixin.event.TransferMessageHandler;
import com.uletian.ultcrm.weixin.event.UnsubscribeEventHandler;

import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.event.EventHandler;
import weixin.popular.util.ExpireSet;
import weixin.popular.util.SignUtil;
import weixin.popular.util.XMLConverUtil;

/**
 * 微信接口验证处理器
 * @author robertxie
 * @version 2015-05-16
 */
@Controller
public class WeixinCheckController {
    // 日志信息
	private static Logger logger = Logger.getLogger(WeixinCheckController.class);
	
    // 重复通知过滤  时效60秒
    private static ExpireSet<String> expireSet = new ExpireSet<String>(60);
    
    // 事件处理器注册在这里
    private Map<String,EventHandler> eventMap = new HashMap<String,EventHandler>();

	
	@Autowired
	private WeixinConfig weixinConfig;
	
	@Autowired
	private SubscribeEventHandler subscribeEventHandler;
	
	@Autowired
	private MenuEventHandler menuEventHandler;
	
	@Autowired
	private UnsubscribeEventHandler unsubscribeEventHandler;
	
	@Autowired
	private TemplateMessageEventHandler templateMessageEventHandler;
	
	@Autowired
	private LocationEventhandler locationEventhandler;
	
	@Autowired
	private TransferMessageHandler transferMessageHandler;
	
	@PostConstruct  
    public void  init(){  
		eventMap.put("subscribe", subscribeEventHandler);
    	eventMap.put("unsubscribe", unsubscribeEventHandler);
    	eventMap.put("click", menuEventHandler);
    	eventMap.put("view", null);
    	eventMap.put("scan", null);
    	eventMap.put("location", locationEventhandler);
    	eventMap.put("scancode_waitmsg", null);
    	eventMap.put("scancode_push", null);
    	eventMap.put("pic_sysphoto", null);
    	eventMap.put("pic_weixin", null);
    	eventMap.put("location_select", null); 
    	eventMap.put("templatesendjobfinish", templateMessageEventHandler); 
    	
    	eventMap.put("text", transferMessageHandler);
    	eventMap.put("image", transferMessageHandler);
    	eventMap.put("voice", transferMessageHandler);
    	eventMap.put("video", transferMessageHandler);
    	eventMap.put("shortvideo", transferMessageHandler);
    	eventMap.put("link", transferMessageHandler);
    	
    }  
    

	
	@RequestMapping(value="/weixin",method=RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        // 微信加密签名  
        String signature = request.getParameter("signature");  
		// 时间戳  
		String timestamp = request.getParameter("timestamp");
		// 随机数  
		String nonce = request.getParameter("nonce");
		// 随机字符串  
		String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();  
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        try{
        	if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
        		System.out.println(echostr);
        		out.print(echostr);   
        	}
        }finally{
        	out.close();
        	out = null;
        }
    }
	
	// 接受用户的事件消息
	@RequestMapping(value="/weixin",method=RequestMethod.POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		logger.info("Start handle a message..."); 
		ServletInputStream inputStream = request.getInputStream();
         Writer writer = response.getWriter();
		
		 request.setCharacterEncoding("UTF-8");
         response.setCharacterEncoding("UTF-8");

         // 响应消息
         XMLMessage returnMsg = null;
         EventMessage eventMessage = null;
         try  {
	         if(inputStream!=null){
	             //转换XML
	             eventMessage = XMLConverUtil.convertToObject(EventMessage.class,inputStream);
	             logger.info("received a weixin event message --- :"+eventMessage.toString());
	             String expireKey = eventMessage.getFromUserName() + "__"
	             				   + eventMessage.getToUserName() + "__"
	             				   + eventMessage.getMsgId() + "__"
	             				   + eventMessage.getEvent() + "__"
	             				   + eventMessage.getCreateTime();
	             if(expireSet.contains(expireKey)){
	             	//重复通知不作处理
	             	return;
	             }else{
	             	expireSet.add(expireKey);
	             }
	             
	             // 判断消息类型。做处理
	             logger.info("Event type is :"+eventMessage.getEvent());
            	 String event = eventMessage.getMsgType().equalsIgnoreCase("event") ? eventMessage.getEvent() : eventMessage.getMsgType();
	             EventHandler  handler = eventMap.get(event.toLowerCase());
	             
	             if (handler != null) {
	            	 returnMsg = handler.handleEvent(eventMessage);
	             }
	            
	             
	         }
         
         }catch(Exception e) {
        	 logger.error("Erroe occured when handle message.", e);
         }
         //创建回复
         if (returnMsg != null) {
	         //回复
	         logger.info("return msg to weixin:"+returnMsg.toXML());
	         returnMsg.toWriter(writer);
         }
         // 回复空字符串
         if (returnMsg == null || eventMessage == null) {
        	 writer.write("");
         }
        
         writer.close();
         
    }
	
	@RequestMapping(value="/weixintest",method=RequestMethod.GET)
    protected void weixintest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.sendRedirect(weixinConfig.getHostPath()+"/auth.html");
	}
	
	
	
}
