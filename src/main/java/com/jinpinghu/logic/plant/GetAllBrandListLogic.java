package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbBrandSaleDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResBrandFileDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.GetBrandListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAllBrandListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetBrandListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetBrandListParam myParam=(GetBrandListParam)GetBrandListParam;
		String productName=myParam.getProductName();
		String status=myParam.getStatus();
		String enterpriseId=myParam.getEnterpriseId();
		Integer type = StringUtil.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String isSale = myParam.getIsSale();
		Integer upStatus = StringUtils.isNullOrEmpty(myParam.getUpStatus())?0:Integer.valueOf(myParam.getUpStatus());

		TbBrandDao tbBrandDao=new TbBrandDao(em);
		TbResBrandFileDao tbResBrandFileDao=new TbResBrandFileDao(em);
		TbResUserRoleDao tbResUserRoleDao=new TbResUserRoleDao(em);
		TbFileDao tfDao = new TbFileDao(em);
//		Integer roleId=Integer.valueOf(tbResUserRoleDao.findRoleIdByUserId(myParam.getUserId()).toString());
//		if(roleId!=2&&StringUtil.isNullOrEmpty(enterpriseId)){
//			res.add("status", 1)
//			.add("msg", "操作成功！");
//			return true;
//		}
		if(StringUtils.isNullOrEmpty(isSale)||isSale.equals("1")) {
			Integer count=tbBrandDao.getAllBrandListCount(productName, enterpriseId, status,type,upStatus);
			if(count!=null&&count>0){
				List<Object[]> list=tbBrandDao.getAllBrandList(productName, enterpriseId, status, nowPage, pageCount,type,upStatus);
				List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> file;
				List<Object[]> fileList=null;
				Map<String,Object> map,fileMap;
				for(Object[] o:list){
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
					map.put("enterpriseId", o[13]);
//					map.put("upStatus", o[14]);
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
		}else {
			Integer enterpriseIds=StringUtils.isNullOrEmpty(enterpriseId)?null:Integer.valueOf(enterpriseId);
			TbBrandSaleDao tbBrandSaleDao = new TbBrandSaleDao(em);
			Integer count = tbBrandSaleDao.findListByTypeCount(enterpriseIds,productName, status,type,upStatus);
			if(count==0||count==null){
				res.add("status", 1);
				res.add("msg", "无数据");
				return true;
			}
			List<Map<String, Object>> list = tbBrandSaleDao.findListByType(enterpriseIds,nowPage,pageCount,productName, status,type,upStatus);
			if(list!=null) {
				for(Map<String, Object> map:list) {
					List<Object[]> fileList=null;
					if(map.containsKey("id")) {
						fileList=tbResBrandFileDao.findFileById(Integer.valueOf(map.get("id").toString()));
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
					}
				}
			}
			res.add("result", list);
			res.add("status", 1);
			res.add("msg", "操作成功");
			res.add("allCounts", count);
			return true;
			
		}

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
