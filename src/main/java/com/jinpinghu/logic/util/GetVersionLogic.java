package com.jinpinghu.logic.util;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.db.bean.AppUtil;
import com.jinpinghu.db.dao.AppUtilDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.util.param.GetVersionParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetVersionLogic extends BaseZLogic {
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetVersionParam myParam = (GetVersionParam)logicParam;
		String machineAppVersion = myParam.getAppVersion();
		AppUtilDao uDao = new AppUtilDao(em);
		
		//获取当前版本
		AppUtil currentVersion = uDao.findByKey("current_version");
		
		AppUtil u_a = uDao.findByValue(currentVersion.getValue());
		if(u_a==null){
			res.add("status", -1);
			res.add("msg", "未知错误，获取失败");
			return true;
		}
		
		if (!StringUtils.isEmpty(machineAppVersion) && machineAppVersion.equals(u_a.getKey())) {
			res.add("status", -1);
			res.add("msg", "您已升级到最新版本");
			return true;
		}
		res.add("status", 1);
		res.add("msg", "成功");
		res.add("androidVersion", u_a.getValue());
		res.add("androidVersionNote", u_a.getNote());
		res.add("androidDownloadUrl", u_a.getUrl());
		res.add("androidSize", u_a.getSize());
		res.add("iosVersion", u_a.getValue());
		res.add("iosVersionNote", u_a.getNote());
		res.add("iosDownloadUrl", u_a.getUrl());
		res.add("iosSize", u_a.getSize());
		res.add("shopVersion", u_a.getValue());
		res.add("shopVersionNote", u_a.getNote());
		res.add("shopDownloadUrl", u_a.getUrl());
		res.add("shopSize", u_a.getSize());
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam objParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		return true;
	}
	
}