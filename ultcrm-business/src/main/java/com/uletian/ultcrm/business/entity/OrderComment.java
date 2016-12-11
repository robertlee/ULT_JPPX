package com.uletian.ultcrm.business.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the order_comment database table.
 * 
 */
@Entity
@Table(name="order_comment")
@NamedQuery(name="OrderComment.findAll", query="SELECT o FROM OrderComment o")
public class OrderComment implements Serializable {
	private static final long serialVersionUID = 1L;

	private String comment;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="lobby_star")
	private Integer lobbyStar;

	@Column(name="plant_star")
	private Integer plantStar;

	@Column(name="sa_star")
	private Integer saStar;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	private Customer customer;

	//bi-directional many-to-one association to Order
	@ManyToOne
	private Order order;

	public OrderComment() {
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
	 * @return the lobbyStar
	 */
	public Integer getLobbyStar() {
		return lobbyStar;
	}

	/**
	 * @param lobbyStar the lobbyStar to set
	 */
	public void setLobbyStar(Integer lobbyStar) {
		this.lobbyStar = lobbyStar;
	}

	/**
	 * @return the plantStar
	 */
	public Integer getPlantStar() {
		return plantStar;
	}

	/**
	 * @param plantStar the plantStar to set
	 */
	public void setPlantStar(Integer plantStar) {
		this.plantStar = plantStar;
	}

	/**
	 * @return the saStar
	 */
	public Integer getSaStar() {
		return saStar;
	}

	/**
	 * @param saStar the saStar to set
	 */
	public void setSaStar(Integer saStar) {
		this.saStar = saStar;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}