/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Card;
import com.uletian.ultcrm.business.entity.CardBatch;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Event;

/**
 * 
 * @author robertxie,Javen
 * 2015年10月22日
 */
@RepositoryRestResource(collectionResourceRel = "card", path = "card")
public interface CardRepository extends  PagingAndSortingRepository<Card, Long>{
	@Query("from Card where customer = ?") 
	List<Card> findByCustomer(Customer customer);

	@Query("from Card where id = ?") 
	Card findCardById(String id); 
	
	Card findByCardNoAndCardBatch(String cardNo,CardBatch cardBatch);
	
	Long countByCustomerAndStatus(Customer customer, String status );
	
	Long countByCustomerAndStatusAndType(Customer customer, String status,String type );
	
	Long countByCustomerAndTechAndStatusAndType(Customer customer,Tech tech, String status,String type );
	
	List<Card> findByEventAndCustomer(Event event, Customer customer);

	List<Card> findByCustomerAndStatusAndType(Customer customer, String statusPublish, String string);
	
	
}