package com.jinpinghu.logic.toolRecoveryRecord;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResToolRecoveryRecordFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolRecoveryRecordFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.AddOrUpdateToolRecoveryRecordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddToolRecoveryRecordFileLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateToolRecoveryRecordParam myParam = (AddOrUpdateToolRecoveryRecordParam)logicParam;
		String id = myParam.getId();
		String file = myParam.getFile();
			
		TbFileDao tfDao = new TbFileDao(em);
		TbResToolRecoveryRecordFileDao trfgDao =new TbResToolRecoveryRecordFileDao(em);
		if(!StringUtils.isEmpty(id)) {
			List<TbFile> fileList = tfDao.findByToolRecoveryRecordId(Integer.valueOf(id));
			List<TbResToolRecoveryRecordFile> resFileList = trfgDao.findByToolRecordId(Integer.valueOf(id));
			
			if(resFileList!=null){
				for(TbResToolRecoveryRecordFile trgf:resFileList){
					trfgDao.delete(trgf);
				}
			}
			if(resFileList!=null){
				for(TbFile tbFile:fileList){
					tfDao.delete(tbFile);
				}
			}
			
			if(!StringUtils.isEmpty(file)){
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
						TbResToolRecoveryRecordFile trpf=new TbResToolRecoveryRecordFile();
						trpf.setFileId(tfe.getId());
						trpf.setToolRecoveryRecordId(Integer.valueOf(id));
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
}
