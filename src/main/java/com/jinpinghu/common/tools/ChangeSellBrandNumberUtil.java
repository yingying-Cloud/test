package com.jinpinghu.common.tools;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbSellBrand;
import com.jinpinghu.db.bean.TbSellBrandRecord;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbSellBrandDao;
import com.jinpinghu.db.dao.TbSellBrandRecordDao;

public class ChangeSellBrandNumberUtil {
	private ChangeSellBrandNumberUtil() {}
	private static ChangeSellBrandNumberUtil changeToolNumberUtil;
	public static ChangeSellBrandNumberUtil init(){
		if(changeToolNumberUtil==null){
			synchronized (ChangeSellBrandNumberUtil.class) {
				if(changeToolNumberUtil==null){
					changeToolNumberUtil = new ChangeSellBrandNumberUtil();
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
	
	public synchronized boolean minusNumberBrand(Integer brandId,BigDecimal number,EntityManager em,Integer enterpriseId,String useName){
		TbSellBrandDao ttDao = new TbSellBrandDao(em);
		TbBrandDao tbBrandDao = new TbBrandDao(em);
		TbSellBrand tbSellBrand = null;
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbSellBrand tt = ttDao.findByBrand(brandId, enterpriseId);
		TbEnterprise enterprise = enterpriseDao.findById(enterpriseId);
		if(brandId==null||number==null){
			return false;
		}
		
		BigDecimal num = null;
		
		try {
			num = new BigDecimal(tt.getNumber());
		}catch(Exception ex) {
			num = new BigDecimal(0);
		}
		BigDecimal newNumber = num.subtract(number);
//		if(newNumber.compareTo(BigDecimal.ZERO)==1||newNumber.compareTo(BigDecimal.ZERO)==0) {
			tt.setNumber(newNumber+"");
//		}
		ttDao.update(tt);
		TbSellBrandRecordDao tbSellBrandRecordDao = new TbSellBrandRecordDao(em);
		//农资企业出库流水
		TbSellBrandRecord tbSellBrandRecord = new TbSellBrandRecord();
		tbSellBrandRecord.setDelFlag(0);
		tbSellBrandRecord.setEnterpriseId(tt.getEnterpriseId());
		tbSellBrandRecord.setSellBrandId(tt.getId());
		tbSellBrandRecord.setRecordType(2);
		tbSellBrandRecord.setNumber(number +"");
		tbSellBrandRecord.setAllNumber(newNumber+"");
		tbSellBrandRecord.setUseTime(new Date());
		tbSellBrandRecord.setUseName(useName);
		tbSellBrandRecord.setInputTime(new Date());
		if(enterprise!=null) {
			tbSellBrandRecord.setOutMobile(enterprise.getEnterpriseLinkMobile());
			tbSellBrandRecord.setOutName(enterprise.getName());
		}
		tbSellBrandRecordDao.save(tbSellBrandRecord);
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
	public synchronized boolean minusNumberSellBrand(Integer sellBrandId,BigDecimal number,EntityManager em,Integer enterpriseId,String useName){
		TbSellBrandDao ttDao = new TbSellBrandDao(em);
		TbEnterpriseDao enterprisedao = new TbEnterpriseDao(em);
		TbEnterprise enterprise = enterprisedao.findById(enterpriseId);
		if(sellBrandId==null||number==null){
			return false;
		}
		TbSellBrand tt = ttDao.findById(sellBrandId);
		BigDecimal num = null;
		
		try {
			num = new BigDecimal(tt.getNumber());
		}catch(Exception ex) {
			num = new BigDecimal(0);
		}
		BigDecimal newNumber = num.subtract(number);
//		if(newNumber.compareTo(BigDecimal.ZERO)==1||newNumber.compareTo(BigDecimal.ZERO)==0) {
			tt.setNumber(newNumber+"");
//		}
		ttDao.update(tt);
		TbSellBrandRecordDao tbSellBrandRecordDao = new TbSellBrandRecordDao(em);
		//农资企业出库流水
		TbSellBrandRecord tbSellBrandRecord = new TbSellBrandRecord();
		tbSellBrandRecord.setDelFlag(0);
		tbSellBrandRecord.setEnterpriseId(tt.getEnterpriseId());
		tbSellBrandRecord.setSellBrandId(tt.getId());
		tbSellBrandRecord.setRecordType(2);
		tbSellBrandRecord.setNumber(number +"");
		tbSellBrandRecord.setAllNumber(newNumber+"");
		tbSellBrandRecord.setUseTime(new Date());
		tbSellBrandRecord.setUseName(useName);
		tbSellBrandRecord.setInputTime(new Date());
		if(enterprise!=null) {
			tbSellBrandRecord.setOutMobile(enterprise.getEnterpriseLinkMobile());
			tbSellBrandRecord.setOutName(enterprise.getName());
		}
		tbSellBrandRecordDao.save(tbSellBrandRecord);
		return true;
	}
	public synchronized boolean addNumber(Integer brandId,BigDecimal number,EntityManager em,Integer enterpriseId,String useName){
		TbSellBrandDao ttDao = new TbSellBrandDao(em);
		TbBrandDao tbBrandDao = new TbBrandDao(em);
		
		TbSellBrand checkIsExist = ttDao.findByBrand(brandId, enterpriseId);
		if(checkIsExist==null) {
			TbBrand tb = tbBrandDao.findById(brandId);
			checkIsExist =new TbSellBrand();
			checkIsExist.setDelFlag(0);
			checkIsExist.setBrandId(brandId);
			checkIsExist.setEnterpriseId(enterpriseId);
			checkIsExist.setProductName(tb.getProductName());
			checkIsExist.setDescribe(tb.getDescribe());
			checkIsExist.setInputTime(new Date());
			checkIsExist.setSpec(tb.getSpec());
			checkIsExist.setUnit(tb.getUnit());
			checkIsExist.setType(tb.getType());
			checkIsExist.setStatus(tb.getStatus());
			checkIsExist.setPrice(tb.getPrice());
			checkIsExist.setNumber("0");
			ttDao.save(checkIsExist);
		}else {
			checkIsExist =  ttDao.findById(checkIsExist.getId());
		}
		if(number==null){
			return false;
		}
		BigDecimal num = null;
		
//		try {
			num = new BigDecimal(checkIsExist.getNumber());
//		}catch(java.lang.NumberFormatException ex) {
//			num = new BigDecimal(0);
//		}
		BigDecimal newNumber = num.add(number);
		checkIsExist.setNumber(newNumber+"");
		ttDao.update(checkIsExist);
		TbSellBrandRecordDao toolRecordDao = new TbSellBrandRecordDao(em);
		//企业购买农资入库流水
		TbSellBrandRecord tbToolRecord = new TbSellBrandRecord();
		tbToolRecord.setDelFlag(0);
		tbToolRecord.setEnterpriseId(enterpriseId);
		tbToolRecord.setSellBrandId(checkIsExist.getId());
		tbToolRecord.setRecordType(1);
		tbToolRecord.setNumber(number +"");
		tbToolRecord.setAllNumber(newNumber+"");
		tbToolRecord.setUseTime(new Date());
		tbToolRecord.setUseName(useName);
		tbToolRecord.setInputTime(new Date());
		toolRecordDao.save(tbToolRecord);
		return true;
	}
}
