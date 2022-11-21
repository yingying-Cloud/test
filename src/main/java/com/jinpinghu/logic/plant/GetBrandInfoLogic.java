package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResBrandFile;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResBrandFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetBrandInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());

		TbBrandDao tbBrandDao=new TbBrandDao(em);
		TbResBrandFileDao tbResBrandFileDao=new TbResBrandFileDao(em);
		
		Object[] o=tbBrandDao.getBrandInfo(id);
		List<Map<String,Object>> file;
		List<Object[]> fileList=null;
		Map<String,Object> map,fileMap;
		
		map=new HashMap<String,Object>();
		map.put("id", o[0]);
		map.put("enterpriseName", o[1]);
		map.put("productName", o[2]);
		map.put("model", o[3]);
		map.put("registeredTrademark", o[4]);
		map.put("authorizationCategory", o[5]);
		map.put("inputTime", DateTimeUtil.formatTime2((Date)o[6]));
		map.put("status", o[7]);
		map.put("type", o[8]);
		map.put("price", o[9]);
		map.put("unit", o[10]);
		map.put("spec", o[11]);
		map.put("describe", o[12]);
		map.put("enterpriseName", o[13]);
		fileList=tbResBrandFileDao.findFileById(Integer.valueOf(o[0].toString()));
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
