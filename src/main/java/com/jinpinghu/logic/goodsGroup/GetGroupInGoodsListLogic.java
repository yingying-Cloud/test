package com.jinpinghu.logic.goodsGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbGroupGoodsDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.goodsGroup.param.GetGroupGoodsListParam;
import com.jinpinghu.logic.goodsGroup.param.GetGroupInGoodsListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetGroupInGoodsListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetGroupInGoodsListParam myParam  = (GetGroupInGoodsListParam)logicParam;
		String groupId =myParam.getGroupId();
		Integer type =StringUtils.isEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		TbGroupGoodsDao groupGoodsDao = new TbGroupGoodsDao(em);
		List<Integer> ids = null;
		if(!StringUtils.isEmpty(groupId)) {
			String[] split = groupId.split(",");
			ids = new ArrayList<Integer>();
			for(String id:split) {
				ids.add(Integer.valueOf(id));
			}
		}
		Integer count = groupGoodsDao.findGroupIdCount(ids,type);
		if(count==null||count==0) {
			res.add("msg", "空数据");
			res.add("status", 2);
			return true;
		}
		JSONArray ja = new JSONArray();
		List<Object[]> groupList = groupGoodsDao.findGroupId(ids,nowPage,pageCount,type);
		if(groupList!=null) {
			for(Object[] o:groupList) {
				JSONObject jo = new JSONObject();
				jo.put("groupName",o[1] );
				List<Map<String,Object>> list = groupGoodsDao.findGoodsByGroupId(Integer.valueOf(o[0].toString()),type);
				jo.put("goods", list);
				ja.add(jo);
			}
		}
		res.add("result", ja).add("allCounts", count);
		res.add("msg", "操作成功");
		res.add("status", 1);
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
