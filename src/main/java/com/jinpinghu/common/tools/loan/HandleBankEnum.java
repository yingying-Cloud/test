package com.jinpinghu.common.tools.loan;

public enum HandleBankEnum {

	yyb("873010","营业部"),dhzh("873020","当湖支行"),jkzh("873030","经开支行"),cqzh("873040","曹桥支行"),xdzh("873050","新埭支行"),
	gczh("873060","广陈支行"),xczh("873070","新仓支行"),zpzh("873080","乍浦支行"),hgzh("873090","黄姑支行"),ldzh("873100","林埭支行"),
	dsgzh("873110","独山港支行"),	kjzh("873120","科技支行"),sgzh("873130","三港支行");

	private String code;
	private String name;
	
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	HandleBankEnum(String code, String name) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.name = name;
	}
	
	public static String getNameByCode(String code) {
		HandleBankEnum[] handleBanks = HandleBankEnum.values();
        for (HandleBankEnum handleBank : handleBanks) {
            if(handleBank.getCode().equals(code)) {
                return handleBank.getName();
            }
        }
        return "";
	}
}
