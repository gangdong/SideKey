package com.atmel.sidekeydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

public class DebugActivity extends Activity {

	
	private DebugView debug_view;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//
		
		setContentView(R.layout.activity_debug);
		
		LinearLayout test_relative = (LinearLayout) this
				.findViewById(R.id.linearlayout_debug);
		
		
		debug_view = new DebugView(this);
		
		test_relative.addView(debug_view);
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(ConstantFactory.nTimer!=null){
			ConstantFactory.nTimer.cancel();
			ConstantFactory.nTimer = null;
		}
		super.onDestroy();
	}
	
	
	
	
}
