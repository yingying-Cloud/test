package com.jinpinghu.logic.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbResEnterpriseLinkorderinfo;
import com.jinpinghu.db.bean.TbResEnterpriseToolOrder;
import com.jinpinghu.db.bean.TbResOrderCar;
import com.jinpinghu.db.bean.TbShoppingCar;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbResEnterpriseLinkorderinfoDao;
import com.jinpinghu.db.dao.TbResOrderCarDao;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.db.dao.TbToolRecordDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.AddToBOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddToBOrderLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddToBOrderParam myParam = (AddToBOrderParam)logicParam;
		String userId = myParam.getUserId();
		String idcard = myParam.getIdcard();
		String name = myParam.getName();
		String address = myParam.getAddress();
		String sex = myParam.getSex();
		String nation = myParam.getNation();
		String orderInfo = myParam.getOrderInfo();
		Integer sellEnterpriseId = Integer.valueOf(myParam.getSellEnterpriseId());
		Integer buyEnterpriseId = Integer.valueOf(myParam.getBuyEnterpriseId());
		String phone = myParam.getPhone();
		
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		TbUserDao tbUserDao = new TbUserDao(em);
		TbShoppingCarDao shoppingCarDao = new TbShoppingCarDao(em);
		TbResOrderCarDao resOrderCarDao = new TbResOrderCarDao(em);
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		TbToolDao toolDao = new TbToolDao(em);
		TbToolRecordDao toolRecordDao = new TbToolRecordDao(em);
		
		TbLinkOrderInfo linkOrderInfo = linkOrderInfoDao.findByIdcard(idcard);
		if (linkOrderInfo == null) {
			linkOrderInfo = new TbLinkOrderInfo();
			linkOrderInfo.setDelFlag(0);
			linkOrderInfo.setLegalPersonIdcard(idcard);
			linkOrderInfo.setInputTime(new Date());
			linkOrderInfo.setAddress(address);
			linkOrderInfo.setName(name);
			linkOrderInfo.setNation(nation);
			linkOrderInfo.setSex(sex);
			linkOrderInfo.setUpdateTime(new Date());
			linkOrderInfo.setCountry(null);
			linkOrderInfo.setLinkPeople(name);
			linkOrderInfo.setLinkMobile(phone);
			linkOrderInfo.setType(2);
			linkOrderInfo.setIsValidation("1");
			linkOrderInfoDao.save(linkOrderInfo);
		}else {
			linkOrderInfo.setAddress(address);
			linkOrderInfo.setLinkMobile(phone);
			linkOrderInfo.setName(name);
			linkOrderInfo.setNation(nation);
			linkOrderInfo.setSex(sex);
			linkOrderInfoDao.update(linkOrderInfo);
		}
		
		TbResEnterpriseLinkorderinfoDao resEnterpriseLinkorderinfoDao = new TbResEnterpriseLinkorderinfoDao(em);
		TbResEnterpriseLinkorderinfo resEnterpriseLinkorderinfo = resEnterpriseLinkorderinfoDao.findByEnterpriseIdAndLinkOrderInfoId(sellEnterpriseId, linkOrderInfo.getId());
		if (resEnterpriseLinkorderinfo == null) {
			resEnterpriseLinkorderinfo = new TbResEnterpriseLinkorderinfo();
			resEnterpriseLinkorderinfo.setEnterpriseId(sellEnterpriseId);
			resEnterpriseLinkorderinfo.setLinkOrderInfoId(linkOrderInfo.getId());
			resEnterpriseLinkorderinfoDao.save(resEnterpriseLinkorderinfo);
		}
		
		TbUser user = tbUserDao.checkLogin2(userId);
		
		Date now = new Date();

		//保存订单信息
		TbToolOrder toolOrder = new TbToolOrder();
		toolOrder.setDelFlag(0);
		toolOrder.setOrderNumber(sellEnterpriseId+""+System.currentTimeMillis());
		toolOrder.setPlantEnterpriseId(linkOrderInfo == null ? null : linkOrderInfo.getId());
		toolOrder.setToolEnterpriseId(sellEnterpriseId);
		toolOrder.setStatus(4);
		toolOrder.setAddPeople(user.getName());
		toolOrder.setPrice(myParam.getPrice());
		toolOrder.setCheck(0);
		toolOrder.setType(2);
		
		toolOrder.setInputTime(now);
		toolOrder.setTimeAudit(now);
		toolOrder.setTimeSend(now);
		toolOrder.setTimePay(now);
		toolOrder.setTimeComplete(now);
		
		toolOrderDao.save(toolOrder);
		
		//添加到关联表
		TbResEnterpriseToolOrder resEnterpriseToolOrder = new TbResEnterpriseToolOrder();
		resEnterpriseToolOrder.setToolOrderId(toolOrder.getId());
		resEnterpriseToolOrder.setEnterpriseId(buyEnterpriseId);
		toolOrderDao.save(resEnterpriseToolOrder);
		
		JSONArray orderInfoArray = JSONArray.fromObject(orderInfo);
		
		//添加订单信息
		if (orderInfoArray != null && !orderInfoArray.isEmpty()) {
			for (int i = 0; i < orderInfoArray.size(); i++) {
				JSONObject orderInfoObj = orderInfoArray.getJSONObject(i);
				TbShoppingCar car = new TbShoppingCar();
				Integer toolId = orderInfoObj.getInt("toolId");
				car.setToolId(toolId);
				car.setEnterpriseId(null);
				car.setIsPay(0);
				car.setDelFlag(0);
				car.setStatus(2);
				car.setNum(orderInfoObj.getString("num"));
				car.setInputTime(now);
				car.setEnterpriseId(sellEnterpriseId);
				String price = StringUtils.isEmpty(orderInfoObj.getString("price")) ? "0" : orderInfoObj.getString("price");
				car.setPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				shoppingCarDao.save(car);
				
				TbResOrderCar tbResOrderCar = new TbResOrderCar();
				tbResOrderCar.setCarId(car.getId());
				tbResOrderCar.setDelFlag(0);
				tbResOrderCar.setOrderId(toolOrder.getId());
				resOrderCarDao.save(tbResOrderCar);
				
				TbTool tool = toolDao.findById(toolId);
				BigDecimal oldNumber = BigDecimal.ZERO;
				try{
					if (tool.getNumber() != null) {
						oldNumber = new BigDecimal(tool.getNumber());
					}
				}catch (NumberFormatException e) {
					// TODO: handle exception
				}
				tool.setNumber(String.valueOf(oldNumber.subtract(new BigDecimal(car.getNum())).intValue()));
				toolDao.update(tool);
				TbToolRecord toolRecord = new TbToolRecord();
				toolRecord.setEnterpriseId(sellEnterpriseId);
				toolRecord.setSupplierName(tool.getSupplierName());
				toolRecord.setUseName(loginUser.getName());
				toolRecord.setUseTime(now);
				toolRecord.setToolId(tool.getId());
				toolRecord.setRecordType(3);
				toolRecord.setAllNumber(tool.getNumber());
				toolRecord.setNumber(car.getNum());
				toolRecord.setDelFlag(0);
				toolRecord.setInputTime(new Date());
				toolRecord.setOutName(linkOrderInfo == null ? null : linkOrderInfo.getName());
				toolRecord.setOutMobile(linkOrderInfo == null ? null : linkOrderInfo.getLinkMobile());
				toolRecord.setPrice(price);
				toolRecordDao.save(toolRecord);
			}
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddToBOrderParam myParam = (AddToBOrderParam)logicParam;
		String idcard = myParam.getIdcard();
		String name = myParam.getName();
		String sellEnterpriseId = myParam.getSellEnterpriseId();
		String buyEnterpriseId = myParam.getBuyEnterpriseId();
		if (StringUtil.isNullOrEmpty(idcard,name)) {
			res.add("status", -1).add("msg", "实名信息不能为空");
			return false;
		}
		if (StringUtil.isNullOrEmpty(sellEnterpriseId,buyEnterpriseId)) {
			res.add("status", -1).add("msg", "企业id不能为空");
			return false;
		}
		return true;
	}

}
