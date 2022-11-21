package com.jinpinghu.logic.sc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.TbSupplyReleaseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.ListUnionSupplyAndBuyParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ListUnionSupplyAndBuyLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ListUnionSupplyAndBuyParam myParam = (ListUnionSupplyAndBuyParam)logicParam;
		String dscd = myParam.getDscd();
		String type = myParam.getType();
		String releaseType = myParam.getReleaseType();
		String name  = myParam.getName();
		String startPrice = myParam.getStartPrice();
		String endPrice = myParam.getEndPrice();
		String orderby = myParam.getOrderby();
		String sortDirection = myParam.getSortDirection();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage()) ? null : Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount()) ? null : Integer.valueOf(myParam.getPageCount());
		Integer loginEnterpriseId = null;
		if (loginUser != null) {
			loginEnterpriseId = EnterpriseDataPermissionUtil.getLoginEnterpriseId(loginUser.getId(), em);
		}
		
		TbSupplyReleaseDao supplyReleaseDao = new TbSupplyReleaseDao(em);
		Integer maxCount = supplyReleaseDao.listUnionSupplyAndBuyCount(dscd, type, name, startPrice, endPrice, releaseType,loginEnterpriseId);
		
		List<Map<String, Object>> resultList = new ArrayList<>(0);
		
		if(maxCount==null||maxCount==0){
			res.add("allCounts",0);
			res.add("maxPage",0);
			res.add("result", resultList);
			res.add("status", 1);
			res.add("msg", "无记录");
			return true;
		}
		int maxPage = 1;
		if(pageCount != null) {
			maxPage = maxCount/pageCount;
			if(maxCount%pageCount!=0){
				maxPage++;
			}
			if (pageCount * nowPage >= (maxCount + pageCount) && maxPage != 0) {
				nowPage = maxPage;
				res.add("allCounts",maxCount);
				res.add("maxPage",maxPage);
				res.add("result", resultList);
				res.add("status", 1);
				res.add("msg", "该页无记录");
				return true;
			}else if(maxPage == 0){
				nowPage = 1;
			}
		}
		
		resultList = supplyReleaseDao.listUnionSupplyAndBuy(dscd, type, name, startPrice, endPrice, releaseType, nowPage, pageCount,
				loginEnterpriseId,orderby,sortDirection);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
