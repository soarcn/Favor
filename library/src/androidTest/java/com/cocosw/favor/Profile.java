package com.cocosw.favor;

import com.f2prateek.rx.preferences.Preference;

import java.util.Set;

/**
 * <p/>
 * Created by kai on 25/09/15.
 */

public interface Profile {

    @Favor("city")
    @Default("Sydney")
    String city();

    @Favor
    String getAddress();

    @Favor
    @Commit
    void setAddress(String address);

    @Favor
    @Default("32.5")
    float getAge();

    @Favor
    void setAge(float age);

    @Favor
    void isAlive(boolean alive);

    @Favor
    @Default("true")
    boolean alive();

    @Favor
    @Default("170")
    int getHeight();

    @Favor
    void setHeight(int height);

    @Favor
    @Default("10000")
    long getDistance();

    @Favor
    void setDistance(long distance);

    @Favor
    Set<String> hobbies();

    @Favor
    void setHobbies(Set<String> hobbies);

    @Favor("name")
    @Default("No Name")
    Preference<String> name();

    @Favor(value = "gender")
    Preference<Boolean> gender();

    @Favor
    Preference<Float> age();

    @Favor
    Preference<Integer> height();

    @Favor
    Preference<Long> distance();


}
