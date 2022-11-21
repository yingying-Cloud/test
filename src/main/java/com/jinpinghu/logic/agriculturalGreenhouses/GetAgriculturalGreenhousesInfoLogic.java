package com.jinpinghu.logic.agriculturalGreenhouses;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.db.dao.TbAgriculturalGreenhousesDao2;
import com.jinpinghu.db.dao.TbResFileGreenhousesDao2;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.param.GetAgriculturalGreenhousesInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetAgriculturalGreenhousesInfoLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAgriculturalGreenhousesInfoParam myParam =(GetAgriculturalGreenhousesInfoParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbAgriculturalGreenhousesDao2 tbAgriculturalGreenhousesDao2 = new TbAgriculturalGreenhousesDao2(em);
		TbResFileGreenhousesDao2 tbResFileGreenhousesDao2 = new TbResFileGreenhousesDao2(em);
		Object[] tbAgriculturalGreenhouses =tbAgriculturalGreenhousesDao2.findObjectById(id);
		JSONObject jo = new JSONObject();
		if(tbAgriculturalGreenhouses!=null) {
			jo.put("id",tbAgriculturalGreenhouses[0]);
//			String area = tbAgriculturalGreenhouses[1]==null?"":StringUtils.isEmpty(tbAgriculturalGreenhouses[1].toString())?"":tbAgriculturalGreenhouses[1].toString();
//			if(!StringUtils.isEmpty(area)) {
//				String areas = area.split("\\(")[2];
//				areas = areas.split("\\)")[0];
//				jo.put("inArea", areas);
//			}
//			jo.put("area",tbAgriculturalGreenhouses[1]);
			jo.put("enterpriseId",tbAgriculturalGreenhouses[2]);
//			jo.put("center",tbAgriculturalGreenhouses[3]);
			jo.put("greenhousesName",tbAgriculturalGreenhouses[3]);
			jo.put("mu",tbAgriculturalGreenhouses[4]);
			jo.put("classify",tbAgriculturalGreenhouses[5]);
			List<Object[]> byGreenhousesId = tbResFileGreenhousesDao2.findUserByGreenhousesId((Integer) tbAgriculturalGreenhouses[0]);
			JSONArray ja = new JSONArray();
			if(byGreenhousesId!=null) {
				for(Object[] o:byGreenhousesId) {
					JSONObject file = new JSONObject();
					file.put("id", o[0]);
					file.put("fileName", o[1]);
					file.put("fileSize", o[2]);
					file.put("fileType", o[3]);
					file.put("fileUrl",  o[4]);
					ja.add(file);
				}
				jo.put("file", ja);
			}
			
		}
		res.add("result", jo).add("status", 1).add("msg","操作成功");
		return true;
	}

}
