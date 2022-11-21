package com.jinpinghu.logic.agriculturalGreenhouses;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbAgriculturalGreenhouses;
import com.jinpinghu.db.dao.TbAgriculturalGreenhousesDao2;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.param.DelAgriculturalGreenhousesParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelAgriculturalGreenhousesLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelAgriculturalGreenhousesParam myParam = (DelAgriculturalGreenhousesParam)logicParam;
		String ids = myParam.getIds();
		
		TbAgriculturalGreenhousesDao2 tbAgriculturalGreenhousesDao2 = new TbAgriculturalGreenhousesDao2(em);
		if(!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbAgriculturalGreenhouses tbAgriculturalGreenhouses =tbAgriculturalGreenhousesDao2.findById(Integer.valueOf(id));
				if(tbAgriculturalGreenhouses!=null) {
					tbAgriculturalGreenhouses.setDelFlag(1);
					tbAgriculturalGreenhousesDao2.update(tbAgriculturalGreenhouses);
				}
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
}
