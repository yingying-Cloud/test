package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbKeyword;

@SuppressWarnings("unchecked")
public class TbKeywordDao extends BaseZDao {

	public TbKeywordDao(EntityManager _em) {
		super(_em);
	}

	public TbKeyword findById(Integer id) {
		try {
			TbKeyword instance = getEntityManager().find(TbKeyword.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<TbKeyword> findAll() {
		try {
			String queryString = "from TbKeyword where delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			List<TbKeyword> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<TbKeyword> findByPostId(Integer postId){
		try {
			String queryString = "from TbKeyword where delFlag = 0 and id in (select keywordId  from TbResPostKeyword where postId = :postId ) ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("postId", postId);
			List<TbKeyword> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
}

