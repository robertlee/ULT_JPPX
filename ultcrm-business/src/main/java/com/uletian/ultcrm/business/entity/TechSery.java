package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tech_series database table.
 * 
 */
@Entity
@Table(name="tech_series")
@NamedQuery(name="TechSery.findAll", query="SELECT c FROM TechSery c")
public class TechSery implements Serializable {
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

	private String name;

	private Long sortid;

	//bi-directional many-to-one association to Tech
	@OneToMany(mappedBy="techSery")
	private List<Tech> techs;

	//bi-directional many-to-one association to TechModel
	@OneToMany(mappedBy="techSery")
	@OrderBy("sortid ASC")
	private List<TechModel> techModels;

	//bi-directional many-to-one association to TechCourse
	@ManyToOne
	@JoinColumn(name="courseid")
	private TechCourse techCourse;

	public TechSery() {
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
		tech.setTechSery(this);

		return tech;
	}

	public Tech removeTech(Tech tech) {
		getTechs().remove(tech);
		tech.setTechSery(null);

		return tech;
	}

	public List<TechModel> getTechModels() {
		return this.techModels;
	}

	public void setTechModels(List<TechModel> techModels) {
		this.techModels = techModels;
	}

	public TechModel addTechModel(TechModel techModel) {
		getTechModels().add(techModel);
		techModel.setTechSery(this);

		return techModel;
	}

	public TechModel removeTechModel(TechModel techModel) {
		getTechModels().remove(techModel);
		techModel.setTechSery(null);

		return techModel;
	}

	public TechCourse getTechCourse() {
		return this.techCourse;
	}

	public void setTechCourse(TechCourse techCourse) {
		this.techCourse = techCourse;
	}

}