package com.jinpinghu.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.sun.org.apache.bcel.internal.generic.I2F;

public class InsertEnterprise {
	
	public static void main(String[] args) {
		EntityManager localhost_em = Persistence.createEntityManagerFactory("localhost").createEntityManager();
		localhost_em.getTransaction().begin();
		
		
		List<Integer> enterpriseIdList = localhost_em.createNativeQuery("SELECT distinct enterprise_id FROM user_copy7 ").getResultList();
		for (Integer enterpriseId : enterpriseIdList) {
			List<Object[]> userList = localhost_em.createNativeQuery("SELECT id, enterprise_id, village, enterprise_name, user_name, user_id_card, area, confirm_area, inflow_area, outflow_area,type,dscd FROM user_copy7 where enterprise_id = "+enterpriseId).getResultList();
			List<Object[]> legalPersonList = userList.stream().filter(user -> user[10].equals(1)).collect(Collectors.toList());
			Object[] legalPerson = legalPersonList.get(0);
			System.out.println(enterpriseId+"___________"+legalPerson[3]);
			localhost_em.createNativeQuery(" INSERT INTO tb_enterprise( name, enterprise_legal_person, enterprise_legal_person_idcard, enterprise_link_people,village,dscd) VALUES ( '"+legalPerson[3]+"', '"+legalPerson[4]+"', '"+legalPerson[5]+"', '"+legalPerson[4]+"','"+legalPerson[2]+"','"+legalPerson[11]+"')").executeUpdate();
			Object databaseEnterpriseId = localhost_em.createNativeQuery(" select LAST_INSERT_ID() ").getSingleResult();
			BigDecimal area = BigDecimal.ZERO;
			BigDecimal confirmArea = BigDecimal.ZERO;
			BigDecimal inflowArea = BigDecimal.ZERO;
			BigDecimal outflowArea = BigDecimal.ZERO;
			for (int i = 0; i < userList.size(); i++) {
				Object[] user = userList.get(i);
				if (user[6] != null) {
					try {
						area = new BigDecimal(user[6].toString());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				if (user[7] != null) {
					try {
						confirmArea = new BigDecimal(user[7].toString());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				if (user[8] != null) {
					try {
						inflowArea = new BigDecimal(user[8].toString());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				if (user[9] != null) {
					try {
						outflowArea = new BigDecimal(user[9].toString());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				if (user[5] == null) {
					continue;
				}
				
				if (user[10].equals(1)) {
					List<Object[]> existEmployeeList = localhost_em.createNativeQuery("SELECT id, enterprise_id, village, enterprise_name, user_name, user_id_card, area, confirm_area, inflow_area, outflow_area,type,dscd FROM user_copy7 where type = 2 and user_id_card = '"+user[5]+"'").getResultList();
					if (!existEmployeeList.isEmpty()) {
						for (Object[] existEmployee : existEmployeeList) {
							localhost_em.createNativeQuery("delete from tb_enterprise_user_production_info where id = "+existEmployee[0]).executeUpdate();
						}
					}
					localhost_em.createNativeQuery("INSERT INTO tb_enterprise_user_production_info(enterprise_id, type, user_name, user_id_card) VALUES (:enterpriseId, 1, :userName, :userIdCard)")
					.setParameter("enterpriseId", databaseEnterpriseId).setParameter("userName", user[4]).setParameter("userIdCard", user[5]).executeUpdate();
				}else {
					List<Object[]> existLeaderList = localhost_em.createNativeQuery("SELECT id, enterprise_id, village, enterprise_name, user_name, user_id_card, area, confirm_area, inflow_area, outflow_area,type,dscd FROM user_copy7 where type = 1 and user_id_card = '"+user[5]+"'").getResultList();
					if (existLeaderList.isEmpty()) {
						localhost_em.createNativeQuery("INSERT INTO tb_enterprise_user_production_info(enterprise_id, type, user_name, user_id_card) VALUES (:enterpriseId,2, :userName, :userIdCard)")
						.setParameter("enterpriseId", databaseEnterpriseId).setParameter("userName", user[4]).setParameter("userIdCard", user[5]).executeUpdate();
					}
				}
			}
			
			localhost_em.createNativeQuery(" update tb_enterprise set area = :area,confirm_area = :confirmArea,inflow_area = :inflowArea,outflow_area=:outflowArea where id = :enterpriseId")
			.setParameter("area", area).setParameter("confirmArea", confirmArea).setParameter("inflowArea", inflowArea).setParameter("outflowArea", outflowArea).setParameter("enterpriseId", databaseEnterpriseId).executeUpdate();
			localhost_em.flush();
			
		}
		
		
		localhost_em.getTransaction().commit();
		
		localhost_em.close();
	}

}
