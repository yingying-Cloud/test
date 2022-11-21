package com.jinpinghu.logic.advertisement;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbAdvertisement;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResAdvertisementEnterprise;
import com.jinpinghu.db.bean.TbResAdvertisementFile;
import com.jinpinghu.db.dao.TbAdvertisementDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResAdvertisementEnterpriseDao;
import com.jinpinghu.db.dao.TbResAdvertisementFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.advertisement.param.AddOrUpdateAdvertisementParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateAdvertisementLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddOrUpdateAdvertisementParam myParam = (AddOrUpdateAdvertisementParam)logicParam;
		String id= myParam.getId();
		String title= myParam.getTitle();
		String file = myParam.getFileUrls();
		Date startTime = StringUtils.isNullOrEmpty(myParam.getStartTime())?null:DateTimeUtil.formatTime(myParam.getStartTime());
		Date endTime = StringUtils.isNullOrEmpty(myParam.getEndTime())?null:DateTimeUtil.formatTime(myParam.getEndTime());
		Integer type = StringUtils.isNullOrEmpty(myParam.getType())?0:Integer.valueOf(myParam.getType());
		String visible =(myParam.getVisible());
		String enterpriseJson = myParam.getEnterpriseJson();
		TbAdvertisementDao taDao = new TbAdvertisementDao(em);
		TbFileDao tbFileDao = new TbFileDao(em);
		TbResAdvertisementFileDao tbResFileAdvertisementDao = new TbResAdvertisementFileDao(em);
		TbResAdvertisementEnterpriseDao tbResAdvertisementEnterpriseDao = new TbResAdvertisementEnterpriseDao(em);
		
		TbAdvertisement ta = null;
		if(!StringUtils.isNullOrEmpty(id)){
			ta=taDao.findById(Integer.valueOf(id));
		}
		if(ta!=null){
			ta.setTitle(title);
			ta.setVisible(visible);
			ta.setType(type);
			ta.setStartTime(startTime);
			ta.setEndTime(endTime);
			taDao.update(ta);
			res.add("msg", "更新成功！");
			res.add("status", 1);
		}else{
			ta = new TbAdvertisement();
			ta.setTitle(title);
			ta.setVisible(visible);
			ta.setType(type);
			ta.setStartTime(startTime);
			ta.setEndTime(endTime);
			ta.setDelFlag(0);
			taDao.save(ta);
			res.add("msg", "保存成功！");
			res.add("status", 1);
		}
		
		List<TbResAdvertisementEnterprise> traes = tbResAdvertisementEnterpriseDao.findByAdvertisementId(ta.getId());
		if(traes!=null){
			for(TbResAdvertisementEnterprise trgf:traes){
				tbResAdvertisementEnterpriseDao.delete(trgf);
			}
		}
		if(!StringUtils.isNullOrEmpty(enterpriseJson)){
			JSONArray arrayF= JSONArray.fromObject(enterpriseJson);
			if(arrayF.size()>0){
				for(int i=0;i<arrayF.size();i++){
					JSONObject jsonObj=(JSONObject) arrayF.get(i);
					TbResAdvertisementEnterprise trpf=new TbResAdvertisementEnterprise();
					trpf.setEnterpriseId(jsonObj.getInt("enterpriseId"));
					trpf.setAdvertisementId(ta.getId());
					tbResFileAdvertisementDao.save(trpf);
				}
			}
		}
		
		
		List<TbFile> tfs =tbFileDao.findByAdvertisementId(ta.getId());
		List<TbResAdvertisementFile> trgfs =tbResFileAdvertisementDao.findByAdvertisementId(ta.getId());
		if(trgfs!=null){
			for(TbResAdvertisementFile trgf:trgfs){
				tbResFileAdvertisementDao.delete(trgf);
			}
		}
		if(tfs!=null){
			for(TbFile tbFile:tfs){
				tbFileDao.delete(tbFile);
			}
		}
		if(!StringUtils.isNullOrEmpty(file)){
			JSONArray arrayF= JSONArray.fromObject(file);
			if(arrayF.size()>0){
				for(int i=0;i<arrayF.size();i++){
					TbFile tfe =null;
					JSONObject jsonObj=(JSONObject) arrayF.get(i);
					tfe = new TbFile();
					if(jsonObj.containsKey("fileName"))
						tfe.setFileName(jsonObj.getString("fileName"));
					if(jsonObj.containsKey("fileSize"))
						tfe.setFileSize(jsonObj.getString("fileSize"));
					if(jsonObj.containsKey("fileType"))
						tfe.setFileType(jsonObj.getInt("fileType"));
					if(jsonObj.containsKey("fileUrl"))
						tfe.setFileUrl(jsonObj.getString("fileUrl"));
					tbFileDao.save(tfe);
					TbResAdvertisementFile trpf=new TbResAdvertisementFile();
					trpf.setFileId(tfe.getId());
					trpf.setAdvertisementId(ta.getId());
					tbResFileAdvertisementDao.save(trpf);
				}
			}
		}
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}
}
