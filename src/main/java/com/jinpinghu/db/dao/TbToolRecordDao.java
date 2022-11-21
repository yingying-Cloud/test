package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbToolRecord;

public class TbToolRecordDao extends BaseZDao{

	public TbToolRecordDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbToolRecord findById(Integer id) {
		try {
			String queryString = "from TbToolRecord where  delFlag = 0 and id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbToolRecord> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] findInfoById(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tr.id, ");
			sb.append(" te.name tnm, ");
			sb.append(" tool.name, ");
			sb.append(" tr.record_type, ");
			sb.append("tr.all_Number, ");
			sb.append("tr.number, ");
			sb.append("tr.use_name, ");
			sb.append("date_format(tr.use_time,'%Y-%m-%d %H:%i:%s'),tr.supplier_name,tool.unit,tool.type,tt.name ttnm ");
			sb.append(" ,tr.out_name,tr.out_mobile,tr.price ");
			sb.append(" from tb_tool_record tr left join tb_tool tool on tool.id=tr.tool_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" where tr.del_flag=0 ");
			sb.append(" and tr.id = :id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id",id );
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Object[]> getToolRecordList(Integer enterpriseId ,Integer toolId,String name,Integer nowPage,
			Integer pageCount,List<Integer> recordType,String startTime,String endTime,String type,String toEnterprise,String outName,String fromName
			,String selectAll,List<Integer> enterpriseIdList,String uniformPrice) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tr.id, ");
			sb.append(" te.name tnm, ");
			sb.append(" tool.name, ");
			sb.append(" tr.record_type, ");
			sb.append("tr.all_Number, ");
			sb.append("tr.number ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_record_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_record_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goods_pic ");
			
			sb.append(",tr.use_name, ");
			sb.append("date_format(tr.use_time,'%Y-%m-%d %H:%i:%s'),tr.supplier_name,tool.unit,tt.name ttnm ");
			sb.append(" ,ifnull(tr.out_name,''),ifnull(tr.out_mobile,''),tool.production_units  ,tool.uniform_price,tr.price,tool.specification  ");
			sb.append(" from tb_tool_record tr left join tb_tool tool on tool.id=tr.tool_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" where tr.del_flag=0 ");
			if (!StringUtils.isEmpty(uniformPrice)) {
				sb.append(" and tool.uniform_price = :uniformPrice ");
			}
			if(!StringUtils.isEmpty(type)) {
				sb.append(" and tool.type = :type ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tr.enterprise_id = :enterpriseId ");
			}
			if(recordType!=null && !recordType.isEmpty()) {
				sb.append(" and tr.record_type in (:recordType) ");
			}
			if(toolId!=null) {
				sb.append(" and tr.tool_id = :toolId ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and date(tr.use_time) >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and date(tr.use_time) <= :endTime ");
			}
			if(!StringUtils.isEmpty(toEnterprise)&&toEnterprise.equals("1")) {
				sb.append(" and ( tr.from_enterprise_id is not null or out_enterprise_id is not null ) ");
			}
			if(!StringUtils.isEmpty(outName)) {
				sb.append(" and tr.out_name like :outName ");
			}
			if(!StringUtils.isEmpty(fromName)) {
				sb.append(" and tr.supplier_name like :fromName ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tr.enterprise_Id  in (:enterpriseIdList) ");
			}
			sb.append(" order by tr.use_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isEmpty(uniformPrice)) {
				query.setParameter("uniformPrice", uniformPrice);
			}
			if(!StringUtils.isEmpty(type)) {
				query.setParameter("type", type);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(recordType!=null && !recordType.isEmpty()) {
				query.setParameter("recordType",recordType );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(!StringUtils.isEmpty(outName)) {
				query.setParameter("outName", "%"+outName+"%" );
			}
			if(!StringUtils.isEmpty(fromName)) {
				query.setParameter("fromName", "%"+fromName+"%" );
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer getToolRecordListCount(Integer enterpriseId ,Integer toolId,String name,List<Integer> recordType,
			String startTime,String endTime,String type,String toEnterprise,String outName,String fromName,String selectAll,
			List<Integer> enterpriseIdList,String uniformPrice) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(tr.id) ");
			sb.append(" from tb_tool_record tr left join tb_tool tool on tool.id=tr.tool_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" where tr.del_flag=0 ");
			if (!StringUtils.isEmpty(uniformPrice)) {
				sb.append(" and tool.uniform_price = :uniformPrice ");
			}
			if(!StringUtils.isEmpty(type)) {
				sb.append(" and tool.type = :type ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tr.enterprise_id = :enterpriseId ");
			}
			if(toolId!=null) {
				sb.append(" and tr.tool_id = :toolId ");
			}
			if(recordType!=null && !recordType.isEmpty()) {
				sb.append(" and tr.record_type in (:recordType) ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and date(tr.use_time) >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and date(tr.use_time) <= :endTime ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tr.enterprise_Id  in (:enterpriseIdList) ");
			}
			if(!StringUtils.isEmpty(toEnterprise)&&toEnterprise.equals("1")) {
				sb.append(" and ( tr.from_enterprise_id is not null or out_enterprise_id is not null ) ");
			}
			if(!StringUtils.isEmpty(outName)) {
				sb.append(" and tr.out_name like :outName ");
			}
			if(!StringUtils.isEmpty(fromName)) {
				sb.append(" and tr.supplier_name like :fromName ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isEmpty(uniformPrice)) {
				query.setParameter("uniformPrice", uniformPrice);
			}
			if(!StringUtils.isEmpty(type)) {
				query.setParameter("type", type);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(recordType!=null && !recordType.isEmpty()) {
				query.setParameter("recordType",recordType );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(!StringUtils.isEmpty(outName)) {
				query.setParameter("outName", "%"+outName+"%" );
			}
			if(!StringUtils.isEmpty(fromName)) {
				query.setParameter("fromName", "%"+fromName+"%" );
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
	
	public List<Object[]> statisticToolRecord(String tm,String name,String dscd,List<Integer> recordTypeList,String selectAll,List<Integer> enterpriseIdList,String uniformPrice){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tto.enterprise_id,DATE_FORMAT(tto.use_time,'%Y-%m-%d') ,sum(tto.number) ");
		queryString.append(" FROM tb_tool_record tto left join tb_enterprise te on tto.enterprise_id = te.id ");
		queryString.append(" left join tb_tool tt on tt.id = tto.tool_id ");
		queryString.append(" where tto.del_flag = 0 and DATE_FORMAT(tto.use_time,'%Y-%m') = :tm ");
		if (!StringUtils.isEmpty(uniformPrice)) {
			queryString.append(" and tt.uniform_price = :uniformPrice ");
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and tto.enterprise_id in (:enterpriseIdList) ");
		}
		if (recordTypeList != null && !recordTypeList.isEmpty()) {
			queryString.append(" and tto.record_type in (:recordTypeList) ");
		}
		if(!StringUtils.isEmpty(name)) {
			queryString.append(" and te.name like :name ");
		}
		if(!StringUtils.isEmpty(dscd)) {
			queryString.append(" and te.dscd like :dscd ");
		}
		queryString.append(" group by tto.enterprise_id,DATE_FORMAT(tto.use_time,'%Y-%m-%d') ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (!StringUtils.isEmpty(uniformPrice)) {
			query.setParameter("uniformPrice", uniformPrice);
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		query.setParameter("tm", tm);
		if (recordTypeList != null && !recordTypeList.isEmpty()) {
			query.setParameter("recordTypeList", recordTypeList);
		}
		if(!StringUtils.isEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	public List<Map<String,Object>> getInToolRecordList(Integer enterpriseId ,Integer toolId,String name,Integer nowPage,
			Integer pageCount,String startTime,String endTime,String type,String productionUnits,String code,
			String supplierName,String buyName,String selectAll,List<Integer> enterpriseIdList) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select tt.name toolName,tt.code,tt.production_units productionUnits,tp.name ,cast(ttr.number as decimal(10,2) ) number,date_format(ttr.input_time,'%Y-%m-%d') inputTime ,ttr.supplier_name supplierName,te.name enterpriseName ");
			sb.append(" from tb_tool_record ttr ");
			sb.append(" inner join tb_tool tt on tt.id=ttr.tool_id ");
			sb.append(" inner join tb_type tp on tp.id=tt.type ");
			sb.append(" inner join tb_enterprise te on te.id=ttr.enterprise_id ");
			sb.append(" where ttr.record_type=1 ");
			if(!StringUtils.isEmpty(type)) {
				sb.append(" and tt.type = :type ");
			}
			if(enterpriseId!=null) {
				sb.append(" and ttr.enterprise_id = :enterpriseId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and ttr.enterprise_id in (:enterpriseIdList) ");
			}
			if(toolId!=null) {
				sb.append(" and ttr.tool_id = :toolId ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and tt.name like :name ");
			}
			if(!StringUtils.isEmpty(productionUnits)) {
				sb.append(" and tt.production_units like :productionUnits ");
			}
			if(!StringUtils.isEmpty(code)) {
				sb.append(" and tt.code like :code ");
			}
			if(!StringUtils.isEmpty(supplierName)) {
				sb.append(" and ttr.supplier_name like :supplierName ");
			}
			if(!StringUtils.isEmpty(buyName)) {
				sb.append(" and te.name like :buyName  ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and date(ttr.use_time) >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and date(ttr.use_time) <= :endTime ");
			}
			sb.append(" order by ttr.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(type)) {
				query.setParameter("type", type);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isEmpty(buyName)) {
				query.setParameter("buyName", "%"+buyName+"%" );
			}
			if(!StringUtils.isEmpty(code)) {
				query.setParameter("code", "%"+code+"%" );
			}
			if(!StringUtils.isEmpty(supplierName)) {
				query.setParameter("supplierName", "%"+supplierName+"%" );
			}
			if(!StringUtils.isEmpty(productionUnits)) {
				query.setParameter("productionUnits", "%"+productionUnits+"%" );
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
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
	
	public List<Map<String,Object>> getOutToolRecordList(Integer enterpriseId ,Integer toolId,String name,Integer nowPage,
			Integer pageCount,String startTime,String endTime,String type,String productionUnits,String code,
			String supplierName,String buyName,Integer plantEnterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select tt.name toolName,tt.code,tt.production_units productionUnits,tp.name ,"
					+ "cast(tsc.num as decimal(10,0)) num,date_format(tto.time_complete,'%Y-%m-%d') timeComplete,"
					+ "if(tto.type=1,tes.name,tloi.name) plantEnterpriseName,tto.plant_enterprise_id plantEnterpriseId,te.name toolEnterpriseName ");
			sb.append(" from tb_shopping_car tsc ");
			sb.append(" inner join tb_res_order_car troc on troc.car_id=tsc.id ");
			sb.append(" inner join tb_tool_order tto on tto.id=troc.order_id ");
			sb.append(" inner join tb_tool tt on tt.id=tsc.tool_id "); 
			sb.append(" inner join tb_type tp on tp.id=tt.type ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id ");
			sb.append(" left join tb_enterprise tes on tes.id=tto.plant_enterprise_id and tto.type=1 ");
			sb.append(" left join tb_link_order_info tloi on tloi.id=tto.plant_enterprise_id and tto.type=2 ");
			sb.append(" where tto.status =4 and tto.del_flag = 0 ");
			if(!StringUtils.isEmpty(type)) {
				sb.append(" and tt.type = :type ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tsc.enterprise_id = :enterpriseId ");
			}
			if(plantEnterpriseId!=null) {
				sb.append(" and tto.plant_enterprise_id = :plantEnterpriseId ");
			}
			if(toolId!=null) {
				sb.append(" and tsc.tool_id = :toolId ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and tt.name like :name ");
			}
			if(!StringUtils.isEmpty(productionUnits)) {
				sb.append(" and tt.production_units like :productionUnits ");
			}
			if(!StringUtils.isEmpty(code)) {
				sb.append(" and tt.code = :code ");
			}
			if(!StringUtils.isEmpty(supplierName)) {
				sb.append(" and te.name like :supplierName ");
			}
			if(!StringUtils.isEmpty(buyName)) {
				sb.append(" and (tes.name like :buyName or tloi.name like :buyName) ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and date(tto.time_complete) >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and date(tto.time_complete) <= :endTime ");
			}
			sb.append(" order by tto.time_complete desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(type)) {
				query.setParameter("type", type);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(plantEnterpriseId!=null) {
				query.setParameter("plantEnterpriseId",plantEnterpriseId );
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isEmpty(buyName)) {
				query.setParameter("buyName", "%"+buyName+"%" );
			}
			if(!StringUtils.isEmpty(code)) {
				query.setParameter("code", code );
			}
			if(!StringUtils.isEmpty(supplierName)) {
				query.setParameter("supplierName", "%"+supplierName+"%" );
			}
			if(!StringUtils.isEmpty(productionUnits)) {
				query.setParameter("productionUnits", "%"+productionUnits+"%" );
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
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
	
	
	public Integer getInToolRecordListCount(Integer enterpriseId ,Integer toolId,String name,
			String startTime,String endTime,String type,String productionUnits,String code,
			String supplierName,String buyName,String selectAll,List<Integer> enterpriseIdList) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(*) ");
			sb.append(" from tb_tool_record ttr ");
			sb.append(" inner join tb_tool tt on tt.id=ttr.tool_id ");
			sb.append(" inner join tb_type tp on tp.id=tt.type ");
			sb.append(" inner join tb_enterprise te on te.id=ttr.enterprise_id ");
			sb.append(" where ttr.record_type=1 ");
			if(!StringUtils.isEmpty(type)) {
				sb.append(" and tt.type = :type ");
			}
			if(enterpriseId!=null) {
				sb.append(" and ttr.enterprise_id = :enterpriseId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and ttr.enterprise_id in (:enterpriseIdList) ");
			}
			if(toolId!=null) {
				sb.append(" and ttr.tool_id = :toolId ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and tt.name like :name ");
			}
			if(!StringUtils.isEmpty(productionUnits)) {
				sb.append(" and tt.production_units like :productionUnits ");
			}
			if(!StringUtils.isEmpty(code)) {
				sb.append(" and tt.code like :code ");
			}
			if(!StringUtils.isEmpty(supplierName)) {
				sb.append(" and ttr.supplier_name like :supplierName ");
			}
			if(!StringUtils.isEmpty(buyName)) {
				sb.append(" and te.name like :buyName  ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and date(ttr.use_time) >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and date(ttr.use_time) <= :endTime ");
			}
			sb.append(" order by ttr.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(type)) {
				query.setParameter("type", type);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isEmpty(buyName)) {
				query.setParameter("buyName", "%"+buyName+"%" );
			}
			if(!StringUtils.isEmpty(code)) {
				query.setParameter("code", "%"+code+"%" );
			}
			if(!StringUtils.isEmpty(supplierName)) {
				query.setParameter("supplierName", "%"+supplierName+"%" );
			}
			if(!StringUtils.isEmpty(productionUnits)) {
				query.setParameter("productionUnits", "%"+productionUnits+"%" );
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
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
	
	public Integer getOutToolRecordListCount(Integer enterpriseId ,Integer toolId,String name,
			String startTime,String endTime,String type,String productionUnits,String code,
			String supplierName,String buyName,Integer plantEnterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(*) ");
			sb.append(" from tb_shopping_car tsc ");
			sb.append(" inner join tb_res_order_car troc on troc.car_id=tsc.id ");
			sb.append(" inner join tb_tool_order tto on tto.id=troc.order_id ");
			sb.append(" inner join tb_tool tt on tt.id=tsc.tool_id "); 
			sb.append(" inner join tb_type tp on tp.id=tt.type ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id ");
			sb.append(" left join tb_enterprise tes on tes.id=tto.plant_enterprise_id and tto.type=1 ");
			sb.append(" left join tb_link_order_info tloi on tloi.id=tto.plant_enterprise_id and tto.type=2 ");
			sb.append(" where tto.status =4 and tto.del_flag = 0 ");
			if(!StringUtils.isEmpty(type)) {
				sb.append(" and tt.type = :type ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tsc.enterprise_id = :enterpriseId ");
			}
			if(plantEnterpriseId!=null) {
				sb.append(" and tto.plant_enterprise_id = :plantEnterpriseId ");
			}
			if(toolId!=null) {
				sb.append(" and tsc.tool_id = :toolId ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and tt.name like :name ");
			}
			if(!StringUtils.isEmpty(productionUnits)) {
				sb.append(" and tt.production_units like :productionUnits ");
			}
			if(!StringUtils.isEmpty(code)) {
				sb.append(" and tt.code = :code ");
			}
			if(!StringUtils.isEmpty(supplierName)) {
				sb.append(" and te.name like :supplierName ");
			}
			if(!StringUtils.isEmpty(buyName)) {
				sb.append(" and (tes.name like :buyName or tloi.name like :buyName) ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and date(tto.time_complete) >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and date(tto.time_complete) <= :endTime ");
			}
			sb.append(" order by tto.time_complete desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(type)) {
				query.setParameter("type", type);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(plantEnterpriseId!=null) {
				query.setParameter("plantEnterpriseId",plantEnterpriseId );
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isEmpty(buyName)) {
				query.setParameter("buyName", "%"+buyName+"%" );
			}
			if(!StringUtils.isEmpty(code)) {
				query.setParameter("code", code );
			}
			if(!StringUtils.isEmpty(supplierName)) {
				query.setParameter("supplierName", "%"+supplierName+"%" );
			}
			if(!StringUtils.isEmpty(productionUnits)) {
				query.setParameter("productionUnits", "%"+productionUnits+"%" );
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
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
	
	
	public List<Object[]> findAllIsSync() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select ttr.id,"
					+ "tt.sync_number,"
					+ "cast(ttr.number as decimal(10,0)) number,"
					+ "date_format(ttr.input_time,'%Y-%m-%d %H:%i:%s') inputTime ,"
					+ "record_type type,te.sync_number enterpriseNumber ,ttr.supplier_name ");
			sb.append(" from tb_tool_record ttr ");
			sb.append(" inner join tb_tool tt on tt.id=ttr.tool_id ");
			sb.append(" inner join tb_tool_catalog ttc on ttc.code=tt.code ");
			sb.append(" inner join tb_enterprise te on te.id=ttr.enterprise_id ");
			sb.append(" where (ttr.record_type=1 or record_type=2) and (ttr.is_sync=0 or ttr.is_sync is null) and ttr.del_flag=0");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] statisticPfToolRecord(Integer enterpriseId ,Integer toolId,String name,List<Integer> recordType,
			String startTime,String endTime,String type,String toEnterprise,String outName,String fromName,String selectAll,
			List<Integer> enterpriseIdList,String uniformPrice) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" ifnull(sum(tr.number+0),0),ifnull(sum(((tr.number+0) * tr.price)),0) ");
			sb.append(" from tb_tool_record tr left join tb_tool tool on tool.id=tr.tool_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" where tr.del_flag=0 ");
			if(!StringUtils.isEmpty(type)) {
				sb.append(" and tool.type = :type ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tr.enterprise_id = :enterpriseId ");
			}
			if(recordType!=null && !recordType.isEmpty()) {
				sb.append(" and tr.record_type in (:recordType) ");
			}
			if(toolId!=null) {
				sb.append(" and tr.tool_id = :toolId ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and date(tr.use_time) >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and date(tr.use_time) <= :endTime ");
			}
			if(!StringUtils.isEmpty(toEnterprise)&&toEnterprise.equals("1")) {
				sb.append(" and ( tr.from_enterprise_id is not null or out_enterprise_id is not null ) ");
			}
			if(!StringUtils.isEmpty(outName)) {
				sb.append(" and tr.out_name like :outName ");
			}
			if(!StringUtils.isEmpty(fromName)) {
				sb.append(" and tr.supplier_name like :fromName ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tr.enterprise_Id  in (:enterpriseIdList) ");
			}
			if (!StringUtils.isEmpty(uniformPrice)) {
				sb.append(" and tool.uniform_price = :uniformPrice ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(type)) {
				query.setParameter("type", type);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(recordType!=null && !recordType.isEmpty()) {
				query.setParameter("recordType",recordType );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(!StringUtils.isEmpty(outName)) {
				query.setParameter("outName", "%"+outName+"%" );
			}
			if(!StringUtils.isEmpty(fromName)) {
				query.setParameter("fromName", "%"+fromName+"%" );
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if (!StringUtils.isEmpty(uniformPrice)) {
				query.setParameter("uniformPrice", uniformPrice);
			}
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
}
