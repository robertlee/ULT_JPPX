package com.uletian.ultcrm.business.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.uletian.ultcrm.business.entity.Store;

@RepositoryRestResource(collectionResourceRel = "store", path = "store")
public interface StoreRepository extends PagingAndSortingRepository<Store, Long>{
	/**
	 * 查询门店数据
	 * @return
	 */
	@Query("from Store")
	public List<Store> getStoreList();
	
	/**
	 * 根据编号查询门店数据
	 * @return
	 */
	@Query("from Store where id = ?")
	public Store getStoreById(Long id); 
}
