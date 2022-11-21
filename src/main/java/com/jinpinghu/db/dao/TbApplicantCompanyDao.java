package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbApplicantCompany;
import com.jinpinghu.db.bean.TbApplicantCompanyProduct;

public class TbApplicantCompanyDao extends BaseZDao{

	public TbApplicantCompanyDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbApplicantCompany findById(Integer id) {
		try {
			String queryString = " from TbApplicantCompany where delFlag = 0 and id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			List<TbApplicantCompany> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Map<String, Object> getApplicantCompanyInfo(Integer id) {
		try {
			String queryString = " select tac.id,enterprise_id enterpriseId,IFNULL(name,'') name,IFNULL(address,'') address, ";
			queryString += " IFNULL(zip_code,'') zipCode,IFNULL(legal_person,'') legalPerson,IFNULL(legal_contact,'') legalContact,IFNULL(legal_mobile,'') legalMobile, ";
			queryString += " IFNULL(agent,'') agent,IFNULL(agent_contact,'') agentContact,IFNULL(agent_mobile,'') agentMobile,IFNULL(fax,'') fax,IFNULL(email,'') email, ";
			queryString += " IFNULL(registered_trademark,'') registeredTrademark,IFNULL(date_format(trademark_service_start_time,'%Y-%m-%d'),'') trademarkServiceStartTime, ";
			queryString += " IFNULL(date_format(trademark_service_end_time,'%Y-%m-%d'),'') trademarkServiceEndTime, ";
			queryString += " IFNULL(type,'') type,IFNULL(production_situation,'') productionSituation,IFNULL(product_quality_attestation,'') ";
			queryString += " productQualityAttestation,IFNULL(machining_info,'') machiningInfo,IFNULL(product_sales,'') productSales, ";
			queryString += " IFNULL(package_source_manager,'') packageSourceManager,IFNULL(other_nt,'') otherNt,tac.status,code,  ";
			queryString += " ifnull(date_format(max(taccr.input_time),'%Y-%m-%d'),'') successTime,ifnull(tac.x,'') x,ifnull(tac.y,'') y, ";
			queryString += " ifnull(profile,'') profile,ifnull(uniform_social_credit_code,'') uniformSocialCreditCode,ifnull(date(operation_period_start_time),'') operationPeriodStartTime, ";
			queryString += " ifnull(date(operation_period_end_time),'') operationPeriodEndTime,ifnull(spyb_name,'') spybName,ifnull(spyb_product,'') spybProduct, ";
			queryString += " ifnull(date(spyb_start_time),'') spybStartTime,ifnull(date(spyb_end_time),'') spybEndTime ";
			queryString += " from tb_applicant_company tac left join tb_applicant_company_check_record taccr on tac.id = taccr.applicant_company_id and taccr.status = 1 where del_flag = 0 and tac.id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id", id);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Map<String, Object> getApplicantCompanyInfoByEnterpriseId(Integer enterpriseId) {
		try {
			String queryString = " select tac.id,enterprise_id enterpriseId,IFNULL(name,'') name,IFNULL(address,'') address, ";
			queryString += " IFNULL(zip_code,'') zipCode,IFNULL(legal_person,'') legalPerson,IFNULL(legal_contact,'') legalContact,IFNULL(legal_mobile,'') legalMobile, ";
			queryString += " IFNULL(agent,'') agent,IFNULL(agent_contact,'') agentContact,IFNULL(agent_mobile,'') agentMobile,IFNULL(fax,'') fax,IFNULL(email,'') email, ";
			queryString += " IFNULL(registered_trademark,'') registeredTrademark,IFNULL(date_format(trademark_service_start_time,'%Y-%m-%d'),'') trademarkServiceStartTime, ";
			queryString += " IFNULL(date_format(trademark_service_end_time,'%Y-%m-%d'),'') trademarkServiceEndTime, ";
			queryString += " IFNULL(type,'') type,IFNULL(production_situation,'') productionSituation,IFNULL(product_quality_attestation,'') ";
			queryString += " productQualityAttestation,IFNULL(machining_info,'') machiningInfo,IFNULL(product_sales,'') productSales, ";
			queryString += " IFNULL(package_source_manager,'') packageSourceManager,IFNULL(other_nt,'') otherNt,tac.status,code,  ";
			queryString += " ifnull(date_format(max(taccr.input_time),'%Y-%m-%d'),'') successTime,ifnull(tac.x,'') x,ifnull(tac.y,'') y, ";
			queryString += " ifnull(profile,'') profile,ifnull(uniform_social_credit_code,'') uniformSocialCreditCode,ifnull(date(operation_period_start_time),'') operationPeriodStartTime, ";
			queryString += " ifnull(date(operation_period_end_time),'') operationPeriodEndTime,ifnull(spyb_name,'') spybName,ifnull(spyb_product,'') spybProduct, ";
			queryString += " ifnull(date(spyb_start_time),'') spybStartTime,ifnull(date(spyb_end_time),'') spybEndTime ";
			queryString += " from tb_applicant_company tac left join tb_applicant_company_check_record taccr on tac.id = taccr.applicant_company_id and taccr.status = 1 where del_flag = 0 and tac.enterprise_id = :enterpriseId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("enterpriseId", enterpriseId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public void delApplicantCompanyProduct(Integer applicantCompanyId) {
		try {
			String queryString = " update tb_applicant_company_product set del_flag = 1 where applicant_company_id = :applicantCompanyId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("applicantCompanyId", applicantCompanyId);
			query.executeUpdate();
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public void delApplicantCompanyCredentials(Integer applicantCompanyId) {
		try {
			String queryString = " update tb_applicant_company_credentials set del_flag = 1 where applicant_company_id = :applicantCompanyId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("applicantCompanyId", applicantCompanyId);
			query.executeUpdate();
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public void delApplicantCompanyTrademark(Integer applicantCompanyId) {
		try {
			String queryString = " update tb_applicant_company_trademark set del_flag = 1 where applicant_company_id = :applicantCompanyId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("applicantCompanyId", applicantCompanyId);
			query.executeUpdate();
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getApplicantCompanyList(String name,Integer enterpriseId,Integer status,Integer nowPage,Integer pageCount){
		try {
			String queryString = " SELECT tac.id,ifnull(tac.name,'') name,ifnull(tac.type,'') type, "
					+ " ifnull(group_concat(distinct tact.name),'') registeredTrademark,ifnull(tac.address,'') address, "
					+ " tac.status,tac.code,ifnull(group_concat(distinct tacp.product_name),'') productName,ifnull(tac.x,'') x,ifnull(tac.y,'') y "
					+ " FROM tb_applicant_company tac left join tb_applicant_company_product tacp on tacp.applicant_company_id = tac.id and tacp.del_flag = 0 "
					+ " left join tb_applicant_company_trademark tact on tact.applicant_company_id = tac.id and tact.del_flag = 0 where tac.del_flag = 0 ";
			if(!StringUtils.isEmpty(name)) {
				queryString += " and tac.name like :name ";
			}
			if(enterpriseId != null) {
				queryString += " and tac.enterprise_id = :enterpriseId ";
			}
			if(status != null) {
				queryString += " and tac.status = :status ";
			}
			queryString += " group by tac.id ";
			queryString += " order by tac.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(enterpriseId != null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(status != null) {
				query.setParameter("status", status);
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
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Integer getApplicantCompanyListCount(String name,Integer enterpriseId,Integer status){
		try {
			String queryString = " SELECT count(distinct id) FROM tb_applicant_company tac where del_flag = 0 ";
			if(!StringUtils.isEmpty(name))
				queryString += " and name like :name ";
			if(enterpriseId != null)
				queryString += " and enterprise_id = :enterpriseId ";
			if(status != null)
				queryString += " and status = :status ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if(status != null)
				query.setParameter("status", status);
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> findApplicantCompanyProductByApplicantCompanyId(Integer applicantCompanyId) {
		try {
			String queryString = " select id,applicant_company_id applicantCompanyId,product_name productName,area,yield,producer,brand_id brandId  ";
			queryString += " from tb_applicant_company_product where del_flag = 0 and applicant_company_id = :applicantCompanyId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("applicantCompanyId", applicantCompanyId);
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
	
	public List<Map<String, Object>> findApplicantCompanyCredentialsByApplicantCompanyId(Integer applicantCompanyId) {
		try {
			String queryString = " select id,applicant_company_id applicantCompanyId,license_name licenseName,license_no licenseNo, ";
			queryString += " licensing_organization licensingOrganization,licensing_time licensingTime ";
			queryString += " from tb_applicant_company_credentials where del_flag = 0 and applicant_company_id = :applicantCompanyId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("applicantCompanyId", applicantCompanyId);
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
	
	public Integer findCountByCode(String code) {
		try {
			String queryString = " SELECT count(distinct id) FROM tb_applicant_company tac where code like :code ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("code", code+"%");
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}

	public List<Map<String, Object>> findApplicantCompanyTrademarkByApplicantCompanyId(Integer applicantCompanyId) {
		try {
			String queryString = " select id,applicant_company_id applicantCompanyId,name name,type,date_format(start_time,'%Y-%m-%d') startTime ,date_format(end_time,'%Y-%m-%d') endTime  ";
			queryString += " from tb_applicant_company_trademark where del_flag = 0 and applicant_company_id = :applicantCompanyId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("applicantCompanyId", applicantCompanyId);
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
	
	public Map<String, Object> getApplicantCompanyInfoById(Integer id) {
		try {
			String queryString = " select tac.id,enterprise_id enterpriseId,IFNULL(name,'') name,IFNULL(address,'') address, ";
			queryString += " IFNULL(zip_code,'') zipCode,IFNULL(legal_person,'') legalPerson,IFNULL(legal_contact,'') legalContact, ";
			queryString += " IFNULL(agent,'') agent,IFNULL(agent_contact,'') agentContact,IFNULL(fax,'') fax,IFNULL(email,'') email, ";
			queryString += " IFNULL(registered_trademark,'') registeredTrademark,IFNULL(date_format(trademark_service_start_time,'%Y-%m-%d'),'') trademarkServiceStartTime, ";
			queryString += " IFNULL(date_format(trademark_service_end_time,'%Y-%m-%d'),'') trademarkServiceEndTime, ";
			queryString += " IFNULL(type,'') type,IFNULL(production_situation,'') productionSituation,IFNULL(product_quality_attestation,'') ";
			queryString += " productQualityAttestation,IFNULL(machining_info,'') machiningInfo,IFNULL(product_sales,'') productSales, ";
			queryString += " IFNULL(package_source_manager,'') packageSourceManager,IFNULL(other_nt,'') otherNt,tac.status,code,  ";
			queryString += " ifnull(tac.x,'') x,ifnull(tac.y,'') y,IFNULL(agent_mobile,'') agentMobile,IFNULL(legal_mobile,'') legalMobile ";
			queryString += " ,IFNULL(date_format(spyb_start_time,'%Y-%m-%d'),'') spybStartTime,IFNULL(date_format(spyb_end_time,'%Y-%m-%d'),'') spybEndTime,profile  ";
			queryString += " ,IFNULL(date_format(operation_period_start_time,'%Y-%m-%d'),'') operationPeriodStartTime,IFNULL(date_format(operation_period_end_time,'%Y-%m-%d'),'') operationPeriodEndTime  ";
			queryString += " ,IFNULL(uniform_social_credit_code,'') uniformSocialCreditCode,IFNULL(spyb_product,'') spybProduct,IFNULL(spyb_name,'') spybName  ";
			
			queryString += " from tb_applicant_company tac  where  tac.id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id", id);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
}
