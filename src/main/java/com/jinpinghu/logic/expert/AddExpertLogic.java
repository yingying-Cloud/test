package com.jinpinghu.logic.expert;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResExpertFile;
import com.jinpinghu.db.bean.TbResUserExpert;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbExpertDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResExpertFileDao;
import com.jinpinghu.db.dao.TbResUserExpertDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.expert.param.AddExpertParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddExpertLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddExpertParam myParam = (AddExpertParam)logicParam;
		String id = myParam.getId();
		String file = myParam.getFile();
		String address= myParam.getAddress();
		String goodsField= myParam.getGoodsField();
		String idcard= myParam.getIdcard();
		String synopsis= myParam.getSynopsis();
		String type = myParam.getType();
		String cost = myParam.getCost();
		String adnm = myParam.getAdnm();
		String productTeam = myParam.getProductTeam();
		
		
		String userId = myParam.getUserId();
		TbUserDao tuDao = new TbUserDao(em);
		TbUser user = tuDao.checkLogin2(userId);
		TbExpertDao expertDao = new TbExpertDao(em);
		TbResUserExpertDao tbResUserExpertDao = new TbResUserExpertDao(em);
		TbExpert expert =null;
		if(!StringUtils.isNullOrEmpty(id)) {
			expert=expertDao.findById(Integer.valueOf(id));
		}else {
			expert = tbResUserExpertDao.findByUserTabId(user.getId());
		}
		if(expert==null) {
			expert = new TbExpert();
			expert.setDelFlag(0);
			expert.setInputTime(new Date());
			expert.setGoodsField(goodsField);
			expert.setAddress(address);
			expert.setIdcard(idcard);
			expert.setStatus(0+"");
			expert.setSynopsis(synopsis);
			expert.setType(type);
			expert.setCost(expertDao.findCost(type));
			expert.setAdnm(adnm);
			expert.setProductTeam(productTeam);
			expertDao.save(expert);
		}else {
			expert.setGoodsField(goodsField);
			expert.setAddress(address);
			expert.setIdcard(idcard);
			expert.setSynopsis(synopsis);
			expert.setType(type);
			expert.setCost(expertDao.findCost(type));
			expert.setAdnm(adnm);
			expert.setProductTeam(productTeam);
			expertDao.update(expert);
		}
		
		TbResUserExpert checkIsExist = tbResUserExpertDao.checkIsExist(user.getId(), expert.getId());
		if(checkIsExist==null) {
			TbResUserExpert tbResUserEnterprice = new TbResUserExpert();
			tbResUserEnterprice.setDelFlag(0);
			tbResUserEnterprice.setExpertId(expert.getId());
			tbResUserEnterprice.setUserTabId(user.getId());
			tbResUserExpertDao.save(tbResUserEnterprice);
		}
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResExpertFileDao trfgDao =new TbResExpertFileDao(em);
		
		List<TbFile> tfs =tfDao.findByExpertId(expert.getId());
		List<TbResExpertFile> trgfs =trfgDao.findByExpertId(expert.getId());
		if(trgfs!=null){
			for(TbResExpertFile trgf:trgfs){
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
					TbResExpertFile trpf=new TbResExpertFile();
					trpf.setFileId(tfe.getId());
					trpf.setExpertId(expert.getId());
					if(jsonObj.containsKey("type")) {
						trpf.setType(jsonObj.getInt("type"));
					}
					trpf.setDelFlag(0);
					trfgDao.save(trpf);
				}
			}
		}
		
		
		res.add("status", 1).add("msg","操作成功").add("id", expert.getId()).add("name", user.getName());
		return true;
	}
}
