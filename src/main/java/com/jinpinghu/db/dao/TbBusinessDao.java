package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.aliyuncs.utils.StringUtils;

public class TbBusinessDao extends BaseZDao {

	public TbBusinessDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	public Map<String, Object> findMapById(Integer id) {
		try {
			String queryString = "SELECT tal.id," + 
					" title,content,type,introduction,author " + 
					" FROM `tb_business` tal where tal.id=:id  ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> getListMap(String title,String author,Integer nowPage,Integer pageCount) {
		try {
			String queryString = "SELECT tal.id," + 
					" title,content,type,introduction,author, " + 
					" ( SELECT file_url "
				+" FROM tb_file f INNER JOIN tb_res_business_file rfg "
				+" ON f.id = rfg.file_id  "
				+" WHERE rfg.business_id = tal.id "
				+" AND f.file_type = 1  LIMIT 1 ) fileUrl "
				+	" FROM `tb_business` tal where 1=1 ";
			if(!StringUtils.isEmpty(title)) {
				queryString += " and title like :title ";
			}
			if(!StringUtils.isEmpty(author)) {
				queryString += " and author like :author ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(title)) {
				query.setParameter("title","%"+title+"%");
			}
			if(!StringUtils.isEmpty(author)) {
				query.setParameter("author","%"+author+"%");
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer getListMapCount(String title,String author) {
		try {
			String queryString = "SELECT count(*) " + 
					" FROM `tb_business` tal where 1=1 ";
			if(!StringUtils.isEmpty(title)) {
				queryString += " and title like :title ";
			}
			if(!StringUtils.isEmpty(author)) {
				queryString += " and author like :author ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(title)) {
				query.setParameter("title","%"+title+"%");
			}
			if(!StringUtils.isEmpty(author)) {
				query.setParameter("author","%"+author+"%");
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
