package com.cocosw.favor;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ApplicationTestCase;

import com.f2prateek.rx.preferences.Preference;

import junit.framework.Assert;

import static android.os.SystemClock.sleep;

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
        FavorAdapter adapter = new FavorAdapter.Builder(getContext()).build();
        adapter.enableLog(true);
        Account account = adapter.create(Account.class);
        remove("password");
        remove("username");
        assertEquals("No Name", account.getUserName());
        Assert.assertNull(account.getPassword());
        account.setPassword("Password");
        assertEquals("Password", account.getPassword());
        remove("extra");
        assertEquals(null, sp().getString("extra", null));
        account.setExtraPassword("extra");
        assertEquals("extra", sp().getString("extra", null));
        assertNull(sp().getString("extrapassword", null));
    }

    public void testPrefix() {
        Account account = new FavorAdapter.Builder(getContext()).build().create(Account.class);
        remove("password");
        assertNull(account.getPassword());
        account.setPassword("Password");
        assertEquals("Password", account.getPassword());

        remove("_password");
        FavorAdapter favor = new FavorAdapter.Builder(getContext()).prefix("_").build();
        Account account1 = favor.create(Account.class);
        assertNull(account1.getPassword());
        account1.setPassword("Password");
        assertEquals("Password", account1.getPassword());
        assertEquals("Password", sp().getString("_password", null));
    }

    public void testRxSharePreference() {
        Preference<String> name = profile.name();
        assertNotNull(name);
        assertEquals("No Name", name.get());
        Preference<Boolean> gender = profile.gender();

        remove("gender");

        assertNotNull(gender);
        gender.set(true);
        sleep(2000);
        assertTrue(gender.get());
        assertTrue(sp().getBoolean("gender", false));

        gender.delete();
        sleep(2000);
        assertNull(gender.get());
    }

    public void testWrongCases() {
        FavorAdapter adapter = new FavorAdapter.Builder(getContext()).build();
        adapter.enableLog(true);
        Wrong wrong = adapter.create(Wrong.class);

        //A warning
        assertNotNull(wrong.commitForRxPreference());

        try {
            wrong.testWrongDefaultValue();
            Assert.fail("Fail to check the wrong default value type");
        } catch (Exception e) {
            Assert.assertEquals(NumberFormatException.class, e.getClass());
        }

        assertNull(wrong.testReturnValueForSetter(11));
        try {
            wrong.testUnsupportedType(null);
            Assert.fail("Fail to check unsupported value type");
        } catch (Exception e) {
            Assert.assertEquals(IllegalArgumentException.class, e.getClass());
            Assert.assertTrue(e.getMessage().contains("Unsupported type"));
        }

        try {
            Object obj = wrong.testUnsupportedType();
            Assert.fail("Fail to check unsupported value type");
        } catch (Exception e) {
            Assert.assertEquals(IllegalArgumentException.class, e.getClass());
            Assert.assertTrue(e.getMessage().contains("Unsupported type"));
        }

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