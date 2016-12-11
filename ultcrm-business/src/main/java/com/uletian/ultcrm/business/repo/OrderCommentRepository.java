/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.OrderComment;

/**
 * 
 * @author robertxie
 * 2015年9月11日
 */
@RepositoryRestResource(collectionResourceRel = "orderComment", path = "orderComment")
public interface OrderCommentRepository extends PagingAndSortingRepository<OrderComment, Long>{
	
	public OrderComment findByOrder(Order order);
	
}
