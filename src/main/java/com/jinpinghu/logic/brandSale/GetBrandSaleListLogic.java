package com.jinpinghu.logic.brandSale;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbBrandSaleDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandSale.param.GetBrandSaleInfoParam;
import com.jinpinghu.logic.brandSale.param.GetBrandSaleListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetBrandSaleListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetBrandSaleListParam myParam = (GetBrandSaleListParam)logicParam;
		Integer enterprsieId =StringUtils.isNullOrEmpty( myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer status =StringUtils.isNullOrEmpty( myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		Integer nowPage =StringUtils.isNullOrEmpty( myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount =StringUtils.isNullOrEmpty( myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String name = myParam.getName();
		TbBrandSaleDao tbBrandSaleDao = new TbBrandSaleDao(em);
		Integer count = tbBrandSaleDao.findListCount(enterprsieId,status,name);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> map = tbBrandSaleDao.findList(enterprsieId,nowPage,pageCount,status,name);
		res.add("result", map);
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("allCounts", count);
		return true;
	}

}
