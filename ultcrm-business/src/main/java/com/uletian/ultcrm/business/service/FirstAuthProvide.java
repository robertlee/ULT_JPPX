package com.uletian.ultcrm.business.service;

import org.springframework.stereotype.Service;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Event;
import com.uletian.ultcrm.business.service.CardAuthProvide.AuthProvide;

@Service
public class FirstAuthProvide implements AuthProvide {

	@Override
	public boolean authentication(Event event, Customer customer) {
		boolean result = false;
		if ("firstjoin".equals(event.getCode())) {
			result = true;
		}else{
			result = true;
		}
		return result;
	}

}
