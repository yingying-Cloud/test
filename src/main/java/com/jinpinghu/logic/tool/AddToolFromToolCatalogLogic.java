package com.jinpinghu.logic.tool;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResToolFile;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.bean.TbToolCatalogYxcf;
import com.jinpinghu.db.bean.TbToolYxcf;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolCatalogYxcfDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.AddToolFromToolCatalogParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;

public class AddToolFromToolCatalogLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToolFromToolCatalogParam myParam = (AddToolFromToolCatalogParam)logicParam;
		String ids = myParam.getIds();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String[] split = ids.split(",");
		List<String> list = Arrays.asList(split);
		TbToolDao toolDao = new TbToolDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		TbResToolFileDao tbResToolFileDao = new TbResToolFileDao(em);
		TbToolCatalogDao catalogDao = new TbToolCatalogDao(em);
		TbToolCatalogYxcfDao tbToolCatalogYxcfDao = new TbToolCatalogYxcfDao(em);
		for(String id:list) {
			TbToolCatalog catalog = catalogDao.findByIdStatus(Integer.valueOf(id));
			if(catalog!=null) {
				TbTool tbTool = toolDao.findByCode(catalog.getCode(),enterpriseId);
				if(tbTool!=null) {
					continue;
				}
				TbTool tool = new TbTool();
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
				
				tool.setN(catalog.getN());
				tool.setP(catalog.getP());
				tool.setK(catalog.getK());
				tool.setQt(catalog.getQt());
				tool.setQrCode(catalog.getQrCode());
				tool.setNPK(catalog.getNPK());
				tool.setNK(catalog.getNK());
				tool.setNP(catalog.getNP());
				tool.setPK(catalog.getPK());
				tool.setZjzl(catalog.getZjzl());
				tool.setYxcfUnit(catalog.getYxcfUnit());
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
				List<TbToolCatalogYxcf> yxcfList = tbToolCatalogYxcfDao.findByToolCatalogId(catalog.getId());
				if(yxcfList!=null) {
					for(TbToolCatalogYxcf yxcf:yxcfList) {
						TbToolYxcf t = new TbToolYxcf();
						t.setEffectiveIngredientsName(yxcf.getEffectiveIngredientsName());
						t.setEffectiveIngredientsValue(yxcf.getEffectiveIngredientsValue());
						t.setUnit(yxcf.getUnit());
						t.setToolId(tool.getId());
						tfDao.save(t);
					}
				}
			}
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
