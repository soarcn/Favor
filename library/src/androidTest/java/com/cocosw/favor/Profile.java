package com.cocosw.favor;

import com.f2prateek.rx.preferences.Preference;

import java.util.Set;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 25/09/15.
 */
public interface Profile {

    @Favor()
    @Default("Sydney")
    String city();

    @Favor()
    void setAge(int age);

    @Default({"Sex", "Sex"})
    Set<String> hobbies();

    @Favor()
    @Commit
    void setAddress(String address);


    @Favor("name")
    @Default("No Name")
    Preference<String> name();

    @Favor(value = "gender")
    Preference<Boolean> gender();
}
