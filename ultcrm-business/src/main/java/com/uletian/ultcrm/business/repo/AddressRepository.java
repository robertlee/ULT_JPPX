package com.uletian.ultcrm.business.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Address;
import com.uletian.ultcrm.business.entity.TechModel;

@RepositoryRestResource(collectionResourceRel = "address", path = "address")
public interface AddressRepository extends  PagingAndSortingRepository<Address, Long>{
  
}
