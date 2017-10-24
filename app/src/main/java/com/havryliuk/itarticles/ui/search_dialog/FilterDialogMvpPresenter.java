package com.havryliuk.itarticles.ui.search_dialog;


import com.havryliuk.itarticles.ui.base.Presenter;
import com.havryliuk.itarticles.utils.events.SearchParamEvent;

/**
 * Created by Igor Havrylyuk on 24.10.2017.
 */

public interface FilterDialogMvpPresenter<V extends FilterDialogMvpView> extends Presenter<V> {

    void onSubmitClick(SearchParamEvent event);

}
