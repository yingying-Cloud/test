package com.jinpinghu.logic.mechine;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbMechineDao;
import com.jinpinghu.db.dao.TbResMechineFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.mechine.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetMechineInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());

		TbMechineDao tbMechineDao=new TbMechineDao(em);
		TbResMechineFileDao tbResMechineFileDao=new TbResMechineFileDao(em);
		
		Object[] o=tbMechineDao.getMechineInfo(id);
		List<Object[]> fileList=null;
		List<Map<String,Object>> file=new ArrayList<Map<String,Object>>();
		Map<String,Object> map,fileMap;
		map= null;
		if(o!=null){
			map=new HashMap<String,Object>();
			map.put("id", o[0]);
			map.put("enterpriseName", o[1]);
			map.put("name", o[2]);
			map.put("model", o[3]);
			map.put("type", o[4]);
			map.put("describe", o[5]);
			map.put("inputTime", DateTimeUtil.formatTime2((Date)o[6]));
			map.put("brand", o[7]);
//			map.put("typeName", o[8]);
			fileList=tbResMechineFileDao.findFileById(Integer.valueOf(o[0].toString()));
			if(fileList!=null){
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
		}
		res.add("status", 1)
		.add("result", map)
		.add("msg", "操作成功！");
		return true;
	}
}
