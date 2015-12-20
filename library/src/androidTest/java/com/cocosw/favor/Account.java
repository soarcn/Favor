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

    @Default("-1")
    int getGroupId();

    @Commit
    void setGroupId(int id);

    @Favor("extra")
    @Commit
    void setExtraPassword(String extra);
}
