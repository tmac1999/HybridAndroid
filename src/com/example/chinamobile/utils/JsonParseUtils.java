package com.example.chinamobile.utils;

import java.util.ArrayList;

import cn.jpush.android.data.s;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chinamobile.bean.Dept;
import com.example.chinamobile.bean.User;

public class JsonParseUtils {
	/**
	 * 将dept解析成js 可以显示的字符串
	 * @param dept
	 * @return
	 */
	public static JSONObject deptToJsString(Dept dept){
		
		if (dept != null) {
			
			JSONObject deptObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			deptObject.put("id", dept.dept_id);
			deptObject.put("label", dept.dept_name);
			deptObject.put("checked", false);
			if (dept.subdepts.size() != 0) {//如果有子部门，遍历子部门
				ArrayList<Dept> subdepts = dept.subdepts;
				for (Dept subdept : subdepts) {
					/*JSONObject subDeptObject = new JSONObject();*/
					//JSONArray jsonArray = new JSONArray();
					/*//将子部门的id  ,name封装成对象，存入数组。
					subDeptObject.put("id", subdept.dept_id);
					subDeptObject.put("label", subdept.dept_name);
					subDeptObject.put("checked", false);*/
					
					JSONObject subResult = deptToJsString(subdept);
					jsonArray.add(subResult);
					
					/*jsonArray.add(subDeptObject);*/
				}
				
			}
			if (dept.users.size() != 0) {
				//同部门同事，判断sort,如果sort值大于dept.sort，则属于user下属，添加到deptobject中
				ArrayList<User> users = dept.users;
				for (User user : users) {
					if (dept.sort != null) {//不等于空，代表是userid所在部门  这一轮 查询
						
						if(Integer.parseInt(user.sort)>Integer.parseInt(dept.sort)){
							JSONObject subUser = new JSONObject();
							JSONObject subUserItem = new JSONObject();
							subUser.put("id", user.user_id);
							subUser.put("label", user.name);
							subUser.put("checked", false);
							subUserItem.put("item", subUser);//将user封装到item中
							//将item封装到jsonarray中
							jsonArray.add(subUserItem);
						}
					}else {
						//等于空，代表 是子部门查询这一轮，不需要判断sort
						JSONObject subUser = new JSONObject();
						JSONObject subUserItem = new JSONObject();
						subUser.put("id", user.user_id);
						subUser.put("label", user.name);
						subUser.put("checked", false);
						subUserItem.put("item", subUser);//将user封装到item中
						//将item封装到jsonarray中
						jsonArray.add(subUserItem);
					}
					
				}
			}
			//将jsonArray封装到要返回的result中
			JSONObject result = new JSONObject();
			result.put("children", jsonArray);//子元素，包含userid 同部门下属，以及下属部门
			result.put("item", deptObject);//根元素，为userid 所在部门
			return result;
		}
		
		
		return null;
	}
}
