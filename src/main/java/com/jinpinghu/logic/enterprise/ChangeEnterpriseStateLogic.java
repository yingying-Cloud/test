package com.jinpinghu.logic.enterprise;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.ChangeEnterpriseStatusParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ChangeEnterpriseStateLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ChangeEnterpriseStatusParam myParam = (ChangeEnterpriseStatusParam)logicParam;
		String ids = myParam.getIds();
		String state=myParam.getStatus();
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		/*��֤�Ƿ���ڸù�˾*/
		if(!StringUtils.isNullOrEmpty(ids)&&!StringUtils.isNullOrEmpty(state)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbEnterprise enterprise = enterpriseDao.findById(Integer.valueOf(id));
				if(enterprise!=null) {
					enterprise.setState(Integer.valueOf(state));
					enterpriseDao.update(enterprise);
				}
			}
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}
}
