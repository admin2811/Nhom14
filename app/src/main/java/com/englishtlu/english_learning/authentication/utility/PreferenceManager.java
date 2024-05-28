package com.englishtlu.english_learning.authentication.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "user_pref";
    private static final String KEY_PASSWORD = "password";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void savePassword(String password) {
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }
    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }
    public void clear() {
        editor.remove(KEY_PASSWORD);
        editor.apply();
    }
}
