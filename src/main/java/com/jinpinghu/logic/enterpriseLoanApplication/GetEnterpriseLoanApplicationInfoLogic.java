package com.jinpinghu.logic.enterpriseLoanApplication;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.loan.LoanApplyReplyRequestBody;
import com.jinpinghu.common.tools.loan.LoanApplyReplyResponse;
import com.jinpinghu.common.tools.loan.LoanUtil;
import com.jinpinghu.db.bean.TbEnterpriseLoanApplication;
import com.jinpinghu.db.bean.TbEnterpriseLoanApplicationRecord;
import com.jinpinghu.db.dao.TbEnterpriseLoanApplicationDao;
import com.jinpinghu.db.dao.TbEnterpriseLoanApplicationRecordDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterpriseLoanApplication.param.GetEnterpriseLoanApplicationInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class GetEnterpriseLoanApplicationInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseLoanApplicationInfoParam myParam = (GetEnterpriseLoanApplicationInfoParam)logicParam;
		Integer id =StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbEnterpriseLoanApplicationDao enterpriseLoanApplicationDao = new TbEnterpriseLoanApplicationDao(em);
		TbEnterpriseLoanApplication enterpriseLoanApplication = null;
		TbEnterpriseLoanApplicationRecordDao recordDao = new TbEnterpriseLoanApplicationRecordDao(em);
		if(id!=null) {
			enterpriseLoanApplication = enterpriseLoanApplicationDao.findById(id);
		}
		TbFileDao tfDao = new TbFileDao(em);
		JSONObject jo = new JSONObject();
		if(enterpriseLoanApplication!=null) {
			jo.put("endTime",enterpriseLoanApplication.getEndTime()==null?null:DateTimeUtil.formatTime(enterpriseLoanApplication.getEndTime()));
			jo.put("startTime",enterpriseLoanApplication.getStartTime()==null?null:DateTimeUtil.formatTime(enterpriseLoanApplication.getStartTime()));
			jo.put("loanAmount",enterpriseLoanApplication.getLoanAmount());
			jo.put("status",enterpriseLoanApplication.getStatus());
			jo.put("useInstruction",enterpriseLoanApplication.getUseInstruction());
			jo.put("id",enterpriseLoanApplication.getId());
			jo.put("bankBorrow",StringUtils.trimToEmpty(enterpriseLoanApplication.getBankBorrow()));
			jo.put("businessIncome",StringUtils.trimToEmpty(enterpriseLoanApplication.getBusinessIncome()));
			jo.put("currentAssets",StringUtils.trimToEmpty(enterpriseLoanApplication.getCurrentAssets()));
			jo.put("customerName",StringUtils.trimToEmpty(enterpriseLoanApplication.getCustomerName()));
			jo.put("financialStatement",StringUtils.trimToEmpty(enterpriseLoanApplication.getFinancialStatement()));
			jo.put("fixedAssets",StringUtils.trimToEmpty(enterpriseLoanApplication.getFixedAssets()));
			jo.put("guarantyStyle",StringUtils.trimToEmpty(enterpriseLoanApplication.getGuarantyStyle()));
			jo.put("guarantyUnit",StringUtils.trimToEmpty(enterpriseLoanApplication.getGuarantyUnit()));
			jo.put("handleBank",StringUtils.trimToEmpty(enterpriseLoanApplication.getHandleBank()));
			jo.put("idNumber",StringUtils.trimToEmpty(enterpriseLoanApplication.getIdNumber()));
			jo.put("idType",StringUtils.trimToEmpty(enterpriseLoanApplication.getIdType()));
			jo.put("loanTime",StringUtils.trimToEmpty(enterpriseLoanApplication.getLoanTime()));
			jo.put("ownershipInterest",StringUtils.trimToEmpty(enterpriseLoanApplication.getOwnershipInterest()));
			jo.put("receivables",StringUtils.trimToEmpty(enterpriseLoanApplication.getReceivables()));
			jo.put("totalAssets",StringUtils.trimToEmpty(enterpriseLoanApplication.getTotalAssets()));
			jo.put("totalIndebtedness",StringUtils.trimToEmpty(enterpriseLoanApplication.getTotalIndebtedness()));
			jo.put("totalProfit",StringUtils.trimToEmpty(enterpriseLoanApplication.getTotalProfit()));
			if(StringUtils.isEmpty(enterpriseLoanApplication.getTlrName()) && StringUtils.isEmpty(enterpriseLoanApplication.getRemark())
					&& StringUtils.isEmpty(enterpriseLoanApplication.getTlrTel())) {
				if(!StringUtils.isEmpty(enterpriseLoanApplication.getApplyCode())) {
					LoanApplyReplyRequestBody loanApplyReplyRequestBody = new LoanApplyReplyRequestBody(LoanUtil.platname, LoanUtil.platnum, 
							enterpriseLoanApplication.getIdType(), enterpriseLoanApplication.getIdNumber(), enterpriseLoanApplication.getApplyCode());
					LoanApplyReplyResponse loanApplyReplyResponse = LoanUtil.loanApplyReply(loanApplyReplyRequestBody);
					if (loanApplyReplyResponse != null) {
						TbEnterpriseLoanApplicationRecord record = new TbEnterpriseLoanApplicationRecord();
						if("0".equals(loanApplyReplyResponse.getBody().getRESULT())) {
							enterpriseLoanApplication.setStatus(4);
							record.setStatus(4);
						}else if ("1".equals(loanApplyReplyResponse.getBody().getRESULT())) {
							enterpriseLoanApplication.setStatus(3);
							record.setStatus(3);
						}
						enterpriseLoanApplication.setTlrName(loanApplyReplyResponse.getBody().getTLR_NAME());
						enterpriseLoanApplication.setRemark(loanApplyReplyResponse.getBody().getREMARK());
						enterpriseLoanApplication.setTlrTel(loanApplyReplyResponse.getBody().getTLR_TEL());
						
						enterpriseLoanApplicationDao.update(enterpriseLoanApplication);
						
						if (record.getStatus() != null) {
							record.setDelFlag(0);
							record.setInputTime(new Date());
							record.setLoanApplicationId(enterpriseLoanApplication.getId());
							record.setName(loanApplyReplyResponse.getBody().getTLR_NAME());
							record.setRemark(loanApplyReplyResponse.getBody().getREMARK());
							enterpriseLoanApplicationDao.save(record);
						}
					}
				}
			}
			jo.put("tlrName",StringUtils.trimToEmpty(enterpriseLoanApplication.getTlrName()));
			jo.put("tlrTel",StringUtils.trimToEmpty(enterpriseLoanApplication.getTlrTel()));
			jo.put("remark",StringUtils.trimToEmpty(enterpriseLoanApplication.getRemark()));
			List<Map<String, Object>> tfs =tfDao.getLoanApplicationListById(enterpriseLoanApplication.getId());
			jo.put("file", tfs);
			List<Map<String, Object>> record =recordDao.getLoanApplicationRecordListById(enterpriseLoanApplication.getId());
			jo.put("record", record);
		}
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", jo);
		return true;
	}
}
