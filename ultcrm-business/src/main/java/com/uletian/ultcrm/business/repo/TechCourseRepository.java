package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.TechCourse;

@RepositoryRestResource(collectionResourceRel = "techCourse", path = "techCourse")
public interface TechCourseRepository extends PagingAndSortingRepository<TechCourse, Long>{
	
	List<TechCourse> findBySortidGreaterThan(Long value);
    
}
