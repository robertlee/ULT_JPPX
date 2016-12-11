package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Function;

@RepositoryRestResource(collectionResourceRel = "function", path = "function")
public interface FunctionRepository extends PagingAndSortingRepository<Function, Long>{
	
	
	
    
}
