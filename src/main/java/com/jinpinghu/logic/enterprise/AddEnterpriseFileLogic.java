package com.jinpinghu.logic.enterprise;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.AddEnterpriseFileParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddEnterpriseFileLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddEnterpriseFileParam myParam = (AddEnterpriseFileParam)logicParam;
		String id = myParam.getId();
		String file = myParam.getFile();
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbEnterprise enterprise = null;
		if(!StringUtils.isNullOrEmpty(id)) {
			enterprise=enterpriseDao.findById(Integer.valueOf(id));
			
			TbFileDao tfDao = new TbFileDao(em);
			TbResToolFileDao trfgDao =new TbResToolFileDao(em);
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
						TbResEnterpriseFile trpf=new TbResEnterpriseFile();
						trpf.setFileId(tfe.getId());
						trpf.setEnterpriseId(enterprise.getId());
						if(jsonObj.containsKey("type")) {
							trpf.setType(jsonObj.getInt("type"));
						}
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}


	/*@Override
	public boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
							EntityManager em)  throws Exception {

		return true;
	}*/
}
