/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.repo.TechRepository;

/**
 * 
 * @author robertxie
 * 2015年10月31日
 */
@Component
public class TechService {
	
	@Autowired
	private TechRepository techRepository;
	
	public Tech getTechByCrmTechIdAndPlateNo(String crmTechId, String plateNo) {
		Tech tech = null;
		if (StringUtils.isNotBlank(crmTechId)) {
			List<Tech> techList = techRepository.findByCrmTechId(crmTechId);
			tech = CollectionUtils.isNotEmpty(techList)?techList.get(0):null;
			if (tech == null &&  StringUtils.isNotBlank(plateNo)) {
				techList = techRepository.findByTechlevelno(plateNo);
				tech = CollectionUtils.isNotEmpty(techList)?techList.get(0):null;
			}
		}
		return tech;
	}
}
