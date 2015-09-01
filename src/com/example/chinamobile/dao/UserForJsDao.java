package com.example.chinamobile.dao;

import java.net.Socket;
import java.util.ArrayList;

import com.example.chinamobile.bean.Dept;
import com.example.chinamobile.bean.User;

import android.R.integer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 读取84220.db
 * 
 * @author mrz
 * 
 */
public class UserForJsDao {
	private SQLiteDatabase db;
	private static String DBPath = "/data/data/com.example.chinamobile/84220.db";

	public UserForJsDao(String path) {
		db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	/**
	 * 打开默认数据库地址
	 */
	public UserForJsDao() {
		db = SQLiteDatabase.openDatabase(DBPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	/*
	 * public User queryUserByUserID(String userid){
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
	public Dept queryDept(String userid) {
		ArrayList<String> list = new ArrayList<String>();// 存放要查userid所在部门的一级子部门
		Dept dept = new Dept();
		// 根据userid到member表中查询相应的dept_id
		String sql1 = "select dept_id,sort from qxdept_member where user_id = ?";
		Cursor cursor0 = db.rawQuery(sql1, new String[] { userid });
		int dept_id = -1;
		String sort = null;
		// 当这个user_id不隶属于任何一个部门时，cursor的row值为0
		if (cursor0.getCount() == 0) {
			// 如果 不属于任何一个部门，抛出一个异常
			// throw new RuntimeException("该用户尚不属于任何部门！");
			// throw new IllegalArgumentException("该用户尚不属于任何部门！");
			throw new NullPointerException("该用户尚不属于任何部门！");
		}
		while (cursor0.moveToNext()) {
			int columnIndex = cursor0.getColumnIndex("dept_id");
			dept_id = cursor0.getInt(columnIndex);
			sort = cursor0.getString(cursor0.getColumnIndex("sort"));
		}

		String sql2 = "select name from qxdept where id = ?";
		Cursor cursor1 = db.rawQuery(sql2,
				new String[] { String.valueOf(dept_id) });
		String dept_name = null;
		if (cursor1.moveToNext()) {
			dept_name = cursor1.getString(cursor1.getColumnIndex("name"));
		}
		dept.sort = sort;
		dept.dept_name = dept_name;
		dept.dept_id = String.valueOf(dept_id);// 本部门的id
		querySubDept(dept, dept.dept_id);
		cursor0.close();
		cursor1.close();

		/*
		 * cursor2.close(); cursor3.close(); cursor4.close();
		 */
		db.close();
		return dept;
	}

	/**
	 * 查找子部门及其人员
	 * 
	 * @param dept
	 * @param dept_id
	 */
	private void querySubDept(Dept dept, String dept_id) {
		Cursor cursor2 = null;
		Cursor cursor3 = null;
		Cursor cursor4 = null;

		// 把dept_id作为parent_id到qxdept中查询其 子 id如果有，继续将子id 作为parent_id 查其子id

		String sql2 = "select id,name from qxdept where parent_id = ?";
		// 根据dept_id到member表中查找其所有user_id
		String sql3 = "select user_id,title,sort from qxdept_member where dept_id = ?";
		// 根据user_id到qxuser表中查找其名字 号码 title
		String sql4 = "select user_name,display_name,mobile_phone from qxuser where id = ?";
		/*
		 * // 根据子部门id 查子部门name
		 * 
		 * String sql5 = "select name from qxdept where id = ?";
		 */
		if (dept_id != null) {
			// 先查部门里所有员工
			cursor3 = db.rawQuery(sql3,
					new String[] { String.valueOf(dept_id) });
			if (cursor3.getCount() == 0) {
				// 改
			}
			while (cursor3.moveToNext()) {
				String user_id = cursor3.getString(cursor3
						.getColumnIndex("user_id"));
				String title = cursor3.getString(cursor3
						.getColumnIndex("title"));
				String sort = cursor3.getString(cursor3.getColumnIndex("sort"));
				User user = new User();
				user.dept_id = String.valueOf(dept_id);
				user.user_id = user_id;
				user.title = title;
				user.sort = sort;
				cursor4 = db.rawQuery(sql4, new String[] { user_id });
				if (cursor4.moveToNext()) {

					String user_name = cursor4.getString(cursor4
							.getColumnIndex("user_name"));
					String display_name = cursor4.getString(cursor4
							.getColumnIndex("display_name"));
					String moblie_phone = cursor4.getString(cursor4
							.getColumnIndex("mobile_phone"));
					user.user_name = user_name;
					user.phone_num = moblie_phone;
					user.name = display_name;
				}
				dept.users.add(user);
			}

			// 再查子部门的id
			cursor2 = db.rawQuery(sql2,
					new String[] { String.valueOf(dept_id) });

			if (cursor2.getCount() != 0) {// 如果有子部门
				while (cursor2.moveToNext()) {
					// 查找所有的 子部门的id
					String subdept_id = cursor2.getString(cursor2
							.getColumnIndex("id"));

					// 子部门的id 对应的子部门的name
					String subdept_name = cursor2.getString(cursor2
							.getColumnIndex("name"));
					Dept subDept = new Dept();
					subDept.dept_id = subdept_id;
					subDept.dept_name = subdept_name;
					dept.subdepts.add(subDept);
					querySubDept(subDept, subdept_id);
				}

			} else {
				if (cursor2 != null) {

					cursor2.close();
				}
				if (cursor3 != null) {

					cursor3.close();
				}
				if (cursor4 != null) {

					cursor4.close();
				}
			}
		}
	}

}
