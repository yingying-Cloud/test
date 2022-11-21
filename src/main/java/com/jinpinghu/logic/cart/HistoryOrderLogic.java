package com.jinpinghu.logic.cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbToolOrderBookDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cart.param.AddToCartParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class HistoryOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToCartParam param = (AddToCartParam)logicParam;
		String name = param.getName();
		String year = param.getYear();
		Integer nowPage = param.getNowPage();
		Integer pageCount = param.getPageCount();
		Integer status = param.getStatus();
		Integer count = 0;
		Integer startIndex = 0;
		Integer endIndex = 0;
		if(nowPage != null && pageCount != null){
			startIndex = (nowPage - 1) * pageCount;
			endIndex = nowPage *pageCount - 1;
		}

		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		
		TbToolOrderBookDao dao = new TbToolOrderBookDao(em);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Integer allCounts = dao.staticByMonthCount(param.getUserId(), year, name, status);

		List<Object> idsList = dao.getShopCarBookList3(param.getUserId(), year, name, status, nowPage, pageCount);
		List<String> idsLists = new ArrayList<String>();
		if(idsList == null || idsList.isEmpty()){
			res.add("status", 1);
			res.add("msg", "操作成功");
			return true;
		}
		for(Object o : idsList){
			idsLists.add(o.toString());
		}
		if(allCounts > 0){

			result = dao.staticByMonth(param.getUserId(), year, name, status);
			if(nowPage != null && pageCount!= null){
				for(Map<String, Object> map : result){
					List<String> ids = Arrays.asList(map.get("ids").toString().split(","));
					/*//判断数据是否在当前页
					if(count < endIndex && count + ids.size() > startIndex ){
						//判断该月的数据是否要切分
						int s = (startIndex <= count ) ? 0 : startIndex - count;
						int e = (count + ids.size() <= endIndex) ? ids.size() : endIndex - count +1;
						ids = ids.subList(s, e);
						List<Map<String, Object>> shopCarList = dao.getShopCarBookList(ids);
						map.put("shopCarList", shopCarList);
						if(count + ids.size() >= endIndex)
							break;
						count += ids.size();
					}*/
					//判断有没有交集

					List<String> idList = new ArrayList<String>(ids);
					idList.retainAll(idsLists);
					if(idList.isEmpty()){
						continue;
					}else{
						List<Map<String, Object>> shopCarList = dao.getShopCarBookList(idList);
						map.put("shopCarList", shopCarList);
						result1.add(map);
					}
				}
			}else{
			
				for(Map<String, Object> map : result){
					List<String> ids = Arrays.asList(map.get("ids").toString().split(","));
					
					List<Map<String, Object>> shopCarList = dao.getShopCarBookList(ids);
					map.put("shopCarList", shopCarList);
					
				}
				result1 = result;
			}
	}
		/*
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> dayList = null;
		Map<String, Object> dayMap = null;
		
		if(count != null && count > 0){
			list = dao.getShopCarBookList2(param.getUserId(), year, name, status, nowPage, pageCount);
			String time = "";
			for(Map<String, Object> map : list){
				//如果和上一条记录不是同一天，放在新一天的 dayList 中存入 result
				String newTime = map.get("inputTime").toString().substring(0,10);
				if(!time.equals(newTime)){
					//将之前的同一天的记录存入 dayMap
					if(dayList != null)
						dayMap.put("record", dayList);
					//将之前的同一天的记录存入 result
					if(dayMap != null)
						result.add(dayMap);
					//开始新的 dayMap， dayList
					String week = null;
					dayMap = new HashMap<String, Object>();
					dayList = new ArrayList<Map<String, Object>>();
					//匹配星期几
					switch(Integer.valueOf(map.get("week").toString())){
					case 1:
						week = "星期一";
						break;
					case 2:
						week = "星期二";
						break;
					case 3:
						week = "星期三";
						break;
					case 4:
						week = "星期四";
						break;
					case 5:
						week = "星期五";
						break;
					case 6:
						week = "星期六";
						break;
					case 0:
						week = "星期日";
						break;
					}
					//将日期存回原  map
					dayMap.put("fullDate", map.get("date")+" "+week);
					//将当前的记录存入新的 dayList
					dayList.add(map);
				}else{
					dayList.add(map);
				}

				time = map.get("inputTime").toString().substring(0,10);
				
			}
			dayMap.put("record", dayList);
			result.add(dayMap);
		}
		
		
	
		*/
			
		
		res.add("allCounts", allCounts);
		res.add("result", result1);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
