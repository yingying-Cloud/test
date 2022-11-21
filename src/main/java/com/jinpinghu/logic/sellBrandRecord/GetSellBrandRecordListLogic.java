package com.jinpinghu.logic.sellBrandRecord;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import com.jinpinghu.db.dao.TbSellBrandRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellBrand.SellBrandType;
import com.jinpinghu.logic.sellBrandRecord.param.GetSellBrandRecordListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetSellBrandRecordListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetSellBrandRecordListParam myParam = (GetSellBrandRecordListParam)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String name =myParam.getName();
		Integer recordType = StringUtils.isEmpty(myParam.getRecordType())?null:Integer.valueOf(myParam.getRecordType());
		Integer sellBrandId = StringUtils.isEmpty(myParam.getSellBrandId())?null:Integer.valueOf(myParam.getSellBrandId());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String type = myParam.getType();
		JSONArray ja = new JSONArray();
		TbSellBrandRecordDao recordDao = new TbSellBrandRecordDao(em);
		
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		
		Integer count = recordDao.getSellBrandRecordListCount(enterpriseId, sellBrandId, name,recordType,startTime,endTime,type);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据！");
			return true;
		}
		
		List<Object[]> list = recordDao.getSellBrandRecordList( enterpriseId, sellBrandId, name, nowPage, pageCount,recordType,startTime,endTime,type);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("id", o[0]);
				jo.put("enterpriseName", o[1]);
				jo.put("sellBrandName", o[2]);
				jo.put("recordType", o[3]);
				jo.put("allNumber", o[4] == null ? "" : new BigDecimal(o[4].toString()));
				jo.put("number", o[5] == null ? "" : new BigDecimal(o[5].toString()));
				jo.put("fileUrl", o[6]);
				jo.put("useName", o[7]);
				jo.put("useTime", o[8]);
				jo.put("unit", o[9]);
				jo.put("type", o[10]);
				jo.put("typeName", o[10]==null?"":SellBrandType.getValue((Integer)o[10]));
				jo.put("outName", o[11]);
				jo.put("outMobile", o[12]);
				
				ja.add(jo);
			}
		}
		res.add("status", 1).add("msg", "操作成功").add("result", ja).add("allCounts", count);
		return true;
	}
}
