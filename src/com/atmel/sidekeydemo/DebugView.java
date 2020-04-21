package com.atmel.sidekeydemo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.atmel.sidekeydemo.datatype.XHoverDelta;
import com.atmel.sidekeydemo.datatype.YHoverDelta;
import com.atmel.sidekeydemo.service.T37Handler;
import com.atmel.sidekeydemo.service.T6Handler;
import com.atmel.sidekeydemo.utility.Utility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class DebugView extends View {

	public int[] heightX = new int[32];
	public int[] deltaX = new int[32];
	
	public int[] heightY = new int[18];
	public int[] deltaY = new int[18];
	
	public int maxChnX = -1;
	public int maxChnY = -1;
	//public int 
	
	private T37Handler t37Handler = new T37Handler(ConstantFactory.mxtDevice);
	private T6Handler t6Handler = new T6Handler(ConstantFactory.mxtDevice);
	private Utility utility = new Utility();
	
	private Point originalPX = new Point(100,1000);
	
	private Point originalPY = new Point(1500,1000);
	
	
	private Point oriTextX = new Point(1150,100);
	private Point oriTextY = new Point(2066,100);
	
	
	private Point oriLogoX = new Point(100,100);
	private Point oriLogoY = new Point(1500,100);
	
	public DebugView(Context context) {
		
		super(context);
		// TODO Auto-generated constructor stub
		
		for(int i=0;i<32;i++){
			
			heightX[i] = 500;
			deltaX[i] = 0;
		}
		
		
		for(int i=0;i<18;i++){
		
			heightY[i] = 500;
			deltaY[i] = 0;
			
		}
		
		
		if(ConstantFactory.nTimer == null){
			ConstantFactory.nTimer = new Timer();
		}
		
		
		t37Handler.readHoverDelta(ConstantFactory.hoverDelta);
		
		
		ConstantFactory.nTimer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}}, 0, 100);
		
		
		
		
	}

	
	
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			
			case 1:
				
				if(ConstantFactory.isReadHoverDeltaDone){
					
					t37Handler.readHoverDelta(ConstantFactory.hoverDelta);
					
					ArrayList<XHoverDelta> xHoverRawDelta = ConstantFactory.hoverDelta.getxHoverRawDelta();
					ArrayList<YHoverDelta> yHoverRawDelta = ConstantFactory.hoverDelta.getyHoverRawDelta();
					
					
					XHoverDelta maxXHovObj = utility.findMaxOnHovXDelta(ConstantFactory.hoverDelta, 0, 32);
					YHoverDelta maxYHovObj = utility.findMaxOnHovYDelta(ConstantFactory.hoverDelta, 0, 18);
					
					maxChnX = maxXHovObj.getIndex();
					
					for(int i=0;i<32;i++){
						
						int rawDelta = xHoverRawDelta.get(i).getDelta();
						if(rawDelta>=0){
							heightX[i] = originalPX.y-rawDelta;
						}
						else{
							heightX[i] = originalPX.y+ Math.abs(rawDelta);
						}
						
						deltaX[i] = rawDelta;
					}
					
					
					maxChnY = maxYHovObj.getIndex();
					
					
					for(int i=0;i<18;i++){
						
						int rawDelta = yHoverRawDelta.get(i).getDelta();
						if(rawDelta>=0){
							heightY[i] = originalPY.y-rawDelta;
						}
						else{
							heightY[i] = originalPY.y+ Math.abs(rawDelta);
						}
						
						deltaY[i] = rawDelta;
					}
					
					
					
					invalidate();
				}
				break;
				
			default:
				
				break;
			}
					
			super.handleMessage(msg);
		}

	};
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		
		 Paint paint = new Paint();
		  //设置画笔颜色为红色
		  paint.setColor(Color.GREEN);
		  //绘制矩形
		  for(int i=0;i<32;i++){
			  
			  if(i==maxChnX){
				  paint.setColor(Color.RED);
			  }
			  else{
				  paint.setColor(Color.GREEN);
			  }
			  
			  if(heightX[i] >= originalPX.y){
				  
				  canvas.drawRect(originalPX.x+i*30, originalPX.y, originalPX.x+i*30+20, heightX[i], paint);
			  }
			  else{
				  canvas.drawRect(originalPX.x+i*30, heightX[i], originalPX.x+i*30+20, originalPX.y, paint);
			  }
			  
		  }
		 
		  
		  for(int i=0;i<18;i++){
			  
			  if(i==maxChnY){
				  paint.setColor(Color.RED);
			  }
			  else{
				  paint.setColor(Color.GREEN);
			  }
			  
			  if(heightY[i] >= originalPY.y){
				  
				  canvas.drawRect(originalPY.x+i*30, originalPY.y, originalPY.x+i*30+20, heightY[i], paint);
			  }
			  else{
				  canvas.drawRect(originalPY.x+i*30, heightY[i], originalPY.x+i*30+20, originalPY.y, paint);
			  }
		  }
		  
		  
		  
		  
		  
		  
		  Paint p_str = new Paint();
		  p_str.setTextSize(20);
		  p_str.setColor(Color.WHITE);
		  
		  for(int i=0; i< 32;i++){
			  
			  canvas.drawText(String.valueOf(i), originalPX.x + i*30, originalPX.y+20, p_str);
		  }
		  
		  for(int i=0; i< 18; i++){
			  
			  canvas.drawText(String.valueOf(i), originalPY.x + i*30, originalPY.y+20, p_str);
		  }
		  
		  
		  
		  p_str.setColor(Color.WHITE);
		  
		  for(int i=0;i<32;i++){
			  
			  if(i==maxChnX){
				  p_str.setColor(Color.RED);
			  }
			  else{
				  p_str.setColor(Color.WHITE);
			  }
			  canvas.drawText(String.valueOf(i)+ "_" + deltaX[i] , oriTextX.x, oriTextX.y+i*30, p_str);
		  }
		  
		  for(int i=0;i<18;i++){
			  
			  if(i==maxChnY){
				  p_str.setColor(Color.RED);
			  }
			  else{
				  p_str.setColor(Color.WHITE);
			  }
			  canvas.drawText(String.valueOf(i)+ "_" + deltaY[i] , oriTextY.x, oriTextY.y+i*30, p_str);
		  }
		  
		  
		  p_str.setColor(Color.WHITE);
		  p_str.setTextSize(50);
		  canvas.drawText("x lines ->", oriLogoX.x, oriLogoX.y, p_str);
		  canvas.drawText("y lines ->", oriLogoY.x, oriLogoY.y, p_str);
		  
		  canvas.drawText("x lines peak:" + deltaX[maxChnX], oriLogoX.x, oriLogoX.y+50, p_str);
		  canvas.drawText("y lines peak:" + deltaY[maxChnY], oriLogoY.x, oriLogoY.y+50, p_str);
		  
		  p_str.setColor(Color.LTGRAY);
		  canvas.drawLine(originalPY.x - 130, 20, originalPY.x-130, 1200, p_str);// 画线 
	}

	
	
	
}
