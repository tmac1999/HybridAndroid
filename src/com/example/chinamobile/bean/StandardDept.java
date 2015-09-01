package com.example.chinamobile.bean;

import java.util.ArrayList;



/**
 * 如果 是部门  则包含item对象和children数组（数组内是Item对象）
 * 如果是员工
 * @author mrz
 *
 */
public class StandardDept {
	public StandardUser item;
	public ArrayList<StandardUser> children;
	
	@Override
	public String toString() {
		if (children != null&&children.size()!=0) {
			
			StringBuilder builder = new StringBuilder();
			for (StandardUser user : children) {
				builder.append("{"+user+"},") ;
			}
			return "{"+item + ", children:[" + builder.toString()+"]}" ;
		}else {
			return "{"+item+"}";
		}
	}
}
