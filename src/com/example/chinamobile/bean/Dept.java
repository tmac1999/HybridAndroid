package com.example.chinamobile.bean;

import java.util.ArrayList;
import java.util.Set;

public class Dept {
	public String dept_id;// 本部门id
	public String dept_name;//部门名字
	public String sort;//userid所在 部门 的 排位
	public ArrayList<Dept> subdepts;// 改部门管辖下的所有子部门 ，若没有子部门，则为空
	public ArrayList<User> users;// 改部门 所有同事--同级
	public Dept(){
		subdepts = new ArrayList<Dept>();
		users = new ArrayList<User>();
	}

	@Override
	public String toString() {
		return "Dept [dept_id=" + dept_id + ", dept_name=" + dept_name
				+ ", sort=" + sort + ", subdepts=" + subdepts + ", users="
				+ users + "]";
	}

}
