package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResOrderInfoFile;

public class TbResOrderInfoFileDao extends BaseZDao{

	public TbResOrderInfoFileDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<TbResOrderInfoFile> findByOrderInfoId(Integer orderInfoId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResOrderInfoFile where orderInfoId =:orderInfoId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("orderInfoId", orderInfoId);
			List<TbResOrderInfoFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
