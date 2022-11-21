package com.jinpinghu.logic.goodsGroup;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.TbGroupGoodsDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.goodsGroup.param.GetGroupGoodsInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetGroupGoodsInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetGroupGoodsInfoParam myParam = (GetGroupGoodsInfoParam)logicParam;
		Integer id= StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String isIn = myParam.getIsIn();
		TbGroupGoodsDao groupGoodsDao = new TbGroupGoodsDao(em);
		Object[] o = groupGoodsDao.findById(id);
		JSONObject jo = new JSONObject();
		if(o!=null) {
			jo.put("id", o[0]);
			jo.put("goodsName", o[1]);
			jo.put("groupId", o[2]);
			jo.put("sources", o[4]);
			jo.put("typeName", o[7]);
			JSONArray ja = new JSONArray();
			List<String> list = groupGoodsDao.findGoodsPriceUnitByGoodsId(id);
			if(list!=null) {
				for(String unit:list) {
					JSONObject priceJo = new JSONObject();
					priceJo.put("unit", unit);
					List<Map<String, Object>> priceChangeByGoodsId = groupGoodsDao.findGoodsPriceChangeByGoodsId(id, startTime, endTime, unit);
//					if(priceChangeByGoodsId!=null&&priceChangeByGoodsId.size()>=2) {
//						Map<String, Object> map0 = priceChangeByGoodsId.get(0);
//						Map<String, Object> map1 = priceChangeByGoodsId.get(1);
//						String price0 = map0.get("price").toString();
//						String price1 = map1.get("price").toString();
//						BigDecimal increases=(new BigDecimal(price0).subtract(new BigDecimal(price1))).divide(new BigDecimal(price0),2);
//						priceJo.put("increases", increases);
//					}
					priceJo.put("child", priceChangeByGoodsId);
					Object[] priceByGoodsId = groupGoodsDao.findGoodsPriceByGoodsId(id,startTime,  endTime, unit);
					if(priceByGoodsId!=null) {
						priceJo.put("maxPrice", priceByGoodsId[0]);
						priceJo.put("minPrice", priceByGoodsId[1]);
						priceJo.put("avgPrice", priceByGoodsId[2]);
					}
					Object[] now = groupGoodsDao.findGoodsPriceByNow(id, DateTimeUtil.formatTime(new Date()), unit);
					Object[] yesterday = groupGoodsDao.findGoodsPriceByNow(id, DateTimeUtil.addDay( DateTimeUtil.formatTime(new Date()),-1), unit);
					if(now!=null) {
						priceJo.put("price", now[0]);
						if(now!=null) {
							String price0 = now[0].toString();
							String price1 = yesterday[0].toString();
							BigDecimal increases=(new BigDecimal(price0).subtract(new BigDecimal(price1))).divide(new BigDecimal(price0),2);
							priceJo.put("increases", increases);
						}
					}
					ja.add(priceJo);
				}
			}
			jo.put("price", ja);
			List<Map<String,Object>> otherGoods = groupGoodsDao.findOtherByGroupId( Integer.valueOf(o[0].toString()),isIn,Integer.valueOf(o[2].toString()));
			jo.put("otherGoods", otherGoods);
		}
		
		res.add("result", jo).add("msg", "操作成功").add("status", 1);
		return true;
	}	
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}

}
