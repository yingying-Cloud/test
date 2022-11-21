package com.jinpinghu.logic.toolCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.GetSellRecordByToolCatalogIdParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetSellRecordByToolCatalogIdLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetSellRecordByToolCatalogIdParam myParam = (GetSellRecordByToolCatalogIdParam)logicParam;
		String seller = myParam.getSeller();
		String buyer = myParam.getBuyer();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String code = myParam.getCode();
		Integer toolCatalogId = myParam.getToolCatalogId();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		Integer enterpriseId = myParam.getEnterpriseId();
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		
		TbToolCatalogDao toolCatalogDao = new TbToolCatalogDao(em);
		
		Integer maxCount = toolCatalogDao.getSellRecordCountByToolCatalogId(seller, buyer, startTime, endTime, toolCatalogId,
				code,enterpriseId,selectAll,permissionEnterpriseIdList);
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		
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
		
		resultList = toolCatalogDao.getSellRecordByToolCatalogId(seller, buyer, startTime, endTime, toolCatalogId, 
				nowPage, pageCount,code,enterpriseId,selectAll,permissionEnterpriseIdList);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
