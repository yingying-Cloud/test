package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbCropFarmingDao;
import com.jinpinghu.db.dao.TbResCropFarmingFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.GetFarmingListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetFarmingListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetFarmingListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetFarmingListParam myParam=(GetFarmingListParam)GetFarmingListParam;
		String workSn=myParam.getWorkSn();
		String enterpriseId=myParam.getEnterpriseId();
		String endTime=myParam.getEndTime();
		String startTime=myParam.getStartTime();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		TbCropFarmingDao tbCropFarmingDao=new TbCropFarmingDao(em);
		TbResCropFarmingFileDao tbResCropFarmingFileDao=new TbResCropFarmingFileDao(em);
		
		Integer count=tbCropFarmingDao.getFarmingListCount(workSn, enterpriseId, startTime, endTime);
		List<Object[]> list=tbCropFarmingDao.getFarmingList(workSn, enterpriseId, startTime, endTime, nowPage, pageCount);
		
		if(list!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> file;
			List<Object[]> fileList=null;
			Map<String,Object> map,fileMap;
			for(Object[] o:list){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("workSn", o[1]);
				map.put("enterpriseName", o[2]);
				map.put("describe", o[3]);
				map.put("addPeople", o[4]);
				map.put("inputTime", DateTimeUtil.formatTime((Date)o[5]));
				map.put("workId", o[6]);
				map.put("productName", o[7]==null?"":o[7]);
				fileList=tbResCropFarmingFileDao.findFileById(Integer.valueOf(o[0].toString()));
				if(fileList!=null){
					file=new ArrayList<Map<String,Object>>();
					for(Object[] ob:fileList){
						fileMap=new HashMap<String,Object>();
						fileMap.put("id", ob[0]);
						fileMap.put("fileName", ob[1]);
						fileMap.put("fileSize", ob[2]);
						fileMap.put("fileType", ob[3]);
						fileMap.put("fileUrl", ob[4]);
						file.add(fileMap);
					}
					map.put("file", file);
				}
				result.add(map);
			}
			
			res.add("status", 1)
			.add("allCounts", count)
			.add("result", result)
			.add("msg", "操作成功！");
		}

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}
