package com.example.chinamobile;

import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chinamobile.base.BaseActivity;
import com.example.chinamobile.base.PartyNewsActivity;
import com.example.chinamobile.base.ShowPackageActivity;
import com.example.chinamobile.base.TestRequestActivity;
import com.example.chinamobile.base.impl.DefaultActivity;
import com.example.chinamobile.base.impl.DemoCallActivity;
import com.example.chinamobile.view.DraggableGridView;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends Activity {

	private String[] mItems = new String[] { "默认服务器页", "Android与JS互调",
			"展示所有应用", "模拟Post请求", "功能5", "功能6", "功能7", "功能8", "功能9" };
	private int[] mPics = new int[] { R.drawable.home_safe,
			R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings };
	@ViewInject(R.id.gv_pushnotify)
	private DraggableGridView gv_pushnotify;
	private GridViewAdapter gridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		gridAdapter = new GridViewAdapter();
		// gv_pushnotify.setAdapter(gridAdapter);
		fillGridView();

		LogUtils.i("android.os.Build.VERSION.CODENAME"
				+ android.os.Build.VERSION.RELEASE);// 系统版本
		LogUtils.i("android.os.Build.VERSION.CODENAME"
				+ android.os.Build.VERSION.CODENAME);
		LogUtils.i("android.os.Build.VERSION.SDK"
				+ android.os.Build.VERSION.SDK);// 手机sdk版本
		LogUtils.i("android.os.Build.MODEL" + android.os.Build.MODEL);// 手机型号
		PackageManager packageManager = getPackageManager();
		List<PackageInfo> list = packageManager.getInstalledPackages(0);

		LogUtils.i("list" + list.toString());
		initFunction();

	}

	private void fillGridView() {
		for (int position = 0; position < 9; position++) {
			LinearLayout gv_item = (LinearLayout) View.inflate(
					MainActivity.this, R.layout.gv_item, null);
			ImageView iv_function = (ImageView) gv_item
					.findViewById(R.id.iv_function);
			TextView tv_function = (TextView) gv_item
					.findViewById(R.id.tv_function);
			tv_function.setText(mItems[position]);
			iv_function.setImageResource(mPics[position]);
			// 设置消息提示 数目

			BadgeView backgroundDrawableBadge = new BadgeView(MainActivity.this);
			backgroundDrawableBadge.setBadgeCount(position);
			// backgroundDrawableBadge.setBackgroundResource(R.drawable.badge_blue);
			backgroundDrawableBadge.setTargetView(iv_function);

			gv_pushnotify.addView(gv_item);
		}

	}

	/**
	 * 初始化跳转到 相应功能activity
	 */
	private void initFunction() {
		gv_pushnotify.setOnItemClickListener(new OnItemClickListener() {

			private Intent intent;

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:// 默认服务器页面
					intent = new Intent(MainActivity.this,
							DefaultActivity.class);
					MainActivity.this.startActivity(intent);
					break;
				case 1:// js android 互相调用测试页面
					intent = new Intent(MainActivity.this,
							DemoCallActivity.class);
					MainActivity.this.startActivity(intent);
					break;
				case 2:// js android 互相调用测试页面
					intent = new Intent(MainActivity.this,
							ShowPackageActivity.class);
					MainActivity.this.startActivity(intent);
					break;
				case 3:// js android 互相调用测试页面
					intent = new Intent(MainActivity.this,
							TestRequestActivity.class);
					MainActivity.this.startActivity(intent);
					break;
				case 4:// js android 互相调用测试页面
					intent = new Intent(MainActivity.this,
							PartyNewsActivity.class);
					MainActivity.this.startActivity(intent);
				default:
					break;
				}

			}
		});

	}

	/**
	 * 9宫格适配器
	 * 
	 * @author mrz
	 * 
	 */
	private class GridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 9;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout gv_item = (LinearLayout) View.inflate(
					MainActivity.this, R.layout.gv_item, null);
			ImageView iv_function = (ImageView) gv_item
					.findViewById(R.id.iv_function);
			TextView tv_function = (TextView) gv_item
					.findViewById(R.id.tv_function);
			tv_function.setText(mItems[position]);
			iv_function.setImageResource(mPics[position]);
			// 设置消息提示 数目

			BadgeView backgroundDrawableBadge = new BadgeView(MainActivity.this);
			backgroundDrawableBadge.setBadgeCount(88);
			// backgroundDrawableBadge.setBackgroundResource(R.drawable.badge_blue);
			backgroundDrawableBadge.setTargetView(iv_function);
			return gv_item;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
