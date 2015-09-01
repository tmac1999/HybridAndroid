package com.example.chinamobile.utils;

import android.util.Log;

public class CustomUtils {
	public static void LogToLogs(String veryLongString) {
		int maxLogSize = 4500;
		System.out.println("veryLongString.length==="+veryLongString.length());
		for (int i = 0; i <= veryLongString.length() / maxLogSize; i++) {

			int start = i * maxLogSize;

			int end = (i + 1) * maxLogSize;

			end = end > veryLongString.length() ? veryLongString.length() : end;

			Log.v("LogToLogs", veryLongString.substring(start, end));

		}

	}
}
