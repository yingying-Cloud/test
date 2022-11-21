package com.jinpinghu.logic.linkOrderInfo;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.baidu.aip.face.AipFace;
import com.jinpinghu.common.tools.BaiduAIUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbResEnterpriseLinkorderinfo;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbResEnterpriseLinkorderinfoDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.linkOrderInfo.param.AddLinkOrderInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddLinkOrderInfoLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddLinkOrderInfoParam myParam = (AddLinkOrderInfoParam)logicParam;
		String name = myParam.getName();
		String idcard = myParam.getIdcard();
		String sex = myParam.getSex();
		String nation = myParam.getNation();
		String address = myParam.getAddress();
		String pic = myParam.getPic();
		String country = myParam.getCountry();
		String isIdcardPic = myParam.getIsIdcardPic();
		String mobile = myParam.getMobile();
		
		TbLinkOrderInfoDao orderInfoDao = new TbLinkOrderInfoDao(em);
		TbResEnterpriseLinkorderinfoDao resEnterpriseLinkorderinfoDao = new TbResEnterpriseLinkorderinfoDao(em);
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		
		TbLinkOrderInfo orderInfo = orderInfoDao.findByIdcard(idcard);
		

		if(orderInfo != null) {
			if(!StringUtils.isEmpty(pic) && "1".equals(isIdcardPic))
				orderInfo.setIdcardPic(pic);
			else if(!StringUtils.isEmpty(pic))
				orderInfo.setLastPic(pic);
			if(!StringUtils.isEmpty(address))
				orderInfo.setAddress(address);
			if(!StringUtils.isEmpty(name))
				orderInfo.setName(name);
			if(!StringUtils.isEmpty(nation))
				orderInfo.setNation(nation);
			if(!StringUtils.isEmpty(sex))
				orderInfo.setSex(sex);
			if(!StringUtils.isEmpty(country))
				orderInfo.setCountry(country);
			if(!StringUtils.isEmpty(mobile))
				orderInfo.setLinkMobile(mobile);
			
			orderInfo.setUpdateTime(new Date());
			orderInfoDao.update(orderInfo);
			
		}else {
			orderInfo = new TbLinkOrderInfo();
			orderInfo.setDelFlag(0);
			orderInfo.setLegalPersonIdcard(idcard);
			orderInfo.setIdcardPic(pic);
			orderInfo.setInputTime(new Date());
			orderInfo.setLastPic(pic);
			orderInfo.setAddress(address);
			orderInfo.setName(name);
			orderInfo.setNation(nation);
			orderInfo.setSex(sex);
			orderInfo.setUpdateTime(new Date());
			orderInfo.setCountry(country);
			orderInfo.setLinkPeople(name);
			orderInfo.setLinkMobile(mobile);
			orderInfo.setType(2);
			orderInfo.setIsValidation("1");
			orderInfoDao.save(orderInfo);
		}
		
		TbResEnterpriseLinkorderinfo resEnterpriseLinkorderinfo = resEnterpriseLinkorderinfoDao.findByEnterpriseIdAndLinkOrderInfoId(enterprise== null ? null : enterprise.getId(), orderInfo.getId());
		if (resEnterpriseLinkorderinfo == null) {
			if (enterprise != null) {
				resEnterpriseLinkorderinfo = new TbResEnterpriseLinkorderinfo();
				resEnterpriseLinkorderinfo.setEnterpriseId(enterprise.getId());
				resEnterpriseLinkorderinfo.setLinkOrderInfoId(orderInfo.getId());
				resEnterpriseLinkorderinfoDao.save(resEnterpriseLinkorderinfo);
			}
		}
		
		AipFace client = BaiduAIUtil.initClient();
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user_info", orderInfo.getName());
	    options.put("quality_control", "NORMAL");
	    options.put("liveness_control", "LOW");
	    options.put("action_type", "APPEND");
		client.addUser(pic, "URL", "jinpinghu", idcard, options);
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
