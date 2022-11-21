package com.jinpinghu.logic.warehouse;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPlantWarehouse;
import com.jinpinghu.db.bean.TbResFilePlantWarehouse;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.bean.TbWork;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantWarehouseDao;
import com.jinpinghu.db.dao.TbResFilePlantWarehouseDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.warehouse.param.AddOrUpdateWareHouseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateWareHouseLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateWareHouseParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateWareHouseParam myParam=(AddOrUpdateWareHouseParam)AddOrUpdateWareHouseParam;
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer recordType=StringUtil.isNullOrEmpty(myParam.getRecordType())?null:Integer.valueOf(myParam.getRecordType());
		String outDirection=myParam.getOutDirection();
		String logisticsInfo=myParam.getLogisticsInfo();
		String linkPeople=myParam.getLinkPeople();
		Integer workId=StringUtil.isNullOrEmpty(myParam.getWorkId())?null:Integer.valueOf(myParam.getWorkId());
		String linkMobile=myParam.getLinkMobile();
		String number=myParam.getNumber();
		String batchNum=myParam.getBatchNum();
		Integer brandId=StringUtil.isNullOrEmpty(myParam.getBrandId())?null:Integer.valueOf(myParam.getBrandId());
		Date inputTime = StringUtil.isNullOrEmpty(myParam.getInputTime())?null:DateTimeUtil.formatTime(myParam.getInputTime());
		String price = myParam.getPrice();
		String oddNumber = myParam.getOddNumber();
		String wrapper = myParam.getWrapper();
		
		String file=StringUtil.isNullOrEmpty(myParam.getFile())?null:myParam.getFile();
		
		TbUser tu=new TbUser();
		TbUserDao tudao=new TbUserDao(em);
		TbPlantWarehouseDao wdao=new TbPlantWarehouseDao(em);
		TbBrandDao tbDao = new TbBrandDao(em);
		TbWorkDao tbWorkDao = new TbWorkDao(em);
		
		TbPlantWarehouse tpr;

		//获取操作员姓名
		tu=tudao.checkLogin2(myParam.getUserId());
		if(tu==null){
			res.add("status", 2)
			.add("msg", "无数据！");
			return true;
		}
		
		if(recordType==1){//入库
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHH");
			TbWork tw = tbWorkDao.findById(workId);
			TbBrand tp=tbDao.findById(Integer.valueOf(tw.getCrop()));
			batchNum=tp.getProductName()+sdf.format(new Date());
			tpr=new TbPlantWarehouse();
			tpr.setEnterpriseId(enterpriseId);
			tpr.setBrandId(tp.getId());
			tpr.setWorkId(workId);
			tpr.setPersionId(tu.getId());
			tpr.setPersionName(tu.getName());
			tpr.setRecordType(recordType);
			tpr.setBatchNum(batchNum);
			tpr.setOutDirection(outDirection);
			tpr.setLogisticsInfo(logisticsInfo);
			tpr.setLinkMobile(linkMobile);
			tpr.setLinkPeople(linkPeople);
			tpr.setNumber(number);
			tpr.setInputTime(inputTime);
			tpr.setDelFlag(0);
			tpr.setOddNumber(oddNumber);
			tpr.setWrapper(wrapper);
			tpr.setPrice(price);
			wdao.save(tpr);			

			if(file!=null){
				TbResFilePlantWarehouseDao fdao=new TbResFilePlantWarehouseDao(em);
				TbFile tf;
				JSONArray ja=JSONArray.fromObject(file);
				JSONObject jo;
				TbFileDao tfdao=new TbFileDao(em); 
				
				if(ja.size()>0){
					for(int i=0;i<ja.size();i++){
						jo=(JSONObject)ja.get(i);
						tf=new TbFile();
						if(jo.containsKey("fileName")){
							tf.setFileName(jo.getString("fileName").toString());
						}
						if(jo.containsKey("filesize")){
							tf.setFileSize(jo.getString("fileSize").toString());
						}
						tf.setFileType(Integer.valueOf(jo.getString("fileType").toString()));
						tf.setFileUrl(jo.getString("fileUrl").toString());
						tfdao.save(tf);

						TbResFilePlantWarehouse trfpr=new TbResFilePlantWarehouse();
						trfpr.setPlantWarehouseId(tpr.getId());
						trfpr.setDelFlag(0);
						trfpr.setFileId(tf.getId());
						fdao.save(trfpr);
					}
				}
			}
			
		}else if(recordType==2){//出库或报损
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHH");
			TbWork tw = tbWorkDao.findById(workId);
			TbBrand tp=tbDao.findById(Integer.valueOf(tw.getCrop()));
			batchNum=tp.getProductName()+sdf.format(new Date());
			tpr=new TbPlantWarehouse();
			tpr.setEnterpriseId(enterpriseId);
			tpr.setBatchNum(batchNum);
			tpr.setBrandId(tp.getId());
			tpr.setWorkId(workId);
			tpr.setPersionId(tu.getId());
			tpr.setPersionName(tu.getName());
			tpr.setRecordType(recordType);
			tpr.setBatchNum(batchNum);
			tpr.setOutDirection(outDirection);
			tpr.setLogisticsInfo(logisticsInfo);
			tpr.setLinkMobile(linkMobile);
			tpr.setLinkPeople(linkPeople);
			tpr.setNumber(number);
			tpr.setInputTime(inputTime);
			tpr.setDelFlag(0);
			tpr.setOddNumber(oddNumber);
			tpr.setWrapper(wrapper);
			tpr.setPrice(price);
			wdao.save(tpr);			
			
			if(file!=null){
				TbResFilePlantWarehouseDao fdao=new TbResFilePlantWarehouseDao(em);
				TbFile tf;
				JSONArray ja=JSONArray.fromObject(file);
				JSONObject jo;
				TbFileDao tfdao=new TbFileDao(em); 
				if(ja.size()>0){
					for(int i=0;i<ja.size();i++){
						jo=(JSONObject)ja.get(i);
						tf=new TbFile();
						if(jo.containsKey("fileName")){
							tf.setFileName(jo.getString("fileName").toString());
						}
						if(jo.containsKey("filesize")){
							tf.setFileSize(jo.getString("fileSize").toString());
						}
						tf.setFileType(Integer.valueOf(jo.getString("fileType").toString()));
						tf.setFileUrl(jo.getString("fileUrl").toString());
						tfdao.save(tf);
						
						TbResFilePlantWarehouse trfpr=new TbResFilePlantWarehouse();
						trfpr.setPlantWarehouseId(tpr.getId());
						trfpr.setDelFlag(0);
						trfpr.setFileId(tf.getId());
						fdao.save(trfpr);
					}
				}
			}
		}
		res.add("status",1);
		res.add("msg", "操作成功！");
		return true;
	}
}
