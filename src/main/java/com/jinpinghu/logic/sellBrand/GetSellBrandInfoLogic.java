package com.jinpinghu.logic.sellBrand;

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
import com.jinpinghu.db.dao.TbResSellBrandFileDao;
import com.jinpinghu.db.dao.TbSellBrandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetSellBrandInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());

		TbSellBrandDao tbBrandDao=new TbSellBrandDao(em);
		TbResSellBrandFileDao tbResBrandFileDao=new TbResSellBrandFileDao(em);
		
		Object[] o=tbBrandDao.getSellBrandInfo(id);
		List<Map<String,Object>> file;
		List<Object[]> fileList=null;
		Map<String,Object> map,fileMap;
		
		map=new HashMap<String,Object>();
		map.put("id", o[0]);
		map.put("enterpriseName", o[1]);
		map.put("productName", o[2]);
		map.put("inputTime", DateTimeUtil.formatTime2((Date)o[3]));
		map.put("status", o[4]);
		map.put("type", o[5]);
		map.put("price", o[6]);
		map.put("unit", o[7]);
		map.put("spec", o[8]);
		map.put("describe", o[9]);
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
