package com.jinpinghu.logic.enterprise;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbResEnterpriseZone;
import com.jinpinghu.db.dao.TbResEnterpriseZoneDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.AddOrRemoveEnterpriseZoneParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrRemoveEnterpriseZoneLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddOrRemoveEnterpriseZoneParam myParam = (AddOrRemoveEnterpriseZoneParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		String zoneIds = myParam.getZoneIds();
		String type = myParam.getType();
		List<Integer> zoneIdList = new ArrayList<Integer>();
		
		String[] zoneIdArray = zoneIds.split(",");
		for (int i = 0; i < zoneIdArray.length; i++) {
			zoneIdList.add(Integer.valueOf(zoneIdArray[i]));
		}
		
		TbResEnterpriseZoneDao resEnterpriseZoneDao = new TbResEnterpriseZoneDao(em);
		
		List<TbResEnterpriseZone> resEnterpriseZoneList = resEnterpriseZoneDao.findByZoneIdEnterpriseId(enterpriseId, zoneIdList);
		
		if("2".equals(type)) {
			if(resEnterpriseZoneList != null) {
				for(int i=0;i<resEnterpriseZoneList.size();i++) {
					TbResEnterpriseZone resEnterpriseZone = resEnterpriseZoneList.get(i);
					resEnterpriseZone.setDelFlag(1);
					resEnterpriseZoneDao.update(resEnterpriseZone);
				}
				
			}
		}else if("1".equals(type)) {
			for(int i=0;i<zoneIdList.size();i++) {
				TbResEnterpriseZone resEnterpriseZone = resEnterpriseZoneDao.findByZoneIdEnterpriseId(enterpriseId, zoneIdList.get(i));
				if(resEnterpriseZone == null) {
					resEnterpriseZone = new TbResEnterpriseZone(zoneIdList.get(i),enterpriseId,0);
					resEnterpriseZoneDao.save(resEnterpriseZone);
				}
			}
			
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddOrRemoveEnterpriseZoneParam myParam = (AddOrRemoveEnterpriseZoneParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		String zoneIds = myParam.getZoneIds();
		String type = myParam.getType();

		if(StringUtils.isEmpty(zoneIds) || StringUtils.isEmpty(type) || enterpriseId == null) {
			res.add("status", -1).add("msg", "必填参数不能为空。");
			res.add("msg", "操作成功");
			return false;
		}
		
		return true;
	}
}
