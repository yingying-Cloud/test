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

public class ChangeLinkOrderInfoMobileLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddLinkOrderInfoParam myParam = (AddLinkOrderInfoParam)logicParam;
		String idcard = myParam.getIdcard();
		String mobile = myParam.getMobile();
		
		TbLinkOrderInfoDao orderInfoDao = new TbLinkOrderInfoDao(em);
		if(!StringUtils.isEmpty(idcard)){
			TbLinkOrderInfo orderInfo = orderInfoDao.findById(Integer.valueOf(idcard));
	
			if(orderInfo != null) {
				orderInfo.setLinkMobile(mobile);
				orderInfo.setUpdateTime(new Date());
				orderInfoDao.update(orderInfo);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
