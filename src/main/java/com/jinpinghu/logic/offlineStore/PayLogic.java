package com.jinpinghu.logic.offlineStore;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.db.bean.AppUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbResEnterpriseLinkorderinfo;
import com.jinpinghu.db.bean.TbResOrderCar;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbShoppingCar;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.AppUtilDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbResEnterpriseLinkorderinfoDao;
import com.jinpinghu.db.dao.TbResOrderCarDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.offlineStore.param.PayParam;

import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class PayLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		
		PayParam myParam = (PayParam)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		String idcard = myParam.getIdcard();
		String toolIds = myParam.getToolIds();
		String accountNums = myParam.getAccountNums();
		String prices = myParam.getPrices();
		String userId = myParam.getUserId();
		String resultAmount = myParam.getResultAmount();
		String code = myParam.getCode();
		String name = myParam.getName();
		String address = myParam.getAddress();
		String sex = myParam.getSex();
		String nation = myParam.getNation();
		String pic= myParam.getPic();
		String finalRatioFees = myParam.getFinalRatioFees();
		
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbRole role = resUserRoleDao.findByUserTabId(loginUser.getId());
		TbEnterprise checkEnterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		if (role != null && role.getId() == 5) {
			if(checkEnterprise != null && checkEnterprise.getState() ==0) {
				res.add("status", -1);
				res.add("msg", "此账号已停用");
				return false;
			}
		}
		
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		
		TbToolOrder toolOrder = toolOrderDao.findByOrderNumber(code);
		if (toolOrder != null) {
			res.add("status", 1);
			res.add("msg", "操作成功");
			return false;
		}
		TbUserDao tbUserDao = new TbUserDao(em);
		TbShoppingCarDao shoppingCarDao = new TbShoppingCarDao(em);
		TbResOrderCarDao resOrderCarDao = new TbResOrderCarDao(em);
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		TbToolDao toolDao = new TbToolDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbResEnterpriseLinkorderinfoDao resEnterpriseLinkorderinfoDao = new TbResEnterpriseLinkorderinfoDao(em);
		TbEnterpriseUserProductionInfoDao enterpriseUserProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
		
		List<Integer> userEnterpriseIdList = enterpriseUserProductionInfoDao.getEnterpriseIdByIdcard(idcard);
		
		AppUtilDao uDao = new AppUtilDao(em);
		
		AppUtil appUtil = uDao.findByKey("tool_record_to_nzy");
		String peoples = appUtil.getValue();
		
		AppUtil checkSwitch = uDao.findByKey("check_switch");
		String checkSwitchValue = checkSwitch.getValue();
		
		TbEnterprise enterprise = enterpriseDao.findById(enterpriseId);
		
		TbLinkOrderInfo linkOrderInfo = null;
		if(!StringUtils.isEmpty(idcard)) {
			linkOrderInfo = linkOrderInfoDao.findByIdcard(idcard);
			if (linkOrderInfo == null) {
				linkOrderInfo = new TbLinkOrderInfo();
				linkOrderInfo.setDelFlag(0);
				linkOrderInfo.setLegalPersonIdcard(idcard);
				linkOrderInfo.setIdcardPic(pic);
				linkOrderInfo.setInputTime(new Date());
				linkOrderInfo.setLastPic(pic);
				linkOrderInfo.setAddress(address);
				linkOrderInfo.setName(name);
				linkOrderInfo.setNation(nation);
				linkOrderInfo.setSex(sex);
				linkOrderInfo.setUpdateTime(new Date());
				linkOrderInfo.setCountry(null);
				linkOrderInfo.setLinkPeople(name);
				linkOrderInfo.setLinkMobile(null);
				linkOrderInfo.setType(2);
				linkOrderInfo.setIsValidation("1");
				linkOrderInfoDao.save(linkOrderInfo);
				
				TbResEnterpriseLinkorderinfo resEnterpriseLinkorderinfo = resEnterpriseLinkorderinfoDao.findByEnterpriseIdAndLinkOrderInfoId(enterprise== null ? null : enterprise.getId(), linkOrderInfo.getId());
				if (resEnterpriseLinkorderinfo == null) {
					if (enterprise != null) {
						resEnterpriseLinkorderinfo = new TbResEnterpriseLinkorderinfo();
						resEnterpriseLinkorderinfo.setEnterpriseId(enterprise.getId());
						resEnterpriseLinkorderinfo.setLinkOrderInfoId(linkOrderInfo.getId());
						resEnterpriseLinkorderinfoDao.save(resEnterpriseLinkorderinfo);
					}
				}
			}else {
				if (!StringUtils.isEmpty(address)) {
					linkOrderInfo.setAddress(address);
				}
				if (!StringUtils.isEmpty(name)) {
					linkOrderInfo.setName(name);
				}
				if (!StringUtils.isEmpty(nation)) {
					linkOrderInfo.setNation(nation);
				}
				if (!StringUtils.isEmpty(sex)) {
					linkOrderInfo.setSex(sex);
				}
				linkOrderInfoDao.update(linkOrderInfo);
			}
			
			
		}
		
		
		TbUser user = tbUserDao.checkLogin2(userId);
		
		Date now = new Date();

		//保存订单信息
		toolOrder = new TbToolOrder();
		toolOrder.setDelFlag(0);
		toolOrder.setOrderNumber(code+"");
		toolOrder.setPlantEnterpriseId(linkOrderInfo == null ? null : linkOrderInfo.getId());
		toolOrder.setToolEnterpriseId(enterpriseId);
		toolOrder.setStatus(4);
		toolOrder.setAddPeople(user.getName());
		toolOrder.setPrice(resultAmount);
		toolOrder.setCheck(0);
		toolOrder.setType(2);
		toolOrder.setPic(pic);
		toolOrder.setInputTime(now);
		
		
		toolOrderDao.save(toolOrder);
		
		//保存订单商品信息
		String[] toolIdArray = toolIds.split(",");
		String[] accountNumArray = accountNums.split(",");
		String[] priceArray = prices.split(",");
		String[] finalRatioFeeArray = finalRatioFees.split(",");
		
		
		BigDecimal allUniformPrice = BigDecimal.ZERO;
		Object[] uniforPriceData = null;
		if (!userEnterpriseIdList.isEmpty()) {
			uniforPriceData = enterpriseUserProductionInfoDao.getUniformPriceData(userEnterpriseIdList);
		}
		for (int i = 0; i < toolIdArray.length; i++) {
			TbShoppingCar car = new TbShoppingCar();
			Integer toolId = Integer.valueOf(toolIdArray[i]);
			TbTool tool = toolDao.findById(toolId);
			car.setToolId(toolId);
			car.setEnterpriseId(null);
			car.setIsPay(0);
			car.setDelFlag(0);
			car.setStatus(2);
			car.setNum(accountNumArray[i]);
			car.setInputTime(now);
			car.setEnterpriseId(enterpriseId);
			car.setUniformPrice(tool.getUniformPrice());
			
			String priceStr = StringUtils.isEmpty(finalRatioFeeArray[i]) ? "0" : finalRatioFeeArray[i];
			String originalPriceStr = StringUtils.isEmpty(priceArray[i]) ? "0" : priceArray[i];
			String numStr = StringUtils.isEmpty(accountNumArray[i]) ? "0" : accountNumArray[i];
			BigDecimal price = BigDecimal.ZERO;
			BigDecimal num = BigDecimal.ZERO;
			try {
				price = new BigDecimal(priceStr);
				num = new BigDecimal(numStr);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if ("1".equals(tool.getUniformPrice())) {
				if (userEnterpriseIdList.isEmpty()) {
					res.add("status", -1);
					res.add("msg", "当前客户没有零差价农资配额！");
					return false;
				}
				allUniformPrice = allUniformPrice.add(price);
			}
			car.setPrice(price.divide(num,2,BigDecimal.ROUND_HALF_UP).toString());
			car.setOriginalPrice(originalPriceStr);
			shoppingCarDao.save(car);
			
			
			TbResOrderCar tbResOrderCar = new TbResOrderCar();
			tbResOrderCar.setCarId(car.getId());
			tbResOrderCar.setDelFlag(0);
			tbResOrderCar.setOrderId(toolOrder.getId());
			resOrderCarDao.save(tbResOrderCar);
			
			
			BigDecimal oldNumber = BigDecimal.ZERO;
			try{
				if (tool.getNumber() != null) {
					oldNumber = new BigDecimal(tool.getNumber());
				}
			}catch (NumberFormatException e) {
				// TODO: handle exception
			}
			if ("1".equals(tool.getUniformPrice()) && (oldNumber.subtract(new BigDecimal(car.getNum()))).compareTo(BigDecimal.ZERO) < 0) {
				res.add("status", -1).add("msg", tool.getName()+"库存不足！");
				return false;
			}
			tool.setNumber(oldNumber.subtract(new BigDecimal(car.getNum())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			toolDao.update(tool);
			TbToolRecord toolRecord = new TbToolRecord();
			toolRecord.setEnterpriseId(enterpriseId);
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
			toolRecord.setPrice(price.divide(num,2,BigDecimal.ROUND_HALF_UP).toString());
			ZDao.saveAsy(toolRecord);
			
			if (!StringUtils.isEmpty(peoples) && linkOrderInfo != null && !StringUtils.isEmpty(linkOrderInfo.getName()) 
					&& peoples.indexOf(linkOrderInfo.getName()) >= 0) {
				try {
//					String url = "http://121.42.25.6:8082/NongZhiYun/api/tool/addToolNumFromJinpinghu.do";
//					StringBuilder param = new StringBuilder();
//					param.append("code=").append(StringUtils.trimToEmpty(tool.getCode())).append("&");
//					param.append("toolNum=").append(accountNumArray[i]).append("&");
//					param.append("toolName=").append(StringUtils.trimToEmpty(tool.getName())).append("&");
//					param.append("userName=").append(linkOrderInfo.getName()).append("&");
//					param.append("price=").append(price).append("&");
//					String[] numUnit = getSpecNumUnit(tool.getSpecification());
//					param.append("spec=").append(numUnit[0]).append("&");
//					param.append("unit=").append(numUnit[1]).append("&");
//					param.append("supplierName=").append(enterprise == null ? "" : StringUtils.trimToEmpty(enterprise.getName()));
//					HttpRequestUtil.sendPost(url, param.toString(), "utf-8");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
		
		if (uniforPriceData != null && allUniformPrice.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal limitPrice = BigDecimal.ZERO;
			BigDecimal buyPrice = BigDecimal.ZERO;
			try {limitPrice = new BigDecimal(uniforPriceData[0].toString());} catch (Exception e) {}
			try {buyPrice = new BigDecimal(uniforPriceData[1].toString());} catch (Exception e) {}
			if (limitPrice.subtract(allUniformPrice).subtract(buyPrice).compareTo(BigDecimal.ZERO) < 0) {
				res.add("status", -1).add("msg", "当前客户零差价农资配额不足！");
				return false;
			}
		}
		
		
		toolOrder.setTimeAudit(now);
		toolOrder.setTimeSend(now);
		toolOrder.setTimePay(now);
		toolOrder.setTimeComplete(now);
		
		toolOrderDao.update(toolOrder);
		
		em.flush();
		if ("1".equals(checkSwitchValue)) {
			Object[] te = toolOrderDao.findSyncById(toolOrder.getId());
			if(te!=null) {
				JSONObject param = new JSONObject();
				param.put("location",te[0]+"");
				param.put("enterpriseNum",te[1]+"");
				param.put("name",te[8]+";"+te[2]+"");
				param.put("num",Integer.valueOf(te[6]+""));
				param.put("numUtil","件");
				param.put("orderNumber",toolOrder.getOrderNumber());
				param.put("orderTime",te[5]+"");
				param.put("price",te[4]+"");
				param.put("ipNum",te[9]+"");
				List<JSONObject> list = toolOrderDao.findInOrderId(toolOrder.getId());
				param.put("detail",list);
				
				String url ="/agricultural/api/alarm/isAlarm";
				String sendPost = HttpRequestUtil.sendPostToken(url, param, "utf-8");
				JSONObject jo = JSONObject.fromObject(sendPost);
				res.add("warning", jo.containsKey("msg")?jo.getString("msg"):"");
			}
		}
		
		
		
		res.add("code", toolOrder.getOrderNumber());
		res.add("status", 1);
		res.add("msg", "成功！");	
		return true;
	}
	
	public static String[] getSpecNumUnit(String spec) {
		if (StringUtils.isEmpty(spec)) {
			return new String[]{"",""};
		}
		String numReg = "[^(0-9|\\*)]";
		String unitReg = "[^(a-z|A-Z|\\u4e00-\\u9fa5)]";
		String num = "";
		String unit = "";
		num = spec.replaceAll(numReg, "\\|");
		unit = spec.replaceAll(unitReg, "\\|").replace("x", "");
		String[] numArray = num.split("\\|");
		String[] unitArray = unit.split("\\|");
		BigDecimal returnNum = BigDecimal.ZERO;
		String returnUnit = "";
		for (int i = 0; i < numArray.length; i++) {
			try {
				if (returnNum.compareTo(BigDecimal.ZERO) == 1) {
//					if (numArray[i].indexOf("*") < 0) {
//						break;
//					}else {
//						returnNum = returnNum.multiply(new BigDecimal(numArray[i].replaceAll("*", "")));
//						break;
//					}
					break;
				}
				returnNum = returnNum.add(new BigDecimal(numArray[i]));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		for (int i = 0; i < unitArray.length; i++) {
			try {
				if (!StringUtils.isEmpty(returnUnit)) {
					break;
				}
				returnUnit = unitArray[i];
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return new String[] {returnNum.compareTo(BigDecimal.ZERO) == 1 ? returnNum.toString() : "1",StringUtils.isEmpty(returnUnit) ? "件" : returnUnit};
	}
	
	
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		PayParam myParam = (PayParam)logicParam;
		String enterpriseId = myParam.getEnterpriseId();
		Integer payType = myParam.getPayType();
		String code = myParam.getCode();
		if(StringUtils.isEmpty(enterpriseId)||payType==null
				||StringUtils.isEmpty(code)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");	
			return false;
		}
		return true;
	}
}
