package com.jinpinghu.logic.toolCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.logic.toolCatalog.param.GetToolCatalogList2Param;
import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetToolCatalogList2Logic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetToolCatalogList2Param myParam = (GetToolCatalogList2Param)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		String toolCatelogIds = myParam.getToolCatalogIds();
		
		List<Integer> toolCatelogIdList = null;
		if(!StringUtils.isEmpty(toolCatelogIds)) {
			String[] toolCatelogIdArray = toolCatelogIds.split(",");
			toolCatelogIdList = new ArrayList<Integer>();
			for(int i=0;i<toolCatelogIdArray.length;i++)
				toolCatelogIdList.add(Integer.valueOf(toolCatelogIdArray[i]));
		}
		
		TbToolCatalogDao toolCatelogDao = new TbToolCatalogDao(em);
		
		List<Map<String, Object>> list = toolCatelogDao.findNotAddToolCatalogList(enterpriseId, toolCatelogIdList);
		
		res.add("status", 1).add("msg", "操作成功").add("result", list == null ? new ArrayList<>() : list);
		return true;
	}

}
