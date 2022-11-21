package com.jinpinghu.logic.sellOrder;

import com.jinpinghu.db.bean.TbSellOrder;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbSellOrderDao;
import com.jinpinghu.db.dao.TbSellShoppingCarDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellOrder.param.ChangeStatusSellOrderParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChangeStatusSellOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ChangeStatusSellOrderParam myParam = (ChangeStatusSellOrderParam)logicParam;
		String id=myParam.getId();
		Integer status = myParam.getStatus();
		TbSellOrderDao toDao = new TbSellOrderDao(em);
		TbBrandDao Dao = new TbBrandDao(em);
		TbSellShoppingCarDao tscDao = new TbSellShoppingCarDao(em);
		TbSellOrder to = toDao.findById(Integer.valueOf(id));
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
