package com.jinpinghu.logic.applicantCompany;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbApplicantCompany;
import com.jinpinghu.db.bean.TbApplicantCompanyCheckRecord;
import com.jinpinghu.db.bean.TbApplicantCompanyCredentials;
import com.jinpinghu.db.bean.TbApplicantCompanyProduct;
import com.jinpinghu.db.bean.TbApplicantCompanyTrademark;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResApplicantCompanyFile;
import com.jinpinghu.db.bean.TbResApplicantCompanyTrademarkFile;
import com.jinpinghu.db.dao.TbApplicantCompanyDao;
import com.jinpinghu.db.dao.TbApplicantCompanyTrademarkDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResApplicantCompanyFileDao;
import com.jinpinghu.db.dao.TbResApplicantCompanyTrademarkFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.applicantCompany.param.AddOrUpdateApplicantCompanyParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateApplicantCompanyLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddOrUpdateApplicantCompanyParam myParam = (AddOrUpdateApplicantCompanyParam)logicParam;
		Integer id = myParam.getId();
		Integer enterpriseId = myParam.getEnterpriseId();
		String name = myParam.getName();
		String address = myParam.getAddress();
		String zipCode = myParam.getZipCode();
		String legalPerson = myParam.getLegalPerson();
		String legalContact = myParam.getLegalContact();
		String agent = myParam.getAgent();
		String agentContact = myParam.getAgentContact();
		String fax = myParam.getFax();
		String email = myParam.getEmail();
		String registeredTrademark = myParam.getRegisteredTrademark();
		Date trademarkServiceStartTime = StringUtils.isEmpty(myParam.getTrademarkServiceStartTime()) ?  null : DateTimeUtil.formatSelf(myParam.getTrademarkServiceStartTime(), "yyyy-MM-dd");
		Date trademarkServiceEndTime = StringUtils.isEmpty(myParam.getTrademarkServiceEndTime()) ?  null : DateTimeUtil.formatSelf(myParam.getTrademarkServiceEndTime(), "yyyy-MM-dd");
		String type = myParam.getType();
		String productionSituation = myParam.getProductionSituation();
		String productQualityAttestation = myParam.getProductQualityAttestation();
		String machiningInfo = myParam.getMachiningInfo();
		String productSales = myParam.getProductSales();
		String packageSourceManager = myParam.getPackageSourceManager();
		String otherNt = myParam.getOtherNt();
		String applicantCompanyProducts = myParam.getApplicantCompanyProducts();
		String applicantCompanyCredentials = myParam.getApplicantCompanyCredentials();
		Double x = myParam.getX();
		Double y = myParam.getY();
		String profile = myParam.getProfile();
		String uniformSocialCreditCode = myParam.getUniformSocialCreditCode();
		Date operationPeriodStartTime = StringUtils.isEmpty(myParam.getOperationPeriodStartTime()) ? null : DateTimeUtil.formatTime(myParam.getOperationPeriodStartTime());
		Date operationPeriodEndTime = StringUtils.isEmpty(myParam.getOperationPeriodEndTime()) ? null : DateTimeUtil.formatTime(myParam.getOperationPeriodEndTime());
		String spybName = myParam.getSpybName();
		String spybProduct = myParam.getSpybProduct();
		Date spybStartTime = StringUtils.isEmpty(myParam.getSpybStartTime()) ? null : DateTimeUtil.formatTime(myParam.getSpybStartTime());
		Date spybEndTime = StringUtils.isEmpty(myParam.getSpybEndTime()) ? null : DateTimeUtil.formatTime(myParam.getSpybEndTime());
		String trademarks = myParam.getTrademarks();
		String files = myParam.getFiles();
		String agentMobile = myParam.getAgentMobile();
		String legalMobile = myParam.getLegalMobile();
		
		TbApplicantCompanyDao applicantCompanyDao = new TbApplicantCompanyDao(em);
		TbResApplicantCompanyFileDao resApplicantCompanyFileDao = new TbResApplicantCompanyFileDao(em);
		TbResApplicantCompanyTrademarkFileDao resApplicantCompanyTrademarkFileDao = new TbResApplicantCompanyTrademarkFileDao(em);
		TbFileDao fileDao = new TbFileDao(em);
		TbApplicantCompanyTrademarkDao applicantCompanyTrademarkDao = new TbApplicantCompanyTrademarkDao(em);
		
		TbApplicantCompany applicantCompany = applicantCompanyDao.findById(id);
		
		applicantCompanyDao.delApplicantCompanyCredentials(id);
		applicantCompanyDao.delApplicantCompanyProduct(id);
		applicantCompanyDao.delApplicantCompanyTrademark(id);
		resApplicantCompanyFileDao.delApplicantCompanyFile(id);
		resApplicantCompanyTrademarkFileDao.delApplicantCompanyTrademarkFile(id);
		
		String code = "";
		String today = DateTimeUtil.formatSelf(new Date(), "yyyyMMdd");
		Integer count = applicantCompanyDao.findCountByCode(today);
		count = count + 1;
		String countStr = String.valueOf(count);
		while(countStr.length()<5)
			countStr = "0"+countStr;
		
		code = today+countStr;
		
		if(applicantCompany == null) {
			applicantCompany = new TbApplicantCompany();
			applicantCompany.setCode(code);
			applicantCompany.setEnterpriseId(enterpriseId);
			applicantCompany.setName(name);
			applicantCompany.setAddress(address);
			applicantCompany.setZipCode(zipCode);
			applicantCompany.setLegalPerson(legalPerson);
			applicantCompany.setLegalContact(legalContact);
			applicantCompany.setAgent(agent);
			applicantCompany.setAgentContact(agentContact);
			applicantCompany.setFax(fax);
			applicantCompany.setEmail(email);
			applicantCompany.setRegisteredTrademark(registeredTrademark);
			applicantCompany.setTrademarkServiceStartTime(trademarkServiceStartTime);
			applicantCompany.setTrademarkServiceEndTime(trademarkServiceEndTime);
			applicantCompany.setType(type);
			applicantCompany.setProductionSituation(productionSituation);
			applicantCompany.setProductQualityAttestation(productQualityAttestation);
			applicantCompany.setMachiningInfo(machiningInfo);
			applicantCompany.setProductSales(productSales);
			applicantCompany.setPackageSourceManager(packageSourceManager);
			applicantCompany.setOtherNt(otherNt);
			applicantCompany.setDelFlag(0);
			applicantCompany.setInputTime(new Date());
			applicantCompany.setStatus(0);
			applicantCompany.setX(x);
			applicantCompany.setY(y);
			applicantCompany.setProfile(profile);
			applicantCompany.setUniformSocialCreditCode(uniformSocialCreditCode);
			applicantCompany.setOperationPeriodStartTime(operationPeriodStartTime);
			applicantCompany.setOperationPeriodEndTime(operationPeriodEndTime);
			applicantCompany.setSpybName(spybName);
			applicantCompany.setSpybProduct(spybProduct);
			applicantCompany.setSpybStartTime(spybStartTime);
			applicantCompany.setSpybEndTime(spybEndTime);
			applicantCompany.setAgentMobile(agentMobile);
			applicantCompany.setLegalMobile(legalMobile);
			applicantCompanyDao.save(applicantCompany);
			
			TbApplicantCompanyCheckRecord applicantCompanyCheckRecord = new TbApplicantCompanyCheckRecord();
			applicantCompanyCheckRecord.setApplicantCompanyId(applicantCompany.getId());
			applicantCompanyCheckRecord.setInputTime(new Date());
			applicantCompanyCheckRecord.setStatus(0);
			applicantCompanyDao.save(applicantCompanyCheckRecord);
		}else {
			if(StringUtils.isEmpty(applicantCompany.getCode()))
				applicantCompany.setCode(code);
			applicantCompany.setName(name);
			applicantCompany.setAddress(address);
			applicantCompany.setZipCode(zipCode);
			applicantCompany.setLegalPerson(legalPerson);
			applicantCompany.setLegalContact(legalContact);
			applicantCompany.setAgent(agent);
			applicantCompany.setAgentContact(agentContact);
			applicantCompany.setFax(fax);
			applicantCompany.setEmail(email);
			applicantCompany.setRegisteredTrademark(registeredTrademark);
			applicantCompany.setTrademarkServiceStartTime(trademarkServiceStartTime);
			applicantCompany.setTrademarkServiceEndTime(trademarkServiceEndTime);
			applicantCompany.setType(type);
			applicantCompany.setProductionSituation(productionSituation);
			applicantCompany.setProductQualityAttestation(productQualityAttestation);
			applicantCompany.setMachiningInfo(machiningInfo);
			applicantCompany.setProductSales(productSales);
			applicantCompany.setPackageSourceManager(packageSourceManager);
			applicantCompany.setOtherNt(otherNt);
			applicantCompany.setX(x);
			applicantCompany.setY(y);
			applicantCompany.setProfile(profile);
			applicantCompany.setUniformSocialCreditCode(uniformSocialCreditCode);
			applicantCompany.setOperationPeriodStartTime(operationPeriodStartTime);
			applicantCompany.setOperationPeriodEndTime(operationPeriodEndTime);
			applicantCompany.setSpybName(spybName);
			applicantCompany.setSpybProduct(spybProduct);
			applicantCompany.setSpybStartTime(spybStartTime);
			applicantCompany.setSpybEndTime(spybEndTime);
			applicantCompany.setAgentMobile(agentMobile);
			applicantCompany.setLegalMobile(legalMobile);
			applicantCompanyDao.update(applicantCompany);
		}
		
		if(!StringUtils.isEmpty(applicantCompanyProducts)) {
			JSONArray applicantCompanyProductArray = JSONArray.fromObject(applicantCompanyProducts);
			for(int i = 0;i<applicantCompanyProductArray.size();i++) {
				JSONObject applicantCompanyProduct = (JSONObject) applicantCompanyProductArray.get(i);
				String productName = applicantCompanyProduct.containsKey("productName") ? applicantCompanyProduct.getString("productName") : "";
				String area = applicantCompanyProduct.containsKey("area") ? applicantCompanyProduct.getString("area") : "";
				String yield = applicantCompanyProduct.containsKey("yield") ? applicantCompanyProduct.getString("yield") : "";
				String producer = applicantCompanyProduct.containsKey("producer") ? applicantCompanyProduct.getString("producer") : "";
				Integer brandId = applicantCompanyProduct.containsKey("brandId") ? applicantCompanyProduct.getInt("brandId") : null;
				TbApplicantCompanyProduct applicantCompanyProduct2 = new TbApplicantCompanyProduct();
				applicantCompanyProduct2.setApplicantCompanyId(applicantCompany.getId());
				applicantCompanyProduct2.setProductName(productName);
				applicantCompanyProduct2.setBrandId(brandId);
				applicantCompanyProduct2.setArea(area);
				applicantCompanyProduct2.setYield(yield);
				applicantCompanyProduct2.setProducer(producer);
				applicantCompanyProduct2.setDelFlag(0);
				applicantCompanyProduct2.setInputTime(new Date());
				applicantCompanyDao.save(applicantCompanyProduct2);
			}
		}
		
		if(!StringUtils.isEmpty(applicantCompanyCredentials)) {
			JSONArray applicantCompanyCredentialsArray = JSONArray.fromObject(applicantCompanyCredentials);
			for(int i = 0;i<applicantCompanyCredentialsArray.size();i++) {
				JSONObject applicantCompanyCredential = (JSONObject) applicantCompanyCredentialsArray.get(i);
				String licenseName = applicantCompanyCredential.containsKey("licenseName") ? applicantCompanyCredential.getString("licenseName") : "";
				String licenseNo = applicantCompanyCredential.containsKey("licenseNo") ? applicantCompanyCredential.getString("licenseNo") : "";
				String licensingOrganization = applicantCompanyCredential.containsKey("licensingOrganization") ? applicantCompanyCredential.getString("licensingOrganization") : "";
				String licensingTime = applicantCompanyCredential.containsKey("licensingTime") ? applicantCompanyCredential.getString("licensingTime") : "";
				TbApplicantCompanyCredentials applicantCompanyCredentials2 = new TbApplicantCompanyCredentials();
				applicantCompanyCredentials2.setApplicantCompanyId(applicantCompany.getId());
				applicantCompanyCredentials2.setLicenseName(licenseName);
				applicantCompanyCredentials2.setLicenseNo(licenseNo);
				applicantCompanyCredentials2.setLicensingOrganization(licensingOrganization);
				applicantCompanyCredentials2.setLicensingTime(licensingTime);
				applicantCompanyCredentials2.setDelFlag(0);
				applicantCompanyCredentials2.setInputTime(new Date());
				applicantCompanyDao.save(applicantCompanyCredentials2);
			}
		}
		
		if(!StringUtils.isEmpty(files)) {
			JSONArray fileArray = JSONArray.fromObject(files);
			TbFile applicantCompanyFile = null;
			for (int i = 0; i < fileArray.size(); i++) {
				JSONObject fileJo=(JSONObject) fileArray.get(i);
				applicantCompanyFile = new TbFile();
				if(fileJo.containsKey("fileName"))
					applicantCompanyFile.setFileName(fileJo.getString("fileName"));
				if(fileJo.containsKey("fileSize"))
					applicantCompanyFile.setFileSize(fileJo.getString("fileSize"));
				if(fileJo.containsKey("fileType"))
					applicantCompanyFile.setFileType(fileJo.getInt("fileType"));
				if(fileJo.containsKey("fileUrl"))
					applicantCompanyFile.setFileUrl(fileJo.getString("fileUrl"));
				fileDao.save(applicantCompanyFile);
				TbResApplicantCompanyFile resApplicantCompanyFile = new TbResApplicantCompanyFile();
				resApplicantCompanyFile.setApplicantCompanyId(applicantCompany.getId());
				resApplicantCompanyFile.setFileId(applicantCompanyFile.getId());
				resApplicantCompanyFile.setType(fileJo.containsKey("type") ? fileJo.getInt("type") : null);
				resApplicantCompanyFile.setDelFlag(0);
				resApplicantCompanyFileDao.save(resApplicantCompanyFile);
			}			
		}
		
		if(!StringUtils.isEmpty(trademarks)) {
			JSONArray trademarkArray = JSONArray.fromObject(trademarks);
			TbFile applicantCompanyTrademarkFile = null;
			TbApplicantCompanyTrademark applicantCompanyTrademark = null;
			for (int i = 0; i < trademarkArray.size(); i++) {
				JSONObject trademarkJo=(JSONObject) trademarkArray.get(i);
				applicantCompanyTrademark = new TbApplicantCompanyTrademark();
				applicantCompanyTrademark.setApplicantCompanyId(applicantCompany.getId());
				applicantCompanyTrademark.setDelFlag(0);
				if(trademarkJo.containsKey("name"))
					applicantCompanyTrademark.setName(trademarkJo.getString("name"));
				if(trademarkJo.containsKey("startTime"))
					applicantCompanyTrademark.setStartTime(DateTimeUtil.formatTime(trademarkJo.getString("startTime")));
				if(trademarkJo.containsKey("endTime"))
					applicantCompanyTrademark.setEndTime(DateTimeUtil.formatTime(trademarkJo.getString("endTime")));
				if(trademarkJo.containsKey("type"))
					applicantCompanyTrademark.setType(trademarkJo.getString("type"));
				applicantCompanyTrademarkDao.save(applicantCompanyTrademark);
				JSONArray fileArray = trademarkJo.containsKey("files") ? trademarkJo.getJSONArray("files") : null;
				if(fileArray != null) {
					for (int j = 0; j < fileArray.size(); j++) {
						JSONObject fileJo=(JSONObject) fileArray.get(j);
						applicantCompanyTrademarkFile = new TbFile();
						if(fileJo.containsKey("fileName"))
							applicantCompanyTrademarkFile.setFileName(fileJo.getString("fileName"));
						if(fileJo.containsKey("fileSize"))
							applicantCompanyTrademarkFile.setFileSize(fileJo.getString("fileSize"));
						if(fileJo.containsKey("fileType"))
							applicantCompanyTrademarkFile.setFileType(fileJo.getInt("fileType"));
						if(fileJo.containsKey("fileUrl"))
							applicantCompanyTrademarkFile.setFileUrl(fileJo.getString("fileUrl"));
						fileDao.save(applicantCompanyTrademarkFile);
						TbResApplicantCompanyTrademarkFile resApplicantCompanyTrademarkFile = new TbResApplicantCompanyTrademarkFile();
						resApplicantCompanyTrademarkFile.setApplicantCompanyTrademarkId(applicantCompanyTrademark.getId());
						resApplicantCompanyTrademarkFile.setFileId(applicantCompanyTrademarkFile.getId());
						resApplicantCompanyTrademarkFile.setType(fileJo.containsKey("type") ? fileJo.getInt("type") : null);
						resApplicantCompanyTrademarkFile.setDelFlag(0);
						resApplicantCompanyTrademarkFileDao.save(resApplicantCompanyTrademarkFile);
					}	
				}
			}			
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
