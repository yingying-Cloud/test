package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbEnterpriseUserinfoDao;
import com.jinpinghu.db.dao.TbResEnterpriseUserinfoFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.GetEnterpriseUserinfoListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetEnterpriseUserinfoListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetEnterpriseUserinfoListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseUserinfoListParam myParam=(GetEnterpriseUserinfoListParam)GetEnterpriseUserinfoListParam;
		String name=myParam.getName();
		String enterpriseId=myParam.getEnterpriseId();
		String type = myParam.getType();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String sex = myParam.getSex();
		TbResEnterpriseUserinfoFileDao tbResEnterpriseUserinfoFileDao=new TbResEnterpriseUserinfoFileDao(em);
		TbEnterpriseUserinfoDao tbEnterpriseUserinfoDao=new TbEnterpriseUserinfoDao(em);
		
		Integer count=tbEnterpriseUserinfoDao.getEnterpriseUserinfoListCount(name, enterpriseId,type,sex);
		List<Object[]> list=tbEnterpriseUserinfoDao.getEnterpriseUserinfoList(name, enterpriseId, nowPage, pageCount,type,sex);
		
		if(list!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> file;
			List<Object[]> fileList=null;
			Map<String,Object> map,fileMap;
			for(Object[] o:list){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("enterpriseName", o[1]);
				map.put("name", o[2]);
				map.put("idCard", o[3]);
				map.put("mobile", o[4]);
				map.put("type", o[5]);
				map.put("address", o[6]);
				map.put("sex", o[7]);
				map.put("inputTime", DateTimeUtil.formatTime2((Date)o[8]));
				map.put("typeName", o[9]);
				map.put("major", o[10]);
				map.put("education", o[11]);
				map.put("title", o[12]);
				map.put("company", o[13]);
				fileList=tbResEnterpriseUserinfoFileDao.findFileById(Integer.valueOf(o[0].toString()));
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
