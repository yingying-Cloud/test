package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbDevice;


public class TbDeciveDao extends BaseZDao{

	public TbDeciveDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	public TbDevice findById(Integer Id){
		try {
			TbDevice instance = getEntityManager().find(TbDevice.class, Id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getList(String deviceName,Integer baseId, String greenhousesName,Integer nowPage,Integer pageCount,Integer greenhousesId,Integer classify ){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT DISTINCT(td.id),td.device_name,td.type,td.input_time,"
					+ "  tag.greenhouses_name,tab.name,td.device_sn "
					+ " ,td.install_time,td.install_address,td.factory,td.equipment_type "
					+ "     ,td.closeInstruction,td.openInstruction,td.searchInstruction,td.classify  "
					+ " ,'' landName "
					+ "FROM tb_device td LEFT JOIN tb_enterprise tab "
					+ "		ON tab.id=td.enterprise_id "
					+ "		LEFT JOIN tb_res_device_greenhouses trdg2 "
					+ "		ON trdg2.device_id=td.id "
					+ "		LEFT JOIN tb_agricultural_greenhouses tag "
					+ "		ON tag.id=trdg2.greenhouses_id "
					+ "WHERE td.del_flag=0   ");
			if(deviceName!=null){
				sb.append("AND device_name LIKE :deviceName ");
			}
			if(baseId!=null){
				sb.append("AND td.enterprise_id = :baseId ");
			}
			if(greenhousesName!=null){
				sb.append("AND tag.greenhouses_name LIKE :greenhousesName ");
			}
			if(greenhousesId!=null){
				sb.append("AND tag.id = :greenhousesId ");
			}
			if(classify!=null){
				sb.append("AND td.classify = :classify ");
			}
			
			sb.append(" order by tag.list_order desc,td.device_name asc ");
			Query query=getEntityManager().createNativeQuery(sb.toString());
			if(deviceName!=null){
				query.setParameter("deviceName", "%"+deviceName+"%");
			}
			if(baseId!=null){
				query.setParameter("baseId", baseId);
			}
			if(greenhousesName!=null){
				query.setParameter("greenhousesName", "%"+greenhousesName+"%");
			}
			if(greenhousesId!=null){
				query.setParameter("greenhousesId", greenhousesId);
			}
			if(classify!=null){
				query.setParameter("classify", classify);
			}
			if(pageCount != null && nowPage != null) {
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Object[]> list=query.getResultList();
			if(list!=null||list.size()>0){
				return list;
			}else{
				return null;
			}
			
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getListCount(String deviceName,Integer baseId,String greenhousesName,Integer greenhousesId,Integer classify){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT COUNT( DISTINCT(td.id))  "
					+ "FROM tb_device td "
					+ "LEFT JOIN tb_res_device_greenhouses trdg "
					+ "ON trdg.device_id=td.id "
					+ "LEFT JOIN tb_agricultural_greenhouses tag "
					+ "ON tag.id=trdg.greenhouses_id "
					+ "WHERE td.del_flag=0  ");
			if(deviceName!=null){
				sb.append("AND device_name LIKE :deviceName ");
			}
			if(baseId!=null){
				sb.append("AND td.enterprise_id = :baseId ");
			}
			if(greenhousesName!=null){
				sb.append("AND tag.greenhouses_name LIKE :greenhousesName ");
			}
			if(greenhousesId!=null){
				sb.append("AND tag.id = :greenhousesId ");
			}
			if(classify!=null){
				sb.append("AND td.classify = :classify ");
			}
			Query query=getEntityManager().createNativeQuery(sb.toString());
			if(deviceName!=null){
				query.setParameter("deviceName", "%"+deviceName+"%");
			}
			if(baseId!=null){
				query.setParameter("baseId", baseId);
			}
			if(greenhousesName!=null){
				query.setParameter("greenhousesName", "%"+greenhousesName+"%");
			}
			if(greenhousesId!=null){
				query.setParameter("greenhousesId", greenhousesId);
			}
			if(classify!=null){
				query.setParameter("classify", classify);
			}
			List<Object> list=query.getResultList();
			if(list!=null||list.size()>0){
				String count=list.get(0).toString();
				return Integer.valueOf(count);
			}else{
				return null;
			}
			
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] getInfo(Integer id){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT DISTINCT(td.id),td.device_name,td.type,td.input_time,"
					+ "  	tag.greenHouses_name GreenHousesName,tab.name,td.device_sn,describe_,"
					+ " 	tag.id tagid,td.install_time,td.install_address,td.factory,td.equipment_type "
					+ "     ,td.closeInstruction,td.openInstruction,td.searchInstruction,td.classify,'' landName,asText(td.geom) area  "
					+ " FROM tb_device td LEFT JOIN tb_enterprise tab "
					+ "		ON tab.id=td.enterprise_id "
					+ "		LEFT JOIN tb_res_device_greenhouses trdg2 "
					+ "		ON trdg2.device_id=td.id "
					+ "		LEFT JOIN tb_agricultural_greenhouses tag "
					+ "		ON tag.id=trdg2.greenhouses_id "
					
					+ "WHERE td.del_flag=0 AND td.id=:id");
	
			Query query=getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id", id);
			List<Object[]> list=query.getResultList();
			if(list!=null||list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
			
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getDeviceDataList(Integer deviceId,String startTime,String endTime,Integer nowPage,Integer pageCount){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT GROUP_CONCAT(typeName) name,GROUP_CONCAT(value) value,date_format(input_time,'%Y-%m-%d %H:%i:%s') time ");
		queryString.append(" FROM `tb_device_monitoring_data` where del_flag = 0  ");
		if (deviceId != null) {
			queryString.append(" and device_id = :deviceId ");
		}
		if (!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(input_time,'%Y-%m-%d') >= :startTime ");
		}
		if (!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(input_time,'%Y-%m-%d') <= :endTime ");
		}
		queryString.append(" group by input_time order by input_time asc ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (deviceId != null) {
			query.setParameter("deviceId", deviceId);
		}
		if (!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		if(nowPage != null && pageCount != null) {
			query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public Integer getDeviceDataListCount(Integer deviceId,String startTime,String endTime){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT count(distinct input_time) ");
		queryString.append(" FROM `tb_device_monitoring_data` where del_flag = 0  ");
		if (deviceId != null) {
			queryString.append(" and device_id = :deviceId ");
		}
		if (!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(input_time,'%Y-%m-%d') >= :startTime ");
		}
		if (!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(input_time,'%Y-%m-%d') <= :endTime ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (deviceId != null) {
			query.setParameter("deviceId", deviceId);
		}
		if (!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		List<Object> list = query.getResultList();
		return Integer.valueOf(list.get(0).toString());
	}
	
	public Map<String, Object> getLastDeviceData(Integer deviceId){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT GROUP_CONCAT(typeName) name,GROUP_CONCAT(value) value,date_format(input_time,'%Y-%m-%d %H:%i:%s') time ");
		queryString.append(" FROM `tb_device_monitoring_data` where del_flag = 0  ");
		if (deviceId != null) {
			queryString.append(" and device_id = :deviceId ");
		}
		queryString.append(" group by input_time order by input_time desc limit 1 ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (deviceId != null) {
			query.setParameter("deviceId", deviceId);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
