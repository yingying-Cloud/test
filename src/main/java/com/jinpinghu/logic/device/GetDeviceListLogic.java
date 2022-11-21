package com.jinpinghu.logic.device;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.TbDeciveDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.device.param.GetDeviceListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetDeviceListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetDeviceListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetDeviceListParam myParam=(GetDeviceListParam)GetDeviceListParam;
		String deviceName=myParam.getDeviceName();
		Integer baseId=Integer.valueOf(myParam.getBaseId());
		Integer greenhousesId=StringUtils.isEmpty(myParam.getGreenhousesId())?null:Integer.valueOf(myParam.getGreenhousesId());
		String greenhousesName=myParam.getGreenhousesName();
		Integer nowPage=myParam.getNowPage();
		Integer pageCount=myParam.getPageCount();
		Integer classify = myParam.getClassify();
		
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		TbDeciveDao ddao=new TbDeciveDao(em);
		Integer count=ddao.getListCount(deviceName, baseId,greenhousesName,greenhousesId,classify);
		if(count==null||count==0){
			res.add("status", 1)
			.add("msg", "无数据！");
			return true;
		}
		List<Object[]> ol=ddao.getList(deviceName, baseId, greenhousesName, nowPage, pageCount,greenhousesId,classify);
		for(Object[] o:ol){
			map=new HashMap<String,Object>();
			map.put("id", o[0]);
			map.put("deviceName", o[1]);
			map.put("type", o[2]);
			map.put("inputTime", DateTimeUtil.formatTime((Date)o[3]));
			if(o[4]!=null){
				/*String[] ghName=o[4].toString().split(",");
				list=new ArrayList<Map<String,Object>>();
				for(String name:ghName){
					nmap=new HashMap<String,Object>();
					nmap.put("greenHousesName", name);
					list.add(nmap);
				}
				map.put("housesName", list);*/
				map.put("greenHousesName", o[4]);
			}
			map.put("baseName", o[5]);
			map.put("deviceSn", o[6]);
			
			map.put("installTime", o[7]==null?null:DateTimeUtil.formatTime((Date)o[7]));
			map.put("installAddress", o[8]);
			map.put("factory", o[9]);
			map.put("equipmentType", o[10]);
//			map.put("sensorNo", o[11]);
			
			map.put("closeInstruction", o[11]);
			map.put("openInstruction", o[12]);
			map.put("searchInstruction", o[13]);
			map.put("classify", o[14]);
			map.put("landName", o[15]);
			
			
			Map<String, Object> lastData = ddao.getLastDeviceData((Integer)o[0]);
			if (lastData != null) {
				String names = (String)lastData.get("name");
				String values = (String)lastData.get("value");
				map.put("name", names);
				map.put("value",values);
				map.put("switchInputTime",(String)lastData.get("time"));
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
			result.add(map);
		}
		res.add("status", 1)
		.add("msg", "查询成功！")
		.add("result", result)
		.add("allCount", count);
		return true;
	}

}
