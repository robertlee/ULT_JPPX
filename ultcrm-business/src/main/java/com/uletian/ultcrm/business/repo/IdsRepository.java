package com.uletian.ultcrm.business.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Ids;

@RepositoryRestResource(collectionResourceRel = "sequence", path = "sequence")
public interface IdsRepository extends PagingAndSortingRepository<Ids, Long>{
	Ids findTopByOrderByIdAsc();
}
