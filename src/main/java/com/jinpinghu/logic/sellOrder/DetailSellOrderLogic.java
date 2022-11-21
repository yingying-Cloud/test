	package com.jinpinghu.logic.sellOrder;

	import com.jinpinghu.common.tools.DateTimeUtil;
	import com.jinpinghu.db.bean.TbEnterprise;
	import com.jinpinghu.db.bean.TbFile;
	import com.jinpinghu.db.bean.TbSellOrder;
	import com.jinpinghu.db.dao.*;
	import com.jinpinghu.logic.BaseZLogic;
	import com.jinpinghu.logic.sellOrder.param.DetailSellOrderParam;
	import fw.jbiz.ext.json.ZSimpleJsonObject;
	import fw.jbiz.logic.ZLogicParam;
	import net.sf.json.JSONObject;

	import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
	import java.util.Map;

public class DetailSellOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DetailSellOrderParam myParam =(DetailSellOrderParam)logicParam;
		String id =myParam.getId();
		TbSellOrderDao toDao =new TbSellOrderDao(em);
		TbBrandDao tbSellDao = new TbBrandDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbSellShoppingCarDao shoppingCarDao = new TbSellShoppingCarDao(em);
		TbFileDao fileDao = new TbFileDao(em);
		TbSellOrder sellOrder = toDao.findById(Integer.valueOf(id));
		JSONObject jo = new JSONObject();
		if(sellOrder!=null) {
			jo.put("id",sellOrder.getId() );
			jo.put("addPeople",sellOrder.getAddPeople());
			jo.put("cancleInfo",sellOrder.getCancelInfo() );
			jo.put("orderNumber",sellOrder.getOrderNumber());
			jo.put("price",StringUtils.isEmpty(sellOrder.getPrice())?"0":new BigDecimal(sellOrder.getPrice()).stripTrailingZeros().toPlainString() );
			jo.put("rejectedInfo",sellOrder.getRejectedInfo() );
			jo.put("status",sellOrder.getStatus() );
			jo.put("inputTime",sellOrder.getInputTime()==null?"":DateTimeUtil.formatTime2(sellOrder.getInputTime()) );
			jo.put("timeSend",sellOrder.getInputTime()==null?"":DateTimeUtil.formatTime2(sellOrder.getTimeSend()));
			jo.put("timeAudit",sellOrder.getTimeAudit()==null?"":DateTimeUtil.formatTime2(sellOrder.getTimeAudit()));
			jo.put("timeCancel",sellOrder.getTimeCancel()==null?"":DateTimeUtil.formatTime2(sellOrder.getTimeCancel()));
			jo.put("timeComplete",sellOrder.getTimeComplete()==null?"":DateTimeUtil.formatTime2(sellOrder.getTimeComplete()));
			jo.put("timePay",sellOrder.getTimePay()==null?"":DateTimeUtil.formatTime2(sellOrder.getTimePay() ));
			jo.put("timeRejected",sellOrder.getTimeRejected()==null?"":DateTimeUtil.formatTime2(sellOrder.getTimeRejected()));
			jo.put("enterpriseId",sellOrder.getEnterpriseId() );
			List<Map<String,Object>> carInfo = shoppingCarDao.findInOrderIdByTrademark(sellOrder.getId());
			jo.put("carInfo", carInfo);
			TbEnterprise enterpriseT = enterpriseDao.findById(sellOrder.getEnterpriseId());
			jo.put("enterpriseName",enterpriseT.getName() );
			jo.put("enterpriseLinkMobile",enterpriseT.getEnterpriseLinkMobile() );
			jo.put("enterpriseLinkPeople",enterpriseT.getEnterpriseLinkPeople() );
			jo.put("baseAddress",enterpriseT.getBaseAddress());
			jo.put("enterpriseCreditCode",enterpriseT.getEnterpriseCreditCode() );
			jo.put("enterpriseLegalPersonIdcard",enterpriseT.getEnterpriseLegalPersonIdcard() );
			jo.put("enterpriseAddress",enterpriseT.getEnterpriseAddress());
			TbFile file = fileDao.findOneByEnterpriseId(sellOrder.getEnterpriseId());
			if(file!=null) {
				jo.put("file", file.getFileUrl());
			}
		}
		res.add("msg", "查询成功");
		res.add("status", 1).add("result", jo);
		return true;
	}

}
