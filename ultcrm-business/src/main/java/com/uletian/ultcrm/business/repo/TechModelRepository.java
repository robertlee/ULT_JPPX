package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.TechModel;

@RepositoryRestResource(collectionResourceRel = "techModel", path = "techModel")
public interface TechModelRepository extends PagingAndSortingRepository<TechModel, Long>{
	
	 @Query("select a from TechModel a where a.techSery.techCourse.id = ?1 order by  a.techSery.id") 
	 public List<TechModel> queryModelListByCourseId(Long courseId);

	 @Query("from TechModel where code = ?")
	 public TechModel findModelByCode(String code); 
	 
}
