/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.value;

/**
 * 测试发送短信
 * @author robertliu2
 * 2015年9月9日
 */
public class Result {
	private String msg = "未知错误";
	private Integer code = -1;
	private Boolean result = false;

	public Result(){}
	
	public Result(Integer code, String msg, Boolean result){
		this.msg = msg;
		this.code = code;
		this.result = result;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

}
