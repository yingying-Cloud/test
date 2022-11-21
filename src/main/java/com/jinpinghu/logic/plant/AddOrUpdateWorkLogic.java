package com.jinpinghu.logic.plant;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbWork;
import com.jinpinghu.db.bean.TbWorkChild;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.db.dao.TbWorkChildDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.AddOrUpdateWorkParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateWorkLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateWorkParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateWorkParam myParam=(AddOrUpdateWorkParam)AddOrUpdateWorkParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String workName=myParam.getWorkName();
		String landBlockSn=myParam.getLandBlockSn();
		String area=myParam.getArea();
		String crop=myParam.getCrop();
		Date startTime=DateTimeUtil.formatTime(myParam.getStartTime());
		Date endTime=DateTimeUtil.formatTime(myParam.getEndTime());
		Date recoveryTime=DateTimeUtil.formatTime(myParam.getRecoveryTime());
		String expectedProduction=myParam.getExpectedProduction();
		String json=myParam.getJson();
		String purchasePeople = myParam.getPurchasePeople();
		String purchaseSource = myParam.getPurchaseSource();
		Date purchaseTime = StringUtils.isEmpty(myParam.getPurchaseTime())?null:DateTimeUtil.formatTime(myParam.getPurchaseTime());;
		Integer greenhousesId = StringUtil.isNullOrEmpty(myParam.getGreenhousesId())?null:Integer.valueOf(myParam.getGreenhousesId());
		TbWork tbWork=new TbWork();
		TbWorkDao tbWorkDao=new TbWorkDao(em);
		TbWorkChild tbWorkChild=null;
		TbWorkChildDao tbWorkChildDao=new TbWorkChildDao(em);
		TbUserDao tbUserDao=new TbUserDao(em);
		TbBrandDao brandDao = new TbBrandDao(em);

		String addPeople= tbUserDao.checkLogin(myParam.getUserId(), myParam.getApiKey()).getName();
		
		if(id==null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHH");
			TbBrand brand = StringUtils.isEmpty(crop) ? null : (StringUtils.isNumeric(crop) ? brandDao.findById(Integer.valueOf(crop)) : null);
			String workSn=brand == null ? "" : brand.getProductName()+enterpriseId+sdf.format(new Date());
			tbWork.setAddPeople(addPeople);
			tbWork.setArea(area);
			tbWork.setCrop(crop);
			tbWork.setDelFlag(0);
			tbWork.setEndTime(endTime);
			tbWork.setEnterpriseId(enterpriseId);
			tbWork.setExpectedProduction(expectedProduction);
			tbWork.setInputTime(new Date());
			tbWork.setLandBlockSn(landBlockSn);
			tbWork.setRecoveryTime(recoveryTime);
			tbWork.setStartTime(startTime);
			tbWork.setWorkName(workName);
			tbWork.setWorkSn(workSn);
			tbWork.setPurchasePeople(purchasePeople);
			tbWork.setPurchaseSource(purchaseSource);
			tbWork.setPurchaseTime(purchaseTime);
			tbWork.setGreenhousesId(greenhousesId);
			tbWorkDao.save(tbWork);
			
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbWorkChild=new TbWorkChild();
						tbWorkChild.setWorkId(tbWork.getId());
						tbWorkChild.setName(jo.getString("name"));
						tbWorkChild.setStartTime(DateTimeUtil.formatTime(jo.getString("startTime")));
						if(jo.containsKey("endTime"))
							tbWorkChild.setEndTime(DateTimeUtil.formatTime(jo.getString("endTime")));
						tbWorkChildDao.save(tbWorkChild);
					}
				}
			}
			
		}else{
			tbWork=tbWorkDao.findById(id);
			tbWork.setAddPeople(addPeople);
			tbWork.setArea(area);
			tbWork.setCrop(crop);
			tbWork.setEndTime(endTime);
			tbWork.setEnterpriseId(enterpriseId);
			tbWork.setExpectedProduction(expectedProduction);
			tbWork.setLandBlockSn(landBlockSn);
			tbWork.setRecoveryTime(recoveryTime);
			tbWork.setStartTime(startTime);
			tbWork.setWorkName(workName);
			tbWork.setPurchasePeople(purchasePeople);
			tbWork.setPurchaseSource(purchaseSource);
			tbWork.setPurchaseTime(purchaseTime);
			tbWork.setGreenhousesId(greenhousesId);
			tbWorkDao.update(tbWork);

			tbWorkChildDao.delById(id);
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbWorkChild=new TbWorkChild();
						tbWorkChild.setWorkId(tbWork.getId());
						tbWorkChild.setName(jo.getString("name"));
						tbWorkChild.setStartTime(DateTimeUtil.formatTime(jo.getString("startTime")));
						if(jo.containsKey("endTime"))
							tbWorkChild.setEndTime(DateTimeUtil.formatTime(jo.getString("endTime")));
						tbWorkChildDao.save(tbWorkChild);
						
					}
				}
			}
		}
		
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
