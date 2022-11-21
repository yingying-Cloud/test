package com.jinpinghu.logic.sellOrder;

import com.jinpinghu.common.tools.ChangeSellBrandNumberUtil;
import com.jinpinghu.common.tools.ChangeToolNumberUtil;
import com.jinpinghu.db.bean.TbSellOrder;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbSellOrderDao;
import com.jinpinghu.db.dao.TbSellShoppingCarDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellOrder.param.CancleSellOrderParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CancleSellOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		CancleSellOrderParam myParam = (CancleSellOrderParam)logicParam;
		String id = myParam.getId();
		String cancleInfo = myParam.getCancleInfo();
		
		String userId =myParam.getUserId();
		String apiKey =myParam.getApiKey();
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser user = tbUserDao.checkLogin(userId, apiKey);
		if(user==null){
			res.add("status", -1);
			res.add("msg", "未知错误，获取用户信息失败");
			return true;
		}
		
		TbSellOrderDao toDao = new TbSellOrderDao(em);
		TbSellShoppingCarDao shoppingCarDao = new TbSellShoppingCarDao(em);
		List<String> list = Arrays.asList(id.split(","));
		
		for(String i:list) {
			TbSellOrder to = toDao.findById(Integer.valueOf(i));
			to.setStatus(-1);
			to.setCancelInfo(cancleInfo);
			to.setTimeCancel(new Date());
			toDao.update(to);
			
			List<Map<String,Object>> carInfo = shoppingCarDao.findInOrderIdByTrademark(to.getId());
			if(carInfo!=null) {
				for(Map<String,Object> map:carInfo) {
					if(map.containsKey("num")&&map.containsKey("baseBrandId")) {
						ChangeSellBrandNumberUtil init = ChangeSellBrandNumberUtil.init();
						init.addNumber(Integer.valueOf(map.get("baseBrandId").toString()), new BigDecimal(map.get("num").toString()), em,to.getSellId(),user.getName());
					}
				}
			}
			
		}
		res.add("msg", "取消成功");
		res.add("status", 1);
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		CancleSellOrderParam myParam = (CancleSellOrderParam)logicParam;
		String id = myParam.getId();
		if(StringUtils.isEmpty(id)){
			res.add("msg", "必填参数不能为空");
			res.add("status", -2);
			return false;
		}
		return true;
	}
}
