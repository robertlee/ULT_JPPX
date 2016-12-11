/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Tech;
import com.uletian.ultcrm.business.entity.Order;
import com.uletian.ultcrm.business.entity.Score;
import com.uletian.ultcrm.business.entity.Store;
import com.uletian.ultcrm.business.repo.TechRepository;
import com.uletian.ultcrm.business.repo.CustomerRepository;
import com.uletian.ultcrm.business.repo.OrderRepository;
import com.uletian.ultcrm.business.repo.ScoreRepository;
import com.uletian.ultcrm.business.repo.StoreRepository;

/**
 * 
 * @author robertxie
 * 2015年10月23日
 */
@Component
public class ScoreService {
	
	private static Logger logger = Logger.getLogger(ScoreService.class);
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ScoreRepository scoreRepository;
	
	@Autowired
	private TechRepository techRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private TechService techService;
	
	
	/**
	 * 增量同步
	 * @param scoreList
	 */
	public void updateScoreList(List<Score> scoreList) {
		// 搜索车辆信息
		Tech tech = getTech(scoreList);
		if (tech == null) {
			throw new RuntimeException("不能处理这个数据，车辆和客户信息在ULTCRM系统中不存在");
		}
		Score firstScore = getFirstScore(scoreList);
		for (Score score : scoreList) {
			// 检查是否有重复发送的数据
			logger.info("time is "+ score.getTime());
			Score oldData = scoreRepository.findByItemid(score.getItemid());
			if (oldData  != null) {
				// 重复的数据
				logger.warn("Duplicte crm score data, data is "+score);
				continue;
			}
			score.setTech(tech);
			
			// 查找订单
			Order order = StringUtils.isNotBlank(score.getOrderid())?orderRepository.findByCrmWorkOrderId(score.getOrderid()):null;
			
			// 查找门店
//			Store store = StringUtils.isNotBlank(score.getStorecode())?storeRepository.findByCode(score.getStorecode()):null;
			score.setOrder(order);
//			score.setStore(store);

			scoreRepository.save(score);
		}
		// 更新车辆的总积分
		tech.setTotalScore(firstScore.getTotalscore());
		techRepository.save(tech);
	}
	
	/**
	 * 全量同步
	 * @param scoreList
	 */
	public void fullSyncScoreList(List<Score> scoreList) {
		Tech tech = getTech(scoreList);
		//List<Score> oldList = tech.getScores();// 这个方式需要延迟加载，不行的。
		List<Score> oldList = scoreRepository.findByTech(tech);
		// 将旧的数据删除掉
		if (CollectionUtils.isNotEmpty(oldList)) {
			for (Score oldScore : oldList) {
				scoreRepository.delete(oldScore);
			}
		}
		updateScoreList(scoreList);
	}
	
	private Score getFirstScore(List<Score> scoreList) {
		if (CollectionUtils.isNotEmpty(scoreList)) {
			Score firstScore = scoreList.get(0);
			return firstScore;
		}
		else {
			logger.warn("score list is null.");
			return null;
		}
	}
	
	private Tech getTech(List<Score> scoreList) {
		Score firstScore = getFirstScore(scoreList);
		if (firstScore == null) {
			logger.warn("can not get a first score");
			return null;
		}
		// 搜索车辆信息
		Tech tech = techService.getTechByCrmTechIdAndPlateNo(firstScore.getCrmtechid(),firstScore.getTechlevelno());
		
		if (tech == null) {
			logger.warn("can not found a tech.");
		}
		return tech;
	}
	
	
}	
