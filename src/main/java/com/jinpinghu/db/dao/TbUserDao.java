package com.jinpinghu.db.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbUser;
import com.mysql.cj.util.StringUtils;


@SuppressWarnings("unchecked")
public class TbUserDao extends BaseZDao {

	public TbUserDao(EntityManager _em) {
		super(_em);
	}

	public TbUser findById(Integer id) {
		try {
			TbUser instance = getEntityManager().find(TbUser.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TbUser findById2(Integer id) {
		try {
			String queryString = "from TbUser where delFlag = 0 and id = :id  ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			List<TbUser> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TbUser findByPhone(String mobile) {
		try {
			String queryString = "from TbUser where delFlag = 0 and mobile = :mobile  ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("mobile", mobile);
			List<TbUser> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbUser findByUsername2(String username2) {
		try {
			String queryString = "from TbUser where delFlag = 0 and username2 = :username2  ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("username2", username2);
			List<TbUser> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TbUser login(String mobile,String password) {
		try {
			String queryString = "from TbUser where delFlag = 0 and  "
					+ "  mobile = :mobile and password = :password ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("mobile", mobile);
			query.setParameter("password", password);
			List<TbUser> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TbUser wechatLogin(String wxId) {
		try {
			String queryString = "from TbUser where delFlag = 0 and  "
					+ "  wxId = :wxId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("wxId", wxId);
			List<TbUser> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TbUser checkLogin(String userId,String apiKey) {
		try {
			String queryString = "from TbUser where delFlag = 0 and  "
					+ "  userId = :userId and apiKey = :apiKey ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("userId", userId);
			query.setParameter("apiKey", apiKey);
			List<TbUser> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TbUser checkLogin2(String userId) {
		try {
			String queryString = "from TbUser where delFlag = 0 and "
					+ "  userId = :userId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("userId", userId);
			List<TbUser> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TbUser checkPhone(String userId,String apiKey,String mobile) {
		try {
			String queryString = "from TbUser where delFlag = 0 and "
					+ "  userId != :userId and apiKey != :apiKey and mobile = :mobile ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("userId", userId);
			query.setParameter("apiKey", apiKey);
			query.setParameter("mobile", mobile);
			List<TbUser> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Object[]> getUserInfo(String name,String enterpriseName,String mobile,Integer roleId,Integer status,Integer enterpriseType) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tu.id,tu.mobiel,tu.name,tu.expert_idcard,tu.expert_field,te.name tnm,te.enterprise_type,te.status from tb_user tu left join bt_res_user_role trur on trur.user_tab_id=tu.id   ");
			sb.append(" left join tb_res_user_enterprise true on true.user_tab_id=tu.id left join tb_enterprise te on te.id=true.enterprise_id ");
			sb.append(" where tu.del_flag=0  ");
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.enterprise_name like :enterpriseName  ");
			}
			if(enterpriseType!=null) {
				sb.append(" and te.enterprise_type = :enterpriseType  ");
			}
			if(status!=null) {
				sb.append(" and te.status = :status  ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tu.name like :name  ");
			}
			if(!StringUtils.isNullOrEmpty(mobile)) {
				sb.append(" and tu.mobile = :mobile  ");
			}
			if(roleId!=null) {
				sb.append(" and trur.role_id = :roleId  ");
			}

			Query query = getEntityManager().createQuery(sb.toString());

			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(enterpriseType!=null) {
				query.setParameter("enterpriseType",enterpriseType );
			}
			if(status!=null) {
				query.setParameter("status",status );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(mobile)) {
				query.setParameter("mobile",mobile );
			}
			if(roleId!=null) {
				query.setParameter("roleId",roleId );
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

	public Integer getNewestUserId() {
		try {
			String queryString = "select MAX(id) from tb_user ";
			Query query = getEntityManager().createNativeQuery(queryString);
			Integer res = (Integer) query.getSingleResult();
			return res;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Integer delUserById(Integer id){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" UPDATE tb_user set del_flag=1 WHERE id=:id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id",id);
			Integer res = query.executeUpdate();
			return res;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Object[]> findUserList(String name,String mobile,List<String> roleId,Integer nowPage,Integer pageCount,String dscd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT tu.id,tu.mobile,DATE_FORMAT(tu.reg_time ,'%Y-%m-%d %H:%i:%s') AS reg_time,tu.name,tu.expert_idcard,tu.expert_field,te.name AS enterprise_name,tr.role_name"
					+ " ,DATE_FORMAT(tu.last_time ,'%Y-%m-%d %H:%i:%s') AS lastTime,tu.dscd,ta.name dsnm,trur.role_id ");
			sb.append(" FROM tb_user tu LEFT JOIN tb_res_user_role trur ON trur.user_tab_id=tu.id ");
			sb.append(" LEFT JOIN tb_role tr ON tr.id=trur.role_id  ");
			sb.append(" LEFT JOIN tb_res_user_enterprise rue ON rue.user_tab_id=tu.id ");
			sb.append(" LEFT JOIN tb_enterprise te ON te.id=rue.enterprise_id ");
			sb.append(" left join tb_area ta on ta.id = tu.dscd ");
			sb.append(" WHERE tu.del_flag=0 ");
			if (!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and tu.dscd like :dscd ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" AND tu.name LIKE :name  ");
			}
			if(!StringUtils.isNullOrEmpty(mobile)) {
				sb.append(" AND tu.mobile LIKE :mobile  ");
			}
			if(roleId!=null) {
				sb.append(" AND trur.role_Id in :roleId  ");
			}
			sb.append(" GROUP BY tu.id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(mobile)) {
				query.setParameter("mobile","%"+mobile+"%" );
			}
			if(roleId!=null) {
				query.setParameter("roleId",roleId );
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

	public BigInteger findUserListCount(String name,String mobile,List<String> roleId,String dscd){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(distinct tu.id)  ");
			sb.append(" FROM tb_user tu LEFT JOIN tb_res_user_role trur ON trur.user_tab_id=tu.id ");
			sb.append(" LEFT JOIN tb_role tr ON tr.id=trur.role_id  ");
			sb.append(" LEFT JOIN tb_res_user_enterprise rue ON rue.user_tab_id=tu.id ");
			sb.append(" LEFT JOIN tb_enterprise te ON te.id=rue.enterprise_id ");
			sb.append(" WHERE tu.del_flag=0 ");
			if (!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and tu.dscd like :dscd ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" AND tu.name LIKE :name  ");
			}
			if(!StringUtils.isNullOrEmpty(mobile)) {
				sb.append(" AND tu.mobile LIKE :mobile  ");
			}
			if(roleId!=null) {
				sb.append(" AND trur.role_Id in :roleId  ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(mobile)) {
				query.setParameter("mobile","%"+mobile+"%" );
			}
			if(roleId!=null) {
				query.setParameter("roleId",roleId );
			}
			BigInteger res=(BigInteger)query.getSingleResult();
			return res;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	
}
