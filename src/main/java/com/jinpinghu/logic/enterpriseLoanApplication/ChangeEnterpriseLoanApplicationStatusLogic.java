package com.jinpinghu.logic.enterpriseLoanApplication;

import com.google.gson.Gson;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.loan.LoanApplyRequestBody;
import com.jinpinghu.common.tools.loan.LoanApplyResponse;
import com.jinpinghu.common.tools.loan.LoanUtil;
import com.jinpinghu.db.bean.TbEnterpriseLoanApplication;
import com.jinpinghu.db.bean.TbEnterpriseLoanApplicationRecord;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseLoanApplicationDao;
import com.jinpinghu.db.dao.TbEnterpriseLoanApplicationRecordDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterpriseLoanApplication.param.ChangeEnterpriseLoanApplicationStatusParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

public class ChangeEnterpriseLoanApplicationStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ChangeEnterpriseLoanApplicationStatusParam myParam = (ChangeEnterpriseLoanApplicationStatusParam)logicParam;
		String ids = myParam.getIds();
		String statusStr=myParam.getStatus();
		String remark = myParam.getRemark();
		String userId = myParam.getUserId();
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser user = tbUserDao.checkLogin2(userId);
		TbEnterpriseLoanApplicationRecordDao recordDao = new TbEnterpriseLoanApplicationRecordDao(em);
		TbEnterpriseLoanApplicationDao enterpriseDao = new TbEnterpriseLoanApplicationDao(em);
		
		Gson gson = new Gson();
		/*��֤�Ƿ���ڸù�˾*/
		if(!StringUtils.isNullOrEmpty(ids)&&!StringUtils.isNullOrEmpty(statusStr)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbEnterpriseLoanApplication enterprise = enterpriseDao.findById(Integer.valueOf(id));
				if(enterprise!=null) {
					Integer status = Integer.valueOf(statusStr);
					enterprise.setStatus(status);
					if (status == 1) {
						LoanApplyRequestBody loanApplyRequestBody = new LoanApplyRequestBody(LoanUtil.platname, LoanUtil.platnum, enterprise.getCustomerName(),
								enterprise.getIdType(), enterprise.getIdNumber(),StringUtils.isNullOrEmpty(enterprise.getLoanAmount()) ? "0" : new BigDecimal(enterprise.getLoanAmount()).multiply(new BigDecimal("10000")).toPlainString(),
								enterprise.getLoanTime(), DateTimeUtil.formatTime(enterprise.getStartTime()),DateTimeUtil.formatTime(enterprise.getEndTime()), enterprise.getUseInstruction(), 
								enterprise.getGuarantyStyle(), enterprise.getGuarantyUnit(),enterprise.getHandleBank(),enterprise.getFinancialStatement(),
								enterprise.getCurrentAssets(), enterprise.getReceivables(), enterprise.getFixedAssets(), 
								enterprise.getTotalAssets(), enterprise.getBankBorrow(), enterprise.getTotalIndebtedness(),enterprise.getOwnershipInterest(), 
								enterprise.getBusinessIncome(), enterprise.getTotalProfit(), null, null, null);
						LoanApplyResponse loanApplyResponse = LoanUtil.commitLoanApply(loanApplyRequestBody);
						if (loanApplyResponse != null) {
							try {
								enterprise.setApplyCode(loanApplyResponse.getBody().getAPPLY_CODE());
							}catch (Exception e) {
								// TODO: handle exception
								res.add("status", -1);
								res.add("msg", enterprise.getCustomerName()+"申请信贷失败，失败信息："+gson.toJson(loanApplyResponse));
								log().error(enterprise.getCustomerName()+"申请信贷失败，失败信息："+gson.toJson(loanApplyResponse));
								return false;
							}
							
						}else {
							res.add("status", -1);
							res.add("msg", enterprise.getCustomerName()+"申请信贷失败，失败信息："+gson.toJson(loanApplyResponse));
							log().error(enterprise.getCustomerName()+"申请信贷失败，失败信息："+gson.toJson(loanApplyResponse));
							return false;
						}
					}
					enterpriseDao.update(enterprise);
					TbEnterpriseLoanApplicationRecord record = new TbEnterpriseLoanApplicationRecord();
					record.setDelFlag(0);
					record.setInputTime(new Date());
					record.setLoanApplicationId(enterprise.getId());
					record.setName(user==null?"":user.getName());
					record.setRemark(remark);
					record.setStatus(Integer.valueOf(status));
					recordDao.save(record);
				}
			}
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}
}
