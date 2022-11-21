package com.jinpinghu.logic.offlineStore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.offlineStore.param.GetToolListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetToolListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetToolListParam myParam = (GetToolListParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		String type = myParam.getType();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		String dnm = myParam.getDnm();
	
		TbToolDao toolDao = new TbToolDao(em);
		
		List<Map<String, Object>> resultList = toolDao.getOfflineStoreToolList(enterpriseId, type, nowPage, pageCount,dnm);
		
		if (resultList != null) {
			for (Map<String, Object> map : resultList) {
				BigDecimal price = BigDecimal.ZERO;
				try {
					price = new BigDecimal(map.get("price").toString());
				} catch (Exception e) {
					// TODO: handle exception
				}
				map.put("price", price.setScale(2,BigDecimal.ROUND_HALF_UP));
				map.put("isWeight", false);
				map.put("weight", 0.0f);
				map.put("gPrice", 0.0f);
				map.put("discount",10);
				map.put("bargain",0.0f);
				map.put("special",0.0f);
				map.put("preferrntial", new ArrayList<>(0));
				map.put("preferentialCommon", new ArrayList<>(0));
			}
		}
		
		Integer count = toolDao.getOfflineStoreToolListCount(enterpriseId, type,dnm);
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("result", resultList);
		res.add("count", count);
		return true;
	}

}
