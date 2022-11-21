package com.jinpinghu.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;

import net.sf.json.JSONObject;

public class HandlerOrderPlantEnterpriseId {

	public static void main(String[] args) {
		EntityManager em = Persistence.createEntityManagerFactory("jinpinghu").createEntityManager();
		em.getTransaction().begin();
		List<String> orderNumberList = em.createNativeQuery("SELECT tto.order_number FROM tb_tool_order tto where tto.del_flag = 0 and tto.plant_enterprise_id is null limit 2200,1500").getResultList();
		System.out.println(orderNumberList.size());
		int i=0;
		for (String orderNumber : orderNumberList) {
			System.out.println(++i);
			List<String> paramList = em.createNativeQuery("SELECT parameter FROM `tb_call_records` where parameter like :orderNumber").setParameter("orderNumber", "%"+orderNumber+"%").getResultList();
			for (String param : paramList) {
				System.out.println(param);
				JSONObject paramJson = JSONObject.fromObject(param);
				String idcard = paramJson.containsKey("idcard") ? paramJson.getString("idcard") : "";
				if (!StringUtils.isEmpty(idcard)) {
					Integer id = Integer.valueOf(em.createNativeQuery("SELECT id FROM tb_link_order_info where legal_person_idcard = :idcard").setParameter("idcard", idcard).getSingleResult().toString());
					em.createNativeQuery("update tb_tool_order tto set tto.plant_enterprise_id = :linkInfoId where order_number = :orderNumber").setParameter("linkInfoId", id).setParameter("orderNumber", orderNumber).executeUpdate();
				}
			}
		}
		em.getTransaction().commit();
		em.close();
	}
}
