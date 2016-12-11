package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Employee;
import com.uletian.ultcrm.business.entity.Store;

@RepositoryRestResource(collectionResourceRel = "employee", path = "employee")
public interface EmployeeRepository extends  PagingAndSortingRepository<Employee, Long>{
	
	
	public Employee findByStoreAndName(Store store, String name);
	
}
