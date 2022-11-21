package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResFileGreenhouses;

public class TbResFileGreenhousesDao2 extends BaseZDao {

	public TbResFileGreenhousesDao2(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<Object[]> findUserByGreenhousesId(Integer greenhousesId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.id,tf.file_name,tf.file_size,tf.file_type,tf.file_url from tb_res_file_greenhouses trfg left join tb_file tf on tf.id=trfg.file_id  where del_flag=0 ");
			if(greenhousesId!=null)
				sb.append(" and greenhouses_id =:greenhousesId ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(greenhousesId!=null)
				query.setParameter("greenhousesId",greenhousesId);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		}catch(RuntimeException  re) {
			throw re;
		}
	}
	public List<TbResFileGreenhouses> findByGreenhousesId(Integer greenhousesId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResFileGreenhouses where greenhousesId =:greenhousesId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("greenhousesId", greenhousesId);
			List<TbResFileGreenhouses> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
