package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbEnterpriseDataPermission;

public class TbEnterpriseDataPermissionDao extends BaseZDao {

	public TbEnterpriseDataPermissionDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public List<Integer> getPermissionEnterpriseId(Integer userTabId){
		String queryString = " select enterprise_id from tb_enterprise_data_permission where del_flag = 0 and user_tab_id = :userTabId ";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("userTabId", userTabId);
		List<Integer> list = query.getResultList();
		return list;
	}
	@SuppressWarnings("unchecked")
	public TbEnterprise findByUserTabId(Integer userTabId) {
		try {
			String queryString = " SELECT te.* FROM `Tb_Enterprise_Data_Permission` rue left join tb_enterprise te on rue.enterprise_id = te.id ";
			queryString       += " where rue.del_flag = 0 and te.del_flag = 0 and rue.user_tab_id = :userTabId ";
			Query query = getEntityManager().createNativeQuery(queryString,TbEnterprise.class);
			query.setParameter("userTabId", userTabId);
			List<TbEnterprise> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}
	
	public List<Map<String,Object>> findInEnterprise(Integer userTabId,String name,String isIn,String dscd,Integer nowPage,Integer pageCount) {
		try {
			String queryString = " SELECT te.id,te.name,te.enterprise_credit_code as enterpriseCreditCode,te.enterprise_legal_person as enterpriseLegalPerson "
					+ " FROM  tb_enterprise te  left join tb_area ta on ta.id=te.dscd  ";
			queryString       += " where  te.del_flag = 0 and te.enterprise_type=3  ";
			if(!StringUtils.isEmpty(name)) {
				queryString += " and te.name like :name ";
			}
			if(!StringUtils.isEmpty(isIn)) {
				if(isIn.equals("0")) {
					queryString += " and te.id not in (select enterprise_id FROM `Tb_Enterprise_Data_Permission` rue where rue.user_tab_id = :userTabId)";
				}else if(isIn.equals("1")) {
					queryString += " and te.id in (select enterprise_id FROM `Tb_Enterprise_Data_Permission` rue where rue.user_tab_id = :userTabId)";
				}
			}
			if(!StringUtils.isEmpty(dscd)) {
				queryString += " and te.dscd = :dscd ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(isIn)) {
				query.setParameter("userTabId", userTabId);
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isEmpty(dscd)) {
				query.setParameter("dscd", dscd);
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
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}
	public Integer findInEnterpriseCount(Integer userTabId,String name,String isIn,String dscd) {
		try {
			String queryString = " SELECT count(*) "
					+ "FROM  tb_enterprise te ";
			queryString       += " where  te.del_flag = 0 and te.enterprise_type=3  ";
			if(!StringUtils.isEmpty(name)) {
				queryString += " and te.name like :name ";
			}
			if(!StringUtils.isEmpty(isIn)) {
				if(isIn.equals("0")) {
					queryString += "and te.id not in (select enterprise_id FROM `Tb_Enterprise_Data_Permission` rue where rue.user_tab_id = :userTabId)";
				}else if(isIn.equals("1")) {
					queryString += "and te.id in (select enterprise_id FROM `Tb_Enterprise_Data_Permission` rue where rue.user_tab_id = :userTabId)";
				}
			}
			if(!StringUtils.isEmpty(dscd)) {
				queryString += " and te.dscd = :dscd ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(isIn)) {
				query.setParameter("userTabId", userTabId);
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}
	
	@SuppressWarnings("unchecked")
	public TbEnterpriseDataPermission checkIsExist(Integer userTabId,Integer enterpriseId) {
		try {
			String queryString = "from TbEnterpriseDataPermission where delFlag =0  "
					+ "  and userTabId = :userTabId and enterpriseId = :enterpriseId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("userTabId", userTabId);
			query.setParameter("enterpriseId", enterpriseId);
			List<TbEnterpriseDataPermission> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public int delByEnterpriseId(Integer enterpriseId) {
		try {
			String queryString = " UPDATE Tb_Enterprise_Data_Permission SET del_flag=1 WHERE enterprise_id=:enterpriseId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("enterpriseId", enterpriseId);
			int result =query.executeUpdate();

			return result;
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}
}
