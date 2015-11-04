package com.cocosw.favor;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p/>
 * Created by kai on 29/09/15.
 */

abstract class Taste {

    protected final SharedPreferences sp;
    protected final String key;
    protected final String[] defaultValue;

    Taste(SharedPreferences sp, String key, String[] defaultValue) {
        this.sp = sp;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @SuppressLint("CommitPrefEdits")
    protected void save(SharedPreferences.Editor editor, boolean commit) {
        if (!commit || Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            editor.apply();
        else
            editor.commit();
    }

    void set(Object object) {
        save(editor(object), false);
    }

    abstract Object get();

    void commit(Object object) {
        save(editor(object), true);
    }

    abstract SharedPreferences.Editor editor(Object object);

    static class StringTaste extends Taste {

        StringTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @Override
        public Object get() {
            return sp.getString(key, defaultValue[0]);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        SharedPreferences.Editor editor(Object object) {
            return sp.edit().putString(key, (String) object);
        }
    }

    static class IntTaste extends Taste {

        IntTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @Override
        Object get() {
            return sp.getInt(key, defaultValue[0]==null?0:Integer.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        SharedPreferences.Editor editor(Object object) {
            return sp.edit().putInt(key, (Integer) object);
        }
    }

    static class BoolTaste extends Taste {

        BoolTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @Override
        Object get() {
            return sp.getBoolean(key, defaultValue[0]==null?false:Boolean.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        SharedPreferences.Editor editor(Object object) {
            return sp.edit().putBoolean(key, (Boolean) object);
        }
    }

    static class FloatTaste extends Taste {
        FloatTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @Override
        Object get() {
            return sp.getFloat(key, defaultValue[0]==null?0:Float.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        SharedPreferences.Editor editor(Object object) {
            return sp.edit().putFloat(key, (Float) object);
        }
    }

    static class LongTaste extends Taste {
        LongTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @Override
        Object get() {
            return sp.getLong(key, defaultValue[0]==null?0:Long.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        SharedPreferences.Editor editor(Object object) {
            return sp.edit().putLong(key, (Long) object);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    static class StringSetTaste extends Taste {
        private final HashSet<String> dv;

        StringSetTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
            if (defaultValue != null && defaultValue.length > 0 && defaultValue[0] != null)
                dv = new HashSet<>(Arrays.asList(defaultValue));
            else dv = null;
        }

        @Override
        Object get() {
            return sp.getStringSet(key, dv);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        SharedPreferences.Editor editor(Object object) {
            return sp.edit().putStringSet(key, (Set<String>) object);
        }
    }

    static class EmptyTaste extends Taste {

        EmptyTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @Override
        Object get() {
            return null;
        }

        @Override
        SharedPreferences.Editor editor(Object object) {
            return null;
        }

    }

}
