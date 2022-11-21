package com.jinpinghu.logic.brandSale;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbBrandSale;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResBrandSaleFile;
import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.dao.TbBrandSaleDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResBrandSaleFileDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandSale.param.AddOrUpdateBrandSaleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateBrandSaleLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateBrandSaleParam myParam = (AddOrUpdateBrandSaleParam)logicParam;
		Integer id = StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Integer brandId = StringUtils.isNullOrEmpty(myParam.getBrandId())?null:Integer.valueOf(myParam.getBrandId());
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Date inputTime = StringUtils.isNullOrEmpty(myParam.getInputTime())?null:DateTimeUtil.formatTime(myParam.getInputTime());
		String price = myParam.getPrice();
		String num = myParam.getNum();
		String file = myParam.getFile();
		String describe = myParam.getDescribe();
		
		TbBrandSaleDao tbBrandSaleDao = new TbBrandSaleDao(em);
		TbBrandSale tbBrandSale = null;
		if(id!=null) {
			tbBrandSale = tbBrandSaleDao.findById(id);
		}
		if(tbBrandSale==null) {
			tbBrandSale = new TbBrandSale();
			tbBrandSale.setBrandId(brandId);
			tbBrandSale.setEnterpriseId(enterpriseId);
			tbBrandSale.setInputTime(inputTime);
			tbBrandSale.setPrice(StringUtils.isNullOrEmpty(price)?"":price);
			tbBrandSale.setNum(num);
			tbBrandSale.setDelFlag(0);
			tbBrandSale.setDescribe(describe);
			tbBrandSale.setStatus(0);
			tbBrandSale.setUpStatus(0);
			tbBrandSaleDao.save(tbBrandSale);
		}else {
			tbBrandSale.setBrandId(brandId);
			tbBrandSale.setEnterpriseId(enterpriseId);
			tbBrandSale.setPrice(price);
			tbBrandSale.setNum(num);
			tbBrandSale.setInputTime(inputTime);
			tbBrandSale.setDescribe(describe);
			tbBrandSale.setUpStatus(0);
			tbBrandSaleDao.update(tbBrandSale);
		}
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResBrandSaleFileDao trfgDao =new TbResBrandSaleFileDao(em);
		
		List<TbFile> tfs =tfDao.findByBrandSaleId(tbBrandSale.getId());
		List<TbResBrandSaleFile> trgfs =trfgDao.findByBrandSaleId(tbBrandSale.getId());
		if(trgfs!=null){
			for(TbResBrandSaleFile trgf:trgfs){
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
					TbResBrandSaleFile trpf=new TbResBrandSaleFile();
					trpf.setFileId(tfe.getId());
					trpf.setBrandSaleId(tbBrandSale.getId());
					trpf.setDelFlag(0);
					trfgDao.save(trpf);
				}
			}
		}
		
		
		res.add("id", brandId);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
