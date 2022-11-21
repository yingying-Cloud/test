package com.jinpinghu.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.db.bean.TbArea;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbAreaDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONObject;

public class AddScheduledExecutor implements Runnable{

	
	TbEnterpriseDao enterpriseDao;
	TbAreaDao saDao;
	public AddScheduledExecutor() {
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			ZJpaHelper.beginTransaction(em);
			enterpriseDao = new TbEnterpriseDao(em);
			saDao = new TbAreaDao(em);
			List<TbEnterprise> syncList = enterpriseDao.findAllIsSync();
			String url ="/base/api/user/enterpriseReg";
			System.out.println("---------------");
			if(syncList!=null) {
				for(TbEnterprise te:syncList) {
					JSONObject param = new JSONObject();
					JSONObject enterpriseJo = new JSONObject();
					enterpriseJo.put("businessScope","100");
					enterpriseJo.put("name",te.getName());
					enterpriseJo.put("plantingSum",StringUtils.isEmpty(te.getPlantScope())?null:te.getPlantScope());
					enterpriseJo.put("legalPersonTel",StringUtils.isEmpty(te.getEnterpriseLinkMobile())?null:te.getEnterpriseLinkMobile());
					enterpriseJo.put("contract",StringUtils.isEmpty(te.getEnterpriseLinkPeople())?null:te.getEnterpriseLinkPeople());
					enterpriseJo.put("contractTel",StringUtils.isEmpty(te.getEnterpriseLinkMobile())?null:te.getEnterpriseLinkMobile());
					enterpriseJo.put("enterpriseAddress",StringUtils.isEmpty(te.getEnterpriseAddress())?null:te.getEnterpriseAddress());
					enterpriseJo.put("enterpriseCreditCode",StringUtils.isEmpty(te.getEnterpriseCreditCode())?null:te.getEnterpriseCreditCode().trim());
					enterpriseJo.put("enterpriseLatitude",StringUtils.isEmpty(te.getY())?null:te.getY());
					enterpriseJo.put("enterpriseLegalPerson",StringUtils.isEmpty(te.getEnterpriseLegalPerson())?null:te.getEnterpriseLegalPerson());
					enterpriseJo.put("enterpriseLegalPersonIdcard",StringUtils.isEmpty(te.getEnterpriseLegalPersonIdcard())?null:te.getEnterpriseLegalPersonIdcard());
					enterpriseJo.put("enterpriseLongitude",StringUtils.isEmpty(te.getX())?null:te.getX());
					enterpriseJo.put("enterpriseType",1);
					enterpriseJo.put("isChain",101);
					enterpriseJo.put("isLocal","100");
					enterpriseJo.put("cityName","嘉兴市");
					enterpriseJo.put("countyName","平湖市");
					enterpriseJo.put("town","");
					if(!StringUtils.isEmpty(te.getDscd())) {
						TbArea area = saDao.findById(te.getDscd());
						if(area!=null) {enterpriseJo.put("town",area.getName());}
					}
					System.out.println(te.getId());
//					param += "&legalPersonTel="+te.getEnterpriseLinkMobile();
					enterpriseJo.put("registeredCapital",StringUtils.isEmpty(te.getRegisteredFunds())?null:te.getRegisteredFunds());
//					enterpriseJo.put("enterprisePicture",enterpriseDao.findEnterpriseFile(te.getId()).trim());
					param.put("enterprise", enterpriseJo);
					JSONObject userJo = new JSONObject();
					userJo.put("mobile", te.getEnterpriseLinkMobile());
					userJo.put("name", te.getEnterpriseLinkPeople());
					param.put("user", userJo);
					
					String sendPost = HttpRequestUtil.sendPostToken(url, param, "utf-8");
					
					System.out.println(sendPost);
					JSONObject jo = JSONObject.fromObject(sendPost);
					if(jo.getInt("code")==200) {
						JSONObject data = jo.getJSONObject("data");
						if(data!=null) {
							String number = data.getJSONObject("enterprise").getString("number");
							te.setSyncNumber(number);
							te.setIsSync(1);
							enterpriseDao.update(te);
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



}
