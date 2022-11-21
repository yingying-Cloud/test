//package com.jinpinghu.logic.brandBrandOrder;
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
//import com.jinpinghu.db.bean.TbLinkBrandOrderInfo;
//import com.jinpinghu.db.bean.TbResBrandOrderCar;
//import com.jinpinghu.db.bean.TbResBrandOrderInfoFile;
//import com.jinpinghu.db.bean.TbShoppingCar;
//import com.jinpinghu.db.bean.TbTool;
//import com.jinpinghu.db.bean.TbToolBrandOrder;
//import com.jinpinghu.db.bean.TbUser;
//import com.jinpinghu.db.dao.TbFileDao;
//import com.jinpinghu.db.dao.TbLinkBrandOrderInfoDao;
//import com.jinpinghu.db.dao.TbResBrandOrderCarDao;
//import com.jinpinghu.db.dao.TbResBrandOrderInfoFileDao;
//import com.jinpinghu.db.dao.TbShoppingCarDao;
//import com.jinpinghu.db.dao.TbToolDao;
//import com.jinpinghu.db.dao.TbToolBrandOrderDao;
//import com.jinpinghu.db.dao.TbUserDao;
//import com.jinpinghu.logic.BaseZLogic;
//import com.jinpinghu.logic.brandBrandOrder.param.AddCompleteBrandOrderParam;
//
//import fw.jbiz.ext.json.ZSimpleJsonObject;
//import fw.jbiz.logic.ZLogicParam;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//public class AddCompleteBrandOrderLogic extends BaseZLogic{
//
//	@Override
//	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
//			EntityManager em) throws Exception {
//		AddCompleteBrandOrderParam myParam = (AddCompleteBrandOrderParam)logicParam;
//		String carJa =myParam.getCarJa();
//		String brandBrandOrderInfoId = myParam.getBrandOrderInfoId();
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
//		TbLinkBrandOrderInfo linkBrandOrderInfo = null;
//		TbLinkBrandOrderInfoDao linkBrandOrderInfoDao = new TbLinkBrandOrderInfoDao(em);
//		if(!StringUtils.isEmpty(brandBrandOrderInfoId)) {
//			linkBrandOrderInfo = linkBrandOrderInfoDao.findById(Integer.valueOf(brandBrandOrderInfoId));
//			linkBrandOrderInfo.setAddress(address);
//			linkBrandOrderInfo.setCreditCode(creditCode);
//			linkBrandOrderInfo.setDelFlag(0);
//			linkBrandOrderInfo.setLegalPerson(legalPerson);
//			linkBrandOrderInfo.setLegalPersonIdcard(legalPersonIdcard);
//			linkBrandOrderInfo.setLinkMobile(linkMobile);
//			linkBrandOrderInfo.setLinkPeople(linkPeople);
//			linkBrandOrderInfo.setName(name);
//			linkBrandOrderInfo.setInputTime(new Date());
//			linkBrandOrderInfo.setArea(area);
//			linkBrandOrderInfo.setType(type);
//			linkBrandOrderInfoDao.update(linkBrandOrderInfo);
//		}else {
//			linkBrandOrderInfo = new TbLinkBrandOrderInfo();
//			linkBrandOrderInfo.setAddress(address);
//			linkBrandOrderInfo.setCreditCode(creditCode);
//			linkBrandOrderInfo.setDelFlag(0);
//			linkBrandOrderInfo.setLegalPerson(legalPerson);
//			linkBrandOrderInfo.setLegalPersonIdcard(legalPersonIdcard);
//			linkBrandOrderInfo.setLinkMobile(linkMobile);
//			linkBrandOrderInfo.setLinkPeople(linkPeople);
//			linkBrandOrderInfo.setName(name);
//			linkBrandOrderInfo.setInputTime(new Date());
//			linkBrandOrderInfo.setArea(area);
//			linkBrandOrderInfo.setType(type);
//			linkBrandOrderInfoDao.save(linkBrandOrderInfo);
//		}
//
//		TbFileDao tfDao = new TbFileDao(em);
//		TbResBrandOrderInfoFileDao trfgDao =new TbResBrandOrderInfoFileDao(em);
//
//		List<TbFile> tfs =tfDao.findByBrandOrderInfoId(linkBrandOrderInfo.getId());
//		List<TbResBrandOrderInfoFile> trgfs =trfgDao.findByBrandOrderInfoId(linkBrandOrderInfo.getId());
//		if(trgfs!=null){
//			for(TbResBrandOrderInfoFile trgf:trgfs){
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
//					TbResBrandOrderInfoFile trpf=new TbResBrandOrderInfoFile();
//					trpf.setFileId(tfe.getId());
//					trpf.setBrandOrderInfoId(Integer.valueOf(linkBrandOrderInfo.getId()));
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
//		String brandBrandOrderNumber ="1002"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6;
//
//
//
//		TbShoppingCarDao tscDao = new TbShoppingCarDao(em);
//		TbToolBrandOrderDao ttoDao =new TbToolBrandOrderDao(em);
//		TbResBrandOrderCarDao resBrandOrderCarDao = new TbResBrandOrderCarDao(em);
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
//				TbToolBrandOrder ttl = new TbToolBrandOrder();
//				ttl.setDelFlag(0);
//				ttl.setInputTime(inputTime);
//				ttl.setBrandOrderNumber(brandBrandOrderNumber+"");
//				ttl.setPlantEnterpriseId(linkBrandOrderInfo.getId());
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
//						TbResBrandOrderCar tbResBrandOrderCar = new TbResBrandOrderCar();
//						tbResBrandOrderCar.setCarId(Integer.valueOf(cid));
//						tbResBrandOrderCar.setDelFlag(0);
//						tbResBrandOrderCar.setBrandOrderId(ttl.getId());
//						resBrandOrderCarDao.save(tbResBrandOrderCar);
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
