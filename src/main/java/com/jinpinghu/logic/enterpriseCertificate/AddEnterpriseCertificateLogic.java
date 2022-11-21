package com.jinpinghu.logic.enterpriseCertificate;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterpriseCertificate;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResEnterpriseCertificateFile;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseCertificateDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResEnterpriseCertificateFileDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterpriseCertificate.param.AddEnterpriseCertificateParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class AddEnterpriseCertificateLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddEnterpriseCertificateParam myParam = (AddEnterpriseCertificateParam)logicParam;
		String id = myParam.getId();
		Integer enterpriseId =StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null: Integer.valueOf(myParam.getEnterpriseId());
		String authority = myParam.getAuthority();
		Date completeTime = StringUtils.isNullOrEmpty(myParam.getCompleteTime())?null: DateTimeUtil.formatTime(myParam.getCompleteTime());
		Date inputTime = StringUtils.isNullOrEmpty(myParam.getInputTime())?null: DateTimeUtil.formatTime(myParam.getInputTime());
		String owner = myParam.getOwner();
		Date publishTime = StringUtils.isNullOrEmpty(myParam.getPublishTime())?null: DateTimeUtil.formatTime(myParam.getPublishTime());
		String registrationNumber = myParam.getRegistrationNumber();
		String softwareName = myParam.getSoftwareName();
		String way = myParam.getWay();
		String file = myParam.getFile();
		String certifcateType = myParam.getCertificateType();

		String userId = myParam.getUserId();
		TbUserDao tuDao = new TbUserDao(em);
		TbUser user = tuDao.checkLogin2(userId);
		TbEnterpriseCertificateDao enterpriseCertificateDao = new TbEnterpriseCertificateDao(em);
		TbEnterpriseCertificate enterpriseCertificate =null;
		/*��֤�Ƿ���ڸù�˾*/
		if(!StringUtils.isNullOrEmpty(id)) {
			enterpriseCertificate=enterpriseCertificateDao.findById(Integer.valueOf(id));
		}
			
		if(enterpriseCertificate==null) {
			enterpriseCertificate= new TbEnterpriseCertificate();
			enterpriseCertificate.setDelFlag(0);
			enterpriseCertificate.setEnterpriseId(enterpriseId);
			enterpriseCertificate.setAuthority(authority);
			enterpriseCertificate.setCompleteTime(completeTime);
			enterpriseCertificate.setInputTime(inputTime);
			enterpriseCertificate.setOwner(owner);
			enterpriseCertificate.setPublishTime(publishTime);
			enterpriseCertificate.setRegistrationNumber(registrationNumber);
			enterpriseCertificate.setSoftwareName(softwareName);
			enterpriseCertificate.setWay(way);
			enterpriseCertificate.setCertificateType(certifcateType);
			enterpriseCertificateDao.save(enterpriseCertificate);
		}else {
			enterpriseCertificate.setEnterpriseId(enterpriseId);
			enterpriseCertificate.setAuthority(authority);
			enterpriseCertificate.setCompleteTime(completeTime);
			enterpriseCertificate.setInputTime(inputTime);
			enterpriseCertificate.setOwner(owner);
			enterpriseCertificate.setPublishTime(publishTime);
			enterpriseCertificate.setRegistrationNumber(registrationNumber);
			enterpriseCertificate.setSoftwareName(softwareName);
			enterpriseCertificate.setWay(way);
			enterpriseCertificate.setCertificateType(certifcateType);
			enterpriseCertificateDao.update(enterpriseCertificate);
		}

		TbFileDao tfDao = new TbFileDao(em);
		TbResEnterpriseCertificateFileDao trfgDao =new TbResEnterpriseCertificateFileDao(em);
		
		List<TbFile> tfs =tfDao.findByEnterpriseCertificateId(enterpriseCertificate.getId());
		List<TbResEnterpriseCertificateFile> trgfs =trfgDao.findByEnterpriseCertificateId(enterpriseCertificate.getId());
		if(trgfs!=null){
			for(TbResEnterpriseCertificateFile trgf:trgfs){
				trfgDao.delete(trgf);
			}
		}
		if(tfs!=null){
			for(TbFile tbFile:tfs){
				tfDao.delete(tbFile);
			}
		}
		
		if(!StringUtils.isNullOrEmpty(file)){
			JSONArray arrayF= JSONArray.fromObject(file);
			if(arrayF.size()>0){
				for(int i=0;i<arrayF.size();i++){
					TbFile tfe =null;
					JSONObject jsonObj=(JSONObject) arrayF.get(i);
					tfe = new TbFile();
					if(jsonObj.containsKey("fileName"))
						tfe.setFileName(jsonObj.getString("fileName"));
					if(jsonObj.containsKey("fileSize"))
						tfe.setFileSize(jsonObj.getString("fileSize"));
					if(jsonObj.containsKey("fileType"))
						tfe.setFileType(jsonObj.getInt("fileType"));
					if(jsonObj.containsKey("fileUrl"))
						tfe.setFileUrl(jsonObj.getString("fileUrl"));
					tfDao.save(tfe);
					TbResEnterpriseCertificateFile trpf=new TbResEnterpriseCertificateFile();
					trpf.setFileId(tfe.getId());
					trpf.setEnterpriseCertificateId(enterpriseCertificate.getId());
					trpf.setDelFlag(0);
					trfgDao.save(trpf);
				}
			}
		}
		
		
		res.add("status", 1).add("msg","操作成功");
		return true;
	}
}
