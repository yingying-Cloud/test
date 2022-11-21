package com.jinpinghu.logic.rotation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbRotationAdvice;
import com.jinpinghu.db.bean.TbRotationEnterprise;
import com.jinpinghu.db.bean.TbRotationTime;
import com.jinpinghu.db.bean.TbRotationTool;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbRotationAdviceDao;
import com.jinpinghu.db.dao.TbRotationEnterpriseDao;
import com.jinpinghu.db.dao.TbRotationTimeDao;
import com.jinpinghu.db.dao.TbRotationToolDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.rotation.param.AddRotationParam;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.jpa.ZJpaHelper;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddRotationLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddRotationParam myParam = (AddRotationParam)logicParam;
		String advice = myParam.getAdvice();
		String enterprise = myParam.getEnterprise();
		String tool = myParam.getTool();
		String rotationTime = myParam.getRotationTime();
		String rotationId = myParam.getRotationId();
		
		TbRotationTimeDao rotationTimeDao = new TbRotationTimeDao(em);
		TbRotationAdviceDao tbRotationAdviceDao = new TbRotationAdviceDao(em);
		TbRotationEnterpriseDao tbRotationEnterpriseDao = new TbRotationEnterpriseDao(em);
		TbRotationToolDao tbRotationToolDao = new TbRotationToolDao(em);
		TbRotationTime rtt =null;
		if(!StringUtils.isEmpty(rotationId)) {
			rtt = rotationTimeDao.findById(Integer.valueOf(rotationId));
		}
		if(rtt==null) {
			rtt = new TbRotationTime();
			rtt.setDelFlag(0);
			rtt.setInputTime(new Date());
			rtt.setRotationTime(rotationTime);
			rotationTimeDao.save(rtt);
		}else {
			rtt.setInputTime(new Date());
			rtt.setRotationTime(rotationTime);
			rotationTimeDao.update(rtt);
		}
		//编辑时删除已存在的关联并重新添加
		tbRotationAdviceDao.delByRotationId(rtt.getId());
		tbRotationEnterpriseDao.delByRotationId(rtt.getId());
		tbRotationToolDao.delByRotationId(rtt.getId());
		
		JSONArray adviceJa = JSONArray.fromObject(advice);
		JSONArray enterpriseJa = JSONArray.fromObject(enterprise);
		JSONArray toolJa = JSONArray.fromObject(tool);
		if(adviceJa!=null) {
			for(int i =0;i<adviceJa.size();i++) {
				JSONObject jo = (JSONObject)adviceJa.get(i);
				TbRotationAdvice tra = new TbRotationAdvice();
				tra.setContent(jo.getString("content"));
				tra.setAdviceTime(DateTimeUtil.formatTime(jo.getString("adviceTime")));
				tra.setDelFlag(0);
				tra.setTitle(jo.getString("title"));
				tra.setRotationId(rtt.getId());
				tbRotationAdviceDao.save(tra);
			}
		}
		if(enterpriseJa!=null) {
			for(int i =0;i<enterpriseJa.size();i++) {
				JSONObject jo = (JSONObject)enterpriseJa.get(i);
				TbRotationEnterprise tra = new TbRotationEnterprise();
				tra.setEnterpriseId(jo.getInt("enterpriseId"));
				tra.setDelFlag(0);
				tra.setRotationId(rtt.getId());
				tbRotationEnterpriseDao.save(tra);
			}
		}
		TbToolCatalogDao tbToolCatalogDao = new TbToolCatalogDao(em);
		if(toolJa!=null) {
			for(int i =0;i<toolJa.size();i++) {
				JSONObject jo = (JSONObject)toolJa.get(i);
				TbRotationTool tra = new TbRotationTool();
				tra.setToolZeroId(jo.getInt("toolZeroId"));
				tra.setDelFlag(0);
				tra.setRotationId(rtt.getId());
				tbRotationToolDao.save(tra);
				
//				TbToolCatalog toolCatelog = tbToolCatalogDao.findById(jo.getInt("toolZeroId"));
//				if(toolCatelog!=null&&!StringUtils.isEmpty(toolCatelog.getUniformPrice())&&toolCatelog.getUniformPrice().equals("1")) {
//					TbEnterpriseZeroDao tbEnterpriseZeroDao = new TbEnterpriseZeroDao(em);
//					TbToolDao tbToolDao = new TbToolDao(em);
//					List<Map<String,Object>> list = tbEnterpriseZeroDao.findAllZero(null,null,null,null);
//					if(jo.containsKey("price")) {
//						toolCatelog.setPrice(jo.getString("price"));
//						tbToolCatalogDao.update(toolCatelog);
//					}
//					if(list!=null) {
//						for(Map<String,Object> map:list ) {
//							String enterpriseId = map.get("id").toString();
//							TbTool tbTool = tbToolDao.findByCode(toolCatelog.getCode(), Integer.valueOf(enterpriseId));
//							if(tbTool!=null&&!StringUtils.isEmpty(tbTool.getUniformPrice())&&tbTool.getUniformPrice().equals("1")&&jo.containsKey("price")) {
//								tbTool.setPrice(jo.getString("price"));
//								tbToolDao.save(tbTool);
//							}
//						}
//					}
//				}
			}
		}
		
		Thread thread = new Thread(()-> {
			EntityManager ems = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
			try {
				ZJpaHelper.beginTransaction(ems);
				TbToolCatalogDao tbToolCatalogDaos = new TbToolCatalogDao(ems);
			if(toolJa!=null) {
				for(int i =0;i<toolJa.size();i++) {
					JSONObject jo = (JSONObject)toolJa.get(i);
					TbToolCatalog toolCatelog = tbToolCatalogDaos.findById(jo.getInt("toolZeroId"));
					if(toolCatelog!=null&&!StringUtils.isEmpty(toolCatelog.getUniformPrice())&&toolCatelog.getUniformPrice().equals("1")) {
						TbEnterpriseZeroDao tbEnterpriseZeroDao = new TbEnterpriseZeroDao(ems);
						TbToolDao tbToolDao = new TbToolDao(ems);
						List<Map<String,Object>> list = tbEnterpriseZeroDao.findAllZero(null,null,null,null);
						if(jo.containsKey("price")) {
							toolCatelog.setPrice(jo.getString("price"));
							tbToolCatalogDaos.update(toolCatelog);
						}
						if(list!=null) {
							for(Map<String,Object> map:list ) {
								String enterpriseId = map.get("id").toString();
								TbTool tbTool = tbToolDao.findByCode(toolCatelog.getCode(), Integer.valueOf(enterpriseId));
								if(tbTool!=null&&!StringUtils.isEmpty(tbTool.getUniformPrice())&&tbTool.getUniformPrice().equals("1")&&jo.containsKey("price")) {
									tbTool.setPrice(jo.getString("price"));
									tbToolDao.save(tbTool);
								}
							}
						}
					}
				}
			}
			ZJpaHelper.commit(ems);
			}catch (Exception e) {
	        	e.printStackTrace();
	            System.out.println("-------------发生异常--------------");
	            ZJpaHelper.rollback(ems);
	        }finally{
	        	ZJpaHelper.closeEntityManager(ems);
	        }
		});
		thread.start();
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
	
}
