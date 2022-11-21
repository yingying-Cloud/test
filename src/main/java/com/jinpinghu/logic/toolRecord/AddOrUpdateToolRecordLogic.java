package com.jinpinghu.logic.toolRecord;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResToolRecordFile;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolRecordFileDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecord.param.AddOrUpdateToolRecordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateToolRecordLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateToolRecordParam myParam = (AddOrUpdateToolRecordParam)logicParam;
		String id = myParam.getId();
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer toolId=StringUtils.isEmpty(myParam.getToolId())?null:Integer.valueOf(myParam.getToolId());
//		Integer toolEnterpriseId=StringUtils.isEmpty(myParam.getToolEnterpriseId())?null:Integer.valueOf(myParam.getToolEnterpriseId());
		Integer recordType = StringUtils.isEmpty(myParam.getRecordType())?null:Integer.valueOf(myParam.getRecordType());
		String number=myParam.getNumber();
		String file = myParam.getFile();
		String supplierName = myParam.getSupplierName();
		String useName = myParam.getUseName();
		
		String outMobile = myParam.getOutMobile();
		String outName = myParam.getOutName();
		String price = myParam.getPrice();
		
		Date now = new Date();
		
		Date useTime = StringUtils.isEmpty(myParam.getUseTime()) ? now : DateTimeUtil.formatSelf(myParam.getUseTime(), "yyyy-MM-dd HH:mm:ss");
		useTime = useTime == null ? now : (useTime.after(now) ? now : useTime);
		
		TbToolRecord toolRecord = null;
		BigDecimal allNumber =  BigDecimal.ZERO;
		TbToolRecordDao recordDao2 = new TbToolRecordDao(em);
		if(!StringUtils.isEmpty(id)) {
			toolRecord = recordDao2.findById(Integer.valueOf(id));
		}
		if(toolRecord!=null) {
			
			recordDao2.update(toolRecord);
			
			
		}else {
			
			TbToolDao toolDao2 = new TbToolDao(em);
			TbTool tool = toolDao2.findById(toolId);
		
			toolRecord = new TbToolRecord();
			toolRecord.setEnterpriseId(enterpriseId);
			toolRecord.setSupplierName(supplierName);
			toolRecord.setUseName(useName);
			toolRecord.setToolId(toolId);
			toolRecord.setRecordType(recordType);
			toolRecord.setNumber(number);
			toolRecord.setInputTime(now);
			toolRecord.setUseTime(useTime);
			toolRecord.setOutMobile(outMobile);
			toolRecord.setOutName(outName);
			toolRecord.setDelFlag(0);
			toolRecord.setPrice(price);
			allNumber = tool.getNumber()==null?BigDecimal.ZERO:new BigDecimal(tool.getNumber());
			
			if(recordType!=null) {
				if(!StringUtils.isEmpty(number)) {
					//入库
					if(recordType==1) {
						allNumber=allNumber.add( new BigDecimal(number)).setScale(2);
					}
					//领用 减去�?次�??
					if(recordType==2) {
						allNumber=allNumber.subtract( new BigDecimal(number)).setScale(2);
//						if(tool!=null&&tool.getSffl()==1) {
						if(allNumber.compareTo(BigDecimal.ZERO)==-1) {
							res.add("status",2)
							.add("msg", "操作数超过库存！");
							return true;
						}
//						}
					}
					//归还  加上�?次�??
					if(recordType==3) {
						allNumber=allNumber.add( new BigDecimal(number)).setScale(2);
					}
					if(recordType==4) {
						allNumber=allNumber.subtract( new BigDecimal(number)).setScale(2);
						if(allNumber.compareTo(BigDecimal.ZERO)==-1) {
							res.add("status",2)
							.add("msg", "操作数超过库存！");
							return true;
						}
					}
				}
			}
			toolRecord.setAllNumber(allNumber+"");
			recordDao2.save(toolRecord);
			if(enterpriseId!=null) {
				tool.setEnterpriseId(enterpriseId);
			}
			tool.setNumber(allNumber+"");
			toolDao2.update(tool);
			
			TbFileDao tfDao = new TbFileDao(em);
			TbResToolRecordFileDao trfgDao =new TbResToolRecordFileDao(em);
			
			if(!StringUtils.isEmpty(file)){
				JSONArray arrayF= JSONArray.fromObject(file);
				if(arrayF.size()>0){
					for(int i=0;i<arrayF.size();i++){
						TbFile tfe =null;
						JSONObject jsonObj=(JSONObject) arrayF.get(i);
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
						TbResToolRecordFile trpf=new TbResToolRecordFile();
						trpf.setFileId(tfe.getId());
						trpf.setToolRecordId(Integer.valueOf(toolRecord.getId()));
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
			
			
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
}
