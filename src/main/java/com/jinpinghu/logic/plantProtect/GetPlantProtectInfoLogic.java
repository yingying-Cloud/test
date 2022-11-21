package com.jinpinghu.logic.plantProtect;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbPlantProtection;
import com.jinpinghu.db.bean.TbType;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbMechineDao;
import com.jinpinghu.db.dao.TbPlantProtectionDao;
import com.jinpinghu.db.dao.TbPlantProtectionServerTimeDao;
import com.jinpinghu.db.dao.TbResMechineFileDao;
import com.jinpinghu.db.dao.TbTypeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantProtect.param.GetPlantProtectInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPlantProtectInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetPlantProtectInfoParam myParam = (GetPlantProtectInfoParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		
		TbPlantProtectionDao plantProtectionDao = new TbPlantProtectionDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbPlantProtection plantProtection = plantProtectionDao.findById(id);
		TbMechineDao mechineDao = new TbMechineDao(em);
		TbResMechineFileDao resMechineFileDao = new TbResMechineFileDao(em);
		TbFileDao tbFileDao = new TbFileDao(em);
		TbPlantProtectionServerTimeDao serverTimeDao = new TbPlantProtectionServerTimeDao(em);
		TbTypeDao tbTypeDao = new TbTypeDao(em);
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		if(plantProtection != null) {
			resultMap.put("id", plantProtection.getId());
			resultMap.put("name", plantProtection.getName());
			resultMap.put("orderDescribe", plantProtection.getOrderDescribe());
			resultMap.put("content", plantProtection.getContent());
			resultMap.put("price", plantProtection.getPrice());
			resultMap.put("machine", mechineDao.getMechineIdByPlantProtectionId(plantProtection.getId()));
			resultMap.put("serverType", plantProtection.getServerType());
			resultMap.put("status", plantProtection.getStatus());
			resultMap.put("startTime", plantProtection.getStartTime()==null?null:DateTimeUtil.formatTime(plantProtection.getStartTime()));
			resultMap.put("endTime", plantProtection.getEndTime()==null?null:DateTimeUtil.formatTime(plantProtection.getEndTime()));
			String product = plantProtectionDao.findPlantProtectionProduct( plantProtection.getId());
			resultMap.put("productName",product);
			if(!StringUtils.isEmpty(plantProtection.getServerType())) {
				TbType type = tbTypeDao.findById(Integer.valueOf(plantProtection.getServerType()));
				resultMap.put("serverTypeName",type.getName());
			}
			TbEnterprise enterprise = null;
			if(plantProtection.getEnterpriseId()!=null) {
				enterprise = enterpriseDao.findById(plantProtection.getEnterpriseId());
				if(enterprise!=null) {
					resultMap.put("enterpriseName", enterprise.getName());
					resultMap.put("enterpriseContact", enterprise.getEnterpriseLinkPeople());
					resultMap.put("enterprisePhone", enterprise.getEnterpriseLinkMobile());
					resultMap.put("enterpriseAddress", enterprise.getEnterpriseAddress());
				}
			}
			List<Map<String,Object>> plantProtectionFile = tbFileDao.getPlantProtectionFile(plantProtection.getId());
			resultMap.put("file", plantProtectionFile);
			List<Map<String,Object>> serverTime = serverTimeDao.findPlantProtectionServerTimeList(plantProtection.getId(), null, null);
			resultMap.put("serverTime", serverTime);
			
			
			List<Object[]> oList=mechineDao.getMechineListByPlantProtectionId(plantProtection.getId());
			List<Object[]> fileList=null;
			Map<String,Object> map,fileMap;
			List<Map<String,Object>> machineFile;
			List<Map<String, Object>> machineList = new ArrayList<Map<String,Object>>(oList.size());
			for (Object[] o : oList) {

				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("enterpriseName", o[1]);
				map.put("name", o[2]);
				map.put("model", o[3]);
				map.put("type", o[4]);
				map.put("describe", o[5]);
				map.put("inputTime", DateTimeUtil.formatTime2((Date)o[6]));
				map.put("brand", o[7]);
//				map.put("typeName", o[8]);
				fileList=resMechineFileDao.findFileById(Integer.valueOf(o[0].toString()));
				machineFile=new ArrayList<Map<String,Object>>(fileList.size());
				if(fileList!=null){
					for(Object[] ob:fileList){
						fileMap=new HashMap<String,Object>();
						fileMap.put("id", ob[0]);
						fileMap.put("fileName", ob[1]);
						fileMap.put("fileSize", ob[2]);
						fileMap.put("fileType", ob[3]);
						fileMap.put("fileUrl", ob[4]);
						machineFile.add(fileMap);
					}
					map.put("file", machineFile);
				}
				machineList.add(map);
			}
			resultMap.put("machineInfo",machineList);
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultMap);
		return true;
	}

}
