package com.jinpinghu.logic.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPostReply;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPostReplyDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.GetTbPostReplyListParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class 
GetTbPostReplyListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetTbPostReplyListParam myParam =(GetTbPostReplyListParam)logicParam;
		Integer postId = myParam.getPostId();
		String sort =StringUtils.isEmpty(myParam.getSort())?"desc":myParam.getSort();
		
		TbPostReplyDao tprDao = new TbPostReplyDao(em);
		TbFileDao tfDao =new TbFileDao(em);
		TbUserDao userDao = new TbUserDao(em);
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		
		List<TbPostReply> tprList = tprDao.findByPostId(postId,sort);
		
		Map<String,Object> tMap;
		List<Map<String,Object>> result =new ArrayList<Map<String,Object>>();
		
		Map<String,Object> tMaps;
		
		if(tprList!=null){
			for(TbPostReply tpr:tprList){
				tMap =new HashMap<String,Object>();
				tMap.put("id",tpr.getId());
				tMap.put("content",tpr.getContent());
				tMap.put("status",tpr.getStatus());
				tMap.put("inputTime",DateTimeUtil.smartFormat(tpr.getInputTime()));
				tMap.put("star",tpr.getStar());
				tMap.put("toolName",tpr.getToolName());
				TbUser replyUser = userDao.findById(tpr.getUserTabId());
//				TbRole replyUserRole = resUserRoleDao.findByUserTabId(replyUser.getId());
				tMap.put("replyMobile", replyUser.getMobile());
				tMap.put("replyUserId", replyUser.getUserId());
				tMap.put("replyUserTabId", replyUser.getId());
//				tMap.put("replyUserRoleId", replyUserRole.getId());
				tMap.put("replyName", replyUser.getName());
				
				//System.out.println(tpr.getId());
				List<TbFile> fileList = tfDao.findByReplyId(tpr.getId());
				if(fileList!=null)
				{
					List<Map<String,Object>> results =new ArrayList<Map     <String,Object>>();
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
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "查询成功");
		return true;
	}

}
