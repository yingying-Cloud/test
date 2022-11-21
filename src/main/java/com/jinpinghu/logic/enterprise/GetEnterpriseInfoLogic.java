package com.jinpinghu.logic.enterprise;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbArea;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbAreaDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResEnterpriseBrandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetEnterpriseInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseInfoParam myParam = (GetEnterpriseInfoParam)logicParam;
		Integer id =StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbAreaDao areaDao = new TbAreaDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbEnterprise enterprise = null;
		
		if(id!=null) {
			enterprise = enterpriseDao.findById(id);
		}
		TbFileDao tfDao = new TbFileDao(em);
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
			jo.put("businessScope", enterprise.getBusinessScope());
			jo.put("permitForoperationNum", enterprise.getPermitForoperationNum());
			jo.put("operationMode", enterprise.getOperationMode());
			List<Map<String, Object>> tfs =tfDao.getListByEnterpriseId(enterprise.getId());
			jo.put("file", tfs);
			TbArea area = areaDao.findById(enterprise.getDscd());
			if(area!=null) {
				jo.put("dscd", area.getId());
				jo.put("dsnm", area.getName());
			}
//			JSONArray ja = new JSONArray();
//			if(tfs!=null) {
//				for(TbFile tf:tfs) {
//					JSONObject file = new JSONObject();
//					file.put("id",tf.getId());
//					file.put("fileName", tf.getFileName());
//					file.put("fileSize", tf.getFileSize());
//					file.put("fileType", tf.getFileType());
//					file.put("fileUrl",  tf.getFileUrl());
//					ja.add(file);
//				}
//				jo.put("file", ja);
//			}
			
			TbResEnterpriseBrandDao enterpriseBrandDao = new TbResEnterpriseBrandDao(em);
			JSONArray jaBrand = new JSONArray();
			List<Object[]> list = enterpriseBrandDao.findBrandInfo(enterprise.getId());
			if(list!=null) {
				for(Object[] trtb:list) {
					JSONObject brand = new JSONObject();
					brand.put("area",trtb[0]);
					brand.put("yield",trtb[1]);
					brand.put("productName", trtb[2]);
					brand.put("brandId", trtb[3]);
					jaBrand.add(brand);
				}
			}
			jo.put("brand", jaBrand);
			
		}
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", jo);
		return true;
	}
}
