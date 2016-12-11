package com.uletian.ultcrm.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Card;
import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Event;
import com.uletian.ultcrm.business.repo.CardRepository;
import com.uletian.ultcrm.business.service.CardAuthProvide.AuthProvide;

@Component
public class AppointmentAuthProvide implements AuthProvide {

	@Autowired
	private CardRepository cardRepository;
	
	@Override
	public boolean authentication(Event event, Customer customer) {
		boolean result = true;
		//一年级作文培训送学时
		if ("appointment_business1".equals(event.getCode())) {
			List<Card> cards = cardRepository.findByCustomerAndStatusAndType(customer, Card.STATUS_PUBLISH, "W");
			if (cards.size() > 0) {
				result = false;
			}
		}



		return result;
	}

}
