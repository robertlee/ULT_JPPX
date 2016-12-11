package com.uletian.ultcrm.business.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.uletian.ultcrm.business.entity.BusinessType;
import com.uletian.ultcrm.business.entity.ClassType;
import com.uletian.ultcrm.business.entity.Schedule;

@RepositoryRestResource(collectionResourceRel = "businessType", path = "businessType")
public interface BusinessTypeRepository extends PagingAndSortingRepository<BusinessType, Long>{
	/**
	 * 查询课程数据
	 * @return
	 */
	@Query("from BusinessType b where b.id in (select distinct c.classId from ClassType as c where c.startBatch = ?1 and c.addressId = ?2) and b.id in (select s.businessType.id from Schedule as s where s.status = ?3 and s.storeId = ?2)")
	public List<BusinessType> getBusinessList(String batchName,Long addressId,String status);
	
	@Query("from BusinessType where id = ?")
	public BusinessType getBusinessById(Long id); 
}
