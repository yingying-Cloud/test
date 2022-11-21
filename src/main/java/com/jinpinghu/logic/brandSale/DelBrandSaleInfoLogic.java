package com.jinpinghu.logic.brandSale;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbBrandSale;
import com.jinpinghu.db.dao.TbBrandSaleDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandSale.param.DelBrandSaleParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelBrandSaleInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelBrandSaleParam myParam = (DelBrandSaleParam)logicParam;
		String id =myParam.getId();
		TbBrandSaleDao tbBrandSaleDao = new TbBrandSaleDao(em);
		if(!StringUtils.isNullOrEmpty(id)) {
		List<String> ids=Arrays.asList(id.split(","));
			for(String i:ids){
				TbBrandSale tbBrandSale=tbBrandSaleDao.findById(Integer.valueOf(i));
				tbBrandSale.setDelFlag(1);
				tbBrandSaleDao.update(tbBrandSale);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
