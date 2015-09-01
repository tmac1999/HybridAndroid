package com.example.chinamobile.utils;

import cn.jpush.android.service.t;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * sp 工具类
 * @author mrz
 *
 */
public class SharePrefsUtils {
	private static String fileName ;
	public SharePrefsUtils(String fileName){
		this.fileName = fileName;
	}
	public static void putParams(Context context,String key,Object obj){
		String type = obj.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		if(obj instanceof String){
			edit.putString(key, (String)obj);
		}
		if(type.equals("Integer")){
			edit.putInt(key, (Integer)obj);
		}
		if("Boolean".equals(type)){
			edit.putBoolean(key, (Boolean)obj);
		}
		if("Long".equals(type)){
			edit.putLong(key, (Long)obj);
		}
		edit.commit();
	}
}
