package com.jinpinghu.logic.agriculturalAdvice;

import java.util.Date;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.db.bean.TbAgriculturalAdvice;
import com.jinpinghu.db.dao.TbAgriculturalAdviceDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalAdvice.param.AddAgriculturalAdviceParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddAgriculturalAdviceLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddAgriculturalAdviceParam myParam = (AddAgriculturalAdviceParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		String type = myParam.getType();
		String title = myParam.getTitle();
		String author = myParam.getAuthor();
		String adviceType = myParam.getAdviceType();
		String content = myParam.getContent();
		
		TbAgriculturalAdviceDao tbagriculturalAdviceDao = new TbAgriculturalAdviceDao(em);
		TbAgriculturalAdvice advice = null;
		if(id!=null) {
			advice = tbagriculturalAdviceDao.findById(id);
		}
		if(advice!=null) {
			advice.setAdviceType(adviceType);
			advice.setAuthor(author);
			advice.setContent(content);
			advice.setTitle(title);
			advice.setType(type);
			tbagriculturalAdviceDao.update(advice);
		}else {
			advice = new TbAgriculturalAdvice();
			advice.setAdviceType(adviceType);
			advice.setAuthor(author);
			advice.setContent(content);
			advice.setInputTime(new Date());
			advice.setTitle(title);
			advice.setType(type);
			tbagriculturalAdviceDao.save(advice);
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
