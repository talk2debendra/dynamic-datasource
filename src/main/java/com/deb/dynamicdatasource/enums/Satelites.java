package com.deb.dynamicdatasource.enums;

import lombok.Getter;

@Getter
public enum Satelites {
	
	SARAL("Saral"),
	ARAYABHAT("Aryabhat");
	
	private String val;

	private Satelites(String val) {
		this.val = val;
	}
	
	public  static Satelites getSatelite(String val) {
		if(SARAL.val.equalsIgnoreCase(val)) {
			return SARAL;
		}
		if(ARAYABHAT.val.equalsIgnoreCase(val)) {
			return ARAYABHAT;
		}
		return null;
	}
}
