package com.jinpinghu.logic.toolCatalog;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResToolFile;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.GetMiniToolCatalogListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class SyncToolUniformPriceLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMiniToolCatalogListParam myParam = (GetMiniToolCatalogListParam)logicParam;
		TbToolDao toolDao = new TbToolDao(em);
		TbEnterpriseZeroDao enterpriseZeroDao = new TbEnterpriseZeroDao(em);
		TbToolCatalogDao catalogDao = new TbToolCatalogDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		TbResToolFileDao tbResToolFileDao = new TbResToolFileDao(em);
		List<TbToolCatalog> catalogList = catalogDao.findByUniformPrice();
		if(catalogList!=null) {
		for(TbToolCatalog toolCatelog:catalogList) {
				List<TbEnterprise> enterpriseList = enterpriseZeroDao.findAllEnterprise();
				if(enterpriseList!=null) {
					for(TbEnterprise enterprise:enterpriseList) {
						TbTool tbTool = toolDao.findByCode(toolCatelog.getCode(),enterprise.getId());
						saveOrUpdateTool(tbTool,enterprise.getId(),toolCatelog,toolDao,tfDao,tbResToolFileDao);
					}
				}
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
	private void saveOrUpdateTool(TbTool tool,Integer enterpriseId,TbToolCatalog catalog,TbToolDao toolDao,
			TbFileDao tfDao,TbResToolFileDao tbResToolFileDao) {
		if(tool==null) {
			tool = new TbTool();
			tool.setDelFlag(0);
			tool.setDescribe(catalog.getDescribe());
			tool.setEnterpriseId(enterpriseId);
			tool.setModel(catalog.getModel());
			tool.setName(catalog.getName());
			tool.setPrice(catalog.getPrice());
			tool.setSpecification(catalog.getSpecification());
			tool.setUnit(catalog.getUnit());
			tool.setType(catalog.getType());
			tool.setSupplierName(catalog.getSupplierName());
			tool.setProductAttributes(catalog.getProductAttributes());
			tool.setRegistrationCertificateNumber(catalog.getRegistrationCertificateNumber());
			tool.setProductionUnits(catalog.getProductionUnits());
			tool.setExplicitIngredients(catalog.getExplicitIngredients());
			tool.setEffectiveIngredients(catalog.getEffectiveIngredients());			
			tool.setImplementationStandards(catalog.getImplementationStandards());
			tool.setDosageForms(catalog.getDosageForms());
			tool.setToxicity(catalog.getToxicity());
			tool.setQualityGrade(catalog.getQualityGrade());
			tool.setUniformPrice(catalog.getUniformPrice());
			tool.setCode(catalog.getCode());
			tool.setWholesalePrice(catalog.getWholesalePrice());
			tool.setHfzc(catalog.getHfzc());
			tool.setApprovalEndDate(catalog.getApprovalEndDate());
			tool.setApprovalNo(catalog.getApprovalNo());
			tool.setApproveNo(catalog.getApproveNo());
			tool.setCertificateNo(catalog.getCertificateNo());
			tool.setCheckHealthNo(catalog.getCheckHealthNo());
			tool.setHealthNo(catalog.getHealthNo());
			tool.setLimitDate(catalog.getLimitDate());
			tool.setProduced(catalog.getProduced());
			tool.setProductionNo(catalog.getProductionNo());
			toolDao.save(tool);
			List<TbFile> tfs =tfDao.findByToolCatalogId(catalog.getId());
			if(tfs!=null) {
				for(TbFile tf:tfs) {
					TbFile t = new TbFile();
					t.setFileName(tf.getFileName());
					t.setFileSize(tf.getFileSize());
					t.setFileType(tf.getFileType());
					t.setFileUrl(tf.getFileUrl());
					tfDao.save(t);
					TbResToolFile tbResToolFile = new TbResToolFile();
					tbResToolFile.setDelFlag(0);
					tbResToolFile.setFileId(t.getId());
					tbResToolFile.setToolId(tool.getId());
					tbResToolFileDao.save(tbResToolFile);
				}
			}
		}else {
			tool.setDescribe(catalog.getDescribe());
			tool.setModel(catalog.getModel());
			tool.setName(catalog.getName());
			tool.setPrice(catalog.getPrice());
			tool.setSpecification(catalog.getSpecification());
			tool.setUnit(catalog.getUnit());
			tool.setType(catalog.getType());
			tool.setSupplierName(catalog.getSupplierName());
			tool.setProductAttributes(catalog.getProductAttributes());
			tool.setRegistrationCertificateNumber(catalog.getRegistrationCertificateNumber());
			tool.setProductionUnits(catalog.getProductionUnits());
			tool.setExplicitIngredients(catalog.getExplicitIngredients());
			tool.setEffectiveIngredients(catalog.getEffectiveIngredients());			
			tool.setImplementationStandards(catalog.getImplementationStandards());
			tool.setDosageForms(catalog.getDosageForms());
			tool.setToxicity(catalog.getToxicity());
			tool.setQualityGrade(catalog.getQualityGrade());
			tool.setUniformPrice(catalog.getUniformPrice());
			tool.setWholesalePrice(catalog.getWholesalePrice());
			tool.setHfzc(catalog.getHfzc());
			tool.setApprovalEndDate(catalog.getApprovalEndDate());
			tool.setApprovalNo(catalog.getApprovalNo());
			tool.setApproveNo(catalog.getApproveNo());
			tool.setCertificateNo(catalog.getCertificateNo());
			tool.setCheckHealthNo(catalog.getCheckHealthNo());
			tool.setHealthNo(catalog.getHealthNo());
			tool.setLimitDate(catalog.getLimitDate());
			tool.setProduced(catalog.getProduced());
			tool.setProductionNo(catalog.getProductionNo());
			toolDao.update(tool);
		}
	}
	
	
	
}
