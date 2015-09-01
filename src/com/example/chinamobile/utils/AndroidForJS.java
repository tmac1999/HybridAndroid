package com.example.chinamobile.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.chinamobile.bean.Dept;
import com.example.chinamobile.dao.UserForJsDao;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.webkit.JavascriptInterface;

;

/**
 * JS的调用的方法
 * 
 * @author 
 *         <p>
 *         此类中的打开的QQ 和微信是直接通过包名和类名调用虽然QQ微信包名不容易变 但是主界面好事可能会变
 *         如果发现打不开QQ微信应用可以查看是否是QQ微信升级更改了类名
 */
public class AndroidForJS {
	String testJsonString = "{item:{id: 'id1',label: 'label1',checked: false},children: [{item:{id: 'id11',label: 'label11',checked: false} },{item:{id: 'id12',label: 'label12',checked: false} },{item:{id: 'id13',label: 'label13',checked: false} }]}";

	Context c;
	String[] qqpackage = new String[] { "com.tencent.mobileqq",
			"com.tencent.mobileqq.activity.SplashActivity" };
	String[] wxpackage = new String[] { "com.tencent.mm",
			"com.tencent.mm.ui.LauncherUI" };

	public AndroidForJS(Context c) {
		this.c = c;
	}

	/**
	 * 查user_id
	 * 
	 * @param telphone
	 */
	@JavascriptInterface
	public String findAllSubUsers() {
		
		UserForJsDao userDao = new UserForJsDao();
		Dept dept;
		try {
			dept = userDao.queryDept("3997036");
			//System.out.println("user_id"+user_id);
			JSONObject result = JsonParseUtils.deptToJsString(dept);
			System.out.println("json===" + result.toJSONString());
			return result.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "用户id 不存在！或用户不属于任何部门！";
		}

	}

	/**
	 * 打电话
	 * 
	 * @param telphone
	 */
	@JavascriptInterface
	public void callPhone(final String telphone) {
		// System.out.println("电话方法被调用了。。。。");
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ telphone));
		c.startActivity(intent);

	}

	/**
	 * 打开QQ 页面的方法
	 * 
	 * @param qq
	 */
	@JavascriptInterface
	public void callQQ(String qq) {
		// 实现调用电话号码

		if (!checkBrowser(qqpackage[0])) {

		} else {
			Intent intent = new Intent();
			ComponentName cmp = new ComponentName(qqpackage[0], qqpackage[1]);
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setComponent(cmp);
			c.startActivity(intent);
		}

	}

	@JavascriptInterface
	public void callWeixin(String weixin) {

		if (!checkBrowser(wxpackage[0])) {

		} else {
			Intent intent = new Intent();
			ComponentName cmp = new ComponentName(wxpackage[0], wxpackage[1]);
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setComponent(cmp);
			c.startActivity(intent);

		}

	}

	// 获取在webview上获取js生成的html的源码
	@JavascriptInterface
	public void getSource(String htmlstr) {
		// Log.e("html", htmlstr);
		// String path = c.getFilesDir().getAbsolutePath() + "/serve.html"; //
		// data/data目录

	}

	// 检测包名的应用是否已经安装在手机
	public boolean checkBrowser(String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			ApplicationInfo info = c.getPackageManager().getApplicationInfo(
					packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
}
