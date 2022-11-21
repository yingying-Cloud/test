package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.mysql.cj.util.StringUtils;

public class TbEnterpriseZeroDao extends BaseZDao{

	public TbEnterpriseZeroDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	public TbEnterpriseZero findById(Integer id) {
		try {
			TbEnterpriseZero instance = getEntityManager().find(TbEnterpriseZero.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbEnterpriseZero findByEnterpriseId(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbEnterpriseZero where enterpriseId=:enterpriseId and delFlag=0 ");
			Query query = getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", id);
			List <TbEnterpriseZero> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Map<String, Object>> findAllZero(List<String> ids,String enterpriseName,Integer nowPage,Integer pageCount ) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select te.id,");
		sb.append("te.name,");
		sb.append("enterprise_credit_code as enterpriseCreditCode,");
		sb.append("enterprise_legal_person as enterpriseLegalPerson,");
		sb.append("enterprise_legal_person_Idcard as enterpriseLegalPersonIdcard, ");
		sb.append("enterprise_link_people as enterpriseLinkPeople,");
		sb.append("enterprise_link_mobile as enterpriseLinkMobile,");
		sb.append("enterprise_address as enterpriseAddress,");
		sb.append("enterprise_type as enterpriseType,");
		sb.append("te.status, ");
		sb.append("ifnull(f.file_url,'') fileUrl, ");
		sb.append("x as x,");
		sb.append("y as y,");
		sb.append("plant_scope as plantScope,base_address baseAddress,plant_name plantName,ta.name dsnm,te.dscd  ");
		sb.append(" from tb_enterprise te  ");
		sb.append(" left join tb_area ta on ta.id=te.dscd ");
		sb.append(" left join tb_res_enterprise_file rfg on rfg.enterprise_id = te.id  AND rfg.type = 2  ");
		sb.append(" left join tb_file f on f.id = rfg.file_id and f.file_type = 1 where te.del_Flag=0 and te.enterprise_type = 3 ");
		if(ids!=null) {
			sb.append(" and te.id not in ( :ids )");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseName)) {
			sb.append(" and te.name like  :enterpriseName ");
		}
		sb.append(" group by te.id ");
		sb.append(" order by te.enterprise_type asc,case when f.file_url is not null then 0 else 1 end,te.id desc ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(ids!=null) {
			query.setParameter("ids", ids);
		}
		if(!StringUtils.isNullOrEmpty(enterpriseName)) {
			query.setParameter("enterpriseName", "%"+enterpriseName+"%");
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
	
	public Integer findAllZeroCount(List<String> ids,String enterpriseName) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(distinct te.id)");
		sb.append(" from tb_enterprise te  ");
		sb.append(" left join tb_area ta on ta.id=te.dscd ");
		sb.append(" left join tb_res_enterprise_file rfg on rfg.enterprise_id = te.id  AND rfg.type = 2  ");
		sb.append(" left join tb_file f on f.id = rfg.file_id and f.file_type = 1 where te.del_Flag=0 and te.enterprise_type = 3 ");
		if(ids!=null) {
			sb.append(" and te.id not in ( :ids )");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseName)) {
			sb.append(" and te.name like  :enterpriseName ");
		}
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(ids!=null) {
			query.setParameter("ids", ids);
		}
		if(!StringUtils.isNullOrEmpty(enterpriseName)) {
			query.setParameter("enterpriseName", "%"+enterpriseName+"%");
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
	
	
	public List<Map<String, Object>> findByAll(String name,String enterpriseCreditCode,String enterpriseLegalPerson,String enterpriseLegalPersonIdcard,
			String enterpriseLinkPeople,String enterpriseLinkMobile,String enterpriseAddress,Integer enterpriseType,Integer status,Integer nowPage,Integer pageCount,String dscd ) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select te.id,");
		sb.append("te.name,");
		sb.append("enterprise_credit_code as enterpriseCreditCode,");
		sb.append("enterprise_legal_person as enterpriseLegalPerson,");
		sb.append("enterprise_legal_person_Idcard as enterpriseLegalPersonIdcard, ");
		sb.append("enterprise_link_people as enterpriseLinkPeople,");
		sb.append("enterprise_link_mobile as enterpriseLinkMobile,");
		sb.append("enterprise_address as enterpriseAddress,");
		sb.append("enterprise_type as enterpriseType,");
		sb.append("te.status, ");
		sb.append("ifnull(f.file_url,'') fileUrl, ");
		sb.append("x as x,");
		sb.append("y as y,");
		sb.append("plant_scope as plantScope,base_address baseAddress,plant_name plantName,ta.name dsnm,te.dscd  ");
		sb.append(" from tb_enterprise_zero tez inner join tb_enterprise te on tez.enterprise_id=te.id left join tb_area ta on ta.id=te.dscd  ");
		sb.append(" left join tb_res_enterprise_file rfg on rfg.enterprise_id = te.id  AND rfg.type = 2  ");
		sb.append(" left join tb_file f on f.id = rfg.file_id and f.file_type = 1 where tez.del_flag=0 and te.del_Flag=0 ");
		if(!StringUtils.isNullOrEmpty(name)) {
			sb.append(" and te.name like :name ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
			sb.append(" and te.enterprise_credit_code=:enterpriseCreditCode ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLegalPerson)) {
			sb.append(" and te.enterprise_legal_person like :enterpriseLegalPerson ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLegalPersonIdcard)) {
			sb.append(" and te.enterprise_legal_person_idcard=:enterpriseLegalPersonIdcard ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLinkPeople)) {
			sb.append(" and te.enterprise_link_people like :enterpriseLinkPeople ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLinkMobile)) {
			sb.append(" and te.enterprise_link_mobile=:enterpriseLinkMobile ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseAddress)) {
			sb.append(" and te.enterprise_address like :enterpriseAddress ");
		}
		if(enterpriseType!=null) {
			sb.append(" and te.enterprise_type=:enterpriseType ");
		}
		if(status!=null) {
			sb.append(" and te.status=:status ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			sb.append(" and te.dscd = :dscd ");
		}
		sb.append(" group by te.id ");
		sb.append(" order by te.enterprise_type asc,case when f.file_url is not null then 0 else 1 end,te.id desc ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
			query.setParameter("enterpriseCreditCode", enterpriseCreditCode);
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLegalPerson)) {
			query.setParameter("enterpriseLegalPerson",  "%"+enterpriseLegalPerson+"%");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLegalPersonIdcard)) {
			query.setParameter("enterpriseLegalPersonIdcard", enterpriseLegalPersonIdcard);
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLinkPeople)) {
			query.setParameter("enterpriseLinkPeople",  "%"+enterpriseLinkPeople+"%");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLinkMobile)) {
			query.setParameter("enterpriseLinkMobile", enterpriseLinkMobile);
		}
		if(!StringUtils.isNullOrEmpty(enterpriseAddress)) {
			query.setParameter("enterpriseAddress",  "%"+enterpriseAddress+"%");
		}
		if(enterpriseType!=null) {
			query.setParameter("enterpriseType", enterpriseType);
		}
		if(status!=null) {
			query.setParameter("status", status);
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
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
	public Integer findByAllCount(String name,String enterpriseCreditCode,String enterpriseLegalPerson,String enterpriseLegalPersonIdcard,
			String enterpriseLinkPeople,String enterpriseLinkMobile,String enterpriseAddress,Integer enterpriseType,Integer status,String dscd) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(tez.id) ");
		sb.append(" from tb_enterprise_zero tez inner join tb_enterprise te on tez.enterprise_id=te.id where tez.del_Flag=0 and te.del_flag=0  ");
		if(!StringUtils.isNullOrEmpty(name)) {
			sb.append(" and te.name like :name ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
			sb.append(" and enterprise_credit_code=:enterpriseCreditCode ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLegalPerson)) {
			sb.append(" and enterprise_legal_person like :enterpriseLegalPerson ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLegalPersonIdcard)) {
			sb.append(" and enterprise_legal_person_idcard=:enterpriseLegalPersonIdcard ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLinkPeople)) {
			sb.append(" and enterprise_link_people like :enterpriseLinkPeople ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLinkMobile)) {
			sb.append(" and enterprise_link_mobile=:enterpriseLinkMobile ");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseAddress)) {
			sb.append(" and enterprise_address like :enterpriseAddress ");
		}
		if(enterpriseType!=null) {
			sb.append(" and enterprise_type=:enterpriseType ");
		}
		if(status!=null) {
			sb.append(" and status=:status ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			sb.append(" and dscd = :dscd ");
		}
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
			query.setParameter("enterpriseCreditCode", enterpriseCreditCode);
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLegalPerson)) {
			query.setParameter("enterpriseLegalPerson",  "%"+enterpriseLegalPerson+"%");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLegalPersonIdcard)) {
			query.setParameter("enterpriseLegalPersonIdcard", enterpriseLegalPersonIdcard);
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLinkPeople)) {
			query.setParameter("enterpriseLinkPeople",  "%"+enterpriseLinkPeople+"%");
		}
		if(!StringUtils.isNullOrEmpty(enterpriseLinkMobile)) {
			query.setParameter("enterpriseLinkMobile", enterpriseLinkMobile);
		}
		if(!StringUtils.isNullOrEmpty(enterpriseAddress)) {
			query.setParameter("enterpriseAddress",  "%"+enterpriseAddress+"%");
		}
		if(enterpriseType!=null) {
			query.setParameter("enterpriseType", enterpriseType);
		}
		if(status!=null) {
			query.setParameter("status", status);
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
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
	
	public List<TbEnterprise> findAllEnterprise() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbEnterprise where id in (select enterpriseId from TbEnterpriseZero where delFlag=0 ) and delFlag=0 ");
			Query query = getEntityManager().createQuery(sb.toString());
			List <TbEnterprise> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
}
