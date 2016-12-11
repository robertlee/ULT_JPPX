/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.TechModel;
import com.uletian.ultcrm.business.entity.SubPackage;
import com.uletian.ultcrm.business.repo.TechModelRepository;
import com.uletian.ultcrm.business.repo.PackageItemRepository;
import com.uletian.ultcrm.business.repo.SubPackageRepository;

/**
 * 
 * @author robertxie
 * 2016年8月21日
 */
@RestController
public class PackageController {
	
	@Autowired
	private SubPackageRepository packageRepository;
	
	@Autowired
	private PackageItemRepository packageItemRepository;
	
	@Autowired
	private TechModelRepository techModelRepository;

	@RequestMapping("/getPackage/{courseId}/{businessTypeId}/{modelId}")
	public Map<String , Object> getCourseList(@PathVariable("courseId")Long courseId, @PathVariable("businessTypeId")Long businessTypeId, @PathVariable("modelId")Long modelId){
		Map<String , Object> result = new HashMap<String,Object>();
		TechModel model = techModelRepository.findOne(modelId);
		result.put("model", model);
		result.put("courseId", courseId);
				
		List<SubPackage> modelPackageList = packageRepository.findByCode("AU42_CNDA");

		if (CollectionUtils.isNotEmpty(modelPackageList)) {
			SubPackage modelPkg = modelPackageList.get(0);
					
			result.put("package", modelPkg);
			
			result.put("packageid",modelPkg.getId());
			
			result.put("packageItems", modelPkg.getPackageItems());
		}		
		return result;	
	}	
}