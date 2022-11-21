package com.jinpinghu.logic.toolRecord;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbToolRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecord.param.GetToolRecordInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetToolRecordInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolRecordInfoParam myParam = (GetToolRecordInfoParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		JSONObject jo = new JSONObject();
		TbToolRecordDao recordDao2 = new TbToolRecordDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Object[] o = recordDao2.findInfoById(id);
		if(o!=null) {
				jo.put("id", o[0]);
				jo.put("enterpriseName", o[1]);
				jo.put("toolName", o[2]);
				jo.put("recordType", o[3]);
				jo.put("allNumber", o[4]);
				jo.put("number", o[5]);
				jo.put("useName", o[6]);
				jo.put("useTime", o[7]);
				jo.put("supplierName", o[8]);
				jo.put("unit", o[9]);
				jo.put("type", o[10]);
				jo.put("typeName", o[11]);
				jo.put("outName", o[12]);
				jo.put("outMobile", o[13]);
				jo.put("price", o[14] == null ? "" : o[14].toString());
				if(o[0]!=null) {
					List<TbFile> tfs =tfDao.findByToolRecordId(Integer.valueOf(o[0].toString()));
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
