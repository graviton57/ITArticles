package com.havryliuk.itarticles.data.remote.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public class DouArticle {

    private int id;
    private String title;
    private String url;
    private String category;
    @SerializedName("category_url")
    private String categoryUrl;
    private String announcement;
    private String tags;
    private int pageviews;
    @SerializedName("comments_count")
    private String commentsCount;
    @SerializedName("img_big")
    private String imgBig;
    @SerializedName("img_big_2x")
    private String imgBig2x;
    @SerializedName("img_small")
    private String imgSmall;
    @SerializedName("img_small_2x")
    private String imgSmall2x;
    @SerializedName("author_name")
    private String authorName;
    @SerializedName("author_url")
    private String authorUrl;
    private String published;
    private boolean isFavorite;

    public DouArticle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getPageviews() {
        return pageviews;
    }

    public void setPageviews(int pageviews) {
        this.pageviews = pageviews;
    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getImgBig() {
        return imgBig;
    }

    public void setImgBig(String imgBig) {
        this.imgBig = imgBig;
    }

    public String getImgBig2x() {
        return imgBig2x;
    }

    public void setImgBig2x(String imgBig2x) {
        this.imgBig2x = imgBig2x;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public String getImgSmall2x() {
        return imgSmall2x;
    }

    public void setImgSmall2x(String imgSmall2x) {
        this.imgSmall2x = imgSmall2x;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
