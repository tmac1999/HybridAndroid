package com.example.chinamobile.base;

import java.util.ArrayList;
import java.util.List;

import com.example.chinamobile.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NewsActivity extends Activity{
	
	@ViewInject(R.id.lv_showpackage)
	private ListView lv_showpackage;
	@ViewInject(R.id.lv_showappname)
	private ListView lv_showappname;
	@ViewInject(R.id.tv_showpackage_total)
	private TextView tv_showpackage_total;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpackage_activity);
		ViewUtils.inject(this);
		List<ApplicationInfo> list = getPackageManager().getInstalledApplications(0);
		ArrayList<String> packageList = new ArrayList<String>();
		ArrayList<String> appList = new ArrayList<String>();
		for (ApplicationInfo applicationInfo : list) {
			packageList.add(applicationInfo.toString()+"=="+applicationInfo.loadLabel(getPackageManager()).toString());
			appList.add(applicationInfo.loadLabel(getPackageManager()).toString());
		}
		lv_showpackage.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, packageList));
		//lv_showappname.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appList));
		lv_showappname.setVisibility(View.GONE);
		tv_showpackage_total.setText("应用总数:"+appList.size());
	}
}
