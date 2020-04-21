/**
 * project:InscreenProximityDemo <BR>
 * file name:PrintUtility.java <BR>
 * @author david.dong
 * create:2015��2��20������12:58:17
 * 
 */
package com.atmel.sidekeydemo.utility;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import com.atmel.sidekeydemo.ConstantFactory;
import com.atmel.sidekeydemo.datatype.MutualDelta;
import com.atmel.sidekeydemo.datatype.MutualReference;
import com.atmel.sidekeydemo.datatype.NodeMutualDelta;
import com.atmel.sidekeydemo.datatype.SelfDelta;
import com.atmel.sidekeydemo.datatype.SelfReference;
import com.atmel.sidekeydemo.datatype.SingleEndDelta;
import com.atmel.sidekeydemo.datatype.SingleEndReference;
import com.atmel.sidekeydemo.datatype.XSelfDelta;
import com.atmel.sidekeydemo.datatype.XSelfReference;
import com.atmel.sidekeydemo.datatype.XSingleEndReference;
import com.atmel.sidekeydemo.datatype.YSelfDelta;
import com.atmel.sidekeydemo.datatype.YSelfReference;
import com.atmel.sidekeydemo.device.MxtDevice;
import com.atmel.sidekeydemo.device.MxtObject;

/**
 * project:InscreenProximityDemo <BR>
 * class name:PrintUtility <BR>
 * 
 * @author david.dong create:2015��2��20������12:58:17
 */
public class Print {

	private Context context;
	
	
	/**
	 * constructor of class:Print <BR>
	 * @author david.dong
	 * create:2015��2��20������1:12:29
	 * @param context
	 */
	public Print(Context context) {
		
		this.context = context;
	}

	public boolean printSelfDelta(MxtDevice mxtDevice, SelfDelta selfDelta){
		
		int count =0;
		StringBuffer sb = new StringBuffer();
		String content = "";
		
		ArrayList<XSelfDelta> xSelfDelta = selfDelta.getxSelfRawDelta();
		ArrayList<YSelfDelta> ySelfDelta = selfDelta.getySelfRawDelta();
		
		for(int i=0;i<xSelfDelta.size();i++){
			
				int x = xSelfDelta.get(i).getIndex();
				int delta = xSelfDelta.get(i).getDelta();
				
				String strX = String.valueOf(x);
				
				content ="(X_" + strX + ") " + String.valueOf(delta) + " ";
				sb.append(content);
				count++;
				sb.append("\r\n");
		}
		
		for(int i=0;i<ySelfDelta.size();i++){
			
			int y = ySelfDelta.get(i).getIndex();
			int delta = ySelfDelta.get(i).getDelta();
			
			String strY = String.valueOf(y);
			
			content ="(Y_" + strY + ") " + String.valueOf(delta) + " ";
			sb.append(content);
			count++;
			sb.append("\r\n");
		}	
		
		sb.append(count);
		FileHandler fh = new FileHandler(context);

		fh.save("/self_delta.txt", sb.toString());
		return true;
	}
	
	public boolean printSelfReference(MxtDevice mxtDevice, SelfReference selfReference){
		
		int count =0;
		StringBuffer sb = new StringBuffer();
		String content = "";
		
		ArrayList<XSelfReference> evenXSelfReference = selfReference.getEvenXSelfReference();
		ArrayList<XSelfReference> oddXSelfReference = selfReference.getOddXSelfReference();
		ArrayList<YSelfReference> ySelfReference = selfReference.getySelfReference();
		
		for(int i=0;i<ySelfReference.size();i++){
			
				int y = ySelfReference.get(i).getIndex();
				int ref = ySelfReference.get(i).getReference();
				
				String strY = String.valueOf(y);
				
				content ="(Y_" + strY + ") " + String.valueOf(ref) + " ";
				sb.append(content);
				count++;
				sb.append("\r\n");
		}
		
		for(int i=0;i<evenXSelfReference.size();i++){
			
			int x = evenXSelfReference.get(i).getIndex() * 2;
			int ref = evenXSelfReference.get(i).getReference();
			
			String strX = String.valueOf(x);
			
			content ="(X_" + strX + ") " + String.valueOf(ref) + " ";
			sb.append(content);
			count++;
			sb.append("\r\n");
		}
		
		for(int i=0;i<oddXSelfReference.size();i++){
			
			int x = oddXSelfReference.get(i).getIndex() * 2 + 1;
			int ref = oddXSelfReference.get(i).getReference();
			
			String strX = String.valueOf(x);
			
			content ="(X_" + strX + ") " + String.valueOf(ref) + " ";
			sb.append(content);
			count++;
			sb.append("\r\n");
		}
		
		sb.append(count);
		FileHandler fh = new FileHandler(context);

		fh.save("/self_reference.txt", sb.toString());
		return true;
		
	}
	
	public boolean printSEReference(MxtDevice mxtDevice, SingleEndReference singleEndReference){
		
		int count =0;
		StringBuffer sb = new StringBuffer();
		String content = "";
		
		ArrayList<XSingleEndReference> evenXSEReference = singleEndReference.getEvenXSEReference();
		ArrayList<XSingleEndReference> oddXSEReference = singleEndReference.getOddXSEReference();
		
		
		for(int i=0;i<evenXSEReference.size();i++){
			
				int x = evenXSEReference.get(i).getIndex() * 2;
				int ref = evenXSEReference.get(i).getDelta();
				
				String strX = String.valueOf(x);
				
				content ="(X_" + strX + ") " + String.valueOf(ref) + " ";
				sb.append(content);
				count++;
				sb.append("\r\n");
		}
		
		for(int i=0;i<oddXSEReference.size();i++){
			
			int x = oddXSEReference.get(i).getIndex() * 2 + 1;
			int ref = oddXSEReference.get(i).getDelta();
			
			String strX = String.valueOf(x);
			
			content ="(X_" + strX + ") " + String.valueOf(ref) + " ";
			sb.append(content);
			count++;
			sb.append("\r\n");
	}
		
		
		sb.append(count);
		FileHandler fh = new FileHandler(context);

		fh.save("/se_reference.txt", sb.toString());
		return true;
	}
	
	
	public boolean printSEDelta(MxtDevice mxtDevice, SingleEndDelta singleEndDelta){
		
		int count =0;
		StringBuffer sb = new StringBuffer();
		String content = "";
		
		for(int i=0;i<singleEndDelta.getRawXDelta().size();i++){
			
				int x = singleEndDelta.getRawXDelta().get(i).getIndex();
				
				String strX = String.valueOf(x);
				
				content ="(X_" + strX + ") " + String.valueOf(singleEndDelta.getRawXDelta().get(i).getDelta()) + " ";
				sb.append(content);
				count++;
				sb.append("\r\n");
		}
		
		sb.append(count);
		FileHandler fh = new FileHandler(context);

		fh.save("/se_delta.txt", sb.toString());
		return true;
	}

	public boolean printMutualReference(MxtDevice mxtDevice, MutualReference mutualReference){
		
		Utility utility = new Utility();
		
		int matixXSize = utility.findMatixXOnInfoBlock(ConstantFactory.mxtDevice);
		if(matixXSize == -1){
			
			Log.v("Print message", "printMutualReference() fail, invalid matixX size!");
			return false;
		}
		
		int matixYSize = utility.findMatixYOnInfoBlock(ConstantFactory.mxtDevice);
		if(matixYSize == -1){
			
			Log.v("Print message", "printMutualReference() fail, invalid matixY size!");
			return false;
		}
		
		int count =0;
		StringBuffer sb = new StringBuffer();
		String content = "";
		for(int i=0;i<matixXSize;i++){
			
			for(int j=0;j<matixYSize;j++){
				int x = mutualReference.getMutualReference().get(count).getIndex().x;
				int y = mutualReference.getMutualReference().get(count).getIndex().y;
				int delta = mutualReference.getMutualReference().get(count).getDelta();
				String strX = String.valueOf(x);
				String strY = String.valueOf(y);
				content ="(" + strX + ","+ strY + ") " + String.valueOf(delta) + " ";
				sb.append(content);
				count++;
				sb.append(" ");
			}
			sb.append("\r\n");
		}
		
		sb.append(count);
		FileHandler fh = new FileHandler(context);

		fh.save("/mutual_reference.txt", sb.toString());
		return true;
		
	}
	
	
	public boolean printMutualDelta(MxtDevice mxtDevice, MutualDelta mutualDelta){
		
		Utility utility = new Utility();
		
		int matixXSize = utility.findMatixXOnInfoBlock(ConstantFactory.mxtDevice);
		if(matixXSize == -1){
			
			Log.v("Print message", "printMutualDelta() fail, invalid matixX size!");
			return false;
		}
		
		int matixYSize = utility.findMatixYOnInfoBlock(ConstantFactory.mxtDevice);
		if(matixYSize == -1){
			
			Log.v("Print message", "printMutualDelta() fail, invalid matixY size!");
			return false;
		}
		
		int count =0;
		StringBuffer sb = new StringBuffer();
		String content = "";
		for(int i=0;i<matixXSize;i++){
			
			for(int j=0;j<matixYSize;j++){
				int x = mutualDelta.getRawDelta().get(count).getIndex().x;
				int y = mutualDelta.getRawDelta().get(count).getIndex().y;
				String strX = String.valueOf(x);
				String strY = String.valueOf(y);
				content ="(" + strX + ","+ strY + ") " + String.valueOf(mutualDelta.getRawDelta().get(count).getDelta()) + " ";
				sb.append(content);
				count++;
				sb.append(" ");
			}
			
			sb.append("\r\n");
		}
		
		sb.append(count);
		FileHandler fh = new FileHandler(context);

		fh.save("/mutual_delta.txt", sb.toString());
		return true;
	}
	
	public void printObjectTable(MxtDevice mxtDevice) {

		ArrayList<MxtObject> mxtObjects = mxtDevice.getMxtInfo()
				.getMxtObjects();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mxtObjects.size(); i++) {

			String tmp = "";

			tmp = "type:" + String.valueOf(mxtObjects.get(i).getType()) + "\n"
					+ "start_pos_lsb:"
					+ String.valueOf(mxtObjects.get(i).getStartPosLsb()) + "\n"
					+ "start_pos_msb:"
					+ String.valueOf(mxtObjects.get(i).getStartPosMsb()) + "\n"
					+ "size:" + String.valueOf(mxtObjects.get(i).getSize())
					+ "\n" + "instances:"
					+ String.valueOf(mxtObjects.get(i).getInstances()) + "\n"
					+ "num_report_ids:"
					+ String.valueOf(mxtObjects.get(i).getNumberReport())
					+ "\n";

			sb.append(tmp);
		}

		FileHandler fh = new FileHandler(context);

		fh.save("/object_table.txt", sb.toString());
	}
}
