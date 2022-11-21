package com.jinpinghu.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.inspection.InspectionParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetShopSortLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		String code = param.getCode();
		String month = param.getMonth();
		if(StringUtil.isNullOrEmpty(month)){
			month = DateTimeUtil.formatSelf(new Date(), "yyyy-MM");
		}
		TbEnterpriseDao dao = new TbEnterpriseDao(em);
		TbUserDao tbUserDao = new TbUserDao(em);
		
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbUser tbUser = tbUserDao.checkLogin2(code);
		TbEnterprise tbEnterprise = resUserEnterpriseDao.findByUserTabId(tbUser.getId());
		Integer enterpriseId = tbEnterprise == null ? null : tbEnterprise.getId();
		
		List<Map<String, Object>> list = dao.getEnterpriseDayCount(month);
		int No = 0;
		int count = 1;
		String oldCount = null;
		String newCount = null;
		String rate = null;
		
		for(Map<String,Object> map : list){
			newCount = map.get("count").toString();
			//并列，排名不变，count+1
			if(oldCount != null && oldCount.equals(newCount)){
				count++;
			//不并列，排名加 count,并将 count 重置为1，更新 oldCount
			}else{
				No += count;
				count = 1;
				oldCount = newCount;
			}
			if(Integer.valueOf(map.get("enterpriseId").toString()).equals(enterpriseId)){
				rate = map.get("rate").toString();
				break;
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rate", rate);
		result.put("no", No);
		
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em)  throws Exception {
		
		return true;
	}
}
