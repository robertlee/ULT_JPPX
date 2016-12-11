/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.CouponBatch;

/**
 * 
 * @author robertxie
 */
@RepositoryRestResource(collectionResourceRel = "couponBatch", path = "couponBatch")
public interface CouponBatchRepository extends  PagingAndSortingRepository<CouponBatch, Long>{
	CouponBatch findByBatchNo(String batchNo);
}
