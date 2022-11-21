package com.jinpinghu.logic.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.ListAllOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class ListAllOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ListAllOrderParam myParam =(ListAllOrderParam)logicParam;
		String toolName=myParam.getToolName();
		String orderNumber=myParam.getOrderNumber();
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
		String name = myParam.getName();
		String isValidation = myParam.getIsValidation();
		String selectAll = myParam.getSelectAll();
		String uniformPrice = myParam.getUniformPrice();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		TbShoppingCarDao shoppingCarDao = new TbShoppingCarDao(em);
		JSONArray ja = new JSONArray();
		Integer count = toolOrderDao.findByAllCount(toolName, orderNumber, enterpriseName, beginCreateTime, endCreateTime, beginPayTime, endPayTime, status,enterpriseId,check,name,isValidation,selectAll,permissionEnterpriseIdList,uniformPrice);
		if(count==0||count==null){
			res.add("status", 1).add("allCounts", count);;
			res.add("msg", "无数据").add("money", BigDecimal.ZERO);;
			return true;
		}
		List<Object[]> list = toolOrderDao.findByAll(toolName, orderNumber, enterpriseName, beginCreateTime, endCreateTime, beginPayTime, endPayTime, status, nowPage, pageCount,enterpriseId,check,name,isValidation,selectAll,permissionEnterpriseIdList,uniformPrice);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("id",o[0] );
				jo.put("toolEnterpriseId",o[1] );
				jo.put("plantEnterpriseId",o[2] );
				jo.put("addPeople",o[3] );
				jo.put("orderNumber",o[4] );
				jo.put("price",(o[5] == null || "".equals(o[5])) ? "" : new BigDecimal(o[5].toString()) );
				jo.put("status",o[6] );
				jo.put("cancleInfo",o[7] );
				jo.put("rejectedInfo",o[8] );
				jo.put("inputTime",o[9] );
				jo.put("timeAudit",o[10] );
				jo.put("timePay",o[11] );
				jo.put("timeComplete",o[12] );
				jo.put("timeCancle",o[13] );
				jo.put("timeRejected",o[14] );
				jo.put("check",o[15] );
				jo.put("checkMoney",o[16] );
				List<Map<String,Object>> carInfo = shoppingCarDao.findInOrderId(Integer.valueOf(o[0].toString()),uniformPrice);
				jo.put("carInfo", carInfo);
				if(Integer.valueOf(o[17].toString())==1) {
					jo.put("plantEnterpriseName",o[18] == null ? "" : o[18].toString());
				}else if(Integer.valueOf(o[17].toString())==2){
					jo.put("name",o[19] == null ? "" : o[19].toString());
					jo.put("infoType",o[20] == null ? "" : o[20].toString());
				}
				jo.put("toolEnterpriseName",o[21] == null ? "" : o[21].toString());
				jo.put("idcard",o[22] == null ? "" : o[22].toString());
				jo.put("idcardPic",o[23] == null ? "" : o[23].toString());
				ja.add(jo);
			}
		}
		
		BigDecimal money = toolOrderDao.findByAllMoney(toolName, orderNumber, enterpriseName, beginCreateTime, endCreateTime, beginPayTime, endPayTime, status, enterpriseId, check, name,isValidation,selectAll,permissionEnterpriseIdList,uniformPrice);
		res.add("result", ja).add("allCounts", count);
		res.add("msg", "查询成功");
		res.add("status", 1).add("money", money == null ? BigDecimal.ZERO : money.setScale(2,BigDecimal.ROUND_HALF_UP));
		return true;
	}

}
