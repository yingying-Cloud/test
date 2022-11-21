package com.jinpinghu.logic.goodsGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbGroupGoodsDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.goodsGroup.param.GetGroupGoodsListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetGroupGoodsListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetGroupGoodsListParam myParam  = (GetGroupGoodsListParam)logicParam;
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
		Integer count = groupGoodsDao.findByGroupIdCount(ids,type);
		if(count==null||count==0) {
			res.add("msg", "空数据");
			res.add("status", 2);
			return true;
		}
		List<Map<String,Object>> list = groupGoodsDao.findByGroupId(ids,nowPage,pageCount,type);
		res.add("result", list).add("allCounts", count);
		res.add("msg", "操作成功");
		res.add("status", 1);
		return true;
	}

}
