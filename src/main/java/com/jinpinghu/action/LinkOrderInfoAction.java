package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.linkOrderInfo.AddLinkOrderInfoLogic;
import com.jinpinghu.logic.linkOrderInfo.ChangeLinkOrderInfoMobileLogic;
import com.jinpinghu.logic.linkOrderInfo.GetLinkOrderInfoByIdcardLogic;
import com.jinpinghu.logic.linkOrderInfo.GetLinkOrderInfoByPicLogic;
import com.jinpinghu.logic.linkOrderInfo.GetLinkOrderInfoByPicLogic2;
import com.jinpinghu.logic.linkOrderInfo.GetLinkOrderInfoByPicLogic3;
import com.jinpinghu.logic.linkOrderInfo.GetLinkOrderInfoListLogic;
import com.jinpinghu.logic.linkOrderInfo.GetLinkOrderInfoLogic;
import com.jinpinghu.logic.linkOrderInfo.GetUniformPriceRecoveryLogic;
import com.jinpinghu.logic.linkOrderInfo.GetUniformPriceSalesLogic;
import com.jinpinghu.logic.linkOrderInfo.param.AddLinkOrderInfoParam;
import com.jinpinghu.logic.linkOrderInfo.param.GetLinkOrderInfoByIdcardParam;
import com.jinpinghu.logic.linkOrderInfo.param.GetLinkOrderInfoByPicParam;
import com.jinpinghu.logic.linkOrderInfo.param.GetLinkOrderInfoListParam;
import com.jinpinghu.logic.linkOrderInfo.param.GetLinkOrderInfoParam;
import com.jinpinghu.logic.linkOrderInfo.param.GetUniformPriceDataParam;

import fw.jbiz.ext.memcache.ZCacheManager;
import fw.jbiz.ext.memcache.interfaces.ICache;


@Produces("application/json;charset=UTF-8")
@Path("linkOrderInfo")
public class LinkOrderInfoAction extends BaseZAction{

	@POST
	@Path("addLinkOrderInfo.do")
	public String addLinkOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("idcard") String idcard,
			@FormParam("sex") String sex,
			@FormParam("nation") String nation,
			@FormParam("address") String address,
			@FormParam("pic") String pic,
			@FormParam("country") String country,
			@FormParam("isIdcardPic") String isIdcardPic,
			@FormParam("mobile") String mobile,
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
		AddLinkOrderInfoParam myParam = new AddLinkOrderInfoParam(userId, apiKey, request);
		myParam.setAddress(address);
		myParam.setIdcard(idcard);
		myParam.setPic(pic);
		myParam.setName(name);
		myParam.setNation(nation);
		myParam.setSex(sex);
		myParam.setCountry(country);
		myParam.setIsIdcardPic(isIdcardPic);
		myParam.setMobile(mobile);
		String result = new AddLinkOrderInfoLogic().process(myParam);
		if (!StringUtils.isEmpty(idcard)) {
			linkInfoLock.remove(idcard);
		}
		return result;
	}
	
	@POST
	@Path("getLinkOrderInfoByPic.do")
	public String getLinkOrderInfoByPic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("pic") String pic,
			@Context HttpServletRequest request) {
		GetLinkOrderInfoByPicParam myParam = new GetLinkOrderInfoByPicParam(userId, apiKey, request);
		myParam.setPic(pic);
		return new GetLinkOrderInfoByPicLogic().process(myParam);
	}
	
	@POST
	@Path("getLinkOrderInfoByPic2.do")
	public String getLinkOrderInfoByPic2(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("pic") String pic,
			@Context HttpServletRequest request) {
		GetLinkOrderInfoByPicParam myParam = new GetLinkOrderInfoByPicParam(userId, apiKey, request);
		myParam.setPic(pic);
		return new GetLinkOrderInfoByPicLogic2().process(myParam);
	}
	
	@POST
	@Path("getLinkOrderInfoByPic3.do")
	public String getLinkOrderInfoByPic3(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("pic") String pic,
			@Context HttpServletRequest request) {
		GetLinkOrderInfoByPicParam myParam = new GetLinkOrderInfoByPicParam(userId, apiKey, request);
		myParam.setPic(pic);
		return new GetLinkOrderInfoByPicLogic3().process(myParam);
	}
	
	@POST
	@Path("getLinkOrderInfoList.do")
	public String getLinkOrderInfoList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("idcard") String idcard,
			@FormParam("mobile") String mobile,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("selectAll") String selectAll,
			@FormParam("dscd") String dscd,
			@FormParam("isZj") String isZj,
			@Context HttpServletRequest request) {
		GetLinkOrderInfoListParam myParam = new GetLinkOrderInfoListParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setIdcard(idcard);
		myParam.setMobile(mobile);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setSelectAll(selectAll);
		myParam.setDscd(dscd);
		myParam.setIsZj(isZj);
		return new GetLinkOrderInfoListLogic().process(myParam);
	}
	
	@POST
	@Path("getLinkOrderInfo.do")
	public String getLinkOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		GetLinkOrderInfoParam myParam = new GetLinkOrderInfoParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new GetLinkOrderInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getLinkOrderInfoByIdcard.do")
	public String getLinkOrderInfoByIdcard(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("idcard") String idcard,
			@Context HttpServletRequest request) {
		GetLinkOrderInfoByIdcardParam myParam = new GetLinkOrderInfoByIdcardParam(userId, apiKey, request);
		myParam.setIdcard(idcard);
		return new GetLinkOrderInfoByIdcardLogic().process(myParam);
	}
	
	@POST
	@Path("changeLinkOrderInfoMobile.do")
	public String addLinkOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("mobile") String mobile,
			@FormParam("idcard") String idcard,
			@Context HttpServletRequest request) {
		AddLinkOrderInfoParam myParam = new AddLinkOrderInfoParam(userId, apiKey, request);
		myParam.setIdcard(idcard);
		myParam.setMobile(mobile);
		return new ChangeLinkOrderInfoMobileLogic().process(myParam);
	}
	
	@POST
	@Path("getUniformPriceSales.do")
	public String getUniformPriceSales(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("idcard") String idcard,
			@Context HttpServletRequest request) {
		GetUniformPriceDataParam myParam = new GetUniformPriceDataParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		myParam.setIdcard(idcard);
		return new GetUniformPriceSalesLogic().process(myParam);
	}
	
	@POST
	@Path("getUniformPriceRecovery.do")
	public String getUniformPriceRecovery(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("idcard") String idcard,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		GetUniformPriceDataParam myParam = new GetUniformPriceDataParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		myParam.setIdcard(idcard);
		return new GetUniformPriceRecoveryLogic().process(myParam);
	}
}
