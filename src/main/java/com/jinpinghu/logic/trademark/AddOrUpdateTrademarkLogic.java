package com.jinpinghu.logic.trademark;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResTrademarkBrand;
import com.jinpinghu.db.bean.TbResTrademarkFile;
import com.jinpinghu.db.bean.TbTrademark;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResTrademarkBrandDao;
import com.jinpinghu.db.dao.TbResTrademarkFileDao;
import com.jinpinghu.db.dao.TbTrademarkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.trademark.param.AddOrUpdateTrademarkParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateTrademarkLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateTrademarkParam myParam = (AddOrUpdateTrademarkParam)logicParam;
		String id = myParam.getId();
		String brandName=myParam.getBrandName();
		String address=myParam.getAddress();
		String trademarkName=myParam.getTrademarkName();
		String productCertification=myParam.getProductCertification();
		String source=myParam.getSource();
		String contractNumber=myParam.getContractNumber();
		String x=myParam.getX();
		String y=myParam.getY();
		String file=myParam.getFile();
		String brand=myParam.getBrand();
		
		TbTrademark trademark = null;
		TbTrademarkDao trademarkDao = new TbTrademarkDao(em);
		if(!StringUtils.isEmpty(id)) {
			trademark = trademarkDao.findById(Integer.valueOf(id));
		}
		if(trademark!=null) {
			trademark.setAddress(address);
			trademark.setBrandName(brandName);
			trademark.setContractNumber(contractNumber);
			trademark.setProductCertification(productCertification);
			trademark.setSource(source);
			trademark.setTrademarkName(trademarkName);
			trademark.setX(x);
			trademark.setY(y);
			trademarkDao.update(trademark);
		}else {
			
			trademark = new TbTrademark();
			trademark.setAddress(address);
			trademark.setBrandName(brandName);
			trademark.setContractNumber(contractNumber);
			trademark.setDelFlag(0);
			trademark.setProductCertification(productCertification);
			trademark.setSource(source);
			trademark.setTrademarkName(trademarkName);
			trademark.setX(x);
			trademark.setY(y);
			trademark.setInputTime(new Date());
			trademarkDao.save(trademark);
		}
		
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResTrademarkFileDao trfgDao =new TbResTrademarkFileDao(em);
		List<TbFile> tfs =tfDao.findByTrademarkId(trademark.getId());
		List<TbResTrademarkFile> trgfs =trfgDao.findByTrademarkId(trademark.getId());
		if(trgfs!=null){
			for(TbResTrademarkFile trgf:trgfs){
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
					TbResTrademarkFile trpf=new TbResTrademarkFile();
					trpf.setFileId(tfe.getId());
					trpf.setTrademarkId(trademark.getId());
					trpf.setDelFlag(0);
					trfgDao.save(trpf);
				}
			}
		}
		
		TbResTrademarkBrandDao trtbsDao =new TbResTrademarkBrandDao(em);
		List<TbResTrademarkBrand> trtbs =trtbsDao.findByTrademarkId(trademark.getId());
		if(trtbs!=null){
			for(TbResTrademarkBrand trgf:trtbs){
				trfgDao.delete(trgf);
			}
		}
		if(!StringUtils.isEmpty(brand)){
			JSONArray arrayF= JSONArray.fromObject(brand);
			if(arrayF.size()>0){
				for(int i=0;i<arrayF.size();i++){
					JSONObject jsonObj=(JSONObject) arrayF.get(i);
					TbResTrademarkBrand trtb=new TbResTrademarkBrand();
					if(jsonObj.containsKey("area"))
					trtb.setArea(jsonObj.getString("area"));
					if(jsonObj.containsKey("brandId"))
					trtb.setBrandId(jsonObj.getInt("brandId"));
//					if(jsonObj.containsKey("trademarkId"))
					trtb.setTrademarkId(trademark.getId());
					if(jsonObj.containsKey("yield"))
					trtb.setYield(jsonObj.getString("yield"));
					trtb.setDelFlag(0);
					trfgDao.save(trtb);
				}
			}
		}
		
		
		
		res.add("status", 1).add("msg", "操作成功");
		
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
