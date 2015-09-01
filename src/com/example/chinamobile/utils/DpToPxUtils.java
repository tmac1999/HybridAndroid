package com.example.chinamobile.utils;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class DpToPxUtils {
	@SuppressLint("NewApi")
	public static int FromDpToPx(Context context, int Dp) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		DisplayMetrics realMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getRealMetrics(realMetrics);
		float density = outMetrics.density;
		float density2 = realMetrics.density;
		System.out.println("realMetrics.densityDpi" + realMetrics.densityDpi
				+ "realMetrics.xdpi" + realMetrics.xdpi
				+ "realMetrics.ydpi" + realMetrics.ydpi
				+ "realMetrics.heightPixels" + realMetrics.heightPixels
				+ "realMetrics.widthPixels" + realMetrics.widthPixels
				+ "realMetrics.scaledDensity" + realMetrics.scaledDensity);
		System.out.println("density===" + density);
		return (int) (Dp * density);
	}
}
