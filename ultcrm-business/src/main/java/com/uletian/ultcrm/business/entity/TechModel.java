package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tech_model database table.
 * 
 */
@Entity
@Table(name="tech_model")
@NamedQuery(name="TechModel.findAll", query="SELECT c FROM TechModel c")
public class TechModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String code;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="create_user_id")
	private Long createUserId;

	@Column(name="last_update_time")
	private Timestamp lastUpdateTime;

	@Column(name="last_update_userid")
	private Long lastUpdateUserid;

	private String name;

	private Long sortid;
	
	@Column(name="description", length = 55)
	private String description;

	//bi-directional many-to-one association to Tech
	@OneToMany(mappedBy="techModel")
	private List<Tech> techs;

	//bi-directional many-to-one association to TechSery
	@ManyToOne
	@JoinColumn(name="seriesid")
	private TechSery techSery;

	public TechModel() {
	}



	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}



	/**
	 * @param lastUpdateUserid the lastUpdateUserid to set
	 */
	public void setLastUpdateUserid(Long lastUpdateUserid) {
		this.lastUpdateUserid = lastUpdateUserid;
	}



	/**
	 * @param sortid the sortid to set
	 */
	public void setSortid(Long sortid) {
		this.sortid = sortid;
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



	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}



	/**
	 * @return the lastUpdateUserid
	 */
	public Long getLastUpdateUserid() {
		return lastUpdateUserid;
	}



	/**
	 * @return the sortid
	 */
	public Long getSortid() {
		return sortid;
	}



	public List<Tech> getTechs() {
		return this.techs;
	}

	public void setTechs(List<Tech> techs) {
		this.techs = techs;
	}

	public Tech addTech(Tech tech) {
		getTechs().add(tech);
		tech.setTechModel(this);

		return tech;
	}

	public Tech removeTech(Tech tech) {
		getTechs().remove(tech);
		tech.setTechModel(null);

		return tech;
	}

	public TechSery getTechSery() {
		return this.techSery;
	}

	public void setTechSery(TechSery techSery) {
		this.techSery = techSery;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}

}