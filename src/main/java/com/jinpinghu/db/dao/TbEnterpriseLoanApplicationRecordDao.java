package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbEnterpriseLoanApplicationRecordDao extends BaseZDao{

	public TbEnterpriseLoanApplicationRecordDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<Map<String, Object>> getLoanApplicationRecordListById(Integer loanApplicationId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select id,status,remark,date_format(input_time,'%Y-%m-%d %H:%i:%s') inputTime ,name  "
					+ "from Tb_enterprise_loan_application_record tref  where  tref.del_Flag = 0 and tref.loan_application_id =:loanApplicationId     ");
			sb.append(" order by input_time desc  ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("loanApplicationId", loanApplicationId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
