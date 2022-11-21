package com.jinpinghu.db.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbPlantWarehouse;
import com.mysql.cj.util.StringUtils;

public class TbPlantWarehouseDao extends BaseZDao{

	public TbPlantWarehouseDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	
	public List<Map<String, Object>> getBrandAllNum(Integer enterpriseId,Integer nowPage,Integer pageCount){
		try {
			String queryString = " SELECT sum(if(tt.record_type=1,tt.number,0)) inNum,sum(if(tt.record_type=2,tt.number,0)) outNum,tb.product_name productName  ";
			queryString += " FROM tb_plant_warehouse tt left join tb_brand tb on tt.brand_id = tb.id where tt.del_flag=0 ";
			if(enterpriseId != null) {
				queryString += " and tt.enterprise_id = :enterpriseId ";
			}
			queryString += " group by tt.brand_id  ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(pageCount != null && nowPage != null) {
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Integer getBrandAllNumCount(Integer enterpriseId){
		try {
			String queryString = " SELECT count(distinct tt.brand_id) ";
			queryString += " FROM tb_plant_warehouse tt left join tb_brand tb on tt.brand_id = tb.id where tt.del_flag=0 ";
			if(enterpriseId != null) {
				queryString += " and tt.enterprise_id = :enterpriseId ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getBrandAllNumByRecordType(Integer enterpriseId,Integer recordType,Integer nowPage,Integer pageCount, String startTime,String endTime,String workSn){
		try {
			String queryString = "    ";
			queryString += " SELECT product_name productName,number,date_format(tt.input_time,'%Y-%m-%d') inputTime,tt.batch_num batchNum,tt.record_type recordType,";
			queryString	+= "	tt.out_direction outDirection,tt.logistics_info logisticsInfo,tt.link_people linkPeople,tt.link_mobile linkMobile,tt.id,tw.work_sn workSn,  ";
			queryString += " tt.wrapper ";
			queryString += " FROM tb_plant_warehouse tt left join tb_brand tb on tt.brand_id = tb.id ";
			queryString += " right join tb_work tw  on tw.id = tt.work_id ";
			queryString += " where tt.del_flag=0 ";
			if(enterpriseId != null) {
				queryString += " and tt.enterprise_id = :enterpriseId ";
			}
			if(recordType != null) {
				queryString += " and tt.record_Type = :recordType ";
			}
			if(!StringUtils.isNullOrEmpty(startTime)&&!StringUtils.isNullOrEmpty(endTime)) {
				queryString += "AND tt.input_time between :startTime AND :endTime ";
			}
			if(!StringUtils.isNullOrEmpty(workSn)) {
				queryString += " and tw.work_sn like :workSn ";
			}
			queryString += " order by tt.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(recordType != null) {
				query.setParameter("recordType", recordType);
			}
			if(!StringUtils.isNullOrEmpty(workSn)) {
				query.setParameter("workSn", "%"+workSn+"%");
			}
			if(!StringUtils.isNullOrEmpty(startTime)&&!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("startTime", startTime);
				query.setParameter("endTime", endTime);
			}
			if(pageCount != null && nowPage != null) {
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Integer getBrandAllNumByRecordTypeCount(Integer enterpriseId,Integer recordType, String startTime,String endTime,String workSn){
		try {
			String queryString = " SELECT count(tw.id) ";
			queryString += " FROM tb_plant_warehouse tt left join tb_brand tb on tt.brand_id = tb.id ";
			queryString += " right join tb_work tw  on tw.id = tt.work_id ";
			queryString += " where tt.del_flag=0 ";
			if(enterpriseId != null) {
				queryString += " and tt.enterprise_id = :enterpriseId ";
			}
			if(recordType != null) {
				queryString += " and tt.record_Type = :recordType ";
			}
			if(!StringUtils.isNullOrEmpty(startTime)&&!StringUtils.isNullOrEmpty(endTime)) {
				queryString += "AND tt.input_time between :startTime AND :endTime ";
			}
			if(!StringUtils.isNullOrEmpty(workSn)) {
				queryString += " and tw.work_sn like :workSn ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(recordType != null) {
				query.setParameter("recordType", recordType);
			}
			if(!StringUtils.isNullOrEmpty(workSn)) {
				query.setParameter("workSn", "%"+workSn+"%");
			}
			if(!StringUtils.isNullOrEmpty(startTime)&&!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("startTime", startTime);
				query.setParameter("endTime", endTime);
			}
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Map<String, Object> getWarehouseInfo(Integer id){
		try {
			String queryString = "    ";
			queryString += " SELECT product_name productName,";
			queryString+="  tt.id,";
			queryString+=" tt.brand_Id brandId,";
			queryString+=" tt.work_Id workId,";
			queryString+=" tt.enterprise_Id enterpriseId,";
			queryString+=" tt.persion_Id persionId,";
			queryString+="tt.persion_Name  persionName,";
			queryString+=" tt.record_Type recordType,";
			queryString+="tt.batch_Num  batchNum,";
			queryString+="tt.out_Direction  outDirection,";
			queryString+=" tt.logistics_Info logisticsInfo,";
			queryString+=" tt.link_People linkPeople,";
			queryString+=" tt.link_Mobile linkMobile,";
			queryString+="  tt.number,";
			queryString+=" tt.odd_Number oddNumber,";
			queryString+="  tt.wrapper,";
			queryString+=" date_format(tt.input_time,'%Y-%m-%d') inputTime,";
			queryString+="  tt.price";
			queryString += " FROM tb_plant_warehouse tt left join tb_brand tb on tt.brand_id = tb.id ";
			queryString += " right join tb_work tw  on tw.id = tt.work_id ";
			queryString += " where tt.del_flag=0 ";
			if(id != null) {
				queryString += " and tt.id = :id ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(id != null) {
				query.setParameter("id", id);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	
}
