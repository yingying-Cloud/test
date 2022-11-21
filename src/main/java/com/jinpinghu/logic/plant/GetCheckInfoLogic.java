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
import com.jinpinghu.logic.plant.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetCheckInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		
		TbResWorkCheckFileDao tbResWorkCheckFileDao=new TbResWorkCheckFileDao(em);
		TbWorkCheckDao tbWorkCheckDao=new TbWorkCheckDao(em);
		
		Object[] o=tbWorkCheckDao.getCheckInfo(id);
		
		List<Map<String,Object>> file;
		List<Object[]> fileList=null;
		Map<String,Object> map,fileMap;
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
		
		res.add("status", 1)
		.add("result", map)
		.add("msg", "操作成功！");
		return true;
	}
}
