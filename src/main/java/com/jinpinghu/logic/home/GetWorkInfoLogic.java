package com.jinpinghu.logic.home;

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
import com.jinpinghu.logic.home.param.GetWorkInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetWorkInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetWorkInfoParam myParam=(GetWorkInfoParam)SimpleParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());

		TbWorkDao tbWorkDao=new TbWorkDao(em);
		TbWorkChildDao tbWorkChildDao=new TbWorkChildDao(em);
		
		List<Map<String,Object>> child;
		List<Object[]> childList=null;
		Map<String,Object> map,childMap;

		Object[] o=tbWorkDao.getWorkCropInfo(id);

		map=new HashMap<String,Object>();
		if(o!=null) {
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
			
			map.put("fl", o[13]);
			map.put("ny", o[14]);
			map.put("enterpriseId", o[15]);
			
			map.put("workTime", o[16]);
			map.put("inNum", o[17]);
			map.put("outNum", o[18]);
			map.put("purchaseSource", o[19]);
			
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
		}
		res.add("status", 1)
		.add("result", map)
		.add("msg", "操作成功！");
	
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
