package com.uletian.ultcrm.business.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class WorkOrderStatusInfo implements Comparable<WorkOrderStatusInfo>{
	private String seq;   //序号
	
	@XStreamOmitField
	private String startflag;   //序号
	
    private String erdat;   //日期
    private String erzet;   //时间
    private String sequence;  //  序列号
    private String statu;   //状态码
    private String sname;  //维修工程师名称
    private String orgeh;   //
    private String plans;  //岗位
    private String dept;   //作业类型
    private String bezei;  // 业务类型描述
    private String etime;  //预计时间
    private String eunit;  //事件单位
    private String descr;  //项目描述
    
    
    @XStreamOmitField
    private String avatar; // 工作人员头像的路径
    
    // 关键状态点
    public static List<String> SPECIAL_STATUS_LIST = new ArrayList<String>();
    public static String STATUS_ENTER = "ZVM002";
    public static String STATUS_PRINTBILL = "ZVM003";
    public static String STATUS_PLANTWORKING = "ZVM007";
    public static String STATUS_QUALITYCHECK = "ZVM008";
    public static String STATUS_CUSTOMERPAY = "ZVM009";
    
    
    static {
    	SPECIAL_STATUS_LIST.add(STATUS_ENTER); //环车检查
    	SPECIAL_STATUS_LIST.add(STATUS_PRINTBILL); //方案确认
    	SPECIAL_STATUS_LIST.add(STATUS_PLANTWORKING); //车间作业 
    	SPECIAL_STATUS_LIST.add(STATUS_QUALITYCHECK); //质检
    	SPECIAL_STATUS_LIST.add(STATUS_CUSTOMERPAY);  //结算
    }
    
    public static List<String> subList(Integer size) {
    	List<String>  reverseList  = new ArrayList<String>();
    	//specialStatusList
    	//specialStatusList.
    	Collections.copy(reverseList, SPECIAL_STATUS_LIST);
    	Collections.reverse(reverseList);
    	return reverseList.subList(SPECIAL_STATUS_LIST.size()-size, SPECIAL_STATUS_LIST.size()-1);
    }
    
	public String getDescr() {
		return descr;
	}
	
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getEunit() {
		return eunit;
	}
	
	public void setEunit(String eunit) {
		this.eunit = eunit;
	}
	
	public String getEtime() {
		return etime;
	}
	
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getBezei() {
		return bezei;
	}
	public void setBezei(String bezei) {
		this.bezei = bezei;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPlans() {
		return plans;
	}
	public void setPlans(String plans) {
		this.plans = plans;
	}
	public String getOrgeh() {
		return orgeh;
	}
	public void setOrgeh(String orgeh) {
		this.orgeh = orgeh;
	}
	public String getStatu() {
		return statu;
	}
	public void setStatu(String statu) {
		this.statu = statu;
	}
	public String getErzet() {
		return erzet;
	}
	public void setErzet(String erzet) {
		this.erzet = erzet;
	}
	public String getErdat() {
		return erdat;
	}
	public void setErdat(String erdat) {
		this.erdat = erdat;
	}
	
	
	
	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	// 两个数据的比较，按照时间排序
	@Override
	public int compareTo(WorkOrderStatusInfo o) {
		Integer thisTime = Integer.valueOf(this.erdat+this.erzet);
		Integer otherTime = Integer.valueOf(o.getErdat()+o.getErzet());
		if (thisTime < otherTime) {
			return -1;
		}
		else if (thisTime > otherTime) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public int getStatusInt()
	{
		int i = 0;
		
		if(this.getStatu() != null)
		{
			i = Integer.valueOf(this.getStatu().substring(3)).intValue();	
		}

		return i;
	}

	public String getStartflag() {
		return startflag;
	}

	public void setStartflag(String startflag) {
		this.startflag = startflag;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
}
