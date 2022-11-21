package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbVideo;

public class TbVideoDao extends BaseZDao{

	public TbVideoDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbVideo findById(Integer Id){
		try {
			TbVideo instance = getEntityManager().find(TbVideo.class, Id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbVideo> findAll(String videoIds){
		try {
			String queryString = " from TbVideo where delFlag = 0 ";
			if(!StringUtils.isEmpty(videoIds))
				queryString += " and id in ("+videoIds+")";
			Query query = getEntityManager().createQuery(queryString);
			List<TbVideo> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public void delByIds(List<String> ids){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("UPDATE tb_video td "
					+ "LEFT JOIN tb_res_video_greenhouses trdg "
					+ "ON td.id=trdg.video_id "
					+ "SET trdg.del_flag=1,td.del_flag=1 WHERE td.id IN(:ids)");
			Query query=getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("ids", ids);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Object[]> getList(Integer enterpriseId,Integer nowPage,Integer pageCount){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT DISTINCT(td.id),td.video_name,td.input_time,te.name,td.video_address,td.device_serial,td.channel_no  "
					+ "FROM tb_video td "
					+ "left join tb_enterprise te on td.enterprise_id = te.id "
					+ "WHERE td.del_flag=0 ");
			if(enterpriseId!=null){
				sb.append("AND td.enterprise_id = :enterpriseId ");
			}
			sb.append(" order by td.list_order desc ");
			Query query=getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null){
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(pageCount != null && nowPage != null) {
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Object[]> list=query.getResultList();
			if(list!=null&&list.size()>0){
				return list;
			}else{
				return null;
			}
			
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getListCount(Integer enterpriseId){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT COUNT( DISTINCT(td.id))  "
					+ "FROM tb_video td "
					+ "WHERE td.del_flag=0 ");
			if(enterpriseId != null){
				sb.append("AND td.enterprise_id = :enterpriseId ");
			}
			Query query=getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			List<Object> list=query.getResultList();
			if(list!=null&&list.size()>0){
				String count=list.get(0).toString();
				return Integer.valueOf(count);
			}else{
				return null;
			}
			
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] getInfo(Integer id){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT DISTINCT(td.id),td.video_name,td.input_time,"
					+ "		(select GROUP_CONCAT(CONCAT(greenHouses_name)) from tb_res_video_greenhouses trdg "
					+ "		LEFT JOIN tb_agricultural_greenhouses tag ON tag.id=trdg.greenHouses_id "
					+ "		WHERE trdg.video_id=td.id)as GreenHousesName,tab.base_name,td.video_address,describe_,"
					+ "		(select GROUP_CONCAT(CONCAT(tag.id)) from tb_res_video_greenhouses trdg "
					+ "		LEFT JOIN tb_agricultural_greenhouses tag ON tag.id=trdg.greenHouses_id "
					+ "		WHERE trdg.video_id=td.id)as GreenHousesId,td.device_serial,td.channel_no "
					+ "FROM tb_video td LEFT JOIN tb_agricultural_base tab "
					+ "		ON tab.id=td.base_id "
					+ "WHERE td.del_flag=0 AND td.id=:id");
	
			Query query=getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id", id);
			List<Object[]> list=query.getResultList();
			if(list!=null&&list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
			
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getGreenhousesVideoList( Integer greenhousesId){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("SELECT DISTINCT(td.id),td.video_name,td.input_time,"
					+ "		(select GROUP_CONCAT(CONCAT(greenHouses_name)) from tb_res_video_greenhouses trdg "
					+ "		LEFT JOIN tb_agricultural_greenhouses tag ON tag.id=trdg.greenHouses_id "
					+ "		WHERE trdg.video_id=td.id)as GreenHousesName,tab.base_name,td.video_address,td.device_serial,td.channel_no  "
					+ " ,( SELECT file_url "
					+ " FROM tb_video_img f  "
					+ " WHERE f.video_id = td.id "
					+ " order by input_time desc LIMIT 1 ) pic "
					+ "FROM tb_video td LEFT JOIN tb_agricultural_base tab "
					+ "		ON tab.id=td.base_id "
					+ "		LEFT JOIN tb_res_video_greenhouses trdg2 "
					+ "		ON trdg2.video_id=td.id "
					+ "		LEFT JOIN tb_agricultural_greenhouses tag "
					+ "		ON tag.id=trdg2.greenhouses_id "
					+ "WHERE td.del_flag=0 ");
			if(greenhousesId!=null){
				sb.append("AND tag.id=:greenhousesId ");
			}
			Query query=getEntityManager().createNativeQuery(sb.toString());
			if(greenhousesId!=null){
				query.setParameter("greenhousesId", greenhousesId);
			}
			List<Object[]> list=query.getResultList();
			if(list!=null&&list.size()>0){
				return list;
			}else{
				return null;
			}
			
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] getImg(String tm,Integer videoId){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" "
					+ "  SELECT file_url,video_id "
					+ " FROM tb_video_img_"+tm+" f  "
					+ " WHERE f.video_id = :videoId "
					+ " order by input_time desc LIMIT 1  ");
			Query query=getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("videoId", videoId);
			List<Object[]> list=query.getResultList();
			if(list!=null&&list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
			
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
}
