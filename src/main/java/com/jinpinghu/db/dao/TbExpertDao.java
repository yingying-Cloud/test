package com.jinpinghu.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbExpert;
import com.mysql.cj.util.StringUtils;

public class TbExpertDao extends BaseZDao {

	public TbExpertDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbExpert findById(Integer id) {
		try {
			TbExpert instance = getEntityManager().find(TbExpert.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String,Object>> findByAll(String name,String status,String type,String lowIntegral,String highIntegral,String adnm,String productTeam,
			Integer nowPage,Integer pageCount,String orderby,String sortDirection){
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select tu.name,tu.mobile,tu.id uid,te.id expertId,te.idcard,te.goods_field goodsField,te.synopsis,te.status,te.address  ");
			sb.append(" ,(select count(*) from tb_post_reply where del_flag=0 and user_tab_id=tu.id ) replyNum ");
			
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_expert_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.expert_id = te.id ");
			sb.append(" AND f.file_type = 1 and rfg.type=1  LIMIT 1 )fileUrl,te.cost,te.type,tt.name typeName,te.adnm ");
			
			sb.append("  from tb_user tu  inner join tb_res_user_expert rue on rue.user_tab_id=tu.id left join tb_expert te on te.id=rue.expert_id "
					+ " left join tb_type tt on tt.id = te.type "
					+ " where tu.del_flag=0 AND te.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tu.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(status)) {
				sb.append(" and find_in_set(te.status , :status) ");
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and find_in_set(te.type , :type) ");
			}
			if(!StringUtil.isNullOrEmpty(lowIntegral)) {
				sb.append(" AND te.cost >= :lowIntegral  ");
			}
			if(!StringUtil.isNullOrEmpty(highIntegral)) {
				sb.append(" AND te.cost <= :highIntegral  ");
			}
			if(!StringUtils.isNullOrEmpty(adnm)) {
				sb.append(" and find_in_set(te.adnm , :adnm) ");
			}
			if(!StringUtils.isNullOrEmpty(productTeam)) {
				sb.append(" and find_in_set(te.product_team , :productTeam) ");
			}
			
			if(StringUtils.isNullOrEmpty(orderby)) {
				sb.append(" order by te.type desc");
			}else if(!StringUtils.isNullOrEmpty(orderby)){
				if(orderby.equals("1")) {
					sb.append(" order by te.type ");
				}else if(orderby.equals("2")) {
					sb.append(" order by if(replyNum is null,1,0),replyNum+0 ");
				}else if(orderby.equals("3")) {
					sb.append(" order by if(cost is null,1,0),cost+0 ");
				}
				ArrayList<String> pcSort = new ArrayList<String>(3) {{add("1");add("2");add("3");}};
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
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(status)) {
				query.setParameter("status", status);
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type", type);
			}
			if(!StringUtil.isNullOrEmpty(lowIntegral)) {
				query.setParameter("lowIntegral",lowIntegral );
			}
			if(!StringUtil.isNullOrEmpty(highIntegral)) {
				query.setParameter("highIntegral",highIntegral );
			}
			if(!StringUtils.isNullOrEmpty(adnm)) {
				query.setParameter("adnm", adnm);
			}
			if(!StringUtils.isNullOrEmpty(productTeam)) {
				query.setParameter("productTeam", productTeam);
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
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public Integer findByAllCount(String name,String status,String type,String lowIntegral,String highIntegral,String adnm,String productTeam){
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) from tb_user tu  inner join tb_res_user_expert rue on rue.user_tab_id=tu.id  ");
			sb.append(" left join tb_expert te on te.id=rue.expert_id where tu.del_flag=0 and te.del_flag=0  ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tu.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(status)) {
				sb.append(" and find_in_set(te.status , :status) ");
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and find_in_set(te.type , :type) ");
			}
			if(!StringUtil.isNullOrEmpty(lowIntegral)) {
				sb.append(" AND te.cost >= :lowIntegral  ");
			}
			if(!StringUtil.isNullOrEmpty(highIntegral)) {
				sb.append(" AND te.cost <= :highIntegral  ");
			}
			if(!StringUtils.isNullOrEmpty(adnm)) {
				sb.append(" and find_in_set(te.adnm , :adnm) ");
			}
			if(!StringUtils.isNullOrEmpty(productTeam)) {
				sb.append(" and find_in_set(te.product_team , :productTeam) ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(status)) {
				query.setParameter("status", status);
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type", type);
			}
			if(!StringUtil.isNullOrEmpty(lowIntegral)) {
				query.setParameter("lowIntegral",lowIntegral );
			}
			if(!StringUtil.isNullOrEmpty(highIntegral)) {
				query.setParameter("highIntegral",highIntegral );
			}
			if(!StringUtils.isNullOrEmpty(adnm)) {
				query.setParameter("adnm", adnm);
			}
			if(!StringUtils.isNullOrEmpty(productTeam)) {
				query.setParameter("productTeam", productTeam);
			}
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public Integer findByExpertId(String userId){
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select te.id  ");
			sb.append("  from tb_user tu  inner join tb_res_user_expert rue on rue.user_tab_id=tu.id left join tb_expert te on te.id=rue.expert_id "
					+ " left join tb_type tt on tt.id = te.type "
					+ " where tu.del_flag=0 AND te.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(userId)) {
				sb.append(" and tu.user_id = :userId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(userId)) {
				query.setParameter("userId", userId);
			}
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public String findCost(String type){
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select cost  ");
			sb.append("  from tb_cost "
					+ " where type=:type ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("type", type);
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return (list.get(0).toString());
			}
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	
}
