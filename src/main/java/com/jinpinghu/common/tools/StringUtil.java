package com.jinpinghu.common.tools;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbUserDao;

public class StringUtil {
	public static boolean isNullOrEmpty(String string) {
		if(string==null||string.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static boolean isNullOrEmpty(String... strings) {
		for (String string : strings) {
			if(string==null||string.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public static String handleArea(String area) {
		if (!isNullOrEmpty(area) && area.length() == 12) {
			if ("0000000000".equals(area.substring(2, 12))) {
				area = area.substring(0,2)+"%";
			}else if ("00000000".equals(area.substring(4, 12))) {
				area = area.substring(0,4)+"%";
			}else if ("000000".equals(area.substring(6, 12))) {
				area = area.substring(0,6)+"%";
			}else if ("000".equals(area.substring(9, 12))) {
				area = area.substring(0,9)+"%";
			}
		}
		return area;
	}
	
	public static String handlePermissionDscd(TbUser loginUser,String userId,EntityManager em) {
		String permissionDscd = getPermissionDscd(loginUser, userId, em);
		return handleArea(permissionDscd);
	}
	
	
	public static String getPermissionDscd(TbUser loginUser,String userId,EntityManager em) {
		if (loginUser == null || isNullOrEmpty(loginUser.getDscd())) {
			TbUserDao userDao = new TbUserDao(em);
			loginUser = userDao.checkLogin2(userId);
			return loginUser == null ? "" : loginUser.getDscd();
		}else {
			return loginUser.getDscd();
		}
	}
	
	
}
