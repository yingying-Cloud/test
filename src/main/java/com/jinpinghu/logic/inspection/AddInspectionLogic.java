package com.jinpinghu.logic.inspection;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.tools.ant.util.DateUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.DateUtil;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbInspection;
import com.jinpinghu.db.dao.TbInspectionDao;
import com.jinpinghu.db.dao.TbInspectionPoint;
import com.jinpinghu.db.dao.TbInspectionPointDao;
import com.jinpinghu.db.dao.TbInspectionType;
import com.jinpinghu.db.dao.TbInspectionTypeDao;
import com.jinpinghu.db.dao.TbResInspectionFile;
import com.jinpinghu.db.dao.TbResInspectionFileDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddInspectionLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		String remark = param.getRemark();
		Integer enterpriseId = param.getEnterpriseId();
		Integer itemId = param.getItemId();
		String file = param.getFile();
		String time = param.getTime();
		String hms = DateTimeUtil.formatSelf(new Date(), "HH:mm:ss");
		Date recordTime = DateTimeUtil.formatTime2(time + " " + hms);
		Integer type = null;
		BigDecimal point = null;

		TbInspectionTypeDao titDao = new TbInspectionTypeDao(em);
		TbInspectionType tbInspectionType = titDao.findById(itemId);
		type = tbInspectionType.getId();
		point = tbInspectionType.getPoint();
		
		TbInspection ins = new TbInspection( param.getUserId(), enterpriseId, 0, remark, itemId,
				type, recordTime, 0);
		TbInspectionDao dao = new TbInspectionDao(em);
		dao.save(ins);
		System.out.print(ins.getId());
		
		
		
		TbFile tf = null;
		TbFileDao tfDao = new TbFileDao(em);
		TbResInspectionFile trf = null;
		TbResInspectionFileDao trfDao = new TbResInspectionFileDao(em);
		
		TbInspectionPoint tbInspectionPoint = new TbInspectionPoint();
		TbInspectionPointDao pDao = new TbInspectionPointDao(em);
		
		tbInspectionPoint.setPoint(point);
		tbInspectionPoint.setDelFlag(0);
		tbInspectionPoint.setInspectionId(ins.getId());
		tbInspectionPoint.setEnterpriseId(enterpriseId);
		Calendar calendar = Calendar.getInstance();
		tbInspectionPoint.setYear( calendar.get(Calendar.YEAR));
		pDao.save(tbInspectionPoint);
		
		JSONArray ja = JSONArray.parseArray(file);
		for(int i = 0; i < ja.size(); i++){
			JSONObject jo = ja.getJSONObject(i);
			tf = new TbFile();
			if(jo.containsKey("fileName"))
				tf.setFileName(jo.getString("fileName"));
			if(jo.containsKey("fileSize"))
				tf.setFileSize(jo.getString("fileSize"));
			if(jo.containsKey("fileType"))
				tf.setFileType(jo.getInteger("fileType"));
			if(jo.containsKey("fileUrl"))
				tf.setFileUrl(jo.getString("fileUrl"));
			tfDao.save(tf);
			
			trf = new TbResInspectionFile(ins.getId(), tf.getId());
			trfDao.save(trf);
		}
		
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
