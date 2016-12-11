package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tech_course database table.
 * 
 */
@Entity
@Table(name="tech_course")
@NamedQuery(name="TechCourse.findAll", query="SELECT c FROM TechCourse c")
public class TechCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	private String logo;

	private String name;

	private Long sortid;
	
	private String code;

	//bi-directional many-to-one association to Tech
	@OneToMany(mappedBy="techCourse")
	private List<Tech> techs;

	//bi-directional many-to-one association to TechSery
	@OneToMany(mappedBy="techCourse")
	@OrderBy("sortid ASC")
	private List<TechSery> techSeries;

	//bi-directional many-to-one association to StoreBrand
	@OneToMany(mappedBy="techCourse")
	private List<StoreBrand> storeBrand;

	public TechCourse() {
	}



	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}



	/**
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}



	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}



	/**
	 * @return the lastUpdateUserid
	 */
	public Long getLastUpdateUserid() {
		return lastUpdateUserid;
	}



	/**
	 * @param lastUpdateUserid the lastUpdateUserid to set
	 */
	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}



	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}



	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sortid
	 */
	public Long getSortid() {
		return sortid;
	}

	/**
	 * @param sortid the sortid to set
	 */
	public void setSortid(Long sortid) {
		this.sortid = sortid;
	}

	public List<Tech> getTechs() {
		return this.techs;
	}

	public void setTechs(List<Tech> techs) {
		this.techs = techs;
	}

	public Tech addTech(Tech tech) {
		getTechs().add(tech);
		tech.setTechCourse(this);

		return tech;
	}

	public Tech removeTech(Tech tech) {
		getTechs().remove(tech);
		tech.setTechCourse(null);

		return tech;
	}

	public List<TechSery> getTechSeries() {
		return this.techSeries;
	}

	public void setTechSeries(List<TechSery> techSeries) {
		this.techSeries = techSeries;
	}

	public TechSery addTechSery(TechSery techSery) {
		getTechSeries().add(techSery);
		techSery.setTechCourse(this);

		return techSery;
	}

	public TechSery removeTechSery(TechSery techSery) {
		getTechSeries().remove(techSery);
		techSery.setTechCourse(null);

		return techSery;
	}

	public List<StoreBrand> getStoreBrands() {
		return this.storeBrand;
	}

	public void setStoreBrands(List<StoreBrand> storeBrand) {
		this.storeBrand = storeBrand;
	}

	public StoreBrand addStoreBrand(StoreBrand storeBrand) {
		getStoreBrands().add(storeBrand);
		storeBrand.setTechCourse(this);

		return storeBrand;
	}

	public StoreBrand removeStoreBrand(StoreBrand storeBrand) {
		getStoreBrands().remove(storeBrand);
		storeBrand.setTechCourse(null);

		return storeBrand;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}