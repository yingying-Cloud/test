package com.jinpinghu.logic.enterprise;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResEnterpriseBrand;
import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.bean.TbResUserEnterprise;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResEnterpriseBrandDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.AddEnterpriseParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddEnterpriseLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddEnterpriseParam myParam = (AddEnterpriseParam)logicParam;
		String id = myParam.getId();
		String name=myParam.getName();
		String enterpriseCreditCode=myParam.getEnterpriseCreditCode();
		String enterpriseLegalPerson=myParam.getEnterpriseLegalPerson();
		String enterpriseLegalPersonIdcard=myParam.getEnterpriseLegalPersonIdcard();
		String enterpriseLinkPeople=myParam.getEnterpriseLinkPeople();
		String enterpriseLinkMobile=myParam.getEnterpriseLinkMobile();
		String enterpriseAddress=myParam.getEnterpriseAddress();
		Integer enterpriseType = StringUtils.isNullOrEmpty(myParam.getEnterpriseType())?null:Integer.valueOf(myParam.getEnterpriseType());
		String file = myParam.getFile();
		String x = myParam.getX();
		String y = myParam.getY();
		String plantScope = myParam.getPlantScope();
		String baseAddress = myParam.getBaseAddress();
		String plantName = myParam.getPlantName();
		String dscd = myParam.getDscd();
		String enterpriseNature = myParam.getEnterpriseNature();
		String changes = myParam.getChanges();
		String registeredFunds = myParam.getRegisteredFunds();
		String brand = myParam.getBrand();
		String type2 = myParam.getType();
		String businessScope = myParam.getBusinessScope();
		String permitForoperationNum = myParam.getPermitForoperationNum();
		String operationMode =myParam.getOperationMode();

		String userId = myParam.getUserId();
		TbUserDao tuDao = new TbUserDao(em);
		TbUser user = tuDao.checkLogin2(userId);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbEnterprise enterprise =null;
		/*��֤�Ƿ���ڸù�˾*/
		if(!StringUtils.isNullOrEmpty(id)) {
			enterprise=enterpriseDao.findById(Integer.valueOf(id));
		}
			
		if(enterprise==null) {
			enterprise= enterpriseDao.findByCode(enterpriseCreditCode);
			if(enterprise==null) {
				enterprise = new TbEnterprise();
				enterprise.setDelFlag(0);
				enterprise.setEnterpriseAddress(enterpriseAddress);
				enterprise.setEnterpriseCreditCode(enterpriseCreditCode);
				enterprise.setEnterpriseLegalPerson(enterpriseLegalPerson);
				enterprise.setEnterpriseLegalPersonIdcard(enterpriseLegalPersonIdcard);
				enterprise.setEnterpriseLinkMobile(enterpriseLinkMobile);
				enterprise.setEnterpriseLinkPeople(enterpriseLinkPeople);
				enterprise.setEnterpriseType(enterpriseType);
				enterprise.setInputTime(new Date());
				enterprise.setName(name);
				enterprise.setStatus(0);
				enterprise.setX(x);
				enterprise.setY(y);
				enterprise.setPlantScope(plantScope);
				enterprise.setPlantName(plantName);
				enterprise.setBaseAddress(baseAddress);
				enterprise.setDscd(dscd);
				enterprise.setEnterpriseNature(enterpriseNature);
				enterprise.setRegisteredFunds(registeredFunds);
				enterprise.setChanges(changes);
				enterprise.setListOrder(0);
				enterprise.setType(type2);
				enterprise.setState(1);
				enterprise.setBusinessScope(businessScope);
				enterprise.setPermitForoperationNum(permitForoperationNum);
				enterprise.setOperationMode(operationMode);
				enterpriseDao.save(enterprise);
			}
		}else {
			enterprise.setEnterpriseAddress(enterpriseAddress);
			enterprise.setEnterpriseLegalPerson(enterpriseLegalPerson);
			enterprise.setEnterpriseCreditCode(enterpriseCreditCode);
			enterprise.setEnterpriseLegalPersonIdcard(enterpriseLegalPersonIdcard);
			enterprise.setEnterpriseLinkMobile(enterpriseLinkMobile);
			enterprise.setEnterpriseLinkPeople(enterpriseLinkPeople);
			enterprise.setInputTime(new Date());
			enterprise.setName(name);
			enterprise.setEnterpriseType(enterpriseType);
			enterprise.setX(x);
			enterprise.setY(y);
			enterprise.setPlantScope(plantScope);
			enterprise.setPlantName(plantName);
			enterprise.setBaseAddress(baseAddress);
			enterprise.setDscd(dscd);
			enterprise.setEnterpriseNature(enterpriseNature);
			enterprise.setRegisteredFunds(registeredFunds);
			enterprise.setChanges(changes);
			enterprise.setType(type2);
			enterprise.setBusinessScope(businessScope);
			enterprise.setPermitForoperationNum(permitForoperationNum);
			enterprise.setOperationMode(operationMode);
			enterpriseDao.update(enterprise);
		}
		TbResUserEnterpriseDao tbResUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		/*��֤�Ƿ���ڸù���*/
		TbResUserEnterprise checkIsExist = tbResUserEnterpriseDao.checkIsExist(user.getId(), enterprise.getId());
		if(checkIsExist==null) {
			TbResUserEnterprise tbResUserEnterprice = new TbResUserEnterprise();
			tbResUserEnterprice.setDelFlag(0);
			tbResUserEnterprice.setEnterpriseId(enterprise.getId());
			tbResUserEnterprice.setUserTabId(user.getId());
			tbResUserEnterpriseDao.save(tbResUserEnterprice);
		}
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResToolFileDao trfgDao =new TbResToolFileDao(em);
		
		
//		if(!StringUtils.isNullOrEmpty(file)){
			
//			List<Integer> type = new ArrayList<Integer>();
//			JSONArray arrayF= JSONArray.fromObject(file);
//			if(arrayF.size()>0){
//				for(int i=0;i<arrayF.size();i++){
//					JSONObject jsonObj=(JSONObject) arrayF.get(i);
//					type.add(jsonObj.getInt("type"));
//				}
//			}
//			if(type!=null&&type.size()>0) {
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
//			}
//		}
		
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
					if(jsonObj.containsKey("type")) {
						trpf.setType(jsonObj.getInt("type"));
					}
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
//					if(jsonObj.containsKey("trademarkId"))
					trtb.setEnterpriseId(enterprise.getId());
					if(jsonObj.containsKey("yield"))
					trtb.setYield(jsonObj.getString("yield"));
					trtb.setDelFlag(0);
					trfgDao.save(trtb);
				}
			}
		}
		
		
		res.add("status", 1).add("msg","操作成功").add("id", enterprise.getId()).add("enterpriseName", enterprise.getName());
		return true;
	}


	/*@Override
	public boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
							EntityManager em)  throws Exception {

		return true;
	}*/
}
