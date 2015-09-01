package com.example.chinamobile.bean;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ForJSTreeBean {
	/*public Item item;

	public class Item {

		public String id;
		public String label;
		public Boolean checked;
	}

	public List<Children> children;

	public class Children {

		public Item item;

		public class Item {

			public String id;
			public String label;
			public Boolean checked;
			Object aa=new JSONArray();
			
			
		}
	}*/
	public static void test() throws JSONException{
		JSONObject aaJsonObject=new JSONObject();
		aaJsonObject.put("id", "1");
		aaJsonObject.put("label", "dd");
		aaJsonObject.put("checked", "false");
		
		System.out.println(aaJsonObject.toString());
	}
	public static void main(String[] args) throws JSONException {
		test();
	}
	
}
/*
 * package test; import java.lang.reflect.Field; import java.io.Serializable;
 * import java.util.List; public class ForJSTreeBean {
 * 
 * public Item item; public class Item {
 * 
 * public String id ; public String label ; public Boolean checked ; }public
 * List<Children> children; public class Children {
 * 
 * public Item item; public class Item {
 * 
 * public String id ; public String label ; public Boolean checked ; } }
 * 
 * }
 */