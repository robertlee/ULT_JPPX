package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.TechSery;

@RepositoryRestResource(collectionResourceRel = "techSery", path = "techSery")
public interface TechSeryRepository extends PagingAndSortingRepository<TechSery, Long>{
    
}