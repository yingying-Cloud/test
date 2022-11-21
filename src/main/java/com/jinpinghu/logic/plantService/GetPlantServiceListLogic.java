package com.jinpinghu.logic.plantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.dao.TbPlantServiceDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantService.param.GetPlantServiceListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPlantServiceListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetPlantServiceListParam myParam = (GetPlantServiceListParam)logicParam;
		String name = myParam.getName();
		String enterpriseName = myParam.getEnterpriseName();
		String dscd = myParam.getDscd();
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		String lowPrice = myParam.getLowPrice();
		String highPrice = myParam.getHighPrice();
		String etName = myParam.getEtName();
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
		TbPlantServiceDao plantServiceDao = new TbPlantServiceDao(em);
		
		TbRole role = resUserRoleDao.findByUserTabId(loginUser.getId());
		TbEnterprise enterprise = null;
		
		Integer maxCount = 0;
		if(role.getId() == 13) {
			enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
			maxCount = plantServiceDao.findPlantServiceListCount(name, null, enterprise.getId(),dscd,lowPrice,highPrice,etName,serverType,startTime,endTime,status);
		}else {
			maxCount = plantServiceDao.findPlantServiceListCount(name, enterpriseName, enterpriseId,dscd,lowPrice,highPrice,etName,serverType,startTime,endTime,status);
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
		if(role.getId() == 13) {
			dataList = plantServiceDao.findPlantServiceList(name, null, enterprise.getId(),dscd,lowPrice,highPrice, etName,serverType,
					startTime,endTime, status, nowPage, pageCount,loginEnterpriseId,orderby,sortDirection);
		}else{
			dataList = plantServiceDao.findPlantServiceList(name, enterpriseName, enterpriseId,dscd,lowPrice,highPrice, etName,serverType,
					startTime,endTime, status, nowPage, pageCount,loginEnterpriseId,orderby,sortDirection);
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", dataList == null ? new ArrayList<>() : dataList).add("allCount", maxCount).add("maxPage", maxPage);
		return true;
	}

}
