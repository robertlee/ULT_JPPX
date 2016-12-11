/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Config;
import com.uletian.ultcrm.business.repo.ConfigRepository;

/**
 * 
 * @author robertxie
 * 2015年9月23日
 */
@Component
public class ConfigService {
	
	@Autowired
	private ConfigRepository configRepository;
	
	public String getValue(String code){
		Config config = configRepository.findByCode(code);
		if (config != null) {
			return config.getValue();
		}
		return "";
	}
}
