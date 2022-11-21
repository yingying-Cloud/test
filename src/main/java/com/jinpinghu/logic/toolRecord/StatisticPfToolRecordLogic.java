package com.jinpinghu.logic.toolRecord;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.TbToolRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecord.param.GetToolRecordListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StatisticPfToolRecordLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolRecordListParam myParam = (GetToolRecordListParam)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String name =myParam.getName();
		String recordType = myParam.getRecordType();
		Integer toolId = StringUtils.isEmpty(myParam.getToolId())?null:Integer.valueOf(myParam.getToolId());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String type = myParam.getType();
		String toEnterprise = myParam.getToEnterprise();
		String outName = myParam.getOutName();
		String fromName = myParam.getFromName();
		String selectAll = myParam.getSelectAll();
		String uniformPrice = myParam.getUniformPrice();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		JSONArray ja = new JSONArray();
		TbToolRecordDao recordDao2 = new TbToolRecordDao(em);
		
		List<Integer> recordTypeList = null;
		if (!StringUtils.isEmpty(recordType)) {
			String[] recordTypeArray = recordType.split(",");
			recordTypeList = new ArrayList<Integer>(recordTypeArray.length);
			for (int i = 0; i < recordTypeArray.length; i++) {
				recordTypeList.add(Integer.valueOf(recordTypeArray[i]));
			}
		}
		JSONObject jo = new JSONObject();
		Object[] list = recordDao2.statisticPfToolRecord( enterpriseId, toolId, name,recordTypeList,startTime,endTime,type,toEnterprise,outName,fromName,selectAll,permissionEnterpriseIdList,uniformPrice);
		if(list!=null) {
			jo.put("number", list[0]);
			jo.put("price", list[1]);
		}
		res.add("status", 1).add("msg", "操作成功").add("result", jo);
		return true;
	}

}
