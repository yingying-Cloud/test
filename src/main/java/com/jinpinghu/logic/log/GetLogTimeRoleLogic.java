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

public class GetLogTimeRoleLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetLogTimeParam myParam = (GetLogTimeParam)logicParam;
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		
		TbLogDao logDao = new TbLogDao(em);
		List<Map<String, Object>> logTime = logDao.getLogRoleTime(startTime,endTime);
		res.add("result", logTime);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetLogTimeParam myParam = (GetLogTimeParam)logicParam;
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		if(StringUtils.isEmpty(startTime)||StringUtils.isEmpty(endTime)) {
			res.add("status", -1);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}

}
