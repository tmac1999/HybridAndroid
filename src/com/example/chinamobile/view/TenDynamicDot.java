package com.example.chinamobile.view;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class TenDynamicDot extends View{

	private Paint paint;
	private ArrayList<Paint> paints;

	public TenDynamicDot(Context context, AttributeSet attrs, int defStyle) {
		this(context,attrs);
	}

	public TenDynamicDot(Context context, AttributeSet attrs) {
		super(context, attrs);
		paints = new ArrayList<Paint>();
		for(int i = 0;i<10;i++){
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(Color.BLUE);
			paints.add(paint);
		}
		if(onColorChangedListener!=null){
			
			onColorChangedListener.onColorChanged(paints);
		}
	}

	public TenDynamicDot(Context context) {
		super(context);
		paints = new ArrayList<Paint>();
		for(int i = 0;i<10;i++){
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(Color.BLUE);
			paints.add(paint);
		}
		if(onColorChangedListener!=null){
			
			onColorChangedListener.onColorChanged(paints);
		}
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//onColorChangedListener.onColorChanged(paints);
		for(int i=0;i<10;i++){
			
			RectF oval = new RectF((1.0f+i*20),(1.0f+i*20),(20.0f+i*20),(20.0f+i*20));
			canvas.drawOval(oval, paints.get(i));
		}
		
		
	}
	public ArrayList<Paint> getPaints(){
		return paints;
				
	}
	private int count = 0;
	int[] colors = {Color.GREEN,Color.BLUE,Color.LTGRAY};
	public void startScaleAnimation(){
		new Thread(){
			public void run() {
				for (int i = 0; i < paints.size(); i++) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//睡3秒，改变颜色
					paints.get(count).setColor(colors[count%3]);
					
					postInvalidate();
//					runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							//主线程触发重绘
//							tdd.invalidate();
//							
//						}
//					});
				}
			};
		}.start();
		count++;
	
	}
	private onColorChangedListener onColorChangedListener;

	public onColorChangedListener getOnColorChangedListener() {
		return onColorChangedListener;
	}

	public void setOnColorChangedListener(
			onColorChangedListener onColorChangedListener) {
		this.onColorChangedListener = onColorChangedListener;
	}

	public interface onColorChangedListener{
		public void onColorChanged(ArrayList<Paint> paints);
	}

}
