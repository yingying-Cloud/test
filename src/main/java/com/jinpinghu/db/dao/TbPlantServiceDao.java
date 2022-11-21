package com.jinpinghu.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbPlantService;

public class TbPlantServiceDao extends BaseZDao{

	public TbPlantServiceDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public TbPlantService findById(Integer id) {
		try {
			String queryString = " from TbPlantService where delFlag = 0 and id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			List<TbPlantService> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	/**
	 * 	获取植保计划列表
	 * @createTime:2019-05-17 17:12:03
	 * @author:harrychao
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findPlantServiceList(String name,String enterpriseName,Integer enterpriseId,String dscd,
			String lowPrice,String highPrice,String etName,String serverType,String startTime,String endTime,String status,Integer nowPage,
			Integer pageCount,Integer loginEnterpriseId,String orderby,String sortDirection) {
		try {
			String queryString = " SELECT tpp.id id,tpp.enterprise_id 'enterpriseId',te.name 'enterpriseName',tpp.price, ";
			queryString += " ifnull(tpp.name,'') 'name',ifnull(tpp.order_describe,'') 'orderDescribe',ifnull(tpp.content,'') 'content',tred.distance ";
			queryString += ",(select count(*) from tb_plant_service_order where plant_service_id = tpp.id and del_flag=0 ) orderCount ";
			
			queryString += " ,ifnull(( SELECT file_url "
					+" FROM tb_file f INNER JOIN tb_res_plant_service_file rfg "
					+" ON f.id = rfg.file_id  "
					+" WHERE rfg.plant_service_id = tpp.id "
					+" LIMIT 1 ),'') fileUrl "
					+ ",date_format(start_time,'%Y-%m-%d') startTime,date_format(end_time,'%Y-%m-%d') endTime,server_type serverType,tpp.status,tt.name typeName ";
			
			queryString += " FROM tb_plant_service tpp left join tb_enterprise te on tpp.enterprise_id = te.id left join tb_type tt on tt.id=tpp.server_type ";
			queryString += " left join tb_res_enterprise_distance tred on tred.primary_enterprise_id = :loginEnterpriseId and tred.compare_enterprise_id = tpp.enterprise_id ";
			queryString += " where tpp.del_flag = 0 and te.del_flag = 0 ";
			if(!StringUtils.isEmpty(name))
				queryString += " and (tpp.name like :name or te.name like :name) ";
			if(!StringUtils.isEmpty(enterpriseName))
				queryString += " and te.name like :enterpriseName ";
			if(enterpriseId != null)
				queryString += " and tpp.enterprise_id = :enterpriseId ";
			if(!StringUtils.isEmpty(dscd)) {
				queryString += " and te.dscd = :dscd ";
			}
			if(!StringUtil.isNullOrEmpty(lowPrice)) {
				queryString +=" AND tpp.price+0 >= :lowPrice  ";
			}
			if(!StringUtil.isNullOrEmpty(highPrice)) {
				queryString +=" AND tpp.price+0 <= :highPrice  ";
			}
			if(!StringUtil.isNullOrEmpty(etName)) {
				queryString +=" AND (tpp.name like :etName or te.name like :etName ) ";
			}
			if(!StringUtils.isEmpty(serverType)) {
				queryString += " and tpp.server_type = :serverType ";
			}

			if(!StringUtils.isEmpty(startTime)) {
				queryString += " and date_format(start_Time,'%Y-%m-%d') >= :startTime ";
			}
			if(!StringUtils.isEmpty(endTime)) {
				queryString += " and date_format(start_Time,'%Y-%m-%d') <= :endTime ";
			}
			if(!StringUtils.isEmpty(status)) {
				queryString += " and tpp.status = :status ";
			}
//			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
//				queryString += " and id not in (select protection_id "
//						+ "from tb_plant_protection_server_time "
//						+ "where  :startTime <= date_format(server_time,'%Y-%m-%d') and date_format(server_time,'%Y-%m-%d') <= :endtime ) ";
//			}
			if(StringUtils.isEmpty(orderby)) {
				queryString += " order by convert(tpp.name using gbk) asc ";
			}else if(!StringUtils.isEmpty(orderby)){
				if(orderby.equals("1")) {
					queryString += " order by convert(tpp.name using gbk) ASC ";
				}else if(orderby.equals("4")) {
					queryString += " order by if(orderCount is null,0,1),orderCount ";
				}else if(orderby.equals("5")) {
					queryString += " order by tpp.start_time ";
				}else if(orderby.equals("6")) {
					queryString += " order by if(tpp.price is null or tpp.price = '',1,0),tpp.price+0.0 ";
				}
				List<String> pcSort = new ArrayList<String>(3) {{add("4");add("5");add("6");}};
				if (pcSort.contains(orderby)) {
					if (StringUtils.isEmpty(sortDirection)) {
						queryString += " desc ";
					}else if ("1".equals(sortDirection)) {
						queryString += " asc ";
					}else if ("2".equals(sortDirection)) {
						queryString += " desc ";
					}
				}
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("loginEnterpriseId", loginEnterpriseId);
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			if(!StringUtils.isEmpty(enterpriseName))
				query.setParameter("name", "%"+enterpriseName+"%");
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if(!StringUtils.isEmpty(dscd)) {
				query.setParameter("dscd", dscd);
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
			if(!StringUtils.isEmpty(serverType)) {
				query.setParameter("serverType",  serverType);
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(status)) {
				query.setParameter("status", status);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			if(pageCount != null && nowPage != null) 
				query.setMaxResults(pageCount).setFirstResult((nowPage -1)*pageCount);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	/**
	 * 	获取植保计划数量
	 * @createTime:2019-05-17 17:11:20
	 * @author:harrychao
	 */
	@SuppressWarnings("unchecked")
	public Integer findPlantServiceListCount(String name,String enterpriseName,Integer enterpriseId,String dscd
			,String lowPrice,String highPrice,String etName,String serverType,String startTime,String endTime,String status) {
		try {
			String queryString = " SELECT count(distinct tpp.id) ";
			queryString += " FROM tb_plant_service tpp left join tb_enterprise te on tpp.enterprise_id = te.id ";
			queryString += " where tpp.del_flag = 0 and te.del_flag = 0 ";
			if(!StringUtils.isEmpty(name))
				queryString += " and (tpp.name like :name or te.name like :name) ";
			if(!StringUtils.isEmpty(enterpriseName))
				queryString += " and te.name like :enterpriseName ";
			if(enterpriseId != null)
				queryString += " and tpp.enterprise_id = :enterpriseId ";
			if(!StringUtils.isEmpty(dscd)) {
				queryString += " and te.dscd = :dscd ";
			}
			if(!StringUtil.isNullOrEmpty(lowPrice)) {
				queryString +=" AND tpp.price+0 >= :lowPrice  ";
			}
			if(!StringUtil.isNullOrEmpty(highPrice)) {
				queryString +=" AND tpp.price+0 <= :highPrice  ";
			}
			if(!StringUtil.isNullOrEmpty(etName)) {
				queryString +=" AND (tpp.name like :etName or te.name like :etName ) ";
			}
			if(!StringUtils.isEmpty(serverType)) {
				queryString += " and tpp.server_type = :serverType ";
			}
			if(!StringUtils.isEmpty(startTime)) {
				queryString += " and date_format(start_Time,'%Y-%m-%d') >= :startTime ";
			}
			if(!StringUtils.isEmpty(endTime)) {
				queryString += " and date_format(start_Time,'%Y-%m-%d') <= :endTime ";
			}
			if(!StringUtils.isEmpty(status)) {
				queryString += " and tpp.status = :status ";
			}
//			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
//				queryString += " and id not in (select protection_id "
//						+ "from tb_plant_protection_server_time "
//						+ "where  :startTime <= date_format(server_time,'%Y-%m-%d') and date_format(server_time,'%Y-%m-%d') <= :endtime ) ";
//			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			if(!StringUtils.isEmpty(enterpriseName))
				query.setParameter("name", "%"+enterpriseName+"%");
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if(!StringUtils.isEmpty(dscd)) {
				query.setParameter("dscd", dscd);
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
			if(!StringUtils.isEmpty(serverType)) {
				query.setParameter("serverType",  serverType);
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(status)) {
				query.setParameter("status", status);
			}
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}

}
