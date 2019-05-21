package com.kd.nvoap.util;

public enum APIDataTypeEnum {

	string(0,"string"),number(1,"number"),boolea(2,"boolean"),object(3,"object"),array(4,"array"),array_string(5,"array[string]"),
	array_number(6,"array[number]"),array_boolean(7,"array[boolean]"),array_object(8,"array[object]"),
	array_array(9,"array[array]");
	
	private Integer index;
	private String name;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private APIDataTypeEnum(Integer index, String name) {
		this.index = index;
		this.name = name;
	}
	
	public static String getNameByIndex(Integer index) {
		for(APIDataTypeEnum type:APIDataTypeEnum.values()) {
			if(type.getIndex() == index) {
				return type.getName();
			}
		}
		return "";
	}

	
}
