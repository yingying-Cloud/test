package com.jinpinghu.logic.plantServiceOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPlantService;
import com.jinpinghu.db.bean.TbPlantServiceOrder;
import com.jinpinghu.db.bean.TbPlantServiceOrderCompletion;
import com.jinpinghu.db.bean.TbResPlantServiceOrderCompletionFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantServiceDao;
import com.jinpinghu.db.dao.TbPlantServiceOrderCompletionDao;
import com.jinpinghu.db.dao.TbPlantServiceOrderDao;
import com.jinpinghu.db.dao.TbResPlantServiceOrderCompletionFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantServiceOrder.param.CompleteOrderParam;

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
		
		TbPlantServiceOrderDao plantServiceOrderDao = new TbPlantServiceOrderDao(em);
		
		TbPlantServiceOrder order = plantServiceOrderDao.findById(id);
		
		if(order == null) {
			res.add("status", -1).add("msg", "订单不存在");
			return false;
		}
		
		TbPlantServiceDao plantServiceDao = new TbPlantServiceDao(em);
		TbPlantService plantService = plantServiceDao.findById(order.getPlantServiceId());
		
		TbPlantServiceOrderCompletionDao completionDao = new TbPlantServiceOrderCompletionDao(em);
		
		TbPlantServiceOrderCompletion completion = completionDao.findByPlantServiceOrderId(id);
		
		if(completion == null) {
			completion = new TbPlantServiceOrderCompletion();
			completion.setPlantServiceOrderId(id);
			completion.setCompleteTime(StringUtils.isEmpty(completeTime) ? new Date() : DateTimeUtil.formatSelf(completeTime, "yyyy-MM-dd"));
			completion.setArea(area);
			completion.setPrice(new BigDecimal(plantService.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			completion.setDelFlag(0);
			completion.setContent(content);
			completionDao.save(completion);
		}else {
			completion.setCompleteTime(StringUtils.isEmpty(completeTime) ? new Date() : DateTimeUtil.formatSelf(completeTime, "yyyy-MM-dd"));
			completion.setArea(area);
			completion.setPrice(new BigDecimal(plantService.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			completion.setContent(content);
			completionDao.update(completion);
		}
		
		TbFileDao tfDao = new TbFileDao(em);
		TbResPlantServiceOrderCompletionFileDao trfgDao = new TbResPlantServiceOrderCompletionFileDao(em);
		List<TbFile> tfs =tfDao.findByPlantServiceOrderCompleteId(completion.getId());
		List<TbResPlantServiceOrderCompletionFile> trgfs =trfgDao.findByCompletionId(completion.getId());
		if(trgfs!=null){
			for(TbResPlantServiceOrderCompletionFile trgf:trgfs){
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
				TbResPlantServiceOrderCompletionFile trpf=new TbResPlantServiceOrderCompletionFile();
				trpf.setFileId(tfe.getId());
				trpf.setPlantServiceOrderCompletionId(completion.getId());
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
