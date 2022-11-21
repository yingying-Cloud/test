package com.jinpinghu.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TbResPlantProtectionMechineDao extends BaseZDao {

	public TbResPlantProtectionMechineDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public void deleteByPlantProtectionId(Integer plantProtectionId) {
		String queryString = " delete from tb_res_plant_protection_mechine where plant_protection_id = :plantProtectionId";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("plantProtectionId", plantProtectionId);
		query.executeUpdate();
	}

}
