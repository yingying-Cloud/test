package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.bean.TbToolRecoveryRecord;
import com.mysql.cj.util.StringUtils;

public class TbToolRecoveryRecordDao extends BaseZDao{

	public TbToolRecoveryRecordDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbToolRecoveryRecord findById(Integer id) {
		try {
			String queryString = "from TbToolRecoveryRecord where  delFlag = 0 and id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbToolRecoveryRecord> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Map<String, Object> findMapById(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tr.id, ");
			sb.append(" tool.name, ");
			sb.append("tr.all_Number allNumber, ");
			sb.append("tr.number, ");
			sb.append("tr.use_name useName, ");
			sb.append("date_format(tr.input_time,'%Y-%m-%d %H:%i:%S') inputTime,tool.unit,tool.type,tr.use_mobile useMobile,tr.operator,tt.name typeName,tl.name toolName,"
					+ "tr.record_number recordNumber,tool.price ");
			sb.append(" from tb_tool_recovery_record tr left join tb_tool_recovery tool on tool.id=tr.tool_recovery_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" left join tb_tool tl on tl.id=tr.tool_id ");
			sb.append(" where tr.del_flag=0 ");
			sb.append(" and tr.id = :id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id",id );
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
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
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tr.id, ");
			sb.append(" te.name tnm, ");
			sb.append(" tool.name, ");
			sb.append("tr.all_Number, ");
			sb.append("tr.number, ");
			sb.append("tr.use_name, ");
			sb.append("date_format(tr.input_time,'%Y-%m-%d %H:%i:%S'),tool.unit,tool.type,tr.use_mobile,tr.operator,tt.name ttnm,tr.link_order_info_id,tr.total_price,tl.name toolName,"
					+ "tr.record_number recordNumber,tool.price,tloi.link_mobile linkMobile,tloi.legal_Person_Idcard ");
			sb.append(" from tb_tool_recovery_record tr left join tb_tool_recovery tool on tool.id=tr.tool_recovery_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" left join tb_tool tl on tl.id=tr.tool_id "
					+ " left join tb_link_order_info tloi on tloi.id=tr.link_order_info_id  ");
			sb.append(" where tr.del_flag=0 ");
			sb.append(" and tr.id = :id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id",id );
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getToolRecoveryRecordList(Integer enterpriseId ,List<String> toolId,String name,
			Integer nowPage,Integer pageCount,String startTime,String endTime,String useName,String enterpriseName
			,String toolName,Integer linkOrderInfoId ,String recordNumber,String selectAll,List<Integer> enterpriseIdList,String dscd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tr.id, ");
			sb.append(" te.name tnm, ");
			sb.append(" tool.name, ");
			sb.append("tr.all_Number, ");
			sb.append("tr.number ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_record_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_record_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goods_pic ");
			
			sb.append(",tr.use_name, ");
			sb.append("date_format(tr.input_time,'%Y-%m-%d %H:%i:%S'),tool.unit ");
			sb.append(" ,tr.use_mobile,tt.name ttnm,tr.total_price,ifnull(tl.name,'') toolName,tool.price,tr.record_number,tr.link_order_info_Id, ");
			sb.append(" ifnull(tloi.legal_person_idcard,'') idcard,ifnull(tloi.last_pic,'') idcardPic,group_concat(distinct tes.name) recoveryEnterpriseName,cast(tr.number * tool.price as decimal(10,2))money ");
			sb.append(" from tb_tool_recovery_record tr left join tb_tool_recovery tool on tool.id=tr.tool_recovery_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" left join tb_tool tl on tl.id=tr.tool_id ");
			sb.append(" left join tb_link_order_info tloi on tloi.id = tr.link_order_info_Id ");
			sb.append(" left join tb_enterprise_user_production_info trupi on trupi.user_id_card=tloi.legal_person_idcard ");
			sb.append(" left join tb_enterprise tes on tes.id=trupi.enterprise_id ");
			sb.append(" where tr.del_flag=0 ");
			if(enterpriseId!=null) {
				sb.append(" and tr.enterprise_id = :enterpriseId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tr.enterprise_id in (:enterpriseIdList) ");
			}
			if(toolId!=null) {
				sb.append(" and tr.tool_recovery_id in (:toolId) ");
			}
			if(linkOrderInfoId!=null) {
				sb.append(" and tr.link_order_info_Id = :linkOrderInfoId ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(useName)) {
				sb.append(" and tr.use_name like :useName ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tr.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tr.input_time) <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				sb.append(" and tl.name like :toolName ");
			}
			if(!StringUtils.isNullOrEmpty(recordNumber)) {
				sb.append(" and tr.record_number like :recordNumber ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd like :dscd ");
			}
			sb.append(" group by tr.id order by  tr.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(linkOrderInfoId!=null) {
				query.setParameter("linkOrderInfoId",linkOrderInfoId );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName", enterpriseName );
			}
			if(!StringUtils.isNullOrEmpty(useName)) {
				query.setParameter("useName", "%"+useName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				query.setParameter("toolName", "%"+toolName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(recordNumber)) {
				query.setParameter("recordNumber", "%"+recordNumber+"%" );
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd",dscd );
			}
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
	public Integer getToolRecoveryRecordListCount(Integer enterpriseId ,List<String> toolId,String name,String startTime,
			String endTime,String useName,String enterpriseName,String toolName,Integer linkOrderInfoId,String recordNumber,
			String selectAll,List<Integer> enterpriseIdList,String dscd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(tr.id) ");
			sb.append(" from tb_tool_recovery_record tr "
					+ " left join tb_tool_recovery tool on tool.id=tr.tool_recovery_id "
					+ " LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id"
					+ " LEFT JOIN tb_tool tl on tl.id = tr.tool_id  ");
			sb.append(" where tr.del_flag=0 ");
			if(enterpriseId!=null) {
				sb.append(" and tr.enterprise_id = :enterpriseId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tr.enterprise_id in (:enterpriseIdList) ");
			}
			if(toolId!=null) {
				sb.append(" and tr.tool_recovery_id in (:toolId) ");
			}
			if(linkOrderInfoId!=null) {
				sb.append(" and tr.link_order_info_Id = :linkOrderInfoId ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(useName)) {
				sb.append(" and tr.use_name like :useName ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tr.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tr.input_time) <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				sb.append(" and tl.name like :toolName ");
			}
			if(!StringUtils.isNullOrEmpty(recordNumber)) {
				sb.append(" and tr.record_number like :recordNumber ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd like :dscd ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(linkOrderInfoId!=null) {
				query.setParameter("linkOrderInfoId",linkOrderInfoId );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName", enterpriseName );
			}
			if(!StringUtils.isNullOrEmpty(useName)) {
				query.setParameter("useName", "%"+useName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				query.setParameter("toolName", "%"+toolName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(recordNumber)) {
				query.setParameter("recordNumber", "%"+recordNumber+"%" );
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd",dscd );
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getListByToolRecoveryId(Integer toolRecoveryId,String recoveryUnit,
			String sellUnit,String startTime,String endTime,Integer nowPage,Integer pageCount,Integer enterpriseId){
		try {
			String queryString = " SELECT tloi.name sellUnit,te.name recoveryUnit,ttrr.number num,DATE_FORMAT(ttrr.input_time,'%Y-%m-%d %H:%i:%s') tm ";
			queryString += " FROM tb_tool_recovery_record ttrr left join tb_tool_recovery ttr on ttrr.tool_recovery_id = ttr.id ";
			queryString += " left join tb_link_order_info tloi on tloi.id = ttrr.link_order_info_id ";
			queryString += " left join tb_enterprise te on te.id = ttrr.enterprise_id where ttrr.del_flag = 0 and ttr.del_flag = 0 ";
			if(enterpriseId != null)
				queryString += " and ttrr.enterprise_id = :enterpriseId ";
			if(toolRecoveryId != null)
				queryString += " and ttrr.tool_recovery_id = :toolRecoveryId ";
			if(!StringUtils.isNullOrEmpty(recoveryUnit))
				queryString += " and te.name like :recoveryUnit ";
			if(!StringUtils.isNullOrEmpty(sellUnit))
				queryString += " and tloi.name like :sellUnit ";
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString += " and DATE_FORMAT(ttrr.input_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString += " and DATE_FORMAT(ttrr.input_time,'%Y-%m-%d') <= :endTime ";
			queryString += " group by ttrr.id ";
			queryString += " order by ttrr.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if(toolRecoveryId != null)
				query.setParameter("toolRecoveryId", toolRecoveryId);
			if(!StringUtils.isNullOrEmpty(recoveryUnit))
				query.setParameter("recoveryUnit", "%"+recoveryUnit+"%");
			if(!StringUtils.isNullOrEmpty(sellUnit))
				query.setParameter("sellUnit", "%"+sellUnit+"%");
			if(!StringUtils.isNullOrEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isNullOrEmpty(endTime))
				query.setParameter("endTime", endTime);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
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
	
	public Integer getListCountByToolRecoveryId(Integer toolRecoveryId,String recoveryUnit,
			String sellUnit,String startTime,String endTime,Integer enterpriseId){
		try {
			String queryString = " SELECT count(distinct ttrr.id) FROM tb_tool_recovery_record ttrr left join tb_tool_recovery ttr on ttrr.tool_recovery_id = ttr.id ";
			queryString += " left join tb_link_order_info tloi on tloi.id = ttrr.link_order_info_id ";
			queryString += " left join tb_enterprise te on te.id = ttrr.enterprise_id where ttrr.del_flag = 0 and ttr.del_flag = 0 ";
			if(enterpriseId != null)
				queryString += " and ttrr.enterprise_id = :enterpriseId ";
			if(toolRecoveryId != null)
				queryString += " and ttrr.tool_recovery_id = :toolRecoveryId ";
			if(!StringUtils.isNullOrEmpty(recoveryUnit))
				queryString += " and te.name like :recoveryUnit ";
			if(!StringUtils.isNullOrEmpty(sellUnit))
				queryString += " and tloi.name like :sellUnit ";
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString += " and DATE_FORMAT(ttrr.input_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString += " and DATE_FORMAT(ttrr.input_time,'%Y-%m-%d') <= :endTime ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if(toolRecoveryId != null)
				query.setParameter("toolRecoveryId", toolRecoveryId);
			if(!StringUtils.isNullOrEmpty(recoveryUnit))
				query.setParameter("recoveryUnit", "%"+recoveryUnit+"%");
			if(!StringUtils.isNullOrEmpty(sellUnit))
				query.setParameter("sellUnit", "%"+sellUnit+"%");
			if(!StringUtils.isNullOrEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isNullOrEmpty(endTime))
				query.setParameter("endTime", endTime);
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
	
	public List<Map<String,Object>> statisticToolRecoveryRecordList(Integer enterpriseId ,List<String> toolId,String name,
			String startTime,String endTime,String useName,String enterpriseName,String toolName,Integer linkOrderInfoId,
			String recordNumber,String selectAll,List<Integer> enterpriseIdList,String dscd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" cast(sum(tr.number) as decimal) number, tool.name name, tool.unit unit, tr.tool_recovery_id id ");
			sb.append(" from tb_tool_recovery_record tr left join tb_tool_recovery tool on tool.id=tr.tool_recovery_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" left join tb_tool tl on tl.id=tr.tool_id ");
			sb.append(" where tr.del_flag=0 ");
			if(enterpriseId!=null) {
				sb.append(" and tr.enterprise_id = :enterpriseId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tr.enterprise_id in (:enterpriseIdList) ");
			}
			if(toolId!=null) {
				sb.append(" and tr.tool_recovery_id in (:toolId) ");
			}
			if(linkOrderInfoId!=null) {
				sb.append(" and tr.link_order_info_Id = :linkOrderInfoId ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(useName)) {
				sb.append(" and tr.use_name like :useName ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tr.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tr.input_time) <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				sb.append(" and tl.name like :toolName ");
			}
			if(!StringUtils.isNullOrEmpty(recordNumber)) {
				sb.append(" and tr.record_number like :recordNumber ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd like :dscd ");
			}
			sb.append(" group by  tr.tool_recovery_id desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(linkOrderInfoId!=null) {
				query.setParameter("linkOrderInfoId",linkOrderInfoId );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName", enterpriseName);
			}
			if(!StringUtils.isNullOrEmpty(useName)) {
				query.setParameter("useName", "%"+useName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				query.setParameter("toolName", "%"+toolName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(recordNumber)) {
				query.setParameter("recordNumber", "%"+recordNumber+"%" );
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd );
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Object[]> findAllIsSync() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tr.id,tr.use_name,"
					+ " tl.sync_number,"
					+ "te.sync_number enterpriseNum,"
					+ "operator,cast(tool.price*tr.number as decimal(10,2)) money,"
					+ "tr.number,tool.name,date_format(tr.input_time,'%Y-%m-%d %H:%i:%s'),te.name enterpriseName");
			sb.append(" from tb_tool_recovery_record tr "
					+ "left join tb_tool_recovery tool on tool.id=tr.tool_recovery_id "
					+ "LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" left join tb_tool tl on tl.id=tr.tool_id ");
			sb.append(" left join tb_tool_catalog ttc on ttc.code=tl.code ");
			
			sb.append(" where tr.del_flag=0 and (tr.is_sync=0 or tr.is_sync is null) ");
			sb.append(" order by  tr.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	//预警统计 零差价农药
	public List<Map<String, Object>> getEnterpriseZeroMoney(String enterpriseName,Integer nowPage,Integer pageCount,Integer enterpriseId,String selectAll,
			List<Integer> enterpriseIdList,String startTime,String endTime,String dscd,String enterpriseType){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT te.id,te.name,sum(ifnull(te.area,0)) area,"); // 实际种植面积
			sb.append(" ifnull(CAST(sum(ifnull(te.area,0)) * (select value from app_util ap where ap.key='uniform_price_limit' limit 1) as decimal(10,2)),0)zeroMoney,"); // 零差价 限额
			sb.append(" case when te.enterprise_type = 99 then ifnull(CAST(sum(ifnull(te.area,0)) * (select value from app_util ap where ap.key='recovery_not_uniform_price_trash_limit' limit 1) as decimal(10,2)),0) when te.enterprise_type = 100 then te.ny_limit_amount else 0 end money,"); // 非零差价收回 限额
			sb.append(" case when te.enterprise_type = 99 then ifnull(CAST(sum(ifnull(te.area,0)) * (select value from app_util ap where ap.key='recovery_nm_limit' limit 1) as decimal(10,2)),0) when te.enterprise_type = 100 then te.nm_limit_amount else 0 end otherMoney,"); // 废弃农膜 限额
			sb.append(" cast(sum((select cast( sum(tsc.num * tsc.price) as decimal(10,2)) from tb_tool_order tto ");
			sb.append(" left join tb_res_order_car troc on troc.order_id=tto.id");
			sb.append(" left join tb_shopping_car tsc on tsc.id=troc.car_id");
			sb.append(" where tsc.uniform_price=1 and  plant_enterprise_id in (select id ");
			sb.append(" 		from tb_link_order_info");
			sb.append(" 		where legal_person_idcard in (select user_id_Card from tb_enterprise_user_production_info where enterprise_id = te.id  ) )  ");
			
			if(!StringUtils.isNullOrEmpty(startTime))
				sb.append( " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				sb.append( " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') <= :endTime ");
			
			sb.append(" )) as decimal(10,2)) buyZeroMoney,group_concat(distinct ta.name) dscdName,group_concat(distinct te.village) village "); // 零差价 已购买
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd "
					+ "		inner join tb_enterprise_user_production_info teup on teup.enterprise_id=te.id"
					+ " where 1=1 ");
			if (!StringUtils.isNullOrEmpty(enterpriseType)) {
				sb.append(" and te.enterprise_type = :enterpriseType ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and te.id in (:enterpriseIdList) ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			sb.append(" group by te.id  ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(enterpriseType)) {
				query.setParameter("enterpriseType", enterpriseType);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",(endTime ));
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd",(dscd ));
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getEnterpriseZeroMoneyCount(String enterpriseName,Integer nowPage,Integer pageCount,Integer enterpriseId,String selectAll,
			List<Integer> enterpriseIdList,String startTime,String endTime,String dscd,String enterpriseType){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT count(distinct te.id ) "); 
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd "
					+ "		inner join tb_enterprise_user_production_info teup on teup.enterprise_id=te.id");
			if (!StringUtils.isNullOrEmpty(enterpriseType)) {
				sb.append(" and te.enterprise_type = :enterpriseType ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and te.id in (:enterpriseIdList) ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(enterpriseType)) {
				query.setParameter("enterpriseType", enterpriseType);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd",(dscd ));
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	

	// 预警统计 废物回收  实际购买数量统计
	
	public Map<String, Object> getRelBuyByEnterprise(Integer enterpriseId,String startTime,String endTime){
		try {
			StringBuffer sb = new StringBuffer();
	
			sb.append(" select cast( sum(if(tt.unit='包' or tt.unit='袋',tsc.num,0)) as decimal(10,2)) buyNumBag,  ");// 实际购买 包/袋
			sb.append(" cast( sum(if(tt.unit='瓶',tsc.num,0)) as decimal(10,2)) buyNumBottle "); // 实际购买瓶
			sb.append(" from tb_tool_order tto  ");
			sb.append(" left join tb_res_order_car troc on troc.order_id=tto.id ");
			sb.append(" left join tb_shopping_car tsc on tsc.id=troc.car_id ");
			sb.append(" left join tb_tool tt on tt.id=tsc.tool_id ");
			sb.append(" where tsc.uniform_price=1 and  plant_enterprise_id in (select id ");
			sb.append(" 		from tb_link_order_info");
			sb.append(" 		where legal_person_idcard in (select user_id_Card from tb_enterprise_user_production_info where enterprise_id = :enterpriseId  ) )  ");

			if(!StringUtils.isNullOrEmpty(startTime))
				sb.append( " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				sb.append( " and DATE_FORMAT(tto.input_time,'%Y-%m-%d') <= :endTime ");
			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	// 预警统计 废物回收 零差价回收统计 
	
	public Map<String, Object> getRelRecovery(Integer enterpriseId,String startTime,String endTime){
		try {
			StringBuffer sb = new StringBuffer();
	
			sb.append(" select cast(sum(if(ttr.id=4 ,ttrr.number,0)) as decimal(10,2))recoveryZeroNumBottle, ");// 零差价 回收瓶
			sb.append(" cast(sum(if(ttr.id=5 ,ttrr.number,0)) as decimal(10,2))recoveryZeroNumBag, "); // 零差价 回收包/袋
			sb.append(" cast(sum(if(ttr.id=6 or ttr.id=7,ttrr.number * ttr.price,0)) as decimal(10,2))recoveryNum, "); // 非零差价 回收 金额
			sb.append(" cast(sum(if(ttr.id=13,ttrr.number * ttr.price,0)) as decimal(10,2))recoveryNumOther "); // 废弃农膜 回收 金额
			sb.append(" from tb_tool_recovery_record ttrr  ");
			sb.append(" left join tb_tool_recovery ttr on ttr.id=ttrr.tool_recovery_id ");
			sb.append(" left join tb_link_order_info tloi on tloi.id=ttrr.link_order_info_id ");
			sb.append(" left join tb_enterprise_user_production_info teuip on teuip.user_id_card=tloi.legal_person_idcard ");
			sb.append(" where ttrr.del_flag=0 and teuip.enterprise_id=:enterpriseId  ");
			if(!StringUtils.isNullOrEmpty(startTime))
				sb.append( " and DATE_FORMAT(ttrr.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				sb.append( " and DATE_FORMAT(ttrr.input_time,'%Y-%m-%d') <= :endTime ");
			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	// 补贴统计 零差价年统计
	
	public List<Map<String, Object>> getRelSellYear(String year,Integer enterpriseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT te.id,te.name,a.num,a.money ");
			sb.append(" from tb_enterprise te  ");
			sb.append(" left join (select cast( sum(tsc.num) as decimal(10,2))num, ");
			sb.append(" cast( sum(tsc.num * tsc.price) as decimal(10,2)) money,plant_enterprise_id ");
			sb.append(" from tb_tool_order tto  ");
			sb.append(" left join tb_res_order_car troc on troc.order_id=tto.id ");
			sb.append(" left join tb_shopping_car tsc on tsc.id=troc.car_id ");
			sb.append(" left join tb_tool tt on tt.id=tsc.tool_id ");
			sb.append(" where tsc.uniform_price=1  and date_format(tto.input_time,'%Y') =:year group by plant_enterprise_id) a on a.plant_enterprise_id=te.id ");
			sb.append(" where te.enterprise_type=3 and te.del_flag=0 and te.id=:enterpriseId ");
	
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(year)) {
				query.setParameter("year", year);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
				return null;
			} catch (RuntimeException re) {
				throw re;
			}
	}
	
	// 补贴统计 零差价月销售统计
	
	public List<Map<String,Object>> getSellZeroMonth(String startTime,String endTime){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select cast( ifnull(sum(tsc.num),0) as decimal(10,2))num, ");
			sb.append(" cast( ifnull(sum(tsc.num * tsc.price),0) as decimal(10,2)) money,"
					+ " tto.tool_enterprise_id enterpriseId,DATE_FORMAT(tto.input_time,'%Y-%m') inputTime ");
			sb.append(" from tb_tool_order tto  ");
			sb.append(" left join tb_res_order_car troc on troc.order_id=tto.id ");
			sb.append(" left join tb_shopping_car tsc on tsc.id=troc.car_id ");
			sb.append(" left join tb_tool tt on tt.id=tsc.tool_id ");
			sb.append(" where tsc.uniform_price=1    ");//uniform_price 改成1
			if(!StringUtils.isNullOrEmpty(startTime))
				sb.append( " and DATE_FORMAT(tto.input_time,'%Y') = :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				sb.append( " and DATE_FORMAT(tto.input_time,'%Y') = :endTime ");
			sb.append(" group by tto.tool_enterprise_id,DATE_FORMAT(tto.input_time,'%Y-%m') ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",startTime );
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",endTime );
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	// 补贴统计 零差价年销售统计
	
		public Map<String,Object> getSellZeroYear(String startTime,String endTime,Integer enterpriseId){
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" select cast( ifnull(sum(tsc.num),0) as decimal(10,2))num, ");
				sb.append(" cast( ifnull(sum(tsc.num * tsc.price),0) as decimal(10,2)) money,"
						+ " tto.tool_enterprise_id enterpriseId ");
				sb.append(" from tb_tool_order tto  ");
				sb.append(" left join tb_res_order_car troc on troc.order_id=tto.id ");
				sb.append(" left join tb_shopping_car tsc on tsc.id=troc.car_id ");
				sb.append(" left join tb_tool tt on tt.id=tsc.tool_id ");
				sb.append(" where tsc.uniform_price=1    ");//uniform_price 改成1
				if(!StringUtils.isNullOrEmpty(startTime))
					sb.append( " and DATE_FORMAT(tto.input_time,'%Y') = :startTime ");
				if(!StringUtils.isNullOrEmpty(endTime))
					sb.append( " and DATE_FORMAT(tto.input_time,'%Y') = :endTime ");
				if(enterpriseId!=null) {
					sb.append(" and tto.tool_enterprise_id=:enterpriseId ");
				}
				Query query = getEntityManager().createNativeQuery(sb.toString());
				if(!StringUtils.isNullOrEmpty(startTime)) {
					query.setParameter("startTime",startTime );
				}
				if(!StringUtils.isNullOrEmpty(endTime)) {
					query.setParameter("endTime",endTime );
				}
				if(enterpriseId!=null) {
					query.setParameter("enterpriseId",enterpriseId );
				}
				query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<String, Object>> list = query.getResultList();
				if (null != list && list.size() > 0) {
					return list.get(0);
				}
				return null;
			} catch (RuntimeException re) {
				throw re;
			}
		}
	
	// 补贴统计 废物回收年统计
	
	public List<Map<String, Object>> getYear(String name,String dscd,String selectAll,List<Integer> enterpriseIdList,String year,Integer nowPage ,Integer pageCount){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT te.id,te.name enterpriseName,a.recoveryZeroNum,a.recoveryZeroMoney,a.recoveryNum,a.recoveryMoney,ta.name dscdName ");
			sb.append(" from tb_enterprise te left join tb_area ta on ta.id=te.dscd  ");
			sb.append(" left join (select  ");
			sb.append(" cast(sum(if(ttr.id!=13 ,ttrr.number,0)) as decimal(10,2))recoveryZeroNum, ");
			sb.append(" cast(sum(if(ttr.id!=13 ,ttrr.number * ttr.price,0)) as decimal(10,2))recoveryZeroMoney, ");
			sb.append(" cast(sum(if(ttr.id=13,ttrr.number*ttr.price,0)) as decimal(10,2))recoveryNum, ");
			sb.append(" cast(sum(if(ttr.id=13,ttrr.total_price,0)) as decimal(10,2))recoveryMoney,ttrr.enterprise_id ");
			sb.append(" from tb_tool_recovery_record ttrr  ");
			sb.append(" left join tb_tool_recovery ttr on ttr.id=ttrr.tool_recovery_id ");
			sb.append(" left join tb_link_order_info tloi on tloi.id=ttrr.link_order_info_id ");
			sb.append(" left join tb_enterprise_user_production_info teuip on teuip.user_id_card=tloi.legal_person_idcard ");
			sb.append(" where   ttrr.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(year))
				sb.append( " and DATE_FORMAT(ttrr.input_time,'%Y') = :year ");
			sb.append(" group by ttrr.enterprise_id) a on a.enterprise_id=te.id ");
			sb.append(" where te.enterprise_type=3 and te.del_flag=0   ");
			
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and te.name like :name  ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd  ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and te.id in (:enterpriseIdList) ");
			}
			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isNullOrEmpty(year)) {
				query.setParameter("year", year);
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	// 补贴统计 废物回收月统计
	public List<Map<String,Object>> getRecoveryMonth(String startTime,String endTime){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" cast(ifnull(sum(if(ttr.id!=13 ,ttrr.number,0)),0) as decimal(10,2))recoveryZeroNum, ");
			sb.append(" cast(ifnull(sum(if(ttr.id!=13 ,ttrr.number*ttr.price,0)),0) as decimal(10,2))recoveryZeroMoney, ");
			sb.append(" cast(ifnull(sum(if(ttr.id=13,ttrr.number,0)),0) as decimal(10,2))recoveryNum, ");
			sb.append(" cast(ifnull(sum(if(ttr.id=13,ttrr.number*ttr.price,0)),0) as decimal(10,2))recoveryMoney,"
					+ "ttrr.enterprise_id enterpriseId ,DATE_FORMAT(ttrr.input_time,'%Y-%m') inputTime ");
			sb.append(" from tb_tool_recovery_record ttrr  ");
			sb.append(" left join tb_tool_recovery ttr on ttr.id=ttrr.tool_recovery_id ");
	/*		sb.append(" left join tb_link_order_info tloi on tloi.id=ttrr.link_order_info_id ");
			sb.append(" left join tb_enterprise_user_production_info teuip on teuip.user_id_card=tloi.legal_person_idcard ");*/
			sb.append(" where  1=1 ");
			if(!StringUtils.isNullOrEmpty(startTime))
				sb.append( " and DATE_FORMAT(ttrr.input_time,'%Y') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				sb.append( " and DATE_FORMAT(ttrr.input_time,'%Y') <= :endTime ");
			
			sb.append( " group by ttrr.enterprise_id,DATE_FORMAT(ttrr.input_time,'%Y-%m') ");
			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",(endTime ));
			}
//			if(enterpriseId!=null) {
//				query.setParameter("enterpriseId", enterpriseId);
//			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String,Object>> statisticAllToolRecovery(Integer enterpriseId ,List<String> toolId,String name,String startTime,
			String endTime,String useName,String enterpriseName,String toolName,Integer linkOrderInfoId,String recordNumber,
			String selectAll,List<Integer> enterpriseIdList,String dscd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ifnull(a.number,0) number,tool.unit unit,ifnull(cast(tool.price * a.number as decimal(10,2)),0) price,tool.name name ");
			sb.append(" from  tb_tool_recovery tool  ");
			sb.append(" left join (select  ifnull(cast(sum(tr.number) as decimal(10,2)),0) number,tool_recovery_id ");
			sb.append(" from tb_tool_recovery_record tr ");
			sb.append(" left JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" left join tb_tool tl on tl.id=tr.tool_id ");
			sb.append(" where tr.del_flag=0 ");
			if(enterpriseId!=null) {
				sb.append(" and tr.enterprise_id = :enterpriseId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tr.enterprise_id in (:enterpriseIdList) ");
			}
			if(toolId!=null) {
				sb.append(" and tr.tool_recovery_id in (:toolId) ");
			}
			if(linkOrderInfoId!=null) {
				sb.append(" and tr.link_order_info_Id = :linkOrderInfoId ");
			}
			if(!StringUtils.isNullOrEmpty(useName)) {
				sb.append(" and tr.use_name like :useName ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tr.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tr.input_time) <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(recordNumber)) {
				sb.append(" and tr.record_number like :recordNumber ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd like :dscd ");
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				sb.append(" and tl.name like :toolName ");
			}
			sb.append("  group by tr.tool_recovery_id ) a  on tool.id=a.tool_recovery_id ");
			sb.append("");
			sb.append(" where tool.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tool.name like :name ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(toolId!=null) {
				query.setParameter("toolId",toolId );
			}
			if(linkOrderInfoId!=null) {
				query.setParameter("linkOrderInfoId",linkOrderInfoId );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName", enterpriseName);
			}
			if(!StringUtils.isNullOrEmpty(useName)) {
				query.setParameter("useName", "%"+useName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				query.setParameter("toolName", "%"+toolName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(recordNumber)) {
				query.setParameter("recordNumber", "%"+recordNumber+"%" );
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
