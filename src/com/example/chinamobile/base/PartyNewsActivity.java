package com.example.chinamobile.base;

import com.example.chinamobile.R;
import com.example.chinamobile.globle.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PartyNewsActivity extends BaseActivity{

	@Override
	public void initData() {
		WebSettings settings = webView_Base.getSettings();
		
		settings.setJavaScriptEnabled(true);
		webView_Base.loadUrl(Constants.Mobile_URL);
		//getSharedPreferences(name, mode)
		
		webView_Base.setWebChromeClient(new WebChromeClient(){
			private WebView webView;

			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog,
					boolean isUserGesture, Message resultMsg) {
				webView = new WebView(getBaseContext());
				return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
			}
		});
	}
}
