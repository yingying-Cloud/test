package com.jinpinghu.logic.rotation;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbRotationTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.rotation.param.GetRotationTimeListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetRotationTimeListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetRotationTimeListParam myParam = (GetRotationTimeListParam)logicParam;
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		TbRotationTimeDao tbRotationTimeDao = new TbRotationTimeDao(em);
		Integer count = tbRotationTimeDao.findAllCount();
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String,Object>> list = tbRotationTimeDao.findAll(nowPage, pageCount);
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "操作成功").add("allCounts", count);
		return true;
	}

}
