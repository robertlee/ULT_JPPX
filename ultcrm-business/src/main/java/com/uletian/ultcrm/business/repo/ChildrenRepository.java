package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Children;


@RepositoryRestResource(collectionResourceRel = "children", path = "children")
public interface ChildrenRepository extends PagingAndSortingRepository<Children,Long>{
	
	@Query("from Children where id in (select id from Children where customerid = ?)") 
    List<Children> findChildrenListByCustomer(Long customerid);
	
	@Query("from Children where customerid = ? and name= ?") 
    List<Children> findChildrenList(Long customerid,String childName);
}




