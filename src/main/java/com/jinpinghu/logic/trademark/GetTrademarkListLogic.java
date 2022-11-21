package com.jinpinghu.logic.trademark;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbResTrademarkBrandDao;
import com.jinpinghu.db.dao.TbTrademarkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.trademark.param.GetTrademarkListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetTrademarkListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetTrademarkListParam myParam = (GetTrademarkListParam)logicParam;
		String name = myParam.getName();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		TbTrademarkDao trademarkDao = new TbTrademarkDao(em);
		TbResTrademarkBrandDao trademarkBrandDao = new TbResTrademarkBrandDao(em);
		Integer count = trademarkDao.findByNameCount(name);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		JSONArray ja = new JSONArray();
		
		List<Object[]> list = trademarkDao.findByName(name, nowPage,pageCount);
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
				jo.put("fileUrl",o[10]);
				JSONArray jaBrand = new JSONArray();
				List<Object[]> listBrand = trademarkBrandDao.findBrandInfo(Integer.valueOf(o[0].toString()));
				if(listBrand!=null) {
					for(Object[] trtb:listBrand) {
						JSONObject brand = new JSONObject();
						brand.put("area",trtb[0]);
						brand.put("yield",trtb[1]);
						brand.put("productName", trtb[2]);
						jaBrand.add(brand);
					}
				}
				jo.put("brand", jaBrand);
				
				
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
