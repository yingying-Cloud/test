package com.jinpinghu.common.tools;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbResTool;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbResToolDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolRecordDao;

public class ChangeToolNumberUtil {
	private ChangeToolNumberUtil() {}
	private static ChangeToolNumberUtil changeToolNumberUtil;
	public static ChangeToolNumberUtil init(){
		if(changeToolNumberUtil==null){
			synchronized (ChangeToolNumberUtil.class) {
				if(changeToolNumberUtil==null){
					changeToolNumberUtil = new ChangeToolNumberUtil();
				}
			}
		}
		return changeToolNumberUtil;
	}
	//减去库存
	/**
	 * 
	 * @param toolId 购买农资id
	 * @param number 数量
	 * @param em
	 * @param plantEnterpriseId 买家企业
	 * @param useName 买家姓名
	 * @return
	 */
	public synchronized boolean minusNumber(Integer toolId,BigDecimal number,EntityManager em,Integer plantEnterpriseId,String useName){
		TbToolDao ttDao = new TbToolDao(em);
		TbEnterpriseDao enterprisedao = new TbEnterpriseDao(em);
		TbEnterprise enterprise = enterprisedao.findById(plantEnterpriseId);
		if(toolId==null||number==null){
			return false;
		}
		
		TbTool tt = ttDao.findById(toolId);
		BigDecimal num = null;
		
		try {
			num = new BigDecimal(tt.getNumber());
		}catch(Exception ex) {
			num = new BigDecimal(0);
		}
		BigDecimal newNumber = num.subtract(number);
		if(newNumber.compareTo(BigDecimal.ZERO)==1||newNumber.compareTo(BigDecimal.ZERO)==0) {
			tt.setNumber(newNumber+"");
		}
		ttDao.update(tt);
		TbToolRecordDao toolRecordDao = new TbToolRecordDao(em);
		//农资企业出库流水
		TbToolRecord tbToolRecord2 = new TbToolRecord();
		tbToolRecord2.setDelFlag(0);
		tbToolRecord2.setEnterpriseId(tt.getEnterpriseId());
		tbToolRecord2.setToolId(tt.getId());
		tbToolRecord2.setRecordType(2);
		tbToolRecord2.setNumber(number +"");
		tbToolRecord2.setAllNumber(newNumber+"");
		tbToolRecord2.setUseTime(new Date());
		tbToolRecord2.setUseName(useName);
		tbToolRecord2.setInputTime(new Date());
		tbToolRecord2.setSupplierName(tt.getSupplierName());
		if(enterprise!=null) {
			tbToolRecord2.setOutMobile(enterprise.getEnterpriseLinkMobile());
			tbToolRecord2.setOutName(enterprise.getName());
		}
		toolRecordDao.save(tbToolRecord2);
		return true;
	}
	public synchronized boolean addNumber(Integer toolId,BigDecimal number,EntityManager em,Integer plantEnterpriseId,String useName){
		TbToolDao ttDao = new TbToolDao(em);
		TbResToolDao trtDao = new TbResToolDao(em);
		TbResTool checkIsExist = trtDao.checkIsExist(toolId, plantEnterpriseId);
		TbTool plantTool = null;
		if(checkIsExist==null) {
			checkIsExist =new TbResTool();
			checkIsExist.setDelFlag(0);
			checkIsExist.setPlantEnterpriseId(plantEnterpriseId);
			checkIsExist.setToolId(toolId);
			
			TbTool tt = ttDao.findById(toolId);
			
			plantTool = new TbTool();
			plantTool.setDelFlag(0);
			plantTool.setType(tt.getType());
			plantTool.setDescribe(tt.getDescribe());
			plantTool.setEnterpriseId(plantEnterpriseId);
			plantTool.setModel(tt.getModel());
			plantTool.setName(tt.getName());
			plantTool.setNumber(0+"");
			plantTool.setUnit(tt.getUnit());
			plantTool.setPrice(tt.getPrice());
			plantTool.setSpecification(tt.getSpecification());
			plantTool.setSupplierName(tt.getSupplierName());
			ttDao.save(plantTool);
			checkIsExist.setPlantToolId(plantTool.getId());
			trtDao.save(checkIsExist);
		}else {
			plantTool =  ttDao.findById(checkIsExist.getPlantToolId());
		}
		if(checkIsExist.getPlantToolId()==null||number==null){
			return false;
		}
		BigDecimal num = null;
		
		try {
			num = new BigDecimal(plantTool.getNumber());
		}catch(java.lang.NumberFormatException ex) {
			num = new BigDecimal(0);
		}
		BigDecimal newNumber = num.add(number);
		plantTool.setNumber(newNumber+"");
		ttDao.update(plantTool);
		TbToolRecordDao toolRecordDao = new TbToolRecordDao(em);
		//企业购买农资入库流水
		TbToolRecord tbToolRecord = new TbToolRecord();
		tbToolRecord.setDelFlag(0);
		tbToolRecord.setEnterpriseId(plantEnterpriseId);
		tbToolRecord.setToolId(plantTool.getId());
		tbToolRecord.setRecordType(1);
		tbToolRecord.setNumber(number +"");
		tbToolRecord.setAllNumber(newNumber+"");
		tbToolRecord.setUseTime(new Date());
		tbToolRecord.setUseName(useName);
		tbToolRecord.setInputTime(new Date());
		tbToolRecord.setSupplierName(plantTool.getSupplierName());
		toolRecordDao.save(tbToolRecord);
		return true;
	}
	
	//减去库存
		/**
		 * 
		 * @param toolId 购买农资id
		 * @param number 数量
		 * @param em
		 * @param plantEnterpriseId 买家企业
		 * @param useName 买家姓名
		 * @return
		 */
		public synchronized boolean minusNumberForToolRecord(Integer toolId,BigDecimal number,EntityManager em,Integer fromEnterpriseId,String useName,
				Integer outEnterpriseId){
			TbToolDao ttDao = new TbToolDao(em);
			TbEnterpriseDao enterprisedao = new TbEnterpriseDao(em);
			TbEnterprise fromEnterprise = enterprisedao.findById(fromEnterpriseId);
			TbEnterprise outEnterprise = enterprisedao.findById(outEnterpriseId);
			if(toolId==null||number==null){
				return false;
			}
			
			TbTool tt = ttDao.findById(toolId);
			BigDecimal num = BigDecimal.ZERO;
			
			try {
				num = new BigDecimal(tt.getNumber());
			}catch(Exception ex) {
			}
			BigDecimal newNumber = num.subtract(number);
//			if(newNumber.compareTo(BigDecimal.ZERO)==1||newNumber.compareTo(BigDecimal.ZERO)==0) {
				tt.setNumber(newNumber+"");
//			}
			ttDao.update(tt);
			TbToolRecordDao toolRecordDao = new TbToolRecordDao(em);
			//农资企业出库流水
			TbToolRecord tbToolRecord2 = new TbToolRecord();
			tbToolRecord2.setDelFlag(0);
			tbToolRecord2.setEnterpriseId(tt.getEnterpriseId());
			tbToolRecord2.setToolId(tt.getId());
			tbToolRecord2.setRecordType(2);
			tbToolRecord2.setNumber(number +"");
			tbToolRecord2.setAllNumber(newNumber+"");
			tbToolRecord2.setUseTime(new Date());
			tbToolRecord2.setUseName(useName);
			tbToolRecord2.setInputTime(new Date());
			tbToolRecord2.setSupplierName(fromEnterprise.getName());
			tbToolRecord2.setPrice(tt.getPrice());
			if(outEnterprise!=null) {
				tbToolRecord2.setOutMobile(outEnterprise.getEnterpriseLinkMobile());
				tbToolRecord2.setOutName(outEnterprise.getName());
				tbToolRecord2.setOutEnterpriseId(outEnterpriseId);
			}
			toolRecordDao.save(tbToolRecord2);
			return true;
		}
		public synchronized boolean addNumberForToolRecord(Integer toolId,String code,BigDecimal number,EntityManager em,Integer outEnterpriseId,String useName,
				Integer fromEnterpriseId){
			TbToolDao ttDao = new TbToolDao(em);
			TbToolCatalogDao catalogDao = new TbToolCatalogDao(em);
			TbEnterpriseDao enterprisedao = new TbEnterpriseDao(em);
			TbEnterprise enterprise = enterprisedao.findById(fromEnterpriseId);
//			TbEnterprise outEnterprise = enterprisedao.findById(outEnterpriseId);
			TbTool plantTool = null; 
			TbToolCatalog catalog = catalogDao.findByCode(code, null);
			if(toolId!=null) {
				plantTool = ttDao.findById(toolId);
			}
			if(plantTool==null) {
				plantTool = new TbTool();
				plantTool.setDelFlag(0);
				plantTool.setDescribe(catalog.getDescribe());
				plantTool.setEnterpriseId(outEnterpriseId);
				plantTool.setModel(catalog.getModel());
				plantTool.setName(catalog.getName());
				plantTool.setPrice(catalog.getPrice());
				plantTool.setSpecification(catalog.getSpecification());
				plantTool.setUnit(catalog.getUnit());
				plantTool.setType(catalog.getType());
				plantTool.setSupplierName(catalog.getSupplierName());
				plantTool.setProductAttributes(catalog.getProductAttributes());
				plantTool.setRegistrationCertificateNumber(catalog.getRegistrationCertificateNumber());
				plantTool.setProductionUnits(catalog.getProductionUnits());
				plantTool.setExplicitIngredients(catalog.getExplicitIngredients());
				plantTool.setEffectiveIngredients(catalog.getEffectiveIngredients());			
				plantTool.setImplementationStandards(catalog.getImplementationStandards());
				plantTool.setDosageForms(catalog.getDosageForms());
				plantTool.setToxicity(catalog.getToxicity());
				plantTool.setQualityGrade(catalog.getQualityGrade());
				plantTool.setUniformPrice(catalog.getUniformPrice());
				plantTool.setCode(catalog.getCode());
				plantTool.setWholesalePrice(catalog.getWholesalePrice());
				
				plantTool.setHfzc(catalog.getHfzc());
				plantTool.setApprovalEndDate(catalog.getApprovalEndDate());
				plantTool.setApprovalNo(catalog.getApprovalNo());
				plantTool.setApproveNo(catalog.getApproveNo());
				plantTool.setCertificateNo(catalog.getCertificateNo());
				plantTool.setCheckHealthNo(catalog.getCheckHealthNo());
				plantTool.setHealthNo(catalog.getHealthNo());
				plantTool.setLimitDate(catalog.getLimitDate());
				plantTool.setProduced(catalog.getProduced());
				plantTool.setProductionNo(catalog.getProductionNo());
				
				plantTool.setN(catalog.getN());
				plantTool.setP(catalog.getP());
				plantTool.setK(catalog.getK());
				plantTool.setQt(catalog.getQt());
				plantTool.setQrCode(catalog.getQrCode());
				plantTool.setNumber(0+"");
				ttDao.save(plantTool);
			}
			BigDecimal num = BigDecimal.ZERO;
			
			try {
				num = new BigDecimal(plantTool.getNumber());
			}catch(Exception ex) {
			}
			BigDecimal newNumber = num.add(number);
			plantTool.setNumber(newNumber+"");
			ttDao.update(plantTool);
			TbToolRecordDao toolRecordDao = new TbToolRecordDao(em);
			//企业购买农资入库流水
			TbToolRecord tbToolRecord = new TbToolRecord();
			tbToolRecord.setDelFlag(0);
			tbToolRecord.setEnterpriseId(outEnterpriseId);
			tbToolRecord.setToolId(plantTool.getId());
			tbToolRecord.setRecordType(1);
			tbToolRecord.setNumber(number +"");
			tbToolRecord.setAllNumber(newNumber+"");
			tbToolRecord.setUseTime(new Date());
			tbToolRecord.setUseName(useName);
			tbToolRecord.setInputTime(new Date());
			tbToolRecord.setSupplierName(enterprise.getName());
			tbToolRecord.setFromEnterpriseId(fromEnterpriseId);
			toolRecordDao.save(tbToolRecord);
			return true;
		}
	
	
}
