package com.jinpinghu.logic.sc;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResBuyReleaseFile;
import com.jinpinghu.db.bean.TbBuyRelease;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResBuyReleaseFileDao;
import com.jinpinghu.db.dao.TbBuyReleaseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.AddBuyReleaseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddBuyReleaseLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddBuyReleaseParam myParam = (AddBuyReleaseParam)logicParam;
		TbBuyRelease paramBuyRelease = myParam.getBuyRelease();
		String fileArray = myParam.getFileArray();
		
		TbBuyReleaseDao buyReleaseDao = new TbBuyReleaseDao(em);
		
		TbBuyRelease buyRelease = buyReleaseDao.findById(paramBuyRelease.getId());
		
		if (buyRelease != null) {
			buyRelease.setEnterpriseId(paramBuyRelease.getEnterpriseId());
			buyRelease.setProductName(paramBuyRelease.getProductName());
			buyRelease.setStartTime(paramBuyRelease.getStartTime());
			buyRelease.setEndTime(paramBuyRelease.getEndTime());
			buyRelease.setNum(paramBuyRelease.getNum());
			buyRelease.setPrice(paramBuyRelease.getPrice());
			buyRelease.setType(paramBuyRelease.getType());
			buyRelease.setContactPerson(paramBuyRelease.getContactPerson());
			buyRelease.setContactPhone(paramBuyRelease.getContactPhone());
			buyRelease.setContactAddress(paramBuyRelease.getContactAddress());
			buyRelease.setDscd(paramBuyRelease.getDscd());
			buyRelease.setSpecification(paramBuyRelease.getSpecification());
			buyReleaseDao.update(buyRelease);
		}else {
			buyRelease = paramBuyRelease;
			buyRelease.setDelFlag(0);
			buyRelease.setStatus(0);
			buyReleaseDao.save(buyRelease);
		}
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResBuyReleaseFileDao trfgDao =new TbResBuyReleaseFileDao(em);
		List<TbFile> tfs =tfDao.findByBuyReleaseId(buyRelease.getId());
		List<TbResBuyReleaseFile> trgfs =trfgDao.findByBuyReleaseId(buyRelease.getId());
		if(trgfs!=null){
			for(TbResBuyReleaseFile trgf:trgfs){
				trfgDao.delete(trgf);
			}
		}
		if(tfs!=null){
			for(TbFile tbFile:tfs){
				tfDao.delete(tbFile);
			}
		}
		if(!StringUtils.isEmpty(fileArray)){
			JSONArray arrayF= JSONArray.fromObject(fileArray);
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
					TbResBuyReleaseFile trpf=new TbResBuyReleaseFile();
					trpf.setFileId(tfe.getId());
					trpf.setBuyReleaseId(buyRelease.getId());
					trfgDao.save(trpf);
				}
			}
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
