package com.jinpinghu.logic.plant;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbEnterpriseUserinfo;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResEnterpriseUserinfoFile;
import com.jinpinghu.db.dao.TbEnterpriseUserinfoDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResEnterpriseUserinfoFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.AddOrUpdateEnterpriseUserinfoParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateEnterpriseUserinfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateEnterpriseUserinfoParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateEnterpriseUserinfoParam myParam=(AddOrUpdateEnterpriseUserinfoParam)AddOrUpdateEnterpriseUserinfoParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());;
		String name=myParam.getName();
		String idCard=myParam.getIdCard();
		String mobile=myParam.getMobile();
		String type=myParam.getType();
		String address=myParam.getAddress();
		String sex=myParam.getSex();
		String json=myParam.getJson();
		String major = myParam.getMajor();
		String education = myParam.getEducation();
		String title = myParam.getTitle();
		String company = myParam.getCompany();
		
		TbResEnterpriseUserinfoFile tbResEnterpriseUserinfoFile=null;
		TbResEnterpriseUserinfoFileDao tbResEnterpriseUserinfoFileDao=new TbResEnterpriseUserinfoFileDao(em);
		TbEnterpriseUserinfoDao tbEnterpriseUserinfoDao=new TbEnterpriseUserinfoDao(em);
		TbEnterpriseUserinfo tbEnterpriseUserinfo=new TbEnterpriseUserinfo();
		TbFile tbFile=null;
		TbFileDao tbFileDao=new TbFileDao(em);
		
		
		if(id==null){
			tbEnterpriseUserinfo.setDelFlag(0);
			tbEnterpriseUserinfo.setName(name);
			tbEnterpriseUserinfo.setEnterpriseId(enterpriseId);
			tbEnterpriseUserinfo.setInputTime(new Date());
			tbEnterpriseUserinfo.setIdCard(idCard);
			tbEnterpriseUserinfo.setMobile(mobile);
			tbEnterpriseUserinfo.setType(type);
			tbEnterpriseUserinfo.setAddress(address);
			tbEnterpriseUserinfo.setSex(sex);
			tbEnterpriseUserinfo.setMajor(major);
			tbEnterpriseUserinfo.setEducation(education);
			tbEnterpriseUserinfo.setCompany(company);
			tbEnterpriseUserinfo.setTitle(title);
			tbEnterpriseUserinfoDao.save(tbEnterpriseUserinfo);
			
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResEnterpriseUserinfoFile=new TbResEnterpriseUserinfoFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResEnterpriseUserinfoFile.setEnterpriseUserinfoId(tbEnterpriseUserinfo.getId());
						tbResEnterpriseUserinfoFile.setFileId(tbFile.getId());
						tbResEnterpriseUserinfoFile.setDelFlag(0);
						tbResEnterpriseUserinfoFileDao.save(tbResEnterpriseUserinfoFile);
					}
				}
			}
		}else{
			tbEnterpriseUserinfo=tbEnterpriseUserinfoDao.findById(id);
			tbEnterpriseUserinfo.setName(name);
			tbEnterpriseUserinfo.setEnterpriseId(enterpriseId);
			tbEnterpriseUserinfo.setIdCard(idCard);
			tbEnterpriseUserinfo.setMobile(mobile);
			tbEnterpriseUserinfo.setType(type);
			tbEnterpriseUserinfo.setAddress(address);
			tbEnterpriseUserinfo.setSex(sex);
			tbEnterpriseUserinfo.setMajor(major);
			tbEnterpriseUserinfo.setEducation(education);
			tbEnterpriseUserinfo.setCompany(company);
			tbEnterpriseUserinfo.setTitle(title);
			tbEnterpriseUserinfoDao.update(tbEnterpriseUserinfo);

			tbResEnterpriseUserinfoFileDao.DelResByEnterpriseUserinfoId(id);
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResEnterpriseUserinfoFile=new TbResEnterpriseUserinfoFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResEnterpriseUserinfoFile.setEnterpriseUserinfoId(tbEnterpriseUserinfo.getId());
						tbResEnterpriseUserinfoFile.setFileId(tbFile.getId());
						tbResEnterpriseUserinfoFile.setDelFlag(0);
						tbResEnterpriseUserinfoFileDao.save(tbResEnterpriseUserinfoFile);
					}
				}
			}
		}
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
