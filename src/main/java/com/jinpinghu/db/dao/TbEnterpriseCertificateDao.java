package com.jinpinghu.db.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbEnterpriseCertificate;
import com.mysql.cj.util.StringUtils;

public class TbEnterpriseCertificateDao extends BaseZDao {

	public TbEnterpriseCertificateDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	public TbEnterpriseCertificate findById(Integer id) {
		try {
			TbEnterpriseCertificate instance = getEntityManager().find(TbEnterpriseCertificate.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer findByAllCount(String name,String startTime,String endTime,Integer enterpriseId) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(id) ");
		sb.append(" from tb_enterprise_certificate te where del_Flag=0  ");
		if(!StringUtils.isNullOrEmpty(name)) {
			sb.append(" and te.softwareName like :name ");
		}
		if(enterpriseId!=null) {
			sb.append(" and te.enterprise_id = :enterpriseId ");
		}
		  if(!StringUtils.isNullOrEmpty(endTime)) {
              sb.append(" and date_format(la.publish_time,'%Y-%m-%d') <= :endTime ");
          }
          if(!StringUtils.isNullOrEmpty(startTime)) {
              sb.append(" and date_format(la.publish_time,'%Y-%m-%d') >= :startTime ");
          }
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(enterpriseId!=null) {
			 query.setParameter("enterpriseId", enterpriseId);
		}
		 if(!StringUtils.isNullOrEmpty(endTime)) {
             query.setParameter("endTime", endTime);
         }
         if(!StringUtils.isNullOrEmpty(startTime)) {
             query.setParameter("startTime", startTime);
         }
		List <Object> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		return null;
	} catch (RuntimeException re) {
		throw re;
	}
	}
	
	public List<Map<String, Object>> findByAll(String name,String startTime,String endTime, Integer nowPage, Integer pageCount,Integer enterpriseId) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append("te.id,");
		sb.append("te.software_name softwareName,");
		sb.append("te.owner,");
		sb.append("date_format(te.complete_time,'%Y-%m-%d') completeTime,");
		sb.append("date_format(te.publish_time,'%Y-%m-%d') publishTime,");
		sb.append("te.way,");
		sb.append("te.authority,");
		sb.append("te.registration_number registrationNumber,");
		sb.append("date_format(te.input_time,'%Y-%m-%d') inputTime,tes.name enterpriseName");
		sb.append(",te.certificate_type certificateType ");
		sb.append(" from tb_enterprise_certificate te inner join tb_enterprise tes on tes.id=te.enterprise_id where te.del_Flag=0  ");
		if(!StringUtils.isNullOrEmpty(name)) {
			sb.append(" and te.softwareName like :name ");
		}
		if(enterpriseId!=null) {
			sb.append(" and te.enterprise_id = :enterpriseId ");
		}
		if(!StringUtils.isNullOrEmpty(endTime)) {
			sb.append(" and date_format(te.publish_time,'%Y-%m-%d') <= :endTime ");
		}
		if(!StringUtils.isNullOrEmpty(startTime)) {
			sb.append(" and date_format(te.publish_time,'%Y-%m-%d') >= :startTime ");
		}
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(enterpriseId!=null) {
			 query.setParameter("enterpriseId", enterpriseId);
		}
		 if(!StringUtils.isNullOrEmpty(endTime)) {
		     query.setParameter("endTime", endTime);
		 }
		 if(!StringUtils.isNullOrEmpty(startTime)) {
		     query.setParameter("startTime", startTime);
		 }
		 if(nowPage!=null&pageCount!=null){
		     query.setFirstResult((nowPage-1)*pageCount);
		     query.setMaxResults(pageCount);
		 }
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
	
	
}
