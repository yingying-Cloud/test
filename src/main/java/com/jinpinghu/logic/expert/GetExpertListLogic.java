package com.jinpinghu.logic.expert;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbExpertDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.expert.param.GetExpertListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetExpertListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetExpertListParam myParam = (GetExpertListParam)logicParam;
		String name=myParam.getName();
		String status = myParam.getStatus();
		String type = myParam.getType();
		String lowIntegral = myParam.getLowIntegral();
		String highIntegral = myParam.getHighIntegral();
		String adnm = myParam.getAdnm();
		String productTeam = myParam.getProductTeam();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String orderby = myParam.getOrderby();
		String sortDirection = myParam.getSortDirection();
		
		TbExpertDao expertDao = new TbExpertDao(em);
		Integer count = expertDao.findByAllCount(name, status,type,lowIntegral,highIntegral,adnm,productTeam);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = expertDao.findByAll(name, status,type,lowIntegral,highIntegral,adnm,productTeam,nowPage,pageCount,orderby,sortDirection);
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", list).add("allCounts", count);
		return true;
	}
}
