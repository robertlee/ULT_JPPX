package com.uletian.ultcrm.business.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uletian.ultcrm.business.entity.Seat;
import com.uletian.ultcrm.business.repo.SeatRepository;
import com.uletian.ultcrm.business.entity.Schedule;
import com.uletian.ultcrm.business.repo.ScheduleRepository;

@RestController 
public class SeatController {
	private static Logger logger = Logger.getLogger(SeatController.class);
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private SeatRepository seatRepository;
  
    @RequestMapping(value = "/getScheduleBy/{scheId}", method = RequestMethod.GET)  
    public Map<String,Object> getScheduleBy(@PathVariable("scheId")Long scheId){
    	Map<String,Object> map = new HashMap<String,Object>();
    	try {
    		logger.info("开始查询课程安排信息，课程参数scheId值为：" + scheId);
			//查询课程安排
    		Schedule schedule = scheduleRepository.getScheduleById(scheId);
    		if(schedule != null){
    			logger.info("查询教师编号为：" + schedule.getTeacher().getId());
    			schedule.setTeachId(schedule.getTeacher().getId());
    			logger.info("查询课程安排信息成功，课程名称为：" + schedule.getClassName());
    			map.put("schedule", schedule);
				//查询座位信息
    			logger.info("根据课程安排编号scheId=" + scheId + "查询座位信息");
		    	List<Seat> seatList = seatRepository.getSeatListByRoom(scheId);
		    	if(seatList != null && !seatList.isEmpty()){
		    		logger.info("查询到教室" + schedule.getClassRoomName() + "的座位数据量为：" + seatList.size());	
		    		map.put("seatList", seatList);
		    	}
    		}
		} catch (Exception e) {
			logger.error("获取相应课程的座位信息失败");
			logger.error(e.getMessage());
		}
    	return map;
    }
	
    @RequestMapping(value = "/getChioceSeat/{roomId}", method = RequestMethod.GET)  
    public List<Seat> getChioceSeat(@PathVariable("roomId")Long roomId){
    	List<Seat> list = new ArrayList<Seat>();
    	try {
			list = seatRepository.getSeatListByRoom(roomId);
		} catch (Exception e) {
			logger.error("获取相应课程的座位信息失败");
			e.printStackTrace();
			return new ArrayList<Seat>();
		}
    	return list;
    }
}
