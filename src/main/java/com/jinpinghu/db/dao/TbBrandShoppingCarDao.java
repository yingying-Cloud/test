package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbBrandShoppingCar;

public class TbBrandShoppingCarDao extends BaseZDao{

	public TbBrandShoppingCarDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbBrandShoppingCar findById(Integer id) {
		try {
			String queryString = "from TbBrandShoppingCar where  delFlag = 0 and id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbBrandShoppingCar> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Object[]> findByCarId(List<Integer> carId) {
		try {
			String queryString = "select group_concat(tsc.id),sum(tsc.price),tsc.type,tsc.sell_id  "
					+ " from "
					+ " tb_brand_shopping_car tsc "
					+ " left join tb_brand tb on tb.id=tsc.brand_id "
					+ " where  tsc.del_Flag = 0 "
					+ " and tsc.id in (:carId) "
					+ " group by tsc.type,tsc.sell_id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("carId",carId);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbBrandShoppingCar findByBrandId(Integer brandId,Integer enterpriseId) {
		try {
			String queryString = "from TbBrandShoppingCar where  delFlag = 0 and status != 2 ";
			if(brandId!=null) {
				queryString += " and  brandId=:brandId ";
			}
			if(enterpriseId!=null) {
				queryString += " and  enterpriseId=:enterpriseId ";
			}
			Query query = getEntityManager().createQuery(queryString);
			if(brandId!=null) {
				query.setParameter("brandId",brandId);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			List<TbBrandShoppingCar> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbBrandShoppingCar findInByBrandId(Integer brandId ,Integer enterpriseId,Integer type,Integer brandSaleId,Integer sellId) {
		try {
			String queryString = "from TbBrandShoppingCar where  delFlag = 0 and status = 1";
			if(brandId!=null) {
				queryString += " and  brandId=:brandId ";
			}
			if(enterpriseId!=null) {
				queryString += " and  enterpriseId=:enterpriseId ";
			}
			if(sellId!=null) {
				queryString += " and  sellId=:sellId ";
			}
			if(type!=null) {
				queryString += " and  type=:type ";
			}
			if(brandSaleId!=null) {
				queryString += " and  brandSaleId=:brandSaleId ";
			}
			Query query = getEntityManager().createQuery(queryString);
			if(brandId!=null) {
				query.setParameter("brandId",brandId);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			if(type!=null) {
				query.setParameter("type",type);
			}
			if(brandSaleId!=null) {
				query.setParameter("brandSaleId",brandSaleId);
			}
			if(sellId!=null) {
				query.setParameter("sellId",sellId);
			}
			List<TbBrandShoppingCar> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String,Object>> findByEnterpriseId(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" tsc.id,");
			sb.append(" tsc.status,");
//			sb.append(" if(tsc.type=1"
//					+ ",(select group_concat(tr.name) from tb_res_enterprise_brand trtb left join tb_enterprise tr on trtb.enterprise_id=tr.id where tsc.brand_id=trtb.brand_id)"
//					+ ",(select  group_concat(tr.name) from tb_brand_sale tbs inner join tb_enterprise tr on tbs.enterprise_id=tr.id where tsc.brand_sale_id=tbs.id)"
//					+ "  ) enterpriseName, ");
			
			
			sb.append(" te.name enterpriseName,");
			sb.append(" tl.product_name brandName,");
			sb.append(" tsc.num,");
			sb.append(" if(tsc.type=1,tl.price,'') price,");
			sb.append(" tsc.is_pay ");
			sb.append(" ,ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_brand_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.brand_id = tl.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ),'') fileUrl ");
			sb.append(" ,tsc.type ");
			sb.append(" from Tb_brand_shopping_car tsc ");
			sb.append(" left join tb_brand tl on tl.id = tsc.brand_id   ");
			sb.append(" left join tb_enterprise te on te.id = tsc.sell_id   ");
			sb.append("  where  tsc.del_Flag = 0 and tsc.status != 2 ");
			if(enterpriseId!=null) {
				sb.append(" and  tsc.enterprise_Id=:enterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public String findByEnterpriseIdCount(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" ifnull(cast(sum(tsc.num+0) as decimal(10,0)),0)");
			sb.append(" from Tb_brand_shopping_car tsc LEFT JOIN tb_enterprise te on te.id=tsc.enterprise_id ");
			sb.append(" left join tb_brand tl on tl.id = tsc.brand_id ");
			sb.append("  where  tsc.del_Flag = 0 and tsc.status != 2 ");
			if(enterpriseId!=null) {
				sb.append(" and  tsc.enterprise_Id=:enterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			List <Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0).toString();
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer findByCarCount(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" count(tsc.id)");
			sb.append(" from Tb_brand_shopping_car tsc LEFT JOIN tb_enterprise te on te.id=tsc.enterprise_id ");
			sb.append(" left join tb_brand tl on tl.id = tsc.brand_id ");
			sb.append("  where  tsc.del_Flag = 0 and tsc.status != 2 ");
			if(enterpriseId!=null) {
				sb.append(" and  tsc.enterprise_Id=:enterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			List <Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String,Object>> findInOrderId(Integer orderId) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" tsc.id,");
			sb.append(" tsc.status,");
			sb.append(" tl.product_name brandName,");
			sb.append(" tsc.num,");
			sb.append(" tl.price,");
			sb.append(" tsc.is_Pay, ");
			sb.append(" tsc.brand_id brandId ");
			sb.append(" ,ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_brand_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.brand_id = tl.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ),'') fileUrl ");
			sb.append(" ,tl.unit unit ");
			sb.append(" ,(select group_concat(tr.name) from tb_res_enterprise_brand trtb left join tb_enterprise tr on trtb.enterprise_id=tr.id where tsc.brand_id=trtb.brand_id  ) enterpriseName ");
			sb.append(" ,tsc.type type,tsc.brand_sale_id brandSaleId ");
			sb.append(" from Tb_brand_shopping_car tsc ");
			sb.append(" left join tb_brand tl on tl.id = tsc.brand_id ");
			sb.append("  where  tsc.del_Flag = 0  ");
			if(orderId!=null) {
				sb.append(" and  tsc.id in (select brand_car_id from tb_res_brand_order_car where brand_order_id=:orderId  ) ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(orderId!=null) {
				query.setParameter("orderId",orderId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String,Object>> findInOrderIdByTrademark(Integer orderId) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" tsc.id,");
			sb.append(" tsc.status,");
			sb.append(" tl.product_name brandName,");
			sb.append(" tsc.num,");
			sb.append(" cast(if(tsc.type=1,tsc.brand_price,'') as decimal) price,");
			sb.append(" tsc.is_Pay, ");
			sb.append(" tsc.brand_id brandId ");
			sb.append(" ,ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_brand_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.brand_id = tl.id ");
			sb.append(" AND f.file_type = 1 and rfg.del_flag=0  LIMIT 1 ),'') fileUrl ");			
			sb.append(" ,tl.unit unit ");
//			sb.append(" ,if(tsc.type=1"
//					+ ",(select group_concat(tr.name) from tb_res_enterprise_brand trtb left join tb_enterprise tr on trtb.enterprise_id=tr.id where tsc.brand_id=trtb.brand_id)"
//					+ ",(select  group_concat(tr.name) from tb_brand_sale tbs inner join tb_enterprise tr on tbs.enterprise_id=tr.id where tsc.brand_sale_id=tbs.id)"
//					+ "  ) enterpriseName ");
			sb.append(",te.name enterpriseName");
			sb.append(" ,if(tsc.type=1,'',(select date_format(tbs.input_time,'%Y-%m-%d') from tb_brand_sale tbs where tsc.brand_sale_id=tbs.id )) inputTime ");
//			if(enterpriseId!=null) {
//				sb.append(" ,(select num from tb_res_brand_car_num  trbc where trbc.brand_car_id=tsc.id and trbc.enterprise_id=:enterpriseId) enterpriseNum ");
//			}
			sb.append(",tsc.type,tl.spec  ");
			sb.append(" from Tb_brand_shopping_car tsc ");
			sb.append(" left join tb_brand tl on tl.id = tsc.brand_id ");
			sb.append(" left join tb_enterprise te on te.id = tsc.sell_id ");
//			sb.append("  left join tb_res_brand_car_num trbc on trbc.brand_car_id=tsc.id ");
			sb.append("  where  tsc.del_Flag = 0  ");
			if(orderId!=null) {
				sb.append(" and  tsc.id in (select brand_car_id from tb_res_brand_order_car where brand_order_id=:orderId  ) ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(orderId!=null) {
				query.setParameter("orderId",orderId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public String getOrderPrice(Integer orderId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select cast(sum(tsc.price) as decimal(10,1)) price ");
			sb.append(" from Tb_brand_shopping_car tsc ");
			sb.append(" left join tb_brand tl on tl.id = tsc.brand_id ");
//			sb.append("  left join tb_res_brand_car_num trbc on trbc.brand_car_id=tsc.id ");
			sb.append("  where  tsc.del_Flag = 0  ");
			if(orderId!=null) {
				sb.append(" and  tsc.id in (select brand_car_id from tb_res_brand_order_car where brand_order_id=:orderId  ) ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(orderId!=null) {
				query.setParameter("orderId",orderId);
			}
			List <Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0).toString();
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	
	
}
