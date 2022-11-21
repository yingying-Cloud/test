package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbFaceData;

public class TbFaceDataDao extends BaseZDao {

	public TbFaceDataDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbFaceData findByIdcard(String idcard){
		try{
			StringBuffer sb =new StringBuffer();
			sb.append(" from  TbFaceData where idcard like :idcard ");
			Query query = getEntityManager().createQuery(sb.toString());
			query.setParameter("idcard", "%"+idcard+"%");
			List<TbFaceData> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re){
			throw re;
		}
	}

}
