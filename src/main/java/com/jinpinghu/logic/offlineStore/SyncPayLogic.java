package com.jinpinghu.logic.offlineStore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.jinpinghu.common.tools.DateTimeUtil;
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
import com.jinpinghu.logic.offlineStore.param.SyncPayParam;

import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class SyncPayLogic extends BaseZLogic {

	public SyncPayLogic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		SyncPayParam myParam = (SyncPayParam) logicParam;
		String jsonData = myParam.getJsonData();
		String userId = myParam.getUserId();
		
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
		
		Gson g = new Gson();
		JsonData dataBean = g.fromJson(jsonData, JsonData.class);
		if(dataBean==null) {
			res.add("status", 1);
			res.add("msg", "成功！");
			return true;
		}
		List<JsonDataBean> dataList = dataBean.getData();
		if(dataList==null||dataList.size()==0) {
			res.add("status", 1);
			res.add("msg", "成功！");
			return true;
		}
		
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		TbUserDao tbUserDao = new TbUserDao(em);
		TbShoppingCarDao shoppingCarDao = new TbShoppingCarDao(em);
		TbResOrderCarDao resOrderCarDao = new TbResOrderCarDao(em);
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		TbToolDao toolDao = new TbToolDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		AppUtilDao uDao = new AppUtilDao(em);
		TbResEnterpriseLinkorderinfoDao resEnterpriseLinkorderinfoDao = new TbResEnterpriseLinkorderinfoDao(em);
		TbEnterpriseUserProductionInfoDao enterpriseUserProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
		
		
		AppUtil appUtil = uDao.findByKey("tool_record_to_nzy");
		String peoples = appUtil.getValue();
		
		AppUtil checkSwitch = uDao.findByKey("check_switch");
		String checkSwitchValue = checkSwitch.getValue();
		
		TbUser user = tbUserDao.checkLogin2(userId);
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> tMap;
		for (int i = 0; i < dataList.size(); i++) {
			tMap = new HashMap<>();
			JsonDataBean jsonDataBean = dataList.get(i);
			try {
				// 保存订单信息
				TbToolOrder o = toolOrderDao.findByOrderNumber(jsonDataBean.getCode());
				if(o==null) {
					//保存订单信息
					TbLinkOrderInfo linkOrderInfo = null;
					if(!StringUtils.isEmpty(jsonDataBean.getIdcard())) {
						linkOrderInfo = linkOrderInfoDao.findByIdcard(jsonDataBean.getIdcard());
						if (linkOrderInfo == null) {
							linkOrderInfo = new TbLinkOrderInfo();
							linkOrderInfo.setDelFlag(0);
							linkOrderInfo.setLegalPersonIdcard(jsonDataBean.getIdcard());
							linkOrderInfo.setIdcardPic(jsonDataBean.getPic());
							linkOrderInfo.setInputTime(new Date());
							linkOrderInfo.setLastPic(jsonDataBean.getPic());
							linkOrderInfo.setAddress(jsonDataBean.getAddress());
							linkOrderInfo.setName(jsonDataBean.getName());
							linkOrderInfo.setNation(jsonDataBean.getNation());
							linkOrderInfo.setSex(jsonDataBean.getSex());
							linkOrderInfo.setUpdateTime(new Date());
							linkOrderInfo.setCountry(null);
							linkOrderInfo.setLinkPeople(null);
							linkOrderInfo.setLinkMobile(null);
							linkOrderInfo.setType(2);
							linkOrderInfo.setIsValidation("1");
							linkOrderInfoDao.save(linkOrderInfo);
							
							TbResEnterpriseLinkorderinfo resEnterpriseLinkorderinfo = resEnterpriseLinkorderinfoDao.findByEnterpriseIdAndLinkOrderInfoId(StringUtils.isEmpty(jsonDataBean.getShopId()) ? null : Integer.valueOf(jsonDataBean.getShopId()), linkOrderInfo.getId());
							if (resEnterpriseLinkorderinfo == null) {
								resEnterpriseLinkorderinfo = new TbResEnterpriseLinkorderinfo();
								resEnterpriseLinkorderinfo.setEnterpriseId(StringUtils.isEmpty(jsonDataBean.getShopId()) ? null : Integer.valueOf(jsonDataBean.getShopId()));
								resEnterpriseLinkorderinfo.setLinkOrderInfoId(linkOrderInfo.getId());
								resEnterpriseLinkorderinfoDao.save(resEnterpriseLinkorderinfo);
							}
							em.flush();
						}else {
							if (!StringUtils.isEmpty(jsonDataBean.getAddress())) {
								linkOrderInfo.setAddress(jsonDataBean.getAddress());
							}
							if (!StringUtils.isEmpty(jsonDataBean.getName())) {
								linkOrderInfo.setName(jsonDataBean.getName());
							}
							if (!StringUtils.isEmpty(jsonDataBean.getNation())) {
								linkOrderInfo.setNation(jsonDataBean.getNation());
							}
							if (!StringUtils.isEmpty(jsonDataBean.getSex())) {
								linkOrderInfo.setSex(jsonDataBean.getSex());
							}
							linkOrderInfoDao.update(linkOrderInfo);
						}
						
					}
					List<Integer> userEnterpriseIdList = enterpriseUserProductionInfoDao.getEnterpriseIdByIdcard(jsonDataBean.getIdcard());
					TbToolOrder toolOrder = new TbToolOrder();
					toolOrder.setDelFlag(0);
					toolOrder.setOrderNumber(jsonDataBean.getCode());
					toolOrder.setPlantEnterpriseId(linkOrderInfo == null ? null : linkOrderInfo.getId());
					toolOrder.setToolEnterpriseId(StringUtils.isEmpty(jsonDataBean.getShopId()) ? null : Integer.valueOf(jsonDataBean.getShopId()));
					toolOrder.setStatus(4);
					toolOrder.setAddPeople(user.getName());
					toolOrder.setPrice(jsonDataBean.getResultAmount());
					toolOrder.setCheck(0);
					toolOrder.setType(2);
					toolOrder.setPic(jsonDataBean.getPic());
					toolOrder.setInputTime(DateTimeUtil.formatSelf(jsonDataBean.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
					
					
					toolOrderDao.save(toolOrder);
					
					String toolIds = jsonDataBean.getGoodsIds();
					String accountNums = jsonDataBean.getAccountNums();
					String prices = jsonDataBean.getPrices();
					String finalRatioFees = jsonDataBean.getFinalRatioFees();
					
					//保存订单商品信息
					String[] toolIdArray = toolIds.split(",");
					String[] accountNumArray = accountNums.split(",");
					String[] priceArray = prices.split(",");
					String[] finalRatioFeeArray = finalRatioFees.split(",");
					
//					BigDecimal allPrice = BigDecimal.ZERO;
					BigDecimal allUniformPrice = BigDecimal.ZERO;
					Object[] uniforPriceData = null;
					if (!userEnterpriseIdList.isEmpty()) {
						uniforPriceData = enterpriseUserProductionInfoDao.getUniformPriceData(userEnterpriseIdList);
					}
					for (int j = 0; j < toolIdArray.length; j++) {
						TbTool tool = toolDao.findById(Integer.valueOf(toolIdArray[j]));
						TbShoppingCar car = new TbShoppingCar();
						car.setToolId(Integer.valueOf(toolIdArray[j]));
						car.setEnterpriseId(null);
						car.setIsPay(0);
						car.setDelFlag(0);
						car.setStatus(2);
						car.setNum(accountNumArray[j]);
						car.setInputTime(DateTimeUtil.formatSelf(jsonDataBean.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
						car.setEnterpriseId(StringUtils.isEmpty(jsonDataBean.getShopId()) ? null : Integer.valueOf(jsonDataBean.getShopId()));


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
						toolRecord.setEnterpriseId(StringUtils.isEmpty(jsonDataBean.getShopId()) ? null : Integer.valueOf(jsonDataBean.getShopId()));
						toolRecord.setSupplierName(tool.getSupplierName());
						toolRecord.setUseName(loginUser.getName());
						toolRecord.setUseTime(DateTimeUtil.formatSelf(jsonDataBean.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
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
						
						TbEnterprise enterprise = enterpriseDao.findById(toolRecord.getEnterpriseId());
						
						if (!StringUtils.isEmpty(peoples) && linkOrderInfo != null && !StringUtils.isEmpty(linkOrderInfo.getName()) 
								&& peoples.indexOf(linkOrderInfo.getName()) >= 0) {
							try {
//								String url = "http://121.42.25.6:8082/NongZhiYun/api/tool/addToolNumFromJinpinghu.do";
//								StringBuilder param = new StringBuilder();
//								param.append("code=").append(StringUtils.trimToEmpty(tool.getCode())).append("&");
//								param.append("toolNum=").append(accountNumArray[i]).append("&");
//								param.append("toolName=").append(StringUtils.trimToEmpty(tool.getName())).append("&");
//								param.append("userName=").append(linkOrderInfo.getName()).append("&");
//								param.append("price=").append(price).append("&");
//								String[] numUnit = getSpecNumUnit(tool.getSpecification());
//								param.append("spec=").append(numUnit[0]).append("&");
//								param.append("unit=").append(numUnit[1]).append("&");
//								param.append("supplierName=").append(enterprise == null ? "" : StringUtils.trimToEmpty(enterprise.getName()));
//								HttpRequestUtil.sendPost(url, param.toString(), "utf-8");
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
					
					
					toolOrder.setTimeAudit(new Date());
					toolOrder.setTimeSend(new Date());
					toolOrder.setTimePay(new Date());
					toolOrder.setTimeComplete(new Date());
//					toolOrder.setPrice(allPrice.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
					
					toolOrderDao.update(toolOrder);
					em.flush();
					tMap.put("status", "SUCCESS");
					
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
							res.add("warning", jo.containsKey("msg")? jo.getString("msg"):"");
						}
					}
					
					
				}else {
					tMap.put("status", "REPEAT");
				}
			} catch (Exception e) {
				e.printStackTrace();
				tMap.put("status", "FAIL");
			}
			tMap.put("tableName", "");
			tMap.put("code", jsonDataBean.getCode());
			resultList.add(tMap);
		}
		
		
		res.add("result", resultList);
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
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SyncPayParam myParam = (SyncPayParam) logicParam;
		String jsonData = myParam.getJsonData();
		if (StringUtils.isEmpty(jsonData)) {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
	class JsonData{
		private List<JsonDataBean> data;

		public List<JsonDataBean> getData() {
			return data;
		}

		public void setData(List<JsonDataBean> data) {
			this.data = data;
		}
		
	}

	class JsonDataBean {
		private String shopId;
		private String outType;
		private String cardId;
		private String code;
		private String orderMode;
		private String orderRatio;
		private String sourceAmount;
		private String resultAmount;
		private String goodsIds;
		private String barCodes;
		private String goodsTypes;
		private String originalGoodsNames;
		private String accountNums;
		private String accountUnits;
		private String accountWeights;
		private String goodsModes;
		private String goodsRatios;
		private String prices;
		private String fees;
		private String finalRatioFees;
		private String authCode;
		private String orderTime;
		private String idcard;
		private String name;
		private String sex;
		private String nation;
		private String address;
		private String pic;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getNation() {
			return nation;
		}

		public void setNation(String nation) {
			this.nation = nation;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getOutType() {
			return outType;
		}

		public void setOutType(String outType) {
			this.outType = outType;
		}

		public String getCardId() {
			return cardId;
		}

		public void setCardId(String cardId) {
			this.cardId = cardId;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getOrderMode() {
			return orderMode;
		}

		public void setOrderMode(String orderMode) {
			this.orderMode = orderMode;
		}

		public String getOrderRatio() {
			return orderRatio;
		}

		public void setOrderRatio(String orderRatio) {
			this.orderRatio = orderRatio;
		}

		public String getSourceAmount() {
			return sourceAmount;
		}

		public void setSourceAmount(String sourceAmount) {
			this.sourceAmount = sourceAmount;
		}

		public String getResultAmount() {
			return resultAmount;
		}

		public void setResultAmount(String resultAmount) {
			this.resultAmount = resultAmount;
		}

		public String getGoodsIds() {
			return goodsIds;
		}

		public void setGoodsIds(String goodsIds) {
			this.goodsIds = goodsIds;
		}

		public String getBarCodes() {
			return barCodes;
		}

		public void setBarCodes(String barCodes) {
			this.barCodes = barCodes;
		}

		public String getGoodsTypes() {
			return goodsTypes;
		}

		public void setGoodsTypes(String goodsTypes) {
			this.goodsTypes = goodsTypes;
		}

		public String getOriginalGoodsNames() {
			return originalGoodsNames;
		}

		public void setOriginalGoodsNames(String originalGoodsNames) {
			this.originalGoodsNames = originalGoodsNames;
		}

		public String getAccountNums() {
			return accountNums;
		}

		public void setAccountNums(String accountNums) {
			this.accountNums = accountNums;
		}

		public String getAccountUnits() {
			return accountUnits;
		}

		public void setAccountUnits(String accountUnits) {
			this.accountUnits = accountUnits;
		}

		public String getAccountWeights() {
			return accountWeights;
		}

		public void setAccountWeights(String accountWeights) {
			this.accountWeights = accountWeights;
		}

		public String getGoodsModes() {
			return goodsModes;
		}

		public void setGoodsModes(String goodsModes) {
			this.goodsModes = goodsModes;
		}

		public String getGoodsRatios() {
			return goodsRatios;
		}

		public void setGoodsRatios(String goodsRatios) {
			this.goodsRatios = goodsRatios;
		}

		public String getPrices() {
			return prices;
		}

		public void setPrices(String prices) {
			this.prices = prices;
		}

		public String getFees() {
			return fees;
		}

		public void setFees(String fees) {
			this.fees = fees;
		}

		public String getFinalRatioFees() {
			return finalRatioFees;
		}

		public void setFinalRatioFees(String finalRatioFees) {
			this.finalRatioFees = finalRatioFees;
		}

		public String getAuthCode() {
			return authCode;
		}

		public void setAuthCode(String authCode) {
			this.authCode = authCode;
		}

		public String getOrderTime() {
			return orderTime;
		}

		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}

		public String getIdcard() {
			return idcard;
		}

		public void setIdcard(String idcard) {
			this.idcard = idcard;
		}

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}

	}

}
