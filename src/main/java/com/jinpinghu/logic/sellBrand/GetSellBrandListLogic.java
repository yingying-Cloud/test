package com.jinpinghu.logic.sellBrand;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.ObjectUtils.Null;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbBrandSaleDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResBrandFileDao;
import com.jinpinghu.db.dao.TbResSellBrandFileDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbSellBrandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.GetBrandListParam;
import com.jinpinghu.logic.sellBrand.param.GetSellBrandListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetSellBrandListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetBrandListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetSellBrandListParam myParam=(GetSellBrandListParam)GetBrandListParam;
		String productName=myParam.getProductName();
		String status=myParam.getStatus();
		String enterpriseId=myParam.getEnterpriseId();
		Integer type = StringUtil.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		Integer upStatus = StringUtils.isNullOrEmpty(myParam.getUpStatus())?null:Integer.valueOf(myParam.getUpStatus());

		TbSellBrandDao tbBrandDao=new TbSellBrandDao(em);
		TbResSellBrandFileDao tbResBrandFileDao=new TbResSellBrandFileDao(em);
		TbResUserRoleDao tbResUserRoleDao=new TbResUserRoleDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Integer count=tbBrandDao.getSellBrandListCount(productName, enterpriseId, status,type,upStatus);
		if(count!=null&&count>0){
			List<Object[]> list=tbBrandDao.getSellBrandList(productName, enterpriseId, status, nowPage, pageCount,type,upStatus);
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> file;
			List<Object[]> fileList=null;
			Map<String,Object> map,fileMap;
			for(Object[] o:list){
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
				map.put("enterpriseId", o[10]);
				map.put("number", (o[11] == null || "".equals(o[11])) ? "" : new BigDecimal(o[11].toString()));
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
				result.add(map);
			}

			res.add("status", 1)
			.add("allCounts", count)
			.add("result", result)
			.add("msg", "操作成功！");
			return true;
		}

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
