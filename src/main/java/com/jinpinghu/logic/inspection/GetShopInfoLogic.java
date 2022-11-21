package com.jinpinghu.logic.inspection;

import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetShopInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		String code = param.getCode();

		TbEnterpriseDao dao = new TbEnterpriseDao(em);
		TbUserDao tbUserDao = new TbUserDao(em);
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		
		TbUser tbUser = tbUserDao.checkLogin2(code);
		TbEnterprise tbEnterprise = resUserEnterpriseDao.findByUserTabId(tbUser.getId());
		Integer enterpriseId = tbEnterprise == null ? null : tbEnterprise.getId();
		
		Map<String, Object> result = dao.getEnterpriseInfo(enterpriseId);
		Integer score = Integer.valueOf(result.get("score").toString());
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
		result.put("level", star);
		
		Map<String, Object> status = dao.getEnterpriseStatus(enterpriseId);
		String qr = null;
		if(status != null)
			qr = status.get("QRCode").toString();
		
		if(qr == null || qr.equals("4") ){
			qr = "绿码";
		}else if(qr.equals("2")){
			qr = "黄码";
		}else if(qr.equals("3")){
			qr = "蓝码";
		}else if(qr.equals("1")){
			qr = "红码";
		}
		result.put("color", qr);
		
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em)  throws Exception {
		
		return true;
	}
}
