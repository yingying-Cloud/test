package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResEnterpriseCertificateFile;


public class TbResEnterpriseCertificateFileDao extends BaseZDao{

	public TbResEnterpriseCertificateFileDao(EntityManager _em) {
		super(_em);
	}
	public List<TbResEnterpriseCertificateFile> findByEnterpriseCertificateId(Integer enterpriseCertificateId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResEnterpriseCertificateFile where enterpriseCertificateId =:enterpriseCertificateId   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseCertificateId", enterpriseCertificateId);
			List<TbResEnterpriseCertificateFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

}
