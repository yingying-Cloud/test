package com.jinpinghu.logic.work;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbCropFarmingDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantWarehouseDao;
import com.jinpinghu.db.dao.TbResBrandFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingToolDao;
import com.jinpinghu.db.dao.TbResWorkCheckFileDao;
import com.jinpinghu.db.dao.TbWorkCheckDao;
import com.jinpinghu.db.dao.TbWorkChildDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.work.param.GetAllWorkInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetAllWorkInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAllWorkInfoParam myParam = (GetAllWorkInfoParam)logicParam;
		Integer id = StringUtils.isNullOrEmpty(myParam.getWorkId())?null:Integer.valueOf(myParam.getWorkId());
		String workSn=myParam.getWorkSn();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		
		TbEnterpriseDao tbEnterpriseDao = new TbEnterpriseDao(em);
		TbWorkDao tbWorkDao=new TbWorkDao(em);
		TbWorkChildDao tbWorkChildDao=new TbWorkChildDao(em);
		TbResCropFarmingToolDao tbResCropFarmingToolDao = new TbResCropFarmingToolDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		TbCropFarmingDao tbCropFarmingDao = new TbCropFarmingDao(em);
		TbResCropFarmingFileDao tbResCropFarmingFileDao = new TbResCropFarmingFileDao(em);
		TbWorkCheckDao tbWorkCheckDao = new TbWorkCheckDao(em);
		TbResWorkCheckFileDao tbResWorkCheckFileDao = new TbResWorkCheckFileDao(em);
		
		List<Map<String,Object>> child;
		List<Object[]> childList=null;
		Map<String,Object> map,childMap;

		Object[] o=tbWorkDao.getWorkInfo(id,workSn);

		map=new HashMap<String,Object>();
		
		JSONObject workJo = new JSONObject();
		workJo.put("id", o[0]);
		workJo.put("addPeople", o[1]);
		workJo.put("workName", o[2]);
		workJo.put("workSn", o[3]);
		workJo.put("landBlockSn", o[4]);
		workJo.put("area", o[5]);
		workJo.put("startTime", DateTimeUtil.formatTime((Date)o[7]));
		workJo.put("endTime", DateTimeUtil.formatTime((Date)o[8]));
		workJo.put("recoveryTime", DateTimeUtil.formatTime((Date)o[9]));
		workJo.put("expectedProduction", o[10]);
		workJo.put("inputTime", DateTimeUtil.formatTime2((Date)o[11]));
		
		map.put("purchaseSource", o[13]);
		map.put("purchasePeople", o[14]);
		map.put("purchaseTime", o[15]);
		
//		workJo.put("crop",o[12]);
		
		
		TbBrandDao tbBrandDao=new TbBrandDao(em);
		TbResBrandFileDao tbResBrandFileDao=new TbResBrandFileDao(em);
		if(o[6]!=null) {
			Object[] crop=tbBrandDao.getBrandInfo(Integer.valueOf(o[6].toString()));
			if(crop!=null) {
				List<Map<String,Object>> cropfile;
				List<Object[]> cropfileList=null;
				Map<String,Object> cropJo,fileMap;
				cropJo=new HashMap<String,Object>();
				cropJo.put("id", crop[0]);
				cropJo.put("enterpriseName", crop[1]);
				cropJo.put("productName", crop[2]);
				cropJo.put("model", crop[3]);
				cropJo.put("registeredTrademark", crop[4]);
				cropJo.put("authorizationCategory", crop[5]);
				cropJo.put("inputTime", DateTimeUtil.formatTime2((Date)crop[6]));
				cropJo.put("status", crop[7]);
				cropJo.put("type", crop[8]);
				cropJo.put("price", crop[9]);
				cropJo.put("unit", crop[10]);
				cropJo.put("spec", crop[11]);
				cropJo.put("describe", crop[12]);
				cropJo.put("enterpriseName", crop[13]);
				cropfileList=tbResBrandFileDao.findFileById(Integer.valueOf(crop[0].toString()));
				if(cropfileList!=null){
					cropfile=new ArrayList<Map<String,Object>>();
					for(Object[] ob:cropfileList){
						fileMap=new HashMap<String,Object>();
						fileMap.put("id", ob[0]);
						fileMap.put("fileName", ob[1]);
						fileMap.put("fileSize", ob[2]);
						fileMap.put("fileType", ob[3]);
						fileMap.put("fileUrl", ob[4]);
						cropfile.add(fileMap);
					}
					cropJo.put("file", cropfile);
				}
				map.put("crop", cropJo);
			}
		}
		if(enterpriseId!=null) {
			Map<String, Object> enterpriseJo = tbEnterpriseDao.findAllById(enterpriseId);
			map.put("enterprise", enterpriseJo);
		}
//		JSONObject enterpriseJo = new JSONObject();
//		enterpriseJo.put("enterpriseName", o[1]);
//		enterpriseJo.put("enterpriseCreditCode",o[13]);
//		enterpriseJo.put("enterpriseLegalPerson",o[14]);
//		enterpriseJo.put("enterpriseLegalPersonIdcard",o[15]);
//		enterpriseJo.put("enterpriseLinkPeople",o[16]);
//		enterpriseJo.put("enterpriseLinkMobile",o[17]);
//		enterpriseJo.put("enterpriseAddress",o[18]);
//		enterpriseJo.put("enterpriseType",o[19]);
//		enterpriseJo.put("status",o[20]);
//		enterpriseJo.put("x",o[21]);
//		enterpriseJo.put("y",o[22]);
//		enterpriseJo.put("plantScope",o[23]);
//		enterpriseJo.put("baseAddress",o[24]);
//		enterpriseJo.put("plantName",o[25]);
		
		
		if(enterpriseId!=null) {
			List<Map<String, Object>> tfs =tfDao.getListByEnterpriseId(enterpriseId);
			if(tfs!=null) {
				workJo.put("file", tfs);
			}
		}
		
		if(o[8]!=null&&new Date().before((Date)o[8])){
			workJo.put("status", "0");
		}else if(o[9]!=null&&new Date().before((Date)o[9])){
			workJo.put("status", "1");
		}else{
			workJo.put("status", "2");
		}
		childList=tbWorkChildDao.getWorkChildList(Integer.valueOf(o[0].toString()));
		if(childList!=null){
			child=new ArrayList<Map<String,Object>>();
			for(Object[] ob:childList){
				childMap=new HashMap<String,Object>();
				childMap.put("id", ob[0]);
//				childMap.put("workId", ob[1]);
				childMap.put("name", ob[2]);
				childMap.put("startTime", DateTimeUtil.formatTime((Date)ob[3]));
				childMap.put("endTime", DateTimeUtil.formatTime((Date)ob[4]));
				
				List<String> timeList = tbWorkChildDao.getTimeList(Integer.valueOf(o[0].toString()),  DateTimeUtil.formatTime((Date)ob[3]), DateTimeUtil.formatTime((Date)ob[4]));
				if(timeList!=null) {
					JSONArray ja = new JSONArray();
					for(String time:timeList) {
						JSONObject trackJa = new JSONObject();
						trackJa.put("time", time);
						List<Map<String, Object>> farminToolList = tbResCropFarmingToolDao.getFarminToolList(o[3].toString(),time);
						if(farminToolList!=null) {
							trackJa.put("farmingTool", farminToolList);
						}
						List<Map<String,Object>> farmingList = tbCropFarmingDao.getAllFarmingList(o[3].toString(),time);
						if(farmingList!=null) {
							for(Map<String, Object> farming:farmingList) {
								List<Object[]> fileList=tbResCropFarmingFileDao.findFileById(Integer.valueOf(farming.get("id").toString()));
								if(fileList!=null){
									List<Map<String,Object>> file=new ArrayList<Map<String,Object>>();
									for(Object[] obs:fileList){
										HashMap<String,Object> fileMap=new HashMap<String,Object>();
										fileMap.put("id", obs[0]);
										fileMap.put("fileName", obs[1]);
										fileMap.put("fileSize", obs[2]);
										fileMap.put("fileType", obs[3]);
										fileMap.put("fileUrl", obs[4]);
										file.add(fileMap);
									}
									farming.put("file", file);
								}
							}
							trackJa.put("farming", farmingList);
						}
						
						List<Map<String, Object>> checkList = tbWorkCheckDao.getAllCheckList(o[3].toString(),time);
						if(checkList!=null) {
							for(Map<String, Object> check:checkList) {
								List<Object[]> fileList=tbResWorkCheckFileDao.findFileById(Integer.valueOf(check.get("id").toString()));
								if(fileList!=null){
									List<Map<String,Object>> file=new ArrayList<Map<String,Object>>();
									for(Object[] obs:fileList){
										HashMap<String,Object> fileMap=new HashMap<String,Object>();
										fileMap.put("id", obs[0]);
										fileMap.put("fileName", obs[1]);
										fileMap.put("fileSize", obs[2]);
										fileMap.put("fileType", obs[3]);
										fileMap.put("fileUrl", obs[4]);
										file.add(fileMap);
									}
									check.put("file", file);
								}
							}
							trackJa.put("check", checkList);
						}
						ja.add(trackJa);
					}
					childMap.put("traceList", ja);
				}
				child.add(childMap);
			}
			workJo.put("child", child);
		}
		TbPlantWarehouseDao rdao=new TbPlantWarehouseDao(em);
		List<Map<String, Object>> inList = rdao.getBrandAllNumByRecordType(enterpriseId, 1, null, null,"","",o[3].toString());
		List<Map<String, Object>> outList = rdao.getBrandAllNumByRecordType(enterpriseId, 2, null, null,"","",o[3].toString());
		
		map.put("inRecord", inList);
		map.put("outRecord", outList);
		map.put("work", workJo);
		res.add("status", 1)
		.add("result", map)
		.add("msg", "操作成功！");
		
		
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
		}
}
