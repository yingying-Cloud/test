package com.jinpinghu.db.dao;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbToolZero;
import com.mysql.cj.util.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class TbToolZeroDao extends BaseZDao{

	public TbToolZeroDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<Map<String, Object>> findAll(List<String> ids ){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" ttc.id, ");
			sb.append(" ttc.name, ");
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
			sb.append(" from tb_tool_zero tool inner join tb_tool_catalog ttc on ttc.id=tool.tool_catalog_id  ");
			if(ids!=null) {
				sb.append(" and tool.enterprise_id not in ( :ids )");
			}
			sb.append(" where tool.del_flag=0 ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(ids!=null) {
				query.setParameter("ids", ids);
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


	public TbToolZero findById(Integer id) {
		try {
			String queryString = "from TbToolZero where  delFlag = 0 and id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbToolZero> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbToolZero findByToolCatalogId(Integer toolCatalogId) {
		try {
			String queryString = "from TbToolZero where  delFlag = 0 and toolCatalogId=:toolCatalogId  ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("toolCatalogId",toolCatalogId);
			List<TbToolZero> list = query.getResultList();
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


			sb.append(" from tb_tool_zero ttz inner join tb_tool_catalog tool   ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
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

	public List<Object[]> findByName(Integer enterpriseId,String name,String enterpriseName,String enterpriseType,
									 Integer nowPage,Integer pageCount,String supplierName,String type,String unit,List<String> notIn,String code) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tool.id, ");
			sb.append(" tool.name, ");
			sb.append(" tool.model, ");
			sb.append(" tool.specification, ");
			sb.append(" tool.unit,");
			sb.append(" tool.price, ");
			sb.append(" tool.number, ");
			sb.append(" tool.describe_, ");
			sb.append(" tool.type ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_zero_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_zero_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goods_pic,tool.supplier_name,tt.name ttnm,tool.production_units,tool.uniform_price,tool.code,  ");
			sb.append(" sum(case when tto.status > 0 then tsc.num else 0 end) sellNum ");
			sb.append(" from tb_tool_zero tool  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" left join tb_shopping_car tsc on tool.id = tsc.tool_id ");
			sb.append(" left join tb_res_order_car troc on troc.car_id = tsc.id ");
			sb.append(" left join tb_tool_order tto on troc.order_id = tto.id ");
			sb.append(" where tool.del_flag=0 ");
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
				sb.append(" AND tool.code like :code ");
			}
			sb.append(" group by tool.id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
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
			if(notIn!=null) {
				query.setParameter("notIn",notIn );
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit","%"+unit+"%" );
			}
			if(!StringUtil.isNullOrEmpty(code)) {
				query.setParameter("code", "%"+code+"%");
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
								   String type,String unit,List<String> notIn,String code) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(tool.id) ");
			sb.append(" from tb_tool_zero tool  ");
			sb.append(" where tool.del_flag=0 ");
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
				sb.append(" AND tool.code like :code ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
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
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Map<String, Object>> findNotAddToolList(Integer enterpriseId,List<Integer> toolIdList){
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
			sb.append(" describe_ 'describe', ");
			sb.append(" tool.type ");
			sb.append(" ,ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_zero_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_zero_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ),'') goodsPic,tool.production_units productionUnits,tool.uniform_price uniformPrice ");
			sb.append(",tt.name typeName ");
			sb.append(" from tb_tool tool  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" where tool.del_flag=0 ");
			if(toolIdList != null && toolIdList.size()>0)
				sb.append(" and tool.id not in (:toolIdList) ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
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
}
