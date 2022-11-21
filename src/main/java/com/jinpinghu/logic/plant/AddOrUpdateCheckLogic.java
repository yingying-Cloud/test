package com.jinpinghu.logic.plant;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbWorkCheck;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResWorkCheckFile;
import com.jinpinghu.db.dao.TbWorkCheckDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResWorkCheckFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.AddOrUpdateCheckParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateCheckLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateCheckParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateCheckParam myParam=(AddOrUpdateCheckParam)AddOrUpdateCheckParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Integer workId=StringUtil.isNullOrEmpty(myParam.getWorkId())?null:Integer.valueOf(myParam.getWorkId());
		Date checkTime=DateTimeUtil.formatTime(myParam.getCheckTime());
		String people=myParam.getPeople();
		String unit=myParam.getUnit();
		String content=myParam.getContent();
		Date inputTime=StringUtil.isNullOrEmpty(myParam.getInputTime())?new Date():DateTimeUtil.formatTime(myParam.getInputTime());
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String json=myParam.getJson();
		

		TbResWorkCheckFile tbResWorkCheckFile=null;
		TbResWorkCheckFileDao tbResWorkCheckFileDao=new TbResWorkCheckFileDao(em);
		TbWorkCheckDao tbWorkCheckDao=new TbWorkCheckDao(em);
		TbWorkCheck tbWorkCheck=new TbWorkCheck();
		TbFile tbFile=null;
		TbFileDao tbFileDao=new TbFileDao(em);
		
		
		if(id==null){
			tbWorkCheck.setDelFlag(0);
			tbWorkCheck.setEnterpriseId(enterpriseId);
			tbWorkCheck.setWorkId(workId);
			tbWorkCheck.setCheckTime(checkTime);
			tbWorkCheck.setInputTime(inputTime);
			tbWorkCheck.setPeople(people);
			tbWorkCheck.setContent(content);
			tbWorkCheck.setUnit(unit);
			tbWorkCheckDao.save(tbWorkCheck);
			
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResWorkCheckFile=new TbResWorkCheckFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResWorkCheckFile.setWorkCheckId(tbWorkCheck.getId());
						tbResWorkCheckFile.setFileId(tbFile.getId());
						tbResWorkCheckFile.setDelFlag(0);
						tbResWorkCheckFileDao.save(tbResWorkCheckFile);
					}
				}
			}
		}else{
			tbWorkCheck=tbWorkCheckDao.findById(id);
			tbWorkCheck.setEnterpriseId(enterpriseId);
			tbWorkCheck.setWorkId(workId);
			tbWorkCheck.setCheckTime(checkTime);
			tbWorkCheck.setInputTime(inputTime);
			tbWorkCheck.setPeople(people);
			tbWorkCheck.setContent(content);
			tbWorkCheck.setUnit(unit);
			tbWorkCheckDao.update(tbWorkCheck);

			tbResWorkCheckFileDao.DelResByCheckId(id);
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResWorkCheckFile=new TbResWorkCheckFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResWorkCheckFile.setWorkCheckId(tbWorkCheck.getId());
						tbResWorkCheckFile.setFileId(tbFile.getId());
						tbResWorkCheckFile.setDelFlag(0);
						tbResWorkCheckFileDao.save(tbResWorkCheckFile);
					}
				}
			}
		}
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
