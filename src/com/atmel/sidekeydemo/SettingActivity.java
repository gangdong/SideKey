/**
 * project:InscreenProximityDemo <BR>
 * file name:SettingActivity.java <BR>
 * @author david.dong
 * create:2015年4月1日上午11:42:04
 * 
 */
package com.atmel.sidekeydemo;

import com.atmel.sidekeydemo.service.T38Handler;
import com.atmel.sidekeydemo.service.T6Handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * project:InscreenProximityDemo <BR>
 * class name:SettingActivity <BR>
 * @author david.dong
 * create:2015年4月1日上午11:42:04
 */
public class SettingActivity extends Activity {

	
	private EditText etPara1;
	private EditText etPara2;
	private EditText etPara3;
	private EditText etPara4;
	private Button btnWrite;
	private Button btnDbg;
	int[] intValues = new int[21];
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		
		etPara1 = (EditText) this.findViewById(R.id.editTextPara1Set);
		etPara2 = (EditText) this.findViewById(R.id.editTextPara2Set);
		etPara3 = (EditText) this.findViewById(R.id.editTextPara3Set);
		etPara4 = (EditText) this.findViewById(R.id.editTextPara4Set);
		
		btnWrite = (Button) this.findViewById(R.id.buttonWriteSetting);
		
		btnDbg = (Button) this.findViewById(R.id.buttonDebug);
	
		
		T38Handler t38Handler = new T38Handler(ConstantFactory.mxtDevice);
		
		byte[] tmpValues = t38Handler.t38ReadValues(0, 8);
		
		for(int i=0;i<4;i++){
			
			int lsb = tmpValues[2*i] & 0xff;
			int msb = tmpValues[2*i+1] & 0xff;
			intValues[i] = lsb |( msb << 8);
		}
		
		
		
		etPara1.setText(String.valueOf(intValues[0]));
		etPara2.setText(String.valueOf(intValues[1]));
		etPara3.setText(String.valueOf(intValues[2]));
		etPara4.setText(String.valueOf(intValues[3]));
			
		
		WriteBtnOnClickListener writeBtnOnClickListener = new WriteBtnOnClickListener(this);
	
		this.btnWrite.setOnClickListener(writeBtnOnClickListener);
		
	
		this.btnDbg.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingActivity.this, DebugActivity.class);
				startActivity(intent);
			}
			
		});
		
	}
}

class WriteBtnOnClickListener implements Button.OnClickListener{

	private Activity act;
	
	
	/**
	 * constructor of class:WriteBtnOnClickListener <BR>
	 * @author david.dong
	 * create:2015年4月1日下午2:52:55
	 * @param act
	 */
	public WriteBtnOnClickListener(Activity act) {
		this.act = act;
	}


	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditText etPara1 = (EditText) this.act.findViewById(R.id.editTextPara1Set);
		EditText etPara2 = (EditText) this.act.findViewById(R.id.editTextPara2Set);
		EditText etPara3 = (EditText) this.act.findViewById(R.id.editTextPara3Set);
		EditText etPara4 = (EditText) this.act.findViewById(R.id.editTextPara4Set);
		
		
		byte[] tmpBytes = new byte[8];
		
		int intPara1 = Integer.parseInt(etPara1.getText().toString());
		int intPara2 = Integer.parseInt(etPara2.getText().toString());
		int intPara3 = Integer.parseInt(etPara3.getText().toString());
		int intPara4 = Integer.parseInt(etPara4.getText().toString());
		

		tmpBytes[0] = (byte)(intPara1 & 0xff);
		tmpBytes[1] = (byte)((intPara1 >> 8) & 0xff);
		
		tmpBytes[2] = (byte)(intPara2 & 0xff);
		tmpBytes[3] = (byte)((intPara2 >> 8) & 0xff);
		
		tmpBytes[4] = (byte)(intPara3 & 0xff);
		tmpBytes[5] = (byte)((intPara3 >> 8) & 0xff);
		
		tmpBytes[6] = (byte)(intPara4 & 0xff);
		tmpBytes[7] = (byte)((intPara4 >> 8) & 0xff);
		
		
		T38Handler t38Handler = new T38Handler(ConstantFactory.mxtDevice);
		
		
		t38Handler.t38WriteValues(tmpBytes, 0);
		
		
		T6Handler t6Handler = new T6Handler(ConstantFactory.mxtDevice);
		
		t6Handler.t6Backup();
	}
	
	
}