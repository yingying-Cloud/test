package com.jinpinghu.db.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbLinkOrderInfo;

public class TbLinkOrderInfoDao extends BaseZDao{

	public TbLinkOrderInfoDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbLinkOrderInfo findById(Integer id) {
		try {
			String queryString = "from TbLinkOrderInfo where id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);

//			String queryString = " SELECT tloi.id, tloi.enterprise_id enterpriseId, " +
//					"tloi.zone_name zoneName, ifnull(tloi.name,'') name, tloi.credit_code creditCode, " +
//					"tloi.legal_person legalPerson, ifnull(tloi.legal_person_idcard,'') legalPersonIdcard," +
//					"tloi.link_people linkPeople, ifnull(tloi.address,'') address, " +
//					"tloi.area, tloi.type, tloi.del_flag delFlag, tloi.country, tloi.idcard_pic idcardPic, " +
//					"if(tloi.last_pic is null or tloi.last_pic = '','https://zhihuishouyin.oss-cn-beijing.aliyuncs.com/1599729063251.jpg',tloi.last_pic) lastPic, " +
//					"ifnull(tloi.sex,'') sex,ifnull(tloi.link_mobile,'') linkMobile, " +
//					"ifnull(tloi.nation,'') nation, tloi.update_time updateTime, tloi.is_validation isValidation, " +
//					"tloi.is_sync isSync, tloi.sync_number syncNumber, tloi.is_other isOther, " +
//					"date_format(tloi.input_time,'%Y-%m-%d %H:%i:%s') inputTime FROM Tb_Link_Order_Info tloi where tloi.del_flag = 0 ";
//			if(id != null){
//				queryString += " and tloi.id = :id ";
//			}
//			Query query = getEntityManager().createNativeQuery(queryString,TbLinkOrderInfo.class);
//			if(id != null){
//				query.setParameter("id", id);
//			}

			List<TbLinkOrderInfo> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbLinkOrderInfo findByEnterpriseId(Integer enterpriseId) {
		try {
			String queryString = "from TbLinkOrderInfo where enterpriseId = :enterpriseId and delFlag=0";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("enterpriseId",enterpriseId);
			List<TbLinkOrderInfo> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbLinkOrderInfo findByIdcard(String idcard) {
		try {
			String queryString = "from TbLinkOrderInfo where legalPersonIdcard = :idcard and delFlag=0 and type = 2 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("idcard",idcard);
			List<TbLinkOrderInfo> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbLinkOrderInfo> findByIdcard2(String idcard) {
		try {
			String queryString = "from TbLinkOrderInfo where (legalPersonIdcard = :idcard or legalPersonIdcard like :idcard2) and delFlag=0 and type = 2 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("idcard",idcard);
			query.setParameter("idcard2","%"+idcard+"%");
			List<TbLinkOrderInfo> list = query.getResultList();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbLinkOrderInfo findByCreditCode(String creditCode) {
		try {
			String queryString = "from TbLinkOrderInfo where creditCode = :creditCode and delFlag=0";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("creditCode",creditCode);
			List<TbLinkOrderInfo> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbLinkOrderInfo findByIegalPersonIdcard(String legalPersonIdcard) {
		try {
			String queryString = "from TbLinkOrderInfo where legalPersonIdcard = :legalPersonIdcard and delFlag=0";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("legalPersonIdcard",legalPersonIdcard);
			List<TbLinkOrderInfo> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbLinkOrderInfo findByCreditCodeAndId(String creditCode,Integer id) {
		try {
			String queryString = "from TbLinkOrderInfo where creditCode = :creditCode and delFlag=0 and id!=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("creditCode",creditCode);
			query.setParameter("id",id);
			List<TbLinkOrderInfo> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbLinkOrderInfo findByIegalPersonIdcardAndId(String legalPersonIdcard,Integer id) {
		try {
			String queryString = "from TbLinkOrderInfo where legalPersonIdcard = :legalPersonIdcard and delFlag=0 and id!=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("legalPersonIdcard",legalPersonIdcard);
			query.setParameter("id",id);
			List<TbLinkOrderInfo> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public Object[] findInfoById(Integer id) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT id, ");
			sql.append(" enterprise_id, ");
			sql.append(" zone_name, ");
			sql.append(" name, ");
			sql.append(" credit_code, ");
			sql.append(" legal_person, ");
			sql.append(" legal_person_idcard, ");
			sql.append(" link_people, ");
			sql.append(" link_mobile, ");
			sql.append(" address, ");
			sql.append(" date_format(input_time,'%Y-%m-%d %H:%i:%s'), ");
			sql.append(" area, ");
			sql.append(" type ");
			sql.append(" FROM tb_link_order_info ");
			sql.append(" WHERE del_flag=0 ");
			if (id!=null){
				sql.append(" AND id=:id ");
			}
			Query query = getEntityManager().createNativeQuery(sql.toString());
			if (id!=null){
				query.setParameter("id",id);
			}
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public int addLinkOrderInfo(TbLinkOrderInfo tbLinkOrderInfo){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(" INSERT IGNORE INTO tb_link_order_info ");
			sql.append(" (enterprise_id,zone_name,name,credit_code,legal_person,legal_person_idcard,link_people,link_mobile,address,input_time,area,del_flag,type) ");
			sql.append(" VALUES(:enterpriseId,:zoneName,:name,:creditCode,:legalPerson,:legalPersonIdcard,:linkPeople,:linkMobile,:address,:inputTime,:area,:delFlag,:type) ");
			Query query=getEntityManager().createNativeQuery(sql.toString());
			if (tbLinkOrderInfo!=null){
				query.setParameter("enterpriseId",tbLinkOrderInfo.getEnterpriseId());
				query.setParameter("zoneName",tbLinkOrderInfo.getZoneName());
				query.setParameter("name",tbLinkOrderInfo.getName());
				query.setParameter("creditCode",tbLinkOrderInfo.getCreditCode());
				query.setParameter("legalPerson",tbLinkOrderInfo.getLegalPerson());
				query.setParameter("legalPersonIdcard",tbLinkOrderInfo.getLegalPersonIdcard());
				query.setParameter("linkPeople",tbLinkOrderInfo.getLinkPeople());
				query.setParameter("linkMobile",tbLinkOrderInfo.getLinkMobile());
				query.setParameter("address",tbLinkOrderInfo.getAddress());
				query.setParameter("inputTime",tbLinkOrderInfo.getInputTime());
				query.setParameter("area",tbLinkOrderInfo.getArea());
				query.setParameter("delFlag",0);
				query.setParameter("type",tbLinkOrderInfo.getType());
			}
			Integer res=(Integer)query.executeUpdate();
			return res;
		}catch(RuntimeException re) {
			throw re;
		}
	}

	public int delLinkOrderInfo(Integer id){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE tb_link_order_info SET del_flag=1 WHERE id=:id ");
			Query query=getEntityManager().createNativeQuery(sql.toString());
			query.setParameter("id",id);
			Integer res=(Integer)query.executeUpdate();
			return res;
		}catch(RuntimeException re) {
			throw re;
		}
	}

	public int updLinkOrderInfo(TbLinkOrderInfo tbLinkOrderInfo){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE tb_link_order_info SET ");
			sql.append(" enterprise_id=:enterpriseId, ");
			sql.append(" zone_name=:zoneName, ");
			sql.append(" name=:name, ");
			sql.append(" credit_code=:creditCode, ");
			sql.append(" legal_person=:legalPerson, ");
			sql.append(" legal_person_idcard=:legalPersonIdcard, ");
			sql.append(" link_people=:linkPeople, ");
			sql.append(" link_mobile=:linkMobile, ");
			sql.append(" address=:address, ");
			sql.append(" area=:area, ");
			sql.append(" type=:type ");
			sql.append(" WHERE del_flag=0 AND id=:id ");
			sql.append(" AND id in  (SELECT id FROM tb_link_order_info WHERE legal_person_idcard=:legalPersonIdcard) ");
			Query query=getEntityManager().createNativeQuery(sql.toString());
			if (tbLinkOrderInfo!=null){
				query.setParameter("enterpriseId",tbLinkOrderInfo.getEnterpriseId());
				query.setParameter("zoneName",tbLinkOrderInfo.getZoneName());
				query.setParameter("name",tbLinkOrderInfo.getName());
				query.setParameter("creditCode",tbLinkOrderInfo.getCreditCode());
				query.setParameter("legalPerson",tbLinkOrderInfo.getLegalPerson());
				query.setParameter("legalPersonIdcard",tbLinkOrderInfo.getLegalPersonIdcard());
				query.setParameter("linkPeople",tbLinkOrderInfo.getLinkPeople());
				query.setParameter("linkMobile",tbLinkOrderInfo.getLinkMobile());
				query.setParameter("address",tbLinkOrderInfo.getAddress());
				query.setParameter("area",tbLinkOrderInfo.getArea());
				query.setParameter("type",tbLinkOrderInfo.getType());
				query.setParameter("id",tbLinkOrderInfo.getId());
			}
			Integer res=(Integer)query.executeUpdate();
			return res;
		}catch(RuntimeException re) {
			throw re;
		}
	}


	public List<Object[]> selLinkOrderInfoList(String name, Integer type, Date startTime, Date endTime,Integer nowPage,Integer pageCount){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT id, ");
			sql.append(" enterprise_id, ");
			sql.append(" zone_name, ");
			sql.append(" name, ");
			sql.append(" credit_code, ");
			sql.append(" legal_person, ");
			sql.append(" legal_person_idcard, ");
			sql.append(" link_people, ");
			sql.append(" link_mobile, ");
			sql.append(" address, ");
			sql.append(" date_format(input_time,'%Y-%m-%d %H:%i:%s'), ");
			sql.append(" area, ");
			sql.append(" type ");
			sql.append(" FROM tb_link_order_info ");
			sql.append(" WHERE del_flag=0 ");
			if (name!=null){
				sql.append(" AND name LIKE :name ");
			}
			if (type!=null){
				sql.append(" AND type =:type ");
			}
			if (startTime!=null){
				sql.append(" AND input_time>=:startTime ");
			}
			if (endTime!=null){
				sql.append(" AND input_time<:endTime ");
			}
			Query query=getEntityManager().createNativeQuery(sql.toString());
			if (name!=null){
				query.setParameter("name","%"+name+"%");
			}
			if (type!=null){
				query.setParameter("type",type);
			}
			if (startTime!=null){
				query.setParameter("startTime",startTime);
			}
			if (endTime!=null){
				query.setParameter("endTime",endTime);
			}
			if(pageCount != null && nowPage != null) {
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Object[]> list=query.getResultList();
			if (list!=null&&list.size()>0){
				return list;
			}
			return null;
		}catch(RuntimeException re){
			throw re;
		}
	}

	public Integer selLinkOrderInfoListCount(String name,Integer type, Date startTime, Date endTime){
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT count(*) ");
			sql.append(" FROM tb_link_order_info ");
			sql.append(" WHERE del_flag=0 ");
			if (name!=null){
				sql.append(" AND name LIKE :name ");
			}
			if (type!=null){
				sql.append(" AND type =:type ");
			}
			if (startTime!=null){
				sql.append(" AND input_time>=:startTime ");
			}
			if (endTime!=null){
				sql.append(" AND input_time<:endTime ");
			}
			Query query=getEntityManager().createNativeQuery(sql.toString());
			if (name!=null){
				query.setParameter("name","%"+name+"%");
			}
			if (type!=null){
				query.setParameter("type",type);
			}
			if (startTime!=null){
				query.setParameter("startTime",startTime);
			}
			if (endTime!=null){
				query.setParameter("endTime",endTime);
			}
			Object res=query.getSingleResult();
			if (res!=null){
				return Integer.valueOf(res.toString());
			}
			return null;
		}catch(RuntimeException re){
			throw re;
		}
	}
	
	public List<TbLinkOrderInfo> findAll() {
		try {
			String queryString = " from TbLinkOrderInfo where delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			List<TbLinkOrderInfo> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getLinkOrderInfoList(String name,Integer nowPage,Integer pageCount,Integer enterpriseId,
			String idcard,String mobile,String enterpriseName,String selectAll,List<Integer> enterpriseIdList,String dscd,String isZj){
		try {
			String queryString = " SELECT tloi.id,ifnull(tloi.name,'') name,if(tloi.last_pic is null or tloi.last_pic = '','https://zhihuishouyin.oss-cn-beijing.aliyuncs.com/1599729063251.jpg',tloi.last_pic) pic,ifnull(tloi.sex,'') sex,ifnull(tloi.link_mobile,'') mobile, ";
			queryString += " ifnull(tloi.nation,'') nation,ifnull(tloi.address,'') address,ifnull(tloi.legal_person_idcard,'') idcard,date_format(tloi.input_time,'%Y-%m-%d %H:%i:%s') inputTime, ";
			queryString += " ifnull(te.enterprise_address,'') enterpriseAddress,ifnull(te.name,'') enterpriseName,ifnull(te2.name,'') ownEnterpriseName FROM tb_link_order_info tloi "
					+ "left join tb_res_enterprise_linkorderinfo trel on tloi.id = trel.link_order_info_id "
					+ "LEFT JOIN tb_enterprise te on te.id=trel.enterprise_id left join tb_enterprise te2 on te2.id = tloi.enterprise_id "
					+ "where tloi.del_flag = 0 and tloi.type = 2 ";
			if ("1".equals(isZj)) {
				queryString += " and tloi.legal_person_idcard like '33%' ";
			}else if("0".equals(isZj)) {
				queryString += " and (tloi.legal_person_idcard not like '33%' or tloi.legal_person_idcard is null) ";
			}
			if(!StringUtils.isEmpty(name))
				queryString += " and tloi.name like :name  ";
			if(!StringUtils.isEmpty(enterpriseName))
				queryString += " and te.name like :enterpriseName ";
			if(enterpriseId != null)
				queryString += " and trel.enterprise_id = :enterpriseId ";
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and trel.enterprise_id in (:enterpriseIdList) ";
			}
			if(!StringUtils.isEmpty(idcard))
				queryString += " and tloi.legal_person_idcard like :idcard ";
			if(!StringUtils.isEmpty(mobile))
				queryString += " and tloi.link_mobile like :mobile ";
			if(!StringUtils.isEmpty(dscd))
				queryString += " and te.dscd = :dscd ";
			queryString += " group by tloi.id "
					+ "order by tloi.id=18832 desc,convert(tloi.name using gbk) asc, last_pic is null ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			if(!StringUtils.isEmpty(enterpriseName))
				query.setParameter("enterpriseName", "%"+enterpriseName+"%");
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isEmpty(idcard))
				query.setParameter("idcard", "%"+idcard+"%");
			if(!StringUtils.isEmpty(mobile))
				query.setParameter("mobile", "%"+mobile+"%");
			if(!StringUtils.isEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(nowPage != null && pageCount != null)
				query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
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
	
	public Map<String, Object> getProvinceLinkOrderInfoCount(String name,Integer enterpriseId,String idcard,String mobile,String enterpriseName,
			String selectAll,List<Integer> enterpriseIdList,String dscd,String isZj){
		try {
			String queryString = " SELECT count(distinct case when tloi.legal_person_idcard like '33%' then tloi.id end ) zjCount,  "
					+ " count(distinct case when tloi.legal_person_idcard not like '33%' or tloi.legal_person_idcard is null then tloi.id end) notZjCount "
					+ "FROM tb_link_order_info tloi left join tb_res_enterprise_linkorderinfo trel on tloi.id = trel.link_order_info_id "
					+ "LEFT JOIN tb_enterprise te on te.id=trel.enterprise_id "
					+ "where tloi.del_flag = 0 and tloi.type = 2 ";
			if ("1".equals(isZj)) {
				queryString += " and tloi.legal_person_idcard like '33%' ";
			}else if("0".equals(isZj)) {
				queryString += " and (tloi.legal_person_idcard not like '33%' or tloi.legal_person_idcard is null) ";
			}
			if(!StringUtils.isEmpty(name))
				queryString += " and tloi.name like :name   ";
			if(!StringUtils.isEmpty(enterpriseName))
				queryString += " and te.name like :enterpriseName ";
			if(enterpriseId != null)
				queryString += " and trel.enterprise_id = :enterpriseId ";
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and trel.enterprise_id in (:enterpriseIdList) ";
			}
			if(!StringUtils.isEmpty(idcard))
				queryString += " and tloi.legal_person_idcard like :idcard ";
			if(!StringUtils.isEmpty(dscd))
				queryString += " and te.dscd = :dscd ";
			if(enterpriseId != null)
				queryString += " and trel.enterprise_id = :enterpriseId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			if(!StringUtils.isEmpty(enterpriseName))
				query.setParameter("enterpriseName", "%"+enterpriseName+"%");
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isEmpty(idcard))
				query.setParameter("idcard", "%"+idcard+"%");
			if(!StringUtils.isEmpty(mobile))
				query.setParameter("mobile", "%"+mobile+"%");
			if(!StringUtils.isEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
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
	
	public Integer getLinkOrderInfoListCount(String name,Integer enterpriseId,String idcard,String mobile,String enterpriseName,
			String selectAll,List<Integer> enterpriseIdList,String dscd,String isZj){
		try {
			String queryString = " SELECT count(distinct tloi.id) FROM "
					+ "tb_link_order_info tloi left join tb_res_enterprise_linkorderinfo trel on tloi.id = trel.link_order_info_id "
					+ "LEFT JOIN tb_enterprise te on te.id=trel.enterprise_id "
					+ "where tloi.del_flag = 0 and tloi.type = 2 ";
			if ("1".equals(isZj)) {
				queryString += " and tloi.legal_person_idcard like '33%' ";
			}else if("0".equals(isZj)) {
				queryString += " and (tloi.legal_person_idcard not like '33%' or tloi.legal_person_idcard is null) ";
			}
			if(!StringUtils.isEmpty(name))
				queryString += " and tloi.name like :name   ";
			if(!StringUtils.isEmpty(enterpriseName))
				queryString += " and te.name like :enterpriseName ";
			if(enterpriseId != null)
				queryString += " and trel.enterprise_id = :enterpriseId ";
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and trel.enterprise_id in (:enterpriseIdList) ";
			}
			if(!StringUtils.isEmpty(idcard))
				queryString += " and tloi.legal_person_idcard like :idcard ";
			if(!StringUtils.isEmpty(dscd))
				queryString += " and te.dscd = :dscd ";
			if(enterpriseId != null)
				queryString += " and trel.enterprise_id = :enterpriseId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			if(!StringUtils.isEmpty(enterpriseName))
				query.setParameter("enterpriseName", "%"+enterpriseName+"%");
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isEmpty(idcard))
				query.setParameter("idcard", "%"+idcard+"%");
			if(!StringUtils.isEmpty(mobile))
				query.setParameter("mobile", "%"+mobile+"%");
			if(!StringUtils.isEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
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
	
	public List<TbLinkOrderInfo> findAllIsSync() {
		try {
			String queryString = "from TbLinkOrderInfo where  delFlag=0 and ( isSync=0 or isSync is null) and type=2  ";
			Query query = getEntityManager().createQuery(queryString);
			List<TbLinkOrderInfo> list = query.getResultList();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public String getLinkOrderInfoEnterpriseSyncNumber(Integer id){
		try {
			String queryString = " SELECT ifnull(group_concat(te.sync_number),'')  ";
			queryString += " FROM tb_link_order_info tloi left join tb_res_enterprise_linkorderinfo trel on tloi.id = trel.link_order_info_id "
					+ "  	left join tb_enterprise te on te.id=trel.enterprise_id where tloi.id=:id ";
			queryString += " group by tloi.id  ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id", id);
			List<String> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	public TbLinkOrderInfo findBySyncNumber(String syncNumber) {
		try {
			String queryString = "from TbLinkOrderInfo where syncNumber = :syncNumber ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("syncNumber",syncNumber);
			List<TbLinkOrderInfo> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
