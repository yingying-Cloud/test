package com.jinpinghu.db.dao;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbSmsInfo;


public class TbSmsInfoDao extends BaseZDao {

	public TbSmsInfoDao(EntityManager _em) {
		super(_em);
	}

	public TbSmsInfo findById(Integer id) {
		try {
			TbSmsInfo instance = getEntityManager().find(TbSmsInfo.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
