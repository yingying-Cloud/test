package com.jinpinghu.logic.plantProtect;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPlantProtection;
import com.jinpinghu.db.bean.TbPlantProtectionServerTime;
import com.jinpinghu.db.bean.TbResPlantProtectionFile;
import com.jinpinghu.db.bean.TbResPlantProtectionMechine;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantProtectionDao;
import com.jinpinghu.db.dao.TbPlantProtectionServerTimeDao;
import com.jinpinghu.db.dao.TbResPlantProtectionFileDao;
import com.jinpinghu.db.dao.TbResPlantProtectionMechineDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantProtect.param.AddOrUpdatePlantProtectParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdatePlantProtectLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddOrUpdatePlantProtectParam myParam = (AddOrUpdatePlantProtectParam)logicParam;
		String name = myParam.getName();
		String orderDescribe = myParam.getOrderDescribe();
		String content = myParam.getContent();
		String price = myParam.getPrice();
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		String machine = myParam.getMachine();
		String serverType = myParam.getServerType();
		Date startTime = StringUtils.isEmpty(myParam.getStartTime()) ? null : DateTimeUtil.formatTime(myParam.getStartTime());
		Date endTime = StringUtils.isEmpty(myParam.getEndTime()) ? null : DateTimeUtil.formatTime(myParam.getEndTime());
		String json = myParam.getJson();
		
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbFileDao tbFileDao = new TbFileDao(em);
		TbResPlantProtectionFileDao tbResPlantProtectionFileDao = new TbResPlantProtectionFileDao(em);
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		if(enterprise == null) {
			res.add("status", "-1").add("msg", "植保企业不存在");
			return false;
		}
		
		TbPlantProtectionDao plantProtectionDao = new TbPlantProtectionDao(em);
		TbPlantProtection plantProtection = plantProtectionDao.findById(id);
		if(plantProtection == null) {
			plantProtection = new TbPlantProtection(enterprise.getId(), name, orderDescribe, content, price, 0);
			plantProtection.setServerType(serverType);
			plantProtection.setStartTime(startTime);
			plantProtection.setEndTime(endTime);
			plantProtection.setStatus("0");
			plantProtectionDao.save(plantProtection);
		}else {
			plantProtection.setName(name);
			plantProtection.setOrderDescribe(orderDescribe);
			plantProtection.setContent(content);
			plantProtection.setPrice(price);
			plantProtection.setServerType(serverType);
			plantProtection.setStartTime(startTime);
			plantProtection.setEndTime(endTime);
			plantProtectionDao.update(plantProtection);
		}
		
		TbResPlantProtectionMechineDao resPlantProtectionMechineDao = new TbResPlantProtectionMechineDao(em);
		resPlantProtectionMechineDao.deleteByPlantProtectionId(plantProtection.getId());
		if (!StringUtils.isEmpty(machine)) {
			String[] mechineArray = machine.split(",");
			for (int i = 0; i < mechineArray.length; i++) {
				TbResPlantProtectionMechine resPlantProtectionMechine = new TbResPlantProtectionMechine();
				resPlantProtectionMechine.setMechineId(Integer.valueOf(mechineArray[i]));
				resPlantProtectionMechine.setPlantProtectionId(plantProtection.getId());
				resPlantProtectionMechineDao.save(resPlantProtectionMechine);
			}
		}
		
		tbResPlantProtectionFileDao.DelFileByPlantProtectionId(plantProtection.getId());
		TbFile tbFile;
		TbResPlantProtectionFile resPlantProtectionFile;
		if(!StringUtil.isNullOrEmpty(json)){
			JSONArray ja=JSONArray.fromObject(json);
			if(ja!=null){
				JSONObject jo=null;
				for(int i=0;i<ja.size();i++){
					jo=ja.getJSONObject(i);
					tbFile=new TbFile();
					resPlantProtectionFile=new TbResPlantProtectionFile();
					
					if(jo.containsKey("fileSize"))
						tbFile.setFileSize(jo.getString("fileSize"));
					if(jo.containsKey("fileName"))
						tbFile.setFileName(jo.getString("fileName"));
					tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
					tbFile.setFileUrl(jo.getString("fileUrl"));
					tbFileDao.save(tbFile);
					resPlantProtectionFile.setPlantProtectionId(plantProtection.getId());
					resPlantProtectionFile.setFileId(tbFile.getId());
					tbResPlantProtectionFileDao.save(resPlantProtectionFile);
				}
			}
		}
		String serverTime = myParam.getServerTime();
		TbPlantProtectionServerTimeDao serverTimeDao = new TbPlantProtectionServerTimeDao(em);
		serverTimeDao.DelServerTimeByPlantProtectionId(plantProtection.getId());
		if(!StringUtil.isNullOrEmpty(serverTime)){
			JSONArray ja=JSONArray.fromObject(serverTime);
			if(ja!=null){
				JSONObject jo=null;
				for(int i=0;i<ja.size();i++){
					jo=ja.getJSONObject(i);

					TbPlantProtectionServerTime protectionServerTime = new TbPlantProtectionServerTime();
					protectionServerTime.setProtectionId(plantProtection.getId());
					protectionServerTime.setServerTime(DateTimeUtil.formatTime(jo.getString("time")));
					serverTimeDao.save(protectionServerTime);
				}
			}
		}
		
		
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
