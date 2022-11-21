package com.jinpinghu.logic.sellOrder;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.*;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellOrder.param.ListAllSellOrderParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
public class ListAllSellOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ListAllSellOrderParam myParam =(ListAllSellOrderParam)logicParam;
		String brandName=myParam.getBrandName();
		String sellSellOrderNumber=myParam.getSellOrderNumber();
		String enterpriseName=myParam.getEnterpriseName();
		String beginCreateTime=myParam.getBeginCreateTime();
		String endCreateTime=myParam.getEndCreateTime();
		String beginPayTime=myParam.getBeginPayTime();
		String endPayTime=myParam.getEndPayTime();
		Integer status=StringUtils.isEmpty(myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer check = StringUtils.isEmpty(myParam.getCheck())?null:Integer.valueOf(myParam.getCheck());
		Integer plantEnterpriseId=StringUtils.isEmpty(myParam.getTrademarkId())?null:Integer.valueOf(myParam.getTrademarkId());
		Integer type=StringUtils.isEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		String name = myParam.getName();
		TbSellOrderDao sellSellOrderDao = new TbSellOrderDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbSellShoppingCarDao shoppingCarDao = new TbSellShoppingCarDao(em);
		JSONArray ja = new JSONArray();
		
		String userId = myParam.getUserId();
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser checkLogin2 = tbUserDao.checkLogin2(userId);
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbRole role = resUserRoleDao.findByUserTabId(checkLogin2.getId());
		if(role.getId()!=9&&role.getId()!=2&&(enterpriseId==null&&plantEnterpriseId==null)) {
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		
		Object[] count = sellSellOrderDao.findByTrademarkCount(brandName, sellSellOrderNumber, enterpriseName, beginCreateTime, endCreateTime, beginPayTime, endPayTime, status,enterpriseId,check,plantEnterpriseId,type,role.getId(),name);
		if(count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Object[]> list = sellSellOrderDao.findByTrademark(brandName, sellSellOrderNumber, enterpriseName, beginCreateTime, endCreateTime, beginPayTime, endPayTime, status, nowPage, pageCount,enterpriseId,check,plantEnterpriseId,type,role.getId(),name);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("id",o[0] );
				jo.put("enterpriseId",o[1] );
				jo.put("addPeople",o[2] );
				jo.put("orderNumber",o[3] );
				jo.put("price",o[4]==null?"":new BigDecimal(o[4].toString()) );
				jo.put("status",o[5] );
				jo.put("cancleInfo",o[6] );
				jo.put("rejectedInfo",o[7] );
				jo.put("inputTime",o[8] );
				jo.put("timeAudit",o[9] );
				jo.put("timePay",o[10] );
				jo.put("timeComplete",o[11] );
				jo.put("timeCancle",o[12] );
				jo.put("timeRejected",o[13] );
				jo.put("sellName",o[15] );
				List<Map<String,Object>> carInfo = shoppingCarDao.findInOrderIdByTrademark(Integer.valueOf(o[0].toString()));
				jo.put("carInfo", carInfo);
				TbEnterprise enterpriseT = enterpriseDao.findById(Integer.valueOf(o[1].toString()));
				jo.put("enterpriseName",enterpriseT.getName() );
				ja.add(jo);
			}
		}
		res.add("result", ja).add("allCounts", count[0]).add("money", count[1]==null? BigDecimal.ZERO:count[1]);
		res.add("msg", "查询成功");
		res.add("status", 1);
		return true;
	}

}
