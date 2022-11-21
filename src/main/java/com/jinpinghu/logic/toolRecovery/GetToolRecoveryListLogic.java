package com.jinpinghu.logic.toolRecovery;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.TbToolRecoveryDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecovery.param.GetToolRecoveryListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetToolRecoveryListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolRecoveryListParam myParam = (GetToolRecoveryListParam)logicParam;
		String name = myParam.getName();
		Integer enterpriseId =StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String dscd = myParam.getDscd();
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		
		TbToolRecoveryDao toolRecoveryDao2 = new TbToolRecoveryDao(em);
		
		Integer count = toolRecoveryDao2.findByNameCount(enterpriseId,name, startTime, endTime,dscd,selectAll,permissionEnterpriseIdList);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		JSONArray ja = new JSONArray();
		
		List<Object[]> list = toolRecoveryDao2.findByName(enterpriseId,name, startTime, endTime,nowPage,pageCount,dscd,selectAll,permissionEnterpriseIdList);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo  =new JSONObject();
				jo.put("id", o[0]);
				jo.put("toolRecoveryName", o[2]);
				jo.put("enterpriseName", o[1]);
				jo.put("model", o[3]);
				jo.put("specification", o[4]);
				jo.put("unit", o[5]);
				jo.put("describe", o[7]);
				jo.put("type", o[8]);
				jo.put("fileUrl",o[9]);
				jo.put("typeName", o[10]);
				jo.put("price", o[11]);
				try {
					jo.put("number", new BigDecimal(o[6].toString()));
				}catch (Exception e) {
					// TODO: handle exception
					jo.put("number", "");
				}
				
				ja.add(jo);
			}
		}
		res.add("status", 1).add("msg", "操作成功").add("result", ja).add("allCounts", count);
		return true;
	}
}
