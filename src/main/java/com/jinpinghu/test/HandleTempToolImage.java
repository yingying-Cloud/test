package com.jinpinghu.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class HandleTempToolImage {

	public static void main(String[] args) {
		EntityManager em = Persistence.createEntityManagerFactory("jinpinghu").createEntityManager();
		em.getTransaction().begin();
		List<Object[]> imageList = em.createNativeQuery("select id, info_images  FROM temptool").getResultList();
		
		for (Object[] imageObj : imageList) {
			Integer id = Integer.valueOf(imageObj[0].toString());
			String images = (String)imageObj[1];
			String[] imageArray = images.split("\\|");
			for (String imageUrl : imageArray) {
				em.createNativeQuery("INSERT INTO jinpinghu.temptool_file(temptool_id, file_url) VALUES ( :id, :imageUrl)")
				.setParameter("id", id).setParameter("imageUrl", imageUrl).executeUpdate();
			}
		}
		
		em.getTransaction().commit();
		em.close();
	}
}
