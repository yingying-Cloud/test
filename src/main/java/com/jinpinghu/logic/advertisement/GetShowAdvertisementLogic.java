package com.jinpinghu.logic.advertisement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbAdvertisement;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbAdvertisementDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.advertisement.param.DetailAdvertisementParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetShowAdvertisementLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DetailAdvertisementParam myParam = (DetailAdvertisementParam)logicParam;
		String code = myParam.getCode();
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser tbUser = tbUserDao.checkLogin2(code);
		TbEnterprise tbEnterprise = resUserEnterpriseDao.findByUserTabId(tbUser.getId());
		
		TbAdvertisementDao taDao = new TbAdvertisementDao(em);
		TbFileDao tbFileDao = new TbFileDao(em);
		Map<String, Object> ta = null;
		if(tbEnterprise!=null){
			ta = taDao.getShowAdvertisement(tbEnterprise.getId());
			if(ta!=null){
				List<TbFile> list = tbFileDao.findByAdvertisementId(Integer.valueOf(ta.get("id").toString()));
				ta.put("fileList", list);
			}
		}
		res.add("result", ta);
		res.add("msg", "成功！");
		res.add("status", 1);
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
			return true;
	}
}
