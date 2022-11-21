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
import com.jinpinghu.db.bean.TbWork;
import com.jinpinghu.db.dao.TbCropFarmingDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingToolDao;
import com.jinpinghu.db.dao.TbWorkChildDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.home.param.GetFarmingListParam;
import com.jinpinghu.logic.home.param.GetWorkFarmingListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetWorkFamingListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetWorkFarmingListParam myParam = (GetWorkFarmingListParam)logicParam;
		String workSn = myParam.getWorkSn();
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		
		TbCropFarmingDao farmingDao = new TbCropFarmingDao(em);
		TbResCropFarmingFileDao tbResCropFarmingFileDao=new TbResCropFarmingFileDao(em);
		TbResCropFarmingToolDao toolDao = new TbResCropFarmingToolDao(em);
		TbWorkDao workDao = new TbWorkDao(em);
		JSONArray ja = new JSONArray();
		TbWorkChildDao workChildDao = new TbWorkChildDao(em);
		List<Object[]> childListTime = workChildDao.getWorkChildListTime(workSn,enterpriseId);
		if(childListTime!=null) {
			for(Object[] ob:childListTime) {
				JSONObject joW = new JSONObject();
				joW.put("name", ob[2]);
				joW.put("startTime", DateTimeUtil.formatTime((Date)ob[3]));
				joW.put("endTime", DateTimeUtil.formatTime((Date)ob[4]));
				joW.put("workSn",ob[5] );
				Integer imageCount = workChildDao.getFarmingFileCountByWorkSn(workSn, DateTimeUtil.formatTime((Date)ob[3]), DateTimeUtil.formatTime((Date)ob[4]));
				joW.put("imageCount",imageCount );
				List<Map<String,Object>> tresult=new ArrayList<Map<String,Object>>();
				Map<String, Object> tmap = new HashMap<String,Object>();
				List<String> timeList = farmingDao.getFarmingTimeList(workSn, DateTimeUtil.formatTime((Date)ob[3]),DateTimeUtil.formatTime((Date)ob[4]),enterpriseId,null,null,null);
				if(timeList!=null) {
					for (int i = 0; i < timeList.size(); i++) {
						tmap = new HashMap<String, Object>();
						String time = timeList.get(i);
						tmap.put("time", time);
						List<Map<String, Object>> list=farmingDao.getFarmingList(workSn, time,enterpriseId,null);
						if(list!=null){
//							List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
//							List<Map<String,Object>> file;
//							List<Object[]> fileList=null;
//							Map<String,Object> map,fileMap;
							for(Map<String, Object> map:list){
								if (map!=null){
						            if(map.get("file")!=null&&map.get("file")!="") {
						                JSONArray imageJson = JSONArray.fromObject(map.get("file"));
						                map.replace("file", imageJson);
						            }else{
						                map.remove("file");
						            }
//								map=new HashMap<String,Object>();
//								map.put("id", o[0]);
//								map.put("workSn", o[1]);
//								map.put("enterpriseName", o[2]);
//								map.put("describe", o[3]);
//								map.put("addPeople", o[4]);
//								map.put("inputTime", DateTimeUtil.formatTime((Date)o[5]));
//								map.put("workId", o[6]);
//								map.put("productName", o[7]);
//								fileList=tbResCropFarmingFileDao.findFileById(Integer.valueOf(o[0].toString()));
//								if(fileList!=null){
//									file=new ArrayList<Map<String,Object>>();
//									for(Object[] ob:fileList){
//										fileMap=new HashMap<String,Object>();
//										fileMap.put("id", ob[0]);
//										fileMap.put("fileName", ob[1]);
//										fileMap.put("fileSize", ob[2]);
//										fileMap.put("fileType", ob[3]);
//										fileMap.put("fileUrl", ob[4]);
//										file.add(fileMap);
//									}
//									map.put("file", file);
//								}
//								result.add(map);
									}
								}
							}
						tmap.put("farming", list);
						List<Map<String,Object>> toolResult =new ArrayList<Map<String,Object>>();
						List<TbResCropFarmingTool> cropFarmingTools = toolDao.findByWorkIdTime(workSn, time);
						if(cropFarmingTools != null) {
							for (int j = 0; j < cropFarmingTools.size(); j++) {
								TbResCropFarmingTool cropFarmingTool = cropFarmingTools.get(j);
								Map<String, Object> toolMap = new HashMap<String,Object>();
								toolMap.put("addPeople", cropFarmingTool.getAddPeople());
								toolMap.put("toolId", cropFarmingTool.getToolId());
								toolMap.put("toolName", cropFarmingTool.getToolName());
								toolMap.put("specification", cropFarmingTool.getSpecification());
								toolMap.put("unit", cropFarmingTool.getUnit());
								toolMap.put("num", cropFarmingTool.getNum());
								toolMap.put("inputTime", DateTimeUtil.formatSelf(cropFarmingTool.getInputTime(),"yyyy-MM-dd HH:mm:ss"));
								toolResult.add(toolMap);
							}
						}
						tmap.put("tool", toolResult);
						tresult.add(tmap);
					}
					joW.put("info", tresult);
				}
				ja.add(joW);
			}
		}
		TbWork work = workDao.findByWorkSn(workSn);
		if(work != null) {
			res.add("purchaseSource", StringUtils.trimToEmpty(work.getPurchaseSource()));
			res.add("purchasePeople", StringUtils.trimToEmpty(work.getPurchasePeople()));
			res.add("purchaseTime", DateTimeUtil.formatTime(work.getPurchaseTime()));
		}else {
			res.add("purchaseSource", "");
			res.add("purchasePeople", "");
			res.add("purchaseTime", "");
		}
		
		res.add("status", 1)
		.add("result", ja)
		.add("msg", "操作成功！");
		return true;
	}

}
