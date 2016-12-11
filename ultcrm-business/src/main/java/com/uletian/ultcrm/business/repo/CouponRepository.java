/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Coupon;
import com.uletian.ultcrm.business.entity.CouponBatch;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Event;

/**
 * 
 * @author robertxie
 * 2015年10月22日
 */
@RepositoryRestResource(collectionResourceRel = "coupon", path = "coupon")
public interface CouponRepository extends  PagingAndSortingRepository<Coupon, Long>{
	 
	Coupon findByCouponNoAndCouponBatch(String couponNo,CouponBatch couponBatch);
	
	Long countByCustomerAndStatus(Customer customer, String status );
	
	List<Coupon> findByEventAndCustomer(Event event, Customer customer);
}