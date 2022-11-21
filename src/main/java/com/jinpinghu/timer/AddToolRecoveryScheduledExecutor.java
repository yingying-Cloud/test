package com.jinpinghu.timer;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.bean.TbToolRecoveryRecord;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolRecoveryRecordDao;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONObject;

public class AddToolRecoveryScheduledExecutor implements Runnable{

	TbToolRecoveryRecordDao toolRecoveryRecordDao;
	public AddToolRecoveryScheduledExecutor() {
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			ZJpaHelper.beginTransaction(em);
			toolRecoveryRecordDao = new TbToolRecoveryRecordDao(em);
			List<Object[]> syncList = toolRecoveryRecordDao.findAllIsSync();
			String url ="/agricultural/api/recovery/insert";
			if(syncList!=null) {
				for(Object[] te:syncList) {
					JSONObject param = new JSONObject();
					
					TbToolRecoveryRecord record = toolRecoveryRecordDao.findById(Integer.valueOf(te[0].toString()));
					
					param.put("buyer",te[1]);
					param.put("productNum",te[2]);
					param.put("enterpriseNum",te[3]);
					param.put("operator",te[9]);
					param.put("money",te[5]);
					param.put("name",te[7]);
					param.put("num",te[6]);
					param.put("time",te[8]);
					param.put("type",te[7]);
					
					String sendPost = HttpRequestUtil.sendPostToken(url, param, "utf-8");
					JSONObject jo = JSONObject.fromObject(sendPost);
					if(jo.getInt("code")==200&&record!=null) {
						record.setIsSync(1);
						toolRecoveryRecordDao.update(record);
					}
					
					
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
