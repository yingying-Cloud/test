package com.jinpinghu.logic.cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbCartDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cart.param.AddToCartParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetCartListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToCartParam myParam = (AddToCartParam)logicParam;
		String cartIds = StringUtil.isNullOrEmpty(myParam.getCartIds())? null : myParam.getCartIds();
		
		TbCartDao cartDao = new TbCartDao(em);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
										
		List<String> cartIdList = Arrays.asList( cartIds.split(",") );
		//获取店名以及店id
		List<Object[]> gidAndWidList = cartDao.getEnterpriseIds(cartIdList);
		String enterpriseId = null;
		List<Map<String, Object>> toolList = null;
		Map<String, Object> resultMap = null;
		
		for(int i = 0; i<gidAndWidList.size(); i++){
			resultMap = new HashMap<String, Object>();
			//取出enterpriseId
			enterpriseId = gidAndWidList.get(i)[0].toString();						
			//根据enterpriseId和uid找出旗下的商品信息
			toolList = cartDao.GetToolByEnterpriseId(myParam.getUserId(), enterpriseId, cartIdList); 
			if(toolList != null){
								
				resultMap.put("toolList", toolList);
				resultMap.put("enterpriseName", gidAndWidList.get(i)[1].toString());
				resultMap.put("enterpriseId", enterpriseId);
				result.add(resultMap);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("result", result);
		return true;
	}
	
}
