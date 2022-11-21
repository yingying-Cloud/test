package com.jinpinghu.logic.trademark;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResTrademarkBrandDao;
import com.jinpinghu.db.dao.TbTrademarkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.trademark.param.GetTrademarkInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetTrademarkInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetTrademarkInfoParam myParam = (GetTrademarkInfoParam)logicParam;
		Integer id = StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbTrademarkDao trademarkDao = new TbTrademarkDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		TbResTrademarkBrandDao trademarkBrandDao = new TbResTrademarkBrandDao(em);
		Object[] o = trademarkDao.findInfoById(id);
		JSONObject jo  =new JSONObject();
		if(o!=null) {
			jo.put("id", o[0]);
			jo.put("brandName",o[1]);
			jo.put("address", o[2]);
			jo.put("trademarkName", o[3]);
			jo.put("productCertification", o[4]);
			jo.put("source",o[5]);
			jo.put("inputTime", o[6]);
			jo.put("contractNumber", o[7]);
			jo.put("x",o[8]);
			jo.put("y",o[9]);
				if(o[0]!=null) {
					List<TbFile> tfs =tfDao.findByTrademarkId(Integer.valueOf(o[0].toString()));
					JSONArray ja = new JSONArray();
					if(tfs!=null) {
						for(TbFile tf:tfs) {
							JSONObject file = new JSONObject();
							file.put("id",tf.getId());
							file.put("fileName", tf.getFileName());
							file.put("fileSize", tf.getFileSize());
							file.put("fileType", tf.getFileType());
							file.put("fileUrl",  tf.getFileUrl());
							ja.add(file);
						}
						jo.put("file", ja);
					}
					JSONArray jaBrand = new JSONArray();
					List<Object[]> list = trademarkBrandDao.findBrandInfo(Integer.valueOf(o[0].toString()));
					if(list!=null) {
						for(Object[] trtb:list) {
							JSONObject brand = new JSONObject();
							brand.put("area",trtb[0]);
							brand.put("yield",trtb[1]);
							brand.put("productName", trtb[2]);
							brand.put("brandId", trtb[3]);
							jaBrand.add(brand);
						}
					}
					jo.put("brand", jaBrand);
				}
		}
		res.add("status", 1).add("msg", "操作成功").add("result", jo);
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
