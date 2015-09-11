package com.example.chinamobile.base;

import java.util.ArrayList;

import com.example.chinamobile.R;
import com.example.chinamobile.view.TenDynamicDot;
import com.example.chinamobile.view.TenDynamicDot.onColorChangedListener;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;

public class TestCustomViewActivity extends Activity{
	private Button btn;
	private TenDynamicDot tdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testcustomview_activity);
		tdd = (TenDynamicDot) findViewById(R.id.tdd);
		btn = (Button) findViewById(R.id.btn);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(tdd+"btn"+btn);
				//final ArrayList<Paint> paints = tdd.getPaints();
				tdd.startScaleAnimation();
				
			}
		});
		tdd.setOnColorChangedListener(new onColorChangedListener() {
			
			@Override
			public void onColorChanged(final ArrayList<Paint> paints) {
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
							paints.get(i).setColor(Color.GREEN);
							tdd.postInvalidate();
//							runOnUiThread(new Runnable() {
//								
//								@Override
//								public void run() {
//									//主线程触发重绘
//									tdd.invalidate();
//									
//								}
//							});
						}
					};
				}.start();
				
			}
		});
	}
}
