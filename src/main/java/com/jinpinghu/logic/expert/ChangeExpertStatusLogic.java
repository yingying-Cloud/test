package com.jinpinghu.logic.expert;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.dao.TbExpertDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.expert.param.ChangeExpertStatusParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ChangeExpertStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ChangeExpertStatusParam myParam = (ChangeExpertStatusParam)logicParam;
		String ids = myParam.getIds();
		String status=myParam.getStatus();
		TbExpertDao expertDao = new TbExpertDao(em);
		/*��֤�Ƿ���ڸù�˾*/
		if(!StringUtils.isNullOrEmpty(ids)&&!StringUtils.isNullOrEmpty(status)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbExpert expert = expertDao.findById(Integer.valueOf(id));
				if(expert!=null) {
					expert.setStatus(status);
					expertDao.update(expert);
				}
			}
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}
}
