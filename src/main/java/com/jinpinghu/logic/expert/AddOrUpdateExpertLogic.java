package com.jinpinghu.logic.expert;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResExpertFile;
import com.jinpinghu.db.bean.TbResUserEnterprise;
import com.jinpinghu.db.bean.TbResUserExpert;
import com.jinpinghu.db.bean.TbResUserRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbExpertDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResExpertFileDao;
import com.jinpinghu.db.dao.TbResUserExpertDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.expert.param.AddOrUpdateExpertParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.common.util.EncryptTool;
import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateExpertLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateExpertParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateExpertParam myParam = (AddOrUpdateExpertParam)AddOrUpdateExpertParam;
		String id = myParam.getId();
		String file = myParam.getFile();
		String address= myParam.getAddress();
		String goodsField= myParam.getGoodsField();
		String idcard= myParam.getIdcard();
		String synopsis= myParam.getSynopsis();
		String mobile=myParam.getMobile();
		String name=myParam.getName();
		String type = myParam.getType();
		String adnm = myParam.getAdnm();
		String productTeam = myParam.getProductTeam();
		
		TbResUserRoleDao tbResUserRoleDao = new TbResUserRoleDao(em);
		TbUserDao tuDao = new TbUserDao(em);
		TbExpertDao expertDao = new TbExpertDao(em);
		TbResUserExpertDao tbResUserExpertDao = new TbResUserExpertDao(em);
		TbExpert expert =null;
		TbUser tbUser=null;
		
		if(StringUtil.isNullOrEmpty(id)) {
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
			
			tbUser = tuDao.findByPhone(mobile);
			//如果用户不存在，新建
			if(tbUser==null){
				String tbUserId = ZDao.genPkId();
				String password=EncryptTool.md5("123456");
				String apiKey = _blow.encryptString(name + password+ System.currentTimeMillis()).substring(0, 100);
				tbUser = new TbUser(null,tbUserId, apiKey, mobile, password, new Date());
				tbUser.setDelFlag(0);
				tbUser.setName(name);
				tbUser.setExpertIdcard(idcard);
				tuDao.save(tbUser);
				
				TbResUserExpert tbResUserExpert = new TbResUserExpert();
				tbResUserExpert.setDelFlag(0);
				tbResUserExpert.setExpertId(expert.getId());
				tbResUserExpert.setUserTabId(tbUser.getId());
				tbResUserExpertDao.save(tbResUserExpert);
				
				TbResUserRole tbResUserRole = new TbResUserRole();
				tbResUserRole.setDelFlag(0);
				tbResUserRole.setInputTime(new Date());
				tbResUserRole.setUserTabId(tbUser.getId());
				tbResUserRole.setRoleId(6);
				tbResUserRoleDao.save(tbResUserRole);
				
				
			}else{
				TbResUserExpert tbResUserExpert = new TbResUserExpert();
				tbResUserExpert.setDelFlag(0);
				tbResUserExpert.setExpertId(expert.getId());
				tbResUserExpert.setUserTabId(tbUser.getId());
				tbResUserExpertDao.save(tbResUserExpert);
			}
			
		}else {
			expert = expertDao.findById(Integer.valueOf(id));
			
			tbUser = tbResUserExpertDao.getUserByExpertId(expert.getId());
			tbUser.setExpertIdcard(idcard);
			tbUser.setName(name);
			tbUser.setMobile(mobile);
			tuDao.update(tbUser);
			expert.setType(type);
			expert.setCost(expertDao.findCost(type));
			expert.setGoodsField(goodsField);
			expert.setAddress(address);
			expert.setIdcard(idcard);
			expert.setSynopsis(synopsis);
			expert.setAdnm(adnm);
			expert.setProductTeam(productTeam);
			expertDao.update(expert);
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
		
		
		res.add("status", 1).add("msg","操作成功").add("id", expert.getId()).add("user_tab_id",tbUser.getId() );
		return true;
	}
}
