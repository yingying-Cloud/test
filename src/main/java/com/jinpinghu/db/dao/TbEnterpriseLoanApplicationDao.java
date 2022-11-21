package com.jinpinghu.db.dao;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbEnterpriseLoanApplication;
import com.mysql.cj.util.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class TbEnterpriseLoanApplicationDao extends BaseZDao {
    public TbEnterpriseLoanApplicationDao(EntityManager _em) {
        super(_em);
    }

    public TbEnterpriseLoanApplication findById(Integer id) {
        try {
            TbEnterpriseLoanApplication instance = getEntityManager().find(TbEnterpriseLoanApplication.class, id);
            if (instance != null) {
                return instance;
            } else {
                return null;
            }
        } catch (RuntimeException re) {
            throw re;
        }
    }
    public List<Map<String, Object>> findByAll(String name,List<String> status, Integer nowPage, Integer pageCount,String startTime,String endTime,Integer enterpriseId ) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" select la.id,");
            sb.append(" te.name,");
            sb.append(" la.loan_amount loanAmount,");
            sb.append(" date_format(la.start_time,'%Y-%m-%d') startTime,");
            sb.append(" date_format(la.end_time,'%Y-%m-%d') endTime,");
            sb.append(" la.use_instruction,");
            sb.append(" la.status");
            sb.append(" ,la.enterprise_id enterpriseId,handle_bank lender ");
            sb.append(" from tb_enterprise_loan_application la inner join tb_enterprise te on te.id=la.enterprise_id   where la.del_Flag=0  ");
            if(!StringUtils.isNullOrEmpty(name)) {
                sb.append(" and te.name like :enterpriseName ");
            }
            if(enterpriseId!=null) {
                sb.append(" and te.id=:enterpriseId ");
            }
            if(status!=null) {
                sb.append(" and la.status in :status ");
            }
            if(!StringUtils.isNullOrEmpty(endTime)) {
                sb.append(" and date_format(la.start_time,'%Y-%m-%d') <= :endTime ");
            }
            if(!StringUtils.isNullOrEmpty(startTime)) {
                sb.append(" and date_format(la.start_time,'%Y-%m-%d') >= :startTime ");
            }
            sb.append(" order by te.enterprise_type asc,te.input_time desc ");
            Query query = getEntityManager().createNativeQuery(sb.toString());
            if(!StringUtils.isNullOrEmpty(name)) {
                query.setParameter("enterpriseName", "%"+name+"%");
            }
            if(status!=null) {
                query.setParameter("status", status);
            }
            if(enterpriseId!=null) {
            	query.setParameter("enterpriseId", enterpriseId);
            }
            if(!StringUtils.isNullOrEmpty(endTime)) {
                query.setParameter("endTime", endTime);
            }
            if(!StringUtils.isNullOrEmpty(startTime)) {
                query.setParameter("startTime", startTime);
            }
            if(nowPage!=null&pageCount!=null){
                query.setFirstResult((nowPage-1)*pageCount);
                query.setMaxResults(pageCount);
            }
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List <Map<String,Object>> list = query.getResultList();
            return list;
        } catch (RuntimeException re) {
            throw re;
        }
    }
    public Integer findByAllCount(String name,List<String> status,String startTime,String endTime,Integer enterpriseId) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" select count(la.id) ");
            sb.append(" from tb_enterprise_loan_application la inner join tb_enterprise te on te.id=la.enterprise_id   where la.del_Flag=0  ");
            if(!StringUtils.isNullOrEmpty(name)) {
                sb.append(" and te.name like :enterpriseName ");
            }
            if(enterpriseId!=null) {
                sb.append(" and te.id=:enterpriseId ");
            }
            if(status!=null) {
                sb.append(" and la.status in :status ");
            }
            if(!StringUtils.isNullOrEmpty(endTime)) {
                sb.append(" and date_format(la.start_time,'%Y-%m-%d') <= :endTime ");
            }
            if(!StringUtils.isNullOrEmpty(startTime)) {
                sb.append(" and date_format(la.start_time,'%Y-%m-%d') >= :startTime ");
            }
            sb.append(" order by te.enterprise_type asc,te.input_time desc ");
            Query query = getEntityManager().createNativeQuery(sb.toString());
            if(!StringUtils.isNullOrEmpty(name)) {
                query.setParameter("enterpriseName", "%"+name+"%");
            }
            if(status!=null) {
                query.setParameter("status", status);
            }
            if(enterpriseId!=null) {
            	query.setParameter("enterpriseId", enterpriseId);
            }
            if(!StringUtils.isNullOrEmpty(endTime)) {
                query.setParameter("endTime", endTime);
            }
            if(!StringUtils.isNullOrEmpty(startTime)) {
                query.setParameter("startTime", startTime);
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
}
