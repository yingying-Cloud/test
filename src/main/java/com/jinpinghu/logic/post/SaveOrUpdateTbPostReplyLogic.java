package com.jinpinghu.logic.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPost;
import com.jinpinghu.db.bean.TbPostReply;
import com.jinpinghu.db.bean.TbResRelpyFile;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.db.dao.TbPostReplyDao;
import com.jinpinghu.db.dao.TbResRelpyFileDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.SaveOrUpdateTbPostReplyParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class SaveOrUpdateTbPostReplyLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		SaveOrUpdateTbPostReplyParam myParam = (SaveOrUpdateTbPostReplyParam) logicParam;
		String id = myParam.getId();
		String content = myParam.getContent();
		Integer postId = myParam.getPostId();
		String file = myParam.getFile();
		String toolName = myParam.getToolName();

		Date inputTime = new Date();

		TbUserDao tuDao = new TbUserDao(em);
		TbPostDao tpDao = new TbPostDao(em);
		TbPost tp = tpDao.findById(postId);
		if (tp == null || tp.getDelFlag() == 1) {
			res.add("msg", "帖子不存在");
			res.add("status", -1);
			return true;
		}
		TbPostReplyDao tprDao = new TbPostReplyDao(em);
		TbPostReply tpr = null;
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> tMap=new HashMap<String,Object>();
		if (!StringUtils.isEmpty(id))
			tpr = tprDao.findById(Integer.valueOf(id));
		if (tpr != null) {
			tpr.setContent(content);
			tpr.setStatus(1);
			tpr.setToolName(toolName);
			tprDao.update(tpr);
			tMap.put("id", id);
			tMap.put("content", content);
			result.add(tMap);
			res.add("result", result);
			res.add("msg", "更新成功");
		} else {
			tpr = new TbPostReply();
			tpr.setUserTabId(loginUser.getId());
			tpr.setPostId(tp.getId());
			tpr.setContent(content);
			tpr.setInputTime(inputTime);
			tpr.setStatus(1);
			tpr.setDelFlag(0);
			tpr.setToolName(toolName);
			tprDao.save(tpr);
			Integer count = tprDao.findByPostIdCount(postId);
			tp.setLastReplyId(tpr.getId());
			tp.setReplyCount(count);
			tp.setLastReplyTime(inputTime);
			tpDao.update(tp);
			res.add("msg", "保存成功");
		}
		tMap.put("id",tpr.getId());
		tMap.put("content",tpr.getContent());
		tMap.put("status",tpr.getStatus());
		tMap.put("inputTime",DateTimeUtil.smartFormat(tpr.getInputTime()));
		TbUser replyUser = tuDao.findById(tpr.getUserTabId());
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbRole role = resUserRoleDao.findByUserTabId(tpr.getUserTabId());
		tMap.put("roleId",role.getId());
		tMap.put("mobile",replyUser.getMobile());
		tMap.put("userId", replyUser.getUserId());
		tMap.put("nickName", replyUser.getName());
		if (!StringUtils.isEmpty(file)) {
			TbFileDao tfDao = new TbFileDao(em);
			TbResRelpyFileDao trrfDao = new TbResRelpyFileDao(em);
			JSONArray array = JSONArray.fromObject(file);
			List<Map<String,Object>> results =new ArrayList<Map<String,Object>>();
			if (array.size() > 0) {
				for (int i = 0; i < array.size(); i++) {
					Map<String,Object> tMaps =new HashMap<String,Object>();
					TbFile tf = null;
					JSONObject jsonObj = (JSONObject) array.get(i);
					if (jsonObj.containsKey("id"))
						tf = tfDao.findById(jsonObj.getInt("id"));
					if (tf != null) {
						if (jsonObj.containsKey("fileName")){
							tf.setFileName(jsonObj.getString("fileName"));
							tMaps.put("fileName",tf.getFileName());
						}
						if (jsonObj.containsKey("fileSize")){
							tf.setFileSize(jsonObj.getString("fileSize"));
							tMaps.put("fileSize",tf.getFileSize());
					}
						if (jsonObj.containsKey("fileType")){
							tf.setFileType(jsonObj.getInt("fileType"));
							tMaps.put("fileType",tf.getFileType());
						}
						if (jsonObj.containsKey("fileUrl")){
							tf.setFileUrl(jsonObj.getString("fileUrl"));
							tMaps.put("fileUrl",tf.getFileUrl());
						}
						tfDao.update(tf);
					} else {
						tf = new TbFile();
						if (jsonObj.containsKey("fileName")){
							tf.setFileName(jsonObj.getString("fileName"));
							tMaps.put("fileName",tf.getFileName());
						}
						if (jsonObj.containsKey("fileSize")){
							tf.setFileSize(jsonObj.getString("fileSize"));
							tMaps.put("fileSize",tf.getFileSize());
						}
						if (jsonObj.containsKey("fileType")){
							tf.setFileType(jsonObj.getInt("fileType"));
							tMaps.put("fileType",tf.getFileType());
						}
						if (jsonObj.containsKey("fileUrl")){
							tf.setFileUrl(jsonObj.getString("fileUrl"));
							tMaps.put("fileUrl",tf.getFileUrl());
						}
						tfDao.save(tf);
						TbResRelpyFile trrf = new TbResRelpyFile();
						trrf.setFileId(tf.getId());
						trrf.setReplyId(tpr.getId());
						trrfDao.save(trrf);
					}
					tMaps.put("fileId", tf.getId());
					results.add(tMaps);
				}
			}
			tMap.put("file", results);
		}
		result.add(tMap);
		res.add("result", result);
		res.add("status", 1);
		return true;
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) {
		SaveOrUpdateTbPostReplyParam myParam = (SaveOrUpdateTbPostReplyParam) logicParam;

		String content = myParam.getContent();
		Integer postId = myParam.getPostId();

		if (StringUtils.isEmpty(content) || postId == null) {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空！");
			return false;
		}
		return true;
	}

}