package com.atmel.sidekeydemo.device;

public class MxtDevice {

	private MxtConnType mxtConnType;
	private MxtInfo mxtInfo;
	
	
	
	
	public MxtDevice() {
		this.mxtConnType = null;
		this.mxtInfo = null;
	}
	public MxtConnType getMxtConnType() {
		return mxtConnType;
	}
	public void setMxtConnType(MxtConnType mxtConnType) {
		this.mxtConnType = mxtConnType;
	}
	public MxtInfo getMxtInfo() {
		return mxtInfo;
	}
	public void setMxtInfo(MxtInfo mxtInfo) {
		this.mxtInfo = mxtInfo;
	}
	
	
	
	
}
