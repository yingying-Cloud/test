package com.jinpinghu.logic.toolRecovery;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResToolRecoveryFile;
import com.jinpinghu.db.bean.TbToolRecovery;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolRecoveryFileDao;
import com.jinpinghu.db.dao.TbToolRecoveryDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecovery.param.AddOrUpdateToolRecoveryParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateToolRecoveryLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateToolRecoveryParam myParam = (AddOrUpdateToolRecoveryParam)logicParam;
		String id = myParam.getId();
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String name = myParam.getName();
		String model = myParam.getModel();
		String specification = myParam.getSpecification();
		String unit=myParam.getUnit();
		String describe = myParam.getDescribe();
		Integer type=StringUtils.isEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		String file = myParam.getFile();
		String price = myParam.getPrice();
		TbToolRecovery toolRecovery = null;
		TbToolRecoveryDao toolRecoveryDao2 = new TbToolRecoveryDao(em);
		if(!StringUtils.isEmpty(id)) {
			toolRecovery = toolRecoveryDao2.findById(Integer.valueOf(id));
		}
		if(toolRecovery!=null) {
			toolRecovery.setDescribe(describe);
			toolRecovery.setEnterpriseId(enterpriseId);
			toolRecovery.setModel(model);
			toolRecovery.setName(name);
			toolRecovery.setSpecification(specification);
			toolRecovery.setUnit(unit);
			toolRecovery.setType(type);
			toolRecovery.setPrice(price);
			toolRecoveryDao2.update(toolRecovery);
		}else {
			
			toolRecovery = new TbToolRecovery();
			toolRecovery.setDelFlag(0);
			toolRecovery.setDescribe(describe);
			toolRecovery.setEnterpriseId(enterpriseId);
			toolRecovery.setModel(model);
			toolRecovery.setName(name);
			toolRecovery.setSpecification(specification);
			toolRecovery.setUnit(unit);
			toolRecovery.setType(type);
			toolRecovery.setInputTime(new Date());
			toolRecovery.setPrice(price);
			toolRecoveryDao2.save(toolRecovery);
		}
		
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResToolRecoveryFileDao trfgDao =new TbResToolRecoveryFileDao(em);
		
		List<TbFile> tfs =tfDao.findByToolRecoveryId(toolRecovery.getId());
		List<TbResToolRecoveryFile> trgfs =trfgDao.findByToolRecoveryId(toolRecovery.getId());
		if(trgfs!=null){
			for(TbResToolRecoveryFile trgf:trgfs){
				trfgDao.delete(trgf);
			}
		}
		if(tfs!=null){
			for(TbFile tbFile:tfs){
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
					TbResToolRecoveryFile trpf=new TbResToolRecoveryFile();
					trpf.setFileId(tfe.getId());
					trpf.setToolRecoveryId(toolRecovery.getId());
					trpf.setDelFlag(0);
					trfgDao.save(trpf);
				}
			}
		}
		
		
		res.add("status", 1).add("msg", "操作成功");
		
		return true;
	}
}
