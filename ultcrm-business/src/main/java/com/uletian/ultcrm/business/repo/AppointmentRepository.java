package com.uletian.ultcrm.business.repo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.uletian.ultcrm.business.entity.Appointment;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.TimeSegment;


@RepositoryRestResource(collectionResourceRel = "appointment", path = "appointment")
public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, Long>{
	
	List<Appointment> findByCustomerIdAndStoreIdAndTimeSegment(Long customerId, Long storeId, TimeSegment timeSegment);	
 
	@Query("select a from Appointment a where a.store.id= ?1 and a.timeSegment.id = ?2 and a.order.tech.id = ?3 ")
	List<Appointment> findByStoreAndTimeSegmentAndTech(Long storeId,Long timeSegmentId,Long techId );
	
	@Query("select a from Appointment a where a.order.status= ?1 ")
	List<Appointment> findByOrderStatus(Integer status);
	
	@Query("select a from Appointment a where a.order.status= 1 and a.timeSegment.dateSegment < ?1 ")
	List<Appointment> findExpireData(Date date);
	
	
	List<Appointment> findByOrderIn(Collection<Order> orders);
	
	@Query("from Appointment a where a.order.status= 1 and a.timeSegment.dateSegment = ? and a.timeSegment.timeSegment = ?")
	List<Appointment> findByTimeSegment(Date date, Long time);
	
	Appointment findByOrder(Order order);
	
}