package com.jinpinghu.db.dao;

import com.jinpinghu.db.bean.TbFile;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class TbFileDao extends BaseZDao {

	public TbFileDao(EntityManager _em) {
		super(_em);
	}
	public List<TbFile> findByToolRecordId(Integer toolRecordId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where   id in ( select fileId from TbResToolRecordFile where delFlag = 0 and toolRecordId =:toolRecordId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolRecordId", toolRecordId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findBySellBrandRecordId(Integer sellBrandRecordId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where   id in ( select fileId from TbResSellBrandRecordFile where delFlag = 0 and sellBrandRecordId =:sellBrandRecordId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("sellBrandRecordId", sellBrandRecordId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findByToolId(Integer toolId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResToolFile where delFlag = 0 and toolId =:toolId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolId", toolId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

	public List<TbFile> findByEnterpriseId(Integer enterpriseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResEnterpriseFile where delFlag = 0 and enterpriseId =:enterpriseId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findByEnterpriseIdType(Integer enterpriseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResEnterpriseFile where delFlag = 0 and enterpriseId =:enterpriseId   and ( type=1 or type=2 or type=99)   )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findByEnterpriseIdType(Integer enterpriseId,List<Integer> type){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResEnterpriseFile where delFlag = 0 and enterpriseId =:enterpriseId   and ( type in (:type ) )   )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			query.setParameter("type", type);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getListByEnterpriseId(Integer enterpriseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tref.type type,tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize,tref.id resEnterpriseFileId from Tb_Res_Enterprise_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.del_Flag = 0 and tref.enterprise_id =:enterpriseId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getListByEnterpriseIdType(Integer enterpriseId,Integer type){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tref.type type,tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize,tref.id resEnterpriseFileId from Tb_Res_Enterprise_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.del_Flag = 0 and tref.enterprise_id =:enterpriseId     ");
			if(type!=null) {
				sb.append(" and tref.type=:type ");
			}
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			if(type!=null) {
				query.setParameter("type", type);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getListByEnterpriseIdTypeCount(Integer enterpriseId,Integer type){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tref.type,count(*) count from Tb_Res_Enterprise_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.del_Flag = 0 and tref.enterprise_id =:enterpriseId     ");
			sb.append(" group by tref.type ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	
	public TbFile findById(Integer id) {
		try {
			String queryString = " from TbFile where id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			List<TbFile> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbFile> findByPostId(Integer postId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResPostFile where postId =:postId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("postId", postId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbFile> findByReplyId(Integer replyId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResRelpyFile where replyId =:replyId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("replyId", replyId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbFile> findByPlantProtectionOrderCompleteId(Integer completeId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResPlantProtectionOrderCompletionFile where plantProtectionOrderCompletionId =:completeId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("completeId", completeId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbFile> findByExpertId(Integer expertId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResExpertFile where expertId =:expertId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("expertId", expertId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbFile> findByTrademarkId(Integer trademarkId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResTrademarkFile where trademarkId =:trademarkId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("trademarkId", trademarkId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbFile> findByToolRecoveryId(Integer toolRecoveryId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResToolRecoveryFile where toolRecoveryId =:toolRecoveryId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolRecoveryId", toolRecoveryId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	public List<TbFile> findByToolRecoveryRecordId(Integer toolRecoveryRecordId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResToolRecoveryRecordFile where toolRecoveryRecordId =:toolRecoveryRecordId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolRecoveryRecordId", toolRecoveryRecordId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbFile> findByOrderInfoId(Integer orderInfoId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResOrderInfoFile where delFlag = 0 and orderInfoId =:orderInfoId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("orderInfoId", orderInfoId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

	public List<TbFile> findByEnterpriseLoanApplicationId(Integer loanApplicationId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResLoanApplicationFile where delFlag = 0 and loanApplicationId =:loanApplicationId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("loanApplicationId", loanApplicationId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

	
	public  List<Map<String, Object>> findMapByToolRecoveryRecordId(Integer toolRecoveryRecordId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,ifnull(tf.file_size,'') fileSize  from Tb_Res_Tool_Recovery_Record_File trtrf left join tb_file tf on tf.id=trtrf.file_id where   tool_Recovery_Record_Id =:toolRecoveryRecordId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("toolRecoveryRecordId", toolRecoveryRecordId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<Map<String, Object>> getListByExpertId(Integer expertId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tref.type type,tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_Res_expert_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.del_Flag = 0 and tref.expert_id =:expertId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("expertId", expertId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> getLoanApplicationListById(Integer loanApplicationId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,ifnull(tf.file_size,'') fileSize from Tb_Res_loan_application_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.del_Flag = 0 and tref.loan_application_id =:loanApplicationId     ");
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
	
	public List<Map<String, Object>> getMapByOrderInfoId(Integer orderInfoId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,ifnull(tf.file_size,'') fileSize from Tb_File tf where  id in ( select file_Id from Tb_Res_Order_Info_File where del_Flag = 0 and order_Info_Id =:orderInfoId  )   ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("orderInfoId", orderInfoId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	
	public TbFile findOneByEnterpriseId(Integer enterpriseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResEnterpriseFile where delFlag = 0 and enterpriseId =:enterpriseId and type=1  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public TbFile findOneByOrderInfoId(Integer orderInfoId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResOrderInfoFile where delFlag = 0 and orderInfoId =:orderInfoId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("orderInfoId", orderInfoId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

	public List<TbFile> findByToolCatalogId(Integer toolId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResToolCatalogFile where delFlag = 0 and toolCatalogId =:toolId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolId", toolId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findBySellBrandId(Integer sellBrandId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResSellBrandFile where delFlag = 0 and sellBrandId =:sellBrandId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("sellBrandId", sellBrandId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findByBrandId(Integer brandId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResBrandFile where delFlag = 0 and brandId =:brandId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("brandId", brandId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<TbFile> findByBrandSaleId(Integer brandSaleId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResBrandSaleFile where delFlag = 0 and brandSaleId =:brandSaleId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("brandSaleId", brandSaleId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getByBrandSaleId(Integer brandSaleId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_Res_Brand_Sale_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.del_Flag = 0 and tref.brand_Sale_Id =:brandSaleId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("brandSaleId", brandSaleId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findByResCropFarmingWaterId(Integer resCropFarmingWaterId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResCropFarmingWaterFile where delFlag = 0 and resCropFarmingWaterId =:resCropFarmingWaterId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("resCropFarmingWaterId", resCropFarmingWaterId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> findByResCropFarmingWaterIdMap(Integer resCropFarmingWaterId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select  tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_File tf where  id in ( select file_Id from Tb_Res_Crop_Farming_Water_File where del_Flag = 0 and res_Crop_Farming_Water_Id =:resCropFarmingWaterId  )   ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("resCropFarmingWaterId", resCropFarmingWaterId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findByEnterpriseCertificateId(Integer enterpriseCertificateId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResEnterpriseCertificateFile where delFlag = 0 and enterpriseCertificateId =:enterpriseCertificateId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseCertificateId", enterpriseCertificateId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> findByEnterpriseCertificateMap(Integer enterpriseCertificateId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select  tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_File tf where  id in ( select file_Id from Tb_Res_Enterprise_Certificate_File where del_Flag = 0 and enterprise_Certificate_Id =:enterpriseCertificateId  )   ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("enterpriseCertificateId", enterpriseCertificateId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> findByPlantWarehouseIdMap(Integer plantWarehouseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select  tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_File tf where  id in ( select file_Id from Tb_Res_file_plant_warehouse where del_Flag = 0 and plant_warehouse_id =:plantWarehouseId  )   ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("plantWarehouseId", plantWarehouseId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findByAdvertisementId(Integer advertisementId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResAdvertisementFile where advertisementId =:advertisementId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("advertisementId", advertisementId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> findByAdvertisementIMap(Integer advertisementId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select  tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_File tf where  id in ( select file_Id from Tb_Res_advertisement_file where  advertisement_id =:advertisementId  )   ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("advertisementId", advertisementId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<TbFile> findByGreenhousesId(Integer greenhousesId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResFileGreenhouses where delFlag = 0 and greenhousesId =:greenhousesId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("greenhousesId", greenhousesId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<TbFile> findByPlantServiceOrderCompleteId(Integer completeId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResPlantServiceOrderCompletionFile where plantServiceOrderCompletionId =:completeId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("completeId", completeId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbFile> findBySupplyReleaseId(Integer supplyReleaseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResSupplyReleaseFile where supplyReleaseId =:supplyReleaseId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("supplyReleaseId", supplyReleaseId);
			List<TbFile> list=query.getResultList();
			return list;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbFile> findByBuyReleaseId(Integer buyReleaseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResBuyReleaseFile where buyReleaseId =:buyReleaseId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("buyReleaseId", buyReleaseId);
			List<TbFile> list=query.getResultList();
			return list;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<Map<String, Object>> getListByBusinessId(Integer businessId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_Res_business_File tref left join Tb_File tf on tf.id=tref.file_id where tref.business_id =:businessId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("businessId", businessId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getPlantProtectionFile(Integer plantProtectionId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_Res_plant_protection_File tref left join Tb_File tf on tf.id=tref.file_id where   tref.plant_protection_id =:plantProtectionId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("plantProtectionId", plantProtectionId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getPlantServiceFile(Integer plantServiceId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_Res_plant_service_File tref left join Tb_File tf on tf.id=tref.file_id where   tref.plant_service_id =:plantServiceId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("plantServiceId", plantServiceId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getListByLogisticsId(Integer logisticsId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_Res_logistics_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.logistics_id =:logisticsId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("logisticsId", logisticsId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getListByPestsId(Integer pestsId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_Res_pests_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.pests_id =:pestsId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("pestsId", pestsId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<TbFile> findByPestsId(Integer pestsId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResPestsFile where pestsId=:pestsId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("pestsId", pestsId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbFile> findByDeviceId(Integer deviceId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResFileDevice where delFlag = 0 and deviceId =:deviceId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("deviceId", deviceId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> getListByAgriculturalClassroomId(Integer agriculturalId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_Res_agricultural_classroom_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.agricultural_classroom_Id =:agriculturalId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("agriculturalId", agriculturalId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<TbFile> findByAgriculturalClassroomId(Integer agriculturalClassroomId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResAgriculturalClassroomFile where  agriculturalClassroomId =:agriculturalClassroomId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("agriculturalClassroomId", agriculturalClassroomId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<TbFile> findByEvaluate(Integer evaluateId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbFile where  id in ( select fileId from TbResEvaluateFile where delFlag = 0 and evaluateId =:evaluateId  )   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("evaluateId", evaluateId);
			List<TbFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> findByEvaluateMap(Integer evaluateId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize from Tb_Res_evaluate_File tref left join Tb_File tf on tf.id=tref.file_id where  tref.evaluate_id =:evaluateId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("evaluateId", evaluateId);
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
