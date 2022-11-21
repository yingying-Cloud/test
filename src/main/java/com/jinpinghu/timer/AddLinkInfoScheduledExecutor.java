package com.jinpinghu.timer;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONObject;

public class AddLinkInfoScheduledExecutor implements Runnable{

	
	TbLinkOrderInfoDao linkOrderInfoDao;
	public AddLinkInfoScheduledExecutor() {
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			ZJpaHelper.beginTransaction(em);
			linkOrderInfoDao = new TbLinkOrderInfoDao(em);
			List<TbLinkOrderInfo> syncList = linkOrderInfoDao.findAllIsSync();
			String url ="/agricultural/api/realName/insert";
			if(syncList!=null) {
				for(TbLinkOrderInfo te:syncList) {
					JSONObject param = new JSONObject();
					param.put("idCard",te.getLegalPersonIdcard());
					param.put("name",te.getLinkPeople());
					param.put("address",te.getAddress());
					param.put("concact",te.getLinkMobile());
					param.put("sex",te.getSex());
					param.put("picture",te.getIdcardPic());
					//linkid的企业编码 逗号隔开给他们 返回的编码保存下来 在订单的时候拼接 给他们 
					param.put("enterpriseNum",linkOrderInfoDao.getLinkOrderInfoEnterpriseSyncNumber(te.getId()));
					String sendPost = HttpRequestUtil.sendPostToken(url, param, "utf-8");
					JSONObject jo = JSONObject.fromObject(sendPost);
					if(jo.getInt("code")==200) {
						JSONObject data = jo.getJSONObject("data");
						String number = data.getString("id");
						System.out.println(number);
						te.setSyncNumber(number);
						te.setIsSync(1);
						linkOrderInfoDao.update(te);
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
		default:
			break;
		}
		return back;
	}
	private Integer typeToCode(Integer type) {
		Integer back = null;
		switch(type) {
		case 12:
			back=1021;
			break;
		case 13:
			back=1011;
			break;
		case 14:
			back=1001;
			break;
		case 17:
			back=991;
			break;
		case 15:
			back=1041;
			break;
		case 16:
			back=1031;
			break;
		default:
			break;
		}
		return back;
	}


}
