package com.jinpinghu.logic.warehouse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantWarehouseDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.warehouse.param.GetPlantWarehouseListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPlantWarehouseListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetPlantWarehouseListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetPlantWarehouseListParam myParam=(GetPlantWarehouseListParam)GetPlantWarehouseListParam;
		String plantName=StringUtils.isNullOrEmpty(myParam.getPlantName())?null:myParam.getPlantName();
		Integer recordType=StringUtils.isNullOrEmpty(myParam.getWarehouseType())?null:Integer.valueOf(myParam.getWarehouseType());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		Integer enterpriseId =StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()); 
		String workSn = myParam.getWorkSn();
		
		Integer nowPage=StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		TbUserDao tudao=new TbUserDao(em);
		TbUser tu=new TbUser();
		tu=tudao.checkLogin2(myParam.getUserId());
		if(tu==null){
			res.add("status", 2)
			.add("msg", "无数据！");
			return true;
		}
		TbPlantWarehouseDao rdao=new TbPlantWarehouseDao(em);
		List<Map<String, Object>> list = null;
		Integer count = null;
		if(recordType==null||recordType==0) {
			list = rdao.getBrandAllNum(enterpriseId, nowPage, pageCount);
			count = rdao.getBrandAllNumCount(enterpriseId);
			if(count==null){
				res.add("status", 2)
				.add("msg", "无数据！");
				return true;
			}
			if(list!=null) {
				for(Map<String, Object> map:list) {
					BigDecimal number = new BigDecimal(map.get("inNum").toString()).subtract(new BigDecimal(map.get("outNum").toString()));
					map.put("number", number.compareTo(BigDecimal.ZERO)==-1?0:number.toBigInteger());
				}
			}
		}else {
			list = rdao.getBrandAllNumByRecordType(enterpriseId, recordType, nowPage, pageCount,startTime,endTime,workSn);
			count = rdao.getBrandAllNumByRecordTypeCount(enterpriseId, recordType, startTime, endTime,workSn);
			if(count==null){
				res.add("status", 2)
				.add("msg", "无数据！");
				return true;
			}
			TbFileDao tfdao=new TbFileDao(em);
			if(list!=null) {
				for(Map<String, Object> map:list) {
					List<Map<String, Object>> file = tfdao.findByPlantWarehouseIdMap(Integer.valueOf(map.get("id").toString()));
					map.put("file", file);
					
				}
			}
		}
		res.add("status", 1)
		.add("msg", "操作成功！")
		.add("allCounts", count)
		.add("result", list);
		return true;
	}
}
