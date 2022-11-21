package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbEnterpriseUserinfo;

public class TbEnterpriseUserinfoDao extends BaseZDao{

	public TbEnterpriseUserinfoDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public TbEnterpriseUserinfo findById(Integer id) {
		try {
			String queryString = "from TbEnterpriseUserinfo where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbEnterpriseUserinfo> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public void delEnterpriseUserinfoById(Integer id) {
		try {
			String queryString = 
					 " UPDATE "
					+ "	Tb_Enterprise_Userinfo eu LEFT JOIN Tb_res_Enterprise_Userinfo_file uf ON eu.id=uf.enterprise_userinfo_id "
					+ "SET uf.del_flag=1 , eu.del_flag=1 "
					+ "WHERE eu.id = :id";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public Object[] getEnterpriseUserinfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	eu.id,"
 					+ "	te.name tname,"
 					+ " eu.name ename,"
 					+ " eu.id_card,"
 					+ " eu.mobile,"
 					+ " eu.type,"
 					+ " eu.address,"
 					+ "	eu.sex,"
 					+ "	eu.input_time,"
 					+ " tt.name typename, "
 					+ " eu.major major, "
 					+ " eu.education education, "
 					+ " eu.company company, "
 					+ " eu.title title "
 					+ "FROM "
 					+ "	tb_enterprise_userinfo eu LEFT JOIN tb_enterprise te ON eu.enterprise_id=te.id "
 					+ " LEFT JOIN tb_type tt ON tt.id=eu.type "
 					+ "WHERE "
 					+ " eu.del_flag=0 AND te.del_flag=0 "
 					+ " AND eu.id = :id ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
				query.setParameter("id",id);
			
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Object[]> getEnterpriseUserinfoList(String name, String enterpriseId,Integer nowPage, Integer pageCount,String type,String sex) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	eu.id,"
 					+ "	te.name tname,"
 					+ " eu.name ename,"
 					+ " eu.id_card,"
 					+ " eu.mobile,"
 					+ " eu.type,"
 					+ " eu.address,"
 					+ "	eu.sex,"
 					+ "	eu.input_time,"
 					+ " tt.name typename, "
 					+ " eu.major major, "
 					+ " eu.education education, "
 					+ " eu.title title, "
 					+ " eu.company company "
 					+ "FROM "
 					+ "	tb_enterprise_userinfo eu LEFT JOIN tb_enterprise te ON eu.enterprise_id=te.id "
 					+ " LEFT JOIN tb_type tt ON tt.id=eu.type "
 					+ "WHERE "
 					+ " eu.del_flag=0  AND te.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(name))
				queryString += " AND eu.name LIKE :name ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND eu.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(type))
				queryString += " AND eu.type = :type ";
			if(!StringUtil.isNullOrEmpty(sex))
				queryString += " AND eu.sex = :sex ";
			queryString+=" ORDER BY eu.input_time DESC";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(name))
				query.setParameter("name","%"+name+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(type))
				query.setParameter("type",type);
			if(!StringUtil.isNullOrEmpty(sex))
				query.setParameter("sex",sex);
			if(pageCount != null && nowPage != null) {
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getEnterpriseUserinfoListCount(String name, String enterpriseId,String type,String sex) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	COUNT(1) "
 					+ "FROM "
 					+ "	tb_enterprise_userinfo eu "
 					+ "WHERE "
 					+ " eu.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(name))
				queryString += " AND eu.name LIKE :name ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND eu.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(type))
				queryString += " AND eu.type = :type ";
			if(!StringUtil.isNullOrEmpty(sex))
				queryString += " AND eu.sex = :sex ";
			queryString+=" ORDER BY eu.input_time DESC";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(name))
				query.setParameter("name","%"+name+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(type))
				query.setParameter("type",type);
			if(!StringUtil.isNullOrEmpty(sex))
				query.setParameter("sex",sex);
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				String count=list.get(0).toString();
				return Integer.valueOf(count);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getEnterpriseUserinfoTypeCount(Integer enterpriseId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	COUNT(id) count,type "
 					+ "FROM "
 					+ "	tb_enterprise_userinfo eu "
 					+ "WHERE "
 					+ " eu.del_flag=0 ";
			if(enterpriseId!=null) {
				queryString += " AND eu.enterprise_id = :enterpriseId ";
			}
			queryString+=" group BY eu.type";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			List <Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	
}
