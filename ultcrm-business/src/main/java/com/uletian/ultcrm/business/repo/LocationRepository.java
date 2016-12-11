package com.uletian.ultcrm.business.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Location;

@RepositoryRestResource(collectionResourceRel = "location", path = "location")
public interface LocationRepository extends PagingAndSortingRepository<Location, Long>{
 
	@Query(value = "from Location where createUserId = ? and typeid = 2")
	Location findByCustomerid(Long id);
    
}
