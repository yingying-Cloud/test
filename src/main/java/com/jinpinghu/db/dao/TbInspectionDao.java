package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbArea;

public class TbInspectionDao extends BaseZDao{

	public TbInspectionDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbInspection findById(Integer id) {
		try {
			String queryString = "from TbInspection where id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbInspection> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Map<String, Object>> getInspectionList(Integer status, String enterpriseName, String startTime, String endTime,
			Integer enterpriseId, String dscd, Integer nowPage, Integer pageCount) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select ti.id id, it.`name` typeName, ii.`name` itemName, te.`name` enterpriseName,"
					+ " date_format(ti.input_time, '%Y-%m-%d %H:%i') inputTime, DATE_FORMAT(ti.input_time,'%Y年%c月%e日') date,"
					+ " dayofweek(ti.input_time)-1 `week`, ti.inspection_type typeId, ti.status status "
					+ "from tb_inspection ti left join tb_enterprise te on ti.enterprise_id = te.id "
					+ "	left join tb_inspection_item ii on ti.inspection_item_id = ii.id "
					+ "	left join tb_inspection_type it on ti.inspection_type = it.id "
					+ "where ti.del_flag = 0  ");
			if(status != null){
				sb.append(" and ti.status = :status ");
			}
			if(!StringUtil.isNullOrEmpty(enterpriseName)){
				sb.append("	and te.`name` like :enterpriseName ");
			}
			if(!StringUtil.isNullOrEmpty(startTime)){
				sb.append( " and date(ti.input_time) >= :startTime ");
			}
			if(!StringUtil.isNullOrEmpty(endTime)){
				sb.append( " and date(ti.input_time) <= :endTime ");
			}
			if(enterpriseId != null){
				sb.append( " and ti.enterprise_id = :enterpriseId ");
			}
			if(!StringUtil.isNullOrEmpty(dscd)){
				sb.append( " and te.dscd = :dscd ");
			}
			sb.append(" ORDER BY ti.input_time desc");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(status != null){
				query.setParameter("status", status);
			}
			if(!StringUtil.isNullOrEmpty(enterpriseName)){
				query.setParameter("enterpriseName", "%"+enterpriseName+"%");
			}
			if(!StringUtil.isNullOrEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtil.isNullOrEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(enterpriseId != null){
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtil.isNullOrEmpty(dscd)){
				query.setParameter("dscd", dscd);
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
	
	
	public Integer getInspectionListCount(Integer status, String enterpriseName, String startTime, String endTime,
			Integer enterpriseId, String dscd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select count(0) "
					+ "from tb_inspection ti left join tb_enterprise te on ti.enterprise_id = te.id "
					+ "where ti.del_flag = 0 ");
			if(status != null){
				sb.append(" and ti.status = :status ");
			}
			if(!StringUtil.isNullOrEmpty(enterpriseName)){
				sb.append("	and te.`name` like :enterpriseName ");
			}
			if(!StringUtil.isNullOrEmpty(startTime)){
				sb.append( " and date(ti.input_time) >= :startTime ");
			}
			if(!StringUtil.isNullOrEmpty(endTime)){
				sb.append( " and date(ti.input_time) <= :endTime ");
			}
			if(enterpriseId != null){
				sb.append( " and ti.enterprise_id = :enterpriseId");
			}
			if(!StringUtil.isNullOrEmpty(dscd)){
				sb.append( " and te.dscd = :dscd ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(status != null)
				query.setParameter("status", status);
			if(!StringUtil.isNullOrEmpty(enterpriseName)){
				query.setParameter("enterpriseName", "%"+enterpriseName+"%");
			}
			if(!StringUtil.isNullOrEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtil.isNullOrEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(enterpriseId != null){
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtil.isNullOrEmpty(dscd)){
				query.setParameter("dscd", dscd);
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0 && list.get(0) != null) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Map<String, Object> getInspectionInfo(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select ti.id id, it.`name` typeName, ii.`name` itemName, te.`name` enterpriseName,"
					+ " date_format(ti.input_time, '%Y-%m-%d %H:%i') inputTime, ti.remark remark, ti.inspection_type typeId "
					+ "from tb_inspection ti left join tb_enterprise te on ti.enterprise_id = te.id "
					+ "	left join tb_inspection_item ii on ti.inspection_item_id = ii.id "
					+ "	left join tb_inspection_type it on ti.inspection_type = it.id "
					+ "where ti.id = :id ");
			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id", id);
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
	
	
	public void delInspections(List<String> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("update tb_inspection set del_flag = 1 where id in (:ids) ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("ids", ids);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public void delInspectionPoint(List<String> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("update tb_inspection_point set del_flag = 1 where inspection_id in (:ids) ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("ids", ids);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
