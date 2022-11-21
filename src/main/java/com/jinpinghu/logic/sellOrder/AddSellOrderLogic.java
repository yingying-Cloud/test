package com.jinpinghu.logic.sellOrder;

import com.jinpinghu.db.bean.TbSellOrder;
import com.jinpinghu.db.bean.TbSellShoppingCar;
import com.jinpinghu.common.tools.ChangeSellBrandNumberUtil;
import com.jinpinghu.common.tools.ChangeToolNumberUtil;
import com.jinpinghu.db.bean.TbResSellOrderCar;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbSellOrderDao;
import com.jinpinghu.db.dao.TbSellShoppingCarDao;
import com.jinpinghu.db.dao.TbResSellOrderCarDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellOrder.param.AddSellOrderParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddSellOrderLogic extends BaseZLogic{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddSellOrderParam myParam = (AddSellOrderParam)logicParam;
		String 	carId =myParam.getCarId();
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
		TbSellShoppingCarDao tscDao = new TbSellShoppingCarDao(em);
		TbSellOrderDao ttoDao =new TbSellOrderDao(em);
		TbResSellOrderCarDao resSellOrderCarDao = new TbResSellOrderCarDao(em);
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
				TbSellOrder ttl = new TbSellOrder();
				ttl.setDelFlag(0);
				ttl.setInputTime(inputTime);
				ttl.setOrderNumber("1002"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6+"");
				ttl.setEnterpriseId(enterpriseId);
				ttl.setStatus(1);
				ttl.setAddPeople(user.getName());
				ttl.setPrice(jo[1]==null?"0":jo[1].toString());
				ttl.setSellId(jo[2]==null?null:Integer.valueOf(jo[2].toString()));
				ttoDao.save(ttl);
				tid=ttl.getId();
				if(jo[0]!=null) {
					String[] split2 = jo[0].toString().split(",");
					List<String> carIds =Arrays.asList(split2);
					//添加关联
					for(String cid:carIds) {
						TbResSellOrderCar tbResSellOrderCar = new TbResSellOrderCar();
						tbResSellOrderCar.setSellCarId(Integer.valueOf(cid));
						tbResSellOrderCar.setDelFlag(0);
						tbResSellOrderCar.setSellOrderId(ttl.getId());
						resSellOrderCarDao.save(tbResSellOrderCar);
						TbSellShoppingCar car = tscDao.findById(Integer.valueOf(cid));
						car.setStatus(2);
						tscDao.update(car);
						
						if(car!=null) {
							ChangeSellBrandNumberUtil init = ChangeSellBrandNumberUtil.init();
							init.minusNumberSellBrand(car.getBrandId(), new BigDecimal(car.getNum()), em,car.getSellId(),user.getName());
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
