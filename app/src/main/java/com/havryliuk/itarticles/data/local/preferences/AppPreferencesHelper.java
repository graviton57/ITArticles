package com.havryliuk.itarticles.data.local.preferences;

import javax.inject.Inject;

/**
 * Created by Igor Havrylyuk on 23.10.2017.
 */

public class AppPreferencesHelper implements IPreferencesHelper {

    private static final String PREF_KEY_IS_LOGGED_IN = "PREF_KEY_IS_LOGGED_IN";
    private static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME_VALUE";
    private static final String PREF_KEY_USER_TOKEN = "PREF_KEY_USER_TOKEN_VALUE";

    private CommonPreferencesHelper commonPreferencesHelper;

    @Inject
    public AppPreferencesHelper(CommonPreferencesHelper commonPreferencesHelper) {
        this.commonPreferencesHelper = commonPreferencesHelper;
    }

    @Override
    public void setLoggedIn(boolean value) {
        commonPreferencesHelper.setBooleanToPrefs(PREF_KEY_IS_LOGGED_IN, value);
    }

    @Override
    public boolean isLoggedIn() {
        return commonPreferencesHelper.getBooleanFromPrefs(PREF_KEY_IS_LOGGED_IN);
    }

    @Override
    public void setUserName(String userName) {
        commonPreferencesHelper.setStringToPrefs(PREF_KEY_USER_NAME, userName);
    }

    @Override
    public String getUserName() {
        return commonPreferencesHelper.getStringFromPrefs(PREF_KEY_USER_NAME);
    }

    @Override
    public String getAccessToken() {
        return commonPreferencesHelper.getStringFromPrefs(PREF_KEY_USER_TOKEN);
    }

    @Override
    public void setAccessToken(String accessToken) {
        commonPreferencesHelper.setStringToPrefs(PREF_KEY_USER_TOKEN, accessToken);
    }

}
