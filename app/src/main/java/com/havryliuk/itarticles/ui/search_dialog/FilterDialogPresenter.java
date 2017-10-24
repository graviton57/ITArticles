package com.havryliuk.itarticles.ui.search_dialog;


import com.havryliuk.itarticles.data.DataManager;
import com.havryliuk.itarticles.data.remote.helper.CompositeDisposableHelper;
import com.havryliuk.itarticles.ui.base.BasePresenter;
import com.havryliuk.itarticles.utils.events.SearchParamEvent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Igor Havrylyuk on 24.10.2017.
 */

public class FilterDialogPresenter<V extends FilterDialogMvpView> extends BasePresenter<V>
        implements FilterDialogMvpPresenter<V> {

    @Inject
    public FilterDialogPresenter(CompositeDisposableHelper compositeDisposableHelper,
                                 DataManager dataManager) {
        super(compositeDisposableHelper, dataManager);
    }

    @Override
    public void onSubmitClick(SearchParamEvent event) {
        Timber.i("onSubmitClick value=%s", event.toString());
        getMvpView().onFilterValueChange(event);
        getMvpView().dismissDialog();
    }

}
