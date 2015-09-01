package com.example.chinamobile.base;

import com.example.chinamobile.R;
import com.example.chinamobile.globle.Constants;
import com.example.chinamobile.utils.AndroidForJS;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public abstract class BaseActivity extends Activity{
	
	
	public WebView webView_Base;
	
	public LinearLayout ll_base_loading;
	
	@ViewInject(R.id.ll_base_error)
	public LinearLayout ll_base_error;
	/**
	 * 加载webview的时候，考虑到三个状态，分别进行处理
	 * 1.加载失败状态
	 * 2.超时状态
	 * 3.正在加载状态
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_activity);
		ViewUtils.inject(this); 
		webView_Base = (WebView) findViewById(R.id.wv_baseactivity);
		ll_base_loading = (LinearLayout) findViewById(R.id.ll_base_loading);
		ll_base_error = (LinearLayout) findViewById(R.id.ll_base_error);
		LogUtils.i("webView_Base===="+webView_Base);
		initData();
	}
	public abstract void initData();
	
}
