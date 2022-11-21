package com.jinpinghu.logic.plantService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbPlantService;
import com.jinpinghu.db.bean.TbType;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantProtectionServerTimeDao;
import com.jinpinghu.db.dao.TbPlantServiceDao;
import com.jinpinghu.db.dao.TbPlantServiceServerTimeDao;
import com.jinpinghu.db.dao.TbTypeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantService.param.GetPlantServiceInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPlantServiceInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetPlantServiceInfoParam myParam = (GetPlantServiceInfoParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		
		TbPlantServiceDao plantServiceDao = new TbPlantServiceDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbPlantService plantService = plantServiceDao.findById(id);
		TbFileDao tbFileDao = new TbFileDao(em);
		TbPlantServiceServerTimeDao serverTimeDao = new TbPlantServiceServerTimeDao(em);
		TbTypeDao tbTypeDao = new TbTypeDao(em);
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		if(plantService != null) {
			resultMap.put("id", plantService.getId());
			resultMap.put("name", plantService.getName());
			resultMap.put("orderDescribe", plantService.getOrderDescribe());
			resultMap.put("content", plantService.getContent());
			resultMap.put("price", plantService.getPrice());
			resultMap.put("serverType", plantService.getServerType());
			resultMap.put("startTime", plantService.getStartTime()==null?null:DateTimeUtil.formatTime(plantService.getStartTime()));
			resultMap.put("endTime", plantService.getEndTime()==null?null:DateTimeUtil.formatTime(plantService.getEndTime()));
			resultMap.put("status", plantService.getStatus());
			if(!StringUtils.isEmpty(plantService.getServerType())) {
				TbType type = tbTypeDao.findById(Integer.valueOf(plantService.getServerType()));
				resultMap.put("serverTypeName",type.getName());
			}
			TbEnterprise enterprise = null;
			if(plantService.getEnterpriseId()!=null) {
				enterprise = enterpriseDao.findById(plantService.getEnterpriseId());
				if(enterprise!=null) {
					resultMap.put("enterpriseName", enterprise.getName());resultMap.put("enterpriseContact", enterprise.getEnterpriseLinkPeople());
					resultMap.put("enterprisePhone", enterprise.getEnterpriseLinkMobile());
					resultMap.put("enterpriseAddress", enterprise.getEnterpriseAddress());
				}
			}
			List<Map<String,Object>> plantServiceFile = tbFileDao.getPlantServiceFile(plantService.getId());
			resultMap.put("file", plantServiceFile);
			List<Map<String,Object>> serverTime = serverTimeDao.findPlantServiceServerTimeList(plantService.getId(), null, null);
			resultMap.put("serverTime", serverTime);
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultMap);
		return true;
	}

}
