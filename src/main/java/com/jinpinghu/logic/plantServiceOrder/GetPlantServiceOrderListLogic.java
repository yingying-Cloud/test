package com.jinpinghu.logic.plantServiceOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.dao.TbPlantServiceOrderDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantServiceOrder.param.GetPlantServiceOrderListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPlantServiceOrderListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetPlantServiceOrderListParam myParam = (GetPlantServiceOrderListParam)logicParam;
		String name = myParam.getName();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage()) ? null : Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount()) ? null : Integer.valueOf(myParam.getPageCount());
		Integer status = StringUtils.isEmpty(myParam.getStatus()) ? null : Integer.valueOf(myParam.getStatus());
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		String type = myParam.getType();
		
		TbPlantServiceOrderDao plantServiceOrderDao = new TbPlantServiceOrderDao(em);
		
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		
		TbRole role = resUserRoleDao.findByUserTabId(loginUser.getId());
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		
		Integer maxCount = 0;
		if(role.getId() == 3){
			//种植企业
			if("1".equals(type) || StringUtils.isEmpty(type)) {
				maxCount = plantServiceOrderDao.getPlantServiceOrderCount(name, startTime, endTime, enterprise.getId(), null, status,null);
			}else if("2".equals(type)) {
				maxCount = plantServiceOrderDao.getPlantServiceOrderCount(name, startTime, endTime, null, enterprise.getId(), status,null);
			}
		}else if(role.getId() == 13){
			//植保企业
			maxCount = plantServiceOrderDao.getPlantServiceOrderCount(name, startTime, endTime, null, enterprise.getId(), status,null);
		}else if(role.getId() == 2){
			//管理员
			maxCount = plantServiceOrderDao.getPlantServiceOrderCount(name, startTime, endTime, null, null, status,null);
		}
		if(maxCount==null||maxCount==0){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		int maxPage = 1;
		if(pageCount != null) {
			maxPage = maxCount/pageCount;
			if(maxCount%pageCount!=0){
				maxPage++;
			}
			if (pageCount * nowPage >= (maxCount + pageCount) && maxPage != 0) {
				nowPage = maxPage;
				res.add("allCounts",maxCount);
				res.add("maxPage",maxPage);
				res.add("result", new ArrayList<>());
				res.add("status", 1);
				res.add("msg", "该页无记录");
				return true;
			}else if(maxPage == 0){
				nowPage = 1;
			}
		}
		
		
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		if(role.getId() == 3){
			//种植企业
			if("1".equals(type) || StringUtils.isEmpty(type)) {
				resultList = plantServiceOrderDao.getPlantServiceOrderList(name, startTime, endTime, enterprise.getId(), null, status,nowPage,pageCount,null);
			}else if("2".equals(type)) {
				resultList = plantServiceOrderDao.getPlantServiceOrderList(name, startTime, endTime, null, enterprise.getId(), status,nowPage,pageCount,null);
			}
		}else if(role.getId() == 13){
			//服务企业
			resultList = plantServiceOrderDao.getPlantServiceOrderList(name, startTime, endTime, null, enterprise.getId(), status,nowPage,pageCount,null);
		}else if(role.getId() == 2){
			//管理员
			resultList = plantServiceOrderDao.getPlantServiceOrderList(name, startTime, endTime, null, null, status,nowPage,pageCount,enterpriseId);
		}
		
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
