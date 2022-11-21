package com.jinpinghu.logic.workTime;

import java.util.Date;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbResUserWorkTime;
import com.jinpinghu.db.dao.TbUserWorkTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.workTime.param.AddOrUpdateWorkTimeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateWorkTimeLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateWorkTimeParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateWorkTimeParam myParam=(AddOrUpdateWorkTimeParam)AddOrUpdateWorkTimeParam;
		String json=myParam.getJson();
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String workTime = myParam.getWorkTime();
		Integer workId = StringUtils.isEmpty(myParam.getWorkId())?null:Integer.valueOf(myParam.getWorkId());
		Integer userTabId = StringUtils.isEmpty(myParam.getUserTabId())?null:Integer.valueOf(myParam.getUserTabId());
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbUserWorkTimeDao tbUserWorkTimeDao=new TbUserWorkTimeDao(em);
		
//		tbUserWorkTimeDao.delWorkTimeByInputTime(baseId, time,workId);//删除所有当天该基地员工工时
//		tbUserWorkTimeDao.delWorkTimeAllByInputTime(baseId, time,workId);//删除所有当天该基地员工工时
		
//		JSONArray ja=JSONArray.fromObject(json);
//		JSONObject jo;
		TbResUserWorkTime tbResUserWorkTime = id==null?null:tbUserWorkTimeDao.findById(id);
		
		if(tbResUserWorkTime!=null) {
			tbResUserWorkTime.setEnterpriseId(enterpriseId);
			tbResUserWorkTime.setWorkTime(Double.valueOf(workTime));
			tbResUserWorkTime.setWorkId(workId);
			tbUserWorkTimeDao.update(tbResUserWorkTime);
		}else {
			tbResUserWorkTime=new TbResUserWorkTime();
			tbResUserWorkTime.setEnterpriseId(enterpriseId);
			tbResUserWorkTime.setInputTime(new Date());
			tbResUserWorkTime.setUserTabId(userTabId);
			tbResUserWorkTime.setWorkTime(Double.valueOf(workTime));
			tbResUserWorkTime.setWorkId(workId);
			tbUserWorkTimeDao.save(tbResUserWorkTime);
		}
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;

	}

}
