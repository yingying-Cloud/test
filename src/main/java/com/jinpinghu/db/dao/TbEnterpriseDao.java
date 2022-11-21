package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbEnterprise;
import com.mysql.cj.util.StringUtils;

public class TbEnterpriseDao extends BaseZDao{

	public TbEnterpriseDao(EntityManager _em) {
		super(_em);
	}
	
	public TbEnterprise findById(Integer id) {
		try {
			TbEnterprise instance = getEntityManager().find(TbEnterprise.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbEnterprise findByToolOrderId(Integer toolOrderId) {
		try {
			String queryString = "from TbEnterprise where delFlag=0 and id in (select enterpriseId from TbResEnterpriseToolOrder where toolOrderId = :toolOrderId) ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("toolOrderId", toolOrderId);
			List<TbEnterprise> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbEnterprise findByCode(String enterpriseCreditCode) {
		try {
			String queryString = "from TbEnterprise where delFlag=0 and enterpriseCreditCode=:enterpriseCreditCode ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("enterpriseCreditCode", enterpriseCreditCode);
			List<TbEnterprise> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbEnterprise findBySyncNumber(String syncNumber) {
		try {
		String queryString = "from TbEnterprise where delFlag=0 and syncNumber=:syncNumber ";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("syncNumber", syncNumber);
		List<TbEnterprise> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	} catch (RuntimeException re) {
		throw re;
	}
	}
	public TbEnterprise findByDeviceSn(String deviceSn) {
		try {
		String queryString = "from TbEnterprise where delFlag=0 and deviceSn=:deviceSn ";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("deviceSn", deviceSn);
		List<TbEnterprise> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	} catch (RuntimeException re) {
		throw re;
	}
	}
	
	public List<Map<String, Object>> findByAll(String name,String enterpriseCreditCode,String enterpriseLegalPerson,String enterpriseLegalPersonIdcard,
			String enterpriseLinkPeople,String enterpriseLinkMobile,String enterpriseAddress,Integer enterpriseType,Integer status,Integer nowPage,
			Integer pageCount,String dscd,String selectAll,List<Integer> enterpriseIdList
			,Integer state) {
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
		sb.append("enterprise_nature as enterpriseNature,");
		sb.append("te.status, ");
		sb.append("ifnull(f.file_url,'') fileUrl, ");
		sb.append("x as x,");
		sb.append("y as y,");
		sb.append("plant_scope as plantScope,base_address baseAddres,plant_name plantName,ta.name dsnm,te.dscd  ");
		sb.append(",te.list_order listOrder,te.type,te.state,ifnull(te.changes,'') changes,  ");
		
		//sb.append( " buy.buyNum buyNum,sell.sellNum sellNum,sell.toolSum,sell.toolCount, ");
		sb.append(" date_format(tu.last_time,'%Y-%m-%d %H:%i:%s') lastTime,ifnull(tu.cash_register_version,'') cashRegisterVersion, ");
		sb.append(" ifnull(tu.cash_register_id,'') cashRegisterId,count(distinct tppo.id) plantOrderCount, ");
		sb.append(" count( DISTINCT tppo.plant_protection_id) plantServiceCount,sum(tppo.price+0) plantSum,sum(tppo.area+0) plantArea, ");
		sb.append(" if(teo.id is null,0,1) flag,");
		sb.append(" ifnull((SELECT min(inspection_type) FROM tb_inspection WHERE del_flag = 0 and enterprise_id = te.id AND STATUS = 0 ),4) QRCode,"
				+ " round(100 - IF(sum( tip.point ) > 0, sum( tip.point ), 0 ),0) score ");
		sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd  ");
		sb.append(" left join tb_res_enterprise_file rfg on rfg.enterprise_id = te.id  AND rfg.type = 2  ");
		sb.append(" left join tb_file f on f.id = rfg.file_id and f.file_type = 1 ");
		sb.append(" left join tb_res_user_enterprise rue on te.id = rue.enterprise_id ");
		sb.append(" left join tb_user tu on tu.id = rue.user_tab_id ");
		sb.append(" left join tb_inspection_point tip on tip.enterprise_id = te.id ");
		/*sb.append(" left join (select tto.tool_enterprise_id enterpriseId,sum(tsc.num) sellNum,sum(tsc.num*tsc.price) toolSum,count(distinct tto.id) toolCount ");
		sb.append(" from tb_tool_order tto left join tb_res_order_car troc on troc.order_id = tto.id ");
		sb.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
		sb.append(" where tto.status = 4 and tto.del_flag = 0 and tto.input_time >= concat(date_format(now(),'%Y-%m'),'-01 00:00:00') ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			sb.append(" and tto.tool_enterprise_id in (:enterpriseIdList) ");
		}
		sb.append(" group by tto.tool_enterprise_id) sell on sell.enterpriseId = te.id ");
		sb.append(" left join (select ttr.enterprise_id enterpriseId,sum( ttr.number) buyNum ");
		sb.append(" from tb_tool_record ttr  ");
		sb.append(" where ttr.del_flag = 0 and ttr.record_type = 1 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			sb.append(" and ttr.enterprise_id in (:enterpriseIdList) ");
		}
		sb.append(" and ttr.use_time >= concat(date_format(now(),'%Y-%m'),'-01 00:00:00') group by ttr.enterprise_id) buy on buy.enterpriseId = te.id ");*/
		sb.append(" left join tb_plant_protection_order tppo on tppo.plant_enterprise_id = te.id ");
		sb.append(" left join tb_enterprise_zero teo on te.id = teo.enterprise_id ");
		sb.append(" where te.del_Flag=0  ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			sb.append(" and te.id in (:enterpriseIdList) ");
		}
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
		if(state!=null) {
			sb.append(" and te.state=:state ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			sb.append(" and te.dscd = :dscd ");
		}
		sb.append(" group by te.id ");
		sb.append(" order by tu.last_time desc, te.list_order desc,te.enterprise_type asc,case when f.file_url is not null then 0 else 1 end,te.id desc ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
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
		if(state!=null) {
			query.setParameter("state", state);
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
			String enterpriseLinkPeople,String enterpriseLinkMobile,String enterpriseAddress,Integer enterpriseType,Integer status,String dscd,
			String selectAll,List<Integer> enterpriseIdList ,Integer state) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(id) ");
		sb.append(" from tb_enterprise te where del_Flag=0  ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			sb.append(" and te.id in (:enterpriseIdList) ");
		}
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
		if(state!=null) {
			sb.append(" and state=:state ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			sb.append(" and dscd = :dscd ");
		}
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
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
		if(state!=null) {
			query.setParameter("state", state);
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
	
	public Object getSumPlantScope(String name,String dscd) {
		try {
			String queryString = " select sum(plant_scope) from tb_enterprise te where del_flag = 0 ";
			
			if(!StringUtils.isNullOrEmpty(name)) {
				queryString+=" and te.name like :name ";
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				queryString+=" and dscd = :dscd ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			
			List <Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}


	public List<Map<String, Object>> findSourceByAll(String name,String enterpriseCreditCode,Integer enterpriseType,Integer pageCount ,Integer nowPage) {
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
			sb.append("ta.name dsnm,te.dscd  ");
			sb.append(" ,tens.source source");
			sb.append(" ,DATE_FORMAT(tens.input_Time,'%Y-%m-%d') inputTime");
			sb.append(" ,tens.capital_Ability capitalAbility");
			sb.append(" ,tens.development_Ability developmentAbility");
			sb.append(" ,tens.corporate_Credit corporateCredit");
			sb.append(" ,tens.achievement achievement");
			sb.append(" ,tens.plant_Yield plantYield");
			sb.append(" ,tens.technical_Ability technicalAbility");
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd ");
			sb.append(" left join tb_enterprise_new_grade tens on tens.enterprise_id=te.id  where te.del_Flag=0  ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and (te.name like :name or te.enterprise_legal_person like :name or te.enterprise_link_people like :name) ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
				sb.append(" and enterprise_credit_code=:enterpriseCreditCode ");
			}
			if(enterpriseType!=null) {
				sb.append(" and enterprise_type=:enterpriseType ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
				query.setParameter("enterpriseCreditCode", enterpriseCreditCode);
			}
			if(enterpriseType!=null) {
				query.setParameter("enterpriseType", enterpriseType);
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
	public Integer findSourceByAllCount(String name,String enterpriseCreditCode,Integer enterpriseType) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(te.id)");
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd   ");
			sb.append(" left join tb_enterprise_new_grade tens on tens.enterprise_id=te.id  where te.del_Flag=0 ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and (te.name like :name or te.enterprise_legal_person like :name or te.enterprise_link_people like :name) ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
				sb.append(" and enterprise_credit_code=:enterpriseCreditCode ");
			}
			if(enterpriseType!=null) {
				sb.append(" and enterprise_type=:enterpriseType ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
				query.setParameter("enterpriseCreditCode", enterpriseCreditCode);
			}
			if(enterpriseType!=null) {
				query.setParameter("enterpriseType", enterpriseType);
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
	public Map<String, Object> findSourceInfoByCode(String enterpriseCreditCode) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select te.id,");
			sb.append("te.name");
//			sb.append("enterprise_credit_code as enterpriseCreditCode,");
//			sb.append("enterprise_legal_person as enterpriseLegalPerson,");
//			sb.append("enterprise_legal_person_Idcard as enterpriseLegalPersonIdcard, ");
//			sb.append("enterprise_link_people as enterpriseLinkPeople,");
//			sb.append("enterprise_link_mobile as enterpriseLinkMobile,");
//			sb.append("enterprise_address as enterpriseAddress,");
//			sb.append("enterprise_type as enterpriseType,");
//			sb.append("te.status, ");
//			sb.append("ta.name dsnm,te.dscd  ");
			sb.append(" ,tens.source source");
			sb.append(" ,DATE_FORMAT(tens.input_Time,'%Y-%m-%d') inputTime");
			sb.append(" ,tens.capital_Ability capitalAbility");
			sb.append(" ,tens.development_Ability developmentAbility");
			sb.append(" ,tens.corporate_Credit corporateCredit");
			sb.append(" ,tens.achievement achievement");
			sb.append(" ,tens.plant_Yield plantYield");
			sb.append(" ,tens.technical_Ability technicalAbility ");
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd ");
			sb.append(" left join tb_enterprise_new_grade tens on tens.enterprise_id=te.id  where te.del_Flag=0  ");
//			if(!StringUtils.isNullOrEmpty(name)) {
//				sb.append(" and (te.name like :name or te.enterprise_legal_person like name or te.enterprise_link_people like name) ");
//			}
			if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
				sb.append(" and enterprise_credit_code=:enterpriseCreditCode ");
			}
//			if(enterpriseType!=null) {
//				sb.append(" and enterprise_type=:enterpriseType ");
//			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
//			if(!StringUtils.isNullOrEmpty(name)) {
//				query.setParameter("name", "%"+name+"%");
//			}
			if(!StringUtils.isNullOrEmpty(enterpriseCreditCode)) {
				query.setParameter("enterpriseCreditCode", enterpriseCreditCode);
			}
//			if(enterpriseType!=null) {
//				query.setParameter("enterpriseType", enterpriseType);
//			}
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


	public List<Map<String, Object>> findPlantByAll(String name,String time,Integer nowPage,Integer pageCount ) {
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
			sb.append("ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_enterprise_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.enterprise_id = te.id ");
			sb.append(" AND f.file_type = 1 and rfg.type=2  LIMIT 1 ),'') as fileUrl, ");
			sb.append("x as x,");
			sb.append("y as y,");
			sb.append("plant_scope as plantScope,base_address baseAddress,plant_name plantName,ta.name dsnm,te.dscd  ");
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd where del_Flag=0  and enterprise_type=1  ");
			if(StringUtils.isNullOrEmpty(time)&&!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and te.plant_name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(time)){
				sb.append(" and te.id in (select tw.enterprise_id from tb_work tw left join tb_brand tb on tw.crop=tb.id where date_format('start_time','%Y-%m')>=:time and date_format('end_time','%Y-%m')<=:time  ");
				if(!StringUtils.isNullOrEmpty(name)) {
					sb.append(" and tb.product_name like :name");
				}
				sb.append(")");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(time)){
				query.setParameter("time", time);
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
	public Integer findPlantByAllCount(String name,String time) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(id) ");
			sb.append(" from tb_enterprise te where del_Flag=0 and enterprise_type=1 ");
			if(StringUtils.isNullOrEmpty(time)&&!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and te.plant_name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(time)){
				sb.append(" and te.id in (select tw.enterprise_id from tb_work tw left join tb_brand tb on tw.crop=tb.id where date_format('start_time','%Y-%m')>=:time and date_format('end_time','%Y-%m')<=:time  ");
				if(!StringUtils.isNullOrEmpty(name)) {
					sb.append(" and tb.product_name like :name");
				}
				sb.append(")");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(time)){
				query.setParameter("time", time);
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

	
	public List<Map<String, Object>> findToolByAll(String type,Integer nowPage,Integer pageCount ) {
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
		sb.append("ifnull(( SELECT file_url ");
		sb.append(" FROM tb_file f INNER JOIN tb_res_enterprise_file rfg ");
		sb.append(" ON f.id = rfg.file_id  ");
		sb.append(" WHERE rfg.enterprise_id = te.id ");
		sb.append(" AND f.file_type = 1 and rfg.type=2  LIMIT 1 ),'') as fileUrl, ");
		sb.append("x as x,");
		sb.append("y as y,");
		sb.append("plant_scope as plantScope,base_address baseAddress,plant_name plantName,ta.name dsnm,te.dscd  ");
		
		sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd where del_Flag=0  and te.enterprise_type=3  ");
		if(!StringUtils.isNullOrEmpty(type)&&type.equals("2")) {
			sb.append(" and te.id in (select enterprise_id from tb_zone where del_flag=0 )");
		}
		sb.append(" order by te.enterprise_type asc,te.input_time desc ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
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
	public Integer findToolByAllCount(String type) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(id) ");
		sb.append(" from tb_enterprise te where del_Flag=0 and enterprise_type=3 ");
		if(!StringUtils.isNullOrEmpty(type)&&type.equals("2")) {
			sb.append(" and te.id in (select enterprise_id from tb_zone where del_flag=0 )");
		}
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
	public Map<String, Object> findAllById(Integer id) {
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
		sb.append("ifnull(( SELECT file_url ");
		sb.append(" FROM tb_file f INNER JOIN tb_res_enterprise_file rfg ");
		sb.append(" ON f.id = rfg.file_id  ");
		sb.append(" WHERE rfg.enterprise_id = te.id ");
		sb.append(" AND f.file_type = 1 and rfg.type=2  LIMIT 1 ),'') as fileUrl, ");
		sb.append("x as x,");
		sb.append("y as y,");
		sb.append("plant_scope as plantScope,base_address baseAddress,plant_name plantName,ta.name dsnm,te.dscd  ");
		
		sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd where del_Flag=0  ");
		if(id!=null) {
			sb.append(" and te.id=:id ");
		}
		sb.append(" order by te.enterprise_type asc,te.input_time desc ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(id!=null) {
			query.setParameter("id", id);
		}
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

	public List<Map<String, Object>> findSourceTopByAll() {
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
			sb.append("ta.name dsnm,te.dscd  ");
			sb.append(" ,tens.source source");
			sb.append(" ,DATE_FORMAT(tens.input_Time,'%Y-%m-%d') inputTime");
			sb.append(" ,tens.capital_Ability capitalAbility");
			sb.append(" ,tens.development_Ability developmentAbility");
			sb.append(" ,tens.corporate_Credit corporateCredit");
			sb.append(" ,tens.achievement achievement");
			sb.append(" ,tens.plant_Yield plantYield");
			sb.append(" ,tens.technical_Ability technicalAbility");
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd ");
			sb.append(" left join tb_enterprise_new_grade tens on tens.enterprise_id=te.id  where te.del_Flag=0  ");
			sb.append(" and te.id in (1,97,14,12,100) ");
			sb.append(" order by tens.source desc limit 5");
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
	
	public List<Map<String, Object>> statisticEnterpriseByDscdStatus(String selectAll,List<Integer> enterpriseIdList){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT te.dscd dscd,ta.name dsnm,count(*) total,count(case when te.status = 0 then te.id end) pend, ");
		queryString.append(" count(case when te.status = 1 then te.id end) success,count(case when te.status = 2 then te.id end) fail ");
		queryString.append(" FROM tb_enterprise te left join tb_area ta on te.dscd = ta.id where te.del_flag = 0 and te.enterprise_type = 3 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		queryString.append(" group by te.dscd order by te.dscd asc ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public Map<String, Object> statisticEnterpriseTotalByDscdStatus(String selectAll,List<Integer> enterpriseIdList){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT '' dscd,'合计' dsnm,count(*) total,count(case when te.status = 0 then te.id end) pend, ");
		queryString.append(" count(case when te.status = 1 then te.id end) success,count(case when te.status = 2 then te.id end) fail ");
		queryString.append(" FROM tb_enterprise te left join tb_area ta on te.dscd = ta.id where te.del_flag = 0 and te.enterprise_type = 3 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String, Object> map = (Map<String, Object>) query.getSingleResult();
		return map;
	}
	
	public List<Map<String, Object>> getToolEnterpriseList(String name,String dscd,String selectAll,List<Integer> enterpriseIdList){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT te.id,te.name FROM tb_enterprise te where te.del_flag = 0 and te.enterprise_type = 3 ");
		if(!StringUtils.isNullOrEmpty(name)) {
			queryString.append(" and te.name like :name ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			queryString.append(" and te.dscd like :dscd ");
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public List<TbEnterprise> findAllIsSync() {
		try {
		String queryString = "from TbEnterprise where delFlag=0 and enterpriseType=3 and ( isSync=0 or isSync is null )";
		Query query = getEntityManager().createQuery(queryString);
		List<TbEnterprise> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return list;
		}
		return null;
	} catch (RuntimeException re) {
		throw re;
	}
	}
	
	public String findEnterpriseFile(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ifnull(concat(file_url),'') ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_enterprise_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.enterprise_id = :id ");
			sb.append(" AND f.file_type = 1 and rfg.type=1  ");	
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id", id);
			List<String> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getEnterpriseBrandList(String name,String enterpriseId,Integer nowPage,Integer pageCount) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tb.id,"
 					+ "	product_name productName,"
 					+ " tb.status,tb.type "
// 					+ " ,tb.price,tb.unit,tb.spec,tb.describe_ "
 					+ " ,if (exists(select * "
 					+ "from tb_res_enterprise_brand trtb "
 					+ "left join tb_enterprise tr on trtb.enterprise_id=tr.id "
 					+ "where trtb.brand_id=tb.id and tr.id= trtb.enterprise_Id and trtb.del_flag=0 ) ,1 , 0 ) isIn "
 					+ " FROM "
 					+ "	tb_brand tb  left join  tb_res_enterprise_brand  trtb on tb.id=trtb.brand_id  "
 					+  "WHERE "
 					+ " tb.del_flag=0 and trtb.del_flag=0 ";
 			if(!StringUtils.isNullOrEmpty(name)) {
 				queryString += " and tb.product_name like :name ";
 			}
 			if(!StringUtils.isNullOrEmpty(enterpriseId)) {
 				queryString += " and trtb.enterprise_id = :enterpriseId ";
 			}
 			queryString += " order by isIn desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseId)) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer getEnterpriseBrandListCount(String name,String enterpriseId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	count(*) "
 					+ " FROM "
 					+ "	tb_brand tb left join  tb_res_enterprise_brand  trtb on tb.id=trtb.brand_id "
 					+  "WHERE "
 					+ " tb.del_flag=0  and trtb.del_flag=0 ";
 			if(!StringUtils.isNullOrEmpty(name)) {
 				queryString += " and tb.product_name like :name ";
 			}
 			if(!StringUtils.isNullOrEmpty(enterpriseId)) {
 				queryString += " and trtb.enterprise_id = :enterpriseId ";
 			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseId)) {
				query.setParameter("enterpriseId", enterpriseId);
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
	
	public Map<String,Object> getBuySellNumByEnterpriseId(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append( " select (select sum(ttr.number) from tb_tool_record ttr where ttr.record_type=1 and ttr.enterprise_id=:enterpriseId ) buyNum ");
			sb.append( " ,( select sum(case when tto.status =4  then tsc.num else 0 end)  " + 
					" from tb_shopping_car tsc" + 
					" inner join tb_res_order_car troc on troc.car_id=tsc.id" + 
					" inner join tb_tool_order tto on tto.id=troc.order_id" + 
					" where tto.del_flag = 0 and tto.tool_enterprise_id=:enterpriseId ) sellNum ");
 			Query query = getEntityManager().createNativeQuery(sb.toString());
 			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Map<String, Object>> getToEnterpriseList(Integer enterpriseId,String name,Integer enterpriseType, String dscd, Integer pageCount ,Integer nowPage) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select te.id,");
			sb.append("te.name,");
			sb.append("enterprise_link_people as enterpriseLinkPeople,");
			sb.append("enterprise_link_mobile as enterpriseLinkMobile,");
			sb.append("ta.name dsnm,te.dscd  ");
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd ");
			sb.append(" where te.del_Flag=0 and enterprise_type=3 ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and (te.name like :name or te.enterprise_legal_person like :name or te.enterprise_link_people like :name) ");
			}
			if(enterpriseType!=null) {
				sb.append(" and enterprise_type=:enterpriseType ");
			}
			if(enterpriseId!=null) {
				sb.append(" and te.id !=:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			sb.append(" order by te.dscd asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(enterpriseType!=null) {
				query.setParameter("enterpriseType", enterpriseType);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
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
	public List<TbEnterprise> findAllZero(String name,String dscd,String selectAll,List<Integer> enterpriseIdList,Integer nowPage ,Integer pageCount) {
		try {
		String queryString = "from TbEnterprise te where delFlag=0 and enterpriseType=3";
		if(!StringUtils.isNullOrEmpty(name)) {
			queryString +=" and te.name like :name  ";
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			queryString +=" and te.dscd = :dscd  ";
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString +=" and te.id in (:enterpriseIdList) ";
		}
		Query query = getEntityManager().createQuery(queryString);
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		if(nowPage!=null&pageCount!=null){
			query.setFirstResult((nowPage-1)*pageCount);
			query.setMaxResults(pageCount);
		}
		List<TbEnterprise> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return list;
		}
		return null;
	} catch (RuntimeException re) {
		throw re;
	}
	}
	
	public Integer findAllZeroCount(String name,String dscd,String selectAll,List<Integer> enterpriseIdList) {
		try {
		String queryString = "select count(*) from Tb_Enterprise te where del_Flag=0 and enterprise_Type=3";
		if(!StringUtils.isNullOrEmpty(name)) {
			queryString +=" and te.name like :name  ";
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			queryString +=" and te.dscd = :dscd  ";
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString +=" and te.id in (:enterpriseIdList) ";
		}
		Query query = getEntityManager().createNativeQuery(queryString);
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
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
	public List<Map<String, Object>> getSelfEnterpriseList(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select te.id,");
			sb.append("te.name,");
			sb.append("enterprise_link_people as enterpriseLinkPeople,");
			sb.append("enterprise_link_mobile as enterpriseLinkMobile,");
			sb.append("ta.name dsnm,te.dscd  ");
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd ");
			sb.append(" where te.del_Flag=0 and enterprise_type=3 ");
			if(enterpriseId!=null) {
				sb.append(" and te.id =:enterpriseId ");
			}
			sb.append(" order by te.dscd asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
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
	
	public List<Map<String,Object>> statisticZeroEnterpriseSell(String name,String dscd,String selectAll,List<Integer> enterpriseIdList,String year,Integer nowPage ,Integer pageCount) {
		try {
			StringBuffer sb = new StringBuffer();
			
		sb.append(" select te.name enterpsieName,ta.name dscdName,te.id ,a.num,a.money ");
		sb.append( " from tb_enterprise te left join tb_area ta on ta.id=te.dscd  ");
		sb.append(" left join (select cast( ifnull(sum(tsc.num),0) as decimal(10,2))num, ");
		sb.append(" cast( ifnull(sum(tsc.num * tsc.price),0) as decimal(10,2)) money,"
				+ " tto.tool_enterprise_id enterpriseId ");
		sb.append(" from tb_tool_order tto  ");
		sb.append(" left join tb_res_order_car troc on troc.order_id=tto.id ");
		sb.append(" left join tb_shopping_car tsc on tsc.id=troc.car_id ");
		sb.append(" left join tb_tool tt on tt.id=tsc.tool_id ");
		sb.append(" where tsc.uniform_price=1    ");//uniform_price 改成1
		if(!StringUtils.isNullOrEmpty(year))
			sb.append( " and DATE_FORMAT(tto.input_time,'%Y') = :year ");
		sb.append(" group by tto.tool_enterprise_id ");
		sb.append(" )a on a.enterpriseId = te.id ");
		sb.append(" where te.del_flag=0 and enterprise_type=3 ");
		if(!StringUtils.isNullOrEmpty(name)) {
			sb.append(" and te.name like :name  ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			sb.append(" and te.dscd = :dscd  ");
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			sb.append(" and te.id in (:enterpriseIdList) ");
		}
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		if(!StringUtils.isNullOrEmpty(year)) {
			query.setParameter("year", year);
		}
		if(nowPage!=null&pageCount!=null){
			query.setFirstResult((nowPage-1)*pageCount);
			query.setMaxResults(pageCount);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return list;
		}
		return null;
	} catch (RuntimeException re) {
		throw re;
	}
	}
	
	public List<Map<String, Object>> getEnterpriseList(String name, String dscd, Integer nowPage, Integer pageCount){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT te.id,te.name FROM tb_enterprise te where te.del_flag = 0 and te.enterprise_type = 3 ");
		if(!StringUtils.isNullOrEmpty(name)) {
			queryString.append(" and te.name like :name ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			queryString.append(" and te.dscd = :dscd ");
		}
		
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if(nowPage!=null&pageCount!=null){
			query.setFirstResult((nowPage-1)*pageCount);
			query.setMaxResults(pageCount);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public Integer getEnterpriseListCount(String name, String dscd){
		StringBuilder queryString = new StringBuilder();
		queryString.append(
				" SELECT count(0) "
				+ "FROM tb_enterprise te "
				+ "where te.del_flag = 0 and te.enterprise_type = 3 ");
		if(!StringUtils.isNullOrEmpty(name)) {
			queryString.append(" and te.name like :name ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			queryString.append(" and te.dscd = :dscd ");
		}
		
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if(!StringUtils.isNullOrEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		List<Object> list = query.getResultList();
		if(list != null && list.size() > 0){
			return Integer.valueOf(list.get(0).toString());
		}
		return null;
	}
	

	public Map<String, Object> getEnterpriseInfo(Integer enterpriseId){
		StringBuilder queryString = new StringBuilder();
		queryString.append(
				" SELECT te.id id, te.`name` enterpriseName, te.enterprise_legal_person legalPerson, te.business_scope businessScope,"
				+ " te.state state, count(distinct tip.id) violationCount,  round(100 - IF(sum( tip.point ) > 0, sum( tip.point ), 0 ),0) score, "
				+ "	count(case when ti.`status` =1 then ti.id end) changeCount "
				+ "from tb_enterprise te "
				+ "	LEFT JOIN tb_inspection_point tip ON tip.enterprise_id = te.id "
				+ "	LEFT JOIN tb_inspection ti ON tip.inspection_id = ti.id "
				+ "where tip.del_flag = 0 and ti.del_flag = 0 and  tip.enterprise_id = :enterpriseId and tip.year = year(CURDATE())");
				
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("enterpriseId", enterpriseId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> getEnterpriseStatus(Integer enterpriseId){
		StringBuilder queryString = new StringBuilder();
		queryString.append(
				" SELECT inspection_type QRCode "
				+ "from tb_inspection "
				+ "where del_flag = 0 and enterprise_id = :enterpriseId and status = 0 "
				+ "ORDER BY inspection_type");
				
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("enterpriseId", enterpriseId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>> getEnterpriseDayCount(String time){
		StringBuilder queryString = new StringBuilder();
		queryString.append("select te.id enterpriseId, ifnull(count(0),0) count, round(ifnull(count(0),0)/DAY(now())*100) rate "
				+ "from tb_enterprise te left join 	(("
				+ "	SELECT tto.tool_enterprise_id enterpriseId, DATE_FORMAT(tto.input_time,'%Y-%m-%d') day "
				+ "	from  tb_tool_order tto"
				+ "	where date_format(tto.input_time, '%Y-%m') = :time and tto.status = 4 and tto.del_flag = 0 "
				+ "	group by enterpriseId, DATE_FORMAT(tto.input_time,'%Y-%m-%d') "
				+ ") union ("
				+ "	SELECT tto.enterprise_id enterpriseId, DATE_FORMAT(tto.input_time,'%Y-%m-%d') day "
				+ "	from  tb_tool_record tto "
				+ "	where date_format(tto.input_time, '%Y-%m') = :time and tto.record_type = 2 and tto.del_flag = 0 "
				+ "	group by enterpriseId, DATE_FORMAT(tto.input_time,'%Y-%m-%d'))"
				+ "	) sum on te.id = sum.enterpriseId "
				+ "where te.enterprise_type = 3"
				+ "		and te.del_flag = 0 "
				+ "GROUP BY enterpriseId "
				+ "ORDER BY count desc ");
		
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		
		if(!StringUtils.isNullOrEmpty(time)) {
			query.setParameter("time", time);
		}
		
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
}
