package com.jinpinghu.logic.facedata;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.db.bean.TbFaceData;
import com.jinpinghu.db.dao.TbFaceDataDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.facedata.param.DetailFaceDataParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DetailFaceDataLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DetailFaceDataParam myParam = (DetailFaceDataParam)logicParam;
		String idcard = myParam.getIdcard();
		
		TbFaceDataDao faceDataDao = new TbFaceDataDao(em);
		
		TbFaceData faceDataEntity = faceDataDao.findByIdcard(idcard);
		
		Map<String, String> resultMap = new HashMap<String, String>(4);
		resultMap.put("name","");
		resultMap.put("idcard","");
		resultMap.put("facePic","");
		resultMap.put("faceData","");
		if (faceDataEntity != null) {
			resultMap.put("name",StringUtils.trimToEmpty(faceDataEntity.getName()));
			resultMap.put("idcard",StringUtils.trimToEmpty(faceDataEntity.getIdcard()));
			resultMap.put("facePic",StringUtils.trimToEmpty(faceDataEntity.getFacePic()));
			resultMap.put("faceData",StringUtils.trimToEmpty(faceDataEntity.getFaceData()));
		}
		res.add("status", 1).add("msg", "操作成功").add("result", resultMap);
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DetailFaceDataParam myParam = (DetailFaceDataParam)logicParam;
		String idcard = myParam.getIdcard();
		
		if (StringUtils.isEmpty(idcard)) {
			res.add("status", -1).add("msg", "身份证号码不能为空");
			return false;
		}
		return true;
	}

}
