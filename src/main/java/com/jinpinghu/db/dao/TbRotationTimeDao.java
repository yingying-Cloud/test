package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbRotationTime;
import com.mysql.cj.util.StringUtils;

public class TbRotationTimeDao extends BaseZDao{

	public TbRotationTimeDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	public TbRotationTime findById(Integer id) {
		try {
			String queryString = "from TbRotationTime where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbRotationTime> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Map<String, Object> findNew() {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id rotationId,rotation_time rotationTime  ");
		sb.append(" from tb_rotation_time ttt where del_flag=0 order by ttt.input_time desc limit 1 ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List <Map<String,Object>> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> findAll(Integer nowPage,Integer pageCount) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id rotationId,rotation_time rotationTime,date_format(input_time,'%Y-%m-%d %H:%i:%s') inputTime  ");
		sb.append(" from tb_rotation_time ttt where del_flag=0 order by ttt.input_time desc  ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List <Map<String,Object>> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return list;
		}
		return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Integer findAllCount() {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(id)  ");
		sb.append(" from tb_rotation_time ttt where del_flag=0   ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		List <Object> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
