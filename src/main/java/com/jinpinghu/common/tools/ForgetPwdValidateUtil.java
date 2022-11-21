package com.jinpinghu.common.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fw.olib.org.json.JSONException;
import fw.olib.org.json.JSONObject;

public class ForgetPwdValidateUtil {
	private static long timeOutTime = 300000;
	//key:手机号   value:s[0]-临时票据 s[1]:-生成时间戳
	private static Map<String, String[]> tempTicketMap = new HashMap<String, String[]>();
	
	/**
	 * 生成临时票据
	 * @return
	 */
	public static String getTempTicket(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 置入临时票据
	 * @param phone
	 * @param codeInfo
	 */
	public static void setCodeInfoToMap(String phone,String[] tempTicketInfo){
		synchronized ("tempTicketK") {
			tempTicketMap.put(phone, tempTicketInfo);
			if(tempTicketMap.size()>100){
				//若图中超过100个，则调用清理过时信息操作，释放空间
				cleanOutTimeTicket();
			}
			//qrCodeMapInfo();
		}
	}
	
	/**
	 * 遍历临时票据
	 */
	public static void codeMapInfo(){
		System.out.println("-------------当前信息图信息-------------");
		if(tempTicketMap.size()==0){
			System.out.println("无信息");
		}
		for (String phone : tempTicketMap.keySet()) {
			String[] s = tempTicketMap.get(phone);
			System.out.println("phone:"+phone+"||tempTicket:"+s[0]);
		}
		System.out.println("---------------------------------------");
	}
	
	/**
	 * 清理过时信息
	 */
	private static void cleanOutTimeTicket() {
		for (String phone : tempTicketMap.keySet()) {
			String[] s = tempTicketMap.get(phone);
			if(s!=null){
				if(System.currentTimeMillis()-Long.valueOf(s[1])>timeOutTime){
					tempTicketMap.remove(phone);
				}
			}
		}
	}
	
	/**
	 * 验证临时凭证
	 * @param tempTicket
	 * @return
	 */
	public static boolean validateTempTicketInfo(String phone,String tempTicket){
		String[] s;
		boolean result = false;
		synchronized ("tempTicketK") {
			s = tempTicketMap.get(phone);
			if(s!=null&&s[0].equals(tempTicket.trim())){
				tempTicketMap.remove(phone);
				if(System.currentTimeMillis()-Long.valueOf(s[1])>timeOutTime){
					result = false;
				}else{
					result = true;
				}
			}
		}
		return result;
	}
	
}
