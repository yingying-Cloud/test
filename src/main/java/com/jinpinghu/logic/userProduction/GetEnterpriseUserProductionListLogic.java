package com.jinpinghu.logic.userProduction;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbArea;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbAreaDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.userProduction.param.GetEnterpriseUserProductionListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class GetEnterpriseUserProductionListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseUserProductionListParam myParam = (GetEnterpriseUserProductionListParam)logicParam;
		Integer id =StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbAreaDao areaDao = new TbAreaDao(em);
		TbEnterprise enterprise = null;
		if(id!=null) {
			enterprise = enterpriseDao.findById(id);
		}
		TbEnterpriseUserProductionInfoDao tfDao = new TbEnterpriseUserProductionInfoDao(em);
		JSONObject jo = new JSONObject();
		if(enterprise!=null) {
			jo.put("enterpriseAddress",enterprise.getEnterpriseAddress());
			jo.put("enterpriseCreditCode",enterprise.getEnterpriseCreditCode());
			jo.put("enterpriseLegalPerson",enterprise.getEnterpriseLegalPerson());
			jo.put("enterpriseLegalPersonIdcard",enterprise.getEnterpriseLegalPersonIdcard());
			jo.put("enterpriseLinkMobile",enterprise.getEnterpriseLinkMobile());
			jo.put("enterpriseLinkPeople",enterprise.getEnterpriseLinkPeople());
			jo.put("enterpriseType",enterprise.getEnterpriseType());
			jo.put("inputTime",DateTimeUtil.formatTime2(enterprise.getInputTime()));
			jo.put("name",enterprise.getName());
			jo.put("status",enterprise.getStatus());
			jo.put("id",enterprise.getId());
			jo.put("x",enterprise.getX());
			jo.put("y",enterprise.getY());
			jo.put("plantScope",enterprise.getPlantScope());
			jo.put("baseAddress",enterprise.getBaseAddress());
			jo.put("plantName",enterprise.getPlantName());
			jo.put("enterpriseNature",enterprise.getEnterpriseNature());
			jo.put("changes",enterprise.getChanges());
			jo.put("registeredFunds",enterprise.getRegisteredFunds());
			jo.put("type",enterprise.getType());
			jo.put("state",enterprise.getState());
			
			jo.put("area",enterprise.getArea());
			jo.put("confirmArea",enterprise.getConfirmArea());
			jo.put("inflowArea",enterprise.getInflowArea());
			jo.put("outflowArea",enterprise.getOutflowArea());
			jo.put("village",enterprise.getVillage());
			jo.put("nmLimitAmount",enterprise.getNmLimitAmount());
			jo.put("nyLimitAmount",enterprise.getNyLimitAmount());
			
			TbArea area = areaDao.findById(enterprise.getDscd());
			if(area!=null) {
				jo.put("dscd", area.getId());
				jo.put("dsnm", area.getName());
			}
			List<Map<String, Object>> list =tfDao.getListByEnterpriseId(enterprise.getId(),1);
			jo.put("userProduction", list);
		}
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", jo);
		return true;
	}
}
