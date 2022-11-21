package com.jinpinghu.logic.enterprise;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseListParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetEnterpriseListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseListParam myParam = (GetEnterpriseListParam)logicParam;
		String name=myParam.getName();
		String enterpriseCreditCode=myParam.getEnterpriseCreditCode();
		String enterpriseLegalPerson=myParam.getEnterpriseLegalPerson();
		String enterpriseLegalPersonIdcard=myParam.getEnterpriseLegalPersonIdcard();
		String enterpriseLinkPeople=myParam.getEnterpriseLinkPeople();
		String enterpriseLinkMobile=myParam.getEnterpriseLinkMobile();
		String enterpriseAddress=myParam.getEnterpriseAddress();
		Integer status = StringUtils.isNullOrEmpty(myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		Integer enterpriseType = StringUtils.isNullOrEmpty(myParam.getEnterpriseType())?null:Integer.valueOf(myParam.getEnterpriseType());
		String dscd = myParam.getDscd();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String selectAll = myParam.getSelectAll();
		Integer state = StringUtils.isNullOrEmpty(myParam.getState())?null:Integer.valueOf(myParam.getState());
		String userId = logicParam.getUserId();
		String apiKey = logicParam.getApiKey();
		TbUserDao tbUserDao = new TbUserDao(em);
		List<Integer> permissionEnterpriseIdList = null;
		if(!StringUtil.isNullOrEmpty(apiKey)&&!StringUtil.isNullOrEmpty(userId)){
			TbUser user = tbUserDao.checkLogin(userId, apiKey);
			permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(user.getId(), em);
		}
		TbToolDao tbToolDao = new TbToolDao(em);
		TbPlantProtectionOrderDao plantProtectionOrderDao = new TbPlantProtectionOrderDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbEnterpriseZeroDao enterpriseZeroDao = new TbEnterpriseZeroDao(em);
		Integer count = enterpriseDao.findByAllCount(name, enterpriseCreditCode, enterpriseLegalPerson, enterpriseLegalPersonIdcard, enterpriseLinkPeople, enterpriseLinkMobile, enterpriseAddress, enterpriseType, status,dscd,selectAll,permissionEnterpriseIdList,state);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = enterpriseDao.findByAll(name, enterpriseCreditCode, enterpriseLegalPerson, enterpriseLegalPersonIdcard, enterpriseLinkPeople, enterpriseLinkMobile, enterpriseAddress, enterpriseType, status,nowPage,pageCount,dscd,selectAll,permissionEnterpriseIdList,state);
		if(list!=null)
		for(Map<String, Object> map:list) {
			Integer enterpriseId = (Integer) map.get("id");
			
			Object[] allEnterpriseInfo = plantProtectionOrderDao.statisticalAllEnterpriseInfo(enterpriseId,
					DateTimeUtil.firstDayOfMonth(DateTimeUtil.formatSelf(new Date(), "yyyy-MM")),
			DateTimeUtil.lastDayOfMonth(DateTimeUtil.formatSelf(new Date(), "yyyy-MM")));
			if(allEnterpriseInfo!=null) {
				map.put("peopleCount", allEnterpriseInfo[0]);
				map.put("nzCount", allEnterpriseInfo[1]);
				//map.put("recoveryCount", allEnterpriseInfo[2]);
			}
			
		
			Object recordNoCount = tbToolDao.statisticRecordNoCount(enterpriseId);
			map.put("recordNoCount", recordNoCount);
			//星级
			Integer score = Integer.valueOf(map.get("score").toString());
			String star = null;
			if(score.equals(100)){
				star = "六星";
			}else if(score >= 90){
				star = "五星";
			}else if(score >= 70){
				star = "四星";
			}else if(score >= 60){
				star = "三星";
			}else if(score >= 40){
				star = "二星";
			}else if(score >= 20){
				star = "一星";
			}
			map.put("level", star);
			//四色码
			String qr = map.get("QRCode").toString();
			
			if(qr == null || qr.equals("4") ){
				qr = "绿码";
			}else if(qr.equals("2")){
				qr = "黄码";
			}else if(qr.equals("3")){
				qr = "蓝码";
			}else if(qr.equals("1")){
				qr = "红码";
			}
			map.put("color", qr);
			
		}

		Object workSum = enterpriseDao.getSumPlantScope(name,dscd);
		res.add("area", workSum == null ? "" : workSum);
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", list).add("allCounts", count);
		return true;
	}
	
   // 身份认证
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
		return true;
	}
}
