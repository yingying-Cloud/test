package com.jinpinghu.db.dao;

import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.bean.TbResLoanApplicationFile;
import com.jinpinghu.db.bean.TbResToolFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class TbResLoanApplicationFileDao extends BaseZDao{

	public TbResLoanApplicationFileDao(EntityManager _em) {
		super(_em);
	}
	public List<TbResLoanApplicationFile> findByEnterpriseLoanApplicationId(Integer loanApplicationId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResLoanApplicationFile where loanApplicationId =:loanApplicationId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("loanApplicationId", loanApplicationId);
			List<TbResLoanApplicationFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

}
