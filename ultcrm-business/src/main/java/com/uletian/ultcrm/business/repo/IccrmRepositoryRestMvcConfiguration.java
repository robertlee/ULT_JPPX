/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.repo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.uletian.ultcrm.business.entity.Address;
import com.uletian.ultcrm.business.entity.Advertise;
import com.uletian.ultcrm.business.entity.Appointment;
import com.uletian.ultcrm.business.entity.Area;
import com.uletian.ultcrm.business.entity.BusinessType;
import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.TechCourse;
import com.uletian.ultcrm.business.entity.TechModel;
import com.uletian.ultcrm.business.entity.TechSery;
import com.uletian.ultcrm.business.entity.City;
import com.uletian.ultcrm.business.entity.Company;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Function;
import com.uletian.ultcrm.business.entity.Location;
import com.uletian.ultcrm.business.entity.LoginLog;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.OrderItem;
import com.uletian.ultcrm.business.entity.PackageItem;
import com.uletian.ultcrm.business.entity.Province;
import com.uletian.ultcrm.business.entity.Role;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.entity.SubPackage;
import com.uletian.ultcrm.business.entity.TimeSegment;
import com.uletian.ultcrm.business.entity.User;

/**
 * 
 * @author robertxie
 * 2015年8月22日
 */
@Configuration
public class IccrmRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {
 
    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        
    	config.exposeIdsFor(Address.class);
        config.exposeIdsFor(Advertise.class);
        config.exposeIdsFor(Appointment.class);
        config.exposeIdsFor(Area.class);
        config.exposeIdsFor(BusinessType.class);
        config.exposeIdsFor(Tech.class);
        config.exposeIdsFor(TechCourse.class);
        config.exposeIdsFor(TechModel.class);
        config.exposeIdsFor(TechSery.class);
        config.exposeIdsFor(City.class);
        config.exposeIdsFor(Company.class);
        config.exposeIdsFor(Customer.class);
        config.exposeIdsFor(Function.class);
        config.exposeIdsFor(Location.class);
        config.exposeIdsFor(LoginLog.class);
        config.exposeIdsFor(SubPackage.class);
        config.exposeIdsFor(Order.class);
        config.exposeIdsFor(OrderItem.class);
        config.exposeIdsFor(com.uletian.ultcrm.business.entity.Package.class);
        
        config.exposeIdsFor(PackageItem.class);
        config.exposeIdsFor(Province.class);
        config.exposeIdsFor(Role.class);
        config.exposeIdsFor(Store.class);
        config.exposeIdsFor(TimeSegment.class);
        config.exposeIdsFor(User.class);
        
       
    }
}
