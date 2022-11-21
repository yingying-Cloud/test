package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbGroup;

public class TbGroupDao extends BaseZDao{

	public TbGroupDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<TbGroup> findByParentId(Integer parentId){
		try{
			StringBuffer sb =new StringBuffer();
			sb.append(" from  TbGroup where delFlag=0 and parentId=:parentId ");
			Query query = getEntityManager().createQuery(sb.toString());
			query.setParameter("parentId", parentId);
			List<TbGroup> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re){
			throw re;
		}
	}
	public List<TbGroup> findAllParentId( ){
		try{
			StringBuffer sb =new StringBuffer();
			sb.append(" from  TbGroup where delFlag=0 and (parentId is null or parentId ='') ");
			Query query = getEntityManager().createQuery(sb.toString());
			List<TbGroup> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re){
			throw re;
		}
	}
}
