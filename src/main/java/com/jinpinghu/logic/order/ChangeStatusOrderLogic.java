package com.jinpinghu.logic.order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.ChangeToolNumberUtil;
import com.jinpinghu.db.bean.TbResOrderCar;
import com.jinpinghu.db.bean.TbShoppingCar;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.ChangeStatusOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ChangeStatusOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ChangeStatusOrderParam myParam = (ChangeStatusOrderParam)logicParam;
		String id=myParam.getId();
		Integer status = myParam.getStatus();
		TbToolOrderDao toDao = new TbToolOrderDao(em);
		TbToolDao toolDao = new TbToolDao(em);
		TbShoppingCarDao tscDao = new TbShoppingCarDao(em);
		TbToolOrder to = toDao.findById(Integer.valueOf(id));
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
		}else{
			
			switch (status) {
			case 2: //备货时间
				to.setTimeAudit(new Date());
				List<Map<String, Object>> tscList = tscDao.findInOrderId(Integer.valueOf(id),null);
				if(tscList==null||tscList.size()==0){
					res.add("status", -1);
					res.add("msg", "请选择购买的商品");
					return false;
				}
//				if(tscList!=null) {
//					for(int i=0;i<tscList.size();i++) {
//						Map<String, Object> jo = tscList.get(i);
//						//修改库存
//						if(jo.containsKey("id")) {
//							TbShoppingCar car = tscDao.findById(Integer.valueOf(jo.get("id").toString()));
//							if(car!=null) {
//								TbTool tool = toolDao.findById(car.getToolId());
//								if(tool==null||new BigDecimal(car.getNum()).compareTo(new BigDecimal(tool.getNumber()))==1) {
//									res.add("status", -1);
//									res.add("msg", "有商品库存不足，请重新确认");
//									return false;
//								}
//								ChangeToolNumberUtil init = ChangeToolNumberUtil.init();
//								init.minusNumber(car.getToolId(), new BigDecimal(car.getNum()), em,to.getPlantEnterpriseId(),user.getName());
//							}
//						}
//					}
//				}
				break;
			case 3://发货时间
				to.setTimeSend(new Date());
				break;
			case 4://确认时间
				to.setTimeComplete(new Date());
				List<Map<String, Object>> tscLists = tscDao.findInOrderId(Integer.valueOf(id),null);
				if(tscLists==null||tscLists.size()==0){
					res.add("status", -1);
					res.add("msg", "请选择购买的商品");
					return false;
				}
				
//				if(tscLists!=null) {
//					for(int i=0;i<tscLists.size();i++) {
//						Map<String, Object> jo = tscLists.get(i);
//						//修改库存
//						if(jo.containsKey("id")) {
//							TbShoppingCar car = tscDao.findById(Integer.valueOf(jo.get("id").toString()));
//							if(car!=null) {
//								ChangeToolNumberUtil init = ChangeToolNumberUtil.init();
//								init.addNumber(car.getToolId(), new BigDecimal(car.getNum()), em,to.getPlantEnterpriseId(),user.getName());
//							}
//						}
//					}
//				}
				break;
			default:
				res.add("msg", "无效的操作数");
				res.add("status", 2);
				return false;
			}

			to.setStatus(status);
			toDao.update(to);
			res.add("msg", "修改成功");
			res.add("status", 1);
			return true;
		}
		
	}

}
