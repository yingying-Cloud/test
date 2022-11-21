package com.jinpinghu.logic.keyword;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbKeyword;
import com.jinpinghu.db.dao.TbKeywordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.keyword.param.AddOrUpdateKeywordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrUpdateKeywordLogic extends BaseZLogic {
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddOrUpdateKeywordParam myParam = (AddOrUpdateKeywordParam)logicParam;
		Integer keyworkId = myParam.getKeyworkId();
		String name = myParam.getName();
		
		TbKeywordDao tbKeywordDao = new TbKeywordDao(em);

		if(keyworkId==null){
			//新增
			TbKeyword tbKeyword = new TbKeyword(name, new Date(), 0);
			tbKeywordDao.save(tbKeyword);
		}else{
			//更新
			TbKeyword tbKeyword = tbKeywordDao.findById(keyworkId);
			if(tbKeyword==null){
				res.add("status", -1);
				res.add("msg", "未知错误查询失败");
				return true;
			}
			tbKeyword.setName(name);
			tbKeywordDao.update(tbKeyword);
		}
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddOrUpdateKeywordParam myParam = (AddOrUpdateKeywordParam)logicParam;
		String name = myParam.getName();
		if(StringUtils.isEmpty(name)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
}
