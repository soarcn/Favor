package com.cocosw.favor;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private Profile profile;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        profile = new FavorAdapter(getContext()).create(Profile.class);
    }

    public void testGetValue() {
        setSP("city", "Sydney");
        Assert.assertEquals("Sydney", profile.city());
    }

    public void testPutValue() {
        profile.setAge(10);
        Assert.assertEquals(10, sp().getInt("age", 0));
    }


    public void testDefaultValue() {
        remove("city");
        Assert.assertEquals("Sydney", profile.city());
    }


    private void setSP(String key, String value) {
        sp().edit().putString(key, value).commit();
    }

    private void remove(String key) {
        sp().edit().remove(key);
    }

    private SharedPreferences sp() {
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }
}