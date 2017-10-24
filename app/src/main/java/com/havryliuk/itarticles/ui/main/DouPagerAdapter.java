
package com.havryliuk.itarticles.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.havryliuk.itarticles.ui.articles.ArticlesFragment;
import com.havryliuk.itarticles.ui.favorites.FavoritesFragment;

import javax.inject.Inject;

/**
 * Created by Igor Havrylyuk on 21.09.2017.
 */

public class DouPagerAdapter extends FragmentPagerAdapter {

    private String[] pages;

    @Inject
    public DouPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setPages(String[] pages) {
        this.pages = pages;
    }

    @Override
    public int getCount() {
        return pages != null ? pages.length : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages[position];
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ArticlesFragment.newInstance();
            case 1:
                return FavoritesFragment.newInstance();
            default:
                return null;
        }
    }

}
