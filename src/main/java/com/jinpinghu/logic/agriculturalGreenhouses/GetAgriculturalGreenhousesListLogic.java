package com.jinpinghu.logic.agriculturalGreenhouses;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.db.dao.TbAgriculturalGreenhousesDao2;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.param.GetAgriculturalGreenhousesListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetAgriculturalGreenhousesListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAgriculturalGreenhousesListParam myParam = (GetAgriculturalGreenhousesListParam)logicParam;
		String enterpriseId = myParam.getEnterpriseId();
		String greenhousesName = myParam.getGreenhousesName();
		String type = myParam.getType();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		
		JSONArray ja = new JSONArray();
		TbAgriculturalGreenhousesDao2 tbAgriculturalGreenhousesDao2 = new TbAgriculturalGreenhousesDao2(em);
		Integer count = tbAgriculturalGreenhousesDao2.getListCount(enterpriseId, greenhousesName,type);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据！");
			return true;
		}
		
		List<Object[]> list = tbAgriculturalGreenhousesDao2.getList(enterpriseId, greenhousesName,type,nowPage,pageCount);
		if(list!=null) {
			for(Object[] o :list) {
				JSONObject jo = new JSONObject();
				jo.put("id", o[0]==null?"":o[0]);
				jo.put("greenhousesName", o[1]==null?"":o[1]);
				jo.put("mu", o[2]==null?"":o[2]);
				jo.put("fileUrl", o[4]==null?"":o[4]);
				jo.put("listOrder", o[5]==null?"":o[5]);
				jo.put("classify", o[6]==null?"":o[6]);
				ja.add(jo);
			}
		}
		res.add("result", ja).add("status", 1).add("msg", "操作成功").add("allCounts", count);
		return true;
	}

}
