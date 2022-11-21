package com.jinpinghu.logic.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbCropFarming;
import com.jinpinghu.db.bean.TbResCropFarmingTool;
import com.jinpinghu.db.bean.TbWork;
import com.jinpinghu.db.dao.TbCropFarmingDao;
import com.jinpinghu.db.dao.TbResCropFarmingFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingToolDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.home.param.GetFarmingInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetFarmingInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetFarmingInfoParam myParam = (GetFarmingInfoParam)logicParam;
		Integer workId = StringUtils.isEmpty(myParam.getWorkId()) ? null : Integer.valueOf(myParam.getWorkId());
		String time = myParam.getTime();
		
		
		TbWorkDao workDao = new TbWorkDao(em);
		
		TbWork work = workDao.findById(workId);
		
		if(work == null) {
			res.add("status", -1).add("msg", "当前任务不存在");
			return false;
		}
		
		TbCropFarmingDao cropFarmingDao = new TbCropFarmingDao(em);
		TbResCropFarmingToolDao cropFarmingToolDao = new TbResCropFarmingToolDao(em);
		TbResCropFarmingFileDao cropFarmingFileDao = new TbResCropFarmingFileDao(em);
		
		int startYear = Integer.valueOf(DateTimeUtil.formatSelf(work.getStartTime(),"yyyy"));
		int endYear = Integer.valueOf(DateTimeUtil.formatSelf(work.getEndTime(),"yyyy"));
		
		for(int i=startYear;i<=endYear;i++) {
			Date checkTime = DateTimeUtil.formatSelf(i+"-"+time, "yyyy-MM-dd HH:mm");
			if(checkTime.after(work.getStartTime()) && checkTime.before(work.getEndTime())) {
				time = DateTimeUtil.formatSelf(checkTime, "yyyy-MM-dd HH:mm");
				break;
			}
				
		}
		
		List<TbCropFarming> cropFarmings = cropFarmingDao.findByWorkIdTime(workId, time);
		List<TbResCropFarmingTool> cropFarmingTools = cropFarmingToolDao.findByWorkIdTime(workId, time);
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		List<Map<String, Object>> commonList = new ArrayList<Map<String, Object>>();
		Map<String, Object> commonMap;
		List<Map<String, Object>> fileResultList;
		Map<String, Object> fileMap;
		
		if(cropFarmings != null) {
			for (int i = 0; i < cropFarmings.size(); i++) {
				TbCropFarming cropFarming = cropFarmings.get(i);
				commonMap = new HashMap<String,Object>();
				commonMap.put("addPeople", cropFarming.getAddPeople());
				commonMap.put("describe", cropFarming.getDescribe());
				commonMap.put("inputTime", DateTimeUtil.formatSelf(cropFarming.getInputTime(),"yyyy-MM-dd HH:mm:ss"));
				List<Object[]> fileList = cropFarmingFileDao.findFileById(cropFarming.getId());
				if(fileList!=null){
					fileResultList=new ArrayList<Map<String,Object>>();
					for(Object[] ob:fileList){
						fileMap=new HashMap<String,Object>();
						fileMap.put("id", ob[0]);
						fileMap.put("fileName", ob[1]);
						fileMap.put("fileSize", ob[2]);
						fileMap.put("fileType", ob[3]);
						fileMap.put("fileUrl", ob[4]);
						fileResultList.add(fileMap);
					}
					commonMap.put("file", fileResultList);
				}
				commonList.add(commonMap);
			}
		}
		resultMap.put("farming", commonList);
		
		commonList = new ArrayList<Map<String, Object>>();
		if(cropFarmingTools != null) {
			for (int i = 0; i < cropFarmingTools.size(); i++) {
				TbResCropFarmingTool cropFarmingTool = cropFarmingTools.get(i);
				commonMap = new HashMap<String,Object>();
				commonMap.put("addPeople", cropFarmingTool.getAddPeople());
				commonMap.put("toolId", cropFarmingTool.getToolId());
				commonMap.put("toolName", cropFarmingTool.getToolName());
				commonMap.put("specification", cropFarmingTool.getSpecification());
				commonMap.put("unit", cropFarmingTool.getUnit());
				commonMap.put("num", cropFarmingTool.getNum());
				commonMap.put("inputTime", DateTimeUtil.formatSelf(cropFarmingTool.getInputTime(),"yyyy-MM-dd HH:mm:ss"));
				commonList.add(commonMap);
			}
		}
		resultMap.put("tool", commonList);
		
		
		res.add("status", 1).add("result", resultMap).add("msg", "操作成功");
		return false;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetFarmingInfoParam myParam = (GetFarmingInfoParam)logicParam;
		Integer workId = StringUtils.isEmpty(myParam.getWorkId()) ? null : Integer.valueOf(myParam.getWorkId());
		String time = myParam.getTime();
		
		if(workId == null || StringUtils.isEmpty(time)) {
			res.add("status", -1);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}

}
