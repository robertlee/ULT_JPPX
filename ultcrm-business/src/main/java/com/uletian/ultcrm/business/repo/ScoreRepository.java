/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Score;

/**
 * 
 * @author robertxie
 * 2015年10月26日
 */
@RepositoryRestResource(collectionResourceRel = "score", path = "score")
public interface ScoreRepository extends  PagingAndSortingRepository<Score, Long>{
	
	 //public List<Advertise> queryAdvertiseListByCourseId(Long courseId); 
	
	//根据车牌号查积分
	List<Score> findByTech(Tech tech);
	
	Score findByItemid(String itemid);
}