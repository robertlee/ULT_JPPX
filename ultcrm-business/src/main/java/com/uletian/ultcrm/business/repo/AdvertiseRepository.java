/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Advertise;


/**
 * 
 * @author robertxie
 * 
 */
@RepositoryRestResource(collectionResourceRel = "advertise", path = "advertise")
public interface AdvertiseRepository extends  PagingAndSortingRepository<Advertise, Long>{	

	@Query("from advertise")
	public List<Advertise> getAdvertiseList();	

	@Query("from advertise where id = ?")
	public Advertise getAdvertiseById(Long id); 
}
