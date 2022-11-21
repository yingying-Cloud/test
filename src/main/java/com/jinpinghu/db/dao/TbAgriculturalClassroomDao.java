package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbAgriculturalClassroom;
import com.mysql.cj.util.StringUtils;

public class TbAgriculturalClassroomDao extends BaseZDao {

	public TbAgriculturalClassroomDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbAgriculturalClassroom findById(Integer id) {
		try {
			String queryString = "from TbAgriculturalClassroom where id = :id  ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbAgriculturalClassroom> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Map<String, Object> findMapById(Integer id) {
		try {
			String queryString = "SELECT tal.id," 
					+" tal.type,title,date_format(input_time,'%Y-%m-%d %H:%i:%s') inputTime,tt.name typeName "
					+" FROM `tb_agricultural_classroom` tal  left join tb_type tt on tt.id=tal.type where tal.id=:id  ";
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
	public List<Map<String, Object>> getListMap(String type,String title,Integer nowPage,Integer pageCount) {
		try {
			String queryString = "SELECT tal.id,"  
					+" tal.type,title,date_format(input_time,'%Y-%m-%d %H:%i:%s') inputTime "
					+" ,( SELECT file_url "
					+" FROM tb_file f INNER JOIN tb_res_agricultural_classroom_file rfg "
					+" ON f.id = rfg.file_id  "
					+" WHERE rfg.agricultural_classroom_id = tal.id "
					+" AND f.file_type = 2  LIMIT 1 ) fileUrl,tt.name typeName  "
					+" FROM `tb_agricultural_classroom` tal left join tb_type  tt on tt.id=tal.type where 1=1 ";
			if(!StringUtils.isNullOrEmpty(type)) {
				queryString += " and tal.type=:type ";
			}
			if(!StringUtils.isNullOrEmpty(title)) {
				queryString += " and title like :title ";
			}
			queryString += " order by tal.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type", type);
			}
			if(!StringUtils.isNullOrEmpty(title)) {
				query.setParameter("title", "%"+title+"%");
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
	public Integer getListMapCount(String type,String title) {
		try {
			String queryString = "SELECT count(*) " + 
					" FROM `tb_agricultural_classroom` tal where 1=1 ";
			if(!StringUtils.isNullOrEmpty(type)) {
				queryString += " and type=:type ";
			}
			if(!StringUtils.isNullOrEmpty(title)) {
				queryString += " and title like :title ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type", type);
			}
			if(!StringUtils.isNullOrEmpty(title)) {
				query.setParameter("title", "%"+title+"%");
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
	
	public void DelFileByAgriculturalId(Integer id) {
		try {
			String queryString = "delete from  tb_res_agricultural_classroom_file WHERE agricultural_classroom_id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
