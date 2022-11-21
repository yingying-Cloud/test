package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.bean.TbResToolFile;


public class TbResToolFileDao extends BaseZDao{

	public TbResToolFileDao(EntityManager _em) {
		super(_em);
	}
	public List<TbResToolFile> findByToolId(Integer toolId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResToolFile where toolId =:toolId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolId", toolId);
			List<TbResToolFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<TbResEnterpriseFile> findByEnterpriseId(Integer enterpriseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResEnterpriseFile where enterpriseId =:enterpriseId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			List<TbResEnterpriseFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbResEnterpriseFile> findByEnterpriseIdType(Integer enterpriseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResEnterpriseFile where enterpriseId =:enterpriseId  and (type=1 or type=2 or type=99)   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			List<TbResEnterpriseFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbResEnterpriseFile> findByEnterpriseIdType(Integer enterpriseId,List<Integer> type){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResEnterpriseFile where enterpriseId =:enterpriseId  and (type in (:type))   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			query.setParameter("type", type);
			List<TbResEnterpriseFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbResEnterpriseFile> findByEnterpriseFileId(Integer id){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResEnterpriseFile where id =:id     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("id", id);
			List<TbResEnterpriseFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
