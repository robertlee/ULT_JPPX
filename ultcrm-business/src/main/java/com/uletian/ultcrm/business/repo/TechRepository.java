package com.uletian.ultcrm.business.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Score;

@RepositoryRestResource(collectionResourceRel = "tech", path = "tech")
public interface TechRepository extends PagingAndSortingRepository<Tech, Long>{
	
	@Query("from Tech where customerid = ?") 
    List<Tech> findTechByCustomer(Long customerid);
	
	List<Tech> findByCustomerOrderByCreateTimeDesc(Customer customer);

	@Query("from Tech where techlevelno = ?") 
	Tech findTechByTechlevelno(String techlevelno);
	
	List<Tech> findByTechlevelno(String techlevelno);
	
	List<Tech> findByCrmTechId(String crmTechId);
	
	@Query("select sum(c.totalScore) as sumScore from Tech c where c.customer = ?1 " )
	List<Long> sumTotalScoreByCustomer(Customer customer);
	
	Tech findTopByCustomerOrderByCreateTimeDesc(Customer customer);
	
}
