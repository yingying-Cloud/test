package com.jinpinghu.logic.evaluate;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.db.bean.TbEvaluate;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPlantProtectionOrder;
import com.jinpinghu.db.bean.TbPlantServiceOrder;
import com.jinpinghu.db.bean.TbResEvaluateFile;
import com.jinpinghu.db.bean.TbScOrder;
import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEvaluateDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbPlantServiceOrderDao;
import com.jinpinghu.db.dao.TbResEvaluateFileDao;
import com.jinpinghu.db.dao.TbScOrderDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.evaluate.param.AddEvaluateParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddEvaluateLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddEvaluateParam myParam = (AddEvaluateParam)logicParam;
		String id = myParam.getId();
		String content = myParam.getContent();
		String level = myParam.getLevel();
		String userId = myParam.getUserId();
		String resId = myParam.getResId();
		String type = myParam.getType();
		String file = myParam.getFile();
		
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser user = tbUserDao.checkLogin2(userId);
		
		TbEvaluateDao tbEvaluateDao = new TbEvaluateDao(em);
		
		TbEvaluate evaluate = null;
		if(!StringUtils.isEmpty(id)) {
			evaluate = tbEvaluateDao.findById(Integer.valueOf(id));
		}
		if(evaluate!=null) {
			evaluate.setContent(content);
			evaluate.setLevel(level);
			evaluate.setName(user.getName());
			evaluate.setResId(resId);
			evaluate.setType(type);
			tbEvaluateDao.update(evaluate);
		}else {
			evaluate = new TbEvaluate();
			evaluate.setContent(content);
			evaluate.setLevel(level);
			evaluate.setName(user.getName());
			evaluate.setResId(resId);
			evaluate.setType(type);
			evaluate.setInputTime(new Date());
			evaluate.setDelFlag(0);
			tbEvaluateDao.save(evaluate);
		}
		
		if(type.equals("1")) {
			TbToolOrderDao tbToolOrderDao = new TbToolOrderDao(em);
			TbToolOrder tbToolOrder = tbToolOrderDao.findById(Integer.valueOf(resId));
			if (tbToolOrder != null) {
				tbToolOrder.setIsEvaluate("1");
				tbToolOrderDao.update(tbToolOrder);
			}
		}else if(type.equals("3")) {
			TbPlantServiceOrderDao tbPlantServiceOrderDao = new TbPlantServiceOrderDao(em);
			TbPlantServiceOrder tbPlantServiceOrder = tbPlantServiceOrderDao.findById(Integer.valueOf(resId));
			tbPlantServiceOrder.setIsEvaluate("1");
			tbPlantServiceOrderDao.update(tbPlantServiceOrder);
		}else if(type.equals("2")) {
			TbPlantProtectionOrderDao tbPlantProtectionOrderDao = new TbPlantProtectionOrderDao(em);
			TbPlantProtectionOrder tbPlantProtectionOrder = tbPlantProtectionOrderDao.findById(Integer.valueOf(resId));
			tbPlantProtectionOrder.setIsEvaluate("1");
			tbPlantProtectionOrderDao.update(tbPlantProtectionOrder);
		}else if(type.equals("4")) {
			TbScOrderDao tbScDao = new TbScOrderDao(em);
			TbScOrder tbScOrder = tbScDao.findById(Integer.valueOf(resId));
			tbScOrder.setIsEvaluate("1");
			tbScDao.update(tbScOrder);
		}else if(type.equals("5")) {
			TbScOrderDao tbScDao = new TbScOrderDao(em);
			TbScOrder tbScOrder = tbScDao.findById(Integer.valueOf(resId));
			tbScOrder.setIsEvaluate("1");
			tbScDao.update(tbScOrder);
		}
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResEvaluateFileDao tbResEvaluateFileDao = new TbResEvaluateFileDao(em);
		
		List<TbFile> tfs =tfDao.findByEnterpriseIdType(evaluate.getId());
		List<TbResEvaluateFile> trgfs =tbResEvaluateFileDao.findByEvaluateId(evaluate.getId());
		if(trgfs!=null){
			for(TbResEvaluateFile trgf:trgfs){
				tbResEvaluateFileDao.delete(trgf);
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
					TbResEvaluateFile trpf=new TbResEvaluateFile();
					trpf.setFileId(tfe.getId());
					trpf.setEvaluateId(evaluate.getId());
					tbResEvaluateFileDao.save(trpf);
				}
			}
		}
		
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
