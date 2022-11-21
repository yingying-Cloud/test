package com.jinpinghu.logic.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.GetMyPostListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetMyPostListLogic extends BaseZLogic {
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetMyPostListParam myParam = (GetMyPostListParam)logicParam;
		String title = myParam.getTitle();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String types = myParam.getTypes();
		String modes = myParam.getModes();
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
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		
		TbRole role = resUserRoleDao.findByUserTabId(loginUser.getId());
		
		
		Integer maxCount = 0;
		if(role.getId() == 6 || role.getId() == 2){
			//专家,获取全部提问帖表
			maxCount = tbPostDao.findAllCountZJ(title, startTime, endTime, types,
					modesList, status,keywordsList,null,null,null);
		}else{
			//个人
			maxCount = tbPostDao.findAllCount(loginUser.getId(), title, startTime, endTime, types,
					modesList, status,keywordsList);
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
			list = tbPostDao.findAllZJ(title, startTime, endTime,
					types, modesList, status, keywordsList, nowPage, pageCount,sort,null,null,null);
		}else{
			//个人
			list = tbPostDao.findAll(loginUser.getId(), title, startTime, endTime,
					types, modesList, status, keywordsList, nowPage, pageCount,sort);
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
			
			tMap.put("status", o[8]);
			tMap.put("top", o[9]);
			
			tMap.put("mode", o[15]);
			if(o[16]!=null){
				String[] listStr = o[16].toString().split(",");
				List<Map<String, Object>> listfile =new ArrayList<Map<String, Object>>();
				for(String file:listStr)
				{
					tMaps = new HashMap<String, Object>();
					tMaps.put("fileUrl", file);
					listfile.add(tMaps);
				}
				tMap.put("file", listfile);
			}
			tMap.put("keywords", o[17]);
			tMap.put("workSn", o[21]);
			tMap.put("isStar", o[22]);
			
			TbUser user =ulDao.findById((Integer) o[18]);
			tMap.put("mobile",user.getMobile());
			tMap.put("userId", user.getUserId());
			tMap.put("userTabId", user.getId());
			tMap.put("name", user.getName());
			
			Map<String, Object> replyMap = new HashMap<String,Object>();
			replyMap.put("replyCount", o[4]);
			replyMap.put("replyUserName", o[5]);
			replyMap.put("replyTime", DateTimeUtil.smartFormat((Date)o[6]));
			replyMap.put("replyContent", o[7]);
			replyMap.put("replyUserTabId", o[11]);
			replyMap.put("replyUserRoleId", o[12]);
			replyMap.put("replyUserId", o[13]);
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
