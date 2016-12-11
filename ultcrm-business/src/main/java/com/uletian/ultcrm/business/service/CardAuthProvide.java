package com.uletian.ultcrm.business.service;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uletian.ultcrm.business.entity.Customer;
import com.uletian.ultcrm.business.entity.Event;

@Service
public class CardAuthProvide {

	private ArrayList<AuthProvide> authProvides;
	
	@Autowired
	private FirstAuthProvide firstAuthProvide;
	
	@Autowired
	private DefaultAuthProvide defaultAuthProvide;
	
	@Autowired
	private AppointmentAuthProvide appointmentAuthProvide;
	
	@PostConstruct
	public void init(){
		if (authProvides == null) {
			authProvides = new ArrayList<CardAuthProvide.AuthProvide>(0);
		}
		authProvides.add(defaultAuthProvide);
		authProvides.add(firstAuthProvide);
		authProvides.add(appointmentAuthProvide);
	}
	
	public boolean authentication(Event event, Customer customer){
		boolean authResult = true;
		for (AuthProvide authProvide : authProvides) {
			authResult = authProvide.authentication(event, customer);
			if (authResult == false) {
				return false;
			}
		}
		return authResult;
	}
	
	public interface AuthProvide{
		boolean authentication(Event event, Customer customer);
	}
}
