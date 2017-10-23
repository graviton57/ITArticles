package com.havryliuk.itarticles.data;

import com.havryliuk.itarticles.data.local.database.IDataBaseHelper;
import com.havryliuk.itarticles.data.local.preferences.IPreferencesHelper;
import com.havryliuk.itarticles.data.remote.IApiHelper;


/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public interface IDataManager extends IPreferencesHelper, IDataBaseHelper, IApiHelper {

}
