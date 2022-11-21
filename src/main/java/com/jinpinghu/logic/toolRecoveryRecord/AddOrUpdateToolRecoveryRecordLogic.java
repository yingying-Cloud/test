package com.jinpinghu.logic.toolRecoveryRecord;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbResToolRecoveryRecordFile;
import com.jinpinghu.db.bean.TbToolRecovery;
import com.jinpinghu.db.bean.TbToolRecoveryRecord;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbResToolRecoveryRecordFileDao;
import com.jinpinghu.db.dao.TbToolRecoveryDao;
import com.jinpinghu.db.dao.TbToolRecoveryRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.AddOrUpdateToolRecoveryRecordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddOrUpdateToolRecoveryRecordLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateToolRecoveryRecordParam myParam = (AddOrUpdateToolRecoveryRecordParam)logicParam;
		String id = myParam.getId();
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer toolRecoveryId=StringUtils.isEmpty(myParam.getToolRecoveryId())?null:Integer.valueOf(myParam.getToolRecoveryId());
		String number=myParam.getNumber();
		String file = myParam.getFile();
		String useName = myParam.getUseName();
		String inputTime = myParam.getInputTime();
		String operator = myParam.getOperator();
		String useMobile = myParam.getUseMobile();
		String linkOrderInfoId = myParam.getLinkOrderInfoId();
		Integer toolId = StringUtils.isEmpty(myParam.getToolId()) ? null : Integer.valueOf(myParam.getToolId());
		
		TbToolRecoveryDao toolRecoveryDao2 = new TbToolRecoveryDao(em);
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		TbEnterpriseUserProductionInfoDao enterpriseUserProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
		TbLinkOrderInfo linkOrderInfo = null;
		List<Integer> userEnterpriseIdList = null;
		if(linkOrderInfoId!=null) {
			linkOrderInfo = linkOrderInfoDao.findByIegalPersonIdcard(linkOrderInfoId);
			
			userEnterpriseIdList = enterpriseUserProductionInfoDao.getEnterpriseIdByIdcard(linkOrderInfo.getLegalPersonIdcard());
		}
		
		String recordNumber ="1003"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6;
		
		TbToolRecoveryRecord toolRecoveryRecord = null;
		
		TbToolRecoveryRecordDao recordDao2 = new TbToolRecoveryRecordDao(em);
		if(!StringUtils.isEmpty(id)) {
			toolRecoveryRecord = recordDao2.findById(Integer.valueOf(id));
		}
		if(toolRecoveryRecord!=null) {
			recordDao2.update(toolRecoveryRecord);
		}else {
			TbToolRecovery toolRecovery = toolRecoveryDao2.findById(toolRecoveryId);
			BigDecimal allNumber = BigDecimal.ZERO;
			try {allNumber = new BigDecimal(number);} catch (Exception e) {}
			BigDecimal totalPrice = allNumber.multiply(new BigDecimal(toolRecovery.getPrice()));
			
			//零差价判断
			if (userEnterpriseIdList.isEmpty()) {
				res.add("status", -1).add("msg", "当前客户没有回收配额");
				return false;
			}
			Object[] recoveryData = null;
			if (!userEnterpriseIdList.isEmpty()) {
				recoveryData = enterpriseUserProductionInfoDao.getRecoveryData(userEnterpriseIdList);
			}
			//购买的零差价农资（包、袋）
			BigDecimal bagUniformPriceNum = BigDecimal.ZERO;
			//购买的零差价农资（瓶）
			BigDecimal bottleUniformPriceNum = BigDecimal.ZERO;
			//回收的零差价农资（包、袋）
			BigDecimal bagUniformPriceRecoveryNum = BigDecimal.ZERO;
			//回收的零差价农资（瓶）
			BigDecimal bottleUniformPriceRecoveryNum = BigDecimal.ZERO;
			//回收的非零差价农资（包、袋）
			BigDecimal bagRecoveryPrice = BigDecimal.ZERO;
			//回收的非零差价农资（瓶）
			BigDecimal bottleRecoveryPrice = BigDecimal.ZERO;
			//回收的农膜
			BigDecimal nmRecoveryPrice = BigDecimal.ZERO;
			//非零差价农药限制
			BigDecimal notUniformPriceTrashLimit = BigDecimal.ZERO;
			//回收农膜限制
			BigDecimal recoveryNmLimit = BigDecimal.ZERO;
			if (recoveryData != null) {
				try {recoveryNmLimit = new BigDecimal(recoveryData[0].toString());} catch (Exception e) {}
				try {notUniformPriceTrashLimit = new BigDecimal(recoveryData[1].toString());} catch (Exception e) {}
				String uniformPriceNum = recoveryData[2].toString();
				String uniformPriceRecoveryNum = recoveryData[3].toString();
				String normalRecoveryPrice = recoveryData[4].toString();
				String[] uniformPriceNumArray = uniformPriceNum.split(",");
				String[] uniformPriceRecoveryNumArray = uniformPriceRecoveryNum.split(",");
				String[] normalRecoveryPriceArray = normalRecoveryPrice.split(",");
				
				try {bottleUniformPriceNum = new BigDecimal(uniformPriceNumArray[0].toString());} catch (Exception e) {}
				try {bagUniformPriceNum = new BigDecimal(uniformPriceNumArray[1].toString());} catch (Exception e) {}
				try {bottleUniformPriceRecoveryNum = new BigDecimal(uniformPriceRecoveryNumArray[0].toString());} catch (Exception e) {}
				try {bagUniformPriceRecoveryNum = new BigDecimal(uniformPriceRecoveryNumArray[1].toString());} catch (Exception e) {}
				try {bottleRecoveryPrice = new BigDecimal(normalRecoveryPriceArray[0].toString());} catch (Exception e) {}
				try {bagRecoveryPrice = new BigDecimal(normalRecoveryPriceArray[1].toString());} catch (Exception e) {}
				try {nmRecoveryPrice = new BigDecimal(normalRecoveryPriceArray[2].toString());} catch (Exception e) {}
				
				
			}
			
			if ( (toolRecoveryId == 4 && bottleUniformPriceNum.subtract(bottleUniformPriceRecoveryNum).subtract(allNumber).compareTo(BigDecimal.ZERO) < 0) ||
					(toolRecoveryId == 5 && bagUniformPriceNum.subtract(bagUniformPriceRecoveryNum).subtract(allNumber).compareTo(BigDecimal.ZERO) < 0) ) {
				res.add("status", -1).add("msg", "当前客户零差价回收数量超过购买数量");
				return false;
			}
			if ( ((toolRecoveryId == 6 || toolRecoveryId == 7) && notUniformPriceTrashLimit.subtract(bagRecoveryPrice).subtract(bottleRecoveryPrice).subtract(totalPrice).compareTo(BigDecimal.ZERO) < 0) ) {
				res.add("status", -1).add("msg", "当前客户非零差价回收配额不足");
				return false;
			}
			if ((toolRecoveryId == 13 && recoveryNmLimit.subtract(nmRecoveryPrice).subtract(totalPrice).compareTo(BigDecimal.ZERO) < 0)) {
				res.add("status", -1).add("msg", "当前客户农膜回收配额不足");
				return false;
			}
			
			toolRecoveryRecord = new TbToolRecoveryRecord();
			toolRecoveryRecord.setEnterpriseId(enterpriseId);
			toolRecoveryRecord.setUseName(useName);
			toolRecoveryRecord.setToolRecoveryId(toolRecoveryId);
			toolRecoveryRecord.setNumber(number);
			toolRecoveryRecord.setInputTime(StringUtils.isEmpty(inputTime)?new Date():DateTimeUtil.formatTime2(inputTime));
			toolRecoveryRecord.setDelFlag(0);
			toolRecoveryRecord.setOperator(operator);
			toolRecoveryRecord.setUseMobile(linkOrderInfo==null?useMobile:linkOrderInfo.getLinkMobile());
			toolRecoveryRecord.setLinkOrderInfoId(linkOrderInfo==null?null:linkOrderInfo.getId());
			toolRecoveryRecord.setToolId(toolId);
			toolRecoveryRecord.setRecordNumber(recordNumber);
			
			//总价
			toolRecoveryRecord.setTotalPrice(totalPrice+"");
			//入库
			allNumber=allNumber.add( new BigDecimal(number)).setScale(2);
			toolRecoveryRecord.setAllNumber(allNumber+"");
			recordDao2.save(toolRecoveryRecord);
			if(enterpriseId!=null) {
				toolRecovery.setEnterpriseId(enterpriseId);
			}
			toolRecovery.setNumber(allNumber+"");
			toolRecoveryDao2.update(toolRecovery);
			
			TbFileDao tfDao = new TbFileDao(em);
			TbResToolRecoveryRecordFileDao trfgDao =new TbResToolRecoveryRecordFileDao(em);
			
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
						TbResToolRecoveryRecordFile trpf=new TbResToolRecoveryRecordFile();
						trpf.setFileId(tfe.getId());
						trpf.setToolRecoveryRecordId(Integer.valueOf(toolRecoveryRecord.getId()));
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
		}
		Map<String, Object> map = recordDao2.findMapById(toolRecoveryRecord.getId());
		
		res.add("status", 1).add("msg", "操作成功").add("result", map);;
		return true;
	}
}
