package com.jinpinghu.logic.login;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.AppUtil;
import com.jinpinghu.db.bean.TbCashRegister;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.bean.TbLog;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.AppUtilDao;
import com.jinpinghu.db.dao.TbCashRegisterDao;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbLogDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserExpertDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.login.param.LoginParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class LoginLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		LoginParam myParam = (LoginParam)logicParam;
		String account = myParam.getAccount();
		String password = myParam.getPassword();
		String wxId = myParam.getWxId();
		String headPic = myParam.getHeadPic();
		String cashRegisterId = myParam.getCashRegisterId();
		String cashRegisterVersion = myParam.getCashRegisterVersion();
		
		Date now = new Date();

		TbUserDao tbUserDao = new TbUserDao(em);
		TbCashRegisterDao cashRegisterDao = new TbCashRegisterDao(em);
		AppUtilDao utilDao = new AppUtilDao(em);
		
		AppUtil appUtil = utilDao.findByKey("develop_machine");
		String developMachines = appUtil.getValue();
		
		List<String> developMachineList = StringUtils.isEmpty(developMachines) ? new ArrayList<String>(0) : Arrays.asList(developMachines.split(","));
		
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		
		Map<String,Object> resultMap = new HashMap<>();
		
		TbUser tbUser = tbUserDao.login(account, password);
		if(tbUser==null) {
			res.add("status", 2);
			res.add("msg", "用户名或密码错误");
			return true;
		}
		//获取用户角色信息
		TbRole role = resUserRoleDao.findByUserTabId(tbUser.getId());
		if (role == null) {
			res.add("status", -1);
			res.add("msg", "请配置用户角色");
			return false;
		}
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(tbUser.getId());
		if (role.getId() == 5) {
			if(enterprise != null && enterprise.getState() ==0) {
				res.add("status", -1);
				res.add("msg", "此账号已停用");
				return false;
			}
		}
		if(!StringUtils.isEmpty(wxId)) {
			TbUser wxUser = tbUserDao.wechatLogin(wxId);
			if(wxUser != null && !wxUser.getMobile().equals(tbUser.getMobile())) {
				
			}else {
				if(StringUtils.isEmpty(tbUser.getWxId()))
					tbUser.setWxId(wxId);
				if(StringUtils.isEmpty(tbUser.getHeadPic()))
					tbUser.setHeadPic(headPic);
			}
		}
		if(!StringUtils.isEmpty(cashRegisterId) && !developMachineList.contains(cashRegisterId)) {
			tbUser.setCashRegisterId(cashRegisterId);
			TbCashRegister cashRegister = cashRegisterDao.findByCashRegisterId(cashRegisterId);
			if (cashRegister != null) {
				cashRegister.setUpdateTime(new Date());
				res.add("baiduAifaceSn", StringUtils.trimToEmpty(cashRegister.getBaiduAifaceSn()));
				res.add("machineVersion", cashRegister.getMachineVersion() == null ? "" : cashRegister.getMachineVersion());
			}else {
				cashRegister = new TbCashRegister(cashRegisterId, null,now,now,0);
				cashRegisterDao.save(cashRegister);
				res.add("baiduAifaceSn", "");
				res.add("machineVersion", "");
			}
		}else if (developMachineList.contains(cashRegisterId)) {
			TbCashRegister cashRegister = cashRegisterDao.findByCashRegisterId(cashRegisterId);
			if (cashRegister == null) {
				cashRegister = new TbCashRegister(cashRegisterId, null,now,now,0);
				cashRegisterDao.save(cashRegister);
			}
			res.add("baiduAifaceSn", cashRegister == null ? "" : StringUtils.trimToEmpty(cashRegister.getBaiduAifaceSn()));
			res.add("machineVersion", cashRegister.getMachineVersion() == null ? "" : cashRegister.getMachineVersion());
		}
		if(!StringUtils.isEmpty(cashRegisterVersion) && !developMachineList.contains(cashRegisterId)) {
			tbUser.setCashRegisterVersion(cashRegisterVersion);
		}
		
		if (!developMachineList.contains(cashRegisterId)) {
			tbUser.setLastTime(new Date());
		}
		
		
		//用户基础信息
		resultMap.put("userTabId", tbUser.getId());
		resultMap.put("userId", tbUser.getUserId());
		resultMap.put("apiKey", tbUser.getApiKey());
		resultMap.put("name", tbUser.getName());
		resultMap.put("mobile", tbUser.getMobile());
		resultMap.put("headPic", tbUser.getHeadPic());
		resultMap.put("wxId", tbUser.getWxId());

		
		//获取用户角色信息
		if (role.getId() == 5) {
			resultMap.put("roleId",11);
		}else if (role.getId() == 14) {
			resultMap.put("roleId",12);
		}
		
		resultMap.put("roleName", role == null ? "" : role.getRoleName());
		
		
		TbResUserExpertDao resUserExpertDao = new TbResUserExpertDao(em);
		TbExpert expert = resUserExpertDao.findByUserTabId(tbUser.getId());
		resultMap.put("enterpriseId", enterprise == null ? "" : enterprise.getId());
		resultMap.put("enterpriseName", enterprise == null ? "" : enterprise.getName());
		resultMap.put("status", enterprise == null ? "" : enterprise.getStatus());
		if(expert != null) {
			resultMap.put("enterpriseId", expert == null ? "" : expert.getId());
			resultMap.put("enterpriseName", expert == null ? "" : tbUser.getName());
			resultMap.put("status", expert == null ? "" : expert.getStatus());
		}
		
		//无用代码,仅用于适配
		resultMap.put("username", tbUser.getMobile());
		resultMap.put("nickname", tbUser.getName());
		resultMap.put("sex", 1);
		List<Map<String,Object>> storeInfoList = new ArrayList<Map<String,Object>>();
		Map<String,Object> storeInfoMap = new HashMap<String,Object>();
		storeInfoMap.put("storeId", resultMap.get("enterpriseId"));
		storeInfoMap.put("storeTabId", resultMap.get("enterpriseId"));
		storeInfoMap.put("storeName",resultMap.get("enterpriseName"));
		storeInfoMap.put("roleId","");
		storeInfoMap.put("premium","");
		storeInfoMap.put("discount","");
		storeInfoMap.put("storeType","");
		storeInfoList.add(storeInfoMap);
		resultMap.put("storeInfo", storeInfoList);
		
		TbEnterpriseZeroDao tbEnterpriseZeroDap = new TbEnterpriseZeroDao(em);
		if(enterprise!=null) {
			TbEnterpriseZero zero = tbEnterpriseZeroDap.findByEnterpriseId(enterprise.getId());
			res.add("flag", zero == null ? 0 : zero.getFlag());
		}
		
		//记录登录信息
		if (!developMachineList.contains(cashRegisterId) || developMachineList.contains(cashRegisterId)) {
			TbLog log = new TbLog();
			TbLogDao logDao = new TbLogDao(em);
			log.setInputTime(new Date());
			log.setName(tbUser.getName());
			log.setUid(tbUser.getId());
			logDao.save(log);
		}
		
		
		res.add("result", resultMap);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		LoginParam myParam = (LoginParam)logicParam;
		String account = myParam.getAccount();
		String password = myParam.getPassword();
		if(StringUtils.isEmpty(account)
				||StringUtils.isEmpty(password)) {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}
}
