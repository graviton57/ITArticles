package com.havryliuk.itarticles.data.data;

import com.havryliuk.itarticles.BuildConfig;
import com.havryliuk.itarticles.data.local.preferences.AppPreferencesHelper;
import com.havryliuk.itarticles.data.local.preferences.CommonPreferencesHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class PreferencesHelperTest {

    private AppPreferencesHelper appPreferencesHelper;

    @Before
    public void setUp(){
        CommonPreferencesHelper commonPreferencesHelper = new CommonPreferencesHelper(
                RuntimeEnvironment.application);

        appPreferencesHelper = new AppPreferencesHelper(commonPreferencesHelper);
    }

    @Test
    public void putAndGetUserName(){
        String userName = "Igor";
        appPreferencesHelper.setUserName(userName);
        assertEquals(userName, appPreferencesHelper.getUserName());
    }

    @Test
    public void putAndGetLoginInfo(){
        appPreferencesHelper.setLoggedIn(true);
        assertTrue(appPreferencesHelper.isLoggedIn());
    }
}
