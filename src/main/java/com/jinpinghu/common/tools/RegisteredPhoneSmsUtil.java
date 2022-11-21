package com.jinpinghu.common.tools;

import java.util.HashMap;
import java.util.Map;

public class RegisteredPhoneSmsUtil {
	private static long timeOutTime = 180000;
	private static long intervalTime = 60000;
	//key:手机号   value:s[0]-code内容 s[1]:-生成时间戳
		private static Map<String, String[]> codeMap = new HashMap<String, String[]>();
		
		private static String string = "1234567890"; 
		private static int getRandom(int count) {
		    return (int) Math.round(Math.random() * (count));
		}
		
		/**
		 * 生成验证码
		 * @return
		 */
		public static String getCode(){
			StringBuffer sb = new StringBuffer();
		    int len = string.length();
		    for (int i = 0; i < 6; i++) {
		        sb.append(string.charAt(getRandom(len-1)));
		    }
		    return sb.toString();
		}
		
		/**
		 * 置入验证信息
		 * @param phone
		 * @param codeInfo
		 */
		public static boolean setCodeInfoToMap(String phone,String[] codeInfo){
			synchronized ("registeredCodeK") {
				//验证该手机号两次发送时间是否超过发送验证时间
				String[] s = codeMap.get(phone);
				if(s!=null){
					if(System.currentTimeMillis()-Long.valueOf(s[1])<intervalTime){
						return false;
					}
				}
				codeMap.put(phone, codeInfo);
				if(codeMap.size()>100){
					//若验证信息图中超过100个，则调用清理过时信息操作，释放空间
					cleanOutTimeCode();
				}
				//qrCodeMapInfo();
				return true;
			}
		}
		
		/**
		 * 遍历信息图
		 */
		public static void codeMapInfo(){
			System.out.println("-------------当前信息图信息-------------");
			if(codeMap.size()==0){
				System.out.println("无信息");
			}
			for (String phone : codeMap.keySet()) {
				String[] s = codeMap.get(phone);
				System.out.println("phone:"+phone+"||code:"+s[0]);
			}
			System.out.println("---------------------------------------");
		}
		
		/**
		 * 清理过时信息
		 */
		private static void cleanOutTimeCode() {
			for (String phone : codeMap.keySet()) {
				String[] s = codeMap.get(phone);
				if(s!=null){
					if(System.currentTimeMillis()-Long.valueOf(s[1])>timeOutTime){
						codeMap.remove(phone);
					}
				}
			}
		}
		
		/**
		 * 验证用户信息
		 * @param code
		 * @return
		 */
		public static boolean validateCodeInfo(String phone,String code){
			synchronized ("registeredCodeK") {
				String[] s = codeMap.get(phone);
				if(s!=null&&s[0].equals(code.trim())){
					codeMap.remove(phone);
					if(System.currentTimeMillis()-Long.valueOf(s[1])>timeOutTime){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			}
		}
		
	}
