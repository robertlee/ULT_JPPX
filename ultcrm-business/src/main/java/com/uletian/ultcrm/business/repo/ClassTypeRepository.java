package com.uletian.ultcrm.business.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.uletian.ultcrm.business.entity.ClassType;

@RepositoryRestResource(collectionResourceRel = "classType", path = "classType")
public interface ClassTypeRepository extends PagingAndSortingRepository<ClassType, Long>{
	/**
	 * 查询所有的课程批次
	 * @return
	 */
	@Query("from ClassType where id in (select id from ClassType group by startBatch)")
	public List<ClassType> getClassBatchList();
	
	/**
	 * 根据课程批次编号查询该批次下所有的门店地址
	 * @return
	 */
	@Query("from ClassType where id in (select id from ClassType where startBatch = ? group by addressId)")
	public List<ClassType> getAddressList(String batchName);
	
	/**
	 * 根据门店编号查询该门店下所有的课程
	 * @return
	 */
	@Query("select DISTINCT className from ClassType where id = ? and addressId = ?")
	public List<ClassType> getClassList(Long batchId,Long addressId);
	
	/**
	 * 根据课程编号查询门店地址
	 * @return
	 */
	@Query("from ClassType where startBatch = ?1 and addressId = ?2 group by addressId")
	public ClassType getAddressById(String batchName,Long addressId); 
}
