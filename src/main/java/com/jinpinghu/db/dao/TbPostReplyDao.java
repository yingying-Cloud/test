package com.jinpinghu.db.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbPostReply;

@SuppressWarnings("unchecked")
public class TbPostReplyDao extends BaseZDao{

	public TbPostReplyDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	public TbPostReply findById(Integer id) {
		try {
			TbPostReply instance = getEntityManager().find(TbPostReply.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<TbPostReply> findByPostId(Integer id,String sort){
		try{
			StringBuffer sb =new StringBuffer();
			sb.append(" from TbPostReply where delFlag=:delFlag and postId =:postId order by inputTime ");
			if(sort.equals("asc"))
				sb.append(" asc ");
			else
				sb.append(" desc ");
			Query query=getEntityManager().createQuery(sb.toString());
			query.setParameter("postId", id);
			query.setParameter("delFlag",0);
			List<TbPostReply> list =query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re){
			throw re;
		}
	}
	public Integer findByPostIdCount(Integer id){
		try{
			StringBuffer sb =new StringBuffer();
			sb.append(" select count(*) from TbPostReply where delFlag=:delFlag and postId = :postId ");
			Query query=getEntityManager().createQuery(sb.toString());
			query.setParameter("postId", id);
			query.setParameter("delFlag",0);
			List<Object> list =query.getResultList();
			if(list!=null&&list.size()>0)
				return Integer.valueOf(list.get(0).toString());
			return null;
		}catch(RuntimeException re){
			throw re;
		}
	}
	
	public List<TbPostReply> findByUserId(Integer userId,Integer postId,String sort){
		try{
			StringBuffer sb =new StringBuffer();
			sb.append(" from TbPostReply where delFlag=:delFlag and userTabId = :userId and postId=:postId  ");
			sb.append(" order by postId,inputTime  ");
			if(sort.equals("asc"))
			{
				sb.append(" asc ");
			}else{
				sb.append(" desc ");
			}
			Query query=getEntityManager().createQuery(sb.toString());
			query.setParameter("delFlag",0);
			query.setParameter("userId", userId);
			query.setParameter("postId", postId);
			List<TbPostReply> list =query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re){
			throw re;
		}
	}
	
	public TbPostReply findLast(Integer postId){
		try{
			StringBuffer sb =new StringBuffer();
			sb.append(" from TbPostReply where delFlag=:delFlag and postId=:postId order by inputTime desc  limit 1  ");
			Query query=getEntityManager().createQuery(sb.toString());
			query.setParameter("postId", postId);
			query.setParameter("delFlag",0);
			List<TbPostReply> list =query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re){
			throw re;
		}
	}
	
	
	
	
	
	
	
}