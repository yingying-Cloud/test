package com.jinpinghu.logic;


import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.jinpinghu.db.bean.TbCallRecords;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.filter.XssFilter;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.common.util.Blowfish;
import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogic;
import fw.jbiz.logic.ZLogicParam;


public abstract class BaseZLogic extends ZLogic {

		// 常量
		public static final String androidVersion = "1.0.0";
		public static final String iosVersion = "1.0.0";
		protected static final Blowfish _blow = new Blowfish("patrol");
		protected static final Blowfish _of = new Blowfish("4H709fjyRIPOVvK");
		protected TbUser loginUser = null;
		
		public BaseZLogic() {
			// 增加权限检查过滤器
			addFilter(new XssFilter());
		}
		
		@Override
		protected String getPersistenceUnitName() {
			return ZSystemConfig.getProperty("PersistenceUnitName");
		}
		
		@Override
		protected abstract boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception;
		
	   // 身份认证
		@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
			String userId = logicParam.getUserId();
			String apiKey = logicParam.getApiKey();
			if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(apiKey)) {
				res.add("status", -2);
				res.add("msg", "必填参数不能为空");
				return false;
			}

			TbUserDao tbUserDao = new TbUserDao(em);
			TbUser user = tbUserDao.checkLogin(userId, apiKey);
			if (user == null) {
				res.add("status", -3);
				res.add("msg", "身份验证失败");
				return false;
			}
			this.loginUser = user;
			
			return true;
	   }
		

		// 参数检查
		@Override
		protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
				EntityManager em)  throws Exception {
			
			return true;
		}
	
}
