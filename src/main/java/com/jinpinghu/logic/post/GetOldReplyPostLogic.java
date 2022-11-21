package com.jinpinghu.logic.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPost;
import com.jinpinghu.db.bean.TbPostReply;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.db.dao.TbPostReplyDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.GetOldReplyPostParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetOldReplyPostLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetOldReplyPostParam myParam =(GetOldReplyPostParam)logicParam;
		String sortPostStr =myParam.getSortPost();
		String sortReplyStr=myParam.getSortReply();
		Integer nowPage =myParam.getNowPage();
		Integer pageCount =myParam.getPageCount();
		
		String sortPost =StringUtils.isEmpty(sortPostStr)?"desc":sortPostStr;
		String sortReply =StringUtils.isEmpty(sortReplyStr)?"desc":sortReplyStr;
		
		pageCount = pageCount==null?10:(pageCount<=0?10:pageCount);
		nowPage = nowPage==null?1:(nowPage<=0?1:nowPage);
		
		TbPostDao tpDao =new TbPostDao(em);
		TbUserDao tuDao =new TbUserDao(em);
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		
		Map<String,Object> tMap;
		List<Map<String,Object>> result =new ArrayList<Map<String,Object>>();
		
		Map<String,Object> tMaps;
		Map<String,Object> tMapr;
		
		List<TbPost> tpList =tpDao.findByInReply(loginUser.getId(),sortPost,1,Integer.MAX_VALUE);
		if(tpList==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		Integer maxCount = tpList.size();
		int maxPage = 1;
		maxPage = maxCount/pageCount;
		if(maxCount%pageCount!=0){
			maxPage++;
		}
		
		if (pageCount * nowPage >= (maxCount + pageCount) && maxPage != 0) {
			nowPage = maxPage;
			res.add("allCounts",maxCount);
			res.add("maxPage",maxPage);
			res.add("result", result);
			res.add("status", 1);
			res.add("msg", "该页无记录");
			return true;
		}else if(maxPage == 0){
			nowPage = 1;
		}
		
		
		
		tpList =tpDao.findByInReply(loginUser.getId(),sortPost,nowPage,pageCount);
		TbPostReplyDao tprDao = new TbPostReplyDao(em);
		TbFileDao tfDao =new TbFileDao(em);
		
		if(tpList!=null){
			for(TbPost tp :tpList)
			{
				tMap =new HashMap<String,Object>();
				TbUser postUser = tuDao.findById(tp.getUserTabId());
				TbRole postRole = resUserRoleDao.findByUserTabId(postUser.getId());
				tMap.put("mobile", postUser.getMobile());
				tMap.put("userId", postUser.getUserId());
				tMap.put("userTabId", postUser.getId());
				tMap.put("userRoleId", postRole.getId());
				tMap.put("name", postUser.getName());
				
				tMap.put("title", tp.getTitle());
				tMap.put("content", tp.getContent());
				tMap.put("inputTime", DateTimeUtil.smartFormat(tp.getInputTime()));
				tMap.put("type", tp.getType());
				tMap.put("mode", tp.getMode());
				tMap.put("top", tp.getTop());
				tMap.put("importantLevel", tp.getImportantLevel());
				tMap.put("changeTime",DateTimeUtil.smartFormat( tp.getChangeTime()));
				tMap.put("lastReplyTime", DateTimeUtil.smartFormat(tp.getLastReplyTime()));
				tMap.put("lastReplyId", tp.getLastReplyId());
				tMap.put("replyCount", tp.getReplyCount());
				tMap.put("status", tp.getStatus());
				
				List<TbPostReply> tprList = tprDao.findByUserId(loginUser.getId(),tp.getId(),sortReply);
				if(tprList!=null){
					List<Map<String,Object>> resulttpr =new ArrayList<Map<String,Object>>();
				for(TbPostReply tpr:tprList)
				{
					tMapr =new HashMap<String,Object>();
					tMapr.put("id",tpr.getId());
					tMapr.put("content",tpr.getContent());
					tMapr.put("status",tpr.getStatus());
					tMapr.put("inputTime",DateTimeUtil.smartFormat(tpr.getInputTime()));
					TbUser replyUser = tuDao.findById(tpr.getUserTabId());
					TbRole replyUserRole = resUserRoleDao.findByUserTabId(replyUser.getId());
					tMap.put("replyMobile", replyUser.getMobile());
					tMap.put("replyUserId", replyUser.getUserId());
					tMap.put("replyUserTabId", replyUser.getId());
					tMap.put("replyUserRoleId", replyUserRole.getId());
					tMap.put("replyName", replyUser.getName());
					List<TbFile> fileList = tfDao.findByReplyId(tpr.getId());
					if(fileList!=null)
					{
						List<Map<String,Object>> results =new ArrayList<Map<String,Object>>();
						for(TbFile tf:fileList)
						{
							tMaps =new HashMap<String,Object>();
							tMaps.put("fileName",tf.getFileName());
							tMaps.put("fileType",tf.getFileType());
							tMaps.put("fileSize",tf.getFileSize());
							tMaps.put("fileUrl",tf.getFileUrl());
							tMaps.put("fileId",tf.getId());
							results.add(tMaps);
						}	
						tMapr.put("file", results);
					}
					resulttpr.add(tMapr);
				}
				tMap.put("reply", resulttpr);
				}
				List<TbFile> fileList = tfDao.findByPostId(tp.getId());
				if(fileList!=null)
				{
					List<Map<String,Object>> results =new ArrayList<Map<String,Object>>();
					for(TbFile tf:fileList)
					{
						tMaps =new HashMap<String,Object>();
						tMaps.put("fileName",tf.getFileName());
						tMaps.put("fileType",tf.getFileType());
						tMaps.put("fileSize",tf.getFileSize());
						tMaps.put("fileUrl",tf.getFileUrl());
						tMaps.put("fileId",tf.getId());
						results.add(tMaps);
					}	
					tMap.put("file", results);
				}
				result.add(tMap);
			}
		}
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "成功！");	
		return true;
	}

}
