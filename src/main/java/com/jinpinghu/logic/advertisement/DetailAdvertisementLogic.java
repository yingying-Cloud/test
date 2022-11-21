package com.jinpinghu.logic.advertisement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbAdvertisement;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbAdvertisementDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResAdvertisementEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.advertisement.param.DetailAdvertisementParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DetailAdvertisementLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DetailAdvertisementParam myParam = (DetailAdvertisementParam)logicParam;
		String id= myParam.getId();
		TbAdvertisementDao taDao = new TbAdvertisementDao(em);
		TbFileDao tbFileDao = new TbFileDao(em);
		TbResAdvertisementEnterpriseDao tbResAdvertisementEnterpriseDao = new TbResAdvertisementEnterpriseDao(em);
		
		TbAdvertisement ta = null;
		Map<String,Object>	tMap =new HashMap<String,Object>();
		if(!StringUtils.isEmpty(id)){
			ta = taDao.findById(Integer.valueOf(id));
			if(ta!=null){
				tMap.put("title", ta.getTitle());
				tMap.put("id", ta.getId());
				tMap.put("visible", ta.getVisible());
				tMap.put("type", ta.getType());
				tMap.put("startTime", ta.getStartTime()==null?"":DateTimeUtil.formatTime(ta.getStartTime()));
				tMap.put("endTime", ta.getEndTime()==null?"":DateTimeUtil.formatTime(ta.getEndTime()));
				List<TbFile> list = tbFileDao.findByAdvertisementId(ta.getId());
				tMap.put("fileList", list);
				List<Map<String,Object>> list2 = tbResAdvertisementEnterpriseDao.findEnterpriseByAdvertisementId(ta.getId());
				tMap.put("enterpriseJson", list2);
				
			}
		}
		res.add("result", tMap);
		res.add("msg", "成功！");
		res.add("status", 1);
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
			return true;
	}
}
