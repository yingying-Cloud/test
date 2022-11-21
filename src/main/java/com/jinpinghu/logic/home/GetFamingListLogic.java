package com.jinpinghu.logic.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbResCropFarmingTool;
import com.jinpinghu.db.dao.TbCropFarmingDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.home.param.GetFarmingListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;

public class GetFamingListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetFarmingListParam myParam = (GetFarmingListParam)logicParam;
		String workSn = myParam.getWorkSn();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		Integer workId = myParam.getWorkId();
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		List<Map<String,Object>> tresult=new ArrayList<Map<String,Object>>();
		Map<String, Object> tmap = new HashMap<String,Object>();
		TbCropFarmingDao farmingDao = new TbCropFarmingDao(em);
		TbResCropFarmingToolDao toolDao = new TbResCropFarmingToolDao(em);
		TbResCropFarmingFileDao tbResCropFarmingFileDao=new TbResCropFarmingFileDao(em);
		List<String> timeList = farmingDao.getFarmingTimeList(workSn, startTime, endTime,enterpriseId,nowPage,pageCount,workId);
		if(timeList!=null) {
			for (int i = 0; i < timeList.size(); i++) {
				tmap = new HashMap<String, Object>();
				String time = timeList.get(i);
				tmap.put("time", time);
				List<Map<String, Object>> list=farmingDao.getFarmingList(workSn, time,enterpriseId,workId);
				if(list!=null){
//					List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
//					List<Map<String,Object>> file;
//					List<Object[]> fileList=null;
//					Map<String,Object> map,fileMap;
					for(Map<String, Object> map:list){
						if (map!=null){
				            if(map.get("file")!=null&&map.get("file")!="") {
				                JSONArray imageJson = JSONArray.fromObject(map.get("file"));
				                map.replace("file", imageJson);
				            }else{
				                map.remove("file");
				            }
//						map=new HashMap<String,Object>();
//						map.put("id", o[0]);
//						map.put("workSn", o[1]);
//						map.put("enterpriseName", o[2]);
//						map.put("describe", o[3]);
//						map.put("addPeople", o[4]);
//						map.put("inputTime", DateTimeUtil.formatTime((Date)o[5]));
//						map.put("workId", o[6]);
//						map.put("productName", o[7]);
//						fileList=tbResCropFarmingFileDao.findFileById(Integer.valueOf(o[0].toString()));
//						if(fileList!=null){
//							file=new ArrayList<Map<String,Object>>();
//							for(Object[] ob:fileList){
//								fileMap=new HashMap<String,Object>();
//								fileMap.put("id", ob[0]);
//								fileMap.put("fileName", ob[1]);
//								fileMap.put("fileSize", ob[2]);
//								fileMap.put("fileType", ob[3]);
//								fileMap.put("fileUrl", ob[4]);
//								file.add(fileMap);
//							}
//							map.put("file", file);
//						}
//						result.add(map);
							}
						}
					}
				tmap.put("farming", list);
				List<Map<String,Object>> toolResult = toolDao.findByWorkIdTime2(workSn, time,workId);
//				if(cropFarmingTools != null) {
//					for (int j = 0; j < cropFarmingTools.size(); j++) {
//						TbResCropFarmingTool cropFarmingTool = cropFarmingTools.get(j);
//						map = new HashMap<String,Object>();
//						map.put("addPeople", cropFarmingTool.getAddPeople());
//						map.put("toolId", cropFarmingTool.getToolId());
//						map.put("toolName", cropFarmingTool.getToolName());
//						map.put("toolType", cropFarmingTool.getTool);
//						map.put("specification", cropFarmingTool.getSpecification());
//						map.put("unit", cropFarmingTool.getUnit());
//						map.put("num", cropFarmingTool.getNum());
//						map.put("inputTime", DateTimeUtil.formatSelf(cropFarmingTool.getInputTime(),"yyyy-MM-dd HH:mm:ss"));
//						toolResult.add(map);
//					}
//				}
				tmap.put("tool", toolResult == null ? new ArrayList<>() : toolResult);
				tresult.add(tmap);
			}
		}
		
		res.add("status", 1)
		.add("result", tresult)
		.add("msg", "操作成功！");
		return true;
	}

}
