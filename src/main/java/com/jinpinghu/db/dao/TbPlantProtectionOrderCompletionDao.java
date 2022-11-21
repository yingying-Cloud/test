package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbPlantProtectionOrderCompletion;

public class TbPlantProtectionOrderCompletionDao extends BaseZDao{

	public TbPlantProtectionOrderCompletionDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbPlantProtectionOrderCompletion findById(Integer id) {
		try {
			String queryString = " from TbPlantProtectionOrderCompletion where delFlag = 0 and id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			List<TbPlantProtectionOrderCompletion> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public TbPlantProtectionOrderCompletion findByPlantProtectionOrderId(Integer orderId) {
		try {
			String queryString = " from TbPlantProtectionOrderCompletion where delFlag = 0 and plantProtectionOrderId = :orderId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("orderId", orderId);
			List<TbPlantProtectionOrderCompletion> list = query.getResultList();
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
