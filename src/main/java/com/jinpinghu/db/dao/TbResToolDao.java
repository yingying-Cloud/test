package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResTool;
import com.jinpinghu.db.bean.TbTool;

public class TbResToolDao extends BaseZDao{

	public TbResToolDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbResTool checkIsExist(Integer toolId,Integer plantEnterpriseId) {
		try {
			String queryString = "from TbResTool where  delFlag = 0 and toolId=:toolId and plantEnterpriseId=:plantEnterpriseId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("toolId",toolId);
			query.setParameter("plantEnterpriseId",plantEnterpriseId);
			List<TbResTool> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
