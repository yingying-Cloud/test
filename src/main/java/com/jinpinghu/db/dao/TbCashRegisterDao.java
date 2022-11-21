package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbCashRegister;

public class TbCashRegisterDao extends BaseZDao {

	public TbCashRegisterDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbCashRegister findById(Integer id) {
		String queryString = " from TbCashRegister where id = :id ";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("id", id);
		List<TbCashRegister> list = query.getResultList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public TbCashRegister findByCashRegisterId(String cashRegisterId) {
		String queryString = " from TbCashRegister where delFlag = 0 and cashRegisterId = :cashRegisterId ";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("cashRegisterId", cashRegisterId);
		List<TbCashRegister> list = query.getResultList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> getCashRegisterInfo(Integer id){
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT tcr.id id,tcr.cash_register_id cashRegisterId,tcr.baidu_aiface_sn baiduAifaceSn, ");
		queryString.append(" GROUP_CONCAT(distinct tu.name) userName, GROUP_CONCAT(distinct te.name) enterpriseName, ");
		queryString.append(" date_format(tu.last_time,'%Y-%m-%d %H:%i:%s') lastTime,date_format(tcr.input_time,'%Y-%m-%d %H:%i:%s') addTime FROM tb_cash_register tcr ");
		queryString.append(" left join tb_user tu on tcr.cash_register_id = tu.cash_register_id ");
		queryString.append(" left join tb_res_user_enterprise tue on tue.user_tab_id = tu.id ");
		queryString.append(" left join tb_enterprise te on te.id = tue.enterprise_id ");
		queryString.append(" where tcr.del_flag = 0 and tcr.id = :id ");
		queryString.append(" group by tcr.id ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("id", id);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>> getCashRegisterList(String name,String cashRegisterId,String baiduAifaceSn,Integer nowPage,Integer pageCount){
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT tcr.id id,tcr.cash_register_id cashRegisterId,ifnull(tcr.baidu_aiface_sn,'') baiduAifaceSn, ");
		queryString.append(" GROUP_CONCAT(distinct tu.name) userName, GROUP_CONCAT(distinct te.name) enterpriseName, ");
		queryString.append(" date_format(tu.last_time,'%Y-%m-%d %H:%i:%s') lastTime,date_format(tcr.input_time,'%Y-%m-%d %H:%i:%s') addTime FROM tb_cash_register tcr ");
		queryString.append(" left join tb_user tu on tcr.cash_register_id = tu.cash_register_id ");
		queryString.append(" left join tb_res_user_enterprise tue on tue.user_tab_id = tu.id ");
		queryString.append(" left join tb_enterprise te on te.id = tue.enterprise_id ");
		queryString.append(" where tcr.del_flag = 0 ");
		if (!StringUtils.isEmpty(name)) {
			queryString.append(" and (tu.name like :name or te.name like :name) ");
		}
		if (!StringUtils.isEmpty(cashRegisterId)) {
			queryString.append(" and tcr.cash_register_id like :cashRegisterId ");
		}
		if (!StringUtils.isEmpty(baiduAifaceSn)) {
			queryString.append(" and tcr.baidu_aiface_sn like :baiduAifaceSn ");
		}
		queryString.append(" group by tcr.id order by tcr.input_time desc ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (!StringUtils.isEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if (!StringUtils.isEmpty(cashRegisterId)) {
			query.setParameter("cashRegisterId", "%"+cashRegisterId+"%");
		}
		if (!StringUtils.isEmpty(baiduAifaceSn)) {
			query.setParameter("baiduAifaceSn", "%"+baiduAifaceSn+"%");
		}
		if(nowPage != null && pageCount != null) {
			query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		return list;
	}
	
	public Integer getCashRegisterCount(String name,String cashRegisterId,String baiduAifaceSn){
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT count(distinct tcr.id) FROM tb_cash_register tcr ");
		queryString.append(" left join tb_user tu on tcr.cash_register_id = tu.cash_register_id ");
		queryString.append(" left join tb_res_user_enterprise tue on tue.user_tab_id = tu.id ");
		queryString.append(" left join tb_enterprise te on te.id = tue.enterprise_id ");
		queryString.append(" where tcr.del_flag = 0  ");
		if (!StringUtils.isEmpty(name)) {
			queryString.append(" and (tu.name like :name or te.name like :name) ");
		}
		if (!StringUtils.isEmpty(cashRegisterId)) {
			queryString.append(" and tcr.cash_register_id like :cashRegisterId ");
		}
		if (!StringUtils.isEmpty(baiduAifaceSn)) {
			queryString.append(" and tcr.baidu_aiface_sn like :baiduAifaceSn ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (!StringUtils.isEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if (!StringUtils.isEmpty(cashRegisterId)) {
			query.setParameter("cashRegisterId", "%"+cashRegisterId+"%");
		}
		if (!StringUtils.isEmpty(baiduAifaceSn)) {
			query.setParameter("baiduAifaceSn", "%"+baiduAifaceSn+"%");
		}
		List<Object> list = query.getResultList();
		return Integer.valueOf(list.get(0).toString());
	}

}
