package com.jinpinghu.logic.toolCatalog;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResTrademarkBrandDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolCatalogYxcfDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.GetToolCatalogInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetToolCatalogInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolCatalogInfoParam myParam = (GetToolCatalogInfoParam)logicParam;
		Integer id = StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbToolCatalogDao toolCatelogDao2 = new TbToolCatalogDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		TbToolCatalogYxcfDao catalogYxcfDao = new TbToolCatalogYxcfDao(em);
		Object[] o = toolCatelogDao2.findInfoById(id);
		JSONObject jo  = null;
		if(o!=null) {
			jo =new JSONObject();;
			jo.put("id", o[0]);
			jo.put("toolCatelogName", o[1]);
			jo.put("model", o[2]);
			jo.put("specification", o[3]);
			jo.put("unit", o[4]);
			jo.put("price", o[5]);
			jo.put("number", o[6]);
			jo.put("describe", o[7]);
			jo.put("type", o[8]);
			jo.put("supplierName", o[9]);
			jo.put("productionUnits",o[10]);
			jo.put("registrationCertificateNumber",o[11]);
			jo.put("explicitIngredients",o[12]);
			jo.put("effectiveIngredients",o[13]);
			jo.put("implementationStandards",o[14]);
			jo.put("dosageForms",o[15]);
			jo.put("productAttributes",o[16]);
			jo.put("toxicity",o[17]);
			jo.put("qualityGrade",o[18]);
			jo.put("uniformPrice",o[19]);
			jo.put("code",o[20]);
			jo.put("typeName",o[21]);
			jo.put("wholesalePrice",o[22]);
			jo.put("remark", o[23]);
			jo.put("name", o[24]);
			jo.put("mobile", o[25]);
			jo.put("inputTime", o[26]);
			jo.put("enterpriseName", o[27]);
			jo.put("hfzc", o[28]);
			
			jo.put("approvalEndDate",o[29]);
			jo.put("approvalNo",o[30]);
			jo.put("approveNo",o[31]);
			jo.put("certificateNo",o[32]);
			jo.put("checkHealthNo",o[33]);
			jo.put("healthNo",o[34]);
			jo.put("limitDate",o[35]);
			jo.put("produced",o[36]);
			jo.put("productionNo",o[37]);
			
			jo.put("n",o[38]);
			jo.put("p",o[39]);
			jo.put("k",o[40]);
			jo.put("qt",o[41]);
			jo.put("qrCode",o[42]);
			
			jo.put("NPK",o[43]);
			jo.put("NP",o[44]);
			jo.put("NK",o[45]);
			jo.put("PK",o[46]);
			jo.put("zjzl",o[47]);
			jo.put("yxcfUnit",o[48]);

			//nysx字段
			jo.put("nysx", o[49]);
			
				if(o[0]!=null) {
					List<TbFile> tfs =tfDao.findByToolCatalogId(Integer.valueOf(o[0].toString()));
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
				List<Map<String, Object>> yxcf = catalogYxcfDao.findMapByToolCatalogId(id);
				jo.put("yxcf", yxcf);
		}
		res.add("status", 1).add("msg", "操作成功").add("result", jo);
		return true;
	}
}
