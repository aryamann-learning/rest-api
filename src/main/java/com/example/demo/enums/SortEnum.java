package com.example.demo.enums;

public enum SortEnum {
	ASC("ASC"), DESC("DESC");
	private final String value;

	private SortEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static SortEnum find(String name) {
		for (SortEnum en : SortEnum.values()) {
			if (en.name().equalsIgnoreCase(name)) {
				return en;
			}

		}
		return null;
	}
}
