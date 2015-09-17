package com.example.chinamobile.base;

import java.util.ArrayList;

import org.json.JSONObject;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.chinamobile.MainActivity;
import com.example.chinamobile.R;
import com.example.chinamobile.utils.Util;
import com.example.chinamobile.view.TenDynamicDot;
import com.example.chinamobile.view.TenDynamicDot.onColorChangedListener;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class TestCustomViewActivity extends Activity implements OnClickListener {
	private Button btn;
	private TenDynamicDot tdd;
	private String mAppid = "1104787677";
	private static Tencent mTencent;
	private Button mBtn_loginQQ;
	private Button mLogoutQQ;
	private Button mShowQQInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testcustomview_activity);
		tdd = (TenDynamicDot) findViewById(R.id.tdd);
		btn = (Button) findViewById(R.id.btn);
		mBtn_loginQQ = (Button) findViewById(R.id.btn_loginQQ);
		mLogoutQQ = (Button) findViewById(R.id.btn_logoutQQ);
		mShowQQInfo = (Button) findViewById(R.id.btn_showQQInfo);
		mLogoutQQ.setOnClickListener(this);
		mShowQQInfo.setOnClickListener(this);
		btn.setOnClickListener(this);
		mBtn_loginQQ.setOnClickListener(this);
		setClick();
		
		mTencent = Tencent.createInstance(mAppid, this);
	}
	
	private void setClick() {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(tdd + "btn" + btn);
				// final ArrayList<Paint> paints = tdd.getPaints();
				String text1 = "Battery in charge....90%";
				String text2 = "Low Battery,Please charge battery...";
				tdd.startScaleAnimationWithText(TestCustomViewActivity.this,text1,text2);

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_loginQQ:
			System.out.println("mTencent" + mTencent.isSessionValid());
			if (!mTencent.isSessionValid()) {
				mTencent.login(this, "all", loginListener);
				// isServerSideLogin = false;
				Log.d("SDKQQAgentPref",
						"FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
			} /*
			 * else { if (isServerSideLogin) { // Server-Side 模式的登陆,
			 * 先退出，再进行SSO登陆 mTencent.logout(this); mTencent.login(this, "all",
			 * loginListener); isServerSideLogin = false;
			 * Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" +
			 * SystemClock.elapsedRealtime()); return; }
			 */
			mTencent.logout(this);
			/*
			 * updateUserInfo(); updateLoginButton();
			 */

			break;
		case R.id.btn_logoutQQ:
			mTencent.logout(TestCustomViewActivity.this);
			break;
		case R.id.btn_showQQInfo:
			UserInfo userInfo = new UserInfo(this, mTencent.getQQToken());
			System.out.println(userInfo.toString());
			Toast.makeText(this, userInfo.toString(), 0).show();	
			break;
		default:
			break;
		}

	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			Log.d("SDKQQAgentPref",
					"AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
			initOpenidAndToken(values);
			// updateUserInfo();
			// updateLoginButton();
		}
	};

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				Util.showResultDialog(TestCustomViewActivity.this, "返回为空",
						"登录失败");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				com.example.chinamobile.utils.Util.showResultDialog(
						TestCustomViewActivity.this, "返回为空", "登录失败");
				return;
			}
			Util.showResultDialog(TestCustomViewActivity.this,
					response.toString(), "登录成功");
			// 有奖分享处理
			// handlePrizeShare();
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(TestCustomViewActivity.this, "onError: "
					+ e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			Util.toastMessage(TestCustomViewActivity.this, "onCancel: ");
			Util.dismissDialog();
			/*
			 * if (isServerSideLogin) { isServerSideLogin = false; }
			 */
		}
	}

	public static void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
			}
		} catch (Exception e) {
		}
	}

}
