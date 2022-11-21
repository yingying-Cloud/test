package com.jinpinghu.logic.arableLand;

import java.util.UUID;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbArableLand;
import com.jinpinghu.db.dao.TbArableLandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.arableLand.param.AddOrUpdateArableLandParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrUpdateArableLandLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddOrUpdateArableLandParam myParam = (AddOrUpdateArableLandParam)logicParam;
		String id = myParam.getId();
		String villageGroup = myParam.getVillageGroup();
		String userName = myParam.getUserName();
		String idCard = myParam.getIdCard();
		String area = myParam.getArea();
		String riceArea = myParam.getRiceArea();
		String wheatArea = myParam.getWheatArea();
		String vegetablesArea = myParam.getVegetablesArea();
		String fruitsArea = myParam.getFruitsArea();
		String otherArea = myParam.getOtherArea();
		String remark = myParam.getRemark();
		String town = myParam.getTown();
		
		TbArableLandDao tbArableaLandDao = new TbArableLandDao(em);
		TbArableLand tbArableaLand = null;
		if(!StringUtils.isEmpty(id)) {
			tbArableaLand = tbArableaLandDao.findById(id);
		}
		if(tbArableaLand!=null) {
			tbArableaLand.setArea(area);
			tbArableaLand.setFruitsArea(fruitsArea);
			tbArableaLand.setIdCard(idCard);
			tbArableaLand.setOtherArea(otherArea);
			tbArableaLand.setRemark(remark);
			tbArableaLand.setRiceArea(riceArea);
			tbArableaLand.setTown(town);
			tbArableaLand.setUserName(userName);
			tbArableaLand.setVegetablesArea(vegetablesArea);
			tbArableaLand.setVillageGroup(villageGroup);
			tbArableaLand.setWheatArea(wheatArea);
			tbArableaLandDao.update(tbArableaLand);
		}else {
			tbArableaLand = new TbArableLand();
			tbArableaLand.setArea(area);
			tbArableaLand.setFruitsArea(fruitsArea);
			tbArableaLand.setIdCard(idCard);
			tbArableaLand.setOtherArea(otherArea);
			tbArableaLand.setRemark(remark);
			tbArableaLand.setRiceArea(riceArea);
			tbArableaLand.setTown(town);
			tbArableaLand.setUserName(userName);
			tbArableaLand.setVegetablesArea(vegetablesArea);
			tbArableaLand.setVillageGroup(villageGroup);
			tbArableaLand.setWheatArea(wheatArea);
			tbArableaLand.setDelFlag(0);
			tbArableaLand.setId(UUID.randomUUID().toString());
			tbArableaLandDao.save(tbArableaLand);
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
