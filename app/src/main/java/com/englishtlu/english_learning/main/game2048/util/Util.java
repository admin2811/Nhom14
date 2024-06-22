package com.englishtlu.english_learning.main.game2048.util;

import android.content.Context;
import android.preference.PreferenceManager;

public class Util {

    public static int log2(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    public static int getCurrentTheme(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(Constants.THEME_PREF , 0);
    }

    public static void setCurrentTheme(Context context, int theme) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putInt(Constants.THEME_PREF , theme)
                .apply();
    }

}
