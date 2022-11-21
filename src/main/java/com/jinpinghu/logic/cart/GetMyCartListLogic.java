package com.jinpinghu.logic.cart;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbCartDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetMyCartListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		TbCartDao cartDao = new TbCartDao(em);
		List<Map<String, Object>> result = cartDao.GetEnterpriseInCart(logicParam.getUserId());
		
		Integer toolStatus = null;
		BigDecimal cartNum = BigDecimal.ZERO;
		BigDecimal toolNum = BigDecimal.ZERO;
		if(result != null){
			for(Map<String, Object> eMap : result){

				List<Map<String, Object>> list = cartDao.GetMyCartList(Arrays.asList(eMap.get("ids").toString().split(",")));
							
				
				for(Map<String, Object> map : list){
					toolStatus = Integer.valueOf(map.get("delFlag").toString());
					if(toolStatus == 1){							//判断商品是否已下架
						map.put("isNormal", 0);
					}else if(toolStatus == 2){
						map.put("isNormal", 1);
						map.put("reason", "该商品已下架！");
					}
					
					cartNum = new BigDecimal(map.get("cartNum").toString());
					toolNum = new BigDecimal(map.get("toolNum").toString());
					String toolName = map.get("toolName").toString();
					if(cartNum.compareTo(toolNum) == 1){
						map.put("isNormal", 0);
						map.put("reason", toolName+":超出商品库存，请调整加购数量");
					}else{
						map.put("isNormal", 1);
					}
				}
				eMap.put("cartList",list );
			}
		}
		res.add("status", 1);
		res.add("result", result);
		res.add("msg", "操作成功");
		return true;
	}

}
