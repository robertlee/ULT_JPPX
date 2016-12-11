/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.TemplateMessageResultCount;

/**
 * 
 * @author robertxie
 * 2015年9月29日
 */
@RepositoryRestResource(collectionResourceRel = "address", path = "address")
public interface TemplateMessageResultCountRepository extends  PagingAndSortingRepository<TemplateMessageResultCount, Long>{
	
	TemplateMessageResultCount findByCustomerAndMsgId(Customer customer , String msgId);
	
}
