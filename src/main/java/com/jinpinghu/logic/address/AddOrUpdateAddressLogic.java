package com.jinpinghu.logic.address;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbReceivingAddress;
import com.jinpinghu.db.dao.TbAddressDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.address.param.AddressParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrUpdateAddressLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddressParam param = (AddressParam)logicParam;
		Integer id = param.getId();
		String linkPeople = param.getLinkPeople();
		String linkMobile = param.getLinkMobile();
		String province = param.getProvince();
		String city = param.getCity();
		String county = param.getCounty();
		String address = param.getAddress();
		
		TbAddressDao dao = new TbAddressDao(em);
		TbReceivingAddress tbReceivingAddress = null;
		
		if(id != null){
			tbReceivingAddress = dao.findById(id);
			tbReceivingAddress.setAddress(address);
			tbReceivingAddress.setCity(city);
			tbReceivingAddress.setCounty(county);
			tbReceivingAddress.setLinkMobile(linkMobile);
			tbReceivingAddress.setLinkPeople(linkPeople);
			tbReceivingAddress.setProvince(province);
			dao.update(tbReceivingAddress);
		}else{
			tbReceivingAddress = new TbReceivingAddress(param.getUserId(),linkPeople, linkMobile, province, city,
			county, address, new Date(), 0, 0);
			dao.save(tbReceivingAddress);
		}
		
		
		res.add("status", 1);
		res.add("msg", "成功");	
		return true;
	}

}
