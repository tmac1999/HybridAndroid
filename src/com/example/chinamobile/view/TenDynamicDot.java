package com.example.chinamobile.view;

import java.util.ArrayList;

import android.R.integer;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewDebug.FlagToString;
import android.view.animation.Animation;

public class TenDynamicDot extends View{
	private float r = 300f;//控件半径
	private float radius = 20f;//圆点半径
	private Paint paint;
	private ArrayList<Paint> paints;
	private static String text = "";
	public TenDynamicDot(Context context, AttributeSet attrs, int defStyle) {
		this(context,attrs);
	}

	public TenDynamicDot(Context context, AttributeSet attrs) {
		super(context, attrs);
		paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintText.setTextSize(30f);
		paints = new ArrayList<Paint>();
		for(int i = 0;i<10;i++){
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(0x00000000);
			paints.add(paint);
		}
		if(onColorChangedListener!=null){
			
			onColorChangedListener.onColorChanged(paints);
		}
	}

	public TenDynamicDot(Context context) {
		super(context);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//onColorChangedListener.onColorChanged(paints);
		for(int i=0;i<10;i++){
			float cx = (float) ((Math.sin(Math.toRadians(36*i))+1)*r)+50f;
			float cy = (float) ((1-Math.cos(Math.toRadians(36*i)))*r)+50f;
			//RectF oval = new RectF((1.0f+i*20),(1.0f+i*20),(20.0f+i*20),(20.0f+i*20));
			//canvas.drawOval(oval, paints.get(i));
			canvas.drawCircle(cx, cy, radius, paints.get(i));
		}
		canvas.drawText(text, r/2, r, paintText);
		
	}
	public ArrayList<Paint> getPaints(){
		return paints;
				
	}
	private int count = 0;
	private boolean flag = true;
	int[] colors = {Color.BLUE,Color.GREEN,Color.LTGRAY};
	public void startScaleAnimationWithText(final Activity activity,final String text1,final String text2){
		
		new Thread(){
			public void run() {
				for (int i = 0; i < paints.size(); i++) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//睡3秒，改变颜色
					paints.get(i).setColor(colors[count%3]);
					startScalePaint(activity,paints.get(i));
					System.out.println("count="+count+"==i="+i);
					postInvalidate();
				}
				
				if (flag) {
					
					startScaleText(text1);
					flag = false;
				}else {
					
					startScaleText(text2);
					flag = true;
				}
			};
		}.start();
		count++;
		System.out.println("count="+count);
			
	}


	private void startScaleText(String text) {
		char[] textArray = text.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<textArray.length;i++){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sb.append(textArray[i]);
			TenDynamicDot.text = sb.toString();
			
			postInvalidate();
			
		}
		
	}
	/**
	 * 渐变动画，动态改变paint的透明度00-ff
	 * @param activity 
	 * @param paint
	 */
	protected void startScalePaint(Activity activity, final Paint paint){
		
		
		
		final ValueAnimator animator = ValueAnimator.ofInt(0xff,00);
		animator.setDuration(200);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			int i = paint.getColor();
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int animatedValue = (Integer) animation.getAnimatedValue();
				System.out.println("<<24==="+Integer.toHexString(animatedValue));
				System.out.println(Integer.toHexString(animatedValue<<24));
				int color = i-(animatedValue<<24);
				String hexString2 = Integer.toHexString(color);
				System.out.println("color=="+hexString2);
				paint.setColor(color);
				postInvalidate();
			}
		});
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				
				animator.start();
			}
		});
	}
	private onColorChangedListener onColorChangedListener;
	private Paint paintText;

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
