package com.jinpinghu.logic.enterpriseCertificate;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterpriseCertificate;
import com.jinpinghu.db.dao.TbAreaDao;
import com.jinpinghu.db.dao.TbEnterpriseCertificateDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterpriseCertificate.param.GetEnterpriseCertificateInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class GetEnterpriseCertificateInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseCertificateInfoParam myParam = (GetEnterpriseCertificateInfoParam)logicParam;
		Integer id =StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbEnterpriseCertificateDao enterpriseCertificateDao = new TbEnterpriseCertificateDao(em);
		TbEnterpriseCertificate enterpriseCertificate = null;
		if(id!=null) {
			enterpriseCertificate = enterpriseCertificateDao.findById(id);
		}
		TbFileDao tfDao = new TbFileDao(em);
		JSONObject jo = new JSONObject();
		if(enterpriseCertificate!=null) {
			jo.put("completeTime",enterpriseCertificate.getCompleteTime()==null?null:DateTimeUtil.formatTime(enterpriseCertificate.getCompleteTime()));
			jo.put("inputTime",enterpriseCertificate.getInputTime()==null?null:DateTimeUtil.formatTime(enterpriseCertificate.getInputTime()));
			jo.put("publishTime",enterpriseCertificate.getPublishTime()==null?null:DateTimeUtil.formatTime(enterpriseCertificate.getPublishTime()));
			jo.put("authority",enterpriseCertificate.getAuthority());
			jo.put("owner",enterpriseCertificate.getOwner());
			jo.put("registrationNumber",enterpriseCertificate.getRegistrationNumber());
			jo.put("softwareName",enterpriseCertificate.getSoftwareName());
			jo.put("way",enterpriseCertificate.getWay());
			jo.put("id",enterpriseCertificate.getId());
			jo.put("certificateType",enterpriseCertificate.getCertificateType());
			List<Map<String, Object>> tfs =tfDao.findByEnterpriseCertificateMap(enterpriseCertificate.getId());
			jo.put("file", tfs);
		}
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", jo);
		return true;
	}
}
