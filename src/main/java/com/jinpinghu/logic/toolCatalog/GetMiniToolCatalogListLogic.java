package com.jinpinghu.logic.toolCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.GetMiniToolCatalogListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetMiniToolCatalogListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetMiniToolCatalogListParam myParam = (GetMiniToolCatalogListParam)logicParam;
		String toolName = myParam.getToolName();
		String productionUnits = myParam.getProductionUnits();
		String type = myParam.getType();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		String selectAll = myParam.getSelectAll();
		String uniformPrice = myParam.getUniformPrice();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		String productAttributes = myParam.getProductAttributes();
		
		TbToolCatalogDao toolCatalogDao = new TbToolCatalogDao(em);
		
		Integer maxCount = toolCatalogDao.getMiniToolCatalogListCount(toolName, productionUnits, type ,uniformPrice, productAttributes);
		
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
		
		resultList = toolCatalogDao.getMiniToolCatalogList(toolName, productionUnits, type, nowPage, pageCount,selectAll,
				permissionEnterpriseIdList,uniformPrice,productAttributes);
		
		Object[] nf = toolCatalogDao.getMiniToolCatalogListNyFlCount(toolName, productionUnits, type, uniformPrice,productAttributes);
		if(nf!=null) {
			res.add("count",nf[0]);
			res.add("ny",nf[2]);
			res.add("fl",nf[1]);
		}
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
