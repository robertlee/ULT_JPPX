package com.uletian.ultcrm.business.service;

import java.util.HashSet;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uletian.ultcrm.business.entity.Ids;
import com.uletian.ultcrm.business.repo.IdsRepository;

@Service
public class IdsManager {
	
	private static Logger logger = Logger.getLogger(IdsManager.class);
	
	private char[] idsChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
			'U', 'V', 'W', 'X', 'Y', 'Z'};
	@Autowired
	private IdsRepository idsRepository;
	
	@PostConstruct
	public void init(){
		Random random = new Random();
		long count = idsRepository.count();
		if (count == 0) {
			logger.debug("生成序列CODE数据");
			StringBuilder sb = new StringBuilder();
			HashSet<Ids> sequences = new HashSet<Ids>(500);
			for (int i = 0; i < 500; i++) {
				for (int j = 0; j < 6; j++) {
					sb.append(idsChar[random.nextInt(36)]);
				}
				Ids ids = new Ids();
				ids.setCode(sb.toString());
				sb.setLength(0);
				sequences.add(ids);
			}
			idsRepository.save(sequences);
			logger.info("生成序列数据完成");
		}
	}
	
	public String getCode(){
		Ids ids = idsRepository.findTopByOrderByIdAsc();
		String result = ids.getCode();
		idsRepository.delete(ids);
		logger.debug("取出sequence ["+ result +"]");
		return result;
	}
}
