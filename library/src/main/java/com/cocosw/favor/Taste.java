package com.cocosw.favor;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;

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

    protected void save(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            editor.apply();
        else
            editor.commit();
    }

    abstract void set(Object object);

    abstract Object get();

    abstract void commit(Object object);

    static class StringTaste extends Taste {

        StringTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        public void set(Object object) {
            save(sp.edit().putString(key, String.valueOf(object)));
        }


        @Override
        public Object get() {
            return sp.getString(key, defaultValue[0]);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void commit(Object object) {
            sp.edit().putString(key, String.valueOf(object)).commit();
        }
    }

    static class IntTaste extends Taste {

        IntTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void set(Object object) {
            save(sp.edit().putInt(key, (Integer) object));
        }

        @Override
        Object get() {
            return sp.getInt(key, defaultValue[0]==null?0:Integer.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void commit(Object object) {
            sp.edit().putInt(key, (Integer) object).commit();
        }
    }

    static class BoolTaste extends Taste {

        BoolTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void set(Object object) {
            save(sp.edit().putBoolean(key, (Boolean) object));
        }

        @Override
        Object get() {
            return sp.getBoolean(key, defaultValue[0]==null?false:Boolean.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void commit(Object object) {
            sp.edit().putBoolean(key, (Boolean) object).commit();
        }
    }

    static class FloatTaste extends Taste {
        FloatTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void set(Object object) {
            save(sp.edit().putFloat(key, (Float) object));
        }

        @Override
        Object get() {
            return sp.getFloat(key, defaultValue[0]==null?0:Float.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void commit(Object object) {
            sp.edit().putFloat(key, (Float) object).commit();
        }
    }

    static class LongTaste extends Taste {
        LongTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void set(Object object) {
            save(sp.edit().putLong(key, (Long) object));
        }

        @Override
        Object get() {
            return sp.getLong(key, defaultValue[0]==null?0:Long.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void commit(Object object) {
            sp.edit().putLong(key, (Long) object).commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    static class StringSetTaste extends Taste {
        StringSetTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void set(Object object) {
            //noinspection unchecked
            save(sp.edit().putStringSet(key, (Set<String>) object));
        }

        @Override
        Object get() {
            return sp.getLong(key, Long.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void commit(Object object) {
            sp.edit().putLong(key, (Long) object).commit();
        }
    }

    static class EmptyTaste extends Taste {

        EmptyTaste(SharedPreferences sp, String key, String[] defaultValue) {
            super(sp, key, defaultValue);
        }

        @Override
        void set(Object object) {

        }

        @Override
        Object get() {
            return null;
        }

        @Override
        void commit(Object object) {

        }
    }

}
