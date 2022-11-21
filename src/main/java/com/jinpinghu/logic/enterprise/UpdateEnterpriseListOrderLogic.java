package com.jinpinghu.logic.enterprise;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.UpdateEnterpriseListOrderParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateEnterpriseListOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		UpdateEnterpriseListOrderParam myParam = (UpdateEnterpriseListOrderParam)logicParam;
		String listOrderJa = myParam.getListOrderJa();
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		if(!StringUtils.isNullOrEmpty(listOrderJa)){
			JSONArray arrayF= JSONArray.fromObject(listOrderJa);
			if(arrayF.size()>0){
				for(int i=0;i<arrayF.size();i++){
					JSONObject jsonObj=(JSONObject) arrayF.get(i);
					if(jsonObj.containsKey("id")) {
						TbEnterprise enterprise = enterpriseDao.findById(jsonObj.getInt("id"));
						if(enterprise!=null) {
							if(jsonObj.containsKey("listOrder")) {
								enterprise.setListOrder(jsonObj.getInt("listOrder"));
								enterpriseDao.update(enterprise);
							}
						}
					}
				}
			}
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
