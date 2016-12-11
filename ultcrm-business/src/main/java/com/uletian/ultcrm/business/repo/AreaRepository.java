package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Area;

@RepositoryRestResource(collectionResourceRel = "area", path = "area")
public interface AreaRepository extends PagingAndSortingRepository<Area, Long>{
	


	
    
}
