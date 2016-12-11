/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.OrderItem;

/**
 * 
 * @author robertxie
 * 2015年9月7日
 */
@RepositoryRestResource(collectionResourceRel = "orderItem", path = "orderItem")
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long>{

}
