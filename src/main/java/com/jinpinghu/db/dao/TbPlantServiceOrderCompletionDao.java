package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbPlantServiceOrderCompletion;

public class TbPlantServiceOrderCompletionDao extends BaseZDao{

	public TbPlantServiceOrderCompletionDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbPlantServiceOrderCompletion findById(Integer id) {
		try {
			String queryString = " from TbPlantServiceOrderCompletion where delFlag = 0 and id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			List<TbPlantServiceOrderCompletion> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public TbPlantServiceOrderCompletion findByPlantServiceOrderId(Integer orderId) {
		try {
			String queryString = " from TbPlantServiceOrderCompletion where delFlag = 0 and plantServiceOrderId = :orderId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("orderId", orderId);
			List<TbPlantServiceOrderCompletion> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}

}
