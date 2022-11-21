package com.jinpinghu.logic.tool;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResToolCatalogFile;
import com.jinpinghu.db.bean.TbResToolFile;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.bean.TbToolCatalogYxcf;
import com.jinpinghu.db.bean.TbToolYxcf;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolCatalogFileDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolCatalogYxcfDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolYxcfDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.AddOrUpdateToolParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateToolLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateToolParam myParam = (AddOrUpdateToolParam)logicParam;
		String id = myParam.getId();
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String name = myParam.getName();
		String model = myParam.getModel();
		String specification = myParam.getSpecification();
		String unit=myParam.getUnit();
		String price=myParam.getPrice();
		String number=myParam.getNumber();
		String describe = myParam.getDescribe();
		Integer type=StringUtils.isEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		String file = myParam.getFile();
		String supplierName = myParam.getSupplierName();
		String productionUnits=myParam.getProductionUnits();
		String registrationCertificateNumber=myParam.getRegistrationCertificateNumber();
		String explicitIngredients=myParam.getExplicitIngredients();
		String effectiveIngredients=myParam.getEffectiveIngredients();
		String implementationStandards=myParam.getImplementationStandards();
		String dosageForms=myParam.getDosageForms();
		String productAttributes=myParam.getProductAttributes();
		String toxicity=myParam.getToxicity();
		String qualityGrade=myParam.getQualityGrade();
		String uniformPrice=myParam.getUniformPrice();
		String code=myParam.getCode();
		String wholesalePrice = myParam.getWholesalePrice();
		String dnm = myParam.getDnm();
		String hfzc = myParam.getHfzc();
		Date approvalEndDate =StringUtils.isEmpty( myParam.getApprovalEndDate())?null:DateTimeUtil.formatTime(myParam.getApprovalEndDate());
		String approvalNo = myParam.getApprovalNo();
		String approveNo = myParam.getApproveNo();
		String certificateNo = myParam.getCertificateNo();
		String checkHealthNo = myParam.getCheckHealthNo();
		String healthNo = myParam.getHealthNo();
		Date limitDate = StringUtils.isEmpty(myParam.getLimitDate())?null:DateTimeUtil.formatTime(myParam.getLimitDate());
		String produced = myParam.getProduced();
		String productionNo = myParam.getProductionNo();
		String n = myParam.getN();
		String p = myParam.getP();
		String k = myParam.getK();
		String qt = myParam.getQt();
		String qrCode = myParam.getQrCode();
		String NPK = myParam.getNPK();
		String NK = myParam.getNK();
		String NP = myParam.getNP();
		String PK = myParam.getPK();
		String yxcfUnit = myParam.getYxcfUnit();
		String zjzl = myParam.getZjzl();
		String yxcfJa = myParam.getYxcfJa();
		//nysx字段
		String nysx = myParam.getNysx();
		
		TbTool tool = null;
		TbToolDao toolDao2 = new TbToolDao(em);
		if(!StringUtils.isEmpty(id)) {
			tool = toolDao2.findById(Integer.valueOf(id));
		}
		
		if(!StringUtils.isEmpty(dnm)&&enterpriseId!=null) {
			TbTool findByDnm = toolDao2.findByDnm(dnm, enterpriseId,StringUtils.isEmpty(id)?null:Integer.valueOf(id));
			if(findByDnm!=null) {
				res.add("status", 2).add("msg", "该店内码已存在！");
				return true;
			}
		}
		if(tool!=null) {
			if(describe != null) {
				tool.setDescribe(describe);
			}
			if (enterpriseId != null) {
				tool.setEnterpriseId(enterpriseId);
			}
			if (model != null) {
				tool.setModel(model);
			}
			if (name != null) {
				tool.setName(name);
			}
			if (price != null) {
				tool.setPrice(price);
			}
			if (specification != null) {
				tool.setSpecification(specification);
			}
			if (unit != null) {
				tool.setUnit(unit);
			}
			if (type != null) {
				tool.setType(type);
			}
			if (supplierName != null) {
				tool.setSupplierName(supplierName);
			}
			if (productionUnits != null) {
				tool.setProductionUnits(productionUnits);
			}
			if (registrationCertificateNumber != null) {
				tool.setRegistrationCertificateNumber(registrationCertificateNumber);
			}
			if (explicitIngredients != null) {
				tool.setExplicitIngredients(explicitIngredients);
			}
			if (effectiveIngredients != null) {
				tool.setEffectiveIngredients(effectiveIngredients);
			}
			if (implementationStandards != null) {
				tool.setImplementationStandards(implementationStandards);
			}	
			if (dosageForms != null) {
				tool.setDosageForms(dosageForms);
			}
			if (productAttributes != null) {
				tool.setProductAttributes(productAttributes);
			}
			if (toxicity != null) {
				tool.setToxicity(toxicity);
			}
			if (qualityGrade != null) {
				tool.setQualityGrade(qualityGrade);
			}
			if (uniformPrice != null) {
				tool.setUniformPrice(uniformPrice);
			}
			if (wholesalePrice != null) {
				tool.setWholesalePrice(wholesalePrice);
			}
			if (dnm != null) {
				tool.setDnm(dnm);
			}
			if (hfzc != null) {
				tool.setHfzc(hfzc);
			}
			if (approvalEndDate != null) {
				tool.setApprovalEndDate(approvalEndDate);
			}
			if (approvalNo != null) {
				tool.setApprovalNo(approvalNo);
			}
			if (certificateNo != null) {
				tool.setCertificateNo(certificateNo);
			}
			if (checkHealthNo != null) {
				tool.setCheckHealthNo(checkHealthNo);
			}
			if (healthNo != null) {
				tool.setHealthNo(healthNo);
			}
			if (limitDate != null) {
				tool.setLimitDate(limitDate);
			}
			if (produced != null) {
				tool.setProduced(produced);
			}
			if (productionNo != null) {
				tool.setProductionNo(productionNo);
			}
			if (n != null) {
				tool.setN(n);
			}
			if (p != null) {
				tool.setP(p);
			}
			if (k != null) {
				tool.setK(k);
			}
			if (qt != null) {
				tool.setQt(qt);
			}
			if (qrCode != null) {
				tool.setQrCode(qrCode);
			}
			if (NPK != null) {
				tool.setNPK(NPK);
			}
			if (NK != null) {
				tool.setNK(NK);
			}
			if (NP != null) {
				tool.setNP(NP);
			}
			if (PK != null) {
				tool.setPK(PK);
			}
			if (zjzl != null) {
				tool.setZjzl(zjzl);
			}
			if (yxcfUnit != null) {
				tool.setYxcfUnit(yxcfUnit);
			}
			if (nysx != null) {
				tool.setNysx(nysx);
			}
//			tool.setCode(code);
			
			toolDao2.update(tool);
		}else {
			
			tool = new TbTool();
			tool.setDelFlag(0);
			tool.setDescribe(describe);
			tool.setEnterpriseId(enterpriseId);
			tool.setModel(model);
			tool.setName(name);
			tool.setPrice(price);
			tool.setSpecification(specification);
			tool.setUnit(unit);
			tool.setType(type);
			tool.setSupplierName(supplierName);
			tool.setProductAttributes(productAttributes);
			tool.setRegistrationCertificateNumber(registrationCertificateNumber);
			tool.setProductionUnits(productionUnits);
			tool.setExplicitIngredients(explicitIngredients);
			tool.setEffectiveIngredients(effectiveIngredients);			
			tool.setImplementationStandards(implementationStandards);
			tool.setDosageForms(dosageForms);
			tool.setToxicity(toxicity);
			tool.setQualityGrade(qualityGrade);
			tool.setUniformPrice(uniformPrice);
			tool.setCode(code);
			tool.setWholesalePrice(wholesalePrice);
			tool.setDnm(dnm);
			tool.setHfzc(hfzc);
			tool.setApprovalEndDate(approvalEndDate);
			tool.setApprovalNo(approvalNo);
			tool.setApproveNo(approveNo);
			tool.setCertificateNo(certificateNo);
			tool.setCheckHealthNo(checkHealthNo);
			tool.setHealthNo(healthNo);
			tool.setLimitDate(limitDate);
			tool.setProduced(produced);
			tool.setProductionNo(productionNo);
			tool.setN(n);
			tool.setP(p);
			tool.setK(k);
			tool.setQt(qt);
			tool.setQrCode(qrCode);
			tool.setNPK(NPK);
			tool.setNK(NK);
			tool.setNP(NP);
			tool.setPK(PK);
			tool.setZjzl(zjzl);
			tool.setYxcfUnit(yxcfUnit);
			tool.setNysx(nysx);
			toolDao2.save(tool);
		}
		
		
		if (file != null) {
			TbFileDao tfDao = new TbFileDao(em);
			TbResToolFileDao trfgDao =new TbResToolFileDao(em);
			
			List<TbFile> tfs =tfDao.findByToolId(tool.getId());
			List<TbResToolFile> trgfs =trfgDao.findByToolId(tool.getId());
			if(trgfs!=null){
				for(TbResToolFile trgf:trgfs){
					trfgDao.delete(trgf);
				}
			}
			if(tfs!=null){
				for(TbFile tbFile:tfs){
					tfDao.delete(tbFile);
				}
			}
			
			if(!StringUtils.isEmpty(file)){
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
						TbResToolFile trpf=new TbResToolFile();
						trpf.setFileId(tfe.getId());
						trpf.setToolId(Integer.valueOf(tool.getId()));
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
		}
		
		TbToolYxcfDao yxcfDao = new TbToolYxcfDao(em);
		yxcfDao.DelYxcfByToolId(tool.getId());
		if(!StringUtils.isEmpty(yxcfJa)){
			JSONArray yxcfList= JSONArray.fromObject(yxcfJa);
			if(yxcfList.size()>0){
				for(int i=0;i<yxcfList.size();i++){
					JSONObject jo = (JSONObject) yxcfList.get(i);
					TbToolYxcf tbToolYxcf = new TbToolYxcf();
					tbToolYxcf.setEffectiveIngredientsName(jo.containsKey("effectiveIngredientsName")?jo.getString("effectiveIngredientsName"):null);
					tbToolYxcf.setEffectiveIngredientsValue(jo.containsKey("effectiveIngredientsValue")?jo.getString("effectiveIngredientsValue"):null);
					tbToolYxcf.setToolId(tool.getId());
					tbToolYxcf.setUnit(jo.containsKey("unit")?jo.getString("unit"):null);
					yxcfDao.save(tbToolYxcf);
				}
			}
		}
		
		TbToolCatalogDao toolCatelogDao = new TbToolCatalogDao(em);
		TbToolCatalog toolCatelog = toolCatelogDao.findByCodeUserId(code, myParam.getUserId());
		if(toolCatelog!=null) {
			
			toolCatelog.setDescribe(describe);
			toolCatelog.setModel(model);
			toolCatelog.setName(name);
			toolCatelog.setPrice(price);
			toolCatelog.setSpecification(specification);
			toolCatelog.setUnit(unit);
			toolCatelog.setType(type);
			toolCatelog.setSupplierName(supplierName);
			toolCatelog.setProductionUnits(productionUnits);
			toolCatelog.setRegistrationCertificateNumber(registrationCertificateNumber);
			toolCatelog.setExplicitIngredients(explicitIngredients);
			toolCatelog.setEffectiveIngredients(effectiveIngredients);
			toolCatelog.setImplementationStandards(implementationStandards);
			toolCatelog.setDosageForms(dosageForms);
			toolCatelog.setProductAttributes(productAttributes);
			toolCatelog.setToxicity(toxicity);
			toolCatelog.setQualityGrade(qualityGrade);
			toolCatelog.setUniformPrice(uniformPrice);
			toolCatelog.setCode(code);
			toolCatelog.setWholesalePrice(wholesalePrice);
			toolCatelog.setRemark(null);
			
			toolCatelog.setHfzc(hfzc);
			toolCatelog.setApprovalEndDate(approvalEndDate);
			toolCatelog.setApprovalNo(approvalNo);
			toolCatelog.setApproveNo(approveNo);
			toolCatelog.setCertificateNo(certificateNo);
			toolCatelog.setCheckHealthNo(checkHealthNo);
			toolCatelog.setHealthNo(healthNo);
			toolCatelog.setLimitDate(limitDate);
			toolCatelog.setProduced(produced);
			toolCatelog.setProductionNo(productionNo);
			
			toolCatelog.setN(n);
			toolCatelog.setP(p);
			toolCatelog.setK(k);
			toolCatelog.setQt(qt);
			toolCatelog.setQrCode(qrCode);
			toolCatelog.setNPK(NPK);
			toolCatelog.setNK(NK);
			toolCatelog.setNP(NP);
			toolCatelog.setPK(PK);
			toolCatelog.setZjzl(zjzl);
			toolCatelog.setYxcfUnit(yxcfUnit);
			toolCatelog.setNysx(nysx);
			toolCatelogDao.update(toolCatelog);

			TbFileDao tfDao = new TbFileDao(em);
			TbResToolCatalogFileDao trfgDao =new TbResToolCatalogFileDao(em);
			
			List<TbFile> tfs =tfDao.findByToolCatalogId(toolCatelog.getId());
			List<TbResToolCatalogFile> trgfs =trfgDao.findByToolCatalogId(toolCatelog.getId());
			if(trgfs!=null){
				for(TbResToolCatalogFile trgf:trgfs){
					trfgDao.delete(trgf);
				}
			}
			if(tfs!=null){
				for(TbFile tbFile:tfs){
					tfDao.delete(tbFile);
				}
			}
			
			if(!StringUtils.isEmpty(file)){
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
						TbResToolCatalogFile trpf=new TbResToolCatalogFile();
						trpf.setFileId(tfe.getId());
						trpf.setToolCatalogId(Integer.valueOf(toolCatelog.getId()));
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
			
			TbToolCatalogYxcfDao catalogYxcfDao = new TbToolCatalogYxcfDao(em);
			catalogYxcfDao.DelYxcfByToolId(toolCatelog.getId());
			if(!StringUtils.isEmpty(yxcfJa)){
				JSONArray yxcfList= JSONArray.fromObject(yxcfJa);
				if(yxcfList.size()>0){
					for(int i=0;i<yxcfList.size();i++){
						JSONObject jo = (JSONObject) yxcfList.get(i);
						TbToolCatalogYxcf tbToolYxcf = new TbToolCatalogYxcf();
						tbToolYxcf.setEffectiveIngredientsName(jo.containsKey("effectiveIngredientsName")?jo.getString("effectiveIngredientsName"):null);
						tbToolYxcf.setEffectiveIngredientsValue(jo.containsKey("effectiveIngredientsValue")?jo.getString("effectiveIngredientsValue"):null);
						tbToolYxcf.setToolCatalogId(toolCatelog.getId());
						tbToolYxcf.setUnit(jo.containsKey("unit")?jo.getString("unit"):null);
						catalogYxcfDao.save(tbToolYxcf);
					}
				}
			}
			
			if(!StringUtils.isEmpty(toolCatelog.getUniformPrice())&&toolCatelog.getUniformPrice().equals("1")) {
				TbToolDao tbToolDao = new TbToolDao(em);
				tbToolDao.updateUniforPrice(toolCatelog.getCode(), price, uniformPrice);
			}
			
			
			
		}
		
		
		res.add("status", 1).add("msg", "操作成功");
		
		return true;
	}
}
