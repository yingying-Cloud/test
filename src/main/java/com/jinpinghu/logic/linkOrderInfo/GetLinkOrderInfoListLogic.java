package com.jinpinghu.logic.linkOrderInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.linkOrderInfo.param.GetLinkOrderInfoListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetLinkOrderInfoListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetLinkOrderInfoListParam myParam = (GetLinkOrderInfoListParam)logicParam;
		String name = myParam.getName();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		Integer enterpriseId = myParam.getEnterpriseId();
		String idcard = myParam.getIdcard();
		String mobile = myParam.getMobile();
		String enterpriseName = myParam.getEnterpriseName();
		String selectAll = myParam.getSelectAll();
		String dscd = myParam.getDscd();
		String isZj = myParam.getIsZj();
		TbUserDao tbUserDao = new TbUserDao(em);
		loginUser = tbUserDao.checkLogin(myParam.getUserId(), myParam.getApiKey());
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser == null ? null : loginUser.getId(), em);
		
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		
		Integer maxCount = linkOrderInfoDao.getLinkOrderInfoListCount(name,enterpriseId,idcard,mobile,enterpriseName,selectAll,permissionEnterpriseIdList,dscd,isZj);
		Map<String, Object> zjCountMap = linkOrderInfoDao.getProvinceLinkOrderInfoCount(name, enterpriseId, idcard, mobile, enterpriseName, selectAll, permissionEnterpriseIdList, dscd,isZj);
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		if(maxCount==null||maxCount==0){
			res.add("allCounts",0);
			res.add("maxPage",0);
			res.add("result", resultList);
			res.add("status", 1);
			res.add("zjCount", zjCountMap == null ? 0 : zjCountMap.get("zjCount"));
			res.add("notZjCount", zjCountMap == null ? 0 : zjCountMap.get("notZjCount"));
			res.add("msg", "无记录");
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
				res.add("result", resultList);
				res.add("status", 1);
				res.add("msg", "该页无记录");
				res.add("zjCount", zjCountMap == null ? 0 : zjCountMap.get("zjCount"));
				res.add("notZjCount", zjCountMap == null ? 0 : zjCountMap.get("notZjCount"));
				return true;
			}else if(maxPage == 0){
				nowPage = 1;
			}
		}
		
		resultList = linkOrderInfoDao.getLinkOrderInfoList(name, nowPage, pageCount,enterpriseId,idcard,mobile,enterpriseName,selectAll,permissionEnterpriseIdList,dscd,isZj);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("zjCount", zjCountMap == null ? 0 : zjCountMap.get("zjCount"));
		res.add("notZjCount", zjCountMap == null ? 0 : zjCountMap.get("notZjCount"));
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}

}
