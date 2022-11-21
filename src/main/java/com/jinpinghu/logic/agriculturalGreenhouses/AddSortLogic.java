package com.jinpinghu.logic.agriculturalGreenhouses;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbAgriculturalGreenhouses;
import com.jinpinghu.db.dao.TbAgriculturalGreenhousesDao2;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.param.AddSortParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddSortLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddSortParam myParam = (AddSortParam)logicParam;
		JSONArray sortArray = StringUtils.isEmpty(myParam.getSortArray()) ? null : JSONArray.fromObject(myParam.getSortArray());
		
		TbAgriculturalGreenhousesDao2 tbAgriculturalGreenhousesDao2 = new TbAgriculturalGreenhousesDao2(em);
		
		if(sortArray != null && sortArray.size()>0) {
			for(int i=0;i<sortArray.size();i++) {
				JSONObject jo = (JSONObject)sortArray.get(i);
				Integer id = null;
				Integer listOrder = 0;
				if(jo.containsKey("id"))
					id = jo.getInt("id");
				if(jo.containsKey("listOrder"))
					listOrder = StringUtils.isNumeric(jo.getString("listOrder")) ? Integer.valueOf(jo.getString("listOrder")) : 0;
				if(id!=null) {
					TbAgriculturalGreenhouses tag = tbAgriculturalGreenhousesDao2.findById(id);
					if(tag != null) {
						tag.setListOrder(listOrder);
						tbAgriculturalGreenhousesDao2.update(tag);
					}
				}
			}
		}
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
}
