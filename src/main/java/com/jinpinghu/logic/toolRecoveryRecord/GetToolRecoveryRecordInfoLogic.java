package com.jinpinghu.logic.toolRecoveryRecord;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbToolRecoveryRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.GetToolRecoveryRecordInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetToolRecoveryRecordInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolRecoveryRecordInfoParam myParam = (GetToolRecoveryRecordInfoParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		JSONObject jo = new JSONObject();
		TbToolRecoveryRecordDao recordDao2 = new TbToolRecoveryRecordDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Object[] o = recordDao2.findInfoById(id);
		if(o!=null) {
				jo.put("id", o[0]);
				jo.put("enterpriseName", o[1]);
				jo.put("toolRecoveryName", o[2]);
				jo.put("allNumber", o[3]);
				jo.put("number", o[4]);
				jo.put("useName", o[5]);
				jo.put("inputTime", o[6]);
				jo.put("unit", o[7]);
				jo.put("type", o[8]);
				jo.put("useMobile", o[9]);
				jo.put("operator", o[10]);
				jo.put("typeName", o[11]);
				jo.put("linkOrderInfoId", o[12]);
				jo.put("totalPrice", o[13]);
				jo.put("toolName", o[14]);
				jo.put("price", o[16]);
				jo.put("recordNumber", o[15]);
				jo.put("legalPersonIdcard", o[18]);
				jo.put("linkMobile", o[17]);
				if(o[0]!=null) {
					List<TbFile> tfs =tfDao.findByToolRecoveryRecordId(Integer.valueOf(o[0].toString()));
					JSONArray ja = new JSONArray();
					if(tfs!=null) {
						for(TbFile tf:tfs) {
							JSONObject file = new JSONObject();
							file.put("id",tf.getId());
							file.put("fileName", tf.getFileName());
							file.put("fileSize", tf.getFileSize());
							file.put("fileType", tf.getFileType());
							file.put("fileUrl",  tf.getFileUrl());
							ja.add(file);
						}
						jo.put("file", ja);
					}
				}
		}
		res.add("status", 1).add("msg", "操作成功").add("result", jo);
		return true;
	}
}
