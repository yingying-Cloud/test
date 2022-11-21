//package com.jinpinghu.logic.warehouse;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//
//import com.mysql.jdbc.StringUtils;
//import com.nzy.common.DateTimeUtil;
//import com.nzy.db.bean.TbUser;
//import com.nzy.db.dao.TbPlantRecordDao;
//import com.nzy.db.dao.TbUserDao;
//import com.nzy.logic.BaseZLogic;
//import com.nzy.logic.wareHouse.param.GetPlantRecordListParam;
//
//import fw.jbiz.ext.json.ZSimpleJsonObject;
//import fw.jbiz.logic.ZLogicParam;
//
//public class GetPlantRecordListLogic extends BaseZLogic{
//
//	@Override
//	protected boolean execute(ZLogicParam GetPlantRecordListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
//		GetPlantRecordListParam myParam=(GetPlantRecordListParam)GetPlantRecordListParam;
//		String plantName=StringUtils.isNullOrEmpty(myParam.getPlantName())?null:myParam.getPlantName();
//		Integer baseId=StringUtils.isNullOrEmpty(myParam.getBaseId())?null:Integer.valueOf(myParam.getBaseId());
//		String greenhousesName=StringUtils.isNullOrEmpty(myParam.getGreenhousesName())?null:myParam.getGreenhousesName();
//		Integer recordType=StringUtils.isNullOrEmpty(myParam.getRecordType())?null:Integer.valueOf(myParam.getRecordType());
//		String time=myParam.getTime();
//		Integer nowPage=Integer.valueOf(myParam.getNowPage());
//		Integer pageCount=Integer.valueOf(myParam.getPageCount());
//		
//		TbUserDao tudao=new TbUserDao(em);
//		TbUser tu=new TbUser();
//		tu=tudao.findByUserId(myParam.getUserId());
//		if(tu==null){
//			res.add("status", 2)
//			.add("msg", "无数据！");
//			return true;
//		}
//		String username=tu.getNickname();
//		
//		TbPlantRecordDao rdao=new TbPlantRecordDao(em);
//		
//		Integer count=rdao.getPlantRecordCount(plantName, baseId, greenhousesName, recordType, time, username);
//		if(count==null){
//			res.add("status", 2)
//			.add("msg", "无数据！");
//			return true;
//		}
//		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map;
//		List<Object[]> list=rdao.getPlantRecordList(plantName, baseId, greenhousesName, recordType, time, username, nowPage, pageCount);
//		if(list!=null){
//			for(Object[] ob:list ){
//				map=new HashMap<String,Object>();
//				map.put("plantName", ob[0]);
//				map.put("greenhousesNamme", ob[1]);
//				map.put("number", ob[2]);
//				map.put("inputTime", DateTimeUtil.formatTime2((Date)ob[3]));
//				map.put("batchNum", ob[4]);
//				map.put("recordType", ob[5]);
//				map.put("outDirection", ob[6]);
//				map.put("logisticsInfo", ob[7]);
//				map.put("linkPeople", ob[8]);
//				map.put("linkMobile", ob[9]);
//				map.put("recordId", ob[10]);
//				map.put("box", ob[11]);
//				map.put("reportLoss", ob[12]);
//				result.add(map);
//			}
//		}
//		res.add("status", 1)
//		.add("msg", "操作成功！")
//		.add("allCounts", count)
//		.add("result", result);
//		return true;
//	}
//	// 参数检查
//	@Override
//	protected boolean validate(ZLogicParam GetPlantRecordListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
//		GetPlantRecordListParam myParam=(GetPlantRecordListParam)GetPlantRecordListParam;
//		String baseId=myParam.getBaseId();
//		String nowPage=myParam.getNowPage();
//		String pageCount=myParam.getPageCount();
//
//		
//		if(StringUtils.isNullOrEmpty(nowPage.toString())||StringUtils.isNullOrEmpty(pageCount.toString())
//				||StringUtils.isNullOrEmpty(baseId)){
//			res.add("status", -2)
//			.add("msg", "参数不得为空！");
//			return false;
//		}
//		return true;
//	}
//}
