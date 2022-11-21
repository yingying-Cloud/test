package com.jinpinghu.logic.enterprise;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResEnterpriseBrand;
import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResEnterpriseBrandDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.UpdateEnterpriseParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateEnterpriseLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		UpdateEnterpriseParam myParam = (UpdateEnterpriseParam)logicParam;
		Integer id = myParam.getId();
		String name=myParam.getName();
		String enterpriseCreditCode=myParam.getEnterpriseCreditCode();
		String enterpriseLegalPerson=myParam.getEnterpriseLegalPerson();
		String enterpriseLegalPersonIdcard=myParam.getEnterpriseLegalPersonIdcard();
		String enterpriseLinkPeople=myParam.getEnterpriseLinkPeople();
		String enterpriseLinkMobile=myParam.getEnterpriseLinkMobile();
		String enterpriseAddress=myParam.getEnterpriseAddress();
		Integer enterpriseType = StringUtils.isNullOrEmpty(myParam.getEnterpriseType())?null:Integer.valueOf(myParam.getEnterpriseType());
		String file = myParam.getFile();
		String dscd = myParam.getDscd();
		String enterpriseNature = myParam.getEnterpriseNature();
		String changes = myParam.getChanges();
		String brand = myParam.getBrand();
		String registeredFunds = myParam.getRegisteredFunds();
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		/*��֤�Ƿ���ڸù�˾*/
		TbEnterprise enterprise = enterpriseDao.findById(id);
		if(enterprise!=null) {
			enterprise.setEnterpriseAddress(enterpriseAddress);
			enterprise.setEnterpriseCreditCode(enterpriseCreditCode);
			enterprise.setEnterpriseLegalPerson(enterpriseLegalPerson);
			enterprise.setEnterpriseLegalPersonIdcard(enterpriseLegalPersonIdcard);
			enterprise.setEnterpriseLinkMobile(enterpriseLinkMobile);
			enterprise.setEnterpriseLinkPeople(enterpriseLinkPeople);
			enterprise.setInputTime(new Date());
			enterprise.setName(name);
			enterprise.setDscd(dscd);
			enterprise.setEnterpriseType(enterpriseType);
			enterprise.setEnterpriseNature(enterpriseNature);
			enterprise.setRegisteredFunds(registeredFunds);
			enterprise.setChanges(changes);
			enterpriseDao.update(enterprise);
			
			TbFileDao tfDao = new TbFileDao(em);
			TbResToolFileDao trfgDao =new TbResToolFileDao(em);
			
			List<TbFile> tfs =tfDao.findByEnterpriseIdType(enterprise.getId());
			List<TbResEnterpriseFile> trgfs =trfgDao.findByEnterpriseIdType(enterprise.getId());
			if(trgfs!=null){
				for(TbResEnterpriseFile trgf:trgfs){
					trfgDao.delete(trgf);
				}
			}
			if(tfs!=null){
				for(TbFile tbFile:tfs){
					tfDao.delete(tbFile);
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
						tfDao.save(tfe);
						TbResEnterpriseFile trpf=new TbResEnterpriseFile();
						trpf.setFileId(tfe.getId());
						trpf.setEnterpriseId(enterprise.getId());
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
			
			TbResEnterpriseBrandDao trtbsDao =new TbResEnterpriseBrandDao(em);
			List<TbResEnterpriseBrand> trtbs =trtbsDao.findByEnterpriseId(enterprise.getId());
			if(trtbs!=null){
				for(TbResEnterpriseBrand trgf:trtbs){
					trfgDao.delete(trgf);
				}
			}
			if(!StringUtils.isNullOrEmpty(brand)){
				JSONArray arrayF= JSONArray.fromObject(brand);
				if(arrayF.size()>0){
					for(int i=0;i<arrayF.size();i++){
						JSONObject jsonObj=(JSONObject) arrayF.get(i);
						TbResEnterpriseBrand trtb=new TbResEnterpriseBrand();
						if(jsonObj.containsKey("area"))
						trtb.setArea(jsonObj.getString("area"));
						if(jsonObj.containsKey("brandId"))
						trtb.setBrandId(jsonObj.getInt("brandId"));
//						if(jsonObj.containsKey("trademarkId"))
						trtb.setEnterpriseId(enterprise.getId());
						if(jsonObj.containsKey("yield"))
						trtb.setYield(jsonObj.getString("yield"));
						trtb.setDelFlag(0);
						trfgDao.save(trtb);
					}
				}
			}
			
		}
		
		res.add("status", 1).add("msg","操作成功");
		return true;
	}
}
