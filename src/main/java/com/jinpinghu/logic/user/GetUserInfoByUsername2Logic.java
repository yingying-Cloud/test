package com.jinpinghu.logic.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.db.bean.TbArea;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.bean.TbResUserEnterprise;
import com.jinpinghu.db.bean.TbResUserRole;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbAreaDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserExpertDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.GetUserInfoByMobileParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetUserInfoByUsername2Logic extends BaseZLogic{

	private final static String url = "https://pinghu.sznhl.net/api/base/user/pass/queryByMobileOutside";//http://39.104.62.243:8804/base/user/pass/queryByMobileOutside/";
	private final static String password = "e10adc3949ba59abbe56e057f20f883e";
	
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetUserInfoByMobileParam myParam = (GetUserInfoByMobileParam)logicParam;
		String username2 = myParam.getUsername2();
		
		TbUserDao userDao = new TbUserDao(em);
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbAreaDao areaDao = new TbAreaDao(em);
		
		TbUser tbUser = userDao.findByUsername2(username2);

		//调用外部接口，获取用户信息
		String request = HttpRequestUtil.sendGet(url+"/"+username2, null);
		JSONObject jo = JSONObject.parseObject(request).getJSONObject("data");
		if(jo != null && !jo.isEmpty()){
			String enterName = StringUtils.isEmpty(jo.getString("enterName")) ? jo.getString("name") : jo.getString("enterName");

			TbResUserEnterpriseDao resEDao = new TbResUserEnterpriseDao(em);
			
			if(tbUser==null) {//账户不存在，新增
				//新增用户
				String userId = ZDao.genPkId();
				String apiKey = _blow.encryptString(jo.getString("mobile") + password + System.currentTimeMillis()).substring(0, 100);
				tbUser = new TbUser(null, userId, apiKey, jo.getString("mobile"), password, new Date());
				tbUser.setName(jo.getString("name"));
				tbUser.setDelFlag(0);
				tbUser.setUsername2(username2);
				tbUser.setExpertIdcard(jo.getString("idCard"));
				tbUser.setDscd("330482000000");
				userDao.save(tbUser);
				//新增角色关联
				TbResUserRole tbResUserRole = new TbResUserRole(tbUser.getId(),3, new Date(), 0);
				new TbResUserRoleDao(em).save(tbResUserRole);
				//新增企业
				TbEnterprise te = new TbEnterprise(null, enterName, new Date(), 1, 0);
				te.setDscd("330482000000");
				new TbEnterpriseDao(em).save(te);
				//新增企业关联
				TbResUserEnterprise ue = new TbResUserEnterprise(tbUser.getId(), te.getId(), 0);
				resEDao.save(ue);
			}else{
				//更新用户
				tbUser.setMobile(jo.getString("mobile"));
				tbUser.setName(jo.getString("name"));
				tbUser.setExpertIdcard(jo.getString("idCard"));
				tbUser.setDscd("330482000000");
				userDao.update(tbUser);
				//更新企业
				TbEnterprise te = resEDao.findByUserTabId(tbUser.getId());
				te.setName(enterName);
				te.setDscd("330482000000");
				userDao.update(te);
			}
		}
		if(tbUser == null){
			res.add("msg", "账号不存在").add("status", 1);
			return  true;
		}
		Map<String,Object> resultMap = new HashMap<>();
		
		//获取用户角色信息
		TbRole role = resUserRoleDao.findByUserTabId(tbUser.getId());
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(tbUser.getId());
		if (role.getId() == 5) {
			if(enterprise != null && enterprise.getState() ==0) {
				res.add("status", -1);
				res.add("msg", "此账号已停用");
				return false;
			}
		}
		
		resultMap.put("userTabId", tbUser.getId());
		resultMap.put("userId", tbUser.getUserId());
		resultMap.put("apiKey", tbUser.getApiKey());
		resultMap.put("name", tbUser.getName());
		resultMap.put("mobile", tbUser.getMobile());
		resultMap.put("headPic", tbUser.getHeadPic());
		resultMap.put("wxId", tbUser.getWxId());
		resultMap.put("dscd", tbUser.getDscd());
		
		TbArea area = areaDao.findById(tbUser.getDscd());
		resultMap.put("dsnm", area == null ? "" : area.getName());
		
		resultMap.put("roleId", role == null ? "" : role.getId());
		resultMap.put("roleName", role == null ? "" : role.getRoleName());
		
		
		TbResUserExpertDao resUserExpertDao = new TbResUserExpertDao(em);
		TbExpert expert = resUserExpertDao.findByUserTabId(tbUser.getId());
		
		resultMap.put("enterpriseId", enterprise == null ? "" : enterprise.getId());
		resultMap.put("enterpriseName", enterprise == null ? "" : enterprise.getName());
		resultMap.put("status", enterprise == null ? "" : enterprise.getStatus());
		resultMap.put("industrial", enterprise == null ? "" : enterprise.getIndustrial());
		if(expert != null) {
			resultMap.put("enterpriseId", expert == null ? "" : expert.getId());
			resultMap.put("enterpriseName", expert == null ? "" : tbUser.getName());
			resultMap.put("status", expert == null ? "" : expert.getStatus());
		}
		TbEnterpriseZeroDao tbEnterpriseZeroDap = new TbEnterpriseZeroDao(em);
		if(enterprise!=null) {
			TbEnterpriseZero zero = tbEnterpriseZeroDap.findByEnterpriseId(enterprise.getId());
			resultMap.put("flag", zero == null ? 0 : zero.getFlag());
		}
		
		
		res.add("result", resultMap);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}
}
