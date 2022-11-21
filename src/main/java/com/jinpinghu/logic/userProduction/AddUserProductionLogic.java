package com.jinpinghu.logic.userProduction;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbEnterpriseUserProductionInfo;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.userProduction.param.AddUserProductionParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddUserProductionLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddUserProductionParam myParam = (AddUserProductionParam)logicParam;
		String enterpriseId = myParam.getEnterpriseId();
		String userIdCard = myParam.getUserIdCard();
		String userName = myParam.getUserName();
		Integer id = StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		
		TbEnterpriseUserProductionInfoDao userProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
		TbEnterpriseDao tbEnterpriseDao = new TbEnterpriseDao(em);
		
		TbEnterpriseUserProductionInfo userProductionInfo = userProductionInfoDao.getByUserIdCard(userIdCard,id);
		if(userProductionInfo!=null) {
			if(userProductionInfo.getType()==1) {
				TbEnterprise enterprise = tbEnterpriseDao.findById(userProductionInfo.getEnterpriseId());
				res.add("status", 2).add("msg","此人已在\""+enterprise.getName()+"\"生产主体中是户主或法人！");
				return true;
			}else if(userProductionInfo.getType()==2) {
				TbEnterprise enterprise = tbEnterpriseDao.findById(userProductionInfo.getEnterpriseId());
				res.add("status", 2).add("msg","此人已在\""+enterprise.getName()+"\"生产主体中是员工！");
				return true;
			}
		}
		
		userProductionInfo = userProductionInfoDao.getById(id);
		if(userProductionInfo!=null) {
			userProductionInfo.setEnterpriseId(Integer.valueOf(enterpriseId));
			userProductionInfo.setUserName(userName);
			userProductionInfo.setUserIdCard(userIdCard);
			userProductionInfoDao.update(userProductionInfo);
		}else {
			userProductionInfo =new TbEnterpriseUserProductionInfo();
			userProductionInfo.setEnterpriseId(Integer.valueOf(enterpriseId));
			userProductionInfo.setUserName(userName);
			userProductionInfo.setUserIdCard(userIdCard);
			userProductionInfo.setType(2);
			userProductionInfoDao.save(userProductionInfo);
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}


	/*@Override
	public boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
							EntityManager em)  throws Exception {

		return true;
	}*/
}
