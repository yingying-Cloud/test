package com.jinpinghu.logic.inspection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbInspectionDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetInspectionListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;

		Integer enterpriseId = param.getEnterpriseId();
		Integer status = param.getStatus();
		String enterpriseName = param.getEnterpriseName();
		Integer nowPage = StringUtil.isNullOrEmpty(param.getNowPage()) ? null : Integer.valueOf(param.getNowPage());
		Integer pageCount = StringUtil.isNullOrEmpty(param.getPageCount()) ? null : Integer.valueOf(param.getPageCount());
		String startTime = param.getStartTime();
		String endTime = param.getEndTime();
		String dscd = null;
		
		//判断用户身份
		TbResUserRoleDao resRoleDao = new TbResUserRoleDao(em);
		String roleId = resRoleDao.findRoleIdByUserId(param.getUserId()).toString();
		
		if(roleId.equals("5") || roleId.equals("14")){//农资店
			
		}else if(roleId.equals("11")){//农业农村局
			
		}else if(roleId.equals("12")){//农资监管员
			TbUserDao uDao = new TbUserDao(em);
			TbUser user = uDao.checkLogin2(param.getUserId());
			dscd = user.getDscd();
		}
		
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> dayList = null;
		Map<String, Object> dayMap = null;
		
		
		TbInspectionDao dao = new TbInspectionDao(em);
		Integer count = dao.getInspectionListCount(status, enterpriseName, startTime, endTime, enterpriseId, dscd);
		
		if(count != null && count > 0){
			list = dao.getInspectionList(status, enterpriseName, startTime, endTime, enterpriseId, dscd, nowPage, pageCount);
			String time = "";
			for(Map<String, Object> map : list){
				//如果和上一条记录不是同一天，放在新一天的 dayList 中存入 result
				String newTime = map.get("inputTime").toString().substring(0,10);
				if(!time.equals(newTime)){
					//将之前的同一天的记录存入 dayMap
					if(dayList != null)
						dayMap.put("record", dayList);
					//将之前的同一天的记录存入 result
					if(dayMap != null)
						result.add(dayMap);
					//开始新的 dayMap， dayList
					String week = null;
					dayMap = new HashMap<String, Object>();
					dayList = new ArrayList<Map<String, Object>>();
					//匹配星期几
					switch(Integer.valueOf(map.get("week").toString())){
					case 1:
						week = "星期一";
						break;
					case 2:
						week = "星期二";
						break;
					case 3:
						week = "星期三";
						break;
					case 4:
						week = "星期四";
						break;
					case 5:
						week = "星期五";
						break;
					case 6:
						week = "星期六";
						break;
					case 0:
						week = "星期日";
						break;
					}
					//将日期存回原  map
					dayMap.put("fullDate", map.get("date")+" "+week);
					//将当前的记录存入新的 dayList
					dayList.add(map);
				}else{
					dayList.add(map);
				}

				time = map.get("inputTime").toString().substring(0,10);
				
			}
			dayMap.put("record", dayList);
			result.add(dayMap);
		}
		
		res.add("result", result);
		res.add("allCounts", count);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
