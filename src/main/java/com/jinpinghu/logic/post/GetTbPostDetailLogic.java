package com.jinpinghu.logic.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbKeyword;
import com.jinpinghu.db.bean.TbPost;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbKeywordDao;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.GetTbPostDetailParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetTbPostDetailLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetTbPostDetailParam myParam =(GetTbPostDetailParam)logicParam;
		Integer id = myParam.getId();
		TbPostDao tpDao =new TbPostDao(em);
		TbPost tp = tpDao.findById(id);
//		TbPostReplyDao tprDao = new TbPostReplyDao(em);
		TbUserDao userDao = new TbUserDao(em);
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		
		Map<String,Object> tMap = new HashMap<String,Object>();
		Map<String,Object> tMaps;
		List<Map<String,Object>> result =new ArrayList<Map<String,Object>>();
		if(tp!=null){
		if(tp.getDelFlag()==0){
			TbUser postUser = userDao.findById(tp.getUserTabId());
			TbRole postUserRole = resUserRoleDao.findByUserTabId(tp.getUserTabId());
			tMap.put("mobile", postUser.getMobile());
			tMap.put("userId", postUser.getUserId());
			tMap.put("userTabId", postUser.getId());
			tMap.put("roleId", postUserRole.getId());
			tMap.put("id", tp.getId());
			tMap.put("name", postUser.getName());;
			tMap.put("title", tp.getTitle());
			tMap.put("content", tp.getContent());
			tMap.put("inputTime", DateTimeUtil.formatTime2(tp.getInputTime()));
			tMap.put("type", tp.getType());
			tMap.put("mode", tp.getMode());
			tMap.put("top", tp.getTop());
			tMap.put("importantLevel", tp.getImportantLevel());
			tMap.put("changeTime",DateTimeUtil.formatTime2( tp.getChangeTime()));
			tMap.put("lastReplyTime", DateTimeUtil.formatTime2(tp.getLastReplyTime()));
//			tMap.put("lastReplyId", tp.getLastReplyId());
			tMap.put("replyCount", tp.getReplyCount());
			tMap.put("status", tp.getStatus());
			tMap.put("workSn", tp.getWorkSn());
			tMap.put("isStar", tp.getIsStar());
//			if(tp.getLastReplyId()!=null){
//			TbPostReply tpr = tprDao.findById(tp.getLastReplyId());
//				if(tpr!=null){
//					tMap.put("replyContent",tpr.getContent());
//					tMap.put("replyStatus",tpr.getStatus());
//					TbUser replyUser = userDao.findById(tpr.getUserTabId());
//					TbRole replyUserRole = resUserRoleDao.findByUserTabId(tpr.getUserTabId());
//					tMap.put("replyName",replyUser.getName());
//					tMap.put("replyUserRoleId", replyUserRole.getId());
//					tMap.put("replyMobile", replyUser.getMobile());
//					tMap.put("replyUserTabId", replyUser.getId());
//					tMap.put("replyUserId", replyUser.getUserId());
//				}
//			}
			TbFileDao tfDao =new TbFileDao(em);
			List<TbFile> fileList = tfDao.findByPostId(id);
			if(fileList!=null)
			{
				for(TbFile tf:fileList)
				{
					tMaps =new HashMap<String,Object>();
					tMaps.put("fileName",tf.getFileName());
					tMaps.put("fileType",tf.getFileType());
					tMaps.put("fileSize",tf.getFileSize());
					tMaps.put("fileUrl",tf.getFileUrl());
					tMaps.put("fileId",tf.getId());
					result.add(tMaps);
				}
				tMap.put("file", result);
			}
			
			TbKeywordDao  tkwDao = new TbKeywordDao(em);
			List<TbKeyword> tkwList =tkwDao.findByPostId(id);
			if(tkwList!=null){
				List<Map<String, Object>> resultk =new ArrayList<Map<String, Object>>();
				for(TbKeyword tkw:tkwList){
					Map<String,Object> tMapK= new HashMap<String,Object>();
					tMapK.put("id", tkw.getId());
					tMapK.put("name", tkw.getName());
					resultk.add(tMapK);
				}
				tMap.put("keyword", resultk);
			}
		}
		}
		res.add("result", tMap);
		res.add("status", 1);
		res.add("msg", "查询成功");
		return true;
	}

}
