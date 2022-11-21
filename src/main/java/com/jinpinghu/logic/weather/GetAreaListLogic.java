package com.jinpinghu.logic.weather;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbHourWeatherDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.weather.param.GetAreaListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAreaListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAreaListParam myParam = (GetAreaListParam)logicParam;
		String needSelf = myParam.getNeedSelf();
		String dscd = myParam.getDscd();
		String permissionDscd = StringUtil.handlePermissionDscd(loginUser, myParam.getUserId(), em);
		TbHourWeatherDao weatherDao = new TbHourWeatherDao(em);
		List<Map<String, Object>> list = null;
		if(!StringUtils.isEmpty(dscd))
			list = weatherDao.findPinghuAreaList(dscd,permissionDscd,needSelf);
		else
			list = weatherDao.findAreaList();
		res.add("result", list);
		res.add("msg", "操作成功");
		res.add("status", 1);
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
			return true;
		}
}
