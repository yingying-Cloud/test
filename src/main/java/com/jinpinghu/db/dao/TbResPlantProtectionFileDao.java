package com.jinpinghu.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TbResPlantProtectionFileDao extends BaseZDao {

	public TbResPlantProtectionFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public void DelFileByPlantProtectionId(Integer id) {
		try {
			String queryString = "delete from  tb_res_plant_protection_file WHERE plant_protection_id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
