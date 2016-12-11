/**
 * Copyright &copy; 2014 uletian All rights reserved
 */
package com.uletian.ultcrm.business.msg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.uletian.ultcrm.business.entity.Score;
import com.uletian.ultcrm.business.service.ScoreService;
import com.uletian.ultcrm.business.value.ScoreMessage;

import reactor.util.CollectionUtils;


/**
 * 
 * @author robertxie
 * 2015年10月23日
 */
@Component
public class ScoreMsgService extends AbstractMsgHandler {
	private static Logger logger = Logger.getLogger(ScoreMsgService.class);
	public static String CARD_TOPIC = "ICCRMSCORE";
/*	
	@Autowired
	private JmsTemplate topicJmsTemplate;*/
	
	@Autowired
	private ScoreService scoreService;
	
	public ScoreMsgService(){
		super(ScoreMessage.class,"score");
	}

	@Override
	public void addHandlers() {
		MsgActionHandler fullHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Score> scoreList = convertToScoreList((ScoreMessage)msgObject);
				if (CollectionUtils.isEmpty(scoreList)) {
					logger.error("There is a empty score msg , it has not any items.");
				}
				scoreService.fullSyncScoreList(scoreList);
			}
		};
		
		MsgActionHandler newHandler = new MsgActionHandler(){
			@Override
			public void handleMsg(MsgObject msgObject) {
				List<Score> scoreList = convertToScoreList((ScoreMessage)msgObject);
				if (CollectionUtils.isEmpty(scoreList)) {
					logger.error("There is a empty score msg , it has not any items.");
				}
				scoreService.updateScoreList(scoreList);
			}
		};
		
		this.addHandler("FULL", fullHandler);
		this.addHandler("NEW", newHandler);
	}

	public List<Score> convertToScoreList(ScoreMessage scoreMessage) {
		List<Score> scoreList = new ArrayList<Score>();
		for (ScoreMessage.ScoreContent innerScore : scoreMessage.getScoreList()) {
			Score score = new Score();
			BeanUtils.copyProperties(scoreMessage, score);
			BeanUtils.copyProperties(innerScore, score);
			// 添加totalscore, value等字段
			score.setTotalscore(Integer.valueOf(scoreMessage.getTotalscore().trim()));
			score.setValue(Integer.valueOf(innerScore.getValue().trim()));
			score.setDescription(StringUtils.isNotBlank(innerScore.getDesc())?innerScore.getDesc().trim():"");
			scoreList.add(score);
		}
		return scoreList;
	}
	
	
	
	
	
	
	
}
