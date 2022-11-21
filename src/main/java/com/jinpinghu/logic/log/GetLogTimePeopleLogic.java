package com.jinpinghu.logic.log;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.db.dao.TbLogDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.log.param.GetLogTimeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetLogTimePeopleLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetLogTimeParam myParam = (GetLogTimeParam)logicParam;
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String name = myParam.getName();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		TbLogDao logDao = new TbLogDao(em);
		Integer count = logDao.getLogPeopleTimeCount(name,startTime,endTime);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据！");
			return true;
		}
		List<Map<String, Object>> logTime = logDao.getLogPeopleTime(name,startTime,endTime,nowPage,pageCount);
		res.add("result", logTime);
		res.add("allCounts", count);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
}
