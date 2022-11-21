package com.jinpinghu.logic.sc;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbBuyRelease;
import com.jinpinghu.db.bean.TbScOrder;
import com.jinpinghu.db.bean.TbSupplyRelease;
import com.jinpinghu.db.dao.TbBuyReleaseDao;
import com.jinpinghu.db.dao.TbScOrderDao;
import com.jinpinghu.db.dao.TbSupplyReleaseDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.AddScOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddScOrderLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddScOrderParam myParam = (AddScOrderParam)logicParam;
		Date startTime = StringUtils.isEmpty(myParam.getStartTime()) ? null : DateTimeUtil.formatSelf(myParam.getStartTime(), "yyyy-MM-dd");
		Date endTime = StringUtils.isEmpty(myParam.getEndTime()) ? null : DateTimeUtil.formatSelf(myParam.getEndTime(), "yyyy-MM-dd");
		String contactPerson = myParam.getContactPerson();
		String contactPhone = myParam.getContactPhone();
		String contactAddress = myParam.getContactAddress();
		Integer workId = StringUtils.isEmpty(myParam.getWorkId()) ? null : Integer.valueOf(myParam.getWorkId());
		String remark = myParam.getRemark();
		Integer supplyReleaseId = StringUtils.isEmpty(myParam.getSupplyReleaseId()) ? null : Integer.valueOf(myParam.getSupplyReleaseId());
		Integer buyReleaseId = StringUtils.isEmpty(myParam.getBuyReleaseId()) ? null : Integer.valueOf(myParam.getBuyReleaseId());
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		Integer num = StringUtils.isEmpty(myParam.getNum()) ? null : Integer.valueOf(myParam.getNum());
		String productName = myParam.getProductName();
		BigDecimal price = StringUtils.isEmpty(myParam.getPrice()) ? null : new BigDecimal(myParam.getPrice());
		
		TbScOrderDao scOrderDao = new TbScOrderDao(em);
		TbSupplyReleaseDao supplyReleaseDao = new TbSupplyReleaseDao(em);
		TbBuyReleaseDao buyReleaseDao = new TbBuyReleaseDao(em);
		
		TbSupplyRelease supplyRelease = supplyReleaseDao.findById(supplyReleaseId);
		
		TbBuyRelease buyRelease = buyReleaseDao.findById(buyReleaseId);
		if (buyRelease == null && supplyRelease == null) {
			res.add("status", -1).add("msg", "求购发布与供应发布不能同时为空");
			return false;
		}
		
		if (StringUtils.isEmpty(productName)) {
			TbWorkDao workDao = new TbWorkDao(em);
			productName = workDao.getProductNameByWorkId(supplyRelease == null ? workId : supplyRelease.getWorkId());
		}
		
		num = buyRelease == null ? num : buyRelease.getNum();
		
		TbScOrder scOrder = new TbScOrder();
		scOrder.setBuyEnterpriseId(buyRelease == null ? enterpriseId : buyRelease.getEnterpriseId());
		scOrder.setSupplyEnterpriseId(supplyRelease == null ? enterpriseId : supplyRelease.getEnterpriseId());
		scOrder.setWorkId(supplyRelease == null ? workId : supplyRelease.getWorkId());
		scOrder.setOrderNumber((supplyRelease == null ? enterpriseId : supplyRelease.getEnterpriseId())+""+System.currentTimeMillis());
		scOrder.setNum(num);
		scOrder.setPrice(buyRelease == null ? price : buyRelease.getPrice());
		scOrder.setStartTime(buyRelease == null ? startTime : buyRelease.getStartTime());
		scOrder.setEndTime(buyRelease == null ? endTime : buyRelease.getEndTime());
		scOrder.setBuyContactPerson(buyRelease == null ? contactPerson : buyRelease.getContactPerson());
		scOrder.setBuyContactAddress(buyRelease == null ? contactAddress : buyRelease.getContactAddress());
		scOrder.setBuyContactPhone(buyRelease == null ? contactPhone : buyRelease.getContactPhone());
		scOrder.setSupplyContactPerson(supplyRelease == null ? contactPerson : supplyRelease.getContactPerson());
		scOrder.setSupplyContactAddress(supplyRelease == null ? contactAddress : supplyRelease.getContactAddress());
		scOrder.setSupplyContactPhone(supplyRelease == null ? contactPhone : supplyRelease.getContactPhone());
		scOrder.setRemark(remark);
		scOrder.setSupplyReleaseId(supplyReleaseId);
		scOrder.setBuyReleaseId(buyReleaseId);
		scOrder.setProductName(buyRelease == null ? productName : buyRelease.getProductName());
		scOrder.setStatus(1);
		scOrder.setReleaseType(buyRelease == null ? "" : "2");
		scOrder.setSpecification(buyRelease == null ? supplyRelease.getSpecification() : buyRelease.getSpecification());
		scOrder.setDelFlag(0);
		
		scOrderDao.save(scOrder);
		
		if (supplyRelease != null) {
			Integer stock = supplyRelease.getStock();
			if (stock-num >= 0) {
				supplyRelease.setStock(stock-num);
			}else {
				res.add("status", -1).add("msg", "供应发布库存不足");
				return false;
			}
			supplyReleaseDao.update(supplyRelease);
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
