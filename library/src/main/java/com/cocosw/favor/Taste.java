package com.cocosw.favor;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;

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
            return sp.getInt(key, Integer.valueOf(defaultValue[0]));
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
            return sp.getBoolean(key, Boolean.valueOf(defaultValue[0]));
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
            return sp.getFloat(key, Float.valueOf(defaultValue[0]));
        }

        @SuppressLint("CommitPrefEdits")
        @Override
        void commit(Object object) {
            sp.edit().putFloat(key, (Float) object).commit();
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
