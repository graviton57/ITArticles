package com.havryliuk.itarticles.ui.favorites;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.havryliuk.itarticles.R;
import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.ui.base.BaseRecyclerViewAdapter;
import com.havryliuk.itarticles.utils.AppUtils;

import java.text.ParseException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public class FavoritesAdapter extends BaseRecyclerViewAdapter<FavoritesAdapter.ViewHolder, DouArticle> {

    public interface OnArticleClickListener{
        void onArticleClick(String url);
        void onFavoriteClick(int articleId, boolean isFavorite);
    }

    private Context context;
    private OnArticleClickListener listener;

    @Inject
    public FavoritesAdapter(Context context) {
        this.context = context;
        setData(new ArrayList<DouArticle>());
    }

    public void setListener(OnArticleClickListener listener) {
        this.listener = listener;
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoritesAdapter.ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DouArticle douArticle = getItem(position);
        final int articleId = douArticle.getId();
        final String articleUrl = douArticle.getUrl();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onArticleClick(articleUrl);
                }
            }
        });
        holder.txtTitle.setText(AppUtils.fromHtml(douArticle.getTitle()));
        holder.txtAuthorName.setText(douArticle.getAuthorName());
        String articleDate = douArticle.getPublished();
        try {
            holder.txtDate.setText(
                    AppUtils.FORMAT_FROM_ITEM_LIST.format(AppUtils.FORMAT_TO.parse(articleDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtViewsCount.setText(String.valueOf(douArticle.getPageviews()));
        holder.txtAnnouncement.setText(AppUtils.fromHtml(douArticle.getAnnouncement()));
        holder.txtComments.setText(douArticle.getCommentsCount());
        holder.txtCategory.setText(douArticle.getCategory());
        holder.txtTags.setText(douArticle.getTags());
        String articleImage = douArticle.getImgBig();
        final boolean isFavorite = douArticle.isFavorite();
        holder.imageStar.setImageURI(isFavorite ? Uri.parse("res:///" + R.drawable.ic_heart) :
                Uri.parse("res:///" + R.drawable.ic_heart_gray));
        holder.imageStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onFavoriteClick(articleId, isFavorite);
                }
            }
        });

        if (articleImage.isEmpty()){
            holder.imageView.setImageURI(Uri.parse(douArticle.getImgSmall()));
        } else {
            holder.imageView.setImageURI(Uri.parse(douArticle.getImgBig()));
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.article_title) TextView txtTitle;
        @BindView(R.id.article_author) TextView txtAuthorName;
        @BindView(R.id.article_date) TextView txtDate;
        @BindView(R.id.article_views) TextView txtViewsCount;
        @BindView(R.id.article_announcement) TextView txtAnnouncement;
        @BindView(R.id.article_category) TextView txtCategory;
        @BindView(R.id.article_tags) TextView txtTags;
        @BindView(R.id.article_comments) TextView txtComments;
        @BindView(R.id.article_image) SimpleDraweeView imageView;
        @BindView(R.id.image_star) SimpleDraweeView imageStar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
