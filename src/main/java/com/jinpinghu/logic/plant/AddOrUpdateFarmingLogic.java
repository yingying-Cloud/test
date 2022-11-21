package com.jinpinghu.logic.plant;


import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbCropFarming;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResCropFarmingFile;
import com.jinpinghu.db.bean.TbResCropFarmingWork;
import com.jinpinghu.db.dao.TbCropFarmingDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingFileDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.AddOrUpdateFarmingParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateFarmingLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateFarmingParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateFarmingParam myParam=(AddOrUpdateFarmingParam)AddOrUpdateFarmingParam;
		String json=myParam.getJson();
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Date inputTime=StringUtil.isNullOrEmpty(myParam.getInputTime())?new Date():DateTimeUtil.formatTime2(myParam.getInputTime()+" "+DateTimeUtil.formatTime7(new Date()));
		
		TbCropFarming tbCropFarming=new TbCropFarming();
		TbCropFarmingDao tbCropFarmingDao=new TbCropFarmingDao(em);
		TbResCropFarmingFile tbResCropFarmingFile=null;
		TbResCropFarmingFileDao tbResCropFarmingFileDao=new TbResCropFarmingFileDao(em);
		TbFile tbFile=null;
		TbFileDao tbFileDao = new TbFileDao(em);
		TbUserDao tbUserDao=new TbUserDao(em);

		String addPeople= tbUserDao.checkLogin(myParam.getUserId(), myParam.getApiKey()).getName();
		if(id==null){
			tbCropFarming.setInputTime(new Date());
			tbCropFarming.setDelFlag(0);
			tbCropFarming.setAddPeople(addPeople);
			tbCropFarming.setEnterpriseId(Integer.valueOf(myParam.getEnterpriseId()));
			tbCropFarming.setInputTime(inputTime);
			tbCropFarming.setWorkId(myParam.getWorkId());
			tbCropFarming.setDescribe(myParam.getDescribe());
			tbCropFarmingDao.save(tbCropFarming);
			
			TbResCropFarmingWork resCropFarmingWork = new TbResCropFarmingWork();
			resCropFarmingWork.setCropFarmingId(tbCropFarming.getId());
			resCropFarmingWork.setWorkId(myParam.getWorkId());
			tbCropFarmingDao.save(resCropFarmingWork);
			
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResCropFarmingFile=new TbResCropFarmingFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResCropFarmingFile.setCropFarmingId(tbCropFarming.getId());
						tbResCropFarmingFile.setFileId(tbFile.getId());
						tbResCropFarmingFile.setDelFlag(0);
						tbResCropFarmingFileDao.save(tbResCropFarmingFile);
					}
				}
			}
		}else{
			tbCropFarming=tbCropFarmingDao.findById(id);
			tbCropFarming.setAddPeople(addPeople);
			tbCropFarming.setEnterpriseId(Integer.valueOf(myParam.getEnterpriseId()));
			tbCropFarming.setInputTime(inputTime);
			tbCropFarming.setWorkId(myParam.getWorkId());
			tbCropFarming.setDescribe(myParam.getDescribe());
			tbCropFarmingDao.update(tbCropFarming);
			tbResCropFarmingFileDao.DelResByFarmingId(id);
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResCropFarmingFile=new TbResCropFarmingFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResCropFarmingFile.setCropFarmingId(id);
						tbResCropFarmingFile.setFileId(tbFile.getId());
						tbResCropFarmingFile.setDelFlag(0);
						tbResCropFarmingFileDao.save(tbResCropFarmingFile);
					}
				}
			}
		}
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
