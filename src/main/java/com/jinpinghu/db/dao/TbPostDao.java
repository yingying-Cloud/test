package com.jinpinghu.db.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbPost;

@SuppressWarnings("unchecked")
public class TbPostDao extends BaseZDao {

	public TbPostDao(EntityManager _em) {
		super(_em);
	}

	public TbPost findById(Integer id) {
		try {
			TbPost instance = getEntityManager().find(TbPost.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Object[]> findAll(Integer userTabId,String title,String startTime,String endTime,
			String types,List<String> modes,Integer status,List<Integer> keywords, Integer nowPage,
			Integer pageCount,Integer sort) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("( ");
			sb.append("SELECT p.id,p.title,p.content pc,p.input_time pit,p.reply_count, ");
			sb.append("u.username,pr.input_time prit,pr.content prc,p.status,p.top ptop,p.important_level pil,u.id uid,u.user_type uut,u.user_id,u.realname,p.mode, ");
			sb.append("( SELECT GROUP_CONCAT(f.file_url) ");
			sb.append("FROM tb_file f ");
			sb.append("INNER JOIN tb_res_post_file pf ON f.id = pf.file_id ");
			sb.append("WHERE pf.post_id = p.id  ");
			sb.append(") files, ");
			sb.append("( SELECT GROUP_CONCAT(k. NAME) ");
			sb.append("FROM tb_keyword k ");
			sb.append("INNER JOIN tb_res_post_keywork pk ON k.id = pk.keyword_id ");
			sb.append("WHERE pk.post_id = p.id ");
			sb.append(") keywords,p.work_sn workSn,p.is_star isStar  ");
			sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
			sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
			sb.append("ON pr.user_tab_id = u.id LEFT JOIN tb_res_post_keywork k ");
			sb.append("ON p.id = k.post_id ");
			sb.append("WHERE del_flag = 0 ");
			if(userTabId!=null){
				sb.append("AND p.user_tab_id = :userTabId ");
			}
			if(!StringUtils.isEmpty(title)){
				sb.append("AND( p.title LIKE :title ");
				sb.append(" OR p.content LIKE :title) ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("AND p.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("AND p.input_time <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("AND p.type IN ( :types ) ");
			}
			if(modes!=null&&modes.size()>0){
				sb.append("AND p.mode IN ( :modes ) ");
			}
			if(status!=null){
				sb.append("AND p.status = :status ");
			}
			
			sb.append(") UNION (");
			sb.append("SELECT p.id,p.title,p.content pc,p.input_time pit,p.reply_count, ");
			sb.append("u.username,pr.input_time prit,pr.content prc,p.status,p.top ptop,p.important_level pil,u.id uid,u.user_type uut,u.user_id,u.realname,p.mode, ");
			sb.append("( SELECT GROUP_CONCAT(f.file_url) ");
			sb.append("FROM tb_file f ");
			sb.append("INNER JOIN tb_res_post_file pf ON f.id = pf.file_id ");
			sb.append("WHERE pf.post_id = p.id  ");
			sb.append(") files, ");
			sb.append("( SELECT GROUP_CONCAT(k. NAME) ");
			sb.append("FROM tb_keyword k ");
			sb.append("INNER JOIN tb_res_post_keywork pk ON k.id = pk.keyword_id ");
			sb.append("WHERE pk.post_id = p.id ");
			if(keywords!=null&&keywords.size()>0){
				sb.append("AND k.post_id in ( :keywords ) ");
			}
			sb.append(") keywords ");
			sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
			sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
			sb.append("ON pr.user_tab_id = u.id LEFT JOIN tb_res_post_keywork k ");
			sb.append("ON p.id = k.post_id ");
			sb.append("WHERE del_flag = 0 ");
			if(!StringUtils.isEmpty(title)){
				sb.append("AND( p.title LIKE :title ");
				sb.append(" OR p.content LIKE :title) ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("AND p.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("AND p.input_time <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("AND p.type IN ( :types ) ");
			}
			if(modes!=null&&modes.size()>0){
				sb.append("AND p.mode IN ( :modes ) ");
			}
			if(status!=null){
				sb.append("AND p.status = :status ");
			}

			sb.append(") ORDER BY ptop DESC,pil ");
			if(sort==1)
			{
				sb.append(" ,pit DESC ");
			}else if(sort==2){
				sb.append(" ,pit ASC ");
			}else if(sort ==3){
				sb.append(" ,p.reply_count desc ");
			}
			else if(sort ==4){
				sb.append(" ,p.input_time desc ");
			}
			else if(sort ==5){
				sb.append(" ,if(p.last_reply_time is null,1,0),p.last_reply_time desc ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(userTabId!=null){
				query.setParameter("userTabId", userTabId);
			}
			if(!StringUtils.isEmpty(title)){
				query.setParameter("title", "%"+title+"%");
			}
			if(!StringUtils.isEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(types)){
				query.setParameter("types", types);
			}
			if(modes!=null&&modes.size()>0){
				query.setParameter("modes", modes);
			}
			if(status!=null){
				query.setParameter("status", status);
			}
			if(keywords!=null&&keywords.size()>0){
				query.setParameter("keywords", keywords);
			}
			query.setFirstResult((nowPage-1)*pageCount);
			query.setMaxResults(pageCount);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer findAllCount(Integer userTabId,String title,String startTime,String endTime,
			String types,List<String> modes,Integer status,List<Integer> keywords) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT COUNT(*) ");
			sb.append("FROM ");
			sb.append("( ");
			sb.append("( ");
			sb.append("SELECT p.id,p.title,p.content pc,p.input_time pit,p.reply_count, ");
			sb.append("u.username,pr.input_time prit,pr.content prc,p.status,p.top ptop,p.important_level pil,u.id uid,u.user_type uut,u.user_id,u.realname ");
			sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
			sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
			sb.append("ON pr.user_tab_id = u.id LEFT JOIN tb_res_post_keywork k ");
			sb.append("ON p.id = k.post_id ");
			sb.append("WHERE del_flag = 0 ");
			if(userTabId!=null){
				sb.append("AND p.user_tab_id = :userTabId ");
			}
			if(!StringUtils.isEmpty(title)){
				sb.append("AND( p.title LIKE :title ");
				sb.append(" OR p.content LIKE :title) ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("AND p.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("AND p.input_time <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("AND p.type IN ( :types ) ");
			}
			if(modes!=null&&modes.size()>0){
				sb.append("AND p.mode IN ( :modes ) ");
			}
			if(status!=null){
				sb.append("AND p.status = :status ");
			}
			if(keywords!=null&&keywords.size()>0){
				sb.append("AND k.post_id in ( :keywords ) ");
			}
			sb.append(") UNION (");
			sb.append("SELECT p.id,p.title,p.content pc,p.input_time pit,p.reply_count, ");
			sb.append("u.username,pr.input_time prit,pr.content prc,p.status,p.top ptop,p.important_level pil,u.id uid,u.user_type uut,u.user_id,u.realname ");
			sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
			sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
			sb.append("ON pr.user_tab_id = u.id LEFT JOIN tb_res_post_keywork k ");
			sb.append("ON p.id = k.post_id ");
			sb.append("WHERE del_flag = 0 ");
			if(!StringUtils.isEmpty(title)){
				sb.append("AND p.title LIKE :title ");
				sb.append("AND p.content LIKE :title ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("AND p.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("AND p.input_time <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("AND p.type IN ( :types ) ");
			}
			sb.append("AND p.mode = 2 ");
			if(status!=null){
				sb.append("AND p.status = :status ");
			}
			if(keywords!=null&&keywords.size()>0){
				sb.append("AND k.post_id in ( :keywords ) ");
			}
			sb.append(") ");
			sb.append(") a");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(userTabId!=null){
				query.setParameter("userTabId", userTabId);
			}
			if(!StringUtils.isEmpty(title)){
				query.setParameter("title", "%"+title+"%");
			}
			if(!StringUtils.isEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(types)){
				query.setParameter("types", types);
			}
			if(modes!=null&&modes.size()>0){
				query.setParameter("modes", modes);
			}
			if(status!=null){
				query.setParameter("status", status);
			}
			if(keywords!=null&&keywords.size()>0){
				query.setParameter("keywords", keywords);
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
	
	public List<Object[]> findAllZJ(String title,String startTime,String endTime,
			String types,List<String> modes,Integer status,List<Integer>  keywords,Integer nowPage,
			Integer pageCount,Integer sort,Integer enterpriseId,Integer expertId,String isStar) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT DISTINCT p.id,p.title,p.content pc,p.input_time pit,p.reply_count, ");
			sb.append("u.name replyName,pr.input_time prit,pr.content prc,p.status,p.top ptop, ");
			sb.append("p.important_level pil,u.id uid,trur.role_id roleId,u.user_id,u.name name,p.mode, ");
			sb.append(" GROUP_CONCAT(distinct tf.id)  files,GROUP_CONCAT(distinct tk. id)  keywords,p.user_tab_id,p.last_reply_id,p.expert_id,"
					+ "p.work_sn workSn,p.is_star isStar,p.level,tc.cost,pr.star,pr.tool_name ");
			sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
			sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
			sb.append("ON pr.user_tab_id = u.id LEFT JOIN tb_res_post_keyword k ");
			sb.append("ON p.id = k.post_id left join tb_res_user_role trur on u.id = trur.user_tab_id ");
			sb.append("left join tb_res_post_file trpf on trpf.post_id = p.id ");
			sb.append("left join tb_file tf on tf.id = trpf.file_id ");
			sb.append("left join tb_res_post_keyword trpk on trpk.post_id = p.id ");
			sb.append("left join tb_keyword tk on tk.id = trpk.keyword_id ");
			sb.append("left join tb_cost tc on tc.type = p.level ");
			sb.append("WHERE p.del_flag = 0 ");
			if(enterpriseId != null)
				sb.append(" and p.enterprise_id = :enterpriseId ");
			if(!StringUtils.isEmpty(title)){
				sb.append("AND( p.title LIKE :title ");
				sb.append(" OR p.content LIKE :title) ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("AND p.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("AND p.input_time <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("AND p.type IN ( :types ) ");
			}
			if(modes!=null&&modes.size()>0){
				sb.append("AND p.mode IN ( :modes ) ");
			}
			if(status!=null){
				sb.append("AND p.status = :status ");
			}
			if(keywords!=null&&keywords.size()>0){
				sb.append("AND k.keyword_id in ( :keywords ) ");
			}
			if(expertId != null) {
				sb.append(" and p.expert_id = :expertId ");
			}
			if(!StringUtils.isEmpty(isStar)){
				sb.append("AND p.is_star = :isStar ");
			}
			sb.append(" group by p.id ");
			sb.append("ORDER BY ptop DESC,pil ");
			if(sort==1)
			{
				sb.append(" ,pit DESC ");
			}else if(sort==2){
				sb.append(" ,pit ASC ");
			}else if(sort ==3){
				sb.append(" ,p.reply_count desc ");
			}else if(sort ==4){
				sb.append(" ,p.input_time desc ");
			}
			else if(sort ==5){
				sb.append(" ,if(p.last_reply_time is null,1,0),p.last_reply_time desc ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if(!StringUtils.isEmpty(title)){
				query.setParameter("title", "%"+title+"%");
			}
			if(!StringUtils.isEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(types)){
				query.setParameter("types", types);
			}
			if(modes!=null&&modes.size()>0){
				query.setParameter("modes", modes);
			}
			if(status!=null){
				query.setParameter("status", status);
			}
			if(keywords!=null&&keywords.size()>0){
				query.setParameter("keywords", keywords);
			}
			if(expertId!=null){
				query.setParameter("expertId", expertId);
			}
			if(!StringUtils.isEmpty(isStar)){
				query.setParameter("isStar", isStar);
			}
			query.setFirstResult((nowPage-1)*pageCount);
			query.setMaxResults(pageCount);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer findAllCountZJ(String title,String startTime,String endTime,
			String types,List<String> modes,Integer status,List<Integer> keywords,Integer enterpriseId,Integer expertId,String isStar) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT COUNT(distinct p.id) ");
			sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
			sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
			sb.append("ON pr.user_tab_id = u.id LEFT JOIN tb_res_post_keyword k ");
			sb.append("ON p.id = k.post_id ");
			sb.append("WHERE p.del_flag = 0 ");
			if(enterpriseId != null)
				sb.append(" and p.enterprise_id = :enterpriseId ");
			if(!StringUtils.isEmpty(title)){
				sb.append("AND( p.title LIKE :title ");
				sb.append(" OR p.content LIKE :title) ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("AND p.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("AND p.input_time <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("AND p.type IN ( :types ) ");
			}
			if(modes!=null&&modes.size()>0){
				sb.append("AND p.mode IN ( :modes ) ");
			}
			if(status!=null){
				sb.append("AND p.status = :status ");
			}
			if(keywords!=null&&keywords.size()>0){
				sb.append("AND k.id in ( :keywords ) ");
			}
			if(expertId != null) {
				sb.append(" and p.expert_id = :expertId ");
			}
			if(!StringUtils.isEmpty(isStar)){
				sb.append("AND p.is_star = :isStar ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if(!StringUtils.isEmpty(title)){
				query.setParameter("title", "%"+title+"%");
				
			}
			if(!StringUtils.isEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(types)){
				query.setParameter("types", types);
			}
			if(modes!=null&&modes.size()>0){
				query.setParameter("modes", modes);
			}
			if(status!=null){
				query.setParameter("status", status);
			}
			if(keywords!=null&&keywords.size()>0){
				query.setParameter("keywords", keywords);
			}
			if(expertId!=null){
				query.setParameter("expertId", expertId);
			}
			if(!StringUtils.isEmpty(isStar)){
				query.setParameter("isStar", isStar);
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
	
	public List<TbPost> findAllAdmin(String title,String startTime,String endTime,
			String types,String modes,String status,Integer nowPage,
			Integer pageCount) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from TbPost where delFlag = 0 ");
			if(!StringUtils.isEmpty(title)){
				sb.append("and title like :title ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("and inputTime >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("and inputTime <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("and type IN ( :types ) ");
			}
			if(!StringUtils.isEmpty(modes)){
				sb.append("AND mode IN ( :modes ) ");
			}
			if(!StringUtils.isEmpty(status)){
				sb.append("AND status = :status ");
			}
			sb.append("order by top DESC,importantLevel,inputTime DESC ");
			Query query = getEntityManager().createQuery(sb.toString());
			if(!StringUtils.isEmpty(title)){
				query.setParameter("title", "%"+title+"%");
			}
			if(!StringUtils.isEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(types)){
				query.setParameter("types", types);
			}
			if(!StringUtils.isEmpty(modes)){
				query.setParameter("modes", modes);
			}
			if(!StringUtils.isEmpty(status)){
				query.setParameter("status", Integer.valueOf(status));
			}
			query.setFirstResult((nowPage-1)*pageCount);
			query.setMaxResults(pageCount);
			List<TbPost> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer findAllAdminCount(String title,String startTime,String endTime,
			String types,String modes,String status) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select count(*) from TbPost where delFlag = 0 ");
			if(!StringUtils.isEmpty(title)){
				sb.append("and title like :title ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("and inputTime >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("and inputTime <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("and type IN ( :types ) ");
			}
			if(!StringUtils.isEmpty(modes)){
				sb.append("AND mode IN ( :modes ) ");
			}
			if(!StringUtils.isEmpty(status)){
				sb.append("AND status = :status ");
			}
			sb.append("order by top,importantLevel,inputTime DESC ");
			Query query = getEntityManager().createQuery(sb.toString());
			if(!StringUtils.isEmpty(title)){
				query.setParameter("title", "%"+title+"%");
			}
			if(!StringUtils.isEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(types)){
				query.setParameter("types", types);
			}
			if(!StringUtils.isEmpty(modes)){
				query.setParameter("modes", modes);
			}
			if(!StringUtils.isEmpty(status)){
				query.setParameter("status", Integer.valueOf(status));
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
	public List<TbPost> findByInReply(Integer userId,String sort,Integer nowPage,Integer pageCount){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbPost where delFlag = 0 ");
			sb.append(" and id in (select tbPost.id from TbPostReply where delFlag=0 and userTabId=:id )");
			sb.append(" order by lastReplyTime ");
			if(sort.equals("asc"))
				sb.append(" asc ");
			else
				sb.append(" desc ");
			Query query = getEntityManager().createQuery(sb.toString());
			query.setParameter("id", userId);
			query.setFirstResult((nowPage-1)*pageCount);
			query.setMaxResults(pageCount);
			List<TbPost> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> findByModes(Integer userTabId,String title,String startTime,String endTime,
			String types,List<String> modes,Integer status,List<Integer>  keywords, Integer nowPage,
			Integer pageCount,Integer sort,Integer other){
		try {
			StringBuffer sb = new StringBuffer();
			if(other==3)
				sb.append("(");
			sb.append("SELECT DISTINCT p.id,p.title,p.content pc,p.input_time pit,p.reply_count, ");
			sb.append("u.nickname,pr.input_time prit,pr.content prc,p.status,p.top ptop,p.important_level pil,u.id uid,u.user_type uut,u.user_id,u.realname,p.mode, ");
			sb.append("( SELECT GROUP_CONCAT(f.id) ");
			sb.append("FROM tb_file f ");
			sb.append("INNER JOIN tb_res_post_file pf ON f.id = pf.file_id ");
			sb.append("WHERE pf.post_id = p.id  ");
			sb.append(") files, ");
			sb.append("( SELECT GROUP_CONCAT(k. id) ");
			sb.append("FROM tb_keyword k ");
			sb.append("INNER JOIN tb_res_post_keywork pk ON k.id = pk.keyword_id ");
			sb.append("WHERE pk.post_id = p.id and k.del_flag=0  ");
			sb.append(") keywords, ");
			sb.append(" p.open_farmland,p.open_farmland_id,u.sex ");
			sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
			sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
			sb.append("ON pr.user_tab_id = u.id  ");
			sb.append("LEFT JOIN tb_res_post_keywork k on p.id = k.post_id ");
			sb.append("WHERE p.del_flag = 0  ");
			if(other==1){ //other==1  看自己的 私有或者公开帖子 other==2看别人的公开贴
				if(userTabId!=null){
					sb.append("AND p.user_tab_id = :userTabId ");
				}
			}else if(other==2){
				if(userTabId!=null){
					sb.append("AND p.user_tab_id != :userTabId ");
				}
			}
			if(!StringUtils.isEmpty(title)){
				sb.append("AND( p.title LIKE :title ");
				sb.append(" OR p.content LIKE :title) ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("AND p.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("AND p.input_time <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("AND p.type IN ( :types ) ");
			}
			if(modes!=null&&modes.size()>0){
				sb.append("AND p.mode IN ( :modes ) ");
			}
			if(status!=null){
				sb.append("AND p.status = :status ");
			}
			if(keywords!=null&&keywords.size()>0){
				sb.append("AND k.keyword_id in ( :keywords ) ");
			}
			if(other==3){
				sb.append(") UNION (");
				sb.append("SELECT DISTINCT p.id,p.title,p.content pc,p.input_time pit,p.reply_count prc, ");
				sb.append("u.nickname,pr.input_time prit,pr.content prc,p.status,p.top ptop,p.important_level pil,u.id uid,u.user_type uut,u.user_id,u.realname,p.mode, ");
				sb.append("( SELECT GROUP_CONCAT(f.id) ");
				sb.append("FROM tb_file f ");
				sb.append("INNER JOIN tb_res_post_file pf ON f.id = pf.file_id ");
				sb.append("WHERE pf.post_id = p.id  ");
				sb.append(") files, ");
				sb.append("( SELECT GROUP_CONCAT(k. id) ");
				sb.append("FROM tb_keyword k ");
				sb.append("INNER JOIN tb_res_post_keywork pk ON k.id = pk.keyword_id ");
				sb.append("WHERE pk.post_id = p.id and k.del_flag=0 ");
				sb.append(") keywords, ");
				sb.append(" p.open_farmland,p.open_farmland_id,u.sex ");
				sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
				sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
				sb.append("ON pr.user_tab_id = u.id  ");
				sb.append(" LEFT JOIN tb_res_post_keywork k on p.id = k.post_id ");
				sb.append("WHERE p.del_flag = 0 ");
				if(!StringUtils.isEmpty(title)){
					sb.append("AND( p.title LIKE :title ");
					sb.append(" OR p.content LIKE :title) ");
				}
				if(!StringUtils.isEmpty(startTime)){
					sb.append("AND p.input_time >= :startTime ");
				}
				if(!StringUtils.isEmpty(endTime)){
					sb.append("AND p.input_time <= :endTime ");
				}
				if(!StringUtils.isEmpty(types)){
					sb.append("AND p.type IN ( :types ) ");
				}
				if(modes!=null&&modes.size()>0){
					sb.append("AND p.mode IN ( :modes ) ");
				}
				if(status!=null){
					sb.append("AND p.status = :status ");
				}
				if(keywords!=null&&keywords.size()>0){
					sb.append("AND k.keyword_id in ( :keywords ) ");
				}
				sb.append(" )"); 
			}
			sb.append(" ORDER BY ptop DESC,pil ");
			if(sort==1)
			{
				sb.append(" ,pit DESC ");
			}else if(sort==2){
				sb.append(" ,pit ASC ");
			}else if(sort ==3){
				sb.append(" ,prc desc ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(userTabId!=null&&other!=3){
				query.setParameter("userTabId", userTabId);
			}
			if(!StringUtils.isEmpty(title)){
				query.setParameter("title", "%"+title+"%");
			}
			if(!StringUtils.isEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(types)){
				query.setParameter("types", types);
			}
			if(modes!=null&&modes.size()>0){
				query.setParameter("modes", modes);
			}
			if(status!=null){
				query.setParameter("status", status);
			}
			if(keywords!=null&&keywords.size()>0){
				query.setParameter("keywords", keywords);
			}
			query.setFirstResult((nowPage-1)*pageCount);
			query.setMaxResults(pageCount);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer findByModesCount(Integer userTabId,String title,String startTime,String endTime,
			String types,List<String> modes,Integer status,List<Integer> keywords, Integer sort,Integer other){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT COUNT(*) from ");
			sb.append(" ( ");
			if(other==3)
				sb.append(" ( ");
			sb.append("SELECT DISTINCT p.id,p.title,p.content pc,p.input_time pit,p.reply_count, ");
			sb.append("u.username,pr.input_time prit,pr.content prc,p.status,p.top ptop,p.important_level pil,u.id uid,u.user_type uut,u.user_id,u.realname,p.mode, ");
			sb.append("( SELECT GROUP_CONCAT(f.file_url) ");
			sb.append("FROM tb_file f ");
			sb.append("INNER JOIN tb_res_post_file pf ON f.id = pf.file_id ");
			sb.append("WHERE pf.post_id = p.id  ");
			sb.append(") files, ");
			sb.append("( SELECT GROUP_CONCAT(k. id) ");
			sb.append("FROM tb_keyword k ");
			sb.append("INNER JOIN tb_res_post_keywork pk ON k.id = pk.keyword_id ");
			sb.append("WHERE pk.post_id = p.id and k.del_flag=0 ");
			sb.append(") keywords, ");
			sb.append(" p.open_farmland,p.open_farmland_id,u.sex ");
			sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
			sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
			sb.append("ON pr.user_tab_id = u.id  ");
			sb.append("LEFT JOIN tb_res_post_keywork k on p.id = k.post_id ");
			sb.append("WHERE p.del_flag = 0  ");
			
			if(other==1){ //other==1  看自己的 私有或者公开帖子 other==2看别人的公开贴
				if(userTabId!=null){
					sb.append("AND p.user_tab_id = :userTabId ");
				}
			}else if(other==2){
				if(userTabId!=null){
					sb.append("AND p.user_tab_id != :userTabId ");
				}
			}
			if(!StringUtils.isEmpty(title)){
				sb.append("AND( p.title LIKE :title ");
				sb.append(" OR p.content LIKE :title) ");
			}
			if(!StringUtils.isEmpty(startTime)){
				sb.append("AND p.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)){
				sb.append("AND p.input_time <= :endTime ");
			}
			if(!StringUtils.isEmpty(types)){
				sb.append("AND p.type IN ( :types ) ");
			}
			if(modes!=null&&modes.size()>0){
				sb.append("AND p.mode IN ( :modes ) ");
			}
			if(status!=null){
				sb.append("AND p.status = :status ");
			}
			if(keywords!=null&&keywords.size()>0){
				sb.append("AND k.keyword_id in ( :keywords ) ");
			}
			sb.append(" ORDER BY ptop DESC,pil ");
			if(sort==1)
			{
				sb.append(" ,pit DESC ");
			}else if(sort==2){
				sb.append(" ,pit ASC ");
			}else if(sort ==3){
				sb.append(" ,p.reply_count desc ");
			}
			if(other==3){
				sb.append(") UNION (");
				sb.append("SELECT DISTINCT p.id,p.title,p.content pc,p.input_time pit,p.reply_count, ");
				sb.append("u.username,pr.input_time prit,pr.content prc,p.status,p.top ptop,p.important_level pil,u.id uid,u.user_type uut,u.user_id,u.realname,p.mode, ");
				sb.append("( SELECT GROUP_CONCAT(f.file_url) ");
				sb.append("FROM tb_file f ");
				sb.append("INNER JOIN tb_res_post_file pf ON f.id = pf.file_id ");
				sb.append("WHERE pf.post_id = p.id  ");
				sb.append(") files, ");
				sb.append("( SELECT GROUP_CONCAT(k. id) ");
				sb.append("FROM tb_keyword k ");
				sb.append("INNER JOIN tb_res_post_keywork pk ON k.id = pk.keyword_id ");
				sb.append("WHERE pk.post_id = p.id and k.del_flag=0 ");
				sb.append(") keywords, ");
				sb.append(" p.open_farmland,p.open_farmland_id,u.sex ");
				sb.append("FROM tb_post p LEFT JOIN tb_post_reply pr ");
				sb.append("ON p.last_reply_id = pr.id LEFT JOIN tb_user u ");
				sb.append("ON pr.user_tab_id = u.id ");
				
				sb.append("LEFT JOIN tb_res_post_keywork k on p.id = k.post_id ");
				sb.append("WHERE p.del_flag = 0 ");
				if(!StringUtils.isEmpty(title)){
					sb.append("AND( p.title LIKE :title ");
					sb.append(" OR p.content LIKE :title) ");
				}
				if(!StringUtils.isEmpty(startTime)){
					sb.append("AND p.input_time >= :startTime ");
				}
				if(!StringUtils.isEmpty(endTime)){
					sb.append("AND p.input_time <= :endTime ");
				}
				if(!StringUtils.isEmpty(types)){
					sb.append("AND p.type IN ( :types ) ");
				}
				if(modes!=null&&modes.size()>0){
					sb.append("AND p.mode IN ( :modes ) ");
				}
				if(status!=null){
					sb.append("AND p.status = :status ");
				}
				if(keywords!=null&&keywords.size()>0){
					sb.append("AND k.keyword_id in ( :keywords ) ");
				}
				sb.append(" ORDER BY ptop DESC,pil ");
				if(sort==1)
				{
					sb.append(" ,pit DESC ");
				}else if(sort==2){
					sb.append(" ,pit ASC ");
				}else if(sort ==3){
					sb.append(" ,p.reply_count desc ");
				}
				sb.append(")");
			}
			
			sb.append(" ) a ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(userTabId!=null&&other!=3){
				query.setParameter("userTabId", userTabId);
			}
			if(!StringUtils.isEmpty(title)){
				query.setParameter("title", "%"+title+"%");
			}
			if(!StringUtils.isEmpty(startTime)){
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(types)){
				query.setParameter("types", types);
			}
			if(modes!=null&&modes.size()>0){
				query.setParameter("modes", modes);
			}
			if(status!=null){
				query.setParameter("status", status);
			}
			if(keywords!=null&&keywords.size()>0){
				query.setParameter("keywords", keywords);
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
	
	public List<Object[]> getStatusCount(Integer enterpriseId,Integer expertId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tto.id),tto.status ");
			sb.append(" from tb_post tto LEFT JOIN tb_enterprise te on te.id=tto.enterprise_Id  ");
			sb.append(" where tto.del_flag=0 ");
			if(enterpriseId!=null) {
				sb.append(" and  enterprise_id=:enterpriseId");
			}
			if(expertId!=null) {
				sb.append(" and  expert_id=:expertId");
			}
			sb.append(" group by tto.status ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(expertId!=null) {
				query.setParameter("expertId", expertId);
			}
			List <Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
}
