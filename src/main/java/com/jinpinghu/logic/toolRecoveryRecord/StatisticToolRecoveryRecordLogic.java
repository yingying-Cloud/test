package com.jinpinghu.logic.toolRecoveryRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbToolRecoveryRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.GetToolRecoveryRecordListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticToolRecoveryRecordLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolRecoveryRecordListParam myParam = (GetToolRecoveryRecordListParam)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String name =myParam.getName();
		//Integer toolRecoveryId = StringUtils.isEmpty(myParam.getToolRecoveryId())?null:Integer.valueOf(myParam.getToolRecoveryId());
		List<String> toolRecoveryIdList = StringUtil.isNullOrEmpty(myParam.getToolRecoveryId()) ? null : Arrays.asList(myParam.getToolRecoveryId().split(","));
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String useName = myParam.getUseName();
		String enterpriseName = myParam.getEnterpriseName();
		String toolName = myParam.getToolName();
		Integer linkOrderInfoId =  StringUtils.isEmpty(myParam.getLinkOrderInfoId())?null:Integer.valueOf(myParam.getLinkOrderInfoId());
		String recordNumber = myParam.getRecordNumber();
		String selectAll = myParam.getSelectAll();
		String dscd = myParam.getDscd();
		if(!StringUtil.isNullOrEmpty(dscd)){
			if(dscd.substring(2, 4).equals("00")){
				dscd = dscd.substring(0,2)+"%";
			}else if(dscd.substring(4, 6).equals("00")){
				dscd = dscd.substring(0,4)+"%";
			}else if(dscd.substring(6, 9).equals("000")){
				dscd = dscd.substring(0,6)+"%";
			}else if(dscd.substring(9, 12).equals("000")){
				dscd = dscd.substring(0,9)+"%";
			}
		}
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		
		TbToolRecoveryRecordDao recordDao2 = new TbToolRecoveryRecordDao(em);
		
		List<Map<String, Object>> list = recordDao2.statisticToolRecoveryRecordList( enterpriseId, toolRecoveryIdList, name,
				startTime,endTime,useName,enterpriseName,toolName,linkOrderInfoId,recordNumber,selectAll,permissionEnterpriseIdList,dscd);
		res.add("status", 1).add("msg", "操作成功").add("result", list);
		return true;
	}
	
	
}
