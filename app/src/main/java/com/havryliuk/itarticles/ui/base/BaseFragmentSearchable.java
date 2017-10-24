package com.havryliuk.itarticles.ui.base;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.havryliuk.itarticles.R;

/**
 * Created by Igor Havrylyuk on 24.10.2017.
 */

@SuppressWarnings("deprecation")
public abstract class BaseFragmentSearchable extends BaseFragment
        implements SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener{

    protected String searchQuery;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_articles, menu);
        setupSearchView(menu.findItem(R.id.action_search));
    }

    private void setupSearchView(MenuItem searchItem) {
        if (searchItem != null) {
            MenuItemCompat.setOnActionExpandListener(searchItem, this);
            SearchManager searchManager = (SearchManager) (getActivity()
                    .getSystemService(Context.SEARCH_SERVICE));
            searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {
                searchView.setSearchableInfo(searchManager
                        .getSearchableInfo(getActivity().getComponentName()));
                searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                searchView.clearFocus();
                searchView.setOnQueryTextListener(this);
                setupSearchOptions(searchView);
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchQuery = query;
        if (searchView != null) {
            searchView.clearFocus();
        }
        startSearch();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        searchQuery = query;
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        searchQuery = null;
        closeSearch();
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    protected abstract void startSearch();

    protected abstract void setupSearchOptions(SearchView searchView);

    protected abstract void closeSearch();
}
