package com.example.chinamobile.base.impl;

import java.util.Timer;
import java.util.TimerTask;

import org.itri.html5webview.MyWb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chinamobile.R;
import com.example.chinamobile.base.BaseActivity;
import com.example.chinamobile.bean.Dept;
import com.example.chinamobile.dao.UserForJsDao;
import com.example.chinamobile.globle.Constants;
import com.example.chinamobile.utils.AndroidForJS;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DemoCallActivity extends Activity{
	@ViewInject(R.id.wv_baseactivity)
	public WebView webView_Base;

	@ViewInject(R.id.ll_base_loading)
	public LinearLayout ll_base_loading;

	@ViewInject(R.id.ll_base_error)
	public LinearLayout ll_base_error;

	private View btn_newPage;
	@OnClick(R.id.btn_calljs)
	public void callJS(View v){
		webView_Base.loadUrl("javascript:callJS()");
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.democall_activity);
		
		ViewUtils.inject(this);
		
		initData();
		btn_newPage = findViewById(R.id.btn_newpage);
		btn_newPage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				webView_Base.loadUrl("http://192.168.1.3:8080/task/mobile/apply_release.jsp?userid=3997036");
				System.out.println("http://192.168.1.3:8080/task/mobile/apply_release.jsp?dasdas=123");
			}
		});
	}

	
	public void initData() {
		// 获取到webview的设置对象
		WebSettings settings = webView_Base.getSettings();
		// AssetManager assetManager =

		/*http://192.168.1.4:8080/task/mobile/apply_release.jsp?userid=5495356
*/			
		//String path = "file:///android_asset/calljava.html";
		String pathTomcat = "http://192.168.1.7:8081/staffCorner/mobile/allPosts.jsp?userId=10004&userName=%E8%94%A1%E6%8C%AF%E5%8D%8E";
		//String pathTomcat = "http://192.168.1.17:8080/task/mobile/apply_release.jsp?userid=9999739";
		webView_Base.loadUrl(pathTomcat);
		webView_Base.setBackgroundColor(Color.parseColor("#4EC964"));
		// 允许执行js代码
		settings.setJavaScriptEnabled(true);
		// 允许放大缩小
		settings.setBuiltInZoomControls(true);
		
		webView_Base.setWebViewClient(new WebViewClient() {
			
			/**
			 * 在开始加载网页时，开启计时器，计时器超过5秒，进入超时页面。
			 */
			@SuppressLint("NewApi")
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view,
					String url) {
				System.out.println("shouldInterceptRequest==="+url);
				return super.shouldInterceptRequest(view, url);
			}
			@SuppressLint("NewApi")
			@Override
			public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
				System.out.println("shouldOverrideKeyEvent==="+event.getAction()+"getKeyCode=="+event.getKeyCode()+"getSource=="+event.getSource());
				return super.shouldOverrideKeyEvent(view, event);
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				System.out.println("url===="+url);
				/*TimerTask task = new TimerTask() {
					
					@Override
					public void run() {
						LogUtils.i("开始弹吐司了");
						//三秒后showtoast提示加载失败
						Looper.prepare();
						Toast.makeText(DemoCallActivity.this, "加载失败", 0).show();
						Looper.loop();
					}
				};
				Timer timer = new Timer();
				//timer.schedule(task, 0, 1000);
				timer.schedule(task, 3000);*/
			}

			/**
			 * 页面加载完成后，关闭progressbar
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				ll_base_loading.setVisibility(View.INVISIBLE);
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
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				int lastIndexOf = url.lastIndexOf("gohome");
				int indexOf = url.indexOf("gohome");
				System.out.println("lastIndexOf===="+lastIndexOf+"indexOf"+indexOf);
				
				if(url.contains("gohome")){
					System.out.println("goback====");
					DemoCallActivity.this.finish();
				}
				return super.shouldOverrideUrlLoading(view, url);
			}
			
			
		});
		//添加与js 交互的接口对象
		webView_Base.addJavascriptInterface(new AndroidForJS(this), "Android");
		
		
		webView_Base.setWebChromeClient(new WebChromeClient(){
			/*Tell the client to display a javascript alert dialog. If the client returns true, WebView will assume that the client will handle the dialog. If the client returns false, it will continue execution.

			Parameters
			view  The WebView that initiated the callback. 
			url  The url of the page requesting the dialog. 
			message  Message to be displayed in the window. 
			result  A JsResult to confirm that the user hit enter. 

			Returns
			boolean Whether the client will handle the alert dialog. */

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsAlert(view, url, message, result);
			}
			
			
			
			
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {

				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				DemoCallActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Chooser"),
						FILECHOOSER_RESULTCODE);

			}

			// For Android 3.0+
			public void openFileChooser(ValueCallback uploadMsg,
					String acceptType) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				DemoCallActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Browser"),
						FILECHOOSER_RESULTCODE);
			}
			
			// For Android 4.1
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String acceptType, String capture) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				DemoCallActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Chooser"),
						DemoCallActivity.FILECHOOSER_RESULTCODE);

			}
			
		});
	}
	
	/**
	 * 处理  openFileChooser
	 */
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage)
				return;
			Uri result = intent == null || resultCode != RESULT_OK ? null
					: intent.getData();
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
		}
	}
}
