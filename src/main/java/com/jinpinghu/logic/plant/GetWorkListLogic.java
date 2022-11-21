package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbWorkChildDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.GetWorkListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetWorkListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetWorkListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetWorkListParam myParam=(GetWorkListParam)GetWorkListParam;
		String search=myParam.getSearch();
		String crop=myParam.getCrop();
		String workSn=myParam.getWorkSn();
		String enterpriseId=myParam.getEnterpriseId();
		String endTime=myParam.getEndTime();
		String startTime=myParam.getStartTime();
		String brandId = myParam.getBrandId();
		String statuss = StringUtils.isEmpty(myParam.getStatus()) ? "0,1,2" : myParam.getStatus();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		String[] statusArray = statuss.split(",");
		List<Integer> statusList = new ArrayList<Integer>();
		for (int i = 0; i < statusArray.length; i++) {
			String status = statusArray[i];
			statusList.add(Integer.valueOf(status));
		}
		
		
		TbWorkDao tbWorkDao=new TbWorkDao(em);
		TbWorkChildDao tbWorkChildDao=new TbWorkChildDao(em);
		
		Integer count=tbWorkDao.getWorkListCount(search, enterpriseId, crop, workSn, startTime, endTime,statusList,brandId);
		
		if(count!=null&&count>0){
			List<Object[]> list=tbWorkDao.getWorkList(search, enterpriseId, crop, workSn, startTime, endTime, nowPage, pageCount,statusList,brandId);
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> child;
			List<Object[]> childList=null;
			Map<String,Object> map,childMap;
			for(Object[] o:list){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("enterpriseName", o[1]);
				map.put("addPeople", o[2]);
				map.put("workName", o[3]);
				map.put("workSn", o[4]);
				map.put("landBlockSn", o[5]);
				map.put("area", o[6]);
				map.put("crop", o[7]);
				map.put("startTime", DateTimeUtil.formatTime((Date)o[8]));
				map.put("endTime", DateTimeUtil.formatTime((Date)o[9]));
				map.put("recoveryTime", DateTimeUtil.formatTime((Date)o[10]));
				map.put("expectedProduction", o[11]);
				map.put("inputTime", DateTimeUtil.formatTime2((Date)o[12]));
				map.put("fileUrl", o[13]);
				
				map.put("purchaseSource", o[14]);
				map.put("purchasePeople", o[15]);
				map.put("purchaseTime", o[16]);
				
				map.put("greenhousesName", o[18]);
				map.put("workTime", o[19]);
				
				if(new Date().before((Date)o[8])){
					map.put("status", "0");
				}else if(new Date().before((Date)o[9])){
					map.put("status", "1");
				}else{
					map.put("status", "2");
				}
				childList=tbWorkChildDao.getWorkChildList(Integer.valueOf(o[0].toString()));
				if(childList!=null){
					child=new ArrayList<Map<String,Object>>();
					for(Object[] ob:childList){
						childMap=new HashMap<String,Object>();
						childMap.put("id", ob[0]);
						childMap.put("workId", ob[1]);
						childMap.put("name", ob[2]);
						childMap.put("startTime", DateTimeUtil.formatTime((Date)ob[3]));
						childMap.put("endTime", DateTimeUtil.formatTime((Date)ob[4]));
						child.add(childMap);
					}
					map.put("child", child);
				}
				result.add(map);
			}
			
			res.add("status", 1)
			.add("allCounts", count)
			.add("result", result)
			.add("msg", "操作成功！");
		}

		res.add("status", 1)
		.add("msg", "操作成功！")
		.add("allCounts", count);
		return true;
	}
	
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
