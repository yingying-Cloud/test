package com.jinpinghu.common.tools;

import java.util.HashMap;
import java.util.Map;

public class RegisteredPhoneSmsUtil {
	private static long timeOutTime = 180000;
	private static long intervalTime = 60000;
	//key:�ֻ���   value:s[0]-code���� s[1]:-����ʱ���
		private static Map<String, String[]> codeMap = new HashMap<String, String[]>();
		
		private static String string = "1234567890"; 
		private static int getRandom(int count) {
		    return (int) Math.round(Math.random() * (count));
		}
		
		/**
		 * ������֤��
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
		 * ������֤��Ϣ
		 * @param phone
		 * @param codeInfo
		 */
		public static boolean setCodeInfoToMap(String phone,String[] codeInfo){
			synchronized ("registeredCodeK") {
				//��֤���ֻ������η���ʱ���Ƿ񳬹�������֤ʱ��
				String[] s = codeMap.get(phone);
				if(s!=null){
					if(System.currentTimeMillis()-Long.valueOf(s[1])<intervalTime){
						return false;
					}
				}
				codeMap.put(phone, codeInfo);
				if(codeMap.size()>100){
					//����֤��Ϣͼ�г���100��������������ʱ��Ϣ�������ͷſռ�
					cleanOutTimeCode();
				}
				//qrCodeMapInfo();
				return true;
			}
		}
		
		/**
		 * ������Ϣͼ
		 */
		public static void codeMapInfo(){
			System.out.println("-------------��ǰ��Ϣͼ��Ϣ-------------");
			if(codeMap.size()==0){
				System.out.println("����Ϣ");
			}
			for (String phone : codeMap.keySet()) {
				String[] s = codeMap.get(phone);
				System.out.println("phone:"+phone+"||code:"+s[0]);
			}
			System.out.println("---------------------------------------");
		}
		
		/**
		 * �����ʱ��Ϣ
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
		 * ��֤�û���Ϣ
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
