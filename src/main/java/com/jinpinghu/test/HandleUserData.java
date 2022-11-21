package com.jinpinghu.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;



public class HandleUserData {
	
	public static void main(String[] args) {
		EntityManager localhost_em = Persistence.createEntityManagerFactory("localhost").createEntityManager();
		localhost_em.getTransaction().begin();
		
		List<Object[]> list = localhost_em.createNativeQuery("SELECT id, enterprise_id, village, enterprise_name, user_name, user_id_card, area, confirm_area, inflow_area, outflow_area FROM user_copy7 ").getResultList();
		String lastEnterpriseName = "";
		int enterpriseId = 0;
		int firstId = 0;
		boolean isSetLeader = false;
		for (Object[] data : list) {
			try {
				String thisEnterpriseName = data[3] == null ? "" : data[3].toString();
				System.out.println(data[0]);
				if (thisEnterpriseName.equals(lastEnterpriseName)) {
					if (lastEnterpriseName.equals(data[4])) {
						localhost_em.createNativeQuery(" update user_copy7 set type = 1,enterprise_id = "+enterpriseId+" where id = "+data[0]).executeUpdate();
						isSetLeader = true;
					}else {
						localhost_em.createNativeQuery(" update user_copy7 set type = 2,enterprise_id = "+enterpriseId+" where id = "+data[0]).executeUpdate();
					}
				}else {
					if (!isSetLeader) {
						localhost_em.createNativeQuery(" update user_copy7 set type = 1 where id = "+firstId).executeUpdate();
					}
					
					isSetLeader = false;
					firstId = (Integer)data[0];
					lastEnterpriseName = thisEnterpriseName;	
					++enterpriseId;
					localhost_em.createNativeQuery(" update user_copy7 set type = 2,enterprise_id = "+enterpriseId+" where id = "+firstId).executeUpdate();
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		
		localhost_em.getTransaction().commit();
		
		localhost_em.close();
	}

}
