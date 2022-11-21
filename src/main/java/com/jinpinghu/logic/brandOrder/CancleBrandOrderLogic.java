package com.jinpinghu.logic.brandOrder;

import com.jinpinghu.common.tools.ChangeSellBrandNumberUtil;
import com.jinpinghu.db.bean.TbBrandOrder;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbBrandOrderDao;
import com.jinpinghu.db.dao.TbBrandShoppingCarDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandOrder.param.CancleBrandOrderParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CancleBrandOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		CancleBrandOrderParam myParam = (CancleBrandOrderParam)logicParam;
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
		TbBrandOrderDao toDao = new TbBrandOrderDao(em);
		TbBrandShoppingCarDao shoppingCarDao = new TbBrandShoppingCarDao(em);
		List<String> list = Arrays.asList(id.split(","));
		for(String i:list) {
			TbBrandOrder to = toDao.findById(Integer.valueOf(i));
			to.setStatus(-1);
			to.setCancelInfo(cancleInfo);
			to.setTimeCancel(new Date());
			toDao.update(to);
			List<Map<String,Object>> carInfo = shoppingCarDao.findInOrderIdByTrademark(to.getId());
			if(carInfo!=null) {
				for(Map<String,Object> map:carInfo) {
					if(map.containsKey("num")&&map.containsKey("brandId")&&map.containsKey("type")&&map.get("type").toString().equals("1")) {
						ChangeSellBrandNumberUtil init = ChangeSellBrandNumberUtil.init();
						init.minusNumberBrand(Integer.valueOf(map.get("brandId").toString()), new BigDecimal(map.get("num").toString()), em,to.getEnterpriseId(),user.getName());
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
		CancleBrandOrderParam myParam = (CancleBrandOrderParam)logicParam;
		String id = myParam.getId();
		if(StringUtils.isEmpty(id)){
			res.add("msg", "必填参数不能为空");
			res.add("status", -2);
			return false;
		}
		return true;
	}
}
