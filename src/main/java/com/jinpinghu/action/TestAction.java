package com.jinpinghu.action;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.jinpinghu.common.tools.loan.LoanApplyReplyRequestBody;
import com.jinpinghu.common.tools.loan.LoanApplyReplyResponse;
import com.jinpinghu.common.tools.loan.LoanApplyRequestBody;
import com.jinpinghu.common.tools.loan.LoanApplyResponse;
import com.jinpinghu.common.tools.loan.LoanUtil;
import com.jinpinghu.logic.test.UploadPicToOssLogic;

@Path("test")
@Produces("application/json;charset=UTF-8")
public class TestAction extends BaseZAction{

	@POST
	@Path("test")
	public String test() {
		return new UploadPicToOssLogic().process(null);
	}
	
	@POST
	@Path("loanApply.do")
	public LoanApplyResponse loanApply(
			@FormParam("PTNAME") String PTNAME,
			@FormParam("PTNUM") String PTNUM,
			@FormParam("CUSTOMER_NAME") String CUSTOMER_NAME,
			@FormParam("ID_TYPE") String ID_TYPE,
			@FormParam("ID_NUMBER") String ID_NUMBER,
			@FormParam("LOAN_AMOUNT") String LOAN_AMOUNT,
			@FormParam("LOAN_TIME") String LOAN_TIME,
			@FormParam("START_TIME") String START_TIME,
			@FormParam("END_TIME") String END_TIME,
			@FormParam("LOAN_USE") String LOAN_USE,
			@FormParam("GUARANTY_STYLE") String GUARANTY_STYLE,
			@FormParam("GUARANTY_UNIT") String GUARANTY_UNIT,
			@FormParam("HANDLE_BANK") String HANDLE_BANK,
			@FormParam("FINANCIAL_STATEMENT") String FINANCIAL_STATEMENT,
			@FormParam("CURRENT_ASSETS") String CURRENT_ASSETS,
			@FormParam("RECEIVABLES") String RECEIVABLES,
			@FormParam("FIXED_ASSETS") String FIXED_ASSETS,
			@FormParam("TOTAL_ASSETS") String TOTAL_ASSETS,
			@FormParam("BANK_BORROW") String BANK_BORROW,
			@FormParam("TOTAL_INDEBTEDNESS") String TOTAL_INDEBTEDNESS,
			@FormParam("OWNERSHIP_INTEREST") String OWNERSHIP_INTEREST,
			@FormParam("BUSINESS_INCOME") String BUSINESS_INCOME,
			@FormParam("TOTAL_PROFIT") String TOTAL_PROFIT,
			@FormParam("EXT1") String EXT1,
			@FormParam("EXT2") String EXT2,
			@FormParam("EXT3") String EXT3) {
		//提交申请
		LoanApplyRequestBody loanApplyBody = new LoanApplyRequestBody(PTNAME, PTNUM, CUSTOMER_NAME, ID_TYPE, ID_NUMBER,
				LOAN_AMOUNT, LOAN_TIME, START_TIME, END_TIME, LOAN_USE, GUARANTY_STYLE, GUARANTY_UNIT, HANDLE_BANK, FINANCIAL_STATEMENT,
				CURRENT_ASSETS, RECEIVABLES, FIXED_ASSETS, TOTAL_ASSETS, BANK_BORROW, TOTAL_INDEBTEDNESS, OWNERSHIP_INTEREST, BUSINESS_INCOME,
				TOTAL_PROFIT, EXT1, EXT2, EXT3);
		return LoanUtil.commitLoanApply(loanApplyBody);
	}
	
	@POST
	@Path("loanApplyReply.do")
	public LoanApplyReplyResponse loanApplyReply(
			@FormParam("PTNAME") String PTNAME,
			@FormParam("PTNUM") String PTNUM,
			@FormParam("applyCode") String applyCode,
			@FormParam("ID_TYPE") String ID_TYPE,
			@FormParam("ID_NUMBER") String ID_NUMBER) {
		//贷款申请回复
		LoanApplyReplyRequestBody loanApplyReplybody = new LoanApplyReplyRequestBody(PTNAME, PTNUM, ID_TYPE, ID_NUMBER, applyCode);
		return LoanUtil.loanApplyReply(loanApplyReplybody);
	}
}
