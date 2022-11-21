package com.jinpinghu.logic.advertisement;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbAdvertisement;
import com.jinpinghu.db.dao.TbAdvertisementDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.advertisement.param.DelAdvertisementParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelAdvertisementLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DelAdvertisementParam myParam = (DelAdvertisementParam)logicParam;
		String ids= myParam.getId();
		TbAdvertisementDao taDao = new TbAdvertisementDao(em);
		TbAdvertisement ta = null;
		Integer count = 0;
		if(!StringUtils.isNullOrEmpty(ids)){
			String[] id = ids.split(",");
			for(String i:id){
				ta = taDao.findById(Integer.valueOf(i));
				if(ta!=null){
					ta.setDelFlag(1);
					taDao.update(ta);
					count++;
				}
			}
		}
		res.add("msg", "成功删除"+count+"个！");
		res.add("status", 1);
		return true;
	}
}
