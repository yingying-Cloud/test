package com.jinpinghu.logic.inspection;

import java.util.Date;

import javax.persistence.EntityManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbInspection;
import com.jinpinghu.db.dao.TbInspectionComplete;
import com.jinpinghu.db.dao.TbInspectionCompleteDao;
import com.jinpinghu.db.dao.TbInspectionDao;
import com.jinpinghu.db.dao.TbResInspectionCompleteFile;
import com.jinpinghu.db.dao.TbResInspectionCompleteFileDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddCompleteLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		String remark = param.getRemark();
		Integer inspectionId = param.getInspectionId();
		Integer status = param.getStatus();
		Integer type = param.getType();
		String file = param.getFile();
		Integer inspectionStatus = null;

		/*//判断用户身份
		TbResUserRoleDao resRoleDao = new TbResUserRoleDao(em);
		String roleId = resRoleDao.findRoleIdByUserId(param.getUserId()).toString();
		
		if(roleId.equals("5") || roleId.equals("14")){//农资店
			
		}else if(roleId.equals("11") || roleId.equals("12")){//农业农村局 || 农资监管员
			//更新主表 status
			inspectionStatus = status;
			TbInspectionDao insDao = new TbInspectionDao(em);
			TbInspection inspection = insDao.findById(inspectionId);
			inspection.setStatus(inspectionStatus);
			insDao.update(inspection);
		}*/
		if(type.equals(1)){//复核
			//更新主表 status
			inspectionStatus = status;
			TbInspectionDao insDao = new TbInspectionDao(em);
			TbInspection inspection = insDao.findById(inspectionId);
			inspection.setStatus(inspectionStatus);
			insDao.update(inspection);
		}
		
		TbInspectionComplete entity = new TbInspectionComplete(type, inspectionId, status, remark, new Date(), 0);
		TbInspectionCompleteDao dao = new TbInspectionCompleteDao(em);
		dao.save(entity);

		TbFile tf = null;
		TbFileDao tfDao = new TbFileDao(em);
		TbResInspectionCompleteFile trf = null; 
		TbResInspectionCompleteFileDao trfDao = new TbResInspectionCompleteFileDao(em);
		
		JSONArray ja = JSONArray.parseArray(file);
		for(int i = 0; i < ja.size(); i++){
			JSONObject jo = ja.getJSONObject(i);
			tf = new TbFile();
			if(jo.containsKey("fileName"))
				tf.setFileName(jo.getString("fileName"));
			if(jo.containsKey("fileSize"))
				tf.setFileSize(jo.getString("fileSize"));
			if(jo.containsKey("fileType"))
				tf.setFileType(jo.getInteger("fileType"));
			if(jo.containsKey("fileUrl"))
				tf.setFileUrl(jo.getString("fileUrl"));
			tfDao.save(tf);
			
			trf = new TbResInspectionCompleteFile(entity.getId(), tf.getId());
			trfDao.save(trf);
		}
		
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}