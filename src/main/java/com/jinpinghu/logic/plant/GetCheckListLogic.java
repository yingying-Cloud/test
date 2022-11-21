package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbResWorkCheckFileDao;
import com.jinpinghu.db.dao.TbWorkCheckDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.GetCheckListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetCheckListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetCheckListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetCheckListParam myParam=(GetCheckListParam)GetCheckListParam;
		String workSn=myParam.getWorkSn();
		String startTime=myParam.getStartTime();
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String endTime=myParam.getEndTime();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		

		TbResWorkCheckFileDao tbResWorkCheckFileDao=new TbResWorkCheckFileDao(em);
		TbWorkCheckDao tbWorkCheckDao=new TbWorkCheckDao(em);
		
		Integer count=tbWorkCheckDao.getCheckListCount(enterpriseId, workSn, startTime, endTime);
		List<Object[]> list=tbWorkCheckDao.getCheckList(enterpriseId, workSn, startTime, endTime, nowPage, pageCount);
		
		if(list!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> file;
			List<Object[]> fileList=null;
			Map<String,Object> map,fileMap;
			for(Object[] o:list){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("workId", o[1]);
				map.put("checkTime", DateTimeUtil.formatTime((Date)o[2]));
				map.put("people", o[3]);
				map.put("unit", o[4]);
				map.put("content", o[5]);
				map.put("inputTime", DateTimeUtil.formatTime((Date)o[6]));
				map.put("workSn", o[7]);
				map.put("enterpriseName", o[8]);
				fileList=tbResWorkCheckFileDao.findFileById(Integer.valueOf(o[0].toString()));
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
