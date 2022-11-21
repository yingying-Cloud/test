package com.jinpinghu.logic.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.GetToolList2Param;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetToolList2Logic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetToolList2Param myParam = (GetToolList2Param)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		String toolIds = myParam.getToolIds();
		String toolName = myParam.getToolName();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		Integer type = myParam.getType();
		
		List<Integer> toolIdList = null;
		if(!StringUtils.isEmpty(toolIds)) {
			String[] toolIdArray = toolIds.split(",");
			toolIdList = new ArrayList<Integer>();
			for(int i=0;i<toolIdArray.length;i++)
				toolIdList.add(Integer.valueOf(toolIdArray[i]));
		}
		
		TbToolDao toolDao = new TbToolDao(em);
		Integer count = toolDao.findNotAddToolListCount(enterpriseId, toolIdList, toolName, type);
		List<Map<String, Object>> list =null;
		if(count != null)
			list = toolDao.findNotAddToolList(enterpriseId, toolIdList, toolName, type, nowPage, pageCount);
		
		res.add("status", 1).add("msg", "操作成功").add("result", list == null ? new ArrayList<>() : list).add("allCounts", count);
		return true;
	}

}
