package com.jinpinghu.logic.order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.ChangeToolNumberUtil;
import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.CancleOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class CancleOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		CancleOrderParam myParam = (CancleOrderParam)logicParam;
		String id = myParam.getId();
		String cancleInfo = myParam.getCancleInfo();
		String userId = myParam.getUserId();
		TbUserDao tuDao =new TbUserDao(em);
		TbUser user = tuDao.checkLogin2(userId);
		TbToolOrderDao toDao = new TbToolOrderDao(em);
		TbShoppingCarDao shoppingCarDao = new TbShoppingCarDao(em);
		List<String> list = Arrays.asList(id.split(","));
		for(String i:list) {
			TbToolOrder to = toDao.findById(Integer.valueOf(i));
			to.setStatus(-1);
			to.setCancelInfo(cancleInfo);
			to.setTimeCancel(new Date());
			toDao.update(to);
			List<Map<String,Object>> carInfo = shoppingCarDao.findInOrderId(to.getId(),null);
			if(carInfo!=null) {
				for(Map<String,Object> map:carInfo) {
					if(map.containsKey("num")&&map.containsKey("toolId")) {
						ChangeToolNumberUtil init = ChangeToolNumberUtil.init();
						init.addNumber(Integer.valueOf(map.get("toolId").toString()), new BigDecimal(map.get("num").toString()), em,to.getToolEnterpriseId(),user.getName());
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
		CancleOrderParam myParam = (CancleOrderParam)logicParam;
		String id = myParam.getId();
		if(StringUtils.isEmpty(id)){
			res.add("msg", "必填参数不能为空");
			res.add("status", -2);
			return false;
		}
		return true;
	}
}
