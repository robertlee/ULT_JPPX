package com.uletian.ultcrm.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="package")
@NamedQuery(name="Package.findAll", query="SELECT p FROM Package p")
public class Package implements Serializable{

	private static final long serialVersionUID = 6003587148924532828L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name", length = 55)
	private String name;
	
	@Column(name="code", length = 55)
	private String code;
	
	@OneToOne
	@JoinColumn(name="storeid")
	private Store store;
	
	@OneToOne
	@JoinColumn(name="courseid")
	private TechCourse course;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public TechCourse getCourse() {
		return course;
	}

	public void setCourse(TechCourse course) {
		this.course = course;
	}
	
}
