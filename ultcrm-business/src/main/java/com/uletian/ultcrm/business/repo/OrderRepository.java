package com.uletian.ultcrm.business.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Order;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{
	
	@Query("from Order where customerid = ?") 
    List<Order> findOrderByCustomer(Long customerid); 
	
	List<Order> findByCustomerAndStatusOrderByCreateTimeDesc(Customer customer, Integer status );
	
	Page<Order> findByCustomerAndStatus(Customer customer, Integer status,Pageable pageable );
	
	Page<Order> findByCustomerAndStatusIn(Customer customer, List<Integer> statusList,Pageable pageable );
	
	List<Order> findByCustomerAndStatusAndLastUpdateTimeAfter(Customer customer, Integer status, Date lastDateTime);
	
	List<Order> findByCustomerAndStatus(Tech tech,Integer status);
	
	List<Order> findByCustomerAndStatusIn(Tech tech, List<Integer> status);
	
	
	List<Order> findByTechAndStatus(Tech tech,Integer status);
	
	List<Order> findByTechAndStatusIn(Tech tech, List<Integer> status);
	
	Order findByCrmWorkOrderId(String crmWorkOrderId);
	
	Long countByCustomerAndStatus(Customer customer, Integer status );
	Long countByCustomerAndStatusIn(Customer customer, List<Integer> status );
	
	Order findTopByCustomerAndTech(Customer customer, Tech tech, Sort sort);
	
	// 查询待评价的订单数量
	Long countByCustomerAndStatusAndOrderCommentsIsNull(Customer customer, Integer status);
	
/*	@Query("select u from Order o where u.firstname = :firstname or u.lastname = :lastname")
	Long countByLastnameOrFirstname(@Param("lastname") String lastname,
	                                 @Param("firstname") String firstname);*/
	
	
	Order findTopByCustomerOrderByCreateTimeDesc(Customer customer);
	@Query("from Order where id = ?")
	Order findById(Long id);
	@Query("from Order where orderid = ?")
	Order findByOrderId(Long orderid);
	
	@Modifying 
	@Query("update Order set status = ? where id = ?")
	void updateStatus(int status,Long id);
	@Query("from Order where status = 1 and schedule.id = ?")
	List<Order> findOrderList(Long scheId);
}
