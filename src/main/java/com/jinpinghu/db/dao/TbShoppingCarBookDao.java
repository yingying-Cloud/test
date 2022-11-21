package com.jinpinghu.db.dao;

import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.jinpinghu.db.bean.TbShoppingCarBook;
import com.jinpinghu.db.dao.BaseZDao;

import fw.jbiz.jpa.ZJpaHelper;

public class TbShoppingCarBookDao extends BaseZDao{

	public TbShoppingCarBookDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public List<TbShoppingCarBook> findByUserIdAndToolId(Integer orderBookId) {
		ZJpaHelper.log("finding WwInfo instance", Level.INFO, null);
		try {
			String queryString = "from TbShoppingCarBook where delFlag = 0 ";
			if(orderBookId != null)
				queryString += " and orderBookId = :orderBookId ";
			Query query = getEntityManager().createQuery(queryString);
			if(orderBookId != null)
				query.setParameter("orderBookId", orderBookId);
			List<TbShoppingCarBook> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}	
	
}
