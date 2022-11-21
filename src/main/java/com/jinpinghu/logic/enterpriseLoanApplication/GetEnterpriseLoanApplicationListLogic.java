package com.jinpinghu.logic.enterpriseLoanApplication;

import com.jinpinghu.common.tools.loan.HandleBankEnum;
import com.jinpinghu.db.dao.TbEnterpriseLoanApplicationDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterpriseLoanApplication.param.GetEnterpriseLoanApplicationListParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GetEnterpriseLoanApplicationListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseLoanApplicationListParam myParam = (GetEnterpriseLoanApplicationListParam)logicParam;
		String status= myParam.getStatus();
		String enterpriseName = myParam.getEnterpriseName();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		List<String> statusList = null;
		if(!StringUtils.isNullOrEmpty(status)) {
			String[] split = status.split(",");
			statusList = Arrays.asList(split);
		}
		TbEnterpriseLoanApplicationDao enterpriseLoanApplicationDao = new TbEnterpriseLoanApplicationDao(em);
		Integer count = enterpriseLoanApplicationDao.findByAllCount(enterpriseName,  statusList,startTime,endTime,enterpriseId);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = enterpriseLoanApplicationDao.findByAll(enterpriseName, statusList,nowPage,pageCount,startTime,endTime,enterpriseId);

		for (Map<String, Object> map : list) {
			map.put("lender", HandleBankEnum.getNameByCode((String)map.get("lender")));
		}
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", list).add("allCounts", count);
		return true;
	}
}
