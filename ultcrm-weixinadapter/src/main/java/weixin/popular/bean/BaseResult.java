package weixin.popular.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.util.StringUtils;

/**
 * 微信请求状态数据
 * @author LiYi
 *
 */
public abstract class BaseResult<T>  {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 实体编号（唯一标识）
	 */
	protected String id;

	


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	public BaseResult(){
		super();
	}

	public BaseResult(
		java.lang.String id
	){
		this.id = id;
	}
	private String errcode;
	private String errmsg;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	public Boolean isSuccess() {
		if (StringUtils.isEmpty(this.errcode) || this.errcode.equals("0")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
