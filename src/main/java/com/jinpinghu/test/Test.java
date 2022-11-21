package com.jinpinghu.test;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.db.bean.TbService;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbServiceDao;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			ZJpaHelper.beginTransaction(em);
			TbEnterpriseDao tbEnterpriseDao = new TbEnterpriseDao(em);
			TbServiceDao tbServiceDao = new TbServiceDao(em);
			List<Map<String,Object>> list = tbServiceDao.findServiceListByType(null);
			for(Map<String,Object> map:list) {
				Object address = map.get("address");
				String param= "key="+"bd64fe221a6fa115fa414ead33c20a68";
				param += "&address="+address+"&city=嘉兴";
				
				String sendGet = HttpRequestUtil.sendGet("https://restapi.amap.com/v3/geocode/geo", param);
				JSONObject jo = JSONObject.fromObject(sendGet);
				Object geocodes = jo.get("geocodes");
				JSONArray jsonArray = JSONArray.fromObject(geocodes);
				if(jsonArray!=null && !jsonArray.isEmpty()) {
					Object localtion = ((JSONObject)jsonArray.get(0)).get("location");
					String[] split = localtion.toString().split(",");
					TbService service = tbServiceDao.findId(Integer.valueOf(map.get("id").toString()));
					service.setX(split[0]);
					service.setY(split[1]);
					System.out.println(address+"   "+localtion);
				}
			}
			ZJpaHelper.commit(em);	
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("-------------发生异常--------------");
			ZJpaHelper.rollback(em);
		}finally{
			ZJpaHelper.closeEntityManager(em);
		}
	}

}
