package com.jinpinghu.logic.pests;

import java.util.List;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPests;
import com.jinpinghu.db.bean.TbResAgriculturalClassroomFile;
import com.jinpinghu.db.bean.TbResPestsFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPestsDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.pests.param.AddPestsParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddPestsLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddPestsParam myParam = (AddPestsParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		String type = myParam.getType();
		String title = myParam.getTitle();
		String features = myParam.getFeatures();
		String happen = myParam.getHappen();
		String agriculturalControl = myParam.getAgriculturalControl();
		String greenControl = myParam.getGreenControl();
		String organicControl = myParam.getOrganicControl();
		String allControl = myParam.getAllControl();
		String greenControlMedicine = myParam.getGreenControlMedicine();
		String organicControlMedicine = myParam.getOrganicControlMedicine();
		String allControlMedicine = myParam.getAllControlMedicine();
		String file = myParam.getFile();
		TbPestsDao pestsDao = new TbPestsDao(em);
		TbPests pests = null;
		if(id!=null) {
			pests = pestsDao.findById(id);
		}
		if(pests!=null) {
			pests.setTitle(title);
			pests.setType(type);
			pests.setAgriculturalControl(agriculturalControl);
			pests.setAllControl(allControl);
			pests.setAllControlMedicine(allControlMedicine);
			pests.setFeatures(features);
			pests.setGreenControl(greenControl);
			pests.setGreenControlMedicine(greenControlMedicine);
			pests.setHappen(happen);
			pests.setOrganicControl(organicControl);
			pests.setOrganicControlMedicine(organicControlMedicine);
			pestsDao.update(pests);
		}else {
			pests = new TbPests();
			pests.setTitle(title);
			pests.setType(type);
			pests.setAgriculturalControl(agriculturalControl);
			pests.setAllControl(allControl);
			pests.setAllControlMedicine(allControlMedicine);
			pests.setFeatures(features);
			pests.setGreenControl(greenControl);
			pests.setGreenControlMedicine(greenControlMedicine);
			pests.setHappen(happen);
			pests.setOrganicControl(organicControl);
			pests.setOrganicControlMedicine(organicControlMedicine);
			pestsDao.save(pests);
		}
		TbFileDao tfDao = new TbFileDao(em);
		pestsDao.DelFileByPestsId(pests.getId());
		List<TbFile> files = tfDao.findByPestsId(pests.getId());
		if(files!=null) {
			for(TbFile tf:files) {
				tfDao.delete(tf);
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
					TbResPestsFile trpf=new TbResPestsFile();
					trpf.setFileId(tfe.getId());
					trpf.setPestsId(pests.getId());
					tfDao.save(trpf);
				}
			}
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
