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

    void testUnsupportedType(Profile profile);

    Profile testUnsupportedType();

    void testTooMuchParameters(int a, int b);

    @Commit
    Preference<String> commitForRxPreference();

    class FavorClz {

    }
}
