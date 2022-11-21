package com.jinpinghu.logic.tool;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResToolFile;
import com.jinpinghu.db.bean.TbResToolRecoveryRecordFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.db.dao.TbResToolRecoveryRecordFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.AddOrUpdateToolParam;
import com.jinpinghu.logic.toolRecoveryRecord.param.AddOrUpdateToolRecoveryRecordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddToolFileLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateToolParam myParam = (AddOrUpdateToolParam)logicParam;
		String id = myParam.getId();
		String file = myParam.getFile();
			
		TbFileDao tfDao = new TbFileDao(em);
		TbResToolFileDao trfgDao =new TbResToolFileDao(em);
		if(!StringUtils.isEmpty(id)) {
			
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
						TbResToolFile trpf=new TbResToolFile();
						trpf.setFileId(tfe.getId());
						trpf.setToolId(Integer.valueOf(id));
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
