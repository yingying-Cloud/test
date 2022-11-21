package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.bean.TbToolRecovery;
import com.mysql.cj.util.StringUtils;

public class TbToolRecoveryDao extends BaseZDao{

	public TbToolRecoveryDao(EntityManager _em) {
		super(_em);
		// TODO �Զ����ɵĹ��캯�����
	}
	public TbToolRecovery findById(Integer id) {
		try {
			String queryString = "from TbToolRecovery where  delFlag = 0 and id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbToolRecovery> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] findInfoById(Integer id, Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tool.id,te.name tnm, ");
			sb.append(" tool.name, ");
			sb.append(" model, ");
			sb.append(" specification, ");
			sb.append(" unit,");
			sb.append(" sum(ifnull(ttrr.number,0)), ");
			sb.append(" describe_, ");
			sb.append(" tool.type,tt.name ttnm,tool.price ");
			sb.append(" from tb_tool_recovery tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" left join tb_tool_recovery_record ttrr on ttrr.tool_recovery_id = tool.id ");
			if(enterpriseId!=null) {
				sb.append(" and ttrr.enterprise_id = :enterpriseId ");
			}
			sb.append(" where tool.del_flag=0 ");
			sb.append(" and tool.id = :id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id",id );
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
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
	
	public List<Object[]> findByName(Integer enterpriseId,String name,String startTime,String endTime,
			Integer nowPage,Integer pageCount,String dscd,String selectAll,List<Integer> enterpriseIdList) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tool.id,te.name tnm, ");
			sb.append(" tool.name, ");
			sb.append(" model, ");
			sb.append(" specification, ");
			sb.append(" unit,");
			sb.append(" sum(ifnull(ttrr.number,0)), ");
			sb.append(" describe_, ");
			sb.append(" tool.type ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_recovery_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_recovery_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goods_pic,tt.name ttnm,tool.price ");
			sb.append(" from tb_tool_recovery tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" left join tb_tool_recovery_record ttrr on ttrr.tool_recovery_id = tool.id ");
			if(enterpriseId!=null) {
				sb.append(" and ttrr.enterprise_id = :enterpriseId ");
			}
			sb.append(" where tool.del_flag=0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool.enterprise_id in (:enterpriseIdList) ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and (tool.name like :name or te.name like :name ) ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tool.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tool.input_time) <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			sb.append(" group by tool.id order by tool.id asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime));
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd",dscd);
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
	public Integer findByNameCount(Integer enterpriseId,String name,String startTime,String endTime,String dscd,String selectAll,List<Integer> enterpriseIdList) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(tool.id) ");
			sb.append(" from tb_tool_recovery tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" where tool.del_flag=0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool.enterprise_id in (:enterpriseIdList) ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and (tool.name like :name or te.name like :name ) ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tool.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tool.input_time) <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime));
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd",dscd);
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
	
	public List<Map<String, Object>> findNotAddToolList(Integer enterpriseId,List<Integer> toolIdList){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tool.id,te.name enterpriseName, ");
			sb.append(" tool.name, ");
			sb.append(" model, ");
			sb.append(" specification, ");
			sb.append(" unit,");
			sb.append(" number, ");
			sb.append(" describe_ 'describe', ");
			sb.append(" tool.type ");
			sb.append(" ,ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_id = tool.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ),'') goodsPic,tt.name ttnm ");
			sb.append(" from tb_tool_recovery tool LEFT JOIN tb_enterprise te on te.id=tool.enterprise_id  ");
			sb.append(" left join tb_type tt on tt.id=tool.type ");
			sb.append(" where tool.del_flag=0 ");
			if(enterpriseId!=null) 
				sb.append(" and tool.enterprise_id = :enterpriseId ");
			if(toolIdList != null && toolIdList.size()>0)
				sb.append(" and tool.id not in (:toolIdList) ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) 
				query.setParameter("enterpriseId", enterpriseId);
			if(toolIdList != null && toolIdList.size()>0)
				query.setParameter("toolIdList", toolIdList);
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
