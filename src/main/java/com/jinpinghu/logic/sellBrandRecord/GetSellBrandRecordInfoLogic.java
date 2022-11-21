package com.jinpinghu.logic.sellBrandRecord;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbSellBrandRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellBrand.SellBrandType;
import com.jinpinghu.logic.sellBrandRecord.param.GetSellBrandRecordInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetSellBrandRecordInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetSellBrandRecordInfoParam myParam = (GetSellBrandRecordInfoParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		JSONObject jo = new JSONObject();
		TbSellBrandRecordDao recordDao = new TbSellBrandRecordDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Object[] o = recordDao.findInfoById(id);
		if(o!=null) {
				jo.put("id", o[0]);
				jo.put("enterpriseName", o[1]);
				jo.put("sellBrandName", o[2]);
				jo.put("recordType", o[3]);
				jo.put("allNumber", (o[4] == null || "".equals(o[4])) ? "" : new BigDecimal(o[4].toString()));
				jo.put("number", (o[5] == null || "".equals(o[5])) ? "" : new BigDecimal(o[5].toString()));
				jo.put("useName", o[6]);
				jo.put("useTime", o[7]);
				jo.put("unit", o[8]);
				jo.put("type", o[9]);
				jo.put("typeName", SellBrandType.getValue((Integer)o[9]));
				jo.put("outName", o[10]);
				jo.put("outMobile", o[11]);
				if(o[0]!=null) {
					List<TbFile> tfs =tfDao.findBySellBrandRecordId(Integer.valueOf(o[0].toString()));
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
