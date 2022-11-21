package com.jinpinghu.timer;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolRecordDao;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONObject;

public class AddToolRecordScheduledExecutor implements Runnable{

	
	TbToolRecordDao toolRecordDao;
	public AddToolRecordScheduledExecutor() {
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			ZJpaHelper.beginTransaction(em);
			toolRecordDao = new TbToolRecordDao(em);
			List<Object[]> syncList = toolRecordDao.findAllIsSync();
			String url ="/agricultural/api/inventory/add";
			if(syncList!=null) {
				for(Object[] te:syncList) {
					JSONObject param = new JSONObject();
					TbToolRecord tool = toolRecordDao.findById(Integer.valueOf(te[0].toString()));
					param.put("num",Integer.valueOf(te[2]+""));
					param.put("productNum",te[1]);
					param.put("type",te[4]);
					param.put("numUnit","件");
					param.put("createTime",te[3]);
					param.put("enterpriseId",te[5]);
					param.put("supplier",te[6]);
					String sendPost = HttpRequestUtil.sendPostToken(url, param, "utf-8");
					JSONObject jo = JSONObject.fromObject(sendPost);
					if(jo.getInt("code")==200&&tool!=null) {
						tool.setIsSync(1);
						toolRecordDao.update(tool);
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
