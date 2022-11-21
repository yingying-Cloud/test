package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.mysql.cj.util.StringUtils;

public class TbToolCatalogDao extends BaseZDao{

	public TbToolCatalogDao(EntityManager _em) {
		super(_em);
		// TODO �Զ����ɵĹ��캯�����
	}
	public TbToolCatalog findById(Integer id) {
		try {
			String queryString = " from TbToolCatalog where  delFlag = 0 and id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbToolCatalog> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbToolCatalog findByIdStatus(Integer id) {
		try {
			String queryString = " from TbToolCatalog where  delFlag = 0 and id=:id and status=1 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbToolCatalog> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbToolCatalog findByCode(String code,Integer id) {
		try {
			String queryString = "from TbToolCatalog where  delFlag = 0 and code=:code ";
			if(id!=null) {
				queryString+=" and id!=:id";
			}
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("code",code);
			if(id!=null) {
				query.setParameter("id",id);
			}
			List<TbToolCatalog> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbToolCatalog findByCodeUserId(String code,String userId) {
		try {
			String queryString = "from TbToolCatalog where  delFlag = 0 and code=:code ";
			if(!StringUtils.isNullOrEmpty(userId)) {
				queryString+=" and userId=:userId";
			}
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("code",code);
			if(!StringUtils.isNullOrEmpty(userId)) {
				query.setParameter("userId",userId);
			}
			List<TbToolCatalog> list = query.getResultList();
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
			sb.append(" tool.id, ");
			sb.append(" tool.name, ");
			sb.append(" model, ");
			sb.append(" specification, ");
			sb.append(" unit,");
			sb.append(" price, ");
			sb.append(" number, ");
			sb.append(" describe_, ");
			sb.append(" tool.type,tool.supplier_name ");
			sb.append(" ,production_units ");
			sb.append(" ,registration_certificate_number ");
			sb.append(" ,explicit_ingredients ");
			sb.append(" ,effective_ingredients ");
			sb.append(" ,implementation_standards ");
			sb.append(" ,dosage_forms ");
			sb.append(" ,product_attributes ");
			sb.append(" ,toxicity ");
			sb.append(" ,quality_grade ");
			sb.append(" ,uniform_price ");
			sb.append(" ,code,tt.name typeName ");
			sb.append(" ,wholesale_price,remark,tu.name userName ,tu.mobile,date_format(tool.input_time,'%Y-%m-%d') inputTime,te.name enterpriseName"
					+ ",tool.hfzc"
					+ ",date_format(tool.approval_end_date,'%Y-%m-%d') " + 
					",tool.approval_no" + 
					",tool.approve_no" + 
					",tool.certificate_no" + 
					",tool.check_health_no" + 
					",tool.health_no" + 
					",date_format(tool.limit_date,'%Y-%m-%d') " + 
					",tool.produced" + 
					",tool.production_no,tool.n,tool.p,tool.k,tool.qt,tool.qr_code   ");
			//nysx字段
			sb.append(",tool.NPK,tool.NP,tool.NK,tool.PK,tool.zjzl,tool.yxcf_unit,tool.nysx ");
			sb.append(" from tb_tool_catalog tool left join tb_type tt on tt.id=tool.type  ");
			sb.append(" LEFT JOIN  tb_user tu on tu.user_id=tool.user_id ");
			sb.append(" LEFT JOIN tb_res_user_enterprise rue ON rue.user_tab_id=tu.id ");
			sb.append(" LEFT JOIN tb_enterprise te ON te.id=rue.enterprise_id ");
			
			sb.append(" where tool.del_flag=0 ");
			sb.append(" and tool.id = :id ");
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
	
	public List<Object[]> findByName(String name,
			Integer nowPage,Integer pageCount,String supplierName,String type,String unit,String code,String uniformPrice,List<String> ids,String status
			,String productAttributes) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tool.id, ");
			sb.append(" tool.name, ");
			sb.append(" model, ");
			sb.append(" specification, ");
			sb.append(" unit,");
			sb.append(" price, ");
			sb.append(" number, ");
			sb.append(" describe_, ");
			sb.append(" tool.type ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_catalog_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_catalog_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goods_pic,tool.supplier_name,tool.production_units,tool.uniform_price,tt.name typeName,tool.code  ");
			
			sb.append(" ,registration_certificate_number ");
			sb.append(" ,explicit_ingredients ");
			sb.append(" ,effective_ingredients ");
			sb.append(" ,implementation_standards ");
			sb.append(" ,dosage_forms ");
			sb.append(" ,product_attributes ");
			sb.append(" ,toxicity ");
			sb.append(" ,quality_grade ");
			sb.append(" ,tool.status ");
			sb.append(" ,tool.wholesale_price,remark,tu.name userName ,tu.mobile,date_format(tool.input_time,'%Y-%m-%d') inputTime,te.name enterpriseName  ");
			sb.append(",ifnull(tool.NPK,''),ifnull(tool.NP,''),ifnull(tool.NK,''),ifnull(tool.PK,''),"
					+ "ifnull(tool.zjzl,''),ifnull(tool.yxcf_unit,''),ifnull(tool.N,''),ifnull(tool.P,''),ifnull(tool.K,''),ifnull(tool.qt,'') ");
//			sb.append(",( select count(*) from tb_tool tl where tl.code=tool.code and (record_no is not null or record_no !='')  )  ");
			
			sb.append(" from tb_tool_catalog tool left join tb_type tt on tt.id=tool.type ");
			
			sb.append(" LEFT JOIN  tb_user tu on tu.user_id=tool.user_id ");
			sb.append(" LEFT JOIN tb_res_user_enterprise rue ON rue.user_tab_id=tu.id ");
			sb.append(" LEFT JOIN tb_enterprise te ON te.id=rue.enterprise_id ");
			
			sb.append(" where tool.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" AND (tool.code like :code or tool.registration_certificate_number like :code) ");
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				sb.append(" and tool.production_units like :unit ");
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				sb.append(" and tool.supplier_name like :supplierName ");
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and tool.type = :type ");
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				sb.append(" AND tool.uniform_price = :uniformPrice ");
			}
			if(ids!=null) {
				sb.append(" AND tool.id not in (:ids )");
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				sb.append(" AND tool.status = :status ");
			}
			if(!StringUtil.isNullOrEmpty(productAttributes)) {
				sb.append(" AND tool.product_attributes = :productAttributes ");
			}
			sb.append(" order by  tool.status asc,tool.input_time desc");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", "%"+code+"%");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit","%"+unit+"%" );
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				query.setParameter("supplierName","%"+supplierName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type",type );
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				query.setParameter("uniformPrice",uniformPrice );
			}
			if(ids!=null) {
				query.setParameter("ids",ids );
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				query.setParameter("status",status );
			}
			if(!StringUtil.isNullOrEmpty(productAttributes)) {
				query.setParameter("productAttributes",productAttributes );
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
	public Integer findByNameCount(String name,String supplierName,String type,String unit,String code,String uniformPrice,List<String> ids,
			String status,String productAttributes ) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(tool.id) ");
			sb.append(" from tb_tool_catalog tool   ");
			sb.append(" where tool.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" AND (tool.code like :code or tool.registration_certificate_number like :code) ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				sb.append(" and tool.production_units like :unit ");
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				sb.append(" and tool.supplier_name like :supplierName ");
			}
			if(!StringUtil.isNullOrEmpty(type)) {
				sb.append(" AND tool.type = :type ");
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				sb.append(" AND tool.uniform_price = :uniformPrice ");
			}
			if(ids!=null) {
				sb.append(" AND tool.id not in (:ids )");
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				sb.append(" AND tool.status = :status ");
			}
			if(!StringUtil.isNullOrEmpty(productAttributes)) {
				sb.append(" AND tool.product_attributes = :productAttributes ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", "%"+code+"%");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit","%"+unit+"%" );
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				query.setParameter("supplierName","%"+supplierName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type",type );
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				query.setParameter("uniformPrice",uniformPrice );
			}
			if(ids!=null) {
				query.setParameter("ids",ids );
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				query.setParameter("status",status );
			}
			if(!StringUtil.isNullOrEmpty(productAttributes)) {
				query.setParameter("productAttributes",productAttributes );
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
	
	public List<Map<String, Object>> findNotAddToolCatalogList(Integer enterpriseId,List<Integer> toolIdList){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tool.id,te.name enterpriseName, ");
			sb.append(" tool.name, ");
			sb.append(" model, ");
			sb.append(" specification, ");
			sb.append(" unit,");
			sb.append(" price, ");
			sb.append(" number, ");
			sb.append(" describe_ 'describe', ");
			sb.append(" type ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_catalog_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_catalog_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goodsPic,tool.production_units productionUnits,tool.uniform_price uniformPrice ");
			sb.append(" from tb_tool tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" where tool.del_flag=0 ");
			if(enterpriseId!=null) 
				sb.append(" and tool.enterprise_id = :enterpriseId ");
			if(toolIdList != null && toolIdList.size()>0)
				sb.append(" and tool.id not in (:toolIdList) ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) 
				query.setParameter("enterpriseId", enterpriseId);
			if(toolIdList != null && toolIdList.size()>0)
				query.setParameter("toolIdList", toolIdList);
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
	
	public List<Map<String, Object>> getMiniToolCatalogList(String toolName,String productionUnits,String type,
			Integer nowPage,Integer pageCount,String selectAll,List<Integer> enterpriseIdList,String uniformPrice,String productAttributes){
		try {
			String queryString = " SELECT ttc.id,ttc.name toolName,ttc.type,tty.name typeName,ttc.production_units productionUnits, ";
			queryString += " ifnull(tf.file_url,'') toolPic,sell.sellNum sellNum, ttc.code code,buy.buyNum buyNum, ttc.uniform_price uniformPrice ";
			queryString += " FROM tb_tool_catalog ttc ";
			queryString += " left join tb_res_tool_catalog_file trtcf on ttc.id = trtcf.tool_catalog_id ";
			queryString += " left join tb_file tf on tf.id = trtcf.file_id ";
			queryString += " left join tb_type tty on tty.id = ttc.type ";
			queryString += " left join (select tt.code,sum(tsc.num) sellNum ";
			queryString += " from tb_tool_order tto left join tb_res_order_car troc on troc.order_id = tto.id ";
			queryString += " left join tb_shopping_car tsc on tsc.id = troc.car_id left join tb_tool tt on tt.id = tsc.tool_id ";
			queryString += " where tto.status = 4 and tto.del_flag = 0 and tto.input_time >= concat(date_format(now(),'%Y-%m'),'-01 00:00:00') ";
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and tto.tool_enterprise_id in (:enterpriseIdList) ";
			}
			queryString += " group by tt.code) sell on sell.code = ttc.code ";
			queryString += " left join (select tt.code,sum( ttr.number) buyNum ";
			queryString += " from tb_tool_record ttr left join tb_tool tt on ttr.tool_id = tt.id ";
			queryString += " where ttr.del_flag = 0 and ttr.record_type = 1 ";
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and ttr.enterprise_id in (:enterpriseIdList) ";
			}
			queryString += " and ttr.use_time >= concat(date_format(now(),'%Y-%m'),'-01 00:00:00') group by tt.code) buy on buy.code = ttc.code ";
			queryString += " where ttc.del_flag = 0  and ttc.status=1 ";
			if(!StringUtils.isNullOrEmpty(toolName))
				queryString += " and ttc.name like :toolName ";
			if(!StringUtils.isNullOrEmpty(productionUnits))
				queryString += " and ttc.production_units like :productionUnits ";
			if(!StringUtils.isNullOrEmpty(type))
				queryString += " and ttc.type like :type ";
			if(!StringUtils.isNullOrEmpty(uniformPrice))
				queryString += " and ttc.uniform_price = :uniformPrice ";
			if(!StringUtils.isNullOrEmpty(productAttributes))
				queryString += " and ttc.product_attributes = :productAttributes ";
			
			queryString += " group by ttc.id ";
			queryString += " order by case when tf.file_url is not null then 0 else 1 end , sellNum desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isNullOrEmpty(toolName))
				query.setParameter("toolName", "%"+toolName+"%");
			if(!StringUtils.isNullOrEmpty(productionUnits))
				query.setParameter("productionUnits", "%"+productionUnits+"%");
			if(!StringUtils.isNullOrEmpty(type))
				query.setParameter("type", type);
			if(!StringUtils.isNullOrEmpty(uniformPrice))
				query.setParameter("uniformPrice", uniformPrice);
			if(!StringUtils.isNullOrEmpty(productAttributes))
				query.setParameter("productAttributes", productAttributes);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
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
	
	public Integer getMiniToolCatalogListCount(String toolName,String productionUnits,String type,String uniformPrice,String productAttributes){
		try {
			String queryString = " SELECT count(distinct ttc.id) FROM tb_tool_catalog ttc ";
			queryString += " where ttc.del_flag = 0 and ttc.status=1 ";
			if(!StringUtils.isNullOrEmpty(toolName))
				queryString += " and ttc.name like :toolName ";
			if(!StringUtils.isNullOrEmpty(productionUnits))
				queryString += " and ttc.production_units like :productionUnits ";
			if(!StringUtils.isNullOrEmpty(type))
				queryString += " and ttc.type like :type ";
			if(!StringUtils.isNullOrEmpty(uniformPrice))
				queryString += " and ttc.uniform_price = :uniformPrice ";
			if(!StringUtils.isNullOrEmpty(productAttributes))
				queryString += " and ttc.product_attributes = :productAttributes ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isNullOrEmpty(toolName))
				query.setParameter("toolName", "%"+toolName+"%");
			if(!StringUtils.isNullOrEmpty(productionUnits))
				query.setParameter("productionUnits", "%"+productionUnits+"%");
			if(!StringUtils.isNullOrEmpty(type))
				query.setParameter("type", type);
			if(!StringUtils.isNullOrEmpty(uniformPrice))
				query.setParameter("uniformPrice", uniformPrice);
			if(!StringUtils.isNullOrEmpty(productAttributes))
				query.setParameter("productAttributes", productAttributes);
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
	
	public Object[] getMiniToolCatalogListNyFlCount(String toolName,String productionUnits,String type,String uniformPrice,String productAttributes){
		try {
			String queryString = " SELECT count(*) count,count(type = 12 or null) fl,count(type = 13 or null) ny FROM tb_tool_catalog ttc ";
			queryString += " where ttc.del_flag = 0 and ttc.status=1 ";
			if(!StringUtils.isNullOrEmpty(toolName))
				queryString += " and ttc.name like :toolName ";
			if(!StringUtils.isNullOrEmpty(productionUnits))
				queryString += " and ttc.production_units like :productionUnits ";
			if(!StringUtils.isNullOrEmpty(type))
				queryString += " and ttc.type like :type ";
			if(!StringUtils.isNullOrEmpty(uniformPrice))
				queryString += " and ttc.uniform_price = :uniformPrice ";
			if(!StringUtils.isNullOrEmpty(productAttributes))
				queryString += " and ttc.product_attributes = :productAttributes ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isNullOrEmpty(toolName))
				query.setParameter("toolName", "%"+toolName+"%");
			if(!StringUtils.isNullOrEmpty(productionUnits))
				query.setParameter("productionUnits", "%"+productionUnits+"%");
			if(!StringUtils.isNullOrEmpty(type))
				query.setParameter("type", type);
			if(!StringUtils.isNullOrEmpty(uniformPrice))
				query.setParameter("uniformPrice", uniformPrice);
			if(!StringUtils.isNullOrEmpty(productAttributes))
				query.setParameter("productAttributes", productAttributes);
			List<Object[]> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getSellRecordByToolCatalogId(String seller,String buyer,String startTime,
			String endTime,Integer toolCatalogId,Integer nowPage,Integer pageCount,String code,Integer enterpriseId,
			String selectAll,List<Integer> enterpriseIdList){
		try {
			String queryString = " SELECT te2.name seller,CONCAT(ifnull(te.name,''),ifnull(tloi.link_people,'')) buyer, ";
			queryString += " round(tsc.num,2) num,DATE_FORMAT(tto.input_time,'%Y-%m-%d %H:%i:%s') tm,te2.id enterpriseId ";
			queryString += " FROM tb_shopping_car tsc left join tb_res_order_car troc on troc.car_id = tsc.id ";
			queryString += " left join tb_tool_order tto on tto.id = troc.order_id ";
			queryString += " left join tb_tool tt on tt.id = tsc.tool_id left join tb_tool_catalog ttc on ttc.code = tt.code ";
			queryString += " left join tb_enterprise te on tto.plant_enterprise_id = te.id and tto.type = 1 ";
			queryString += " left join tb_link_order_info tloi on tloi.id = tto.plant_enterprise_id and tto.type = 2 ";
			queryString += " left join tb_enterprise te2 on tt.enterprise_id = te2.id ";
			queryString += " where tsc.status = 2  and tto.status > 0 and tto.del_flag = 0 and tsc.del_flag = 0 ";
			if(enterpriseId != null)
				queryString += " and tt.enterprise_id = :enterpriseId ";
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and tt.enterprise_id in (:enterpriseIdList) ";
			}
			if(toolCatalogId != null)
				queryString += " and ttc.id = :toolCatalogId ";
			if(!StringUtils.isNullOrEmpty(code))
				queryString += " and ttc.code = :code ";
			if(!StringUtils.isNullOrEmpty(seller))
				queryString += " and te2.name like :seller ";
			if(!StringUtils.isNullOrEmpty(buyer))
				queryString += " and (te.name like :buyer or tloi.link_people like :buyer) ";
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString += " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString += " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') <= :endTime ";
			queryString += " group by tsc.id ";
			queryString += " order by tto.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(toolCatalogId != null)
				query.setParameter("toolCatalogId", toolCatalogId);
			if(!StringUtils.isNullOrEmpty(code))
				query.setParameter("code", code);
			if(!StringUtils.isNullOrEmpty(seller))
				query.setParameter("seller", "%"+seller+"%");
			if(!StringUtils.isNullOrEmpty(buyer))
				query.setParameter("buyer", "%"+buyer+"%");
			if(!StringUtils.isNullOrEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isNullOrEmpty(endTime))
				query.setParameter("endTime", endTime);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
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
	
	public Integer getSellRecordCountByToolCatalogId(String seller,String buyer,String startTime,
			String endTime,Integer toolCatalogId,String code,Integer enterpriseId,String selectAll,List<Integer> enterpriseIdList){
		try {
			String queryString = " SELECT count(distinct tsc.id) FROM tb_shopping_car tsc left join tb_res_order_car troc on troc.car_id = tsc.id ";
			queryString += " left join tb_tool_order tto on tto.id = troc.order_id ";
			queryString += " left join tb_tool tt on tt.id = tsc.tool_id left join tb_tool_catalog ttc on ttc.code = tt.code ";
			queryString += " left join tb_enterprise te on tto.plant_enterprise_id = te.id and tto.type = 1 ";
			queryString += " left join tb_link_order_info tloi on tloi.id = tto.plant_enterprise_id and tto.type = 2 ";
			queryString += " left join tb_enterprise te2 on tt.enterprise_id = te2.id ";
			queryString += " where tsc.status = 2  and tto.status > 0 and tto.del_flag = 0 and tsc.del_flag = 0 ";
			if(enterpriseId != null)
				queryString += " and tt.enterprise_id = :enterpriseId ";
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and tt.enterprise_id in (:enterpriseIdList) ";
			}
			if(toolCatalogId != null)
				queryString += " and ttc.id = :toolCatalogId ";
			if(!StringUtils.isNullOrEmpty(code))
				queryString += " and ttc.code = :code ";
			if(!StringUtils.isNullOrEmpty(seller))
				queryString += " and te2.name like :seller ";
			if(!StringUtils.isNullOrEmpty(buyer))
				queryString += " and (te.name like :buyer or tloi.link_people like :buyer) ";
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString += " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString += " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') <= :endTime ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(toolCatalogId != null)
				query.setParameter("toolCatalogId", toolCatalogId);
			if(!StringUtils.isNullOrEmpty(code))
				query.setParameter("code", code);
			if(!StringUtils.isNullOrEmpty(seller))
				query.setParameter("seller", "%"+seller+"%");
			if(!StringUtils.isNullOrEmpty(buyer))
				query.setParameter("buyer", "%"+buyer+"%");
			if(!StringUtils.isNullOrEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isNullOrEmpty(endTime))
				query.setParameter("endTime", endTime);
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
	
	public List<Map<String, Object>> statisticToolCatalog(Integer status,String selectAll,List<Integer> enterpriseIdList){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT '合计' name,count(distinct ttc.id) total,count(case when ttc.type = 12 then ttc.id end) fertilizer, ");
		queryString.append(" count(case when ttc.type = 13 then ttc.id end) pesticide,count(case when ttc.type = 14 then ttc.id end) seed, ");
		queryString.append(" count(case when ttc.type = 15 then ttc.id end) other FROM tb_tool_catalog ttc  ");
		queryString.append(" left join tb_user tu on ttc.user_id = tu.user_id left join tb_res_user_enterprise rue on rue.user_tab_id = tu.id ");
		queryString.append(" left join tb_enterprise te on te.id = rue.enterprise_id ");
		queryString.append(" where ttc.del_flag = 0 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		if (status != null) {
			queryString.append(" and ttc.status = :status ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		if (status != null) {
			query.setParameter("status", status);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public List<Object[]> findByUser(String userId,Integer nowPage,Integer pageCount,String name,String supplierName,String type,String unit,String code,String uniformPrice,List<String> ids,String status) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tool.id, ");
			sb.append(" tool.name, ");
			sb.append(" model, ");
			sb.append(" specification, ");
			sb.append(" unit,");
			sb.append(" price, ");
			sb.append(" number, ");
			sb.append(" describe_, ");
			sb.append(" tool.type ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_catalog_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_catalog_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goods_pic,tool.supplier_name,tool.production_units,tool.uniform_price,tt.name typeName,tool.code  ");
			
			sb.append(" ,registration_certificate_number ");
			sb.append(" ,explicit_ingredients ");
			sb.append(" ,effective_ingredients ");
			sb.append(" ,implementation_standards ");
			sb.append(" ,dosage_forms ");
			sb.append(" ,product_attributes ");
			sb.append(" ,toxicity ");
			sb.append(" ,quality_grade ");
			sb.append(" ,tool.status ");
			sb.append(" ,tool.wholesale_price,remark,tu.name userName ,tu.mobile,date_format(tool.input_time,'%Y-%m-%d') inputTime,te.name enterpriseName  ");
			sb.append(" from tb_tool_catalog tool left join tb_type tt on tt.id=tool.type ");
			
			sb.append(" LEFT JOIN  tb_user tu on tu.user_id=tool.user_id ");
			sb.append(" LEFT JOIN tb_res_user_enterprise rue ON rue.user_tab_id=tu.id ");
			sb.append(" LEFT JOIN tb_enterprise te ON te.id=rue.enterprise_id ");
			
			sb.append(" where tool.del_flag=0 and ( tool.user_id is not null and tool.user_id !='' and tool.remark is not null and tool.remark !=''   )  ");
			if(!StringUtil.isNullOrEmpty(userId)) {
				sb.append(" AND tool.user_id = :userId ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" AND (tool.code like :code or tool.registration_certificate_number like :code) ");
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				sb.append(" and tool.production_units like :unit ");
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				sb.append(" and tool.supplier_name like :supplierName ");
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and tool.type = :type ");
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				sb.append(" AND tool.uniformPrice = :uniformPrice ");
			}
			if(ids!=null) {
				sb.append(" AND tool.id not in (:ids )");
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				sb.append(" AND tool.status = :status ");
			}
			sb.append(" order by  tool.status asc");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(userId)) {
				query.setParameter("userId",userId );
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", "%"+code+"%");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit","%"+unit+"%" );
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				query.setParameter("supplierName","%"+supplierName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type",type );
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				query.setParameter("uniformPrice",uniformPrice );
			}
			if(ids!=null) {
				query.setParameter("ids",ids );
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				query.setParameter("status",status );
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
	public Integer findByUserCount(String userId ,String name,String supplierName,String type,String unit,String code,String uniformPrice,List<String> ids,String status) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(tool.id) ");
			sb.append(" from tb_tool_catalog tool   ");
			sb.append(" where tool.del_flag=0 and ( tool.user_id is not null and tool.user_id !='' and tool.remark is not null and tool.remark !=''   )  ");
			if(!StringUtil.isNullOrEmpty(userId)) {
				sb.append(" AND tool.user_id = :userId ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" AND (tool.code like :code or tool.registration_certificate_number like :code) ");
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				sb.append(" and tool.production_units like :unit ");
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				sb.append(" and tool.supplier_name like :supplierName ");
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and tool.type = :type ");
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				sb.append(" AND tool.uniformPrice = :uniformPrice ");
			}
			if(ids!=null) {
				sb.append(" AND tool.id not in (:ids )");
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				sb.append(" AND tool.status = :status ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(userId)) {
				query.setParameter("userId",userId );
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", "%"+code+"%");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit","%"+unit+"%" );
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				query.setParameter("supplierName","%"+supplierName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type",type );
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				query.setParameter("uniformPrice",uniformPrice );
			}
			if(ids!=null) {
				query.setParameter("ids",ids );
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				query.setParameter("status",status );
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
	public List<TbTool> findAllIsSync() {
		try {
			String queryString = " from TbTool where  delFlag = 0 and (isSync=0 or isSync is null) and enterpriseId in ( select id from TbEnterprise where delFlag=0 and enterpriseType=3 ) ";
			Query query = getEntityManager().createQuery(queryString);
			List<TbTool> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public String findToolFile(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  SELECT ifnull(concat(file_url),'') ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_catalog_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_catalog_id = :id ");
			sb.append(" AND f.file_type = 1 ");
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
	
	public List<Map<String, Object>> getSellRecordByToolCatalogIdByDscd(String seller,String buyer,String startTime,
			String endTime,Integer toolCatalogId,Integer nowPage,Integer pageCount,String code,Integer enterpriseId,
			String selectAll,List<Integer> enterpriseIdList){
		try {
			String queryString = " SELECT te2.name seller,round(sum(tsc.num),2) orderCount,te2.id enterpriseId  ";
			queryString += " FROM tb_shopping_car tsc left join tb_res_order_car troc on troc.car_id = tsc.id ";
			queryString += " left join tb_tool_order tto on tto.id = troc.order_id ";
			queryString += " left join tb_tool tt on tt.id = tsc.tool_id left join tb_tool_catalog ttc on ttc.code = tt.code ";
			queryString += " left join tb_enterprise te on tto.plant_enterprise_id = te.id and tto.type = 1 ";
			queryString += " left join tb_link_order_info tloi on tloi.id = tto.plant_enterprise_id and tto.type = 2 ";
			queryString += " left join tb_enterprise te2 on tt.enterprise_id = te2.id ";
			queryString += " where tsc.status = 2  and tto.status > 0 and tto.del_flag = 0 and tsc.del_flag = 0 ";
			if(enterpriseId != null)
				queryString += " and tt.enterprise_id = :enterpriseId ";
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and tt.enterprise_id in (:enterpriseIdList) ";
			}
			if(toolCatalogId != null)
				queryString += " and ttc.id = :toolCatalogId ";
			if(!StringUtils.isNullOrEmpty(code))
				queryString += " and ttc.code = :code ";
			if(!StringUtils.isNullOrEmpty(seller))
				queryString += " and te2.name like :seller ";
			if(!StringUtils.isNullOrEmpty(buyer))
				queryString += " and (te.name like :buyer or tloi.link_people like :buyer) ";
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString += " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString += " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') <= :endTime ";
			queryString += " group by te2.id ";
			queryString += " order by tto.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(toolCatalogId != null)
				query.setParameter("toolCatalogId", toolCatalogId);
			if(!StringUtils.isNullOrEmpty(code))
				query.setParameter("code", code);
			if(!StringUtils.isNullOrEmpty(seller))
				query.setParameter("seller", "%"+seller+"%");
			if(!StringUtils.isNullOrEmpty(buyer))
				query.setParameter("buyer", "%"+buyer+"%");
			if(!StringUtils.isNullOrEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isNullOrEmpty(endTime))
				query.setParameter("endTime", endTime);
			if(nowPage!=null&pageCount!=null){
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
	public Integer getSellRecordCountByToolCatalogIdByDscdCount(String seller,String buyer,String startTime,
			String endTime,Integer toolCatalogId,String code,Integer enterpriseId,String selectAll,List<Integer> enterpriseIdList){
		try {
			String queryString = " SELECT count(distinct te2.id) ";
			queryString += " FROM tb_shopping_car tsc left join tb_res_order_car troc on troc.car_id = tsc.id ";
			queryString += " left join tb_tool_order tto on tto.id = troc.order_id ";
			queryString += " left join tb_tool tt on tt.id = tsc.tool_id left join tb_tool_catalog ttc on ttc.code = tt.code ";
			queryString += " left join tb_enterprise te on tto.plant_enterprise_id = te.id and tto.type = 1 ";
			queryString += " left join tb_link_order_info tloi on tloi.id = tto.plant_enterprise_id and tto.type = 2 ";
			queryString += " left join tb_enterprise te2 on tt.enterprise_id = te2.id ";
			queryString += " where tsc.status = 2  and tto.status > 0 and tto.del_flag = 0 and tsc.del_flag = 0 ";
			if(enterpriseId != null)
				queryString += " and tt.enterprise_id = :enterpriseId ";
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and tt.enterprise_id in (:enterpriseIdList) ";
			}
			if(toolCatalogId != null)
				queryString += " and ttc.id = :toolCatalogId ";
			if(!StringUtils.isNullOrEmpty(code))
				queryString += " and ttc.code = :code ";
			if(!StringUtils.isNullOrEmpty(seller))
				queryString += " and te2.name like :seller ";
			if(!StringUtils.isNullOrEmpty(buyer))
				queryString += " and (te.name like :buyer or tloi.link_people like :buyer) ";
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString += " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString += " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') <= :endTime ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(toolCatalogId != null)
				query.setParameter("toolCatalogId", toolCatalogId);
			if(!StringUtils.isNullOrEmpty(code))
				query.setParameter("code", code);
			if(!StringUtils.isNullOrEmpty(seller))
				query.setParameter("seller", "%"+seller+"%");
			if(!StringUtils.isNullOrEmpty(buyer))
				query.setParameter("buyer", "%"+buyer+"%");
			if(!StringUtils.isNullOrEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isNullOrEmpty(endTime))
				query.setParameter("endTime", endTime);
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
	
	public List<TbToolCatalog> findByUniformPrice() {
		try {
			String queryString = " from TbToolCatalog where  delFlag = 0 and uniformPrice=1 ";
			Query query = getEntityManager().createQuery(queryString);
			List<TbToolCatalog> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
