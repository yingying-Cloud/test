package com.jinpinghu.logic.brandSale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbBrandSaleDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResBrandFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandSale.param.GetBrandSaleInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetBrandSaleInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetBrandSaleInfoParam myParam = (GetBrandSaleInfoParam)logicParam;
		Integer id =StringUtils.isNullOrEmpty( myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbBrandSaleDao tbBrandSaleDao = new TbBrandSaleDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		TbResBrandFileDao tbResBrandFileDao=new TbResBrandFileDao(em);
		Map<String, Object> map = tbBrandSaleDao.findMapById(id);
		List<Object[]> fileList=tbResBrandFileDao.findFileById(Integer.valueOf(map.get("brandId").toString()));
		if(fileList!=null){
			List<Map<String,Object>>  file=new ArrayList<Map<String,Object>>();
			for(Object[] ob:fileList){
				Map<String,Object> fileMap=new HashMap<String,Object>();
				fileMap.put("id", ob[0]);
				fileMap.put("fileName", ob[1]);
				fileMap.put("fileSize", ob[2]);
				fileMap.put("fileType", ob[3]);
				fileMap.put("fileUrl", ob[4]);
				file.add(fileMap);
			}
			map.put("file", file);
		}
		res.add("result", map);
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("id", id);
		return true;
	}
	
}
