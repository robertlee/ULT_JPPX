/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.CardBatch;

/**
 * 
 * @author robertxie
 * 2015年10月22日
 */
@RepositoryRestResource(collectionResourceRel = "cardBatch", path = "cardBatch")
public interface CardBatchRepository extends  PagingAndSortingRepository<CardBatch, Long>{
	
	CardBatch findByBatchNo(String batchNo);
	
}
