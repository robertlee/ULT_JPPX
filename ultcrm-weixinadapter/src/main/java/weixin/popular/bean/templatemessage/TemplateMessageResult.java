package weixin.popular.bean.templatemessage;

import weixin.popular.bean.BaseResult;

public class TemplateMessageResult extends BaseResult{

	private Long msgid;
	
	private String templateId;

	public Long getMsgid() {
		return msgid;
	}

	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	

}
