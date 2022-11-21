package com.jinpinghu.db.dao;

import com.jinpinghu.db.bean.TbZone;
import fw.jbiz.db.ZEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class TbZoneDao extends BaseZDao{
    public TbZoneDao(EntityManager _em) {
        super(_em);
    }

    public TbZone findById(Integer id) {
        try {
            String queryString = "from TbZone where id = :id ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter("id",id);
            List<TbZone> list = query.getResultList();
            if (null != list && list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public Integer save(TbZone tbZone) {
        try{
            StringBuffer queryString=new StringBuffer();
            queryString.append(" INSERT INTO tb_zone ");
            queryString.append(" (enterprise_id,code,name,area,describe_,del_flag)VALUES ");
            queryString.append("(:enterpriseId,:code ,:name ,:area, :describe ,0) ");
            Query query=getEntityManager().createNativeQuery(queryString.toString());
            if (tbZone!=null){
            	query.setParameter("enterpriseId",tbZone.getEnterpriseId());
                query.setParameter("code",tbZone.getCode());
                query.setParameter("name",tbZone.getName());
                query.setParameter("area",tbZone.getArea());
                query.setParameter("describe",tbZone.getDescribe());
            }
            Integer res=query.executeUpdate();
            if (res!=null){
                return res;
            }
            return null;
        } catch (RuntimeException re) {
            throw re;
        }
    }


    public Integer delete(Integer id) {
        try{
            StringBuffer queryString=new StringBuffer();
            queryString.append(" UPDATE tb_zone SET del_flag=1 ");
            queryString.append(" WHERE del_flag=0 ");
            queryString.append(" AND id=:id ");
            Query query=getEntityManager().createNativeQuery(queryString.toString());
            if (id!=null){
                query.setParameter("id",id);
            }
            Integer res=query.executeUpdate();
            if (res!=null){
                return res;
            }
            return null;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public Integer update(TbZone tbZone) {
        try{
            StringBuffer queryString=new StringBuffer();
            queryString.append(" UPDATE tb_zone SET code=:code,name=:name,area=:area,describe_=:describe,enterprise_id=:enterpriseId ");
            queryString.append(" WHERE del_flag=0 ");
            queryString.append(" AND id=:id ");
            Query query=getEntityManager().createNativeQuery(queryString.toString());
            if (tbZone!=null){
                query.setParameter("code",tbZone.getCode());
                query.setParameter("name",tbZone.getName());
                query.setParameter("area",tbZone.getArea());
                query.setParameter("describe",tbZone.getDescribe());
                query.setParameter("id",tbZone.getId());
                query.setParameter("enterpriseId", tbZone.getEnterpriseId());
            }
            Integer res=query.executeUpdate();
            if (res!=null){
                return res;
            }
            return null;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List<Object[]> findTbZoneList(String name,Integer nowPage,Integer pageCount,Integer enterpriseId){
        try{
            StringBuffer queryString=new StringBuffer();
            queryString.append(" SELECT tz.id enterpriseId,tz.code,tz.name,tz.area,tz.describe_,tz.enterprise_id,te.name enterpriseName ");
            queryString.append(" FROM tb_zone tz left join tb_enterprise te on tz.enterprise_id = te.id ");
            queryString.append(" WHERE tz.del_flag=0 ");
            if(enterpriseId != null)
            	queryString.append(" and tz.enterprise_id = :enterpriseId ");
            if (name!=null){
                queryString.append(" AND tz.name LIKE :name ");
            }
            Query query=getEntityManager().createNativeQuery(queryString.toString());
            if(enterpriseId != null)
            	query.setParameter("enterpriseId", enterpriseId);
            if (name!=null){
                query.setParameter("name","%"+name+"%");
            }
            if (nowPage!=null&&pageCount!=null){
                query.setFirstResult((nowPage-1)*pageCount);
                query.setMaxResults(pageCount);
            }
            List<Object[]> list=query.getResultList();
            if(list!=null||list.size()>0){
                return list;
            }else{
                return null;
            }
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public Integer findTbZoneListCount(String name){
        try{
            StringBuffer queryString=new StringBuffer();
            queryString.append(" SELECT COUNT(*) ");
            queryString.append(" FROM tb_zone ");
            queryString.append(" WHERE del_flag=0 ");
            if (name!=null){
                queryString.append(" AND name LIKE :name ");
            }
            Query query=getEntityManager().createNativeQuery(queryString.toString());
            if (name!=null){
                query.setParameter("name","%"+name+"%");
            }
            Object res=query.getSingleResult();
            if(res!=null){
                return Integer.valueOf(res.toString());
            }else{
                return null;
            }
        } catch (RuntimeException re) {
            throw re;
        }
    }
    
    public List<TbZone> findByEnterpriseId(Integer enterpriseId) {
		try {
			String queryString = "from TbZone where  delFlag = 0 and enterpriseId = :enterpriseId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("enterpriseId",enterpriseId);
			List<TbZone> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
