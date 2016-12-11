/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.TechCourse;
import com.uletian.ultcrm.business.entity.TechModel;
import com.uletian.ultcrm.business.entity.TechSery;
import com.uletian.ultcrm.business.repo.TechCourseRepository;
import com.uletian.ultcrm.business.repo.TechModelRepository;

/**
 * 选择车型和门店的控制器
 * @author robertxie
 * 2015年8月21日
 */
@RestController
public class SelectModelController {	
	@Autowired
	private TechCourseRepository techCourseRepository;
	
	@Autowired
	private TechModelRepository techModelRepository;
	
	@RequestMapping("/getTechModelByCourseId/{courseId}")
	@Cacheable("tech")
	public List<Map<String,Object>> getCourseList(@PathVariable("courseId")Long courseId) throws InterruptedException{
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		TechCourse techCourse = techCourseRepository.findOne(courseId);
		if (techCourse != null) {
			List<TechSery> serList = techCourse.getTechSeries();
			for (TechSery sery:serList) {
				List<TechModel> modelList = sery.getTechModels();
				if (!CollectionUtils.isEmpty(modelList)) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("seryName", sery.getName());
					List<Map<String,Object>> modelDataList = new ArrayList<Map<String,Object>>();
					map.put("modelData", modelDataList);
					for (TechModel model: modelList) {
						Map<String,Object> modelData = new HashMap<String,Object>();
						modelData.put("id", model.getId());
						modelData.put("name", model.getName());
						modelDataList.add(modelData);
					}
					result.add(map);
				}
			}
		}
		return result;
	}	
}
