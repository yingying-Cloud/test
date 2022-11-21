package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResAdvertisementFile;
import com.jinpinghu.db.bean.TbResBrandSaleFile;

public class TbResAdvertisementFileDao extends BaseZDao {

	public TbResAdvertisementFileDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<TbResAdvertisementFile> findByAdvertisementId(Integer advertisementId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResAdvertisementFile where advertisementId =:advertisementId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("advertisementId", advertisementId);
			List<TbResAdvertisementFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
