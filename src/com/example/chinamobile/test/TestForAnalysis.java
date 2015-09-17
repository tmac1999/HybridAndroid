package com.example.chinamobile.test;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.chinamobile.base.NewsActivity;
import com.example.chinamobile.bean.Dept;
import com.example.chinamobile.bean.StandardDept;
import com.example.chinamobile.bean.StandardUser;
import com.example.chinamobile.dao.UserForJsDao;
import com.example.chinamobile.utils.CustomUtils;
import com.example.chinamobile.utils.JsonParseUtils;
import com.lidroid.xutils.util.LogUtils;

import android.R.integer;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.os.Messenger;
import android.test.InstrumentationTestCase;
import android.text.format.Formatter;


public class TestForAnalysis extends InstrumentationTestCase{
	@Override
	protected void setUp() throws Exception {
		getInstrumentation().getContext();
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	public void testLogicalCaculate(){
		int i = 0xffff0000;
		int j = 0x55000000;
		int result = j-i;
		String hexString = Integer.toHexString(result);
		//int result = i&&j;
		System.out.println("===="+hexString);
		
		
		
		ValueAnimator animator = ValueAnimator.ofInt(00,0xff);
		animator.setDuration(2000);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int i = 0xffff0000;
				int animatedValue = (Integer) animation.getAnimatedValue();
				System.out.println("<<24==="+animatedValue);
				int color = (animatedValue<<24)&i;
				System.out.println("color=="+color);
				
			}
		});
		animator.start();
	}
	public void testMathAPI(){
		
		double cos = Math.cos(Math.toRadians(60));
		System.out.println("cos60="+cos);
	}
	public void testTimeAPI(){
		long currentTimeMillis = System.currentTimeMillis();
		currentTimeMillis = 1000000000;
		
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		String format2 = format.format(new Date(currentTimeMillis));
		String timeString = format.format(new Date());
		System.out.println("currentTimeMillis"+currentTimeMillis+"=="+currentTimeMillis/(1000*60*60*24)+"time=="+format2);
	}
	
	@SuppressWarnings("unchecked")
	public void testArrayList(){
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		List<ArrayList<String>> list2 = Arrays.asList(list);
		
		ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>(list2);
		System.out.println("arrayList.toString()"+arrayList.toString());
		System.out.println("list2"+list2.toString());
		System.out.println("list"+list.toString());
	}
	@SuppressWarnings("unchecked")
	public void TestJsonString(){
		UserForJsDao dao = new UserForJsDao();
		Dept dept = dao.queryDept("9999739");
		JSONObject result = JsonParseUtils.deptToJsString(dept);
		System.out.println(result.toJSONString());
		CustomUtils.LogToLogs(result.toJSONString());
		/*String jsonString = JSON.toJSONString(dept);
		System.out.println(jsonString);*/
	}
	public void TestBean(){
		StandardDept standardDept = new StandardDept();
		standardDept.item = new StandardUser();
		standardDept.children = new ArrayList<StandardUser>();
		standardDept.item.checked = false;
		standardDept.item.id = 100+"";
		standardDept.item.label = "部门";
		for(int i = 0;i<10;i++){
			StandardUser standardUser = new StandardUser();
			standardUser.id = i+"";
			standardUser.label = "员工"+i;
			standardUser.checked = false;
			standardDept.children.add(standardUser);
		}
		
		
		
		
		
		ArrayList<StandardDept> list = new ArrayList<StandardDept>();
		StandardDept standardDept2 = new StandardDept();
		standardDept.item = new StandardUser();
		standardDept.children = new ArrayList<StandardUser>();
		standardDept.item.checked = false;
		standardDept.item.id = 100+"";
		standardDept.item.label = "部门2";
		
		list.add(standardDept);
		list.add(standardDept2);
		StringBuffer buffer = new StringBuffer();
		for (StandardDept standardDept3 : list) {
			
			buffer.append(standardDept3);
		}
		System.out.println(buffer);
		
		/*String jsonString = JSON.toJSONString(standardDept);
		System.out.println(jsonString);
		LogUtils.i(jsonString);*/
	}
	public void test() throws JSONException{
		JSONObject object = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for(int i = 0;i<4;i++){
			JSONObject subobject = new JSONObject();
			
			JSONObject aaJsonObject=new JSONObject();
			aaJsonObject.put("id", "1"+i);
			aaJsonObject.put("label", "dd"+i);
			aaJsonObject.put("checked", false);
			subobject.put("item", aaJsonObject);
			jsonArray.add(subobject);
		}
		
		/*jsonArray.add(object);
		jsonArray.add(object);
		jsonArray.add(object);*/
		JSONObject aa=new JSONObject();
		aa.put("id", "1");
		aa.put("label", "dd");
		aa.put("checked", false);
		
		object.put("children", jsonArray);
		object.put("item", aa);
		//System.out.println(object.toString(0));
		System.out.println(object.toString());
		String jsonString = JSON.toJSONString(object,true);
		System.out.println(jsonString);
	}
	/*public void TestIMCMCC(){
		com.im_cmcc.
		UserDao dao = new com.im_cmcc.UserDao();
		Dept dept = dao.queryDept("9999718");
		JSONObject result = JsonParseUtils.deptToJsString(dept);
		
		
		System.out.println(result.toJSONString());
		String jsonString = JSON.toJSONString(dept);
		System.out.println(jsonString);
	}*/
}
