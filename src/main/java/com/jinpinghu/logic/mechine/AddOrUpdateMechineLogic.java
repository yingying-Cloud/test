package com.jinpinghu.logic.mechine;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbMechine;
import com.jinpinghu.db.bean.TbResMechineFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbMechineDao;
import com.jinpinghu.db.dao.TbResMechineFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.mechine.param.AddOrUpdateMechineParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateMechineLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateMechineParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateMechineParam myParam=(AddOrUpdateMechineParam)AddOrUpdateMechineParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String model=myParam.getModel();
		String name=myParam.getName();
		String describe=myParam.getDescribe();
		String brand=myParam.getBrand();
		String type=myParam.getType();
		String json =myParam.getJson();
		
		TbMechine tbMechine=new TbMechine();
		TbMechineDao tbMechineDao=new TbMechineDao(em);
		TbResMechineFile tbResMechineFile=null;
		TbResMechineFileDao tbResMechineFileDao=new TbResMechineFileDao(em);
		TbFile tbFile=null;
		TbFileDao tbFileDao = new TbFileDao(em);
		
		if(id==null){
			tbMechine.setName(name);
			tbMechine.setModel(model);
			tbMechine.setDescribe(describe);
			tbMechine.setBrand(brand);
			tbMechine.setEnterpriseId(enterpriseId);
			tbMechine.setType(type);
			tbMechine.setDelFlag(0);
			tbMechine.setInputTime(new Date());
			tbMechineDao.save(tbMechine);
			
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResMechineFile=new TbResMechineFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResMechineFile.setMechineId(tbMechine.getId());
						tbResMechineFile.setFileId(tbFile.getId());
						tbResMechineFile.setDelFlag(0);
						tbResMechineFileDao.save(tbResMechineFile);
					}
				}
			}
			
		}else{
			tbMechine=tbMechineDao.findById(id);
			tbMechine.setName(name);
			tbMechine.setModel(model);
			tbMechine.setDescribe(describe);
			tbMechine.setBrand(brand);
			tbMechine.setEnterpriseId(enterpriseId);
			tbMechine.setType(type);
			tbMechineDao.update(tbMechine);

			tbResMechineFileDao.DelFileByMechineId(id);
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResMechineFile=new TbResMechineFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResMechineFile.setMechineId(tbMechine.getId());
						tbResMechineFile.setFileId(tbFile.getId());
						tbResMechineFile.setDelFlag(0);
						tbResMechineFileDao.save(tbResMechineFile);
					}
				}
			}
		}	
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
