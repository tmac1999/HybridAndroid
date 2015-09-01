package com.example.chinamobile.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import cn.jpush.android.data.r;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.chinamobile.R;
import com.example.chinamobile.bean.Dept;
import com.example.chinamobile.dao.UserForJsDao;
import com.example.chinamobile.dao.UserDaoOpenHelper;
import com.example.chinamobile.globle.Constants;
import com.example.chinamobile.utils.DpToPxUtils;
import com.example.chinamobile.utils.JsonParseUtils;
import com.lidroid.xutils.util.LogUtils;

import android.R.integer;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.GetChars;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TestRequestActivity extends Activity implements OnClickListener{
	private Button btn_post;
	private Button btn_queryDB;
	private TextView tv_result;
	private HashMap<String, String> map;
	private String result;

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//System.out.println("result===="+result);
			switch (msg.what) {
			case 1:
				Toast.makeText(TestRequestActivity.this, "拷贝成功", 0).show();
				break;

			default:
				break;
			}
		
			tv_result.setText(result);
		};
	};
	private AsyncTask<String,Integer,Boolean> task;
	private ProgressBar pb_emulator;
	private Button btb_copyDB;
	private File file;
	private EditText et_input;
	private String databasepath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		/*UserDaoOpenHelper helper = new UserDaoOpenHelper(this, "a.db", null, 1);
		
		helper.getReadableDatabase();
		helper.getWritableDatabase();*/
		
		setContentView(R.layout.testrequest_activity);
		databasepath = "/data/data/"+getPackageName(); 
		btn_post = (Button) findViewById(R.id.btn_post);
		tv_result = (TextView) findViewById(R.id.tv_result);
		pb_emulator = (ProgressBar) findViewById(R.id.pb_emulator);
		btb_copyDB = (Button) findViewById(R.id.btn_copyDB);
		btn_queryDB = (Button) findViewById(R.id.btn_queryDB);
		et_input = (EditText) findViewById(R.id.et_input);
		file = new File(databasepath, "84220.db");
		btn_post.setOnClickListener(this);
		btb_copyDB.setOnClickListener(this);
		
		btn_queryDB.setOnClickListener(this);
		int px = DpToPxUtils.FromDpToPx(this, 240);
		
		initData2();
		task = new AsyncTask<String, Integer, Boolean>(){
			
			@Override
			protected Boolean doInBackground(String... params) {
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					publishProgress(i);
				}
				result = httpPost(Constants.CALENDAR_SERVER_IP, map);
				if (result==null||"".equals(result)) {
					return false;
				} else {

					return true;
				}
				
			}
			@Override
			protected void onProgressUpdate(Integer... values) {
				
				System.out.println("values[0]==="+values[0]);
				pb_emulator.setVisibility(View.VISIBLE);
				pb_emulator.setProgress(values[0]*10);
				setProgress(values[0]);
				setProgressBarVisibility(true);
			}
			
			
			@Override
			protected void onPostExecute(Boolean res) {
				
				if (res) {
					
					tv_result.setText(result);
				}else {
					tv_result.setText("连接错误，请检查网络");
				}
			}
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}
			
		};
		
	}
	private void initData() {
		String userId = "13946711635570236005531181935216";
		String startDay = "20150727";
		String endDay = "20150906";
		map = new HashMap<String, String>();
		map.put("cmd", "queryUserSpecifyDayCalendarList");
		//map.put("userId", userId);
		map.put("startDay", startDay);
		map.put("endDay", endDay);
		
		
	}

	/*
	 * 08-12 16:52:31.788: I/System.out(31031): params======={pageCount=12,
	 * pageNumber=1, cmd=queryapplist, handState=0, userId=1407334}
	 */

	private void initData2() {
		String pageCount = "12";
		String pageNumber = "1";
		String handState = "0";
		String userId = "1407334";
		map = new HashMap<String, String>();
		map.put("cmd", "queryapplist");
		//map.put("userId", userId);
		map.put("pageCount", pageCount);
		map.put("pageNumber", pageNumber);
		map.put("handState", handState);
		map.put("userId", userId);
	}

	@Override
	public void onClick(View v) {
		//System.out.println("result====");
		/*new Thread(){
			

			public void run() {
				result = httpPost(Constants.ACTION_BAR, map);
				System.out.println("result===="+result);
				handler.sendEmptyMessage(0);
			};
		}.start();*/
		
		int id = v.getId();
		switch (id) {
		case R.id.btn_post:
			
			task.execute("aaa","bbb","ccc");
			break;

		case R.id.btn_copyDB:
			Toast.makeText(this, "开始拷贝", 0).show();
			new Thread(){
				public void run() {
					copyDB();
					handler.sendEmptyMessage(1);
				};
			}.start();
			break;
		case R.id.btn_queryDB://查询数据库并显示结果
			String path = file.getAbsolutePath();
			System.out.println("path==="+path);
			UserForJsDao userDao = new UserForJsDao(path);
			String userid = et_input.getText().toString();
			Dept dept = null;
			JSONObject deptToJsString = null;
			try {
				dept = userDao.queryDept(userid);
				deptToJsString = JsonParseUtils.deptToJsString(dept);
				
			} catch (Exception e) {
				e.printStackTrace();
				tv_result.setText(e.toString());
				break;
			}
			String deptString = JSON.toJSONString(dept);
			System.out.println(deptString);
			LogUtils.i(deptString);
			tv_result.setText(deptToJsString.toString());
			break;
		}
		
		
	}
	/**
	 * 拷贝 数据库 到 /databese  目录下
	 */
	protected void copyDB() {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = getAssets().open("84220.db");
			
			fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024*4];
			int len = -1;
			while((len = is.read(buffer))!= -1){
				fos.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static String httpPost(String url, Map params) {
		String result = "fail";
		MultipartEntity multipartEntity = new MultipartEntity();
		HttpPost httpPost = new HttpPost(url);
		System.out.println("url=====" + url);
		System.out.println("params======="+params.toString());
		httpPost.setHeader("Accept", "text/html");		Log.d("lxl","4444444444444444");
		if (null != params) {			
			try {
				Iterator it = params.entrySet().iterator();			
				while (it.hasNext()) {				
					Map.Entry entry = (Map.Entry) it.next();				
					multipartEntity.addPart((String) entry.getKey(),
							new StringBody((String) entry.getValue(), Charset.forName("utf-8")));				

				}
				httpPost.setEntity(multipartEntity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		HttpResponse response;
		HttpClient client = getNewHttpClient();
		
		try {
			response = client.execute(httpPost);			Log.d("lxl","777777777777777");
			// 获取响应实体
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity responseEntity = response.getEntity();
				result = EntityUtils.toString(responseEntity);
			}
			// EntityUtils.consume(resEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client = null;
		return result;
	}
	public static HttpClient getNewHttpClient() {
		HttpClient newhttpClient;
		HttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);

		// 设置连接管理器的超时
		ConnManagerParams.setTimeout(params, 15*1000);

		// 设置连接超时
		HttpConnectionParams.setConnectionTimeout(params, 15*1000);
		// 设置Socket超时
		HttpConnectionParams.setSoTimeout(params, 15*1000);

		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 80));

		ClientConnectionManager conManager = new ThreadSafeClientConnManager(params, schReg);

		newhttpClient = new DefaultHttpClient(conManager, params);

		return newhttpClient;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		task.cancel(true);
	}
}
