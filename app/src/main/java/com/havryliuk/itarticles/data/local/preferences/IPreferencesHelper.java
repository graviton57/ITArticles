package com.havryliuk.itarticles.data.local.preferences;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public interface IPreferencesHelper {

    void setLoggedIn(boolean value);

    boolean isLoggedIn();

    void setUserName(String userName);

    String getUserName();

    String getAccessToken();

    void setAccessToken(String accessToken);

}
