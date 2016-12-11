package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.CustomerCode;

@RepositoryRestResource(collectionResourceRel = "customerCode", path = "customerCode")
public interface CustomerCodeRepository extends PagingAndSortingRepository<CustomerCode, Long>{
	
	@Query("from CustomerCode where customerid = ?") 
	public List<CustomerCode> findByCustomer(Customer customer);
	
	@Query("from CustomerCode where phone = ?") 
	public CustomerCode findCodeByPhone(String phone);

	public CustomerCode findByImageCode(String imgCode);
	
}
