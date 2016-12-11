/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author robertxie
 * 2015年11月20日
 */
@Entity
@Table(name="order_cardcoupon")
public class OrderCardCoupon {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	

	private Long cardcouponId;
	
	/**
	 * 表示是卡还是券， card, coupon
	 */
	private String cardcouponType;
	
	/**
	 * 表示发卡券的类型， type: appointment 表示预约发卡券的
	 */
	private String type;

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
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
 
	/**
	 * @return the cardcouponId
	 */
	public Long getCardcouponId() {
		return cardcouponId;
	}

	/**
	 * @param cardcouponId the cardcouponId to set
	 */
	public void setCardcouponId(Long cardcouponId) {
		this.cardcouponId = cardcouponId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the cardcouponType
	 */
	public String getCardcouponType() {
		return cardcouponType;
	}

	/**
	 * @param cardcouponType the cardcouponType to set
	 */
	public void setCardcouponType(String cardcouponType) {
		this.cardcouponType = cardcouponType;
	}
	
	
	
	
	
	
}
