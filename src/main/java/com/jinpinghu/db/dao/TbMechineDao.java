package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbMechine;

public class TbMechineDao extends BaseZDao{

	public TbMechineDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	public TbMechine findById(Integer id) {
		try {
			String queryString = "from TbMechine where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbMechine> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	
	public Object[] getMechineInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tm.id,"
 					+ "	te.name ename,"
 					+ "	tm.name,"
 					+ "	tm.model,"
 					+ "	tm.type,"
 					+ "	tm.describe_,"
 					+ "	tm.input_time,"
 					+ " tm.brand "
// 					+ " tt.name tname "
 					+ "FROM "
 					+ "	tb_mechine tm "
 					+ " LEFT JOIN tb_enterprise te ON te.id=tm.enterprise_id "
// 					+ " LEFT JOIN tb_type tt ON tt.id=tm.type "
 					+ "WHERE "
 					+ " tm.del_flag=0 ";
				queryString += " AND tm.id = :id ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getMechineListByPlantProtectionId(Integer plantProtectionId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tm.id,"
 					+ "	te.name ename,"
 					+ "	tm.name,"
 					+ "	tm.model,"
 					+ "	tm.type,"
 					+ "	tm.describe_,"
 					+ "	tm.input_time,"
 					+ " tm.brand "
 					+ "FROM "
 					+ "	tb_mechine tm "
 					+ " LEFT JOIN tb_enterprise te ON te.id=tm.enterprise_id "
 					+ " LEFT JOIN tb_res_plant_protection_mechine trptm ON trptm.mechine_id=tm.id "
 					+ "WHERE "
 					+ " tm.del_flag=0 ";
				queryString += " AND trptm.plant_protection_id = :plantProtectionId ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("plantProtectionId",plantProtectionId);
			List<Object[]> list = query.getResultList();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public String getMechineIdByPlantProtectionId(Integer plantProtectionId) {
		try {
 			String queryString = 
 					"  SELECT group_concat(distinct tm.id) "
 					+ "	from tb_mechine tm "
 		 			+ " LEFT JOIN tb_enterprise te ON te.id=tm.enterprise_id "
 		 			+ " LEFT JOIN tb_res_plant_protection_mechine trptm ON trptm.mechine_id=tm.id "
 					+ "WHERE "
 					+ " tm.del_flag=0 ";
				queryString += " AND trptm.plant_protection_id = :plantProtectionId ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("plantProtectionId",plantProtectionId);
			List<String> list = query.getResultList();
			if (!list.isEmpty()) {
				return list.get(0);
			}
			return "";
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getMechineList(String name,String enterpriseId,String type,String brand,String model,Integer nowPage,Integer pageCount) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tm.id,"
 					+ "	te.name ename,"
 					+ "	tm.name,"
 					+ "	tm.model,"
 					+ "	tm.type,"
 					+ "	tm.describe_,"
 					+ "	tm.input_time,"
 					+ " tm.brand "
// 					+ " tt.name tname "
 					+ "FROM "
 					+ "	tb_mechine tm "
 					+ " LEFT JOIN tb_enterprise te ON te.id=tm.enterprise_id "
// 					+ " LEFT JOIN tb_type tt ON tt.id=tm.type "
 					+ "WHERE "
 					+ " tm.del_flag=0 ";
			if(!StringUtil.isNullOrEmpty(name))
				queryString += " AND tm.name like :name ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND tm.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(type))
				queryString += " AND tm.type = :type ";
			if(!StringUtil.isNullOrEmpty(brand))
				queryString += " AND tm.brand = :brand ";
			if(!StringUtil.isNullOrEmpty(model))
				queryString += " AND tm.model = :model ";
			queryString += " ORDER BY tm.input_time DESC ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(name))
				query.setParameter("name","%"+name+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(type))
				query.setParameter("type",type);
			if(!StringUtil.isNullOrEmpty(brand))
				query.setParameter("brand",brand);
			if(!StringUtil.isNullOrEmpty(model))
				query.setParameter("model",model);
			
			if(pageCount != null && nowPage != null) {
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
	
	public Integer getMechineListCount(String name,String enterpriseId,String type,String brand,String model) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ " COUNT(1) "
 					+ "FROM "
 					+ "	tb_mechine tm "
 					+ "WHERE "
 					+ " tm.del_flag=0 ";
			if(!StringUtil.isNullOrEmpty(name))
				queryString += " AND tm.name like :name ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND tm.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(type))
				queryString += " AND tm.type = :type ";
			if(!StringUtil.isNullOrEmpty(brand))
				queryString += " AND tm.brand = :brand ";
			if(!StringUtil.isNullOrEmpty(model))
				queryString += " AND tm.model = :model ";
			queryString += " ORDER BY tm.input_time DESC ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(name))
				query.setParameter("name","%"+name+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(type))
				query.setParameter("type",type);
			if(!StringUtil.isNullOrEmpty(brand))
				query.setParameter("brand",brand);
			if(!StringUtil.isNullOrEmpty(model))
				query.setParameter("model",model);
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				String count=list.get(0).toString();
				return Integer.valueOf(count);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	
	public List<Object[]> getMechineTypeCount(Integer enterpriseId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	COUNT(eu.id) count,eu.type "
 					+ "FROM "
 					+ "	tb_mechine eu  "
 					+ "WHERE "
 					+ " eu.del_flag=0 ";
			if(enterpriseId!=null) {
				queryString += " AND eu.enterprise_id = :enterpriseId ";
			}
			queryString+=" group BY eu.type DESC";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			List <Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
}
