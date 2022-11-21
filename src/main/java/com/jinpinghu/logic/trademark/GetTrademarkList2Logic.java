package com.jinpinghu.logic.trademark;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbResTrademarkBrandDao;
import com.jinpinghu.db.dao.TbTrademarkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.trademark.param.GetTrademarkList2Param;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetTrademarkList2Logic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetTrademarkList2Param myParam = (GetTrademarkList2Param)logicParam;
		Integer brandId =StringUtils.isEmpty( myParam.getBrandId())?null:Integer.valueOf(myParam.getBrandId());
		Integer trademarkId =StringUtils.isEmpty( myParam.getTrademarkId())?null:Integer.valueOf(myParam.getTrademarkId());
		String name = myParam.getName();
		String productName = myParam.getProductName();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		TbTrademarkDao trademarkDao = new TbTrademarkDao(em);
		TbResTrademarkBrandDao trademarkBrandDao = new TbResTrademarkBrandDao(em);
		Integer count = trademarkDao.findByBrandCount(name,productName,brandId,trademarkId);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		JSONArray ja = new JSONArray();
		
		List<Object[]> list = trademarkDao.findByBrand(name,productName, nowPage,pageCount,brandId,trademarkId);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo  =new JSONObject();
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
				jo.put("area",o[10]);
				jo.put("yield",o[11]);
				jo.put("productName",o[12]);
				jo.put("fileUrl",o[13]);
				
				ja.add(jo);
			}
		}
		res.add("status", 1).add("msg", "操作成功").add("result", ja).add("allCounts", count);
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
