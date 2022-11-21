package com.jinpinghu.logic.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.ChangeToolNumberUtil;
import com.jinpinghu.db.bean.TbResOrderCar;
import com.jinpinghu.db.bean.TbShoppingCar;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbResOrderCarDao;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.AddCompleteOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddCompleteOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddCompleteOrderParam myParam = (AddCompleteOrderParam)logicParam;
		String carJa =myParam.getCarJa();
		Integer linkOrderInfoId = StringUtils.isEmpty(myParam.getLinkOrderInfoId())?null:Integer.valueOf(myParam.getLinkOrderInfoId());
//		String orderInfoId = myParam.getOrderInfoId();
//		String address = myParam.getAddress();
//		String linkMobile = myParam.getLinkMobile();
//		String legalPerson = myParam.getLegalPerson();
//		String legalPersonIdcard = myParam.getLegalPersonIdcard();
//		String linkPeople = myParam.getLinkPeople();
//		String name = myParam.getName();
//		String creditCode = myParam.getCreditCode();
//		String file = myParam.getFile();
//		String area = myParam.getArea();
//		Integer type = StringUtils.isEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		String userId =myParam.getUserId();
		String apiKey =myParam.getApiKey();
		Date inputTime = new Date();
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser user = tbUserDao.checkLogin(userId, apiKey);
		if(user==null){
			res.add("status", -1);
			res.add("msg", "未知错误，获取用户信息失败");
			return true;
		}
//		TbLinkOrderInfo linkOrderInfo = null;
//		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
//		if(!StringUtils.isEmpty(orderInfoId)) {
//			linkOrderInfo = linkOrderInfoDao.findById(Integer.valueOf(orderInfoId));
//			linkOrderInfo.setAddress(address);
//			linkOrderInfo.setCreditCode(creditCode);
//			linkOrderInfo.setDelFlag(0);
//			linkOrderInfo.setLegalPerson(legalPerson);
//			linkOrderInfo.setLegalPersonIdcard(legalPersonIdcard);
//			linkOrderInfo.setLinkMobile(linkMobile);
//			linkOrderInfo.setLinkPeople(linkPeople);
//			linkOrderInfo.setName(name);
//			linkOrderInfo.setInputTime(new Date());
//			linkOrderInfo.setArea(area);
//			linkOrderInfo.setType(type);
//			linkOrderInfoDao.update(linkOrderInfo);
//		}else {
//			linkOrderInfo = new TbLinkOrderInfo();
//			linkOrderInfo.setAddress(address);
//			linkOrderInfo.setCreditCode(creditCode);
//			linkOrderInfo.setDelFlag(0);
//			linkOrderInfo.setLegalPerson(legalPerson);
//			linkOrderInfo.setLegalPersonIdcard(legalPersonIdcard);
//			linkOrderInfo.setLinkMobile(linkMobile);
//			linkOrderInfo.setLinkPeople(linkPeople);
//			linkOrderInfo.setName(name);
//			linkOrderInfo.setInputTime(new Date());
//			linkOrderInfo.setArea(area);
//			linkOrderInfo.setType(type);
//			linkOrderInfoDao.save(linkOrderInfo);
//		}
		
//		TbFileDao tfDao = new TbFileDao(em);
//		TbResOrderInfoFileDao trfgDao =new TbResOrderInfoFileDao(em);
//		
//		List<TbFile> tfs =tfDao.findByOrderInfoId(linkOrderInfoId);
//		List<TbResOrderInfoFile> trgfs =trfgDao.findByOrderInfoId(linkOrderInfoId);
//		if(trgfs!=null){
//			for(TbResOrderInfoFile trgf:trgfs){
//				trfgDao.delete(trgf);
//			}
//		}
//		if(tfs!=null){
//			for(TbFile tbFile:tfs){
//				tfDao.delete(tbFile);
//			}
//		}
		
//		if(!StringUtils.isEmpty(file)){
//			JSONArray arrayF= JSONArray.fromObject(file);
//			if(arrayF.size()>0){
//				for(int i=0;i<arrayF.size();i++){
//					TbFile tfe =null;
//					JSONObject jsonObj=(JSONObject) arrayF.get(i);
//					tfe = new TbFile();
//					if(jsonObj.containsKey("fileName"))
//						tfe.setFileName(jsonObj.getString("fileName"));
//					if(jsonObj.containsKey("fileSize"))
//						tfe.setFileSize(jsonObj.getString("fileSize"));
//					if(jsonObj.containsKey("fileType"))
//						tfe.setFileType(jsonObj.getInt("fileType"));
//					if(jsonObj.containsKey("fileUrl"))
//						tfe.setFileUrl(jsonObj.getString("fileUrl"));
//					tfDao.save(tfe);
//					TbResOrderInfoFile trpf=new TbResOrderInfoFile();
//					trpf.setFileId(tfe.getId());
//					trpf.setOrderInfoId(linkOrderInfoId);
//					trpf.setDelFlag(0);
//					trfgDao.save(trpf);
//				}
//			}
//		}
		JSONArray ja = JSONArray.fromObject(carJa);
		TbShoppingCarDao tbShoppingCarDao = new TbShoppingCarDao(em);
		List<Integer> carId = new ArrayList<Integer>();
		if(ja!=null) {
			for(int i=0;i<ja.size();i++) {
				JSONObject carJo = (JSONObject)ja.get(i);
				TbToolDao toolDao = new TbToolDao(em);
				TbTool tool = toolDao.findById(carJo.getInt("toolId"));
				if(tool==null){
					res.add("status", -1);
					res.add("msg", "未知错误，获取商品信息失败");
					return true;
				}
				TbShoppingCar car = new TbShoppingCar();
				car.setToolId(carJo.getInt("toolId"));
				car.setEnterpriseId(null);
				car.setIsPay(0);
				car.setDelFlag(0);
				car.setStatus(2);
				car.setNum(carJo.getString("num"));
//				if(new BigDecimal(car.getNum()).compareTo(new BigDecimal(StringUtils.isEmpty(tool.getNumber())?"0":tool.getNumber()))==1) {
//					res.add("status", -1);
//					res.add("msg", "库存不足");
//					return false;
//				}
				String price = StringUtils.isEmpty(tool.getPrice())?"0":tool.getPrice();
				car.setPrice(new BigDecimal(price).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				tbShoppingCarDao.save(car);
				carId.add(car.getId());
			}
		}
		
		String orderNumber ="1002"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6;
		
		
		TbToolDao toolDao = new TbToolDao(em);
		TbShoppingCarDao tscDao = new TbShoppingCarDao(em);
		TbToolOrderDao ttoDao =new TbToolOrderDao(em);
		TbResOrderCarDao resOrderCarDao = new TbResOrderCarDao(em);
		List<Object[]> tscList = tscDao.findByCarId(carId);
		if(tscList==null||tscList.size()==0){
			res.add("status", -1);
			res.add("msg", "请选择购买的商品");
			return false;
		}
		
		if(tscList!=null) {
			for(int i=0;i<tscList.size();i++) {
				Object[] jo = tscList.get(i);
				Integer toolEnterpriseId = Integer.valueOf(jo[1].toString());	
				//新加订单
				TbToolOrder ttl = new TbToolOrder();
				ttl.setDelFlag(0);
				ttl.setInputTime(inputTime);
				ttl.setOrderNumber(orderNumber+"");
				ttl.setPlantEnterpriseId(linkOrderInfoId);
				ttl.setToolEnterpriseId(toolEnterpriseId);
				ttl.setStatus(4);
				ttl.setAddPeople(user.getName());
				ttl.setPrice(jo[2].toString());
				ttl.setCheck(0);
				ttl.setType(2);
				ttoDao.save(ttl);
				BigDecimal checkMoney = BigDecimal.ZERO;
				if(jo[0]!=null) {
					String[] split2 = jo[0].toString().split(",");
					List<String> carIds =Arrays.asList(split2);
					//添加关联
					for(String cid:carIds) {
						TbResOrderCar tbResOrderCar = new TbResOrderCar();
						tbResOrderCar.setCarId(Integer.valueOf(cid));
						tbResOrderCar.setDelFlag(0);
						tbResOrderCar.setOrderId(ttl.getId());
						resOrderCarDao.save(tbResOrderCar);
						TbShoppingCar car = tscDao.findById(Integer.valueOf(cid));
						ChangeToolNumberUtil init = ChangeToolNumberUtil.init();
						init.minusNumber(car.getToolId(), new BigDecimal(car.getNum()), em,ttl.getPlantEnterpriseId(),user.getName());	
						TbTool tool = toolDao.findById(car.getToolId());
						if(tool!=null&&tool.getUniformPrice().equals("1")) {
							checkMoney= checkMoney.add(new BigDecimal(car.getPrice()).multiply(new BigDecimal("0.12")));
						}
					}
					ttl.setCheckMoney(checkMoney.setScale(1, BigDecimal.ROUND_HALF_UP)+"");
					ttoDao.update(ttl);
				}
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
	
		
		return true;
	}
}
