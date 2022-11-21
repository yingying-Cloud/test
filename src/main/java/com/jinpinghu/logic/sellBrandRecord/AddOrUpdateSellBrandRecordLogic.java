package com.jinpinghu.logic.sellBrandRecord;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResSellBrandRecordFile;
import com.jinpinghu.db.bean.TbSellBrand;
import com.jinpinghu.db.bean.TbSellBrandRecord;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResSellBrandRecordFileDao;
import com.jinpinghu.db.dao.TbSellBrandDao;
import com.jinpinghu.db.dao.TbSellBrandRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellBrandRecord.param.AddOrUpdateSellBrandRecordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateSellBrandRecordLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateSellBrandRecordParam myParam = (AddOrUpdateSellBrandRecordParam)logicParam;
		String id = myParam.getId();
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer sellBrandId=StringUtils.isEmpty(myParam.getSellBrandId())?null:Integer.valueOf(myParam.getSellBrandId());
		Integer recordType = StringUtils.isEmpty(myParam.getRecordType())?null:Integer.valueOf(myParam.getRecordType());
		String number=myParam.getNumber();
		String file = myParam.getFile();
		String useName = myParam.getUseName();
		String useTime = myParam.getUseTime();
		String outMobile = myParam.getOutMobile();
		String outName = myParam.getOutName();
		
		TbSellBrandRecord sellBrandRecord = null;
		BigDecimal allNumber =  BigDecimal.ZERO;
		TbSellBrandRecordDao recordDao = new TbSellBrandRecordDao(em);
		if(!StringUtils.isEmpty(id)) {
			sellBrandRecord = recordDao.findById(Integer.valueOf(id));
		}
		if(sellBrandRecord!=null) {
			
			recordDao.update(sellBrandRecord);
			
			
		}else {
			
			TbSellBrandDao sellBrandDao = new TbSellBrandDao(em);
			TbSellBrand sellBrand = sellBrandDao.findById(sellBrandId);
		
			sellBrandRecord = new TbSellBrandRecord();
			sellBrandRecord.setEnterpriseId(enterpriseId);
			sellBrandRecord.setUseName(useName);
			sellBrandRecord.setUseTime(new Date());
			sellBrandRecord.setSellBrandId(sellBrandId);
			sellBrandRecord.setRecordType(recordType);
			sellBrandRecord.setNumber(number);
			sellBrandRecord.setInputTime(new Date());
			sellBrandRecord.setUseTime(StringUtils.isEmpty(useTime)?null:DateTimeUtil.formatTime2(useTime));
			sellBrandRecord.setOutMobile(outMobile);
			sellBrandRecord.setOutName(outName);
			sellBrandRecord.setDelFlag(0);
			allNumber = sellBrand.getNumber()==null?BigDecimal.ZERO:new BigDecimal(sellBrand.getNumber());
			
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
			sellBrandRecord.setAllNumber(allNumber+"");
			recordDao.save(sellBrandRecord);
			sellBrand.setNumber(allNumber+"");
			sellBrandDao.update(sellBrand);
			
			TbFileDao tfDao = new TbFileDao(em);
			TbResSellBrandRecordFileDao trfgDao =new TbResSellBrandRecordFileDao(em);
			
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
						TbResSellBrandRecordFile trpf=new TbResSellBrandRecordFile();
						trpf.setFileId(tfe.getId());
						trpf.setSellBrandRecordId(Integer.valueOf(sellBrandRecord.getId()));
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
