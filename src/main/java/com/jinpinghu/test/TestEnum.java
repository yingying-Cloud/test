package com.jinpinghu.test;

public class TestEnum {
	
	public static void main(String[] args) {
		System.out.println(Enum.valueOf(PlantWayEnum.class, "水培").name());
	}
	
	enum PlantWayEnum{
	    水培("HYDROPONICS"),气雾培("AEROPONICS"),沙培("SANDCULTURE"), 大田("FIELD");

	    PlantWayEnum(String name) {
	    }
	}

}
