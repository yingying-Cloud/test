package com.jinpinghu.logic.brandOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbBrandOrder;
import com.jinpinghu.db.bean.TbBrandSale;
import com.jinpinghu.db.bean.TbBrandShoppingCar;
import com.jinpinghu.db.bean.TbResBrandCarNum;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbBrandOrderDao;
import com.jinpinghu.db.dao.TbBrandSaleDao;
import com.jinpinghu.db.dao.TbBrandShoppingCarDao;
import com.jinpinghu.db.dao.TbResBrandCarNumDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandOrder.param.ChangeBrandOrderPriceParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChangeBrandOrderPriceLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ChangeBrandOrderPriceParam myParam = (ChangeBrandOrderPriceParam)logicParam;
		String id=myParam.getId();
		String carInfo = myParam.getCarInfo();
		String status = myParam.getStatus();
		TbBrandOrderDao toDao = new TbBrandOrderDao(em);
		TbBrandShoppingCarDao tscDao = new TbBrandShoppingCarDao(em);
		TbBrandOrder to = toDao.findById(Integer.valueOf(id));
		String userId =myParam.getUserId();
		String apiKey =myParam.getApiKey();
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser user = tbUserDao.checkLogin(userId, apiKey);
		if(user==null){
			res.add("status", 2);
			res.add("msg", "未知错误，获取用户信息失败");
			return true;
		}
		if(to==null){
			res.add("msg", "该订单已删除或不存在");
			res.add("status", 2);
			return true;
		}
		BigDecimal orderPrice = BigDecimal.ZERO;
		JSONArray car = JSONArray.fromObject(carInfo);
		if(car!=null) {
			for(int i =0;i<car.size();i++) {
				JSONObject  jo = (JSONObject) car.get(i);
				Integer carId = jo.getInt("id");
				String brandPrice = jo.getString("price");
				String num = jo.getString("num");
				TbBrandShoppingCar shoppingCar = tscDao.findById(carId);
				shoppingCar.setBrandPrice(brandPrice);
				shoppingCar.setNum(num);
				if(shoppingCar.getType()==null||shoppingCar.getType()==1) {
					shoppingCar.setPrice(new BigDecimal(brandPrice).multiply(new BigDecimal(num)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}
				tscDao.update(shoppingCar);
				orderPrice= orderPrice.add(new BigDecimal(shoppingCar.getPrice()));
			}
		}
		if(!StringUtils.isNullOrEmpty(status)) {
			switch (Integer.valueOf(status)) {
			case 2: //备货时间
				to.setTimeAudit(new Date());
				List<Map<String, Object>> tscList = tscDao.findInOrderId(Integer.valueOf(id));
				if(tscList==null||tscList.size()==0){
					res.add("status", -1);
					res.add("msg", "请选择购买的商品");
					return false;
				}
				break;
			case 3://发货时间
				to.setTimeSend(new Date());
				List<Map<String, Object>> checktscLists = tscDao.findInOrderId(Integer.valueOf(id));
				if(checktscLists==null||checktscLists.size()==0){
					res.add("status", -1);
					res.add("msg", "请选择购买的商品");
					return false;
				}
				for(Map<String, Object> cars:checktscLists) {
					if(cars.containsKey("type")&&cars.get("type").toString().equals("2")) {
						TbBrandSaleDao tbBrandSaleDao = new TbBrandSaleDao(em);
						TbBrandSale brandSale = tbBrandSaleDao.findById(Integer.valueOf(cars.get("brandSaleId").toString()));
						TbResBrandCarNumDao brandCarNumDao = new TbResBrandCarNumDao(em);
						TbResBrandCarNum resBrandCarNum = brandCarNumDao.findByCarIdAndEnterpriseId(Integer.valueOf(cars.get("id").toString()), brandSale.getEnterpriseId());
				        if(resBrandCarNum!=null) {  //非空更新数量
				            resBrandCarNum.setEnterpriseId(brandSale.getEnterpriseId());
				            resBrandCarNum.setNum(cars.get("num").toString());
				            resBrandCarNum.setBrandCarId(Integer.valueOf(cars.get("id").toString()));
				            brandCarNumDao.update(resBrandCarNum);
				        }else{ //为空添加记录
				            resBrandCarNum = new TbResBrandCarNum();
				            resBrandCarNum.setEnterpriseId(brandSale.getEnterpriseId());
				            resBrandCarNum.setNum(cars.get("num").toString());
				            resBrandCarNum.setBrandCarId(Integer.valueOf(cars.get("id").toString()));
				            resBrandCarNum.setDelFlag(0);
				            brandCarNumDao.save(resBrandCarNum);
				        }
					}
				}
				break;
			case 4://确认时间
				to.setTimeComplete(new Date());
				List<Map<String, Object>> tscLists = tscDao.findInOrderId(Integer.valueOf(id));
				if(tscLists==null||tscLists.size()==0){
					res.add("status", -1);
					res.add("msg", "请选择购买的商品");
					return false;
				}
				break;
			default:
				res.add("msg", "无效的操作数");
				res.add("status", 2);
				return false;
			}
			to.setStatus(Integer.valueOf(status));
		}
		
		to.setPrice(orderPrice.toString());
		toDao.update(to);
		res.add("msg", "修改成功");
		res.add("status", 1);
		return true;
	}
		
}

