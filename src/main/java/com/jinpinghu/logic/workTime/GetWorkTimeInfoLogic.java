package com.jinpinghu.logic.workTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.TbUserWorkTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.workTime.param.AddOrUpdateWorkTimeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetWorkTimeInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateWorkTimeParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateWorkTimeParam myParam=(AddOrUpdateWorkTimeParam)AddOrUpdateWorkTimeParam;
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbUserWorkTimeDao tbUserWorkTimeDao=new TbUserWorkTimeDao(em);
		
		Object[] o = tbUserWorkTimeDao.getWorkTimeInfo(id);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", o[0]);
		map.put("uid",o[1]);
		map.put("inputTime", DateTimeUtil.formatTime((Date)o[2]));
		map.put("workTime",o[3]==null?"":o[3]);
		map.put("nickName", o[4]);
		map.put("workId", o[5]);
		map.put("workSn", o[6]);
		
		res.add("result", map);
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;

	}

}
