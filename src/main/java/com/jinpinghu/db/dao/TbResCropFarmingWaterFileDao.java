package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResCropFarmingWaterFile;
import com.jinpinghu.db.bean.TbResToolFile;

public class TbResCropFarmingWaterFileDao extends BaseZDao {

	public TbResCropFarmingWaterFileDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<TbResCropFarmingWaterFile> findByResCropFarmingWaterId(Integer resCropFarmingWaterId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResCropFarmingWaterFile where resCropFarmingWaterId =:resCropFarmingWaterId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("resCropFarmingWaterId", resCropFarmingWaterId);
			List<TbResCropFarmingWaterFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

}
