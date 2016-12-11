/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.EventBatch;

/**
 * 
 * @author robertxie
 * 2015年10月22日
 */
@RepositoryRestResource(collectionResourceRel = "eventBatch", path = "eventBatch")
public interface EventBatchRepository extends  PagingAndSortingRepository<EventBatch, Long>{
	
}
