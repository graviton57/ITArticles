package com.havryliuk.itarticles.ui.favorites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.havryliuk.itarticles.R;
import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.ui.base.BaseFragment;
import com.havryliuk.itarticles.utils.events.RefreshArticles;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Articles Fragment
 * Created by Igor Havrylyuk on 30.08.2017.
 */

@SuppressWarnings("deprecation")
public class FavoritesFragment extends BaseFragment implements FavoritesMvpView {

    @Inject
    FavoritesMvpPresenter<FavoritesMvpView> presenter;
    @Inject
    FavoritesAdapter favoritesAdapter;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txt_error)
    TextView txtError;

    public static FavoritesFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        getActivityFragmentComponent().inject(this);
        setUnBinder(ButterKnife.bind(this, view));
        presenter.attachView(this);
        initUI();
        return view;
    }

    @Override
    protected void initUI() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorWhite);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(favoritesAdapter);
        favoritesAdapter.setListener(new FavoritesAdapter.OnArticleClickListener() {
            @Override
            public void onFavoriteClick(int articleId, boolean isFavorite) {
                updateFavorite(articleId, isFavorite);
                updateData();
            }

            @Override
            public void onArticleClick(String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        updateData();
    }

    protected void updateData() {
        favoritesAdapter.clear();
        presenter.loadFavorites();
    }

    @Override
    public void showFavorites(List<DouArticle> favorites) {
        showRecycler(true);
        favoritesAdapter.addData(favorites);
    }

    @Override
    public void updateFavorite(int articleId, boolean isFavorite) {
        presenter.updateArticleFavorite(articleId, isFavorite);
    }

    @Override
    public void showEmptyView() {
        showRecycler(false);
        txtError.setText(R.string.error_no_data);
    }

    @Override
    public void onError(String message) {
        showRecycler(false);
        txtError.setText(message);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        presenter.detachView();
        super.onDestroy();
    }

    private void showRecycler(boolean visibility) {
        recyclerView.setVisibility(visibility ? View.VISIBLE : View.GONE);
        txtError.setVisibility(visibility ? View.GONE : View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Subscribe
    public void onEvent(RefreshArticles event) {
        if (isAdded()){
            updateData();
            Timber.d("onEvent() RefreshArticles" );
        }
    }

}
