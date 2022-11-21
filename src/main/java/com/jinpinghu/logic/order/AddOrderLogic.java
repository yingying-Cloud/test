package com.jinpinghu.logic.order;

import java.math.BigDecimal;
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
import com.jinpinghu.logic.order.param.AddOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrderLogic extends BaseZLogic{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddOrderParam myParam = (AddOrderParam)logicParam;
		String 	carId =myParam.getCarId();
		String orderNumber ="1002"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6;
		Integer plantEnterpriseId = myParam.getPlantEnterpriseId();
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
		TbShoppingCarDao tscDao = new TbShoppingCarDao(em);
		TbToolOrderDao ttoDao =new TbToolOrderDao(em);
		TbToolDao toolDao = new TbToolDao(em);
		TbResOrderCarDao resOrderCarDao = new TbResOrderCarDao(em);
		String[] split = carId.split(",");
		List carList =Arrays.asList(split);
		List<Object[]> tscList = tscDao.findByCarId(carList);
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
				ttl.setPlantEnterpriseId(plantEnterpriseId);
				ttl.setToolEnterpriseId(toolEnterpriseId);
				ttl.setStatus(1);
				ttl.setAddPeople(user.getName());
				ttl.setPrice(jo[2].toString());
				ttl.setCheck(1);
				ttl.setType(1);
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
						car.setStatus(2);
						tscDao.update(car);
						TbTool tool = toolDao.findById(car.getToolId());
						if(tool!=null&&tool.getUniformPrice().equals("1")) {
							checkMoney= checkMoney.add(new BigDecimal(car.getPrice()).multiply(new BigDecimal("0.12")));
						}
						if(car!=null) {
							ChangeToolNumberUtil init = ChangeToolNumberUtil.init();
							init.minusNumber(car.getToolId(), new BigDecimal(car.getNum()), em,plantEnterpriseId,user.getName());
						}
						
					}
				}
				ttl.setCheckMoney(checkMoney.setScale(1, BigDecimal.ROUND_HALF_UP)+"");
				ttoDao.update(ttl);
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
	
	
	//修改库存
//					TbShoppingCar car = tscDao.findById(Integer.valueOf(cid));
//					TbTool tool = toolDao.findById(car.getToolId());
//					if(new BigDecimal(car.getNum()).compareTo(new BigDecimal(tool.getNumber()))==1) {
//						res.add("status", -1);
//						res.add("msg", "有商品库存不足，请重新确认");
//						return false;
//					}
//					if(car!=null) {
//						ChangeToolNumberUtil init = ChangeToolNumberUtil.init();
//						init.minusNumber(car.getToolId(), new BigDecimal(car.getNum()), em,plantEnterpriseId,user.getName());
//					}
	
	
}
