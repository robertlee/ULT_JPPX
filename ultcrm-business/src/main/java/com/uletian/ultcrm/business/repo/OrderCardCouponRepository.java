/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.OrderCardCoupon;

/**
 * 
 * @author robertxie
 * 2015年11月20日
 */
public interface OrderCardCouponRepository extends PagingAndSortingRepository<OrderCardCoupon, Long> {
	
	List<OrderCardCoupon> findByOrder(Order order);
	
}
