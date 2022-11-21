package com.jinpinghu.logic.brandOrder;

import com.jinpinghu.common.tools.ChangeSellBrandNumberUtil;
import com.jinpinghu.db.bean.*;
import com.jinpinghu.db.dao.*;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandOrder.param.AddBrandOrderParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddBrandOrderLogic extends BaseZLogic{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddBrandOrderParam myParam = (AddBrandOrderParam)logicParam;
		String 	carId =myParam.getCarId();
//		String brandOrderNumber ="1002"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6;
		Integer enterpriseId = myParam.getEnterpriseId();
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
		
		if(StringUtils.isEmpty(carId)){
			res.add("status", -1);
			res.add("msg", "请选择购买的商品");
			return true;
		}
		TbBrandShoppingCarDao tscDao = new TbBrandShoppingCarDao(em);
		TbBrandOrderDao ttoDao =new TbBrandOrderDao(em);
		TbResBrandOrderCarDao resBrandOrderCarDao = new TbResBrandOrderCarDao(em);
		String[] split = carId.split(",");
		List carList =Arrays.asList(split);
		List<Object[]> tscList = tscDao.findByCarId(carList);
		if(tscList==null||tscList.size()==0){
			res.add("status", -1);
			res.add("msg", "请选择购买的商品");
			return false;
		}
		Integer tid = null;
		if(tscList!=null) {
			for(int i=0;i<tscList.size();i++) {
				Object[] jo = tscList.get(i);
				//新加订单
				TbBrandOrder ttl = new TbBrandOrder();
				ttl.setDelFlag(0);
				ttl.setInputTime(inputTime);
				ttl.setOrderNumber("1002"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6+"");
				ttl.setEnterpriseId(enterpriseId);
				ttl.setStatus(1);
				ttl.setAddPeople(user.getName());
				ttl.setPrice(jo[1]==null?"0":jo[1].toString());
				ttl.setType(jo[2]==null?null:Integer.valueOf(jo[2].toString()));
				ttl.setSellId(jo[3]==null?null:Integer.valueOf(jo[3].toString()));
				ttoDao.save(ttl);
				tid=ttl.getId();
				if(jo[0]!=null) {
					String[] split2 = jo[0].toString().split(",");
					List<String> carIds =Arrays.asList(split2);
					//添加关联
					for(String cid:carIds) {
						TbResBrandOrderCar tbResBrandOrderCar = new TbResBrandOrderCar();
						tbResBrandOrderCar.setBrandCarId(Integer.valueOf(cid));
						tbResBrandOrderCar.setDelFlag(0);
						tbResBrandOrderCar.setBrandOrderId(ttl.getId());
						resBrandOrderCarDao.save(tbResBrandOrderCar);
						TbBrandShoppingCar car = tscDao.findById(Integer.valueOf(cid));
						car.setStatus(2);
						tscDao.update(car);
						
						if(car!=null&&car.getType()==1) {
							ChangeSellBrandNumberUtil init = ChangeSellBrandNumberUtil.init();
							init.addNumber(car.getBrandId(), new BigDecimal(car.getNum()), em,enterpriseId,user.getName());
						}
						
					}
				}
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("orderId", tid);
		return true;
	}
}
