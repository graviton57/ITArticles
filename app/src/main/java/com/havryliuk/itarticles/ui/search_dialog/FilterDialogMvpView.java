package com.havryliuk.itarticles.ui.search_dialog;


import com.havryliuk.itarticles.ui.base.DialogMvpView;
import com.havryliuk.itarticles.utils.events.SearchParamEvent;

/**
 * Created by Igor Havrylyuk on 24.10.2017.
 */

public interface FilterDialogMvpView extends DialogMvpView {

    void onFilterValueChange(SearchParamEvent event);

    void dismissDialog();
}
