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
import com.jinpinghu.logic.mechine.param.GetMechineListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetMechineListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetMechineListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMechineListParam myParam=(GetMechineListParam)GetMechineListParam;
		String name=myParam.getName();
		String type=myParam.getType();
		String brand = myParam.getBrand();
		String model = myParam.getModel();
		String enterpriseId=myParam.getEnterpriseId();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		TbResMechineFileDao tbResMechineFileDao=new TbResMechineFileDao(em);
		TbMechineDao tbMechineDao=new TbMechineDao(em);
		
		Integer count=tbMechineDao.getMechineListCount(name, enterpriseId, type,brand,model);
		
		if(count!=null&&count>0){
			List<Object[]> list=tbMechineDao.getMechineList(name, enterpriseId, type,brand,model, nowPage, pageCount);
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Object[] ob=null;
			Map<String,Object> map,fileMap;
			for(Object[] o:list){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("enterpriseName", o[1]);
				map.put("name", o[2]);
				map.put("model", o[3]);
				map.put("type", o[4]);
				map.put("describe", o[5]);
				map.put("inputTime", DateTimeUtil.formatTime2((Date)o[6]));
				map.put("brand", o[7]);
//				map.put("typeName", o[8]);
				List<Object[]> fileList=tbResMechineFileDao.findFileById(Integer.valueOf(o[0].toString()));
				if(fileList!=null){
					fileMap=new HashMap<String,Object>();
					ob=fileList.get(0);
					fileMap.put("id", ob[0]);
					fileMap.put("fileName", ob[1]);
					fileMap.put("fileSize", ob[2]);
					fileMap.put("fileType", ob[3]);
					fileMap.put("fileUrl", ob[4]);
					map.put("file", fileMap);
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
