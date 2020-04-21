package com.atmel.sidekeydemo.device;

import java.util.ArrayList;

public class MxtInfo {

	private MxtIdInfo mxtIdInfo;
	private ArrayList<MxtObject> mxtObjects;
	private int crc;
	
	
	
	
	
	public MxtInfo() {
		this.mxtIdInfo = null;
		this.mxtObjects = new ArrayList<MxtObject>();
		this.crc = 255;
	}
	
	
	public MxtIdInfo getMxtIdInfo() {
		return mxtIdInfo;
	}
	public void setMxtIdInfo(MxtIdInfo mxtIdInfo) {
		this.mxtIdInfo = mxtIdInfo;
	}

	public void addMxtObject(MxtObject mxtObject){
		
		this.mxtObjects.add(mxtObject);
	}

	/**
	 * @author david.dong
	 * @return the mxtObjects
	 */
	public ArrayList<MxtObject> getMxtObjects() {
		return mxtObjects;
	}


	/**
	 * @author david.dong
	 * @param mxtObjects the mxtObjects to set
	 */
	public void setMxtObjects(ArrayList<MxtObject> mxtObjects) {
		this.mxtObjects = mxtObjects;
	}


	public int getCrc() {
		return crc;
	}


	public void setCrc(int crc) {
		this.crc = crc;
	}

	
	
	
	
}
