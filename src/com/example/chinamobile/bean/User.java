package com.example.chinamobile.bean;

public class User {
	public String user_id;
	public String title;
	public String dept_id;
	
	public String user_name;
	public String name;
	/*public String gender;*/
	public String phone_num;
	/**
	 * 所在 部门内的 排位。（级别）
	 * 100--最低级
	 * 2---副总经理
	 * 1--总经理
	 * 3---一般经理
	 */
	public String sort;
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", title=" + title + ", dept_id="
				+ dept_id + ", user_name=" + user_name + ", name=" + name
				+ ", phone_num=" + phone_num + ", sort=" + sort + "]";
	}
	
}
