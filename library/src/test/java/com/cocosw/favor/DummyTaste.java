package com.cocosw.favor;

import android.content.SharedPreferences;

/**
 * @author Mohd Farid mohd.farid@devfactory.com
 */
public class DummyTaste extends Taste {

    private SharedPreferences.Editor editor;

    DummyTaste(SharedPreferences sp, String key, String[] defaultValue) {
        super(sp, key, defaultValue);
    }

    @Override
    Object get() {
        return "DummyValue";
    }

    @Override
    SharedPreferences.Editor editor(Object object) {
        return editor;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }
}
