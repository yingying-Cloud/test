package com.jinpinghu.logic.plantProtectionOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPlantProtection;
import com.jinpinghu.db.bean.TbPlantProtectionOrder;
import com.jinpinghu.db.bean.TbPlantProtectionOrderCompletion;
import com.jinpinghu.db.bean.TbResPlantProtectionOrderCompletionFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantProtectionDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderCompletionDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbResPlantProtectionOrderCompletionFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantProtectionOrder.param.CompleteOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CompleteOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		CompleteOrderParam myParam = (CompleteOrderParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		String area = myParam.getArea();
		String content = myParam.getContent();
		String completeTime = myParam.getCompleteTime();
		JSONArray fileArray = StringUtils.isEmpty(myParam.getFile()) ? null : JSONArray.fromObject(myParam.getFile());
		
		TbPlantProtectionOrderDao plantProtectionOrderDao = new TbPlantProtectionOrderDao(em);
		
		TbPlantProtectionOrder order = plantProtectionOrderDao.findById(id);
		
		if(order == null) {
			res.add("status", -1).add("msg", "订单不存在");
			return false;
		}
		
		TbPlantProtectionDao plantProtectionDao = new TbPlantProtectionDao(em);
		TbPlantProtection plantProtection = plantProtectionDao.findById(order.getPlantProtectionId());
		
		TbPlantProtectionOrderCompletionDao completionDao = new TbPlantProtectionOrderCompletionDao(em);
		
		TbPlantProtectionOrderCompletion completion = completionDao.findByPlantProtectionOrderId(id);
		
		if(completion == null) {
			completion = new TbPlantProtectionOrderCompletion();
			completion.setPlantProtectionOrderId(id);
			completion.setCompleteTime(DateTimeUtil.formatSelf(completeTime, "yyyy-MM-dd"));
			completion.setArea(area);
			completion.setPrice(new BigDecimal(area).multiply(new BigDecimal(plantProtection.getPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			completion.setDelFlag(0);
			completion.setContent(content);
			completionDao.save(completion);
		}else {
			completion.setCompleteTime(DateTimeUtil.formatSelf(completeTime, "yyyy-MM-dd"));
			completion.setArea(area);
			completion.setPrice(new BigDecimal(area).multiply(new BigDecimal(plantProtection.getPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			completion.setContent(content);
			completionDao.update(completion);
		}
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResPlantProtectionOrderCompletionFileDao trfgDao = new TbResPlantProtectionOrderCompletionFileDao(em);
		List<TbFile> tfs =tfDao.findByPlantProtectionOrderCompleteId(completion.getId());
		List<TbResPlantProtectionOrderCompletionFile> trgfs =trfgDao.findByCompletionId(completion.getId());
		if(trgfs!=null){
			for(TbResPlantProtectionOrderCompletionFile trgf:trgfs){
				trfgDao.delete(trgf);
			}
		}
		if(tfs!=null){
			for(TbFile tbFile:tfs){
				tfDao.delete(tbFile);
			}
		}
		
		if(fileArray.size()>0){
			for(int i=0;i<fileArray.size();i++){
				TbFile tfe =null;
				JSONObject jsonObj=(JSONObject) fileArray.get(i);
				tfe = new TbFile();
				if(jsonObj.containsKey("fileName"))
					tfe.setFileName(jsonObj.getString("fileName"));
				if(jsonObj.containsKey("fileSize"))
					tfe.setFileSize(jsonObj.getString("fileSize"));
				if(jsonObj.containsKey("fileType"))
					tfe.setFileType(jsonObj.getInt("fileType"));
				if(jsonObj.containsKey("fileUrl"))
					tfe.setFileUrl(jsonObj.getString("fileUrl"));
				tfDao.save(tfe);
				TbResPlantProtectionOrderCompletionFile trpf=new TbResPlantProtectionOrderCompletionFile();
				trpf.setFileId(tfe.getId());
				trpf.setPlantProtectionOrderCompletionId(completion.getId());
				trpf.setDelFlag(0);
				trfgDao.save(trpf);
			}
		}
		
		order.setStatus(3);
		order.setTimeConfirm(new Date());
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
