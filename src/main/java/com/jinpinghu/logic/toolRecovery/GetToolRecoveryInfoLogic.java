package com.jinpinghu.logic.toolRecovery;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbToolRecoveryDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecovery.param.GetToolRecoveryInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetToolRecoveryInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolRecoveryInfoParam myParam = (GetToolRecoveryInfoParam)logicParam;
		Integer id = StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Integer enterpriseId = myParam.getEnterpriseId();
		TbToolRecoveryDao toolRecoveryDao2 = new TbToolRecoveryDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Object[] o = toolRecoveryDao2.findInfoById(id,enterpriseId);
		JSONObject jo  =new JSONObject();
		if(o!=null) {
			jo.put("id", o[0]);
			jo.put("toolRecoveryName", o[2]);
			jo.put("name", o[1]);
			jo.put("model", o[3]);
			jo.put("specification", o[4]);
			jo.put("unit", o[5]);
			try {
				jo.put("number", new BigDecimal(o[6].toString()));
			}catch (Exception e) {
				// TODO: handle exception
				jo.put("number", "");
			}
			jo.put("describe", o[7]);
			jo.put("type", o[8]);
			jo.put("typeName", o[9]);
			jo.put("price", o[10]);
				if(o[0]!=null) {
					List<TbFile> tfs =tfDao.findByToolRecoveryId(Integer.valueOf(o[0].toString()));
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
