package com.jinpinghu.common.tools;

import java.util.List;

import com.jinpinghu.db.bean.TbGroup;
import com.jinpinghu.db.dao.TbGroupDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GroupUtil {
	
	public static List<Integer> getAllChildGroupId(Integer groupId,TbGroupDao groupDao,List<Integer> groupIds){
		groupIds.add(groupId);
		List<TbGroup> groupList = groupDao.findByParentId(groupId);
		if(groupList != null && groupList.size()>0) {
			for(int i=0;i<groupList.size();i++) {
				TbGroup childrenGroup = groupList.get(i);
				getAllChildGroupId(childrenGroup.getId(),groupDao,groupIds);
			}
		}
		return groupIds;
	}
	public static JSONArray getAllChildGroupIdJSon(Integer groupId,TbGroupDao groupDao,JSONArray ja){
		List<TbGroup> list = groupDao.findByParentId(groupId);
		if(list!=null) {
			for(TbGroup tg:list) {
				JSONObject jo = new JSONObject();
				jo.put("id", tg.getId());
				jo.put("name", tg.getName());
				JSONArray inJa = new JSONArray();
				jo.put("child", getAllChildGroupIdJSon(tg.getId(),groupDao,inJa));
				ja.add(jo);
			}
		}else {
			return null;
		}
		return ja;
	}
}
