package com.jinpinghu.logic.sellBrand;

public enum SellBrandType {

	plant(1,"种植"),breed(2,"养殖"),other(3,"其他");
	
	private int type;
	private String value;
	
	private SellBrandType(int type,String value) {
		this.setType(type);
		this.setValue(value);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static String getValue(int type) {
		for (SellBrandType sellBrandType : SellBrandType.values()) {
			if(type == sellBrandType.type)
				return sellBrandType.value;
		}
		return null;
	}
}
