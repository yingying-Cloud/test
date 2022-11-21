package com.jinpinghu.logic.plantProtect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.dao.TbMechineDao;
import com.jinpinghu.db.dao.TbPlantProtectionDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantProtect.param.GetPlantProtectionListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPlantProtectionListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetPlantProtectionListParam myParam = (GetPlantProtectionListParam)logicParam;
		String name = myParam.getName();
		String enterpriseName = myParam.getEnterpriseName();
		String dscd = myParam.getDscd();
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		String lowPrice = myParam.getLowPrice();
		String highPrice = myParam.getHighPrice();
		String etName = myParam.getEtName();
		String type = myParam.getType();
		String brand = myParam.getBrand();
		String serverType = myParam.getServerType();
		String orderby = myParam.getOrderby();
		String sortDirection = myParam.getSortDirection();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String status = myParam.getStatus();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage()) ? null : Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount()) ? null : Integer.valueOf(myParam.getPageCount());
		Integer loginEnterpriseId = null;
		if (loginUser != null) {
			loginEnterpriseId = EnterpriseDataPermissionUtil.getLoginEnterpriseId(loginUser.getId(), em);
		}
		
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbPlantProtectionDao plantProtectionDao = new TbPlantProtectionDao(em);
		
		TbRole role = resUserRoleDao.findByUserTabId(loginUser.getId());
		TbEnterprise enterprise = null;
		
		Integer maxCount = 0;
		if(role.getId() == 3 || role.getId() == 2) {
			maxCount = plantProtectionDao.findPlantProtectionListCount(name, enterpriseName, enterpriseId,dscd,lowPrice,highPrice,etName,type,brand,serverType,startTime,endTime,status);
		}else if(role.getId() == 4) {
			enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
			maxCount = plantProtectionDao.findPlantProtectionListCount(name, null, enterprise.getId(),dscd,lowPrice,highPrice,etName,type,brand,serverType,startTime,endTime,status);
		}else {
			res.add("status", -1).add("msg", "权限验证失败！");
			return false;
		}
		
		if(maxCount == null || maxCount == 0) {
			res.add("status", 1);
			res.add("msg", "无数据");
			res.add("result", new ArrayList<>());
			res.add("allCounts",maxCount);
			res.add("maxPage",0);
			return true;
		}
		
		Integer maxPage = null;
		if(pageCount != null) {
			maxPage = 1;
			maxPage = (int) (maxCount/pageCount);
			if(maxCount%pageCount!=0){
				maxPage++;
			}
		}
		
		List<Map<String, Object>> dataList = new ArrayList<>();
		if(role.getId() == 3 || role.getId() == 2) {
			dataList = plantProtectionDao.findPlantProtectionList(name, enterpriseName, enterpriseId, dscd,lowPrice,highPrice,etName,
					type, brand, serverType,startTime,endTime, status,nowPage, pageCount,loginEnterpriseId,orderby,sortDirection);
		}else if(role.getId() == 4) {
			dataList = plantProtectionDao.findPlantProtectionList(name, null, enterprise.getId(), dscd,lowPrice,highPrice, etName, type,
					brand, serverType,startTime,endTime, status,nowPage, pageCount,loginEnterpriseId,orderby,sortDirection);
		}else {
			res.add("status", -1).add("msg", "权限验证失败！");
			return false;
		}
		
		TbMechineDao mechineDao = new TbMechineDao(em);
		for (Map<String, Object> map : dataList) {
			List<Object[]> oList=mechineDao.getMechineListByPlantProtectionId((Integer)map.get("id"));
			List<Map<String, Object>> machineList = new ArrayList<Map<String,Object>>(oList.size());
			for (Object[] o : oList) {
				Map<String, Object> machineMap=new HashMap<String,Object>();
				machineMap.put("model", o[3]);
				machineMap.put("type", o[4]);
				machineMap.put("brand", o[7]);
				machineList.add(machineMap);
			}
			map.put("machineInfo",machineList);
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", dataList == null ? new ArrayList<>() : dataList).add("allCount", maxCount).add("maxPage", maxPage);
		return true;
	}

}
