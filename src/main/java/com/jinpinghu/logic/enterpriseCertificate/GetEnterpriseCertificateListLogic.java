package com.jinpinghu.logic.enterpriseCertificate;

import com.jinpinghu.db.dao.TbEnterpriseCertificateDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterpriseCertificate.param.GetEnterpriseCertificateListParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GetEnterpriseCertificateListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseCertificateListParam myParam = (GetEnterpriseCertificateListParam)logicParam;
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String name = myParam.getName();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		List<String> statusList = null;
		TbEnterpriseCertificateDao enterpriseCertificateDao = new TbEnterpriseCertificateDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Integer count = enterpriseCertificateDao.findByAllCount(name,startTime,endTime,enterpriseId);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = enterpriseCertificateDao.findByAll(name,startTime,endTime,nowPage,pageCount,enterpriseId);
		if(list!=null) {
			for(Map<String,Object> map:list) {
				List<Map<String, Object>> tfs =tfDao.findByEnterpriseCertificateMap(Integer.valueOf(map.get("id").toString()));
				map.put("file", tfs);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", list).add("allCounts", count);
		return true;
	}
}
