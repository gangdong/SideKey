package com.atmel.sidekeydemo.device;

public class MxtConnType {

	private String connType;
	private int refConunt;
	
	
	
	
	public MxtConnType() {
		this.connType = "1";
		this.refConunt = 255;
	}
	public String getConnType() {
		return connType;
	}
	public void setConnType(String connType) {
		this.connType = connType;
	}
	public int getRefConunt() {
		return refConunt;
	}
	public void setRefConunt(int refConunt) {
		this.refConunt = refConunt;
	}
	
	
}
