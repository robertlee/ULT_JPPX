/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.CardConsume;

/**
 * 
 * @author robertxie
 * 2015年10月27日
 */
@RepositoryRestResource(collectionResourceRel = "cardConsume", path = "cardConsume")
public interface CardConsumeRepository extends  PagingAndSortingRepository<CardConsume, Long>{
	
	CardConsume findByItemid(String itemid);
	
}
