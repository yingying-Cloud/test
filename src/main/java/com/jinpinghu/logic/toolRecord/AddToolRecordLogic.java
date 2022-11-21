package com.jinpinghu.logic.toolRecord;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.ChangeToolNumberUtil;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecord.param.AddToolRecordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddToolRecordLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToolRecordParam myParam = (AddToolRecordParam)logicParam;
		Integer fromEnterpriseId=StringUtils.isEmpty(myParam.getFromEnterpriseId())?null:Integer.valueOf(myParam.getFromEnterpriseId());
		Integer outEnterpriseId=StringUtils.isEmpty(myParam.getOutEnterpriseId())?null:Integer.valueOf(myParam.getOutEnterpriseId());
		String useName = myParam.getUseName();
		String toolJa = myParam.getToolJa();
		
		TbToolDao toolDao = new TbToolDao(em);
		ChangeToolNumberUtil changeToolNumberUtil = ChangeToolNumberUtil.init();
		JSONArray jsonArray = JSONArray.fromObject(toolJa);
		for(int i =0;i<jsonArray.size();i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			Integer toolId=jo.getInt("toolId");
			BigDecimal number = jo.containsKey("number")?new BigDecimal(jo.getString("number")):null;
			TbTool fromTool = toolDao.findById(toolId);//卖家 出库农资Id
			TbTool outTool = toolDao.findByCode(fromTool.getCode(), outEnterpriseId);//买家 入库农资Id
			if(number!=null&&number.compareTo(BigDecimal.ZERO)>0) {
				changeToolNumberUtil.minusNumberForToolRecord(fromTool.getId(), number, em, fromEnterpriseId, useName,outEnterpriseId);
				if (fromEnterpriseId != outEnterpriseId && fromTool.getType() != 12) {
					changeToolNumberUtil.addNumberForToolRecord(outTool==null?null:outTool.getId(),fromTool.getCode(), number, em, outEnterpriseId, useName,fromEnterpriseId);
				}
			}
			
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
}
