package com.jinpinghu.timer;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.db.dao.TbToolRecordDao;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddToolOrderScheduledExecutor implements Runnable{

	TbToolOrderDao toolOrderDao;
	TbShoppingCarDao tscDao;
	public AddToolOrderScheduledExecutor() {
	}
	
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			
			AddToolScheduledExecutor task3 = new AddToolScheduledExecutor();
			task3.run();
			
			AddLinkInfoScheduledExecutor task4 = new AddLinkInfoScheduledExecutor();
			task4.run();
			
//			GetToolScheduledExecutor task9 = new GetToolScheduledExecutor();
//			task9.run();
			
			ZJpaHelper.beginTransaction(em);
			toolOrderDao = new TbToolOrderDao(em);
			tscDao = new TbShoppingCarDao(em);
			List<Object[]> syncList = toolOrderDao.findAllIsSync();
			String url ="/agricultural/api/sales/add";
			if(syncList!=null) {
				for(Object[] te:syncList) {
					JSONObject param = new JSONObject();
					param.put("location",te[0]+"");
					param.put("enterpriseNum",te[1]+"");
					param.put("name",te[8]+";"+te[2]+"");
					param.put("num",Integer.valueOf(te[6]+""));
					param.put("numUtil","件");
					param.put("orderNumber",te[3]+"");
					param.put("orderTime",te[5]+"");
					param.put("price",te[4]+"");
					List<JSONObject> list = toolOrderDao.findInOrderId(Integer.valueOf(te[7].toString()));
					param.put("detail",list);
					if(list==null) {
						TbToolOrder order = toolOrderDao.findById(Integer.valueOf(te[7].toString()));
						if(order!=null) {
							System.out.println(order.getId());
							order.setIsSync(1);
							toolOrderDao.update(order);
						};
						continue;
					}
					System.out.println("orderNumber:"+te[3]+"");
					String sendPost = HttpRequestUtil.sendPostToken(url, param, "utf-8");
					JSONObject jo = JSONObject.fromObject(sendPost);
					if(jo.getInt("code")==200) {
						TbToolOrder order = toolOrderDao.findById(Integer.valueOf(te[7].toString()));
						if(order!=null) {
							System.out.println(order.getId());
							order.setIsSync(1);
							toolOrderDao.update(order);
						}
					}
					
					if(jo.getInt("code")==500&&jo.getString("msg").contains("该订单已存在")) {
						TbToolOrder order = toolOrderDao.findById(Integer.valueOf(te[7].toString()));
						if(order!=null) {
							order.setIsSync(1);
							toolOrderDao.update(order);
						}
					}
					
				}
			}
			
			ZJpaHelper.commit(em);
			
			AddToolRecordScheduledExecutor task5 = new AddToolRecordScheduledExecutor();
			task5.run();
			AddToolRecoveryScheduledExecutor task6 = new AddToolRecoveryScheduledExecutor();
			task6.run();
			
    	}catch (Exception e) {
        	e.printStackTrace();
            System.out.println("-------------发生异常--------------");
            ZJpaHelper.rollback(em);
        }finally{
        	ZJpaHelper.closeEntityManager(em);
        }
    	
	}



}
