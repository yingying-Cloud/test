package com.jinpinghu.logic.tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.GetToolListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetToolListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolListParam myParam = (GetToolListParam)logicParam;
		String name = myParam.getName();
		String enterpriseName = myParam.getEnterpriseName();
		String enterpriseType = myParam.getEnterpriseType();
		Integer enterpriseId =StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String type =myParam.getType();
		String unit = myParam.getUnit();
		String ids = myParam.getIds();
		String code = myParam.getCode();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String supplierName = myParam.getSupplierName();
		String dnm = myParam.getDnm();
		String orderby = myParam.getOrderby();
		String sortDirection = myParam.getSortDirection();
		String selectAll = myParam.getSelectAll();
		String recordNo = myParam.getRecordNo();
		String uniformPrice = myParam.getUniformPrice();
		String dscd = myParam.getDscd();
		String productAttributes = myParam.getProductAttributes();
		String lowPrice = myParam.getLowPrice();
		String highPrice = myParam.getHighPrice();
		String etName = myParam.getEtName();
		TbUserDao userDao = new TbUserDao(em);
		loginUser = userDao.checkLogin2(myParam.getUserId());
		List<Integer> permissionEnterpriseIdList = new ArrayList<>(0);
		Integer loginEnterpriseId = null;
		if (loginUser != null) {
			permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
			loginEnterpriseId = EnterpriseDataPermissionUtil.getLoginEnterpriseId(loginUser.getId(), em);
		}
		
		
		List<String> notIn = null;
		if(!StringUtils.isEmpty(ids)) {
			notIn= new ArrayList<String>();
			String[] split = ids.split(",");
			notIn = Arrays.asList(split);
		}
		TbToolDao toolDao2 = new TbToolDao(em);
		
		Integer count = toolDao2.findByNameCount(enterpriseId,name, enterpriseName, enterpriseType,supplierName,
				type,unit,notIn,code,dnm,selectAll,permissionEnterpriseIdList,recordNo,uniformPrice,dscd,productAttributes,lowPrice,highPrice,etName);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		JSONArray ja = new JSONArray();
		
		List<Object[]> list = toolDao2.findByName(enterpriseId,name, enterpriseName, enterpriseType,nowPage,pageCount,supplierName,
				type,unit,notIn,code,dnm,orderby,selectAll,permissionEnterpriseIdList,recordNo,uniformPrice,dscd,productAttributes,
				lowPrice,highPrice,etName,loginEnterpriseId,sortDirection);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo  =new JSONObject();
				jo.put("id", o[0]);
				jo.put("toolName", o[2]);
				jo.put("enterpriseName", o[1]);
				jo.put("model", o[3]);
				jo.put("specification", o[4]);
				jo.put("unit", o[5]);
				jo.put("price", o[6]);
				jo.put("number", (o[7] == null || "".equals(o[7])) ? "" : new BigDecimal(o[7].toString()));
				jo.put("describe", o[8]);
				jo.put("type", o[9]);
				jo.put("fileUrl",o[10]);
				jo.put("supplierName", o[11]);
				jo.put("typeName", o[12]);
				jo.put("productionUnits", o[13]);
				jo.put("uniformPrice", o[14]);
				jo.put("code", o[15]);
				jo.put("num", o[16]);
				jo.put("sellNum", o[16]);
				jo.put("buyNum", o[17]);
				jo.put("wholesalePrice", o[18]);
				jo.put("registrationCertificateNumber", o[19] == null ? "" : o[19].toString());
				jo.put("dnm", o[20]);
				jo.put("recordNo", o[21]);
				jo.put("productAttributes", o[24]);
				jo.put("distance", o[29]);
				jo.put("zero", o[14]==null?"":(o[14]+"").equals("1")?23:"");
				
//				String sellNum = toolDao2.getSellNumByToolId(Integer.valueOf(o[0].toString()));
//				jo.put("sellNum", sellNum);
//				jo.put("num", sellNum);
				ja.add(jo);
			}
		}
		res.add("status", 1).add("msg", "操作成功").add("result", ja).add("allCounts", count);
		return true;
	}
	@Override
   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
			return true;
		}
}
