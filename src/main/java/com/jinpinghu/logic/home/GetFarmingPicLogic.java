package com.jinpinghu.logic.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbResCropFarmingFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.home.param.GetFarmingPicParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetFarmingPicLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetFarmingPicParam myParam = (GetFarmingPicParam)logicParam;
		String workSn = myParam.getWorkSn();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String name = myParam.getName();
		
		TbResCropFarmingFileDao tbResCropFarmingFarmingDao = new TbResCropFarmingFileDao(em);
		
		List<Object[]> files = null;
//		if("育苗期".equals(name))
//			files = tbResCropFarmingFarmingDao.getFarmingFileByWorkSn(workSn, null, null);
//		else
			files = tbResCropFarmingFarmingDao.getFarmingFileByWorkSn(workSn, startTime, endTime);
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap;
		if (files != null) {
			for (int i = 0; i < files.size(); i++) {
				Object[] file = files.get(i);
				resultMap = new HashMap<String,Object>();
//				resultMap.put("id", file.getId());
				resultMap.put("fileName", file[2]);
//				resultMap.put("fileSize", file.getFileSize());
				resultMap.put("fileType", file[1]);
				resultMap.put("fileUrl", file[0]);
				resultList.add(resultMap);
			}
		}
		
		res.add("status",1).add("msg", "操作成功").add("result", resultList);
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}

}
