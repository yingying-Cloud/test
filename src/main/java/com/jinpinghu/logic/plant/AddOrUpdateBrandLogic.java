package com.jinpinghu.logic.plant;

import java.util.Date;
import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResBrandFile;
import com.jinpinghu.db.bean.TbResCropFarmingFile;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResBrandFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.AddOrUpdateBrandParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateBrandLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateBrandParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateBrandParam myParam=(AddOrUpdateBrandParam)AddOrUpdateBrandParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		String productName=myParam.getProductName();
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String model=myParam.getModel();
		String registeredTrademark=myParam.getRegisteredTrademark();
		String authorizationCategory=myParam.getAuthorizationCategory();
		String json =myParam.getJson();
		Integer type = StringUtil.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		String price = myParam.getPrice();
		String describe = myParam.getDescribe();
		String spec = myParam.getSpec();
		String unit = myParam.getUnit();
		Integer status = StringUtils.isNullOrEmpty(myParam.getStatus())?0:Integer.valueOf(myParam.getStatus());
//		Integer upStatus = StringUtils.isNullOrEmpty(myParam.getUpStatus())?0:Integer.valueOf(myParam.getUpStatus());


		TbBrand tbBrand=new TbBrand();
		TbBrandDao tbBrandDao=new TbBrandDao(em);
		TbResBrandFileDao tbResBrandFileDao=new TbResBrandFileDao(em);
		TbResBrandFile 	tbResBrandFile=null;
		TbFile tbFile=null;
		TbFileDao tbFileDao = new TbFileDao(em);
		
		if(id==null){
			tbBrand.setProductName(productName);
			tbBrand.setModel(model);
			tbBrand.setRegisteredTrademark(registeredTrademark);
			tbBrand.setAuthorizationCategory(authorizationCategory);
			tbBrand.setEnterpriseId(enterpriseId);
			tbBrand.setStatus(status);
			tbBrand.setDelFlag(0);
			tbBrand.setInputTime(new Date());
			tbBrand.setType(type);
			tbBrand.setDescribe(describe);
			tbBrand.setPrice(price);
			tbBrand.setSpec(spec);
			tbBrand.setUnit(unit);
//			tbBrand.setUpStatus(upStatus);
			tbBrandDao.save(tbBrand);
			
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResBrandFile=new	TbResBrandFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResBrandFile.setBrandId(tbBrand.getId());
						tbResBrandFile.setFileId(tbFile.getId());
						tbResBrandFile.setDelFlag(0);
						tbResBrandFileDao.save(tbResBrandFile);
					}
				}
			}
			
		}else{
			tbBrand=tbBrandDao.findById(id);
			tbBrand.setProductName(productName);
			tbBrand.setModel(model);
			tbBrand.setRegisteredTrademark(registeredTrademark);
			tbBrand.setEnterpriseId(enterpriseId);
			tbBrand.setType(type);
			tbBrand.setAuthorizationCategory(authorizationCategory);
			tbBrand.setDescribe(describe);
			tbBrand.setPrice(price);
			tbBrand.setSpec(spec);
			tbBrand.setUnit(unit);
			tbBrand.setStatus(status);
//			tbBrand.setUpStatus(upStatus);
			tbBrandDao.update(tbBrand);

			tbResBrandFileDao.DelFileByBrandId(id);
			if(!StringUtil.isNullOrEmpty(json)){
				JSONArray ja=JSONArray.fromObject(json);
				if(ja!=null){
					JSONObject jo=null;
					for(int i=0;i<ja.size();i++){
						jo=ja.getJSONObject(i);
						tbFile=new TbFile();
						tbResBrandFile=new	TbResBrandFile();
						
						if(jo.containsKey("fileSize"))
							tbFile.setFileSize(jo.getString("fileSize"));
						if(jo.containsKey("fileName"))
							tbFile.setFileName(jo.getString("fileName"));
						tbFile.setFileType(Integer.valueOf(jo.getString("fileType")));
						tbFile.setFileUrl(jo.getString("fileUrl"));
						tbFileDao.save(tbFile);
						tbResBrandFile.setBrandId(tbBrand.getId());
						tbResBrandFile.setFileId(tbFile.getId());
						tbResBrandFile.setDelFlag(0);
						tbResBrandFileDao.save(tbResBrandFile);
					}
				}
			}
		}	
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
