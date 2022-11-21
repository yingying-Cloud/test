package com.jinpinghu.logic.user;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.GetUserInfoByMobileParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetUserIntegralLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetUserInfoByMobileParam myParam = (GetUserInfoByMobileParam)logicParam;
		Integer id = myParam.getId();
		
		TbUserDao userDao = new TbUserDao(em);
		TbUser tbUser = userDao.findById(id);
		
		if(tbUser==null) {
			res.add("status", 2);
			res.add("msg", "该账户不存在！");
			return true;
		}
		
		Map<String,Object> resultMap = new HashMap<>();
		
		resultMap.put("userTabId", tbUser.getId());
		resultMap.put("integral", tbUser.getIntegral());
		
		
		res.add("result", resultMap);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
