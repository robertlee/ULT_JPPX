package weixin.popular.bean.xmlmessage;

import weixin.popular.bean.EventMessage;

public class XMLTextMessage extends XMLMessage{

	private String content;
	
	public XMLTextMessage(String toUserName, String fromUserName,String content) {
		super(toUserName, fromUserName, "text");
		this.content = content;
	}
	
	public XMLTextMessage(EventMessage eventMessage,String content) {
		super(eventMessage.getFromUserName(), eventMessage.getToUserName(), "text");
		this.content = content;
	}
	



	@Override
	public String subXML() {
		return "<Content><![CDATA["+content+"]]></Content>";
	}

	
}
