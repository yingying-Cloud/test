package com.jinpinghu.logic.sc;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResSupplyReleaseFile;
import com.jinpinghu.db.bean.TbSupplyRelease;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResSupplyReleaseFileDao;
import com.jinpinghu.db.dao.TbSupplyReleaseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.AddSupplyReleaseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddSupplyReleaseLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddSupplyReleaseParam myParam = (AddSupplyReleaseParam)logicParam;
		TbSupplyRelease paramSupplyRelease = myParam.getSupplyRelease();
		String fileArray = myParam.getFileArray();
		
		TbSupplyReleaseDao supplyReleaseDao = new TbSupplyReleaseDao(em);
		
		TbSupplyRelease supplyRelease = supplyReleaseDao.findById(paramSupplyRelease.getId());
		
		if (supplyRelease != null) {
			supplyRelease.setEnterpriseId(paramSupplyRelease.getEnterpriseId());
			supplyRelease.setBrandId(paramSupplyRelease.getBrandId());
			supplyRelease.setWorkId(paramSupplyRelease.getWorkId());
			supplyRelease.setEstimateOutput(paramSupplyRelease.getEstimateOutput());
			supplyRelease.setSpecification(paramSupplyRelease.getSpecification());
			supplyRelease.setNum(paramSupplyRelease.getNum());
			supplyRelease.setPrice(paramSupplyRelease.getPrice());
			supplyRelease.setType(paramSupplyRelease.getType());
			supplyRelease.setContactPerson(paramSupplyRelease.getContactPerson());
			supplyRelease.setContactPhone(paramSupplyRelease.getContactPhone());
			supplyRelease.setContactAddress(paramSupplyRelease.getContactAddress());
			supplyRelease.setDscd(paramSupplyRelease.getDscd());
			supplyRelease.setStock(paramSupplyRelease.getNum());
			supplyReleaseDao.update(supplyRelease);
		}else {
			supplyRelease = paramSupplyRelease;
			supplyRelease.setDelFlag(0);
			supplyRelease.setStock(supplyRelease.getNum());
			supplyRelease.setStatus(0);
			supplyReleaseDao.save(supplyRelease);
		}
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResSupplyReleaseFileDao trfgDao =new TbResSupplyReleaseFileDao(em);
		List<TbFile> tfs =tfDao.findBySupplyReleaseId(supplyRelease.getId());
		List<TbResSupplyReleaseFile> trgfs =trfgDao.findBySupplyReleaseId(supplyRelease.getId());
		if(trgfs!=null){
			for(TbResSupplyReleaseFile trgf:trgfs){
				trfgDao.delete(trgf);
			}
		}
		if(tfs!=null){
			for(TbFile tbFile:tfs){
				tfDao.delete(tbFile);
			}
		}
		if(!StringUtils.isEmpty(fileArray)){
			JSONArray arrayF= JSONArray.fromObject(fileArray);
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
					TbResSupplyReleaseFile trpf=new TbResSupplyReleaseFile();
					trpf.setFileId(tfe.getId());
					trpf.setSupplyReleaseId(supplyRelease.getId());
					trfgDao.save(trpf);
				}
			}
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
