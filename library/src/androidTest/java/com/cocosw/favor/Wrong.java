package com.cocosw.favor;

import com.f2prateek.rx.preferences.Preference;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 2/10/15.
 */
@AllFavor
public interface Wrong {

    String testReturnValueForSetter(int value);

    @Default("Hello")
    int testWrongDefaultValue();

    @Commit
    Preference<String> commitForRxPreference();

}
