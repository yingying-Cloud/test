package com.jinpinghu.db.dao;

import com.jinpinghu.db.bean.TbResBrandCarNum;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class TbResBrandCarNumDao extends BaseZDao {
    public TbResBrandCarNumDao(EntityManager _em) {
        super(_em);
    }
    public TbResBrandCarNum findByCarIdAndEnterpriseId(Integer carId,Integer enterpriseId){
        try{
            StringBuffer sb = new StringBuffer();
            sb.append(" from TbResBrandCarNum where delFlag=0 ");
            if(carId!=null){
                sb.append(" and brandCarId=:carId ");
            }
            if(enterpriseId!=null){
                sb.append(" and enterpriseId=:enterpriseId ");
            }
            Query query = getEntityManager().createQuery(sb.toString());
            if(carId!=null){
                query.setParameter("carId",carId);
            }
            if(enterpriseId!=null){
                query.setParameter("enterpriseId",enterpriseId);
            }
            List<TbResBrandCarNum> list = query.getResultList();
            if(list!=null&&list.size()>0){
                return list.get(0);
            }else{
                return null;
            }
        }catch(RuntimeException re){
            throw re;
        }
    }

    public List<Map<String, Object>> findByCarId(Integer carId){
        try{
            StringBuffer sb = new StringBuffer();
            sb.append(" select trbc.id,trbc.enterprise_id enterpriseId,trbc.num,tt.name enterpriseName " +
                    " from Tb_Res_Brand_Car_Num trbc " +
                    " left join tb_enterprise tt on tt.id=trbc.enterprise_id " +
                    " where trbc.del_Flag=0 ");
            if(carId!=null){
                sb.append(" and brand_Car_Id=:carId ");
            }
            Query query = getEntityManager().createNativeQuery(sb.toString());
            if(carId!=null){
                query.setParameter("carId",carId);
            }
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List <Map<String,Object>> list = query.getResultList();
            if(list!=null&&list.size()>0){
                return list;
            }else{
                return null;
            }
        }catch(RuntimeException re){
            throw re;
        }
    }
    
    public List<Map<String, Object>> findEnterpriseByCarId(Integer carId){
        try{
            StringBuffer sb = new StringBuffer();
            sb.append(" select trbc.id,trbc.enterprise_id enterpriseId,trbc.num,tt.name enterpriseName " +
                    " from Tb_Res_Brand_Car_Num trbc " +
                    " left join tb_enterprise tt on tt.id=trbc.enterprise_id " +
                    " where trbc.del_Flag=0 ");
            if(carId!=null){
                sb.append(" and brand_Car_Id=:carId ");
            }
            Query query = getEntityManager().createNativeQuery(sb.toString());
            if(carId!=null){
                query.setParameter("carId",carId);
            }
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List <Map<String,Object>> list = query.getResultList();
            if(list!=null&&list.size()>0){
                return list;
            }else{
                return null;
            }
        }catch(RuntimeException re){
            throw re;
        }
    }
    
}
