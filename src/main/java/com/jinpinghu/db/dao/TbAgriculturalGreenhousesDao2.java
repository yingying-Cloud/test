package com.jinpinghu.db.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbAgriculturalGreenhouses;


public class TbAgriculturalGreenhousesDao2 extends BaseZDao{

	public TbAgriculturalGreenhousesDao2(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public Object save(TbAgriculturalGreenhouses bean,String b) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO tb_agricultural_greenhouses ( ");
		sb.append("enterprise_id, ");
//		sb.append("center, ");
		sb.append("greenhouses_name, ");
		sb.append("mu, ");
//		sb.append("area, ");
		sb.append("input_time, ");
		sb.append("del_flag,");
		sb.append("classify");
		sb.append(") VALUES (");
		sb.append("'"+bean.getEnterpriseId() + "', ");
//		sb.append("'"+bean.getCenter() + "', ");
		sb.append("'"+bean.getGreenhousesName() + "', ");
		sb.append("'"+bean.getMu() + "', ");
//		sb.append("ST_GeomFromText("+"'Polygon(("+b+"))'"+") ");
		sb.append("'"+DateTimeUtil.formatTime2(new Date())+"'");
		sb.append(",'"+0+"',");
		sb.append("'"+bean.getClassify()+"'");
		sb.append("); ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		int executeUpdate = query.executeUpdate();
		StringBuffer backId = new StringBuffer();
		backId.append("SELECT LAST_INSERT_ID();");		
		Query queryId = getEntityManager().createNativeQuery(backId.toString());
		List<Object> list = queryId.getResultList();
		if(list!=null&&list.size()>0)
			return list.get(0);
		return "";
	}
	
	public int update(Integer id,Integer enterpriseId,String center,String greenhousesName,String mu,String area,Integer classify ) {
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE tb_agricultural_greenhouses  SET ");
		sb.append(" enterprise_id = '"+enterpriseId+"', ");
//		sb.append(" center = '"+center+"', ");
		sb.append(" greenhouses_name = '"+greenhousesName+"', ");
		sb.append(" mu = '"+mu+"' ");
		sb.append(" , classify = '"+classify+"' ");
//		sb.append(" area = "+"ST_GeomFromText("+"'POLYGON(("+area+"))'"+") ");
		sb.append(" WHERE id = '"+id+"'; ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
//		query.setParameter("area", "Polygon(("+area+"))");
		int executeUpdate = query.executeUpdate();
		return executeUpdate;
	}
	public Object[] findObjectById(Integer id) {
		try {
			String queryString = "select id,asText(area),enterprise_id,greenhouses_name,mu,classify from Tb_Agricultural_Greenhouses where id = :id and del_Flag = 0 ";
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
	
	public TbAgriculturalGreenhouses findById(Integer id) {
		try {
			TbAgriculturalGreenhouses instance = getEntityManager().find(TbAgriculturalGreenhouses.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getList(String enterpriseId,String greenhousesName,String type,Integer nowPage,Integer pageCount) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select tag.id,tag.greenhouses_name,tag.mu,st_asText(area),   ");
			sb.append(" ( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_file_greenhouses rfg ");
			sb.append(" ON f.id = rfg.file_id INNER JOIN tb_agricultural_greenhouses g ");
			sb.append(" ON g.id = rfg.greenhouses_id WHERE g.id = tag.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goods_pic,tag.list_order,tag.classify  ");
			sb.append(" from tb_agricultural_greenhouses tag  ");
			
//			queryString += " left join tb_res_greenhouses_user trgu on tag.id=trgu.greenhouses_id ";
//,(select concat(tu.nickname) from tb_res_greenhouses_user rgu left join tb_user tu on tu.id=rgu.user_tab_id where tag.id=rgu.greenhouses_id )
			sb.append("   where tag.del_flag=0  ");
			if(!StringUtils.isEmpty(greenhousesName))
				sb.append("  and tag.greenhouses_name like :greenhousesName");
			if(!StringUtils.isEmpty(enterpriseId))
				sb.append("  and tag.enterprise_Id = :enterpriseId");
			if(!StringUtils.isEmpty(type))
				sb.append( " and classify = :type");
			sb.append(" order by tag.list_order desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtils.isEmpty(greenhousesName))
				query.setParameter("greenhousesName","%"+greenhousesName+"%");
			if(!StringUtils.isEmpty(type))
				query.setParameter("type",type);
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
	public Integer getListCount(String enterpriseId,String greenhousesName,String type) {
		try {
			String queryString = " select count(id) from tb_agricultural_greenhouses tag  ";
//			queryString += " left join tb_res_greenhouses_user trgu on tag.id=trgu.greenhouses_id ";
//,(select concat(tu.nickname) from tb_res_greenhouses_user rgu left join tb_user tu on tu.id=rgu.user_tab_id where tag.id=rgu.greenhouses_id )
			queryString += " where del_flag=0  ";
			if(!StringUtils.isEmpty(greenhousesName))
				queryString += " and greenhouses_name like :greenhousesName";
			if(!StringUtils.isEmpty(enterpriseId))
				queryString += " and enterprise_id = :enterpriseId";
			if(!StringUtils.isEmpty(type))
				queryString += " and classify = :type";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(enterpriseId))
				query.setParameter("enterpriseId",Integer.valueOf(enterpriseId));
			if(!StringUtils.isEmpty(greenhousesName))
				query.setParameter("greenhousesName","%"+greenhousesName+"%");
			if(!StringUtils.isEmpty(type))
				query.setParameter("type",type);
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getListByBaseId(Integer enterpriseId,String greenhousesName,String nickname,Integer nowPage,Integer pageCount) {
		try {
			String queryString = "   ";
			queryString += " select  ";
			queryString += " id,gn,sname ";
			queryString += " from ( ";
			queryString += " SELECT DISTINCT ";
			queryString += "	tag.id id, ";
			queryString += "	greenhouses_name gn, ";
			queryString += "	tag.list_order listOrder  ";
			queryString += " FROM ";
			queryString += " tb_agricultural_greenhouses tag  ";
			queryString += " where tag.del_flag=0 ";
			if(enterpriseId!=null)
				queryString += " and tag.enterprise_id = :enterpriseId";
			if(!StringUtils.isEmpty(greenhousesName))
				queryString += " and tag.greenhouses_name like :greenhousesName";
			queryString += " ) t ";
			queryString += " WHERE 1=1 ";
			if(!StringUtils.isEmpty(nickname))
				queryString += " and sname like :nickname";
			queryString += " order by listOrder desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null)
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtils.isEmpty(greenhousesName))
				query.setParameter("greenhousesName","%"+greenhousesName+"%");
			if(!StringUtils.isEmpty(nickname))
				query.setParameter("nickname","%"+nickname+"%");
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
	public Integer getListByBaseIdCount(Integer baseId,String greenhousesName,String nickname) {
		try {
			String queryString = "  ";
			queryString += " select  ";
			queryString += " count(*) ";
			queryString += " from ( ";
			queryString += " SELECT DISTINCT ";
			queryString += "	tag.id id, ";
			queryString += "	greenhouses_name gn, ";
			queryString += "	( ";
			queryString += "	SELECT ";
			queryString += "		group_concat(  concat_ws(' ',tu.nickname  ) )  ";
			queryString += "	FROM ";
			queryString += "		tb_res_greenhouses_work_user rgu ";
			queryString += "	LEFT JOIN tb_user tu ON tu.id = rgu.user_tab_id  ";
			queryString += " WHERE ";
			queryString += "	tag.id = rgu.greenhouses_id and rgu.del_flag=0 and tu.del_flag=0 ";
			queryString += " ) sname  ";
			queryString += " FROM ";
			queryString += " tb_agricultural_greenhouses tag  ";
			queryString += " where tag.del_flag=0 ";
			if(baseId!=null)
				queryString += " and tag.base_id = :baseId";
			if(!StringUtils.isEmpty(greenhousesName))
				queryString += " and tag.greenhouses_name like :greenhousesName";
			queryString += " ) t ";
			queryString += " WHERE 1=1 ";
			if(!StringUtils.isEmpty(nickname))
				queryString += " and sname like :nickname";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(baseId!=null)
				query.setParameter("baseId",baseId);
			if(!StringUtils.isEmpty(greenhousesName))
				query.setParameter("greenhousesName","%"+greenhousesName+"%");
			if(!StringUtils.isEmpty(nickname))
				query.setParameter("nickname","%"+nickname+"%");
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getGreenhousesFileList(String baseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  SELECT f.file_url,f.file_name,g.greenhouses_name ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_file_greenhouses rfg ");
			sb.append(" ON f.id = rfg.file_id INNER JOIN tb_agricultural_greenhouses g ");
			sb.append(" ON g.id = rfg.greenhouses_id WHERE 1=1 ");
			sb.append(" AND f.file_type = 1 AND g.del_flag=0 AND rfg.del_flag=0  ");
			if(!StringUtils.isEmpty(baseId)) {
				sb.append(" AND g.base_id=:baseId");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(baseId)) {
				query.setParameter("baseId",Integer.valueOf(baseId));
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
	public Integer getLandListCount(String baseId,String greenhousesName,Integer greenhousesId) {
		try {
			String queryString = " select count(*) from tb_agricultural_greenhouses tag left join tb_agricultural_land tgl on tgl.greenhouses_id=tag.id  ";
			queryString += " where tag.del_flag=0 and tgl.del_flag=0  ";
			if(!StringUtils.isEmpty(greenhousesName))
				queryString += " and tag.greenhouses_name like :greenhousesName";
			if(greenhousesId != null)
				queryString +="  and tag.id = :greenhousesId ";
			if(!StringUtils.isEmpty(baseId))
				queryString += " and tag.base_id = :baseId";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(baseId))
				query.setParameter("baseId",Integer.valueOf(baseId));
			if(!StringUtils.isEmpty(greenhousesName))
				query.setParameter("greenhousesName","%"+greenhousesName+"%");
			if(greenhousesId != null)
				query.setParameter("greenhousesId", greenhousesId);
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Object[]> getLandList(String baseId,String greenhousesName,Integer nowPage,Integer pageCount,Integer greenhousesId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select tag.id,tag.greenhouses_name,tag.mu,asText(tgl.area),tgl.mj,tgl.name,tgl.warning_water_level,tag.classify   ");
			sb.append(" from tb_agricultural_greenhouses tag left join tb_agricultural_land tgl on tgl.greenhouses_id=tag.id  ");
			
			sb.append("   where tag.del_flag=0 and tgl.del_flag = 0 ");
			if(!StringUtils.isEmpty(greenhousesName))
				sb.append("  and tag.greenhouses_name like :greenhousesName");
			if(greenhousesId != null)
				sb.append("  and tag.id = :greenhousesId ");
			if(!StringUtils.isEmpty(baseId))
				sb.append("  and tag.base_id = :baseId");
			sb.append(" order by tag.list_order desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(baseId))
				query.setParameter("baseId",baseId);
			if(greenhousesId != null)
				query.setParameter("greenhousesId", greenhousesId);
			if(!StringUtils.isEmpty(greenhousesName))
				query.setParameter("greenhousesName","%"+greenhousesName+"%");
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
	
	public List<Map<String,Object>> getGreenhousesLandList(Integer baseId) {
	try {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  ");
		sb.append(" id,  ");
		sb.append(" parentId,  ");
		sb.append(" name,   ");
		sb.append(" parentName   ");
		sb.append(" FROM  ");
		sb.append(" (  ");
		sb.append(" SELECT  ");
		sb.append(" 	tal.id,  ");
		sb.append(" 	greenhouses_id parentId,    ");
		sb.append(" name ,tag.greenhouses_name parentName  ");
		sb.append(" FROM  ");
		sb.append(" 	tb_agricultural_land tal left join tb_agricultural_greenhouses tag on tag.id=tal.greenhouses_id  ");
		sb.append(" WHERE  ");
		sb.append(" tal.del_flag = 0 and tag.classify!=1  ");
		if(baseId!=null) {
			sb.append("  and tag.base_id = :baseId");
		}
		sb.append(" UNION SELECT  ");
		sb.append(" 	id,  ");
		sb.append(" 	'' parentId,  ");
		sb.append(" greenhouses_name name , '' parentName  ");
		sb.append(" FROM  ");
		sb.append(" 	tb_agricultural_greenhouses tag  ");
		sb.append(" WHERE  ");
		sb.append(" del_flag = 0   ");
		if(baseId!=null) {
			sb.append("  and tag.base_id = :baseId");
		}
		sb.append(" ) s  ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(baseId!=null) {
			query.setParameter("baseId", baseId);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		if(list!=null&&list.size()>0) {
			return list;
		}
		return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public Map<String,Object> getDeviceClassifyCount(Integer baseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select ( SELECT  ");
			sb.append(" count(td.id) count ");
			sb.append(" FROM ");
			sb.append(" tb_agricultural_greenhouses td ");
			sb.append(" where del_flag=0 and classify=1 ");
			if(baseId!=null) {
				sb.append("  and base_id = :baseId");
			}
			sb.append(" )num1, ");
			sb.append(" (SELECT  ");
			sb.append(" count(td.id) count ");
			sb.append(" FROM ");
			sb.append(" tb_agricultural_greenhouses td ");
			sb.append(" where del_flag=0 and classify=2 ");
			if(baseId!=null) {
				sb.append("  and base_id = :baseId");
			}
			sb.append(" )num2 ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(baseId!=null) {
				query.setParameter("baseId", baseId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0) {
				return list.get(0);
			}
			return null;
		}catch(RuntimeException re) {
				throw re;
			}
	}
	
	public List<Map<String, Object>> getTagList(Integer baseId){
		try {
			String queryString = " SELECT tag.id,tag.greenhouses_name name,ifnull(tag.mu,'') mj,count(distinct tal.id) landCount,sum(ifnull(tdsr.water,'0')) water ";
			queryString += " FROM tb_agricultural_greenhouses tag left join tb_agricultural_land tal on tag.id = tal.greenhouses_id ";
			queryString += " left join tb_res_device_greenhouses trdg on  trdg.greenhouses_id = tag.id ";
			queryString += " left join tb_device td on td.id = trdg.device_id ";
			queryString += " left join tb_device_switch_record tdsr on tdsr.device_id = td.id  ";
			queryString += " where tag.del_flag = 0 ";
			if(baseId != null)
				queryString += " and tag.base_id = :baseId ";
			queryString += " group by tag.id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(baseId != null)
				query.setParameter("baseId", baseId);
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
	
	public Integer getUserCreateGreenhouseCount(Integer baseId) {
		String queryString = " select count(distinct tag.id) from tb_agricultural_greenhouses tag where tag.del_flag = 0 and tag.base_id = :baseId ";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("baseId", baseId);
		List<Object> list = query.getResultList();
		return Integer.valueOf(list.get(0).toString());
	}
	
	public List<Map<String, Object>> getGreenhouseGeomList(Integer enterpriseId){
		try {
			String queryString = " SELECT tag.id,tag.greenhouses_name greenhousesName,tg.feature_type featureType,st_asText(tg.geom) geom ";
			queryString += " FROM tb_agricultural_greenhouses tag left join tb_geom tg on tg.obj_id = tag.id and tg.obj_type = 'greenhouses' ";
			if(enterpriseId != null)
				queryString += " where tag.enterprise_id = :enterpriseId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
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
	
	public List<Map<String, Object>> getEnterpriseGeomList(Integer enterpriseId){
		try {
			String queryString = " SELECT te.id,te.name enterpriseName,tg.feature_type featureType,st_asText(tg.geom) geom ";
			queryString += " FROM tb_enterprise te left join tb_geom tg on tg.obj_id = te.id and tg.obj_type = 'enterprise' ";
			if(enterpriseId != null)
				queryString += " where te.id = :enterpriseId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
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
