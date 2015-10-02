package com.cocosw.favor;

/**
 * <p/>
 * Created by kai on 30/09/15.
 */
@AllFavor
public interface Account {

    @Default("No Name")
    String getUserName();

    String getPassword();

    void setPassword(String password);

    @Favor("extra")
    @Commit
    void setExtraPassword(String extra);
}
