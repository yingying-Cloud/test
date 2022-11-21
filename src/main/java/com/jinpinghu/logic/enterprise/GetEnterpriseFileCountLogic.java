package com.jinpinghu.logic.enterprise;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseFileParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class GetEnterpriseFileCountLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseFileParam myParam = (GetEnterpriseFileParam)logicParam;
		Integer id =StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Integer type = StringUtils.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbEnterprise enterprise = null;
		if(id!=null) {
			enterprise = enterpriseDao.findById(id);
		}
		TbFileDao tfDao = new TbFileDao(em);
		JSONObject jo = null;
		if(enterprise!=null) {
			jo = new JSONObject();
			List<Map<String, Object>> tfs =tfDao.getListByEnterpriseIdTypeCount(enterprise.getId(),type);
			jo.put("fileCount", tfs);
		}
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", jo);
		return true;
	}
}
