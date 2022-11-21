package com.jinpinghu.logic.keyword;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbKeyword;
import com.jinpinghu.db.dao.TbKeywordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.keyword.param.DeleteKeywordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DeleteKeywordLogic extends BaseZLogic {
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DeleteKeywordParam myParam = (DeleteKeywordParam)logicParam;
		String keyworkIds = myParam.getKeyworkIds();
		

		TbKeywordDao tbKeywordDao = new TbKeywordDao(em);
		
		int count = 0;
		String[] keyworkId = keyworkIds.split(",");
		for (int i = 0; i < keyworkId.length; i++) {
			TbKeyword tbKeyword = tbKeywordDao.findById(Integer.valueOf(keyworkId[i]));
			if(tbKeyword!=null){
				tbKeyword.setDelFlag(1);
				tbKeywordDao.update(tbKeyword);
				count++;
			}
		}
		res.add("status", 1);
		res.add("msg", "成功删除"+count+"项");
		return true;
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DeleteKeywordParam myParam = (DeleteKeywordParam)logicParam;
		String keyworkIds = myParam.getKeyworkIds();
		if(StringUtils.isEmpty(keyworkIds)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
}
