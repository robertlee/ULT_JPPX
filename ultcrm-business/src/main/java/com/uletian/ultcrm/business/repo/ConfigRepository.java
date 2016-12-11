package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Config;

@RepositoryRestResource(collectionResourceRel = "config", path = "config")
public interface ConfigRepository extends  PagingAndSortingRepository<Config, Long>{
	
	Config findByCode(String code);
	
}
