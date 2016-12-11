package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.SubPackage;


@RepositoryRestResource(collectionResourceRel = "sub_package", path = "sub_package")
public interface SubPackageRepository extends PagingAndSortingRepository<SubPackage, Long>{
	
	public List<SubPackage> findByCode(String code);		
    
}
