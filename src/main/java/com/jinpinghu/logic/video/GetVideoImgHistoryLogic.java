package com.jinpinghu.logic.video;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.TbVideoImgDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.video.param.GetVideoImgHistoryParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetVideoImgHistoryLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetVideoImgHistoryParam myParam =(GetVideoImgHistoryParam)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		String startTime =StringUtils.isEmpty(myParam.getStartTime())?"2019-01-01":myParam.getStartTime();
		String endTime = StringUtils.isEmpty(myParam.getEndTime())?DateTimeUtil.formatTime(new Date()):myParam.getEndTime();
		String videoName = myParam.getVideoName();
		Integer videoId = StringUtils.isEmpty(myParam.getVideoId()) ? null : Integer.valueOf(myParam.getVideoId());
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		TbVideoImgDao imgDao = new TbVideoImgDao(em);
		
		Date nowDate = DateTimeUtil.formatTime(endTime);
		Date tempDate = DateTimeUtil.formatTime(startTime);
		int nowY = getYear(nowDate);
		int nowM = getMonth(nowDate);
		List<String> tm = new ArrayList<String>();
		while(true){
			String tabName = formatSelf(tempDate, "yyyy_MM");
			int tempY = getYear(tempDate);
			int tempM = getMonth(tempDate);
			tm.add(tabName);
			if(tempY==nowY&&tempM==nowM){
				break;
			}else{
				tempDate = addMonth(tempDate, 1);
			}
		}
		
		
		Integer count = imgDao.findHistoryImgByVideoIdCount(videoId, startTime, endTime,videoName,enterpriseId,tm);
		if(count==null||count==0) {
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		JSONArray ja = new JSONArray();
		List<Object[]> list = imgDao.findHistoryImgByVideoId(videoId, startTime, endTime,nowPage,pageCount,videoName,enterpriseId,tm);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo =new JSONObject();
				jo.put("fileUrl", o[0]);
				jo.put("videoName", o[1]);
				jo.put("inputTime", o[2]);
				ja.add(jo);
			}
		}
		res.add("status", 1).add("msg", "操作成功").add("result", ja).add("allCounts", count);
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}
	
	DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Calendar c = Calendar.getInstance();
	
	private int getYear(Date date){
		try {
			c.setTime(date);
			return c.get(Calendar.YEAR);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	private int getMonth(Date date){
		try {
			c.setTime(date);
			return c.get(Calendar.MONTH);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	private Date addMonth(Date date,int m){
		try {
			c.setTime(date);
			c.add(Calendar.MONTH, m);
			return c.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private String formatSelf(Date date,String f){
		if(date!=null){
			DateFormat d = new SimpleDateFormat(f);
			return d.format(date);
		}else{
			return "";
		}
	}
	
}
