package com.jinpinghu.logic.enterpriseLoanApplication;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterpriseLoanApplication;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResLoanApplicationFile;
import com.jinpinghu.db.dao.TbEnterpriseLoanApplicationDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResLoanApplicationFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterpriseLoanApplication.param.AddEnterpriseLoanApplicationParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AddEnterpriseLoanApplicationLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddEnterpriseLoanApplicationParam myParam = (AddEnterpriseLoanApplicationParam)logicParam;
		String id = myParam.getId();
		Integer enterpriseId =StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null: Integer.valueOf(myParam.getEnterpriseId());
		Date endTime =StringUtils.isNullOrEmpty(myParam.getEndTime())?null: DateTimeUtil.formatTime(myParam.getEndTime());
		Date startTime = StringUtils.isNullOrEmpty(myParam.getStartTime())?null: DateTimeUtil.formatTime(myParam.getStartTime());
		String loanAmount = myParam.getLoanAmount();
		String useInstruction = myParam.getUseInstruction();
		String file = myParam.getFile();
		String bankBorrow = myParam.getBankBorrow();
		String businessIncome = myParam.getBusinessIncome();
		String currentAssets = myParam.getCurrentAssets();
		String customerName = myParam.getCustomerName();
		String financialStatement = myParam.getFinancialStatement();
		String fixedAssets = myParam.getFixedAssets();
		String guarantyStyle = myParam.getGuarantyStyle();
		String guarantyUnit = myParam.getGuarantyUnit();
		String handleBank = myParam.getHandleBank();
		String idNumber = myParam.getIdNumber();
		String idType = myParam.getIdType();
		String loanTime = myParam.getLoanTime();
		String ownershipInterest = myParam.getOwnershipInterest();
		String receivables = myParam.getReceivables();
		String totalAssets = myParam.getTotalAssets();
		String totalIndebtedness = myParam.getTotalIndebtedness();
		String totalProfit = myParam.getTotalProfit();

		TbEnterpriseLoanApplicationDao enterpriseLoanApplicationDao = new TbEnterpriseLoanApplicationDao(em);
		TbEnterpriseLoanApplication enterpriseLoanApplication =null;
		/*��֤�Ƿ���ڸù�˾*/
		if(!StringUtils.isNullOrEmpty(id)) {
			enterpriseLoanApplication=enterpriseLoanApplicationDao.findById(Integer.valueOf(id));
		}
			
		if(enterpriseLoanApplication==null) {
			enterpriseLoanApplication= new TbEnterpriseLoanApplication(null, bankBorrow, businessIncome, currentAssets, customerName,
					0, endTime, enterpriseId, financialStatement, fixedAssets, guarantyStyle, guarantyUnit, handleBank, idNumber, idType,
					new Date(), loanAmount, loanTime, ownershipInterest, receivables, null, startTime, 0, null, null, totalAssets, 
					totalIndebtedness, totalProfit, useInstruction);
			enterpriseLoanApplicationDao.save(enterpriseLoanApplication);
		}else {
			enterpriseLoanApplication.setEndTime(endTime);
			enterpriseLoanApplication.setStartTime(startTime);
			enterpriseLoanApplication.setEnterpriseId(enterpriseId);
			enterpriseLoanApplication.setLoanAmount(loanAmount);
			enterpriseLoanApplication.setUseInstruction(useInstruction);
			enterpriseLoanApplication.setBankBorrow(bankBorrow);
			enterpriseLoanApplication.setBusinessIncome(businessIncome);
			enterpriseLoanApplication.setCurrentAssets(currentAssets);
			enterpriseLoanApplication.setCustomerName(customerName);
			enterpriseLoanApplication.setFinancialStatement(financialStatement);
			enterpriseLoanApplication.setFixedAssets(fixedAssets);
			enterpriseLoanApplication.setGuarantyStyle(guarantyStyle);
			enterpriseLoanApplication.setGuarantyUnit(guarantyUnit);
			enterpriseLoanApplication.setHandleBank(handleBank);
			enterpriseLoanApplication.setIdNumber(idNumber);
			enterpriseLoanApplication.setIdType(idType);
			enterpriseLoanApplication.setLoanTime(loanTime);
			enterpriseLoanApplication.setOwnershipInterest(ownershipInterest);
			enterpriseLoanApplication.setReceivables(receivables);
			enterpriseLoanApplication.setTotalAssets(totalAssets);
			enterpriseLoanApplication.setTotalIndebtedness(totalIndebtedness);
			enterpriseLoanApplication.setTotalProfit(totalProfit);
			enterpriseLoanApplicationDao.update(enterpriseLoanApplication);
		}

		TbFileDao tfDao = new TbFileDao(em);
		TbResLoanApplicationFileDao trfgDao =new TbResLoanApplicationFileDao(em);
		
		List<TbFile> tfs =tfDao.findByEnterpriseLoanApplicationId(enterpriseLoanApplication.getId());
		List<TbResLoanApplicationFile> trgfs =trfgDao.findByEnterpriseLoanApplicationId(enterpriseLoanApplication.getId());
		if(trgfs!=null){
			for(TbResLoanApplicationFile trgf:trgfs){
				trfgDao.delete(trgf);
			}
		}
		if(tfs!=null){
			for(TbFile tbFile:tfs){
				tfDao.delete(tbFile);
			}
		}
		
		if(!StringUtils.isNullOrEmpty(file)){
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
					TbResLoanApplicationFile trpf=new TbResLoanApplicationFile();
					trpf.setFileId(tfe.getId());
					trpf.setLoanApplicationId(enterpriseLoanApplication.getId());
					trpf.setDelFlag(0);
					trfgDao.save(trpf);
				}
			}
		}
		
		
		res.add("status", 1).add("msg","操作成功");
		return true;
	}
}
