package com.jinpinghu.logic.workTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbUserWorkTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.workTime.param.StatisticalWorkingHoursParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticalWorkingHoursLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam StatisticalWorkingHoursParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		StatisticalWorkingHoursParam myParam=(StatisticalWorkingHoursParam)StatisticalWorkingHoursParam;
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		Integer enterpriseId=myParam.getEnterpriseId();
		String nickName=myParam.getNickName();
		List<String> roleId=StringUtil.isNullOrEmpty(myParam.getRoleId())?null:Arrays.asList(myParam.getRoleId().split(","));
		
		TbUserWorkTimeDao tbUserWorkTimeDao=new TbUserWorkTimeDao(em);
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		
		Integer count=tbUserWorkTimeDao.StatisticalWorkingHoursCount(enterpriseId, startTime, endTime, nickName, roleId);
		List<Object[]> list = tbUserWorkTimeDao.StatisticalWorkingHours(enterpriseId, startTime, endTime, nickName, roleId, nowPage, pageCount);
		
		if(list!=null){
			for(Object[] o:list){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("uid",o[1]);
				map.put("workTime", new BigDecimal(o[2].toString()).setScale(1, BigDecimal.ROUND_HALF_UP));
				map.put("nickName", o[3]);
				map.put("roleName", o[4]);
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
