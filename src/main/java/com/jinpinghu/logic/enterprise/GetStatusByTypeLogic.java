package com.jinpinghu.logic.enterprise;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbExpertDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetStatusByTypeParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class GetStatusByTypeLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetStatusByTypeParam myParam = (GetStatusByTypeParam)logicParam;
		String type = myParam.getType();
		Integer id = StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		JSONObject jo = new JSONObject();
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbExpertDao expertDao = new TbExpertDao(em);
		if(!StringUtils.isNullOrEmpty(type)&&id!=null) {
			if(type.equals("1")) {
				TbExpert expert = expertDao.findById(id);
				if(expert!=null)
					jo.put("status", expert.getStatus());
			}else if(type.equals("2")) {
				TbEnterprise enterprise = enterpriseDao.findById(id);
				if(enterprise!=null)
					jo.put("status", enterprise.getStatus());
			}
		}
		res.add("status", 1).add("result", jo).add("msg","操作成功");
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
