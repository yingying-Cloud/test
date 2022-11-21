package com.jinpinghu.logic.brandOrder;

import com.jinpinghu.db.bean.TbBrandOrder;
import com.jinpinghu.db.bean.TbBrandSale;
import com.jinpinghu.db.bean.TbResBrandCarNum;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbBrandOrderDao;
import com.jinpinghu.db.dao.TbBrandSaleDao;
import com.jinpinghu.db.dao.TbBrandShoppingCarDao;
import com.jinpinghu.db.dao.TbResBrandCarNumDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandOrder.param.ChangeStatusBrandOrderParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChangeStatusBrandOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ChangeStatusBrandOrderParam myParam = (ChangeStatusBrandOrderParam)logicParam;
		String id=myParam.getId();
		Integer status = myParam.getStatus();
		TbBrandOrderDao toDao = new TbBrandOrderDao(em);
		TbBrandDao Dao = new TbBrandDao(em);
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
		}else{
			
			switch (status) {
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
				for(Map<String, Object> car:checktscLists) {
					if(car.containsKey("type")&&car.get("type").toString().equals("2")) {
						TbBrandSaleDao tbBrandSaleDao = new TbBrandSaleDao(em);
						TbBrandSale brandSale = tbBrandSaleDao.findById(Integer.valueOf(car.get("brandSaleId").toString()));
						TbResBrandCarNumDao brandCarNumDao = new TbResBrandCarNumDao(em);
						TbResBrandCarNum resBrandCarNum = brandCarNumDao.findByCarIdAndEnterpriseId(Integer.valueOf(car.get("id").toString()), brandSale.getEnterpriseId());
				        if(resBrandCarNum!=null) {  //非空更新数量
				            resBrandCarNum.setEnterpriseId(brandSale.getEnterpriseId());
				            resBrandCarNum.setNum(car.get("num").toString());
				            resBrandCarNum.setBrandCarId(Integer.valueOf(car.get("id").toString()));
				            brandCarNumDao.update(resBrandCarNum);
				        }else{ //为空添加记录
				            resBrandCarNum = new TbResBrandCarNum();
				            resBrandCarNum.setEnterpriseId(brandSale.getEnterpriseId());
				            resBrandCarNum.setNum(car.get("num").toString());
				            resBrandCarNum.setBrandCarId(Integer.valueOf(car.get("id").toString()));
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

			to.setStatus(status);
			toDao.update(to);
			res.add("msg", "修改成功");
			res.add("status", 1);
			return true;
		}
		
	}

}
