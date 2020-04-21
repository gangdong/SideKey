package com.atmel.sidekeydemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import com.atmel.sidekeydemo.datatype.SelfDelta;
import com.atmel.sidekeydemo.datatype.XHoverDelta;
import com.atmel.sidekeydemo.datatype.XSelfDelta;
import com.atmel.sidekeydemo.datatype.YHoverDelta;
import com.atmel.sidekeydemo.service.T113Handler;
import com.atmel.sidekeydemo.service.T37Handler;
import com.atmel.sidekeydemo.service.T38Handler;
import com.atmel.sidekeydemo.service.T6Handler;
import com.atmel.sidekeydemo.utility.LightnessControl;
import com.atmel.sidekeydemo.utility.Print;
import com.atmel.sidekeydemo.utility.Utility;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;
import android.provider.MediaStore;

public class MainActivity extends ActionBarActivity {

	private Button btnProximity;
	private Button btnSetting;

	private Button btnAbout;

	private MaxtouchJni maxtouchJni = new MaxtouchJni();

	static {
		System.loadLibrary("MaxtouchJni");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (ConstantFactory.nTimer != null) {
			ConstantFactory.nTimer.cancel();
			ConstantFactory.nTimer = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		btnProximity = (Button) this.findViewById(R.id.button3);
		btnAbout = (Button) this.findViewById(R.id.button4);
		btnSetting = (Button) this.findViewById(R.id.button2);

		boolean isConnect = maxtouchJni.Scan();
		if (isConnect) {

			int ret = maxtouchJni.GetInfoDebug();

			String strPath = maxtouchJni.GetSysfsPath();

			boolean isNewDevice = maxtouchJni.GetInfo();
			if (isNewDevice) {
				String rst = maxtouchJni
						.loadMxtDevice(ConstantFactory.mxtDevice);
				if (rst.equals("success")) {

					String strFamilyId = String
							.valueOf(ConstantFactory.mxtDevice.getMxtInfo()
									.getMxtIdInfo().getFamilyId());
					String strVariant = String
							.valueOf(ConstantFactory.mxtDevice.getMxtInfo()
									.getMxtIdInfo().getVariantId());
					String strVersion = String
							.valueOf(ConstantFactory.mxtDevice.getMxtInfo()
									.getMxtIdInfo().getVersion());
					String strBuild = String.valueOf(ConstantFactory.mxtDevice
							.getMxtInfo().getMxtIdInfo().getBuild());
					String strXSize = String.valueOf(ConstantFactory.mxtDevice
							.getMxtInfo().getMxtIdInfo().getMatrixXSize());
					String strYSize = String.valueOf(ConstantFactory.mxtDevice
							.getMxtInfo().getMxtIdInfo().getMatrixYSize());
					String strObjects = String
							.valueOf(ConstantFactory.mxtDevice.getMxtInfo()
									.getMxtIdInfo().getNumberObjects());
					String strChecksum = String
							.valueOf(ConstantFactory.mxtDevice.getMxtInfo()
									.getCrc());

					/*
					 * String strMxtDeviceInfo = "family:" +
					 * String.valueOf(ConstantFactory.mxtDevice
					 * .getMxtInfo().getMxtIdInfo().getFamilyId()) + "\n" +
					 * "variant:" + String.valueOf(ConstantFactory.mxtDevice
					 * .getMxtInfo().getMxtIdInfo().getVariantId()) + "\n" +
					 * "version" + String.valueOf(ConstantFactory.mxtDevice
					 * .getMxtInfo().getMxtIdInfo().getVersion()) + "\n" +
					 * "build" + String.valueOf(ConstantFactory.mxtDevice
					 * .getMxtInfo().getMxtIdInfo().getBuild()) + "\n" +
					 * "max x size:" + String.valueOf(ConstantFactory.mxtDevice
					 * .getMxtInfo().getMxtIdInfo() .getMatrixXSize()) + "\n" +
					 * "max y size" + String.valueOf(ConstantFactory.mxtDevice
					 * .getMxtInfo().getMxtIdInfo() .getMatrixYSize()) + "\n" +
					 * "objects:" + String.valueOf(ConstantFactory.mxtDevice
					 * .getMxtInfo().getMxtIdInfo() .getNumberObjects()) + "\n"
					 * + "info block crc:" +
					 * String.valueOf(ConstantFactory.mxtDevice
					 * .getMxtInfo().getCrc()) + "\n";
					 * tv.setText(strMxtDeviceInfo);
					 */
				}
			} else {
				Log.v("mxt status", "get info fail!");
			}
		} else {
			Log.v("mxt status", "probe device fail!");
		}

		
		T37Handler t37Handler = new
				 T37Handler(ConstantFactory.mxtDevice);
		
		long startTime = System.nanoTime();
		t37Handler.readMutualDelta(ConstantFactory.mutualDelta);
		long consumingTime = System.nanoTime() - startTime;
		Log.v("execution time",
				"test read t37 response   "
						+ String.valueOf(consumingTime / 1000)
						+ "us");
		/*
		 * Print print = new Print(this); T37Handler t37Handler = new
		 * T37Handler(ConstantFactory.mxtDevice);
		 * 
		 * 
		 * // print object table to file
		 * print.printObjectTable(ConstantFactory.mxtDevice);
		 * 
		 * // print mutual reference to file
		 * t37Handler.readMutualReference(ConstantFactory.mutualReference);
		 * print.printMutualReference(ConstantFactory.mxtDevice,ConstantFactory.
		 * mutualReference);
		 * 
		 * // print self reference to file
		 * t37Handler.readSelfReference(ConstantFactory.selfReference);
		 * print.printSelfReference
		 * (ConstantFactory.mxtDevice,ConstantFactory.selfReference);
		 * 
		 * // print self delta to file
		 * t37Handler.readSelfDelta(ConstantFactory.selfDelta);
		 * print.printSelfDelta
		 * (ConstantFactory.mxtDevice,ConstantFactory.selfDelta);
		 * 
		 * // print mutual delta to file
		 * t37Handler.readMutualDelta(ConstantFactory.mutualDelta);
		 * print.printMutualDelta
		 * (ConstantFactory.mxtDevice,ConstantFactory.mutualDelta);
		 * 
		 * // print single-end reference to file
		 * t37Handler.readSEReference(ConstantFactory.singleEndReference);
		 * print.printSEReference(ConstantFactory.mxtDevice,
		 * ConstantFactory.singleEndReference);
		 * 
		 * // print single-end delta to file long startTime = System.nanoTime();
		 * t37Handler.readSEDelta(ConstantFactory.singleEndDelta); long
		 * consumingTime = System.nanoTime() - startTime; Log.v("Consume time",
		 * String.valueOf(consumingTime));
		 * print.printSEDelta(ConstantFactory.mxtDevice,
		 * ConstantFactory.singleEndDelta);
		 * 
		 * 
		 * String strPath = maxtouchJni.GetSysfsDirectory();
		 */
		T38Handler t38Handler = new T38Handler(ConstantFactory.mxtDevice);

		byte[] t38Register = t38Handler.t38ReadValues(0, 8);

		int[] intValues = new int[4];

		for (int i = 0; i < 4; i++) {

			int lsb = t38Register[2 * i] & 0xff;
			int msb = t38Register[2 * i + 1] & 0xff;
			intValues[i] = lsb | (msb << 8);
		}

		byte[] tmpBytes = new byte[8];
		if ((intValues[0] == 0) && (intValues[1] == 0) && (intValues[2] == 0)
				&& (intValues[3] == 0)) {

			tmpBytes[0] = (byte) (20 & 0xff);
			tmpBytes[1] = (byte) ((20 >> 8) & 0xff);

			tmpBytes[2] = (byte) (100 & 0xff);
			tmpBytes[3] = (byte) ((100 >> 8) & 0xff);

			tmpBytes[4] = (byte) (4 & 0xff);
			tmpBytes[5] = (byte) ((4 >> 8) & 0xff);

			tmpBytes[6] = (byte) (30 & 0xff);
			tmpBytes[7] = (byte) ((30 >> 8) & 0xff);

			t38Handler.t38WriteValues(tmpBytes, 0);
		}

		LCBtnOnClickListener lcBtnOnClickListener = new LCBtnOnClickListener(
				this);
		this.btnProximity.setOnClickListener(lcBtnOnClickListener);

		this.btnSetting.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MainActivity.this,
						SettingActivity.class);
				startActivity(intent);
				// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			}
		});

		this.btnAbout.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						AboutActivity.class);
				startActivity(intent);
			}
		});

		if (savedInstanceState == null) {
			// getSupportFragmentManager().beginTransaction()
			// .add(R.id.container, new PlaceholderFragment())
			// .commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

class LCBtnOnClickListener implements Button.OnClickListener {

	private byte gain = 0;

	private int textSize = 10;

	private boolean isHandLocked = false;
	private boolean isStartCal = false;

	private boolean isInRange = false;
	private boolean isContacted = false;

	private boolean isClicked = false;

	private int absSeSum = -1;
	private int SeSum = -1;

	private int subAbsSeSum = -1;
	private int subSeSum = -1;

	private int absHoverXSum = -1;
	private int hoverXSum = -1;

	private int subAbsHoverXSum = -1;
	private int subHoverXSum = -1;

	private int absHoverYSum = -1;
	private int hoverYSum = -1;

	private int subAbsHoverYSum = -1;
	private int subHoverYSum = -1;

	private TextView tvText;

	private Utility utility = new Utility();

	private int count = 0;
	private int slideCount = 0;
	private int leftHandConfirmCount = 0;
	private int rightHandConfirmCount = 0;

	private int previousMaxChnIndex = -1;
	private int maxChnIndex = -1;
	private int lockDownIndex = -1;

	private int slideThreshold = -1;
	private int clickThreshold = -1;
	private int clickPersistentL = -1;
	private int clickPersistentH = -1;

	private Activity act;

	T38Handler t38Handler = new T38Handler(ConstantFactory.mxtDevice);
	T37Handler t37Handler = new T37Handler(ConstantFactory.mxtDevice);
	T6Handler t6Handler = new T6Handler(ConstantFactory.mxtDevice);
	T113Handler t113Handler = new T113Handler(ConstantFactory.mxtDevice);

	private SoundPool soundPool;
	private SoundPool soundPool2;

	LightnessControl lightControl = new LightnessControl();

	Vibrator vibrator;

	private EditText etSignal;

	ArrayList<XSelfDelta> xSelfRawDelta = new ArrayList<XSelfDelta>();

	private Button btnSetting;
	private Button btnAbout;

	private LinearLayout textLayout;

	private Button btn;

	int baseLineMax;
	int baseLineSum;

	private EditText etHand;
	private EditText etSlide;

	private Button btnFlag1;
	private Button btnFlag2;
	private Button btnFlag3;
	private Button btnFlag4;
	private Button btnFlag5;
	private Button btnFlag6;
	private Button btnFlag7;
	private Button btnFlag8;
	private Button btnFlag9;
	private Button btnFlag10;
	private Button btnFlag11;
	private Button btnFlag12;
	private Button btnFlag13;
	private Button btnFlag14;
	private Button btnFlag15;
	private Button btnFlag16;
	private Button btnFlag17;
	private Button btnFlag18;
	private Button btnFlag19;
	private Button btnFlag20;
	private Button btnFlag21;
	private Button btnFlag22;
	private Button btnFlag23;
	private Button btnFlag24;
	private Button btnFlag25;
	private Button btnFlag26;
	private Button btnFlag27;
	private Button btnFlag28;
	private Button btnFlag29;
	private Button btnFlag30;
	private Button btnFlag31;
	private Button btnFlag32;

	Button[] btnLists = new Button[32];

	/**
	 * constructor of class:LCBtnOnClickListener <BR>
	 * 
	 * @author david.dong create:2015年2月23日下午11:25:42
	 * @param act
	 */
	public LCBtnOnClickListener(Activity act) {
		this.act = act;
		vibrator = (Vibrator) act.getSystemService(Context.VIBRATOR_SERVICE);
		// etSignal = (EditText) act.findViewById(R.id.editTextSignal);
		//

		// etHand = (EditText) act.findViewById(R.id.editTextHand);
		etSlide = (EditText) act.findViewById(R.id.editTextSlide);

		tvText = (TextView) act.findViewById(R.id.textViewText);

		btnAbout = (Button) act.findViewById(R.id.button4);

		btnSetting = (Button) act.findViewById(R.id.button2);

		textLayout = (LinearLayout) act.findViewById(R.id.textlayout);

		btn = (Button) act.findViewById(R.id.button3);

		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);

		soundPool.load(this.act, R.raw.click, 1);

		soundPool2 = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);

		soundPool2.load(this.act, R.raw.down, 1);

		btnFlag1 = (Button) this.act.findViewById(R.id.flag1);
		btnLists[0] = btnFlag1;

		btnFlag2 = (Button) this.act.findViewById(R.id.flag2);
		btnLists[1] = btnFlag2;

		btnFlag3 = (Button) this.act.findViewById(R.id.flag3);
		btnLists[2] = btnFlag3;

		btnFlag4 = (Button) this.act.findViewById(R.id.flag4);
		btnLists[3] = btnFlag4;

		btnFlag5 = (Button) this.act.findViewById(R.id.flag5);
		btnLists[4] = btnFlag5;

		btnFlag6 = (Button) this.act.findViewById(R.id.flag6);
		btnLists[5] = btnFlag6;

		btnFlag7 = (Button) this.act.findViewById(R.id.flag7);
		btnLists[6] = btnFlag7;

		btnFlag8 = (Button) this.act.findViewById(R.id.flag8);
		btnLists[7] = btnFlag8;

		btnFlag9 = (Button) this.act.findViewById(R.id.flag9);
		btnLists[8] = btnFlag9;

		btnFlag10 = (Button) this.act.findViewById(R.id.flag10);
		btnLists[9] = btnFlag10;

		btnFlag11 = (Button) this.act.findViewById(R.id.flag11);
		btnLists[10] = btnFlag11;

		btnFlag12 = (Button) this.act.findViewById(R.id.flag12);
		btnLists[11] = btnFlag12;

		btnFlag13 = (Button) this.act.findViewById(R.id.flag13);
		btnLists[12] = btnFlag13;

		btnFlag14 = (Button) this.act.findViewById(R.id.flag14);
		btnLists[13] = btnFlag14;

		btnFlag15 = (Button) this.act.findViewById(R.id.flag15);
		btnLists[14] = btnFlag15;

		btnFlag16 = (Button) this.act.findViewById(R.id.flag16);
		btnLists[15] = btnFlag16;

		btnFlag17 = (Button) this.act.findViewById(R.id.flag17);
		btnLists[16] = btnFlag17;

		btnFlag18 = (Button) this.act.findViewById(R.id.flag18);
		btnLists[17] = btnFlag18;

		btnFlag19 = (Button) this.act.findViewById(R.id.flag19);
		btnLists[18] = btnFlag19;

		btnFlag20 = (Button) this.act.findViewById(R.id.flag20);
		btnLists[19] = btnFlag20;

		btnFlag21 = (Button) this.act.findViewById(R.id.flag21);
		btnLists[20] = btnFlag21;

		btnFlag22 = (Button) this.act.findViewById(R.id.flag22);
		btnLists[21] = btnFlag22;

		btnFlag23 = (Button) this.act.findViewById(R.id.flag23);
		btnLists[22] = btnFlag23;

		btnFlag24 = (Button) this.act.findViewById(R.id.flag24);
		btnLists[23] = btnFlag24;

		btnFlag25 = (Button) this.act.findViewById(R.id.flag25);
		btnLists[24] = btnFlag25;

		btnFlag26 = (Button) this.act.findViewById(R.id.flag26);
		btnLists[25] = btnFlag26;

		btnFlag27 = (Button) this.act.findViewById(R.id.flag27);
		btnLists[26] = btnFlag27;

		btnFlag28 = (Button) this.act.findViewById(R.id.flag28);
		btnLists[27] = btnFlag28;

		btnFlag29 = (Button) this.act.findViewById(R.id.flag29);
		btnLists[28] = btnFlag29;

		btnFlag30 = (Button) this.act.findViewById(R.id.flag30);
		btnLists[29] = btnFlag30;

		btnFlag31 = (Button) this.act.findViewById(R.id.flag31);
		btnLists[30] = btnFlag31;

		btnFlag32 = (Button) this.act.findViewById(R.id.flag32);
		btnLists[31] = btnFlag32;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// Button btn = (Button) this.act.findViewById(R.id.button1);

		if (isClicked) {
			isClicked = false;

			// etHand.setText("");
			// etSlideDirection.setText("");

			btnSetting.setEnabled(true);
			btnAbout.setEnabled(true);
			t113Handler.setSelfXGain(gain);

			btn.setText("Start Demo");
			if (ConstantFactory.nTimer != null) {
				ConstantFactory.nTimer.cancel();
				ConstantFactory.nTimer = null;
			}
		} else {
			isClicked = true;

			btnSetting.setEnabled(false);
			btnAbout.setEnabled(false);
			btn.setText("Stop Demo");

			int lightness = lightControl.GetLightness(act);
			Log.v("Lightness Control",
					"lightness is " + String.valueOf(lightness));

			int mode = lightControl.getScreenMode(act);
			lightControl.stopAutoBrightness(act);

			count = 0;
			slideCount = 0;
			leftHandConfirmCount = 0;
			rightHandConfirmCount = 0;

			previousMaxChnIndex = 0;
			maxChnIndex = 0;

			gain = t113Handler.getSelfXGain();

			byte xGain = 10;

			t113Handler.setSelfXGain(xGain);

			byte[] t38Register = t38Handler.t38ReadValues(0, 8);
			int[] intValues = new int[4];
			for (int i = 0; i < 4; i++) {

				int lsb = t38Register[2 * i] & 0xff;
				int msb = t38Register[2 * i + 1] & 0xff;
				intValues[i] = lsb | (msb << 8);
			}

			slideThreshold = intValues[0];
			clickThreshold = intValues[1];
			clickPersistentL = intValues[2];
			clickPersistentH = intValues[3];

			t6Handler.t6Calibrate();

			if (ConstantFactory.nTimer == null) {
				ConstantFactory.nTimer = new Timer();
			}

			// t37Handler.readSEDelta(ConstantFactory.singleEndDelta);
			// t37Handler.readAllSelfDelta(ConstantFactory.selfDelta,
			// ConstantFactory.singleEndDelta);

			xSelfRawDelta.clear();
			// t37Handler.readAllSelfDelta(ConstantFactory.selfDelta,
			// ConstantFactory.singleEndDelta, ConstantFactory.hoverDelta);

			t37Handler.readHoverDelta(ConstantFactory.hoverDelta);

			// utility.copyXSCTDelta(ConstantFactory.selfDelta, xSelfRawDelta);

			ConstantFactory.nTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					absSeSum = 0;
					subAbsSeSum = 0;

					SeSum = 0;
					subAbsSeSum = 0;

					absHoverXSum = 0;
					subAbsHoverXSum = 0;

					hoverXSum = 0;
					subHoverXSum = 0;

					absHoverYSum = 0;
					subAbsHoverYSum = 0;

					hoverYSum = 0;
					subHoverYSum = 0;

					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}
			}, 0, 100);
		}

	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				if (ConstantFactory.isReadHoverDeltaDone) {
					long startTime = System.nanoTime();
					// t37Handler.readSEDelta(ConstantFactory.singleEndDelta);
					// t37Handler.readAllSelfDelta(ConstantFactory.selfDelta,
					// ConstantFactory.singleEndDelta);
					// t37Handler.readAllSelfDelta(ConstantFactory.selfDelta,
					// ConstantFactory.singleEndDelta,
					// ConstantFactory.hoverDelta);
					t37Handler.readHoverDelta(ConstantFactory.hoverDelta);

					// ArrayList<XSelfDelta> diffDeltas = new
					// ArrayList<XSelfDelta>();
					/*
					 * for(int
					 * i=0;i<ConstantFactory.selfDelta.getxSelfRawDelta()
					 * .size();i++){
					 * 
					 * int delta =
					 * ConstantFactory.selfDelta.getxSelfRawDelta().get
					 * (i).getDelta() - xSelfRawDelta.get(i).getDelta(); int
					 * index = i;
					 * 
					 * XSelfDelta tmpXSelfDelta = new XSelfDelta();
					 * tmpXSelfDelta.setDelta(delta);
					 * tmpXSelfDelta.setIndex(index);
					 * 
					 * diffDeltas.add(tmpXSelfDelta);
					 * 
					 * }
					 */

					// ArrayList<Integer> indexs =
					// utility.findIndexOverThresholdOnSCXDelta(ConstantFactory.selfDelta,
					// slideThreshold, 8, 14);
					// ArrayList<Integer> clickIndexs =
					// utility.findIndexOverThresholdOnSCXDelta(ConstantFactory.selfDelta,clickThreshold,clickArea,3,5);

					StringBuffer sb = new StringBuffer();
					// sb.append("(");

					XHoverDelta maxXHovObj = utility.findMaxOnHovXDelta(
							ConstantFactory.hoverDelta, 0, 32);

					YHoverDelta maxYHovObj = utility.findMaxOnHovYDelta(
							ConstantFactory.hoverDelta, 0, 17);

					Log.v("sidekey",
							"sidekey index"
									+ String.valueOf(maxXHovObj.getIndex())
									+ "_"
									+ String.valueOf(maxXHovObj.getDelta())
									+ "_"
									+ String.valueOf(maxYHovObj.getIndex())
									+ "_"
									+ String.valueOf(maxYHovObj.getDelta()));

					// ArrayList<Integer> hoverIndexs =
					// utility.findIndexOverThresholdOnHoverXDelta(ConstantFactory.hoverDelta,
					// 60);

					etSlide.setText("Ch(x)_"
							+ String.valueOf(maxXHovObj.getIndex())
							+ "    Delta_"
							+ String.valueOf(maxXHovObj.getDelta()) + "   "
							+ "Ch(y)_" + String.valueOf(maxYHovObj.getIndex())
							+ "    Delta_"
							+ String.valueOf(maxYHovObj.getDelta()));

					for (int i = 0; i < 32; i++) {

						if (maxXHovObj.getIndex() == i) {

							btnLists[i].setBackgroundColor(Color
									.parseColor("#ff0000"));
						} else {
							btnLists[i].setBackgroundColor(Color
									.parseColor("#00ff00"));
						}

					}

					maxXHovObj = utility.findMaxOnHovXDelta(
							ConstantFactory.hoverDelta, 12, 32);

					// if(clickIndexs.size()!=0){

					// if(maxYHovObj.getIndex() == 16){

					if ((maxXHovObj.getIndex() > 25)
							&& (maxXHovObj.getIndex() < 33)
							&& (maxXHovObj.getDelta() > clickThreshold)
							&& (maxYHovObj.getIndex() == 16)) {

						count++;
						isContacted = true;

					} else {

						if ((count > clickPersistentL)
								&& (count < clickPersistentH) && (isContacted)) {

							// isContacted = true;

							if (isStartCal) {
								isStartCal = false;
								tvText.setText("Atmel");
								tvText.setTextColor(Color.parseColor("#ffffff"));
								tvText.setBackgroundResource(R.drawable.background);
								soundPool.play(1, 1, 1, 0, 0, 1);
							} else {
								isStartCal = true;
								tvText.setText("MaxTouch");
								tvText.setTextColor(Color.parseColor("#0088cc"));
								tvText.setBackgroundResource(R.drawable.background2);
								soundPool.play(1, 1, 1, 0, 0, 1);
								// textLayout.setBackgroundColor(0xffffffff);
							}
						}

						count = 0;
						isContacted = false;

					}

					Log.v("sidekey", "click status" + String.valueOf(count)
							+ String.valueOf(isContacted));

					// if(indexs.size()!=0){

					if ((maxXHovObj.getIndex() < 23)
							&& (maxXHovObj.getIndex() > 11)
							&& (maxXHovObj.getDelta() > slideThreshold)
							&& (maxYHovObj.getIndex() == 16)) {

						// isContacted = true;
						// XSelfDelta maxObj =
						// utility.findMaxOnSCXDelta(ConstantFactory.selfDelta,
						// 8, 14);
						// XHoverDelta maxHovObj =
						// utility.findMaxOnHovXDelta(ConstantFactory.hoverDelta,
						// 0, 37);

						/*
						 * for(int i=8;i<14;i++){
						 * 
						 * int index = diffDeltas.get(i).getIndex(); int delta =
						 * diffDeltas.get(i).getDelta();
						 * 
						 * sb.append("(" + index + "," + delta + ")");
						 * 
						 * 
						 * }
						 */
						int index = maxXHovObj.getIndex();
						String str = "";

						if (slideCount == 0) {
							previousMaxChnIndex = index;
							slideCount++;
						}

						if (index > previousMaxChnIndex) {

							str = "UP Slide";
							// etSlideDirection.setText(str);
							textSize += 10;
							if (textSize > 100) {
								textSize = 100;
							}
							// soundPool.play(1,1, 1, 0, 0, 1);
						} else if (index < previousMaxChnIndex) {

							str = "Down Slide";
							// etSlideDirection.setText(str);
							textSize -= 10;
							if (textSize < 10) {
								textSize = 10;
							}
							// soundPool2.play(1,1, 1, 0, 0, 1);
						} else {

						}
						Log.v("sidekey", "counter_v" + String.valueOf(count));
						/*
						 * if(isContacted == false){ if((count < 20)&&(count
						 * >5)){
						 * 
						 * if(isStartCal){ isStartCal = false;
						 * tvText.setText("Atmel");
						 * //textLayout.setBackgroundColor(0x00000000); }else{
						 * isStartCal = true; tvText.setText("MaxTouch");
						 * //textLayout.setBackgroundColor(0xffffffff); }
						 * 
						 * }
						 * 
						 * }
						 */
						tvText.setTextSize(textSize);
						previousMaxChnIndex = index;

						// isContacted = true;
						// count = 0;

						/*
						 * if(maxObj.getIndex() == 8){
						 * 
						 * }else if(maxObj.getIndex() == 13){
						 * 
						 * }else{ XSelfDelta preObj =
						 * ConstantFactory.selfDelta.getxSelfRawDelta
						 * ().get(index-1); XSelfDelta postObj =
						 * ConstantFactory.
						 * selfDelta.getxSelfRawDelta().get(index+1); }
						 * Log.v("sidekey","diffdelta" + "max (" +
						 * String.valueOf(maxObj.getIndex()) + "," +
						 * String.valueOf(maxObj.getIndex()) +
						 * ")"+String.valueOf(isStartCal));
						 */
						// Log.v("sidekey","diffdelta" + "max (" +
						// String.valueOf(maxObj.getIndex()) + "," +
						// String.valueOf(maxObj.getIndex()) +
						// ")"+String.valueOf(isStartCal));
					} else {
						slideCount = 0;
						/*
						 * isContacted = false; count ++;
						 * 
						 * if(count > 200){ count = 0; }
						 */
					}

					long consumingTime = System.nanoTime() - startTime;
					Log.v("execution time",
							"sidekey response  "
									+ String.valueOf(consumingTime / 1000)
									+ "us");

				}
				break;
			}
			super.handleMessage(msg);
		}

	};

}

class CompareXSelfDelta implements Comparator<XSelfDelta> {

	@Override
	public int compare(XSelfDelta lhs, XSelfDelta rhs) {
		// TODO Auto-generated method stub
		if (lhs.getDelta() > rhs.getDelta()) {
			return 1;
		} else if (lhs.getDelta() == rhs.getDelta()) {
			return 0;
		} else {
			return -1;
		}
	}

}