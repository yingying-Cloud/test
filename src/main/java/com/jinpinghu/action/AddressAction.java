package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import com.jinpinghu.logic.address.AddOrUpdateAddressLogic;
import com.jinpinghu.logic.address.DelAddressLogic;
import com.jinpinghu.logic.address.GetInfoLogic;
import com.jinpinghu.logic.address.GetListLogic;
import com.jinpinghu.logic.address.param.AddressParam;

@Path("address")
@Produces("application/json;charset=UTF-8")
public class AddressAction extends BaseZAction{
	
	/**
	 * 添加/更新收货地址
	 */
	@POST
	@Path("addOrUpdateAddress.do")
	public String addOrUpdateAddress(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,
			@FormParam("linkPeople") String linkPeople,
			@FormParam("linkMobile") String linkMobile,
			@FormParam("province") String province,
			@FormParam("city") String city,
			@FormParam("county") String county,
			@FormParam("address") String address,
			@Context HttpServletRequest request) {
		AddressParam myParam = new AddressParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setLinkPeople(linkPeople);
		myParam.setLinkMobile(linkMobile);
		myParam.setProvince(province);
		myParam.setCity(city);
		myParam.setCounty(county);
		myParam.setAddress(address);
		return new AddOrUpdateAddressLogic().process(myParam);
	}
	
	/**
	 * 设置默认地址
	 */
	@POST
	@Path("setDefautAddress.do")
	public String setDefautAddress(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		AddressParam myParam = new AddressParam(userId, apiKey, request);
		myParam.setId(id);
		return new SetDefautAddressLogic().process(myParam);
	}
	
	/**
	 * 获取收货地址列表
	 */
	@POST
	@Path("getList.do")
	public String getList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		AddressParam myParam = new AddressParam(userId, apiKey, request);
		return new GetListLogic().process(myParam);
	}
	
	
	/**
	 * 获取详情
	 */
	@POST
	@Path("getInfo.do")
	public String getInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		AddressParam myParam = new AddressParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetInfoLogic().process(myParam);
	}
	
	
	/**
	 * 删除收货地址
	 */
	@POST
	@Path("delAddress.do")
	public String delAddress(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		AddressParam myParam = new AddressParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelAddressLogic().process(myParam);
	}
}
