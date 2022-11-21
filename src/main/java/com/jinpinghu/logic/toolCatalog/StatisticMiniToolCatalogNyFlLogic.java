package com.jinpinghu.logic.toolCatalog;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.GetMiniToolCatalogListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticMiniToolCatalogNyFlLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetMiniToolCatalogListParam myParam = (GetMiniToolCatalogListParam)logicParam;
		String toolName = myParam.getToolName();
		String productionUnits = myParam.getProductionUnits();
		String type = myParam.getType();
		String selectAll = myParam.getSelectAll();
		String uniformPrice = myParam.getUniformPrice();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		String productAttributes = myParam.getProductAttributes();
		
		TbToolCatalogDao toolCatalogDao = new TbToolCatalogDao(em);
		
		Object[] nf = toolCatalogDao.getMiniToolCatalogListNyFlCount(toolName, productionUnits, type, uniformPrice,productAttributes);
		if(nf!=null) {
			res.add("count",nf[0]);
			res.add("ny",nf[2]);
			res.add("fl",nf[1]);
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
