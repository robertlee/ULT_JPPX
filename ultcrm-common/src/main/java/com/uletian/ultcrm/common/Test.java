package com.uletian.ultcrm.common;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Test {
	
	private String name;
	
	private String sex;
	
	public static void main(String[] args) {
		Test test = new Test();
		test.setName("ddd");
		test.setSex("1");
		String s = ReflectionToStringBuilder.toString(test);
		System.out.println(s);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	

}
