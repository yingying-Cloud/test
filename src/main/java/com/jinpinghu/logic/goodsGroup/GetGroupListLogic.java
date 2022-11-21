package com.jinpinghu.logic.goodsGroup;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.GroupUtil;
import com.jinpinghu.db.bean.TbGroup;
import com.jinpinghu.db.dao.TbGroupDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.goodsGroup.param.GetGroupListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetGroupListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetGroupListParam myParam = (GetGroupListParam)logicParam;
		TbGroupDao groupDao = new TbGroupDao(em);
		JSONArray ja = new JSONArray();
		List<TbGroup> parentGroup = groupDao.findAllParentId();
		if(parentGroup!=null) {
			for(TbGroup tg:parentGroup) {
				JSONObject jo = new JSONObject();
				jo.put("id", tg.getId());
				jo.put("name", tg.getName());
				JSONArray inJa = new JSONArray();
				jo.put("child", GroupUtil.getAllChildGroupIdJSon(tg.getId(),groupDao,inJa));
				ja.add(jo);
			}
		}
		res.add("result", ja).add("msg", "操作成功").add("status", 1);
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
