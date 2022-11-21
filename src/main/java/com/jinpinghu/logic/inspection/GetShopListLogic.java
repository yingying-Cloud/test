package com.jinpinghu.logic.inspection;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetShopListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		String dscd = null;
		String enterpriseName = param.getEnterpriseName();
		Integer nowPage = StringUtil.isNullOrEmpty(param.getNowPage()) ? null : Integer.valueOf(param.getNowPage());
		Integer pageCount = StringUtil.isNullOrEmpty(param.getPageCount()) ? null : Integer.valueOf(param.getPageCount());

		//判断用户身份
		TbResUserRoleDao resRoleDao = new TbResUserRoleDao(em);
		String roleId = resRoleDao.findRoleIdByUserId(param.getUserId()).toString();
		
		if(roleId.equals("5") || roleId.equals("14")){//农资店
			
		}else if(roleId.equals("11")){//农业农村局
			dscd = param.getDscd();
		}else if(roleId.equals("12")){//农资监管员
			TbUserDao uDao = new TbUserDao(em);
			TbUser user = uDao.checkLogin2(param.getUserId());
			dscd = user.getDscd();
		}
		
		TbEnterpriseDao eDao = new TbEnterpriseDao(em);
		Integer count = eDao.getEnterpriseListCount(enterpriseName, dscd);
		List<Map<String, Object>> result = null;
		if(count != null && count > 0){
			result = eDao.getEnterpriseList(enterpriseName, dscd, nowPage, pageCount);
		}
		
		res.add("result", result);
		res.add("allCounts", count);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
