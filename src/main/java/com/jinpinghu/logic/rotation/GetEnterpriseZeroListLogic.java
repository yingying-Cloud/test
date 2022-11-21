package com.jinpinghu.logic.rotation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.rotation.param.GetToolZeroListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetEnterpriseZeroListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolZeroListParam myParam = (GetToolZeroListParam)logicParam;
		String ids = myParam.getIds();
		String enterpriseName = myParam.getEnterpriseName();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		List<String> id =null;
		if(!StringUtils.isNullOrEmpty(ids)) {
			id = Arrays.asList(ids.split(","));
		}
		TbEnterpriseZeroDao tbToolZeroDao = new TbEnterpriseZeroDao(em);
		
		Integer count = tbToolZeroDao.findAllZeroCount(id,enterpriseName);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		List<Map<String,Object>> list = tbToolZeroDao.findAllZero(id,enterpriseName,nowPage,pageCount);
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "操作成功").add("allCounts", count);
		return true;
	}

}
