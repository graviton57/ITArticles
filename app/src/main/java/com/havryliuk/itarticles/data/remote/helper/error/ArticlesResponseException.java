package com.havryliuk.itarticles.data.remote.helper.error;

import timber.log.Timber;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */
public class ArticlesResponseException extends Throwable {

    public ArticlesResponseException(String detailMessage) {
        super(detailMessage);
        Timber.d("ArticlesResponseException=%s",detailMessage);
    }

}
