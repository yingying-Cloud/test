package com.jinpinghu.logic.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbKeyword;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbExpertDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbKeywordDao;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.GetPostListByModesParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPostListByModesLogic extends BaseZLogic {
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetPostListByModesParam myParam = (GetPostListByModesParam)logicParam;
		String title = myParam.getTitle();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String types = myParam.getTypes();
		String modes = myParam.getModes();
		String isStar = myParam.getIsStar();
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		List<String> modesList=new ArrayList<String>();
		if(!StringUtils.isEmpty(modes)){
			String[] modesStr= modes.split(",");
			for(String mode:modesStr)
			{
				modesList.add(mode);
			}
		}
		Integer status = StringUtils.isEmpty(myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		List<Integer> keywordsList=new ArrayList<Integer>();
		String keywords = myParam.getKeywords();
		
		if(!StringUtils.isEmpty(keywords)){
			String[] keywordsStr= keywords.split(",");
			for(String keyword:keywordsStr)
			{
				keywordsList.add(Integer.valueOf(keyword));
			}
		}
		
		Integer sort =StringUtils.isEmpty(myParam.getSort())?1:Integer.valueOf(myParam.getSort());
		Integer pageCount = myParam.getPageCount();
		Integer nowPage = myParam.getNowPage();
		
		pageCount = pageCount==null?10:(pageCount<=0?10:pageCount);
		nowPage = nowPage==null?1:(nowPage<=0?1:nowPage);
		
		TbUserDao ulDao = new TbUserDao(em);
		TbPostDao tbPostDao = new TbPostDao(em);
		TbFileDao tfDao=new TbFileDao(em);
		TbExpertDao tbExpertDao = new TbExpertDao(em);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		
		TbRole role = resUserRoleDao.findByUserTabId(loginUser.getId());
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		Integer expertId = StringUtils.isEmpty(myParam.getExpertId())?tbExpertDao.findByExpertId(myParam.getUserId()):Integer.valueOf(myParam.getExpertId());
		
		Integer maxCount = 0;
		if(role.getId() == 6 || role.getId() == 2){
			//专家,获取全部提问帖表
			maxCount = tbPostDao.findAllCountZJ(title, startTime, endTime, types,
					modesList, status,keywordsList,enterpriseId,expertId,isStar);
		}else{
			//个人
			maxCount = tbPostDao.findAllCountZJ(title, startTime, endTime, types,
					modesList, status,keywordsList,enterprise.getId(),expertId,isStar);
		}
		if(maxCount==null||maxCount==0){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
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
		
		List<Object[]> list = null;
		
		if(role.getId() == 6 || role.getId() == 2){
			//专家,获取全部提问帖表
			list = tbPostDao.findAllZJ(title, startTime, endTime,types, 
					modesList, status, keywordsList, nowPage, pageCount,sort,null,expertId,isStar);
		}else{
			list = tbPostDao.findAllZJ(title, startTime, endTime,types, 
					modesList, status, keywordsList, nowPage, pageCount,sort,enterprise.getId(),expertId,isStar);
		}
		Map<String, Object> tMap;
		Map<String, Object> tMaps;
		for (int i = 0; i < list.size(); i++) {
			Object[] o = list.get(i);
			tMap = new HashMap<String, Object>();
			tMap.put("id", o[0]);
			tMap.put("title", o[1]);
			tMap.put("content", o[2]);
			tMap.put("inputTime", DateTimeUtil.formatTime2((Date)o[3]));
			tMap.put("replyCount", o[4]);
			tMap.put("status", o[8]);
			tMap.put("top", o[9]);
			tMap.put("mode", o[15]);
			tMap.put("expertId", o[20]);
			tMap.put("workSn", o[21]);
			tMap.put("isStar", o[22]);
			tMap.put("level", o[23]);
			
			if(o[16]!=null){
				String[] listStr = o[16].toString().split(",");
				List<Map<String, Object>> listfile =new ArrayList<Map<String, Object>>();
				for(String file:listStr)
				{
					tMaps = new HashMap<String, Object>();
					TbFile tbFile= tfDao.findById(Integer.valueOf(file));
					tMaps.put("fileUrl", tbFile.getFileUrl());
					tMaps.put("fileName",tbFile.getFileName());
					tMaps.put("fileType",tbFile.getFileType());
					tMaps.put("fileSize",tbFile.getFileSize());
					tMaps.put("fileId",tbFile.getId());
					listfile.add(tMaps);
				}
				tMap.put("file", listfile);
			}
			if(o[17]!=null){
				String kws=o[17].toString();
				if(!StringUtils.isEmpty(kws)){
					String[] kw=kws.split(",");
					TbKeywordDao tkwDao = new TbKeywordDao(em);
					List<Map<String, Object>> resultk =new ArrayList<Map<String, Object>>();
					for(String k:kw){
						Map<String,Object> tMapK= new HashMap<String,Object>();
						TbKeyword tkw =tkwDao.findById(Integer.valueOf(k));
						if(tkw!=null){
							tMapK.put("id", tkw.getId());
							tMapK.put("name", tkw.getName());
							resultk.add(tMapK);
						}
					}
					tMap.put("keyword", resultk);
				}
			}
			//}
			
			TbUser user =ulDao.findById((Integer) o[18]);
			tMap.put("mobile",user.getMobile());
			tMap.put("userId", user.getUserId());
			tMap.put("userTabId", user.getId());
			tMap.put("name", user.getName());
			
			Map<String, Object> replyMap = null;
			if((Integer) o[19] != null) {
				replyMap = new HashMap<String,Object>();
				replyMap.put("replyUserName", o[5]);
				replyMap.put("replyTime", DateTimeUtil.smartFormat((Date)o[6]));
				replyMap.put("replyContent", o[7]);
				replyMap.put("replyUserTabId", o[11]);
				replyMap.put("replyUserRoleId", o[12]);
				replyMap.put("replyUserId", o[13]);
				replyMap.put("star", o[25]);
				replyMap.put("toolName", o[26]);
			}
			tMap.put("reply", replyMap);
			result.add(tMap);
		}
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
}
