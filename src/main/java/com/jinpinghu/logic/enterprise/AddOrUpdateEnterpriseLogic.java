package com.jinpinghu.logic.enterprise;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResEnterpriseBrand;
import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.bean.TbResUserEnterprise;
import com.jinpinghu.db.bean.TbResUserRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResEnterpriseBrandDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.AddOrUpdateEnterpriseParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.common.util.EncryptTool;
import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateEnterpriseLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateEnterpriseParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateEnterpriseParam myParam = (AddOrUpdateEnterpriseParam)AddOrUpdateEnterpriseParam;
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
		String userId = myParam.getUserId();
		String brand = myParam.getBrand();
		String type = myParam.getType();
		String businessScope = myParam.getBusinessScope();
		String permitForoperationNum = myParam.getPermitForoperationNum();
		String operationMode =myParam.getOperationMode();
		
		TbUser user = null;
		
		TbUserDao tuDao = new TbUserDao(em);
		
//		TbUser user = tuDao.checkLogin2(userId);
		TbUser checkUser = null;
		
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbEnterprise enterprise =null;
		TbFileDao tfDao = new TbFileDao(em);
		TbResToolFileDao trfgDao =new TbResToolFileDao(em);
		TbResUserRoleDao tbResUserRoleDao = new TbResUserRoleDao(em);
		TbResUserEnterpriseDao tbResUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		if(StringUtil.isNullOrEmpty(id)) {
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
			enterprise.setStatus(1);
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
			enterprise.setType(type);
			enterprise.setState(1);
			enterprise.setBusinessScope(businessScope);
			enterprise.setPermitForoperationNum(permitForoperationNum);
			enterprise.setOperationMode(operationMode);
			enterpriseDao.save(enterprise);
		
			
			checkUser = tuDao.findByPhone(enterpriseLinkMobile);
			user = tuDao.findByPhone(enterpriseLinkMobile);
			//如果联系人不存在，新建
			if(checkUser==null){
				String tbUserId = ZDao.genPkId();
				String password=EncryptTool.md5("123456");
				String apiKey = _blow.encryptString(enterpriseLinkPeople + password+ System.currentTimeMillis()).substring(0, 100);
				TbUser putUser = new TbUser(null,tbUserId, apiKey, enterpriseLinkMobile, password, new Date());
				putUser.setDelFlag(0);
				putUser.setName(enterpriseLinkPeople);
				putUser.setDscd(dscd);
				tuDao.save(putUser);
				
				TbResUserEnterprise tbResUserEnterprice = new TbResUserEnterprise();
				tbResUserEnterprice.setDelFlag(0);
				tbResUserEnterprice.setEnterpriseId(enterprise.getId());
				tbResUserEnterprice.setUserTabId(putUser.getId());
				tbResUserEnterpriseDao.save(tbResUserEnterprice);
				
				TbResUserRole tbResUserRole = new TbResUserRole();
				tbResUserRole.setDelFlag(0);
				tbResUserRole.setInputTime(new Date());
				tbResUserRole.setUserTabId(putUser.getId());
				if(enterpriseType==1) {
					tbResUserRole.setRoleId(3);
				}else if(enterpriseType==3) {
					tbResUserRole.setRoleId(5);
				}else if(enterpriseType==2) {
					tbResUserRole.setRoleId(4);
				}else if(enterpriseType==4) {
					tbResUserRole.setRoleId(10);
				}else if(enterpriseType==5) {
					tbResUserRole.setRoleId(7);
				}else if(enterpriseType==6) {
					tbResUserRole.setRoleId(13);
				}
				tbResUserRoleDao.save(tbResUserRole);
			}
		}else{
			
			enterprise = enterpriseDao.findById(Integer.valueOf(id));
			if(enterprise!=null) {
				
				checkUser = tuDao.findByPhone(enterprise.getEnterpriseLinkMobile());//当前手机号
				TbUser putUser = tuDao.findByPhone(enterpriseLinkMobile);//修改手机号
				user = tuDao.findByPhone(enterpriseLinkMobile);
				if (checkUser != null) {
					checkUser.setDscd(dscd);
				}
				if(checkUser!=null&&putUser==null) { // 
					checkUser.setMobile(enterpriseLinkMobile);
					checkUser.setName(enterpriseLinkPeople);
					tuDao.update(checkUser);
					enterprise.setEnterpriseLinkMobile(enterpriseLinkMobile);
				}else if(checkUser==null&&putUser==null) {
					String tbUserId = ZDao.genPkId();
					String password=EncryptTool.md5("123456");
					String apiKey = _blow.encryptString(enterpriseLinkPeople + password+ System.currentTimeMillis()).substring(0, 100);
					putUser = new TbUser(null,tbUserId, apiKey, enterpriseLinkMobile, password, new Date());
					putUser.setDelFlag(0);
					putUser.setName(enterpriseLinkPeople);
					putUser.setDscd(dscd);
					tuDao.save(putUser);
					
					TbResUserEnterprise tbResUserEnterprice = new TbResUserEnterprise();
					tbResUserEnterprice.setDelFlag(0);
					tbResUserEnterprice.setEnterpriseId(enterprise.getId());
					tbResUserEnterprice.setUserTabId(putUser.getId());
					tbResUserEnterpriseDao.save(tbResUserEnterprice);
					
					TbResUserRole tbResUserRole = new TbResUserRole();
					tbResUserRole.setDelFlag(0);
					tbResUserRole.setInputTime(new Date());
					tbResUserRole.setUserTabId(putUser.getId());
					if(enterpriseType==1) {
						tbResUserRole.setRoleId(3);
					}else if(enterpriseType==3) {
						tbResUserRole.setRoleId(5);
					}else if(enterpriseType==2) {
						tbResUserRole.setRoleId(4);
					}else if(enterpriseType==4) {
						tbResUserRole.setRoleId(10);
					}else if(enterpriseType==5) {
						tbResUserRole.setRoleId(7);
					}else if(enterpriseType==6) {
						tbResUserRole.setRoleId(13);
					}
					tbResUserRoleDao.save(tbResUserRole);
					enterprise.setEnterpriseLinkMobile(enterpriseLinkMobile);
				}else if(checkUser!=null&&putUser!=null&&!checkUser.getMobile().equals(putUser.getMobile())) { // 
//						checkUser.setMobile(enterpriseLinkMobile);
//						checkUser.setName(enterpriseLinkPeople);
//						tuDao.update(checkUser);
//						enterprise.setEnterpriseLinkMobile(enterpriseLinkMobile);
					res.add("msg","该手机号已存在！");
					res.add("status", 2);
					return false;
				}
				
				enterprise.setEnterpriseAddress(enterpriseAddress);
				enterprise.setEnterpriseCreditCode(enterpriseCreditCode);
				enterprise.setEnterpriseLegalPerson(enterpriseLegalPerson);
				enterprise.setEnterpriseLegalPersonIdcard(enterpriseLegalPersonIdcard);
				enterprise.setEnterpriseLinkPeople(enterpriseLinkPeople);
				enterprise.setInputTime(new Date());
				enterprise.setName(name);
				enterprise.setDscd(dscd);
				enterprise.setEnterpriseType(enterpriseType);
				enterprise.setRegisteredFunds(registeredFunds);
				enterprise.setChanges(changes);
				enterprise.setEnterpriseNature(enterpriseNature);
				enterprise.setType(type);
				enterprise.setBusinessScope(businessScope);
				enterprise.setPermitForoperationNum(permitForoperationNum);
				enterprise.setOperationMode(operationMode);
				enterpriseDao.update(enterprise);

			}
		}
		
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
		if(user!=null&&checkUser!=null&&!user.getMobile().equals(checkUser.getMobile())) {
			res.add("msg","该手机号已存在！");
		}else {
			res.add("msg","操作成功");
		}
		res.add("status", 1).add("id", enterprise.getId()).add("enterpriseName", enterprise.getName());
		return true;
	}
}
