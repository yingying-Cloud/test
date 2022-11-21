package com.jinpinghu.logic.expert;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.bean.TbType;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbExpertDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbTypeDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.expert.param.GetExpertInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class GetExpertInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetExpertInfoParam myParam = (GetExpertInfoParam)logicParam;
		String id = myParam.getId();
		TbExpertDao expertDao = new TbExpertDao(em);
		TbExpert expert = null;
		if(!StringUtils.isNullOrEmpty(id)) {
			expert=expertDao.findById(Integer.valueOf(id));
		}
		TbTypeDao tbTypeDao = new TbTypeDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		JSONObject jo = new JSONObject();
		String uid = myParam.getUid();
		TbUserDao tuDao = new TbUserDao(em);
		if(!StringUtils.isNullOrEmpty(uid)) {
			TbUser user = tuDao.findById(Integer.valueOf(uid));
			if(user!=null) {
				jo.put("name",user.getName());
				jo.put("mobile",user.getMobile());
			}
		}
		if(expert!=null) {
			jo.put("inputTime",DateTimeUtil.formatTime2(expert.getInputTime()));
			jo.put("status",expert.getStatus());
			jo.put("address",expert.getAddress());
			jo.put("goodsField",expert.getGoodsField());
			jo.put("idcard",expert.getIdcard());
			jo.put("id",expert.getId());
			jo.put("status",expert.getStatus());
			jo.put("synopsis",expert.getSynopsis());
			jo.put("cost",expert.getCost());
			jo.put("type",expert.getType());
			jo.put("adnm",expert.getAdnm());
			jo.put("productTeam",expert.getProductTeam());
			if(!StringUtils.isNullOrEmpty(expert.getType())) {
				TbType type = tbTypeDao.findById(Integer.valueOf(expert.getType()));
				jo.put("typeName",type.getName());
			}
			List<Map<String, Object>> tfs =tfDao.getListByExpertId(expert.getId());
			jo.put("file", tfs);
		}
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", jo);
		return true;
	}
}
