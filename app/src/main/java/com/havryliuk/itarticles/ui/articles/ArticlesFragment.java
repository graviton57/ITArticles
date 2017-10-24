package com.havryliuk.itarticles.ui.articles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.havryliuk.itarticles.R;
import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.ui.base.BaseFragmentSearchable;
import com.havryliuk.itarticles.utils.AppUtils;
import com.havryliuk.itarticles.utils.events.RefreshArticles;
import com.havryliuk.itarticles.utils.listener.EndlessRecyclerViewScrollListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Articles Fragment
 * Created by Igor Havrylyuk on 24.10.2017.
 */

@SuppressWarnings("deprecation")
public class ArticlesFragment extends BaseFragmentSearchable implements ArticlesMvpView,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String EXTRA_CATEGORY_KEY = "EXTRA_CATEGORY_KEY";

    @Inject
    ArticlesMvpPresenter<ArticlesMvpView> presenter;
    @Inject
    ArticlesAdapter articlesAdapter;

    private int categoryIndex;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txt_error)
    TextView txtError;

    public static ArticlesFragment newInstance(int categoryIndex) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_CATEGORY_KEY, categoryIndex);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            categoryIndex = getArguments().getInt(EXTRA_CATEGORY_KEY);
        }
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

    private void loadData(String searchTag, int index, int page){
        swipeRefreshLayout.setRefreshing(true);
        String categoryName = AppUtils.getCategoryName(getActivity(), index);
        presenter.loadArticles(searchTag, categoryName, page);
    }

    @Override
    protected void initUI() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorWhite);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        EndlessRecyclerViewScrollListener scrollListener =
                new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadData(searchQuery, categoryIndex, page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(articlesAdapter);
        articlesAdapter.setListener(new ArticlesAdapter.OnArticleClickListener() {
            @Override
            public void onFavoriteClick(int articleId, boolean isFavorite) {
                Toast.makeText(getActivity(), R.string.add_to_favorite,Toast.LENGTH_SHORT).show();
                updateArticle(articleId, isFavorite);
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
        articlesAdapter.clear();
        loadData(searchQuery, categoryIndex, 1);
    }

    @Override
    protected void startSearch() {
       updateData();
    }

    @Override
    protected void setupSearchOptions(SearchView searchView) {

    }

    @Override
    protected void closeSearch() {
        updateData();
    }

    @Override
    public void showArticles(List<DouArticle> articles) {
        showRecycler(true);
        articlesAdapter.addData(articles);
    }

    @Override
    public void updateArticle(int articleId, boolean isFavorite) {
        presenter.updateArticleFavorite(articleId, isFavorite);
        EventBus.getDefault().post(new RefreshArticles());
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
    public void onRefresh() {
        updateData();
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    private void showRecycler(boolean visibility) {
        recyclerView.setVisibility(visibility ? View.VISIBLE : View.GONE);
        txtError.setVisibility(visibility ? View.GONE : View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }
}
