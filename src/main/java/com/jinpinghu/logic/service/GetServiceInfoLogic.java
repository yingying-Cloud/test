package com.jinpinghu.logic.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbServiceDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.service.param.GetServiceInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetServiceInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetServiceInfoParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetServiceInfoParam myParam = (GetServiceInfoParam)GetServiceInfoParam;
		Integer id=Integer.valueOf(myParam.getId());
		
		TbServiceDao tbServiceDao=new TbServiceDao(em);
		
		Map<String,Object> map = tbServiceDao.findById(id);
		
		List<Map<String,Object>> fileList=tbServiceDao.getFileListByServiceId(id);
		if(fileList!=null){
			map.put("file", fileList);
		}

		res.add("status", 1)
		.add("result", map)
		.add("msg", "操作成功");
		
		return true;
	}
	 // 身份认证
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
		return true;
	}
}
