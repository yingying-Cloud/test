package com.jinpinghu.logic.plantService;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPlantService;
import com.jinpinghu.db.bean.TbPlantServiceServerTime;
import com.jinpinghu.db.bean.TbResPlantServiceFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantServiceDao;
import com.jinpinghu.db.dao.TbPlantServiceServerTimeDao;
import com.jinpinghu.db.dao.TbResPlantServiceFileDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantService.param.AddOrUpdatePlantServiceParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdatePlantServiceLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddOrUpdatePlantServiceParam myParam = (AddOrUpdatePlantServiceParam)logicParam;
		String name = myParam.getName();
		String orderDescribe = myParam.getOrderDescribe();
		String content = myParam.getContent();
		String price = myParam.getPrice();
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		String serverType = myParam.getServerType();
		Date startTime = StringUtils.isEmpty(myParam.getStartTime()) ? null : DateTimeUtil.formatTime(myParam.getStartTime());
		Date endTime = StringUtils.isEmpty(myParam.getEndTime()) ? null : DateTimeUtil.formatTime(myParam.getEndTime());
		String json = myParam.getJson();
		
		
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbFileDao tbFileDao = new TbFileDao(em);
		TbResPlantServiceFileDao tbResPlantServiceFileDao = new TbResPlantServiceFileDao(em);
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		if(enterprise == null) {
			res.add("status", "-1").add("msg", "请关联企业");
			return false;
		}
		
		TbPlantServiceDao plantServiceDao = new TbPlantServiceDao(em);
		TbPlantService plantService = plantServiceDao.findById(id);
		if(plantService == null) {
			plantService = new TbPlantService(enterprise.getId(), name, orderDescribe, content, price, 0);
			plantService.setServerType(serverType);
			plantService.setStartTime(startTime);
			plantService.setEndTime(endTime);
			plantService.setStatus("0");
			plantServiceDao.save(plantService);
		}else {
			plantService.setName(name);
			plantService.setOrderDescribe(orderDescribe);
			plantService.setContent(content);
			plantService.setPrice(price);
			plantService.setServerType(serverType);
			plantService.setStartTime(startTime);
			plantService.setEndTime(endTime);
			plantServiceDao.update(plantService);
		}
		
		tbResPlantServiceFileDao.DelFileByPlantServiceId(plantService.getId());
		TbFile tbFile;
		TbResPlantServiceFile resPlantServiceFile;
		if(!StringUtil.isNullOrEmpty(json)){
			JSONArray ja=JSONArray.fromObject(json);
			if(ja!=null){
				JSONObject jo=null;
				for(int i=0;i<ja.size();i++){
					jo=ja.getJSONObject(i);
					tbFile=new TbFile();
					resPlantServiceFile=new TbResPlantServiceFile();
					
					if(jo.containsKey("fileSize"))
						tbFile.setFileSize(jo.getString("fileSize"));
					if(jo.containsKey("fileName"))
						tbFile.setFileName(jo.getString("fileName"));
					tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
					tbFile.setFileUrl(jo.getString("fileUrl"));
					tbFileDao.save(tbFile);
					resPlantServiceFile.setPlantServiceId(plantService.getId());
					resPlantServiceFile.setFileId(tbFile.getId());
					tbResPlantServiceFileDao.save(resPlantServiceFile);
				}
			}
		}
		
		String serverTime = myParam.getServerTime();
		TbPlantServiceServerTimeDao serverTimeDao = new TbPlantServiceServerTimeDao(em);
		serverTimeDao.DelServerTimeByPlantServiceId(plantService.getId());
		if(!StringUtil.isNullOrEmpty(serverTime)){
			JSONArray ja=JSONArray.fromObject(serverTime);
			if(ja!=null){
				JSONObject jo=null;
				for(int i=0;i<ja.size();i++){
					jo=ja.getJSONObject(i);

					TbPlantServiceServerTime serviceServerTime = new TbPlantServiceServerTime();
					serviceServerTime.setServiceId(plantService.getId());
					serviceServerTime.setServerTime(DateTimeUtil.formatTime(jo.getString("time")));
					serverTimeDao.save(serviceServerTime);
				}
			}
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
