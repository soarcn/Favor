package com.cocosw.favor;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ApplicationTestCase;

import com.f2prateek.rx.preferences.Preference;

import junit.framework.Assert;

import java.util.HashSet;
import java.util.Set;

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
        remove("alive");
        Assert.assertEquals(true, profile.alive());
        remove("height");
        Assert.assertEquals(170, profile.getHeight());
        remove("distance");
        Assert.assertEquals(10000, profile.getDistance());
        remove("age");
        Assert.assertEquals(32.5f, profile.getAge());
    }

    public void testLongValue() {
        profile.setDistance(1000);
        Assert.assertEquals(1000, sp().getLong("distance", 0));
        Assert.assertEquals(1000, profile.getDistance());
    }

    public void testBoolValue() {
        remove("alive");
        Assert.assertEquals(true, profile.alive());
        profile.isAlive(false);
        Assert.assertEquals(false, profile.alive());
        Assert.assertEquals(false, sp().getBoolean("alive", true));
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

    public void testRxSPTypes() {
        profile.age().get();
        profile.height().get();
        profile.distance().get();
    }


    public void testWrongCases() {
        final FavorAdapter adapter = new FavorAdapter.Builder(getContext()).build();
        adapter.enableLog(true);
        final Wrong wrong = adapter.create(Wrong.class);

        //A warning
        assertNotNull(wrong.commitForRxPreference());

        try {
            wrong.testWrongDefaultValue();
            Assert.fail("Fail to check the wrong default value type");
        } catch (Exception e) {
            Assert.assertEquals(NumberFormatException.class, e.getClass());
        }

        assertNull(wrong.testReturnValueForSetter(11));

        assertException(new Runnable() {
            @Override
            public void run() {
                wrong.testUnsupportedType(null);
            }
        }, "Unsupported type", "Fail to check unsupported value type");

        assertException(new Runnable() {
            @Override
            public void run() {
                wrong.testUnsupportedType();
            }
        }, "Unsupported type", "Fail to check unsupported value type");


        assertException(new Runnable() {
            @Override
            public void run() {
                wrong.testUnsupportedType(null);
            }
        }, "Unsupported type", "Fail to check unsupported value type");

        assertException(new Runnable() {
            @Override
            public void run() {
                wrong.testTooMuchParameters(1, 2);
            }
        }, "more than one parameter", "Fail to check redundant parameters");

        assertException(new Runnable() {
            @Override
            public void run() {
                adapter.create(Wrong.FavorClz.class);
            }
        }, "interface definitions", "Only interface definitions are supported");
    }

    public void testGivenSharedPreferences() {
        SharedPreferences sp = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        FavorAdapter userAdapter = new FavorAdapter.Builder(sp).build();
        userAdapter.enableLog(true);
        profile.setAddress(null);
        Profile userprofile = userAdapter.create(Profile.class);
        userprofile.setAddress("address");
        assertNull(profile.getAddress());
        assertEquals("address", userprofile.getAddress());
    }

    public void testSetNull() {
        profile.setAddress(null);
        assertNull(profile.getAddress());
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


    private void assertException(Runnable runnable, String message, String failMsg) {
        try {
            runnable.run();
            Assert.fail(failMsg);
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains(message));
        }
    }

    public void testStringSet() {
        remove("hobbies");
        assertTrue(profile.hobbies() == null);
        HashSet<String> hobbies = new HashSet<>();
        hobbies.add("swimming");
        hobbies.add("programming");
        hobbies.add("having sex");
        profile.setHobbies(hobbies);
        Set<String> out = profile.hobbies();
        assertEquals(3, out.size());
        assertTrue(out.contains("swimming"));
        assertTrue(out.contains("programming"));
        assertTrue(out.contains("having sex"));
    }

    public void testSerializableObject() {
        remove("image");
        assertNull(profile.image());
        Image image = new Image();
        image.url = "http://www.cocosw.com";
        image.format = "jpg";
        profile.setImage(image);

        Image img = profile.image();
        assertNotNull(img);
        assertEquals("jpg", img.format);
        assertEquals("http://www.cocosw.com", img.url);
    }


    public void testNegativeNumber() {
        FavorAdapter adapter = new FavorAdapter.Builder(getContext()).build();
        adapter.enableLog(true);
        Account account = adapter.create(Account.class);
        assertEquals(-1, account.getGroupId());
    }


}