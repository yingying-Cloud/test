package com.jinpinghu.logic.appUtil;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.AppUtil;
import com.jinpinghu.db.dao.AppUtilDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.appUtil.param.AddAppUtilParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddAppUtilLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddAppUtilParam myParam = (AddAppUtilParam)logicParam;
		String utilJa = myParam.getUtilJa();
		AppUtilDao appUtilDao = new AppUtilDao(em);
		JSONArray ja = JSONArray.fromObject(utilJa);
		for(int i=0;i<ja.size();i++) {
			JSONObject jo = (JSONObject)ja.get(i);
			Integer id = jo.containsKey("id")?jo.getInt("id"):null;
			if(id!=null) {
				AppUtil appUtil = appUtilDao.findById(id);
				appUtil.setKey(jo.containsKey("key")?jo.getString("key"):"");
				appUtil.setValue(jo.containsKey("value")?jo.getString("value"):"");
				appUtil.setNote(jo.containsKey("note")?jo.getString("note"):"");
				appUtil.setUrl(jo.containsKey("url")?jo.getString("url"):"");
				appUtilDao.update(appUtil);
			}else {
				AppUtil appUtil = new AppUtil();
				appUtil.setKey(jo.containsKey("key")?jo.getString("key"):"");
				appUtil.setValue(jo.containsKey("value")?jo.getString("value"):"");
				appUtil.setNote(jo.containsKey("note")?jo.getString("note"):"");
				appUtil.setUrl(jo.containsKey("url")?jo.getString("url"):"");
				appUtil.setDelFlag(0);
				appUtilDao.save(appUtil);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
