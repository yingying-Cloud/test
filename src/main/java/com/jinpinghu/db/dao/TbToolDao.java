package com.jinpinghu.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbVideoImg;
import com.mysql.cj.util.StringUtils;

public class TbToolDao extends BaseZDao{

	public TbToolDao(EntityManager _em) {
		super(_em);
		// TODO �Զ����ɵĹ��캯�����
	}
	public TbTool findById(Integer id) {
		try {
			String queryString = "from TbTool where  delFlag = 0 and id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbTool> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbTool findByCode(String code,Integer enterpriseId) {
		try {
			String queryString = "from TbTool where  delFlag = 0 and code=:code and enterprise_id=:enterpriseId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("enterpriseId",enterpriseId);
			query.setParameter("code",code);
			List<TbTool> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public void updateUniforPrice(String code,String price,String uniformPrice) {
		try {
			String queryString = " update  tb_tool  ";
			queryString += " set uniform_price =:uniformPrice  ";
			if(!StringUtils.isNullOrEmpty(uniformPrice)&&uniformPrice.equals("1")) {
				queryString += " ,price = :price ";
			}
			queryString += " where code = :code "
					+ " and enterprise_id in "
					+ "( select enterprise_id "
					+ "from tb_enterprise_zero tez "
					+ "inner join tb_enterprise te on te.id=tez.enterprise_id "
					+ "where tez.del_flag=0 and te.del_Flag=0 ) ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("code", code);
			if(!StringUtils.isNullOrEmpty(uniformPrice)&&uniformPrice.equals("1")) {
				query.setParameter("price", price);
			}
			query.setParameter("uniformPrice", uniformPrice);
			query.executeUpdate();
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public void delUniforTool(String code) {
		try {
			String queryString = " update  tb_tool  ";
			queryString += " set del_flag =1 ";
			queryString += " where code = :code "
					+ " and enterprise_id in "
					+ "( select enterprise_id "
					+ "from tb_enterprise_zero tez "
					+ "inner join tb_enterprise te on te.id=tez.enterprise_id "
					+ "where tez.del_flag=0 and te.del_Flag=0 ) ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("code", code);
			query.executeUpdate();
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public TbTool findBySyncNumber(String syncNumber,Integer enterpriseId) {
		try {
			String queryString = "from TbTool where  delFlag = 0 and syncNumber=:syncNumber and enterprise_id=:enterpriseId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("enterpriseId",enterpriseId);
			query.setParameter("syncNumber",syncNumber);
			List<TbTool> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Object[] findInfoById(Integer id,String userId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tool.id,te.name tnm, ");
			sb.append(" tool.name, ");
			sb.append(" model, ");
			sb.append(" specification, ");
			sb.append(" unit,");
			sb.append(" price, ");
			sb.append(" number, ");
			sb.append(" describe_, ");
			sb.append(" tool.type,tool.supplier_name,tt.name ttnm ");
			
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
			sb.append(" ,code ");
			sb.append(" ,wholesale_price, ");
			
			sb.append("if((select count(*) from tb_tool_catalog where code=tool.code and user_id=:userId)>0,1,0) status ");
			sb.append(" ,tool.dnm,tool.hfzc  "
					+ ",date_format(tool.approval_end_date,'%Y-%m-%d') " + 
					",tool.approval_no" + 
					",tool.approve_no" + 
					",tool.certificate_no" + 
					",tool.check_health_no" + 
					",tool.health_no" + 
					",date_format(tool.limit_date,'%Y-%m-%d') " + 
					",tool.produced" + 
					",tool.production_no,te.id enterpriseId,tool.n,tool.p,tool.k,tool.qt,tool.qr_code ");
			sb.append(",tool.NPK,tool.NP,tool.NK,tool.PK,tool.zjzl,tool.yxcf_unit,nysx ");
			sb.append(" from tb_tool tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" where tool.del_flag=0 ");
			sb.append(" and tool.id = :id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id",id );
			query.setParameter("userId",userId );
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> findByName(Integer enterpriseId,String name,String enterpriseName,String enterpriseType,
			Integer nowPage,Integer pageCount,String supplierName,String type,String unit,List<String> notIn,String code,String dnm
			,String orderby,String selectAll,List<Integer> enterpriseIdList,String recordNo,String uniformPrice,String dscd,
			String productAttributes,String lowPrice,String highPrice,String etName,Integer loginEnterpriseId,String sortDirection) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tool.id,te.name tnm, ");
			sb.append(" tool.name, ");
			sb.append(" tool.model, ");
			sb.append(" tool.specification, ");
			sb.append(" tool.unit,");
			sb.append(" tool.price, ");
			sb.append(" tool.number, ");
			sb.append(" tool.describe_, ");
			sb.append(" tool.type ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goods_pic,tool.supplier_name,tt.name ttnm,tool.production_units,tool.uniform_price,tool.code,  ");
			sb.append(" sum(case when tto.status > 0 then tsc.num else 0 end) sellNum, ");
//			sb.append("0");
			sb.append(" (select sum(ttr.number) from tb_tool_record ttr where ttr.record_type=1 and tool.id=ttr.tool_id and te.id=ttr.enterprise_id ) buyNum ");
			sb.append(" ,tool.wholesale_price,tool.registration_certificate_number,tool.dnm,tool.record_no  ");
			
			sb.append(" ,explicit_ingredients ");
			sb.append(" ,effective_ingredients ");
			sb.append(" ,implementation_standards ");
			sb.append(" ,dosage_forms ");
			sb.append(" ,product_attributes ");
			sb.append(" ,toxicity ");
			sb.append(" ,quality_grade,tred.distance ");
			sb.append(",ifnull(tool.NPK,''),ifnull(tool.NP,''),ifnull(tool.NK,''),ifnull(tool.PK,''),"
					+ "ifnull(tool.zjzl,''),ifnull(tool.yxcf_unit,''),ifnull(tool.N,''),ifnull(tool.P,''),ifnull(tool.K,''),ifnull(tool.qt,'') ");
			sb.append(" from tb_tool tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" left join tb_shopping_car tsc on tool.id = tsc.tool_id ");
			sb.append(" left join tb_res_order_car troc on troc.car_id = tsc.id ");
			sb.append(" left join tb_tool_order tto on troc.order_id = tto.id ");
			sb.append(" left join tb_res_enterprise_distance tred on tred.primary_enterprise_id = :loginEnterpriseId and tred.compare_enterprise_id = tool.enterprise_id ");
			sb.append(" where tool.del_flag=0  and te.del_flag=0  ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool.enterprise_id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool.enterprise_id = :enterpriseId ");
			}
			if(notIn!=null) {
				sb.append(" and tool.id not in  :notIn ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				sb.append(" and tool.supplier_name like :supplierName ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseType)) {
				sb.append(" and te.enterprise_Type = :enterpriseType ");
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and tool.type = :type ");
			}
			if(!StringUtil.isNullOrEmpty(unit)) {
				sb.append(" AND tool.production_units like :unit ");
			}
			if(!StringUtil.isNullOrEmpty(code)) {
				sb.append(" AND (tool.code like :code or tool.registration_certificate_number like :code) ");
			}
			if(!StringUtil.isNullOrEmpty(dnm)) {
				sb.append(" AND tool.dnm like :dnm ");
			}
			if(!StringUtil.isNullOrEmpty(recordNo)) {
				if(recordNo.equals("1")) {
					sb.append(" AND tool.record_no !='' and tool.record_no is not null ");
				}else if(recordNo.equals("2")) {
					sb.append(" AND (tool.record_no ='' or tool.record_no is null) ");
				}
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				sb.append(" AND tool.uniform_price = :uniformPrice ");
			}
			if(!StringUtil.isNullOrEmpty(dscd)) {
				sb.append(" AND te.dscd = :dscd ");
			}
			if(!StringUtil.isNullOrEmpty(productAttributes)) {
				sb.append(" AND  tool.product_attributes = :productAttributes  ");
			}
			if(!StringUtil.isNullOrEmpty(lowPrice)) {
				sb.append(" AND tool.price+0 >= :lowPrice  ");
			}
			if(!StringUtil.isNullOrEmpty(highPrice)) {
				sb.append(" AND tool.price+0 <= :highPrice  ");
			}
			if(!StringUtil.isNullOrEmpty(etName)) {
				sb.append(" AND (tool.name like :etName or te.name like :etName ) ");
			}
			sb.append(" group by tool.id  "	);
			if(StringUtils.isNullOrEmpty(orderby)) {
				sb.append(" order by tool.id asc ");
			}else if(!StringUtils.isNullOrEmpty(orderby)){
				if(orderby.equals("1")) {
					sb.append(" order by tool.id ASC ");
				}else if(orderby.equals("2")) {
					sb.append(" order by tool.type ASC ");
				}else if(orderby.equals("3")) {
					sb.append(" order by dnm is null or dnm='' asc,dnm+0 ASC ");
				}else if(orderby.equals("4")) {
					sb.append(" order by if(sum( CASE WHEN tto.STATUS > 0 THEN tsc.num ELSE 0 END ) is null,1,0),sum( CASE WHEN tto.STATUS > 0 THEN tsc.num ELSE 0 END )+0 ");
				}else if(orderby.equals("5")) {
					sb.append(" order by tool.id ");
				}else if(orderby.equals("6")) {
					sb.append(" order by if(tool.price is null or tool.price = '' ,1,0),tool.price+0.0 ");
				}
				List<String> pcSort = new ArrayList<String>(3) {{add("4");add("5");add("6");}};
				if (pcSort.contains(orderby)) {
					if (StringUtils.isNullOrEmpty(sortDirection)) {
						sb.append(" desc ");
					}else if ("1".equals(sortDirection)) {
						sb.append(" asc ");
					}else if ("2".equals(sortDirection)) {
						sb.append(" desc ");
					}
				}
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("loginEnterpriseId", loginEnterpriseId);
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				query.setParameter("supplierName","%"+supplierName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseType)) {
				query.setParameter("enterpriseType",enterpriseType );
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type",type );
			}
			if(!StringUtil.isNullOrEmpty(dnm)) {
				query.setParameter("dnm","%"+dnm+"%" );
			}
			if(notIn!=null) {
				query.setParameter("notIn",notIn );
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit","%"+unit+"%" );
			}
			if(!StringUtil.isNullOrEmpty(code)) {
				query.setParameter("code", "%"+code+"%");
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				query.setParameter("uniformPrice",uniformPrice );
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd",dscd );
			}
			if(!StringUtils.isNullOrEmpty(productAttributes)) {
				query.setParameter("productAttributes",productAttributes );
			}
			if(!StringUtil.isNullOrEmpty(lowPrice)) {
				query.setParameter("lowPrice",lowPrice );
			}
			if(!StringUtil.isNullOrEmpty(highPrice)) {
				query.setParameter("highPrice",highPrice );
			}
			if(!StringUtil.isNullOrEmpty(etName)) {
				query.setParameter("etName", "%"+etName+"%");
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
	public Integer findByNameCount(Integer enterpriseId,String name,String enterpriseName,String enterpriseType,String supplierName,
			String type,String unit,List<String> notIn,String code,String dnm,String selectAll,List<Integer> enterpriseIdList,String recordNo
			,String uniformPrice,String dscd,String productAttributes,String lowPrice,String highPrice,String etName) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(tool.id) ");
			sb.append(" from tb_tool tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" where tool.del_flag=0 and te.del_flag=0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool.enterprise_id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool.enterprise_id = :enterpriseId ");
			}
			if(notIn!=null) {
				sb.append(" and tool.id not in  :notIn ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				sb.append(" and tool.supplier_name like :supplierName ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseType)) {
				sb.append(" and te.enterprise_Type = :enterpriseType ");
			}
			if(!StringUtil.isNullOrEmpty(type)) {
				sb.append(" AND tool.type = :type ");
			}
			if(!StringUtil.isNullOrEmpty(unit)) {
				sb.append(" AND tool.production_units like :unit ");
			}
			if(!StringUtil.isNullOrEmpty(code)) {
				sb.append(" AND (tool.code like :code or tool.registration_certificate_number like :code) ");
			}
			if(!StringUtil.isNullOrEmpty(dnm)) {
				sb.append(" AND tool.dnm like :dnm ");
			}
			if(!StringUtil.isNullOrEmpty(recordNo)) {
				if(recordNo.equals("1")) {
					sb.append(" AND tool.record_no !='' and tool.record_no is not null ");
				}else if(recordNo.equals("2")) {
					sb.append(" AND (tool.record_no ='' or tool.record_no is null) ");
				}
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				sb.append(" AND tool.uniform_price = :uniformPrice ");
			}
			if(!StringUtil.isNullOrEmpty(dscd)) {
				sb.append(" AND te.dscd = :dscd ");
			}
			if(!StringUtil.isNullOrEmpty(productAttributes)) {
				sb.append(" AND  tool.product_attributes =:productAttributes ");
			}
			if(!StringUtil.isNullOrEmpty(lowPrice)) {
				sb.append(" AND tool.price+0 >= :lowPrice  ");
			}
			if(!StringUtil.isNullOrEmpty(highPrice)) {
				sb.append(" AND tool.price+0 <= :highPrice  ");
			}
			if(!StringUtil.isNullOrEmpty(etName)) {
				sb.append(" AND (tool.name like :etName or te.name like :etName ) ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(notIn!=null) {
				query.setParameter("notIn",notIn );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(supplierName)) {
				query.setParameter("supplierName","%"+supplierName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseType)) {
				query.setParameter("enterpriseType",enterpriseType );
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type",type );
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit","%"+unit+"%" );
			}
			if(!StringUtil.isNullOrEmpty(code)) {
				query.setParameter("code", "%"+code+"%");
			}
			if(!StringUtil.isNullOrEmpty(dnm)) {
				query.setParameter("dnm","%"+dnm+"%" );
			}
			if(!StringUtil.isNullOrEmpty(uniformPrice)) {
				query.setParameter("uniformPrice",uniformPrice );
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd",dscd );
			}
			if(!StringUtils.isNullOrEmpty(productAttributes)) {
				query.setParameter("productAttributes",productAttributes );
			}
			if(!StringUtil.isNullOrEmpty(lowPrice)) {
				query.setParameter("lowPrice",lowPrice );
			}
			if(!StringUtil.isNullOrEmpty(highPrice)) {
				query.setParameter("highPrice",highPrice );
			}
			if(!StringUtil.isNullOrEmpty(etName)) {
				query.setParameter("etName", "%"+etName+"%");
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
	
	public List<Map<String, Object>> findNotAddToolList(Integer enterpriseId, List<Integer> toolIdList, String toolName, Integer type, Integer nowPage, Integer pageCount){
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
			sb.append(" tool.type ");
			sb.append(" ,ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ),'') goodsPic,tool.production_units productionUnits,tool.uniform_price uniformPrice ");
			sb.append(",tt.name typeName ");
			sb.append(" from tb_tool tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" where tool.del_flag=0 ");
			if(enterpriseId!=null) 
				sb.append(" and tool.enterprise_id = :enterpriseId ");
			if(toolIdList != null && toolIdList.size()>0)
				sb.append(" and tool.id not in (:toolIdList) ");
			if(!StringUtil.isNullOrEmpty(toolName)){
				sb.append(" and tool.name LIKE :toolName ");
			}
			if(type != null){
				sb.append(" and tool.type = :type ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) 
				query.setParameter("enterpriseId", enterpriseId);
			if(toolIdList != null && toolIdList.size()>0)
				query.setParameter("toolIdList", toolIdList);
			if(!StringUtil.isNullOrEmpty(toolName)){
				query.setParameter("toolName", "%"+toolName+"%");
			}
			if(type != null){
				query.setParameter("type", type);
			}
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
	
	public Integer findNotAddToolListCount(Integer enterpriseId,List<Integer> toolIdList, String toolName, Integer type){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select COUNT(0) ");
			sb.append(" from tb_tool tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" where tool.del_flag=0 ");
			if(enterpriseId!=null) 
				sb.append(" and tool.enterprise_id = :enterpriseId ");
			if(toolIdList != null && toolIdList.size()>0)
				sb.append(" and tool.id not in (:toolIdList) ");
			if(!StringUtil.isNullOrEmpty(toolName)){
				sb.append(" and tool.name LIKE :toolName ");
			}
			if(type != null){
				sb.append(" and tool.type = :type ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) 
				query.setParameter("enterpriseId", enterpriseId);
			if(toolIdList != null && toolIdList.size()>0)
				query.setParameter("toolIdList", toolIdList);
			if(!StringUtil.isNullOrEmpty(toolName)){
				query.setParameter("toolName", "%"+toolName+"%");
			}
			if(type != null){
				query.setParameter("type", type);
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
	
	public List<Map<String, Object>> getTbResCropFarmingToolStatictis(Integer enterpriseId) {
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("select (");
			sb.append(" SELECT ");
			sb.append(" ifnull(cast(sum(ftt.num)/1000 as decimal(10,2)),0) sumTool ");
			sb.append(" FROM");
			sb.append(" tb_res_crop_farming_tool ftt");
			sb.append(" inner JOIN tb_tool tl ON tl.id = ftt.tool_id");
			sb.append(" inner JOIN tb_work tw ON tw.id = ftt.work_id ");
			sb.append(" where 1=1 and tl.type=12 and tl.unit='g' ");
			if(enterpriseId!=null) {
				sb.append(" and ftt.enterprise_id=:enterpriseId ");
			}
			sb.append(" )fl,( ");
			sb.append(" SELECT ");
			sb.append(" ifnull(cast(sum(ftt.num)/1000 as decimal(10,2)),0) sumTool ");
			sb.append(" FROM");
			sb.append(" tb_res_crop_farming_tool ftt");
			sb.append(" inner JOIN tb_tool tl ON tl.id = ftt.tool_id");
			sb.append(" inner JOIN tb_work tw ON tw.id = ftt.work_id ");
			sb.append(" where 1=1 and tl.type=13  and tl.unit='ml' ");
			if(enterpriseId!=null) {
				sb.append(" and ftt.enterprise_id=:enterpriseId ");
			}
			sb.append(" )ny,( ");
			sb.append(" SELECT ");
			sb.append(" ifnull(cast(sum(ftt.num)/1000 as decimal(10,2)),0) sumTool ");
			sb.append(" FROM");
			sb.append(" tb_res_crop_farming_tool ftt");
			sb.append(" inner JOIN tb_tool tl ON tl.id = ftt.tool_id");
			sb.append(" inner JOIN tb_work tw ON tw.id = ftt.work_id ");
			sb.append(" where 1=1 and tl.type=14  and tl.unit='g'  ");
			if(enterpriseId!=null) {
				sb.append(" and ftt.enterprise_id=:enterpriseId ");
			}
			sb.append(" )zz,( ");
			sb.append(" SELECT ");
			sb.append(" ifnull(cast(sum(ftt.num)/1000 as decimal(10,2)),0) sumTool ");
			sb.append(" FROM ");
			sb.append(" tb_res_crop_farming_tool ftt");
			sb.append(" inner JOIN tb_tool tl ON tl.id = ftt.tool_id");
			sb.append(" inner JOIN tb_work tw ON tw.id = ftt.work_id ");
			sb.append(" where 1=1 and tl.type=15  and tl.unit='g'  ");
			if(enterpriseId!=null) {
				sb.append(" and ftt.enterprise_id=:enterpriseId ");
			}
			sb.append(" )qt ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getOfflineStoreToolList(Integer enterpriseId,String type,Integer nowPage,Integer pageCount,String dnm){
		String queryString = " SELECT ifnull(tt.type,'') groupId,ifnull(tt.name,'') name,tt.id id, ";
		queryString += " tf.file_url photo,0.0 vipPrice,0.0 specialPrice,ifnull(tt.code,'') goodsNumber, ";
		queryString += " ifnull(tt.unit,'') inventoryUnit,tt.price  price,ifnull(tt.registration_certificate_number,'') ";
		queryString += " registrationCertificateNumber,ifnull(tt.specification,'') specification,ifnull(tt.dnm,'') dnm,tt.uniform_price uniformPrice ";
		queryString += " FROM tb_tool tt left join tb_res_tool_file trtf on tt.id = trtf.tool_id ";
		queryString += " left join tb_file tf on tf.id = trtf.file_id where tt.del_flag = 0 ";
		if (enterpriseId != null) {
			queryString += " and tt.enterprise_id = :enterpriseId ";
		}
		if (!StringUtils.isNullOrEmpty(type)) {
			if ("23".equals(type)) {
				queryString += " and tt.uniform_price <> 2 ";
			}else {
				queryString += " and tt.type = :type ";
			}
			
		}
		if(!StringUtil.isNullOrEmpty(dnm)) {
			queryString += " and tt.dnm like :dnm ";
		}
		queryString += " group by tt.id order by convert(tt.name using gbk) ASC ";
		Query query = getEntityManager().createNativeQuery(queryString);
		if (enterpriseId != null) {
			query.setParameter("enterpriseId", enterpriseId);
		}
		if (!StringUtils.isNullOrEmpty(type) && !"23".equals(type)) {
			query.setParameter("type", type);
		}
		if(!StringUtil.isNullOrEmpty(dnm)) {
			query.setParameter("dnm","%"+dnm+"%" );
		}
		if(nowPage != null && pageCount != null)
			query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List <Map<String,Object>> list = query.getResultList();
		if (list != null && list.size()>0) {
			return list;
		}
		return null;
	}
	
	public Integer getOfflineStoreToolListCount(Integer enterpriseId,String type,String dnm){
		String queryString = " SELECT count(tt.id) FROM tb_tool tt where tt.del_flag = 0 ";
		if (enterpriseId != null) {
			queryString += " and tt.enterprise_id = :enterpriseId ";
		}
		if (!StringUtils.isNullOrEmpty(type)) {
			queryString += " and tt.type = :type ";
		}
		if(!StringUtil.isNullOrEmpty(dnm)) {
			queryString += " and tt.dnm like :dnm ";
		}
		Query query = getEntityManager().createNativeQuery(queryString);
		if (enterpriseId != null) {
			query.setParameter("enterpriseId", enterpriseId);
		}
		if (!StringUtils.isNullOrEmpty(type)) {
			query.setParameter("type", type);
		}
		if(!StringUtil.isNullOrEmpty(dnm)) {
			query.setParameter("dnm","%"+dnm+"%" );
		}
		List <Object> list = query.getResultList();
		if (list != null && list.size()>0) {
			return Integer.valueOf(list.get(0).toString());
		}
		return null;
	}
	
	public List<Map<String, Object>> statisticStock(String type,String dscd,String selectAll,List<Integer> enterpriseIdList){
		StringBuilder queryString = new StringBuilder();
		if("1".equals(type)) {
			queryString.append(" SELECT te.dscd,ta.name dsnm, ");
		}else if("2".equals(type)) {
			queryString.append(" SELECT te.id,te.name dsnm, ");
		}
		queryString.append(" ifnull(sum(tt.number),0) total,ifnull(sum(case when tt.type = 12 then tt.number end),0) fertilizer, ");
		queryString.append(" ifnull(sum(case when tt.type = 13 then tt.number end),0) pesticide, ");
		queryString.append(" ifnull(sum(case when tt.type = 14 then tt.number end),0) seed, ");
		queryString.append(" ifnull(sum(case when tt.type = 15 then tt.number end),0) other ");
		queryString.append(" from tb_enterprise te left join tb_area ta on te.dscd = ta.id ");
		queryString.append(" left join tb_tool tt on te.id = tt.enterprise_id  ");
		queryString.append(" where te.del_flag = 0 and te.enterprise_type = 3 and tt.del_flag = 0 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			queryString.append(" and te.dscd like :dscd ");
		}
		if("1".equals(type)) {
			queryString.append(" group by te.dscd order by te.dscd ");
		}else if("2".equals(type)) {
			queryString.append(" group by te.id order by te.dscd ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public Map<String, Object> statisticTotalStock(String dscd,String selectAll,List<Integer> enterpriseIdList){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select '' dscd,'合计' dsnm,ifnull(sum(tt.number),0) total, ");
		queryString.append(" ifnull(sum(case when tt.type = 12 then tt.number end),0) fertilizer, ");
		queryString.append(" ifnull(sum(case when tt.type = 13 then tt.number end),0) pesticide, ");
		queryString.append(" ifnull(sum(case when tt.type = 14 then tt.number end),0) seed, ");
		queryString.append(" ifnull(sum(case when tt.type = 15 then tt.number end),0) other ");
		queryString.append(" from tb_enterprise te left join tb_area ta on te.dscd = ta.id ");
		queryString.append(" left join tb_tool tt on te.id = tt.enterprise_id  ");
		queryString.append(" where te.del_flag = 0 and te.enterprise_type = 3 and tt.del_flag = 0 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			queryString.append(" and te.dscd like :dscd ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		if(!StringUtils.isNullOrEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String, Object> map = (Map<String, Object>) query.getSingleResult();
		return map;
	}
	
	public TbTool findByDnm(String dnm,Integer enterpriseId,Integer id) {
		try {
			String queryString = "from TbTool where  delFlag = 0 and dnm=:dnm and  enterpriseId=:enterpriseId";
			if(id!=null) {
				queryString += " and id != :id ";
			}
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("dnm",dnm);
			query.setParameter("enterpriseId",enterpriseId);
			if(id!=null) {
				query.setParameter("id",id);
			}
			List<TbTool> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<TbTool> findAllRecordNo() {
		try {
			String queryString = " from TbTool where  delFlag = 0 and isSync=1 and(recordNo is null or recordNo='') and enterpriseId in ( select id from TbEnterprise where delFlag=0 and enterpriseType=3 ) ";
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
	
	public Object statisticRecordNoCount(Integer enterpriseId){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select count(if((record_no is not null and record_no!=''),tl.id,null)),count(if((record_no is  null or record_no=''),tl.id,null))  ");
		queryString.append(" from tb_tool tl left join tb_enterprise te on te.id=tl.enterprise_id ");
		queryString.append(" where te.del_flag = 0 and te.enterprise_type = 3 and tl.del_flag = 0   ");
		if(enterpriseId!=null) {
			queryString.append(" and te.id = :enterpriseId ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if(enterpriseId!=null) {
			query.setParameter("enterpriseId", enterpriseId);
		}
		List<Object> map = query.getResultList();
		if (null != map && map.size() > 0) {
			return map.get(0);
		}
		return null;
	}
	public Object[] exportStatisticRecordNoCount(Integer enterpriseId){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select count(if((record_no is not null and record_no!=''),tl.id,null)),count(if((record_no is  null or record_no=''),tl.id,null))  ");
		queryString.append(" from tb_tool tl left join tb_enterprise te on te.id=tl.enterprise_id ");
		queryString.append(" where te.del_flag = 0 and te.enterprise_type = 3 and tl.del_flag = 0   ");
		if(enterpriseId!=null) {
			queryString.append(" and te.id = :enterpriseId ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if(enterpriseId!=null) {
			query.setParameter("enterpriseId", enterpriseId);
		}
		List<Object[]> map = query.getResultList();
		if (null != map && map.size() > 0) {
			return map.get(0);
		}
		return null;
	}
	
	public Integer getUniformPriceCount(Integer enterpriseId) {
		String queryString = " SELECT count(*) FROM tb_tool tt where tt.del_flag = 0 and tt.uniform_price <> 2 and tt.enterprise_id = :enterpriseId ";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("enterpriseId", enterpriseId);
		List<Object> list = query.getResultList();
		return Integer.valueOf(list.get(0).toString());
	}
	
	public String getSellNumByToolId(Integer toolId) {
		String queryString = " SELECT sum( CASE WHEN tto.STATUS > 0 THEN tsc.num ELSE 0 END ) sellNum " + 
				"	from  tb_shopping_car tsc " + 
				"	LEFT JOIN tb_res_order_car troc ON troc.car_id = tsc.id " + 
				"	LEFT JOIN tb_tool_order tto ON troc.order_id = tto.id where tsc.tool_id=:toolId ";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("toolId", toolId);
		List<Object> list = query.getResultList();
		if(list!=null)
			return list.get(0).toString();
		return null;
	}
	
}
