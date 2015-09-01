package com.example.chinamobile.base.impl;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.chinamobile.base.BaseActivity;
import com.example.chinamobile.globle.Constants;
import com.example.chinamobile.utils.AndroidForJS;
import com.lidroid.xutils.util.LogUtils;

public class DefaultActivity extends BaseActivity {

	@Override
	public void initData() {
		// 获取到webview的设置对象
		LogUtils.i("webView_Base====" + webView_Base);
		WebSettings settings = webView_Base.getSettings();
		// webView_Base.loadUrl(ConstantURL.Mobile_URL);
		webView_Base.loadUrl("http://www.baidu.com");
		webView_Base.setBackgroundColor(Color.parseColor("#4EC964"));
		// 允许执行js代码
		settings.setJavaScriptEnabled(true);
		// 允许放大缩小
		settings.setBuiltInZoomControls(true);
		webView_Base.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				TimerTask task = new TimerTask() {

					@Override
					public void run() {
						LogUtils.i("开始弹吐司了");
						// 三秒后showtoast提示加载失败
						Looper.prepare();
						Toast.makeText(DefaultActivity.this, "加载失败", 0).show();
						Looper.loop();
					}
				};
				Timer timer = new Timer();
				// timer.schedule(task, 0, 1000);
				timer.schedule(task, 3000);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return super.shouldOverrideUrlLoading(view, url);
			}

			/**
			 * 页面加载完成后，关闭progressbar
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				ll_base_loading.setVisibility(View.INVISIBLE);
				LogUtils.i("onPageFinished==========");
			}

			/**
			 * 页面出错后，根据错误码执行相应逻辑。
			 */
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				System.out.println("errorCode======" + errorCode
						+ "description======" + description
						+ "failingUrl======" + failingUrl);
				webView_Base.setVisibility(View.INVISIBLE);
				ll_base_error.setVisibility(View.VISIBLE);
			}
		});
		webView_Base.addJavascriptInterface(new AndroidForJS(this), "Android");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)&&webView_Base.canGoBack()) {
			webView_Base.goBack();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
