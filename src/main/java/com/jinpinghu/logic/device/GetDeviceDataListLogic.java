package com.jinpinghu.logic.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbDeciveDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.device.param.GetDeviceDataListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetDeviceDataListLogic extends BaseZLogic {

	public GetDeviceDataListLogic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetDeviceDataListParam myParam = (GetDeviceDataListParam)logicParam;
		Integer deviceId = myParam.getDeviceId();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		
		TbDeciveDao deciveDao = new TbDeciveDao(em);
		
		Integer maxCount = deciveDao.getDeviceDataListCount(deviceId, startTime, endTime);
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		if(maxCount==null||maxCount==0){
			res.add("allCounts",0);
			res.add("maxPage",0);
			res.add("result", resultList);
			res.add("status", 1);
			res.add("msg", "无记录");
			return true;
		}
		int maxPage = 1;
		if(pageCount != null) {
			maxPage = maxCount/pageCount;
			if(maxCount%pageCount!=0){
				maxPage++;
			}
			if (pageCount * nowPage >= (maxCount + pageCount) && maxPage != 0) {
				nowPage = maxPage;
				res.add("allCounts",maxCount);
				res.add("maxPage",maxPage);
				res.add("result", resultList);
				res.add("status", 1);
				res.add("msg", "该页无记录");
				return true;
			}else if(maxPage == 0){
				nowPage = 1;
			}
		}
		
		resultList = deciveDao.getDeviceDataList(deviceId, startTime, endTime, nowPage, pageCount);
		
		for (Map<String, Object> map : resultList) {
			String names = (String)map.get("name");
			String values = (String)map.get("value");
			if(!StringUtils.isEmpty(names) && !StringUtils.isEmpty(values)) {
				String[] nameArray = names.split(",");
				String[] valueArray = values.split(",");
				for (int i = 0; i < nameArray.length; i++) {
					switch(nameArray[i]) {
						case "光照":
							map.put("Illumination", valueArray[i]);
							break;
						case "温度":
							map.put("temperature", valueArray[i]);
							break;
						case "湿度":
							map.put("humidity", valueArray[i]);
							break;
						case "Co2浓度":
							map.put("co2", valueArray[i]);
							break;
						case "水温":
							map.put("wt", valueArray[i]);
							break;
						case "高锰酸盐指数":
							map.put("codmn", valueArray[i]);
							break;
						case "总磷":
							map.put("tp", valueArray[i]);
							break;
						case "总氮":
							map.put("tn", valueArray[i]);
							break;
						case "氨氮":
							map.put("nh3n", valueArray[i]);
							break;
						case "酸碱度":
							map.put("ph", valueArray[i]);
							break;
						case "溶解氧":
							map.put("dox", valueArray[i]);
							break;
						case "浊度":
							map.put("turb", valueArray[i]);
							break;
						case "电导率":
							map.put("cond", valueArray[i]);
							break;
						default:
							break;
					}
				}
			}
		}
		
		Map<String, Object> lastData = deciveDao.getLastDeviceData(deviceId);
		if (lastData != null) {
			String names = (String)lastData.get("name");
			String values = (String)lastData.get("value");
			if(!StringUtils.isEmpty(names) && !StringUtils.isEmpty(values)) {
				String[] nameArray = names.split(",");
				String[] valueArray = values.split(",");
				for (int i = 0; i < nameArray.length; i++) {
					switch(nameArray[i]) {
						case "光照":
							lastData.put("Illumination", valueArray[i]);
							break;
						case "温度":
							lastData.put("temperature", valueArray[i]);
							break;
						case "湿度":
							lastData.put("humidity", valueArray[i]);
							break;
						case "Co2浓度":
							lastData.put("co2", valueArray[i]);
							break;
						case "水温":
							lastData.put("wt", valueArray[i]);
							break;
						case "高锰酸盐指数":
							lastData.put("codmn", valueArray[i]);
							break;
						case "总磷":
							lastData.put("tp", valueArray[i]);
							break;
						case "总氮":
							lastData.put("tn", valueArray[i]);
							break;
						case "氨氮":
							lastData.put("nh3n", valueArray[i]);
							break;
						case "酸碱度":
							lastData.put("ph", valueArray[i]);
							break;
						case "溶解氧":
							lastData.put("dox", valueArray[i]);
							break;
						case "浊度":
							lastData.put("turb", valueArray[i]);
							break;
						case "电导率":
							lastData.put("cond", valueArray[i]);
							break;
						default:
							break;
					}
				}
			}
		}
		res.add("lastData", lastData);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
