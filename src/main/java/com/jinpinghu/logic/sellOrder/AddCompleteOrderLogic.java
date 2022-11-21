//package com.jinpinghu.logic.sellSellOrder;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import javax.persistence.EntityManager;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.jinpinghu.common.tools.ChangeToolNumberUtil;
//import com.jinpinghu.db.bean.TbFile;
//import com.jinpinghu.db.bean.TbLinkSellOrderInfo;
//import com.jinpinghu.db.bean.TbResSellOrderCar;
//import com.jinpinghu.db.bean.TbResSellOrderInfoFile;
//import com.jinpinghu.db.bean.TbShoppingCar;
//import com.jinpinghu.db.bean.TbTool;
//import com.jinpinghu.db.bean.TbToolSellOrder;
//import com.jinpinghu.db.bean.TbUser;
//import com.jinpinghu.db.dao.TbFileDao;
//import com.jinpinghu.db.dao.TbLinkSellOrderInfoDao;
//import com.jinpinghu.db.dao.TbResSellOrderCarDao;
//import com.jinpinghu.db.dao.TbResSellOrderInfoFileDao;
//import com.jinpinghu.db.dao.TbShoppingCarDao;
//import com.jinpinghu.db.dao.TbToolDao;
//import com.jinpinghu.db.dao.TbToolSellOrderDao;
//import com.jinpinghu.db.dao.TbUserDao;
//import com.jinpinghu.logic.BaseZLogic;
//import com.jinpinghu.logic.sellSellOrder.param.AddCompleteSellOrderParam;
//
//import fw.jbiz.ext.json.ZSimpleJsonObject;
//import fw.jbiz.logic.ZLogicParam;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//public class AddCompleteSellOrderLogic extends BaseZLogic{
//
//	@Override
//	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
//			EntityManager em) throws Exception {
//		AddCompleteSellOrderParam myParam = (AddCompleteSellOrderParam)logicParam;
//		String carJa =myParam.getCarJa();
//		String sellSellOrderInfoId = myParam.getSellOrderInfoId();
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
//		String userId =myParam.getUserId();
//		String apiKey =myParam.getApiKey();
//		Date inputTime = new Date();
//		TbUserDao tbUserDao = new TbUserDao(em);
//		TbUser user = tbUserDao.checkLogin(userId, apiKey);
//		if(user==null){
//			res.add("status", -1);
//			res.add("msg", "未知错误，获取用户信息失败");
//			return true;
//		}
//		TbLinkSellOrderInfo linkSellOrderInfo = null;
//		TbLinkSellOrderInfoDao linkSellOrderInfoDao = new TbLinkSellOrderInfoDao(em);
//		if(!StringUtils.isEmpty(sellSellOrderInfoId)) {
//			linkSellOrderInfo = linkSellOrderInfoDao.findById(Integer.valueOf(sellSellOrderInfoId));
//			linkSellOrderInfo.setAddress(address);
//			linkSellOrderInfo.setCreditCode(creditCode);
//			linkSellOrderInfo.setDelFlag(0);
//			linkSellOrderInfo.setLegalPerson(legalPerson);
//			linkSellOrderInfo.setLegalPersonIdcard(legalPersonIdcard);
//			linkSellOrderInfo.setLinkMobile(linkMobile);
//			linkSellOrderInfo.setLinkPeople(linkPeople);
//			linkSellOrderInfo.setName(name);
//			linkSellOrderInfo.setInputTime(new Date());
//			linkSellOrderInfo.setArea(area);
//			linkSellOrderInfo.setType(type);
//			linkSellOrderInfoDao.update(linkSellOrderInfo);
//		}else {
//			linkSellOrderInfo = new TbLinkSellOrderInfo();
//			linkSellOrderInfo.setAddress(address);
//			linkSellOrderInfo.setCreditCode(creditCode);
//			linkSellOrderInfo.setDelFlag(0);
//			linkSellOrderInfo.setLegalPerson(legalPerson);
//			linkSellOrderInfo.setLegalPersonIdcard(legalPersonIdcard);
//			linkSellOrderInfo.setLinkMobile(linkMobile);
//			linkSellOrderInfo.setLinkPeople(linkPeople);
//			linkSellOrderInfo.setName(name);
//			linkSellOrderInfo.setInputTime(new Date());
//			linkSellOrderInfo.setArea(area);
//			linkSellOrderInfo.setType(type);
//			linkSellOrderInfoDao.save(linkSellOrderInfo);
//		}
//
//		TbFileDao tfDao = new TbFileDao(em);
//		TbResSellOrderInfoFileDao trfgDao =new TbResSellOrderInfoFileDao(em);
//
//		List<TbFile> tfs =tfDao.findBySellOrderInfoId(linkSellOrderInfo.getId());
//		List<TbResSellOrderInfoFile> trgfs =trfgDao.findBySellOrderInfoId(linkSellOrderInfo.getId());
//		if(trgfs!=null){
//			for(TbResSellOrderInfoFile trgf:trgfs){
//				trfgDao.delete(trgf);
//			}
//		}
//		if(tfs!=null){
//			for(TbFile tbFile:tfs){
//				tfDao.delete(tbFile);
//			}
//		}
//
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
//					TbResSellOrderInfoFile trpf=new TbResSellOrderInfoFile();
//					trpf.setFileId(tfe.getId());
//					trpf.setSellOrderInfoId(Integer.valueOf(linkSellOrderInfo.getId()));
//					trpf.setDelFlag(0);
//					trfgDao.save(trpf);
//				}
//			}
//		}
//		JSONArray ja = JSONArray.fromObject(carJa);
//		TbShoppingCarDao tbShoppingCarDao = new TbShoppingCarDao(em);
//		List<Integer> carId = new ArrayList<Integer>();
//		if(ja!=null) {
//			for(int i=0;i<ja.size();i++) {
//				JSONObject carJo = (JSONObject)ja.get(i);
//				TbToolDao toolDao = new TbToolDao(em);
//				TbTool tool = toolDao.findById(carJo.getInt("toolId"));
//				if(tool==null){
//					res.add("status", -1);
//					res.add("msg", "未知错误，获取商品信息失败");
//					return true;
//				}
//				TbShoppingCar car = new TbShoppingCar();
//				car.setToolId(carJo.getInt("toolId"));
//				car.setEnterpriseId(null);
//				car.setIsPay(0);
//				car.setDelFlag(0);
//				car.setStatus(2);
//				car.setNum(carJo.getString("num"));
//				if(new BigDecimal(car.getNum()).compareTo(new BigDecimal(StringUtils.isEmpty(tool.getNumber())?"0":tool.getNumber()))==1) {
//					res.add("status", -1);
//					res.add("msg", "库存不足");
//					return false;
//				}
//				car.setPrice(new BigDecimal(tool.getPrice()).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
//				tbShoppingCarDao.save(car);
//				carId.add(car.getId());
//			}
//		}
//
//		String sellSellOrderNumber ="1002"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6;
//
//
//
//		TbShoppingCarDao tscDao = new TbShoppingCarDao(em);
//		TbToolSellOrderDao ttoDao =new TbToolSellOrderDao(em);
//		TbResSellOrderCarDao resSellOrderCarDao = new TbResSellOrderCarDao(em);
//		List<Object[]> tscList = tscDao.findByCarId(carId);
//		if(tscList==null||tscList.size()==0){
//			res.add("status", -1);
//			res.add("msg", "请选择购买的商品");
//			return false;
//		}
//
//		if(tscList!=null) {
//			for(int i=0;i<tscList.size();i++) {
//				Object[] jo = tscList.get(i);
//				Integer toolEnterpriseId = Integer.valueOf(jo[1].toString());
//				//新加订单
//				TbToolSellOrder ttl = new TbToolSellOrder();
//				ttl.setDelFlag(0);
//				ttl.setInputTime(inputTime);
//				ttl.setSellOrderNumber(sellSellOrderNumber+"");
//				ttl.setPlantEnterpriseId(linkSellOrderInfo.getId());
//				ttl.setToolEnterpriseId(toolEnterpriseId);
//				ttl.setStatus(4);
//				ttl.setAddPeople(user.getName());
//				ttl.setPrice(jo[2].toString());
//				ttl.setCheck(0);
//				ttl.setType(2);
//				ttoDao.save(ttl);
//				if(jo[0]!=null) {
//					String[] split2 = jo[0].toString().split(",");
//					List<String> carIds =Arrays.asList(split2);
//					//添加关联
//					for(String cid:carIds) {
//						TbResSellOrderCar tbResSellOrderCar = new TbResSellOrderCar();
//						tbResSellOrderCar.setCarId(Integer.valueOf(cid));
//						tbResSellOrderCar.setDelFlag(0);
//						tbResSellOrderCar.setSellOrderId(ttl.getId());
//						resSellOrderCarDao.save(tbResSellOrderCar);
//						TbShoppingCar car = tscDao.findById(Integer.valueOf(cid));
//						ChangeToolNumberUtil init = ChangeToolNumberUtil.init();
//						init.minusNumber(car.getToolId(), new BigDecimal(car.getNum()), em,ttl.getPlantEnterpriseId(),user.getName());
//					}
//				}
//			}
//		}
//		res.add("status", 1);
//		res.add("msg", "操作成功");
//		return true;
//	}
//
//	@Override
//	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
//			EntityManager em) throws Exception {
//
//
//		return true;
//	}
//}
