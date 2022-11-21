package com.jinpinghu.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TbResPlantServiceFileDao extends BaseZDao {

	public TbResPlantServiceFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public void DelFileByPlantServiceId(Integer id) {
		try {
			String queryString = "delete from  tb_res_plant_service_file WHERE plant_service_id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
