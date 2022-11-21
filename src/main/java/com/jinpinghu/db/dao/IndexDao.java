package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class IndexDao extends BaseZDao {

	public IndexDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	//各产品销售量
	public List<Map<String, Object>> statisticAllBrand(Integer nowPage,Integer pageCount,String name) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT  ");
			sb.append(" cast(sum( tbsc.num ) as decimal) num,  ");
			sb.append(" tb.product_name productName,  ");
			sb.append(" cast(sum( tbsc.price ) as decimal) price  ");
			sb.append(" FROM  ");
			sb.append(" tb_sell_order tbo  ");
			sb.append(" INNER JOIN tb_res_sell_order_car trboc ON tbo.id = trboc.sell_order_id  ");
			sb.append(" INNER JOIN tb_sell_shopping_car tbsc ON tbsc.id = trboc.sell_car_id  ");
			sb.append(" INNER JOIN tb_sell_brand tsb ON tsb.id = tbsc.brand_id   ");
			sb.append(" INNER JOIN tb_brand tb ON tb.id = tsb.brand_id   ");
			sb.append(" WHERE  ");
			sb.append(" tbo.del_flag = 0 and tbo.status>=0  ");
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and tb.product_name like :name ");
			}
			sb.append(" GROUP BY  ");
			sb.append(" tb.id  ");
			sb.append(" order by price desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
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
	//各产品销售量
	public Integer statisticAllBrandCount(String name) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT  ");
			sb.append(" count(distinct tb.id) count  ");
			sb.append(" FROM  ");
			sb.append(" tb_sell_order tbo  ");
			sb.append(" INNER JOIN tb_res_sell_order_car trboc ON tbo.id = trboc.sell_order_id  ");
			sb.append(" INNER JOIN tb_sell_shopping_car tbsc ON tbsc.id = trboc.sell_car_id  ");
			sb.append(" INNER JOIN tb_sell_brand tsb ON tsb.id = tbsc.brand_id   ");
			sb.append(" INNER JOIN tb_brand tb ON tb.id = tsb.brand_id   ");
			sb.append(" WHERE  ");
			sb.append(" tbo.del_flag = 0 and tbo.status>=0  ");
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and tb.product_name like :name ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
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
	//各产品类型数量
		public List<Map<String, Object>> statisticBrandTypeCount() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT  ");
				sb.append(" count( distinct tacp.brand_id ) count,tb.type  ");
				sb.append(" FROM  ");
				sb.append(" tb_applicant_company_product tacp ");
				sb.append(" inner join tb_brand tb on tb.id=tacp.brand_id    ");
				sb.append(" inner join tb_applicant_company tac on tac.id=tacp.applicant_company_id ");
				sb.append(" WHERE  ");
				sb.append(" tacp.del_flag = 0 and tac.status=1  ");
				sb.append(" GROUP BY  ");
				sb.append(" tb.type  ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
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
	
	
	//产品销售城市去向
	public List<Map<String, Object>> statisticAllBrandArea() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" 	cast(sum( tbsc.num ) as decimal) num, ");
			sb.append(" 	ta.NAME dsnm, ");
			sb.append(" 	cast(sum( tbsc.price ) as decimal(10,2)) price ");
			sb.append(" FROM ");
			sb.append(" 	tb_sell_order tbo ");
			sb.append(" 	INNER JOIN tb_res_sell_order_car trboc ON tbo.id = trboc.sell_order_id ");
			sb.append(" 	INNER JOIN tb_sell_shopping_car tbsc ON tbsc.id = trboc.sell_car_id ");
			sb.append(" 	INNER JOIN tb_enterprise te ON te.id = tbo.enterprise_id ");
			sb.append(" 	INNER JOIN tb_area ta ON ta.id = te.dscd  ");
			sb.append(" WHERE ");
			sb.append(" 	tbo.del_flag = 0  and tbo.status>=0  ");
			sb.append(" GROUP BY ");
			sb.append(" 	te.dscd ");
			sb.append(" order by ta.id ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
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
	//本周订单数
	public Integer statisticWeekOrder(String startTime,String endTime) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" 	COUNT( tbo.id ) count ");
			sb.append(" FROM ");
			sb.append(" 	tb_sell_order tbo  ");
			sb.append(" WHERE ");
			sb.append(" 	tbo.del_flag = 0 and status>=0   ");
			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
				sb.append(" 	and DATE_FORMAT( tbo.input_time, '%Y-%m-%d' ) >= :startTime  and DATE_FORMAT( tbo.input_time, '%Y-%m-%d' ) <= :endTime  ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
				query.setParameter("startTime",startTime );
				query.setParameter("endTime",endTime );
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
	
	
	//本周下单信息
		public List<Map<String, Object>> statisticWeekOrderInfo(String startTime,String endTime,Integer nowPage,Integer pageCount) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ");
				sb.append(" 		add_people,cast(price AS DECIMAL ) price, date_format(tbo.input_time,'%Y-%m-%d %H:%i:%s') inputTime,te.name enterpriseName,tbo.id id   ");
				sb.append(" FROM ");
				sb.append(" 	tb_sell_order tbo inner join tb_enterprise te on te.id = tbo.enterprise_id ");
				sb.append(" WHERE ");
				sb.append(" 	tbo.del_flag = 0 and tbo.status>=0 ");
				if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
					sb.append(" 	and DATE_FORMAT( tbo.input_time, '%Y-%m-%d' ) >= :startTime  and DATE_FORMAT( tbo.input_time, '%Y-%m-%d' ) <= :endTime  ");
				}
				sb.append(" order by tbo.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
				query.setParameter("startTime",startTime );
				query.setParameter("endTime",endTime );
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
		
		//全部订单信息
		public Map<String, Object> statisticAllOrder() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ");
				sb.append(" 		COUNT( tbo.id ) count ,ifnull(cast(sum(price) as decimal(10,2)),0) price  ");
				sb.append(" FROM ");
				sb.append(" 	tb_sell_order tbo  ");
				sb.append(" WHERE ");
				sb.append(" 	tbo.del_flag = 0 and status>=0  ");
				
			Query query = getEntityManager().createNativeQuery(sb.toString());
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
	

		//总种植面积和产量
		public Map<String, Object> statisticAllWork() {
			try {
				StringBuffer sb = new StringBuffer();

				sb.append(" SELECT ");
				sb.append("(");
				sb.append(" select sum(plant_scope) from tb_enterprise where enterprise_type=1 and del_flag=0 ");
				sb.append(") area,");
				
				sb.append("(");
				sb.append(" select cast(sum(number*price) as decimal(10,2)) from tb_plant_warehouse where record_type=2 and del_flag=0 ");
				sb.append(") production");
				

				Query query = getEntityManager().createNativeQuery(sb.toString());
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

		//各街道合作社产值
		public List<Map<String, Object>> statisticAllEnterpriseProduction() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ");
				sb.append(" 	sum( area ) area, ");
				sb.append(" 	sum( expected_production ) production, ");
				sb.append(" 	te.name,te.id id ");
				sb.append(" FROM ");
				sb.append(" 	tb_work tw inner join tb_enterprise te on tw.enterprise_id=te.id ");
				sb.append(" WHERE ");
				sb.append(" tw.del_flag = 0 ");
				sb.append(" GROUP BY tw.enterprise_id order by production desc limit 5 ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
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
		//各接到合作社数量以及占比
		public List<Map<String, Object>> statisticAllEnterpriseByDscd() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ta.name ,count(te.id) count ");
				sb.append(" from tb_enterprise te inner join tb_area ta on ta.id=te.dscd ");
				sb.append(" where te.del_flag=0 and enterprise_type=1 group by te.dscd ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
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
		
	
		
		//全部种植企业经纬度
		public List<Map<String, Object>> statisticAllEnterprise() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT te.name,te.x,te.y,te.id enterpriseId,te.type ");
				sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd ");
				sb.append(" where te.del_flag=0 and enterprise_type=1 ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
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
		//本月贷款申请
		public Object statisticAllLoanApplication() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT sum(loan_amount) loanAmount ");
				sb.append(" from tb_enterprise_loan_application tela ");
				sb.append(" where tela.del_flag=0 ");
				sb.append(" and DATE_FORMAT( tela.input_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
				List<Object> list = query.getResultList();
				if (null != list && list.size() > 0) {
					return list.get(0);
				}
				return null;
			} catch (RuntimeException re) {
				throw re;
			}
		}
		//本月贷款申请
				public Object statisticAllLoanApplicationCount() {
					try {
						StringBuffer sb = new StringBuffer();
						sb.append(" SELECT count(loan_amount) count ");
						sb.append(" from tb_enterprise_loan_application tela ");
						sb.append(" where tela.del_flag=0 ");
						sb.append(" and DATE_FORMAT( tela.input_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) ");
						Query query = getEntityManager().createNativeQuery(sb.toString());
						query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						List<Object> list = query.getResultList();
						if (null != list && list.size() > 0) {
							return list.get(0);
						}
						return null;
					} catch (RuntimeException re) {
						throw re;
					}
				}
		//贷款总金额
		public Object statisticTotalLoanApplication() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT sum(loan_amount) loanAmount ");
				sb.append(" from tb_enterprise_loan_application tela ");
				sb.append(" where tela.del_flag=0 ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
				List<Map<String,Object>> list = query.getResultList();
				if (null != list && list.size() > 0) {
					return list.get(0);
				}
				return null;
			} catch (RuntimeException re) {
				throw re;
			}
		}
		
		//本月贷款申请通过
		public Object statisticPassLoanApplication() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT sum(loan_amount) loanAmount ");
				sb.append(" from tb_enterprise_loan_application tela ");
				sb.append(" where tela.del_flag=0  ");
				sb.append(" and DATE_FORMAT( tela.input_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) and status>0 ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
				List<Map<String,Object>> list = query.getResultList();
				if (null != list && list.size() > 0) {
					return list.get(0);
				}
				return null;
			} catch (RuntimeException re) {
				throw re;
			}
		}
		
		//本月贷款申请信息
		public List<Map<String, Object>> statisticAllLoanApplicationInfo() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT sum(loan_amount) loanAmount,te.name,count(tela.id) loanNum ");
				sb.append(" from tb_enterprise_loan_application tela ");
				sb.append(" inner join tb_enterprise te on te.id=tela.enterprise_id ");
				sb.append(" where tela.del_flag=0 and tela.status>1 ");
//				sb.append(" and DATE_FORMAT( tela.input_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )  ");
				sb.append(" group by te.id ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
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
		//入住合作社
		public Integer statisticAllCompany() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT count(id) count ");
				sb.append(" from tb_applicant_company ");
				sb.append(" where del_flag=0 and status=1 ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
				List<Object> list = query.getResultList();
				if (null != list && list.size() > 0) {
					String count = list.get(0).toString();
					return Integer.valueOf(count);
				}
				return null;
			} catch (RuntimeException re) {
				throw re;
			}
		}
		//品牌
		public Integer statisticAllCompanyProduct() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT count(distinct tacp.brand_id) count ");
				sb.append(" from tb_applicant_company_product tacp inner join tb_applicant_company tac on tac.id=tacp.applicant_company_id ");
				sb.append(" where tacp.del_flag=0  and tac.status=1  ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				String count = list.get(0).toString();
				return Integer.valueOf(count);
			}
			return null;
			} catch (RuntimeException re) {
				throw re;
			}
		}
		//入住合作社信息
		public List<Map<String, Object>> statisticAllCompanyInfo() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT tac.name,tacp.product_name productName,tac.enterprise_id id ");
				sb.append(" from tb_applicant_company tac INNER JOIN tb_applicant_company_product tacp ON tac.id=tacp.applicant_company_id  ");
				sb.append(" where tac.del_flag=0 and tacp.del_flag=0  and tac.status=1 ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
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
	
		
		
		//company企业列表
		public List<Map<String, Object>> getAllCompanyList() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" select id,name,x,y,enterprise_id enterpriseId ");
				sb.append(" from tb_applicant_company ");
				sb.append(" where del_flag=0 and status=1 ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
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
			;
//		--统计在company订单表里各个商品的销售量
		public List<Map<String, Object>> statisticAllCompanyOrderBrand() {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ");
				sb.append(" 	sum(tbsc.num) num, ");
				sb.append(" 	tb.product_name productName ");
				sb.append("		,cast(sum(tbsc.price)  as decimal) money ");
				sb.append(" FROM ");
				sb.append(" tb_sell_order tbo ");
				sb.append(" INNER JOIN tb_res_sell_order_car trboc ON tbo.id = trboc.sell_order_id ");
				sb.append(" INNER JOIN tb_sell_shopping_car tbsc ON tbsc.id = trboc.sell_car_id ");
				sb.append(" inner join tb_enterprise te on te.id=tbsc.enterprise_id ");
				sb.append(" inner join tb_sell_brand tsb on tsb.id=tbsc.brand_id ");
				sb.append(" inner join tb_brand tb on tb.id=tsb.brand_id ");
				sb.append(" WHERE ");
				sb.append(" 	tbo.del_flag = 0  ");
				sb.append(" 	AND tbo.STATUS >= 0  ");
//				sb.append(" 	AND te.id IN ( SELECT enterprise_id FROM tb_applicant_company WHERE del_flag = 0  and status=1 ) ");
				sb.append(" 	group by tb.id order by money desc; ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
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
//			--统计在company里的种植任务产值
			public List<Map<String, Object>> statisticAllCompanyWork() {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" select sum(area) area,sum(production) production,productName from (  ");
					sb.append(" SELECT  ");
					sb.append(" 			  area area,  ");
					sb.append(" 			 ( select sum(number*price) from tb_plant_warehouse where record_type=2 and del_flag=0 and work_id=tw.id ) production  , ");
					sb.append(" 			 tb.product_name productName, ");
					sb.append(" 			 tb.id ");
					sb.append(" 		 FROM  ");
					sb.append(" 		 tb_work tw  ");
					sb.append(" 		 inner join tb_enterprise te on te.id=tw.enterprise_id ");
					sb.append(" 		 inner join tb_brand tb on tb.id=tw.crop  ");
					sb.append(" 		 WHERE  ");
					sb.append(" 		 tw.del_flag = 0  ");
					sb.append(" AND tw.enterprise_id IN ( SELECT enterprise_id FROM tb_applicant_company WHERE del_flag = 0  and status=1  ) ) a  ");
					sb.append(" GROUP BY a.id ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
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
//				--金额
			public List<Map<String, Object>> statisticAllCompanyOrder(Integer nowPage,Integer pageCount,String name) {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT ");
					sb.append(" cast(sum(tbo.price) as decimal(10,2)) price, ");
					sb.append(" te.`name` enterpriseName,count(tbo.id) count ");
					sb.append(" FROM ");
					sb.append(" tb_sell_order tbo ");
					sb.append(" inner join tb_enterprise te on te.id=tbo.enterprise_id ");
					sb.append(" inner join tb_area ta on ta.id=te.dscd ");
					sb.append(" WHERE ");
					sb.append(" tbo.del_flag = 0  ");
					sb.append(" AND tbo.STATUS >= 0  ");
					if(!StringUtils.isEmpty(name)) {
						sb.append(" and te.`name` like :name ");
					}
					sb.append(" group by te.id  order by price desc ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
					if(!StringUtils.isEmpty(name)) {
						query.setParameter("name", "%"+name+"%");
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
			public Integer statisticAllCompanyOrderCount(String name) {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT ");
					sb.append(" count(distinct te.id) ");
					sb.append(" FROM ");
					sb.append(" tb_sell_order tbo ");
					sb.append(" inner join tb_enterprise te on te.id=tbo.enterprise_id ");
					sb.append(" inner join tb_area ta on ta.id=te.dscd ");
					sb.append(" WHERE ");
					sb.append(" tbo.del_flag = 0  ");
					sb.append(" AND tbo.STATUS >= 0  ");
					if(!StringUtils.isEmpty(name)) {
						sb.append(" and te.`name` like :name ");
					}
					Query query = getEntityManager().createNativeQuery(sb.toString());
					if(!StringUtils.isEmpty(name)) {
						query.setParameter("name", "%"+name+"%");
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
//			--区域
			public List<Map<String, Object>> statisticAllCompanyOrderDscd() {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT ");
					sb.append(" cast(sum(tbo.price) as decimal(10,2)) price, ");
					sb.append(" ta.name dscdName,count(tbo.id) count  ");
					sb.append(" FROM ");
					sb.append(" 	tb_sell_order tbo ");
					sb.append(" 	inner join tb_enterprise te on te.id=tbo.enterprise_id ");
					sb.append(" 	inner join tb_area ta on ta.id=te.dscd ");
					sb.append(" WHERE ");
					sb.append(" 	tbo.del_flag = 0  ");
					sb.append(" AND tbo.STATUS >= 0  ");
					sb.append(" group by te.dscd order by price desc ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
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
//			--每个月
			public Map<String, Object> statisticAllCompanyOrderMonth(String month) {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT ");
					sb.append(" cast(sum(tbo.price) as decimal(10,2)) price,count(tbo.id) count ");
//					sb.append("  date_format(tbo.input_time,'%Y-%m') inputTime ");
					sb.append(" FROM ");
					sb.append(" tb_sell_order tbo ");
					sb.append(" inner join tb_enterprise te on te.id=tbo.enterprise_id ");
					sb.append(" inner join tb_area ta on ta.id=te.dscd ");
					sb.append(" WHERE ");
					sb.append(" tbo.del_flag = 0  ");
					sb.append(" AND tbo.STATUS >= 0  ");
					sb.append(" and date_format(tbo.input_time,'%Y-%m')=:month ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
					query.setParameter("month",month);
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
//			--品牌
			public List<Map<String, Object>> statisticAllCompanyBrand(Integer nowPage,Integer pageCount) {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT ");
					sb.append(" sum(tbsc.num) num, ");
					sb.append(" tb.product_name productName, ");
					sb.append(" cast(sum(tbsc.price) as decimal(10,2)) price,count(tbo.id) count  ");
					sb.append(" FROM ");
					sb.append(" tb_sell_order tbo ");
					sb.append(" INNER JOIN tb_res_sell_order_car trboc ON tbo.id = trboc.sell_order_id ");
					sb.append(" INNER JOIN tb_sell_shopping_car tbsc ON tbsc.id = trboc.sell_car_id ");
					sb.append(" inner join tb_enterprise te on te.id=tbsc.enterprise_id ");
					sb.append(" inner join tb_sell_brand tsb on tsb.id=tbsc.brand_id ");
					sb.append(" inner join tb_brand tb on tb.id=tsb.brand_id ");
					sb.append(" WHERE ");
					sb.append(" 	tbo.del_flag = 0  ");
					sb.append(" 	AND tbo.STATUS >= 0  ");
					sb.append(" group by tb.id order by price desc ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
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
//			--各作物种植土地面积
			public List<Map<String, Object>> statisticAllWorkBrandArea() {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT ");
					sb.append(" sum( area ) area, ");
					sb.append(" sum( expected_production ) production, ");
					sb.append(" tb.product_name productName ");
					sb.append(" FROM ");
					sb.append(" tb_work tw ");
					sb.append(" INNER JOIN tb_enterprise te ON te.id = tw.enterprise_id ");
					sb.append(" INNER JOIN tb_brand tb ON tb.id = tw.crop  ");
					sb.append(" WHERE ");
					sb.append(" 	tw.del_flag = 0  ");
					sb.append(" GROUP BY ");
					sb.append(" 	tb.id order by area desc ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
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
//			--农资
			public List<Map<String, Object>> statisticAllTool() {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT tt.type,cast(sum(trcft.num) as decimal(10,2)) num,tts.name ");
					sb.append(" from tb_res_crop_farming_tool trcft ");
					sb.append(" INNER JOIN tb_tool tt on tt.id=trcft.tool_id ");
					sb.append(" inner join tb_type tts on tts.id=tt.type ");
					sb.append(" 	where trcft.del_flag=0 and ( (tt.type=12 and tt.unit='g') or (tt.type=13 and tt.unit='ml') or (tt.type=14  and tt.unit='g') or (tt.type=15 and tt.unit='g' ) ) ");
					sb.append(" GROUP BY tt.type ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
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
//			--农资top
			public List<Map<String, Object>> statisticAllToolTop(Integer nowPage,Integer pageCount) {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" 	SELECT cast(sum(if(tt.type=12 and tt.unit='g' ,trcft.num,0)) as decimal(10,2)) fl,te.name enterpriseName ");
					sb.append(" ,cast(sum(if(tt.type=13 and tt.unit='ml',trcft.num,0)) as decimal(10,2)) ny,cast(sum(if(tt.type=14  and tt.unit='g' ,trcft.num,0)) as decimal(10,2)) zz,te.id enterpriseId  ");
					sb.append(" from tb_res_crop_farming_tool trcft ");
					sb.append(" INNER JOIN tb_tool tt on tt.id=trcft.tool_id ");
					sb.append(" inner join tb_enterprise te on te.id=trcft.enterprise_id ");
					sb.append(" where trcft.del_flag=0 ");
					sb.append(" GROUP BY trcft.enterprise_id ");
					sb.append(" order by num desc ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
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
			
			public Integer statisticAllToolTopCount() {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" 	SELECT count(distinct te.id) count  ");
					sb.append(" from tb_res_crop_farming_tool trcft ");
					sb.append(" INNER JOIN tb_tool tt on tt.id=trcft.tool_id ");
					sb.append(" inner join tb_enterprise te on te.id=trcft.enterprise_id ");
					sb.append(" where tt.type=13 and trcft.del_flag=0 ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
			
//			--总水量
			public List<Map<String, Object>> statisticAllWater() {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT cast(sum(water_amount) as decimal(10,2)) waterAmount ");
					sb.append(" from tb_res_crop_farming_water trcfw ");
					sb.append(" where del_flag=0 ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
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
//			--用水top
			public List<Map<String, Object>> statisticAllWaterTop(Integer nowPage,Integer pageCount) {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT cast(sum(water_amount) as decimal(10,2)) waterAmount,te.name enterpriseName");
					sb.append(" from tb_res_crop_farming_water trcfw ");
					sb.append(" INNER JOIN tb_enterprise te on te.id=trcfw.enterprise_id ");
					sb.append(" where trcfw.del_flag=0 ");
					sb.append(" group by te.id order by waterAmount desc ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
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
			
			public Integer statisticAllWaterTopCount() {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT count( distinct te.id) count ");
					sb.append(" from tb_res_crop_farming_water trcfw ");
					sb.append(" INNER JOIN tb_enterprise te on te.id=trcfw.enterprise_id ");
					sb.append(" where trcfw.del_flag=0 ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
					List<Object> list = query.getResultList();
					if (null != list && list.size() > 0) {
						return Integer.valueOf(list.get(0).toString());
					}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
			
		
			public List<Map<String, Object>> statisticAllBrandTypeCount() {
				try {
					StringBuffer sb = new StringBuffer();
					sb.append(" SELECT count(distinct tb.id) count,tb.type");
					sb.append(" from tb_enterprise te ");
					sb.append(" INNER JOIN tb_res_enterprise_brand treb on treb.enterprise_id=te.id ");
					sb.append(" inner join tb_brand tb on tb.id=treb.brand_id ");
					sb.append(" where te.del_flag=0 and te.enterprise_type=1 ");
					sb.append(" group by tb.type order by count desc ");
					Query query = getEntityManager().createNativeQuery(sb.toString());
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
	
}
