package com.jinpinghu.logic.facedata;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.db.bean.TbFaceData;
import com.jinpinghu.db.dao.TbFaceDataDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.facedata.param.AddFaceDataParam;
import com.jinpinghu.logic.facedata.param.DetailFaceDataParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddFaceDataLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddFaceDataParam myParam = (AddFaceDataParam)logicParam;
		String idcard = myParam.getIdcard();
		String faceData = myParam.getFaceData();
		String facePic = myParam.getFacePic();
		String name = myParam.getName();
		
		TbFaceDataDao faceDataDao = new TbFaceDataDao(em);
		
		TbFaceData faceDataEntity = faceDataDao.findByIdcard(idcard);
		
		if (faceDataEntity != null) {
			if (!StringUtils.isEmpty(faceData)) {
				faceDataEntity.setFaceData(faceData);
			}
			if (!StringUtils.isEmpty(name)) {
				faceDataEntity.setName(name);
			}
			if (!StringUtils.isEmpty(facePic)) {
				faceDataEntity.setFacePic(facePic);
			}
			faceDataDao.update(faceDataEntity);
		}else {
			faceDataEntity = new TbFaceData();
			if (!StringUtils.isEmpty(faceData)) {
				faceDataEntity.setFaceData(faceData);
			}
			if (!StringUtils.isEmpty(name)) {
				faceDataEntity.setName(name);
			}
			if (!StringUtils.isEmpty(facePic)) {
				faceDataEntity.setFacePic(facePic);
			}
			faceDataEntity.setIdcard(idcard);
			faceDataDao.update(faceDataEntity);
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddFaceDataParam myParam = (AddFaceDataParam)logicParam;
		String idcard = myParam.getIdcard();
		
		if (StringUtils.isEmpty(idcard)) {
			res.add("status", -1).add("msg", "身份证号码不能为空");
			return false;
		}
		return true;
	}

}
