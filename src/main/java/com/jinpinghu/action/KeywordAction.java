package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.keyword.AddOrUpdateKeywordLogic;
import com.jinpinghu.logic.keyword.DeleteKeywordLogic;
import com.jinpinghu.logic.keyword.GetKeywordsLogic;
import com.jinpinghu.logic.keyword.param.AddOrUpdateKeywordParam;
import com.jinpinghu.logic.keyword.param.DeleteKeywordParam;
import com.jinpinghu.logic.keyword.param.GetKeywordsParam;

@Produces("application/json;charset=UTF-8")
@Path("keyword")
public class KeywordAction extends BaseZAction {

	/**
	 * 获取关键字
	 */
	@POST
	@Path("getKeywords.do")
	public String getKeywords(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		GetKeywordsParam myParam = new GetKeywordsParam(userId, apiKey, request);
		return new GetKeywordsLogic().process(myParam);
	}
	
	/**
	 * 添加或修改关键字
	 */
	@POST
	@Path("addOrUpdateKeyword.do")
	public String addOrUpdateKeyword(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("keyworkId") Integer keyworkId,
			@FormParam("name") String name,
			@Context HttpServletRequest request) {
		AddOrUpdateKeywordParam myParam = new AddOrUpdateKeywordParam(userId, apiKey, request);
		myParam.setKeyworkId(keyworkId);
		myParam.setName(name);
		return new AddOrUpdateKeywordLogic().process(myParam);
	}
	
	/**
	 * 删除关键字
	 */
	@POST
	@Path("deleteKeyword.do")
	public String deleteKeyword(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("keyworkIds") String keyworkIds,
			@Context HttpServletRequest request) {
		DeleteKeywordParam myParam = new DeleteKeywordParam(userId, apiKey, request);
		myParam.setKeyworkIds(keyworkIds);
		return new DeleteKeywordLogic().process(myParam);
	}
	
}
