package com.jinpinghu.common.tools;

import java.util.Date;
import java.util.HashMap;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.jinpinghu.db.bean.TbSmsInfo;
import com.jinpinghu.db.dao.TbSmsInfoDao;

import fw.jbiz.common.helper.StringUtil;

public class SendSmsUtil {
	private static CCPRestSmsSDK restAPI = null;
	
	private static void smsInit(){
		restAPI = new CCPRestSmsSDK();
		restAPI.init("app.cloopen.com", "8883");
		// 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
		restAPI.setAccount("aaf98f89506fc2f001508925fdf31808",
				"a612d8ea741840f7a5d596b28e3bb278");
		// 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
		restAPI.setAppId("8a216da86ab0b4d2016ab9888def06e0");
		// 请使用管理控制台中已创建应用的APPID。
	}
	/**
	 * 发送短信
	 * @param phones	手机号 多个用 ,分割 最多一次200个
	 * @param template	模板ID
	 * @param datas		占位数据
	 */
	public static void sendSms(String phones, String template, String[] datas,
			TbSmsInfoDao tbSmsInfoDao) {
		if(restAPI==null){
			synchronized (SendSmsUtil.class) {
				if(restAPI==null){
					smsInit();
				}
			}
		}
		
		if(datas==null||datas.length==0
				||StringUtil.isNullOrEmpty(template)
				||StringUtil.isNullOrEmpty(phones)){
			return;
		}
		String content = "[\"";
		for (int i = 0; i < datas.length; i++) {
			content += "\"" + datas[i]+"\",";
		}
		content = content.substring(0, content.length()-1)+"]";
		if(tbSmsInfoDao!=null){
			TbSmsInfo tbSmsInfo = new TbSmsInfo(phones,template,content, new Date());
			tbSmsInfoDao.save(tbSmsInfo);
		}
		HashMap<String, Object> sendTemplateSMS = restAPI.sendTemplateSMS(phones, template, datas);
		System.out.println(sendTemplateSMS);
	}
	
}
