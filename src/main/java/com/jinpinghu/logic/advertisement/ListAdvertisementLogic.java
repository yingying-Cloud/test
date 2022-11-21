package com.jinpinghu.logic.advertisement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbAdvertisementDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.advertisement.param.ListAdvertisementParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ListAdvertisementLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ListAdvertisementParam myParam = (ListAdvertisementParam)logicParam;
		String title = myParam.getTitle();
		String visible = (myParam.getVisible());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount()) ? null : Integer.valueOf(myParam.getPageCount());
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage()) ? null : Integer.valueOf(myParam.getNowPage());
		String type = myParam.getType();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String status = myParam.getStatus();
		
		TbAdvertisementDao taDao = new TbAdvertisementDao(em);
		Integer count = taDao.getAdvertisementListCount(title, startTime, endTime, type, visible, status);
		List<Map<String,Object>> list = taDao.getAdvertisementList(title, startTime, endTime, type, visible, status, nowPage, pageCount);
		res.add("count", count);
		res.add("result", list);
		res.add("msg", "成功！");
		res.add("status", 1);
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}
}
