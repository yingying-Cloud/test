package com.jinpinghu.logic.order;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.ChangeOrderCheckParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChangeOrderCheckLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ChangeOrderCheckParam myParam = (ChangeOrderCheckParam)logicParam;
		String checkJa=myParam.getCheckJa();
		TbToolOrderDao toDao = new TbToolOrderDao(em);
		JSONArray ja =JSONArray.fromObject(checkJa);
		if(ja!=null) {
			for(int i=0;i<ja.size();i++) {
				JSONObject jo = (JSONObject) ja.get(i);
				TbToolOrder to = toDao.findById(jo.getInt("id"));
				if(to!=null){
					to.setCheck(1);
					to.setCheckMoney(jo.getString("checkMoney"));
					toDao.update(to);
				}
			}
		}
		res.add("msg", "操作成功");
		res.add("status", 1);
		return true;
	}
}
