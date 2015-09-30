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
        profile = new FavorAdapter.Builder(getContext()).build().create(Profile.class);
    }

    public void testGetValue() {
        setSP("city", "Sydney");
        Assert.assertEquals("Sydney", profile.city());
    }

    public void testPutValue() {
        profile.setHeight(120);
        Assert.assertEquals(120, sp().getInt("height", 0));
        assertEquals(120, profile.getHeight());
    }


    public void testDefaultValue() {
        remove("city");
        Assert.assertEquals("Sydney", profile.city());
    }

    public void testLongValue() {
        profile.setDistance(1000);
        Assert.assertEquals(1000, sp().getLong("distance", 0));
        Assert.assertEquals(1000, profile.getDistance());
    }

    public void testBoolValue() {
        remove("alive");
        Assert.assertEquals(false, profile.alive());
        profile.isAlive(true);
        Assert.assertEquals(true, profile.alive());
        Assert.assertEquals(true, sp().getBoolean("alive", false));
    }

    public void testFloatValue() {
        profile.setAge(10.5f);
        Assert.assertEquals(10.5f, sp().getFloat("age", 0));
        assertEquals(10.5f, profile.getAge());
    }

    public void testAllFavor() {
        Account account = new FavorAdapter.Builder(getContext()).build().create(Account.class);
        remove("password");
        remove("username");
        assertEquals("No Name", account.getUserName());
        Assert.assertNull(account.getPassword());
        account.setPassword("Password");
        assertEquals("Password", account.getPassword());
    }

    public void testPrefix() {
        Account account = new FavorAdapter.Builder(getContext()).build().create(Account.class);
        remove("password");
        assertNull(account.getPassword());
        account.setPassword("Password");
        assertEquals("Password", account.getPassword());

        remove("_password");
        FavorAdapter favor = new FavorAdapter.Builder(getContext()).prefix("_").build();
        favor.enableLog(true);
        Account account1 = favor.create(Account.class);
        assertNull(account1.getPassword());
        account1.setPassword("Password");
        assertEquals("Password", account1.getPassword());
        assertEquals("Password", sp().getString("_password", null));
    }


    private void setSP(String key, String value) {
        sp().edit().putString(key, value).commit();
    }

    private void remove(String key) {
        sp().edit().remove(key).commit();
    }

    private SharedPreferences sp() {
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }


}