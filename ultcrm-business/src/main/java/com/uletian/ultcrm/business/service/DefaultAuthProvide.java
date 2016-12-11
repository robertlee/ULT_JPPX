package com.uletian.ultcrm.business.service;

import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Event;
import com.uletian.ultcrm.business.service.CardAuthProvide.AuthProvide;

@Component
public class DefaultAuthProvide implements AuthProvide {

	@Override
	public boolean authentication(Event event, Customer customer) {
		return true;
	}

}
