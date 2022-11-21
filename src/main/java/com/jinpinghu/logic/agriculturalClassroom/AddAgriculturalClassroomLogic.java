package com.jinpinghu.logic.agriculturalClassroom;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.db.bean.TbAgriculturalClassroom;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResAgriculturalClassroomFile;
import com.jinpinghu.db.dao.TbAgriculturalClassroomDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalClassroom.param.AddAgriculturalClassroomParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddAgriculturalClassroomLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddAgriculturalClassroomParam myParam = (AddAgriculturalClassroomParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		String type = myParam.getType();
		String title = myParam.getTitle();
		String file = myParam.getFile();
		
		TbAgriculturalClassroomDao tbagriculturalClassroomDao = new TbAgriculturalClassroomDao(em);
		TbAgriculturalClassroom agriculturalClassroom = null;
		if(id!=null) {
			agriculturalClassroom = tbagriculturalClassroomDao.findById(id);
		}
		if(agriculturalClassroom!=null) {
			agriculturalClassroom.setTitle(title);
			agriculturalClassroom.setType(type);
			tbagriculturalClassroomDao.update(agriculturalClassroom);
		}else {
			agriculturalClassroom = new TbAgriculturalClassroom();
			agriculturalClassroom.setInputTime(new Date());
			agriculturalClassroom.setTitle(title);
			agriculturalClassroom.setType(type);
			tbagriculturalClassroomDao.save(agriculturalClassroom);
		}
		TbFileDao tfDao = new TbFileDao(em);
		tbagriculturalClassroomDao.DelFileByAgriculturalId(agriculturalClassroom.getId());
		List<TbFile> files = tfDao.findByAgriculturalClassroomId(agriculturalClassroom.getId());
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
					TbResAgriculturalClassroomFile trpf=new TbResAgriculturalClassroomFile();
					trpf.setFileId(tfe.getId());
					trpf.setAgriculturalClassroomId(agriculturalClassroom.getId());
					tfDao.save(trpf);
				}
			}
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
