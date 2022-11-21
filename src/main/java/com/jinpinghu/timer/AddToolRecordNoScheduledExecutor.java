package com.jinpinghu.timer;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.dao.TbToolDao;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddToolRecordNoScheduledExecutor implements Runnable{

	public AddToolRecordNoScheduledExecutor() {
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			
			ZJpaHelper.beginTransaction(em);
			TbToolDao tbToolDao = new TbToolDao(em);
			String url ="/agricultural/api/product/getRecordNoList";
			
			List<TbTool> syncList = tbToolDao.findAllRecordNo();
			if(syncList!=null) {
				for(TbTool te:syncList) {
					JSONObject param = new JSONObject();
					param.put("number",te.getSyncNumber());
					param.put("pushStatus",1);
					
					String sendPost = HttpRequestUtil.sendPostToken(url, param, "utf-8");
					JSONObject jo = JSONObject.fromObject(sendPost);
					if(jo.getInt("code")==200) {
						JSONArray data = jo.getJSONArray("data");
						if(data!=null&&data.size()>0) {
							JSONObject object = (JSONObject) data.get(0);
							String recordNo = object.getString("recordNo");
							te.setRecordNo(recordNo);
							tbToolDao.update(te);
						}
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
	private String toxicityText(String toxicity) {
		String back = "";
		switch(toxicity) {
		case "微毒":
			back="102";
			break;
		case "低毒":
			back="102";
			break;
		case "中毒":
			back="103";
			break;
		case "中等毒":
			back="103";
			break;
		case "高毒":
			back="104";
			break;
		case "无毒":
			back="101";
			break;
		case "剧毒":
			back="104";
			break;
		default:
			back="101";
			break;
		}
		return back;
	}
	private String FormText(String forms) {
		String back = "";
		switch(forms) {
		case "粉剂":
			back="100";
			break;
		case "颗粒":
			back="101";
			break;
		case "液体":
			back="102";
			break;
		default:
			back="其他";
			break;
		}
		return back;
	}
	
	private String producedText(String produced) {
		String back = "";
		switch(produced) {
		case "省内":
			back="100";
			break;
		case "省外":
			back="101";
			break;
		case "进口":
			back="102";
			break;
		default:
			break;
		}
		return back;
	}
	
	private Integer typeToCode(Integer type) {
		Integer back = null;
		switch(type) {
		case 102:
			back=12;
			break;
		case 101:
			back=13;
			break;
		case 100:
			back=14;
			break;
		case 99:
			back=17;
			break;
		case 104:
			back=15;
			break;
		case 103:
			back=16;
			break;
		default:
			break;
		}
		return back;
	}
}
