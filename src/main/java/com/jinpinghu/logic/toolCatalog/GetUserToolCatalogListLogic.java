package com.jinpinghu.logic.toolCatalog;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.GetToolCatalogListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetUserToolCatalogListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolCatalogListParam myParam = (GetToolCatalogListParam)logicParam;
		String name = myParam.getName();
		String enterpriseName = myParam.getEnterpriseName();
		String enterpriseType = myParam.getEnterpriseType();
		Integer enterpriseId =StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String type =myParam.getType();
		String unit = myParam.getUnit();
		String code = myParam.getCode();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String supplierName = myParam.getSupplierName();
		String status = myParam.getStatus();
		
		String uniformPrice = myParam.getUniformPrice();
		String ids = myParam.getIds();
		List<String> id = null;
		if(!StringUtils.isEmpty(ids)) {
			id = Arrays.asList(ids.split(","));
		}
		
		TbToolCatalogDao toolCatelogDao2 = new TbToolCatalogDao(em);
		
		Integer count = toolCatelogDao2.findByUserCount(myParam.getUserId(),name,supplierName,type,unit,code,uniformPrice,id,status);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		JSONArray ja = new JSONArray();
		
		List<Object[]> list = toolCatelogDao2.findByUser(myParam.getUserId(),nowPage,pageCount,name,supplierName,type,unit,code,uniformPrice,id,status);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo  =new JSONObject();
				jo.put("id", o[0]);
				jo.put("toolCatelogName", o[1]);
				jo.put("model", o[2]);
				jo.put("specification", o[3]);
				jo.put("unit", o[4]);
				jo.put("price", o[5]);
				jo.put("number", o[6]);
				jo.put("describe", o[7]);
				jo.put("type", o[8]);
				jo.put("fileUrl",o[9]);
				jo.put("supplierName", o[10]);
				jo.put("productionUnits", o[11]);
				jo.put("uniformPrice", o[12]);
				jo.put("typeName", o[13]);
				jo.put("code", o[14]);
				jo.put("status", o[23]);
				jo.put("wholesalePrice", o[24]);
				jo.put("registrationCertificateNumber", o[15] == null ? "" : o[15].toString());
				
				jo.put("remark", o[25]);
				jo.put("name", o[26]);
				jo.put("mobile", o[27]);
				jo.put("inputTime", o[28]);
				jo.put("enterpriseName", o[29]);
				
				ja.add(jo);
			}
		}
		res.add("status", 1).add("msg", "操作成功").add("result", ja).add("allCounts", count);
		return true;
	}
}
