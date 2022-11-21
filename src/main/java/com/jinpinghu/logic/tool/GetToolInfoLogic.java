package com.jinpinghu.logic.tool;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolYxcfDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.GetToolInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetToolInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolInfoParam myParam = (GetToolInfoParam)logicParam;
		Integer id = StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbToolDao toolDao2 = new TbToolDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		TbToolYxcfDao tbToolYxcfDao = new TbToolYxcfDao(em);
		Object[] o = toolDao2.findInfoById(id,myParam.getUserId());
		JSONObject jo  = null; 
		if(o!=null) {
			jo = new JSONObject();
			jo.put("id", o[0]);
			jo.put("toolName", o[2]);
			jo.put("name", o[1]);
			jo.put("model", o[3]);
			jo.put("specification", o[4]);
			jo.put("unit", o[5]);
			jo.put("price", o[6]);
			jo.put("number", o[7]);
			jo.put("describe", o[8]);
			jo.put("type", o[9]);
			jo.put("supplierName", o[10]);
			jo.put("typeName", o[11]);
			jo.put("productionUnits",o[12]);
			jo.put("registrationCertificateNumber",o[13]);
			jo.put("explicitIngredients",o[14]);
			jo.put("effectiveIngredients",o[15]);
			jo.put("implementationStandards",o[16]);
			jo.put("dosageForms",o[17]);
			jo.put("productAttributes",o[18]);
			jo.put("toxicity",o[19]);
			jo.put("qualityGrade",o[20]);
			jo.put("uniformPrice",o[21]);
			jo.put("code",o[22]);
			jo.put("wholesalePrice",o[23]);
			jo.put("status",o[24]);
			jo.put("dnm",o[25]);
			jo.put("hfzc",o[26]);
			
			jo.put("approvalEndDate",o[27]);
			jo.put("approvalNo",o[28]);
			jo.put("approveNo",o[29]);
			jo.put("certificateNo",o[30]);
			jo.put("checkHealthNo",o[31]);
			jo.put("healthNo",o[32]);
			jo.put("limitDate",o[33]);
			jo.put("produced",o[34]);
			jo.put("productionNo",o[35]);
			jo.put("enterpriseId",o[36]);
			
			jo.put("n",o[37]);
			jo.put("p",o[38]);
			jo.put("k",o[39]);
			jo.put("qt",o[40]);
			jo.put("qrCode",o[41]);
			
			jo.put("NPK",o[42]);
			jo.put("NP",o[43]);
			jo.put("NK",o[44]);
			jo.put("PK",o[45]);
			jo.put("zjzl",o[46]);
			jo.put("yxcfUnit",o[47]);
			jo.put("nysx",o[48]);
				if(o[0]!=null) {
					List<TbFile> tfs =tfDao.findByToolId(Integer.valueOf(o[0].toString()));
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
				List<Map<String, Object>> yxcf = tbToolYxcfDao.findMapByToolId(id);
				jo.put("yxcf", yxcf);
		}
		res.add("status", 1).add("msg", "操作成功").add("result", jo);
		return true;
	}
	// 身份认证
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
		return true;
	}
}
