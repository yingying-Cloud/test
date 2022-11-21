package com.jinpinghu.logic.user;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.GetUserListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetUserListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetUserListParam myParam = (GetUserListParam)logicParam;
		String name=myParam.getName();
		String enterpriseName = myParam.getEnterpriseName();
		String mobile = myParam.getMobile();
		Integer roleId = StringUtils.isNullOrEmpty(myParam.getRoleId())?null:Integer.valueOf(myParam.getRoleId());
		Integer status = StringUtils.isNullOrEmpty(myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		Integer enterpriseType = StringUtils.isNullOrEmpty(myParam.getEnterpriseType())?null:Integer.valueOf(myParam.getEnterpriseType());
		TbUserDao userDao = new TbUserDao(em);
		List<Object[]> list = userDao.getUserInfo(name, enterpriseName, mobile, roleId, status, enterpriseType);
		JSONArray ja = new JSONArray();
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo =new JSONObject();
				jo.put("id", o[0]);
				jo.put("mobiel", o[0]);
				jo.put("name", o[0]);
				jo.put("expertIdcard", o[0]);
				jo.put("expertField", o[0]);
				jo.put("name", o[0]);
				jo.put("enterpriseType", o[0]);
				jo.put("status", o[0]);
				ja.add(jo);
			}
		}
		res.add("result", ja);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
