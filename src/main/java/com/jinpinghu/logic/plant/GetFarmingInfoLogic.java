package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbCropFarming;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResCropFarmingFile;
import com.jinpinghu.db.bean.TbWork;
import com.jinpinghu.db.dao.TbCropFarmingDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingFileDao;
import com.jinpinghu.db.dao.TbWorkChildDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetFarmingInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());

		TbCropFarmingDao tbCropFarmingDao=new TbCropFarmingDao(em);
		TbResCropFarmingFileDao tbResCropFarmingFileDao=new TbResCropFarmingFileDao(em);
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> file;
		List<Object[]> fileList=null;
		Map<String,Object> map,fileMap;

		Object[] o=tbCropFarmingDao.getFarmingInfo(id);
		map=new HashMap<String,Object>();
		map.put("id", o[0]);
		map.put("workSn", o[1]);
		map.put("enterpriseName", o[2]);
		map.put("describe",o[3]);
		map.put("addPeople", o[4]);
		map.put("inputTime", DateTimeUtil.formatTime((Date)o[5]));
		map.put("workId", o[6]);
		fileList=tbResCropFarmingFileDao.findFileById(id);
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

		res.add("status", 1)
		.add("result", map)
		.add("msg", "操作成功！");
		return true;
	}

}
