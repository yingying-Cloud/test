package com.jinpinghu.logic.video;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.TbVideoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.video.param.GetVideoListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetVideoListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetDeviceListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetVideoListParam myParam=(GetVideoListParam)GetDeviceListParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		
		Integer nowPage=myParam.getNowPage();
		Integer pageCount=myParam.getPageCount();
		
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		TbVideoDao ddao=new TbVideoDao(em);
		Integer count=ddao.getListCount(enterpriseId);
		if(count==null||count==0){
			res.add("status", 1)
			.add("msg", "无数据！");
			return true;
		}
		List<Object[]> ol=ddao.getList(enterpriseId,nowPage, pageCount);
		if(ol!=null) {
			for(Object[] o:ol){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("videoName", o[1]);
				map.put("inputTime", DateTimeUtil.formatTime2((Date)o[2]));
				map.put("enterpriseName", o[3]);
				map.put("videoAddress", o[4]);
				map.put("deviceSerial", o[5]);
				map.put("channelNo", o[6]);
				
				
				Date now = new Date();
				String tabName = formatSelf(now, "yyyy_MM");
				while(true) {
					Object[] img = ddao.getImg(tabName,Integer.valueOf(o[0].toString()));
					if(img==null) {
						now = addMonth(now,-1);
						tabName = formatSelf(now, "yyyy_MM");
						continue;
					}else if(DateTimeUtil.formatTime("2019-01-01").after(now)) {
						break;
					}
					map.put("fileUrl", img[0]);
					break;
				}
				result.add(map);
			}
		}
		res.add("status", 1)
		.add("msg", "查询成功！")
		.add("result", result)
		.add("allCount", count);
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}
	Calendar c = Calendar.getInstance();
	private String formatSelf(Date date,String f){
		if(date!=null){
			DateFormat d = new SimpleDateFormat(f);
			return d.format(date);
		}else{
			return "";
		}
	}
	private Date addMonth(Date date,int m){
		try {
			c.setTime(date);
			c.add(Calendar.MONTH, m);
			return c.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
