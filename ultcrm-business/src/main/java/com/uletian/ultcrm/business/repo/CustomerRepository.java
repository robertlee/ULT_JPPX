package com.uletian.ultcrm.business.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Customer;

@RepositoryRestResource(collectionResourceRel = "Customer", path = "Customer")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>{
	
	@Query("from Customer where phone = ?")
	Customer findByPhone(String phone);

	@Query("from Customer where syncid = ?")
	Customer findBySyncid(String syncid);

	@Query("from Customer where openid = ?")
	Customer findByOpenid(String openId);
	
	Customer findByCrmCustomerId(String crmCustomerId);
}
