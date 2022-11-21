package com.jinpinghu.logic.farmingWater;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResCropFarmingWater;
import com.jinpinghu.db.bean.TbResCropFarmingWaterFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingWaterDao;
import com.jinpinghu.db.dao.TbResCropFarmingWaterFileDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.farmingWater.param.AddOrUpdateFarmingWaterParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class AddFarmingWaterLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateFarmingWaterParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateFarmingWaterParam myParam=(AddOrUpdateFarmingWaterParam)AddOrUpdateFarmingWaterParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer workId=StringUtil.isNullOrEmpty(myParam.getWorkId())?null:Integer.valueOf(myParam.getWorkId());
		String traffic=myParam.getTraffic();
		String startIrrigationTime=myParam.getStartIrrigationTime();
		String waterAmount=myParam.getWaterAmount();
		String endIrrigationTime=myParam.getEndIrrigationTime();
		String describe=myParam.getDescribe();
		String file = myParam.getFile();
		
		TbResCropFarmingWater tbResCropFarmingWater=null;
		TbResCropFarmingWaterDao tbResCropFarmingWaterDao=new TbResCropFarmingWaterDao(em);
		TbFileDao tbFileDao = new TbFileDao(em);
		TbUserDao tbUserDao=new TbUserDao(em);
		TbResCropFarmingWaterFileDao tbResCropFarmingWaterFileDao = new TbResCropFarmingWaterFileDao(em);
		String addPeople= tbUserDao.checkLogin(myParam.getUserId(), myParam.getApiKey()).getName();
		if(id!=null) {
			tbResCropFarmingWater = tbResCropFarmingWaterDao.findById(id);
		}
		if(tbResCropFarmingWater==null) {
			tbResCropFarmingWater=new TbResCropFarmingWater();
			tbResCropFarmingWater.setAddPeople(addPeople);
			tbResCropFarmingWater.setEnterpriseId(Integer.valueOf(enterpriseId));
			tbResCropFarmingWater.setInputTime(new Date());
			tbResCropFarmingWater.setWorkId(workId);
			tbResCropFarmingWater.setTraffic(traffic);
			tbResCropFarmingWater.setStartIrrigationTime(DateTimeUtil.formatTime2(startIrrigationTime));
			tbResCropFarmingWater.setEndIrrigationTime(DateTimeUtil.formatTime2(endIrrigationTime));
			tbResCropFarmingWater.setWaterAmount(waterAmount);
			tbResCropFarmingWater.setDescribe(describe);
			tbResCropFarmingWater.setDelFlag(0);
			tbResCropFarmingWaterDao.save(tbResCropFarmingWater);
		}else {
			tbResCropFarmingWater.setAddPeople(addPeople);
			tbResCropFarmingWater.setEnterpriseId(Integer.valueOf(enterpriseId));
			tbResCropFarmingWater.setWorkId(workId);
			tbResCropFarmingWater.setTraffic(traffic);
			tbResCropFarmingWater.setStartIrrigationTime(DateTimeUtil.formatTime2(startIrrigationTime));
			tbResCropFarmingWater.setEndIrrigationTime(DateTimeUtil.formatTime2(endIrrigationTime));
			tbResCropFarmingWater.setWaterAmount(waterAmount);
			tbResCropFarmingWater.setDescribe(describe);
			tbResCropFarmingWater.setDelFlag(0);
			tbResCropFarmingWaterDao.update(tbResCropFarmingWater);
		}
		
		
		List<TbFile> tfs =tbFileDao.findByResCropFarmingWaterId(tbResCropFarmingWater.getId());
		List<TbResCropFarmingWaterFile> trgfs =tbResCropFarmingWaterFileDao.findByResCropFarmingWaterId(tbResCropFarmingWater.getId());
		if(trgfs!=null){
			for(TbResCropFarmingWaterFile trgf:trgfs){
				tbResCropFarmingWaterFileDao.delete(trgf);
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
				for(int j=0;j<arrayF.size();j++){
					TbFile tfe =null;
					JSONObject jsonObj=(JSONObject) arrayF.get(j);
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
					TbResCropFarmingWaterFile trpf=new TbResCropFarmingWaterFile();
					trpf.setFileId(tfe.getId());
					trpf.setResCropFarmingWaterId(tbResCropFarmingWater.getId());
					trpf.setDelFlag(0);
					tbResCropFarmingWaterFileDao.save(trpf);
				}
			}
		}
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}