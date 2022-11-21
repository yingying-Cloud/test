package com.jinpinghu.logic.userProduction;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbEnterpriseUserProductionInfo;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.userProduction.param.AddEnterpriseUserProductionParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddEnterpriseUserProductionLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddEnterpriseUserProductionParam myParam = (AddEnterpriseUserProductionParam)logicParam;
		String enterpriseId = myParam.getEnterpriseId();
		String village = myParam.getVillage();
		String dscd = myParam.getDscd();
		String area = myParam.getArea();
		String confirmArea = myParam.getConfirmArea();
		String inflowArea = myParam.getInflowArea();
		String outflowArea = myParam.getOutflowArea();
		String userJa = myParam.getUserJa();
		String name = myParam.getName();
		Integer enterpriseType = StringUtils.isNullOrEmpty(myParam.getEnterpriseType()) ? null : Integer.valueOf(myParam.getEnterpriseType());
		BigDecimal nyLimitAmount = StringUtils.isNullOrEmpty(myParam.getNyLimitAmount()) ? null : new BigDecimal(myParam.getNyLimitAmount());
		BigDecimal nmLimitAmount = StringUtils.isNullOrEmpty(myParam.getNmLimitAmount()) ? null : new BigDecimal(myParam.getNmLimitAmount());
		
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		TbEnterprise enterprise = null;
		if(!StringUtils.isNullOrEmpty(enterpriseId)) {
			enterprise=enterpriseDao.findById(Integer.valueOf(enterpriseId));
		}
		
		if(enterprise!=null) {
			enterprise.setArea(StringUtils.isNullOrEmpty(area)?null:Double.valueOf(area));
			enterprise.setConfirmArea(StringUtils.isNullOrEmpty(confirmArea)?null:Double.valueOf(confirmArea));
			enterprise.setInflowArea(StringUtils.isNullOrEmpty(inflowArea)?null:Double.valueOf(inflowArea));
			enterprise.setOutflowArea(StringUtils.isNullOrEmpty(outflowArea)?null:Double.valueOf(outflowArea));
			enterprise.setNyLimitAmount(nyLimitAmount);
			enterprise.setNmLimitAmount(nmLimitAmount);
			enterprise.setDscd(dscd);
			enterprise.setVillage(village);
			enterpriseDao.update(enterprise);
		}else {
			enterprise = new TbEnterprise();
			enterprise.setName(name);
			enterprise.setInputTime(new Date());
			enterprise.setStatus(0);
			enterprise.setArea(StringUtils.isNullOrEmpty(area)?null:Double.valueOf(area));
			enterprise.setConfirmArea(StringUtils.isNullOrEmpty(confirmArea)?null:Double.valueOf(confirmArea));
			enterprise.setInflowArea(StringUtils.isNullOrEmpty(inflowArea)?null:Double.valueOf(inflowArea));
			enterprise.setOutflowArea(StringUtils.isNullOrEmpty(outflowArea)?null:Double.valueOf(outflowArea));
			enterprise.setNyLimitAmount(nyLimitAmount);
			enterprise.setNmLimitAmount(nmLimitAmount);
			enterprise.setDscd(dscd);
			enterprise.setVillage(village);
			enterprise.setDelFlag(0);
			enterprise.setEnterpriseType(enterpriseType);
			enterprise.setState(1);
			enterprise.setType("1");
			enterpriseDao.save(enterprise);
		}
			
			TbEnterpriseUserProductionInfoDao userProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
//			userProductionInfoDao.delUser(enterprise.getId());
			if(!StringUtils.isNullOrEmpty(userJa)){
				JSONArray arrayF= JSONArray.fromObject(userJa);
				if(arrayF.size()>0){
					for(int i=0;i<arrayF.size();i++){
						JSONObject jsonObj=(JSONObject) arrayF.get(i);
						String userIdCard = jsonObj.getString("userIdCard");
						Integer id = jsonObj.containsKey("id")?!StringUtils.isNullOrEmpty(jsonObj.getString("id"))?jsonObj.getInt("id"):null:null;
						TbEnterpriseUserProductionInfo userProductionInfo = userProductionInfoDao.getByUserIdCardType(userIdCard,2,id);
						if(userProductionInfo!=null) {
							TbEnterprise tbEnterprise = enterpriseDao.findById(userProductionInfo.getEnterpriseId());
							res.add("status", 2).add("msg","此人已在\""+tbEnterprise.getName()+"\"生产主体中是员工！");
							return false;
						}
						
						userProductionInfo = userProductionInfoDao.getById(id);
						
						
						if(userProductionInfo!=null) {
							if (enterpriseType != null && enterpriseType == 100) {
								TbLinkOrderInfo orderInfo = linkOrderInfoDao.findByIdcard(userProductionInfo.getUserIdCard());
								if (orderInfo != null) {
									orderInfo.setName(jsonObj.getString("userName"));
									orderInfo.setLinkPeople(jsonObj.getString("userName"));
									orderInfo.setLegalPersonIdcard(userIdCard);
									linkOrderInfoDao.update(orderInfo);
								}
							}
							userProductionInfo.setEnterpriseId(enterprise.getId());
							if(jsonObj.containsKey("userName"))
								userProductionInfo.setUserName(jsonObj.getString("userName"));
							if(jsonObj.containsKey("userIdCard"))
								userProductionInfo.setUserIdCard(jsonObj.getString("userIdCard"));
							if(jsonObj.containsKey("type"))
								userProductionInfo.setType(jsonObj.getInt("type"));
							userProductionInfoDao.update(userProductionInfo);
						}else {
							userProductionInfo =new TbEnterpriseUserProductionInfo();
							userProductionInfo.setEnterpriseId(enterprise.getId());
							if(jsonObj.containsKey("userName"))
								userProductionInfo.setUserName(jsonObj.getString("userName"));
							if(jsonObj.containsKey("userIdCard"))
								userProductionInfo.setUserIdCard(jsonObj.getString("userIdCard"));
							if(jsonObj.containsKey("type"))
								userProductionInfo.setType(jsonObj.getInt("type"));
							userProductionInfoDao.save(userProductionInfo);
						}
						
						//当企业为集体时，添加人员信息到实名信息表
						if (enterpriseType != null && enterpriseType == 100) {
							TbLinkOrderInfo orderInfo = linkOrderInfoDao.findByIdcard(userIdCard);
							if (orderInfo == null) {
								orderInfo = new TbLinkOrderInfo();
								orderInfo.setDelFlag(0);
								orderInfo.setLegalPersonIdcard(userIdCard);
								orderInfo.setIdcardPic("https://zhihuishouyin.oss-cn-beijing.aliyuncs.com/1599729063251.jpg");
								orderInfo.setInputTime(new Date());
								orderInfo.setLastPic("https://zhihuishouyin.oss-cn-beijing.aliyuncs.com/1599729063251.jpg");
								orderInfo.setAddress(village);
								orderInfo.setName(name);
								orderInfo.setNation("汉族");
								orderInfo.setSex("男");
								orderInfo.setUpdateTime(new Date());
								orderInfo.setCountry("中华人民共和国");
								orderInfo.setLinkPeople(name);
								orderInfo.setLinkMobile(null);
								orderInfo.setType(2);
								orderInfo.setIsValidation("1");
								linkOrderInfoDao.save(orderInfo);
							}
						}
						
						
					}
				}
			}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}


	/*@Override
	public boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
							EntityManager em)  throws Exception {

		return true;
	}*/
}
