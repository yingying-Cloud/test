package com.jinpinghu.logic.workTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbUserWorkTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.workTime.param.GetWorkTimeListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetWorkTimeListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetWorkTimeListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetWorkTimeListParam myParam=(GetWorkTimeListParam)GetWorkTimeListParam;
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		Integer enterpriseId=myParam.getEnterpriseId();
		List<String> roleId=StringUtil.isNullOrEmpty(myParam.getRoleId())?null:Arrays.asList(myParam.getRoleId().split(","));
		Integer workId=StringUtil.isNullOrEmpty(myParam.getWorkId())?null:Integer.valueOf(myParam.getWorkId());
		String workSn = myParam.getWorkSn();
		String name = myParam.getName();
		TbUserWorkTimeDao tbUserWorkTimeDao=new TbUserWorkTimeDao(em);
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		
		Map<String,Object> map;
		
		Integer count=tbUserWorkTimeDao.getWorkTimeCount(enterpriseId, startTime, endTime, roleId,workId,workSn,name);
		List<Object[]> list = tbUserWorkTimeDao.getWorkTimeList(enterpriseId, startTime, endTime, roleId, nowPage, pageCount,workId,workSn,name);
		
		if(list!=null){
			for(Object[] o:list){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("uid",o[1]);
				map.put("inputTime", DateTimeUtil.formatTime((Date)o[2]));
				map.put("workTime",o[3]==null?"":o[3]);
				map.put("nickName", o[4]);
				map.put("workId", o[5]);
				map.put("workSn", o[6]);
				result.add(map);
			}
		}
		
		res.add("status", 1)
		.add("msg", "操作成功！")
		.add("result", result)
		.add("allCounts",count);
		return true;

	}

}
