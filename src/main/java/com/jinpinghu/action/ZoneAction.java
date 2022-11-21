package com.jinpinghu.action;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbZone;
import com.jinpinghu.logic.zone.AddOrUpdateZoneLogic;
import com.jinpinghu.logic.zone.DelZoneLogic;
import com.jinpinghu.logic.zone.GetZoneListLogic;
import com.jinpinghu.logic.zone.GetZoneLogic;
import com.jinpinghu.logic.zone.param.AddOrUpdateZoneParam;
import com.jinpinghu.logic.zone.param.DelZoneParam;
import com.jinpinghu.logic.zone.param.GetZoneListParam;
import com.jinpinghu.logic.zone.param.GetZoneParam;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("zone")
@Produces("application/json;charset=UTF-8")
public class ZoneAction extends BaseZAction{
    @POST
    @Path("addOrUpdateZone.do")
    public String addOrUpdateZone(
            @FormParam("userId") String userId,
            @FormParam("apiKey") String apiKey,
            @FormParam("id") String id,
            @FormParam("enterpriseId") String enterpriseId,
            @FormParam("code") String code,
            @FormParam("name") String name,
            @FormParam("area") String area,
            @FormParam("describe") String describe,
            @Context HttpServletRequest request) {
        AddOrUpdateZoneParam myParam=new AddOrUpdateZoneParam(userId,apiKey,request);
        TbZone tbZone=new TbZone();
        tbZone.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id));
        tbZone.setEnterpriseId(StringUtil.isNullOrEmpty(enterpriseId)?null:Integer.valueOf(enterpriseId));
        tbZone.setCode(StringUtil.isNullOrEmpty(code)?null:code.trim());
        tbZone.setName(StringUtil.isNullOrEmpty(name)?null:name.trim());
        tbZone.setArea(StringUtil.isNullOrEmpty(area)?null:area.trim());
        tbZone.setDescribe(StringUtil.isNullOrEmpty(describe)?null:describe.trim());
        myParam.setTbZone(tbZone);
        return new AddOrUpdateZoneLogic().process(myParam);
    }

    @POST
    @Path("delZone.do")
    public String delZone(
            @FormParam("userId") String userId,
            @FormParam("apiKey") String apiKey,
            @FormParam("id") String id,
            @Context HttpServletRequest request) {
        DelZoneParam myParam=new DelZoneParam(userId,apiKey,request);
        myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id));
        return new DelZoneLogic().process(myParam);
    }

    @POST
    @Path("getZone.do")
    public String getZone(
            @FormParam("userId") String userId,
            @FormParam("apiKey") String apiKey,
            @FormParam("id") String id,
            @Context HttpServletRequest request) {
        GetZoneParam myParam=new GetZoneParam(userId,apiKey,request);
        myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id));
        return new GetZoneLogic().process(myParam);
    }


    @POST
    @Path("getZoneList.do")
    public String getZoneList(
            @FormParam("userId") String userId,
            @FormParam("apiKey") String apiKey,
            @FormParam("enterpriseId") String enterpriseId,
            @FormParam("name") String name,
            @FormParam("nowPage") String nowPage,
            @FormParam("pageCount") String pageCount,
            @Context HttpServletRequest request) {
        GetZoneListParam myParam=new GetZoneListParam(userId,apiKey,request);
        myParam.setEnterpriseId(StringUtil.isNullOrEmpty(enterpriseId)?null:Integer.valueOf(enterpriseId));
        myParam.setName(StringUtil.isNullOrEmpty(name)?null:name.trim());
        myParam.setNowPage(StringUtil.isNullOrEmpty(nowPage)?null:Integer.valueOf(nowPage));
        myParam.setPageCount(StringUtil.isNullOrEmpty(pageCount)?null:Integer.valueOf(pageCount));
        return new GetZoneListLogic().process(myParam);
    }
}
