	package com.jinpinghu.logic.brandOrder;

	import com.jinpinghu.common.tools.DateTimeUtil;
	import com.jinpinghu.db.bean.TbBrandOrder;
	import com.jinpinghu.db.bean.TbEnterprise;
	import com.jinpinghu.db.bean.TbFile;
	import com.jinpinghu.db.dao.*;
	import com.jinpinghu.logic.BaseZLogic;
	import com.jinpinghu.logic.brandOrder.param.DetailBrandOrderParam;
	import fw.jbiz.ext.json.ZSimpleJsonObject;
	import fw.jbiz.logic.ZLogicParam;
	import net.sf.json.JSONArray;
	import net.sf.json.JSONObject;

	import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;
	import java.util.Map;

public class DetailBrandOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DetailBrandOrderParam myParam =(DetailBrandOrderParam)logicParam;
		String id =myParam.getId();
		TbResBrandCarNumDao tbResBrandCarNumDao = new TbResBrandCarNumDao(em);
		TbBrandOrderDao toDao =new TbBrandOrderDao(em);
		TbBrandDao tbBrandDao = new TbBrandDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbBrandShoppingCarDao shoppingCarDao = new TbBrandShoppingCarDao(em);
		TbFileDao fileDao = new TbFileDao(em);
		TbBrandOrder brandOrder = toDao.findById(Integer.valueOf(id));
		JSONObject jo = new JSONObject();
		if(brandOrder!=null) {
			jo.put("id",brandOrder.getId() );
			jo.put("addPeople",brandOrder.getAddPeople());
			jo.put("cancleInfo",brandOrder.getCancelInfo() );
			jo.put("orderNumber",brandOrder.getOrderNumber());
			jo.put("price",StringUtils.isEmpty(brandOrder.getPrice())?"0":new BigDecimal(brandOrder.getPrice()).stripTrailingZeros().toPlainString() );
			jo.put("rejectedInfo",brandOrder.getRejectedInfo() );
			jo.put("status",brandOrder.getStatus() );
			jo.put("inputTime",brandOrder.getInputTime()==null?"":DateTimeUtil.formatTime2(brandOrder.getInputTime()) );
			jo.put("timeSend",brandOrder.getInputTime()==null?"":DateTimeUtil.formatTime2(brandOrder.getTimeSend()));
			jo.put("timeAudit",brandOrder.getTimeAudit()==null?"":DateTimeUtil.formatTime2(brandOrder.getTimeAudit()));
			jo.put("timeCancel",brandOrder.getTimeCancel()==null?"":DateTimeUtil.formatTime2(brandOrder.getTimeCancel()));
			jo.put("timeComplete",brandOrder.getTimeComplete()==null?"":DateTimeUtil.formatTime2(brandOrder.getTimeComplete()));
			jo.put("timePay",brandOrder.getTimePay()==null?"":DateTimeUtil.formatTime2(brandOrder.getTimePay() ));
			jo.put("timeRejected",brandOrder.getTimeRejected()==null?"":DateTimeUtil.formatTime2(brandOrder.getTimeRejected()));
			jo.put("enterpriseId",brandOrder.getEnterpriseId() );
			jo.put("type",brandOrder.getType() );
			List<Map<String,Object>> carInfo = shoppingCarDao.findInOrderIdByTrademark(brandOrder.getId());
//			BigDecimal sum = BigDecimal.ZERO;
//			if(carInfo!=null){
//				for(int i=0;i<carInfo.size();i++){
//					Map<String, Object> map = carInfo.get(i);
//					Object carId = map.get("id");
//					Object brandId = map.get("brandId");
//					List<Map<String, Object>> list = tbResBrandCarNumDao.findEnterpriseByCarId(Integer.valueOf(carId.toString()),trademarkId);
//					map.put("numJa",list==null?"":list);
//					if(map.containsKey("enterpriseNum")&&map.containsKey("price")) {
//						BigDecimal price = new BigDecimal(StringUtils.isEmpty(map.get("price").toString())?"0":map.get("price").toString());
//						BigDecimal enterpriseNum = new BigDecimal(map.get("enterpriseNum").toString());
//						sum=sum.add(price.multiply(enterpriseNum));
//					}
//					 List<Object[]> trademark = tbBrandDao.findTrademarkByBrandId(Integer.valueOf(brandId.toString()),Integer.valueOf(carId.toString()));
//			            if (trademark != null) {
//			            	JSONArray ja = new JSONArray();
//			                for (int j = 0; j < trademark.size(); j++) {
//			                    Object[] o = trademark.get(j);
//			                    JSONObject inEnterprise = new JSONObject();
//			                    inEnterprise.put("enterpriseId", o[0]);
//			                    inEnterprise.put("enterpriseName", o[1]);
//			                    inEnterprise.put("num", o[2]);
//			                    ja.add(jo);
//			                }
//			                map.put("inEnterprise",ja);
//			            }
//				}
//				jo.put("sum",sum );
//			}
			jo.put("carInfo", carInfo);
			TbEnterprise enterpriseT = enterpriseDao.findById(brandOrder.getEnterpriseId());
			jo.put("enterpriseName",enterpriseT.getName() );
			jo.put("enterpriseLinkMobile",enterpriseT.getEnterpriseLinkMobile() );
			jo.put("enterpriseLinkPeople",enterpriseT.getEnterpriseLinkPeople() );
			jo.put("baseAddress",enterpriseT.getBaseAddress());
			jo.put("enterpriseCreditCode",enterpriseT.getEnterpriseCreditCode() );
			jo.put("enterpriseLegalPersonIdcard",enterpriseT.getEnterpriseLegalPersonIdcard() );
			jo.put("enterpriseAddress",enterpriseT.getEnterpriseAddress());
			TbFile file = fileDao.findOneByEnterpriseId(brandOrder.getEnterpriseId());
			if(file!=null) {
				jo.put("file", file.getFileUrl());
			}
		}
		res.add("msg", "查询成功");
		res.add("status", 1).add("result", jo);
		return true;
	}

}
