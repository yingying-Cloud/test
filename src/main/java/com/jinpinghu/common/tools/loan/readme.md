## 方法说明
### LoanUtil类里面有两个方法：
#### 1. commitLoanApply   提交贷款申请
输入参数说明：
PTNAME	平台名称
PTNUM	平台编号
CUSTOMER_NAME	客户名称
ID_TYPE	证件类型
ID_NUMBER	证件号
LOAN_AMOUNT	贷款金额
LOAN_TIME	贷款期限
START_TIME	起始日期
END_TIME	到期日期
LOAN_USE	贷款用途
GUARANTY_STYLE	担保方式
GUARANTY_UNIT	担保单位
HANDLE_BANK	经办支行
FINANCIAL_STATEMENT	财务报表期数
CURRENT_ASSETS	流动资产
RECEIVABLES	应收账款
FIXED_ASSETS	固定资产净额
TOTAL_ASSETS	资产总额
BANK_BORROW	银行借款
TOTAL_INDEBTEDNESS	负债总额
OWNERSHIP_INTEREST	所有者权益
BUSINESS_INCOME	主营业务收入
TOTAL_PROFIT	利润总额
EXT1	备用字段
EXT2	备用字段
EXT3	备用字段

输出参数说明：
APPLY_CODE	申请编号

#### 2. loanApplyReply        贷款申请回复
输入参数说明：
PTNAME	平台名称
PTNUM	平台编号
ID_TYPE	证件类型
ID_NUMBER	证件号
APPLY_CODE	申请编号
输出参数说明：
CUSTOMER_NAME	客户名称
ID_TYPE	证件类型
ID_NUMBER	证件号
LOAN_AMOUNT	贷款金额
LOAN_TIME	贷款期限
RESULT	是否同意
REMARK	备注项
TLR_NAME	客户经理姓名
TLR_TEL	客户经理联系方式
