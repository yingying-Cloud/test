package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import com.jinpinghu.logic.offlineStore.GetToolListLogic;
import com.jinpinghu.logic.offlineStore.PayLogic;
import com.jinpinghu.logic.offlineStore.param.GetToolListParam;
import com.jinpinghu.logic.offlineStore.param.PayParam;
import com.jinpinghu.logic.offlineStore.SyncPayLogic;
import com.jinpinghu.logic.offlineStore.param.SyncPayParam;

import fw.jbiz.ext.memcache.ZCacheManager;
import fw.jbiz.ext.memcache.interfaces.ICache;

@Path("offlineStore")
@Produces("application/json;charset=UTF-8")
public class OfflineStoreAction {

	@POST
	@Path("pay.do")
	public String pay(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("shopId") String enterpriseId,
			@FormParam("goodsIds") String toolIds,
			@FormParam("accountNums") String accountNums,
			@FormParam("prices") String prices,
			@FormParam("payType") String payType,
			@FormParam("orderTime") String orderTime,
			@FormParam("resultAmount") String resultAmount,
			@FormParam("code") String code,
			@FormParam("idcard") String idcard,
			@FormParam("name") String name,
			@FormParam("sex") String sex,
			@FormParam("nation") String nation,
			@FormParam("address") String address,
			@FormParam("pic") String pic,
			@FormParam("finalRatioFees") String finalRatioFees,
			@Context HttpServletRequest request) {
		ICache linkInfoLock = ZCacheManager.getInstance("linkInfoLock");
		if (!StringUtils.isEmpty(idcard)) {
			while("1".equals(linkInfoLock.get(idcard))) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			linkInfoLock.add(idcard, "1");
		}
		
		PayParam myParam = new PayParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setToolIds(toolIds);
		myParam.setAccountNums(accountNums);
		myParam.setPrices(prices);
		myParam.setPayType(StringUtils.isEmpty(payType) ? null : Integer.valueOf(payType));
		myParam.setOrderTime(orderTime);
		myParam.setResultAmount(resultAmount);
		myParam.setCode(code);
		myParam.setIdcard(idcard);
		myParam.setName(name);
		myParam.setSex(sex);
		myParam.setNation(nation);
		myParam.setAddress(address);
		myParam.setPic(pic);
		myParam.setFinalRatioFees(finalRatioFees);
		String result = new PayLogic().process(myParam);
		if (!StringUtils.isEmpty(idcard)) {
			linkInfoLock.remove(idcard);
		}
		return result;
	}
	
	@POST
	@Path("getToolList.do")
	public String getToolList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("shopId") String enterpriseId,
			@FormParam("groupId") String groupId,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@FormParam("dnm") String dnm,
			@Context HttpServletRequest request) {
		GetToolListParam myParam = new GetToolListParam(userId,apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setType(groupId);
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setDnm(dnm);
		return new GetToolListLogic().process(myParam);
	}
	
	/**
	 * 同步离线订单，同步后，支付方式统一为现金支付
	 * @param apiKey
	 * @param userId
	 * @param jsonData jsonDate格式为{"data":[{ },{ },{ }]} 
	 * 		其中data内jsonArray各个节点的jsonObject为pay.do接口中除去userId，apiKey和payType的全部参数
	 * @param request
	 * @return
	 */
	@POST
	@Path("syncPay.do")
	public String syncPay(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("jsonData") String jsonData,
			@Context HttpServletRequest request) {
		SyncPayParam myParam = new SyncPayParam(userId,apiKey,request);
		myParam.setJsonData(jsonData);
		return new SyncPayLogic().process(myParam);
	}
	
}
