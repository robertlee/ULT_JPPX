package com.uletian.ultcrm.business.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.TechModel;
import com.uletian.ultcrm.business.entity.PackageItem;

@RepositoryRestResource(collectionResourceRel = "packageItem", path = "packageItem")
public interface PackageItemRepository extends PagingAndSortingRepository<PackageItem, Long>{
	
	Page<PackageItem> findAll(Pageable pageable);

	
    
}
