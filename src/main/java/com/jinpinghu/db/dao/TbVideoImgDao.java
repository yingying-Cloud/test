package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbVideoImg;

public class TbVideoImgDao extends BaseZDao{

	public TbVideoImgDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	public List<Object[]> findHistoryImgByVideoId(Integer videoId,String startTime,String endTime,Integer nowPage,Integer pageCount,String videoName,Integer enterpriseId,List<String> tmList){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select f,n,d,i from ");
			sb.append(" ( ");
			
			for(int i=0;i<tmList.size();i++){
				if(i!=0) { sb.append(" UNION   "); }
			
				sb.append("(select file_url f,video_name n,date_format(vi.input_time,'%Y-%m-%d %H:%i:%s') d,vi.video_id i from tb_video_img_"+tmList.get(i)+" vi left join tb_video tv on tv.id=vi.video_id where 1=1  ");
				if(videoId!=null) {
					sb.append(" and vi.video_id =:videoId ");
				}
				if(enterpriseId!=null) {
					sb.append(" and tv.enterprise_id =:enterpriseId ");
				}
				if(!StringUtils.isEmpty(startTime))
					sb.append(" and (date_format(vi.input_time,'%Y-%m-%d') >= :startTime ) ");
				if(!StringUtils.isEmpty(endTime))
					sb.append(" and (date_format(vi.input_time,'%Y-%m-%d') <= :endTime ) ");
				if(!StringUtils.isEmpty(videoName)) {
					sb.append(" and tv.video_name like :videoName ");
				}
				sb.append(" ) ");
			}
			sb.append(" )g ");
			sb.append(" order by  d desc,i desc ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			if(videoId!=null) {
				query.setParameter("videoId", videoId);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(videoName)) {
				query.setParameter("videoName", "%"+videoName+"%");
			}
			if(nowPage != null && pageCount != null) {
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Object[]> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public Integer findHistoryImgByVideoIdCount(Integer videoId,String startTime,String endTime,String videoName,Integer enterpriseId,List<String> tmList){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select sum(c+0) from ");
			
			sb.append(" ( ");
			for(int i=0;i<tmList.size();i++){
				if(i!=0) { sb.append(" UNION   "); }
			
				sb.append("(select count(*) c from tb_video_img_"+tmList.get(i)+" vi left join tb_video tv on tv.id=vi.video_id where 1=1  ");
				if(videoId!=null) {
					sb.append(" and vi.video_id =:videoId ");
				}
				if(enterpriseId!=null) {
					sb.append(" and tv.enterprise_id =:enterpriseId ");
				}
				if(!StringUtils.isEmpty(startTime))
					sb.append(" and (date_format(vi.input_time,'%Y-%m-%d') >= :startTime) ");
				if(!StringUtils.isEmpty(endTime))
					sb.append(" and (date_format(vi.input_time,'%Y-%m-%d') <= :endTime ) ");
				if(!StringUtils.isEmpty(videoName)) {
					sb.append(" and tv.video_name like :videoName ");
				}
				sb.append(" ) ");
			}
			sb.append(" )g ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			if(videoId!=null) {
				query.setParameter("videoId", videoId);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(videoName)) {
				query.setParameter("videoName", "%"+videoName+"%");
			}
			List<Object> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return Integer.valueOf(list.get(0).toString());
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public void insert_data(TbVideoImg videoImg,String table) {
		try {
			String queryString = " insert into tb_video_img_"+table+" ";
			queryString += " (video_id, file_url, input_time, del_flag) ";
			queryString += " values(:videoId,:fileUrl,:inputTime,0) ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("videoId", videoImg.getVideoId());
			query.setParameter("fileUrl", videoImg.getFileUrl());
			query.setParameter("inputTime", videoImg.getInputTime());
			query.executeUpdate();
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
}
